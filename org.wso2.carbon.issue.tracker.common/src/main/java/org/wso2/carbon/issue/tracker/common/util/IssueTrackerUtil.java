/*
 * Copyright 2005-2011 WSO2, Inc. (http://wso2.com)
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 */

package org.wso2.carbon.issue.tracker.common.util;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.ServiceClient;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.issue.tracker.common.IssueTrackerConfiguration;
import org.wso2.carbon.issue.tracker.common.IssueTrackerConstants;
import org.wso2.carbon.issue.tracker.common.IssueTrackerException;
import org.wso2.carbon.issue.tracker.common.internal.IssueTrackerCommonServiceComponent;
import org.wso2.carbon.securevault.SecretManagerInitializer;
import org.wso2.carbon.utils.CarbonUtils;
import org.wso2.securevault.SecretResolver;
import org.wso2.securevault.SecretResolverFactory;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Util class for building app factory configuration
 */
public class IssueTrackerUtil {
    private static final Log log = LogFactory.getLog(IssueTrackerUtil.class);
    private static SecretResolver secretResolver;
    private static Map<String, List<String>> configurationMap = new HashMap<String, List<String>>();
    private static IssueTrackerConfiguration issueTrackerConfig = null;

    private IssueTrackerUtil() throws IssueTrackerException {
        loadIssueTrackerConfiguration();
    }

    public static File getApplicationWorkDirectory(String applicationId, String version, String revision)
            throws IssueTrackerException {

        File tempDir = new File(CarbonUtils.getTmpDir() + File.separator + applicationId);
        return tempDir;
    }

    public static IssueTrackerConfiguration getIssueTrackerConfiguration() throws IssueTrackerException {
        if (issueTrackerConfig == null) {
            loadIssueTrackerConfiguration();
        }
        return issueTrackerConfig;
    }

    private static void loadIssueTrackerConfiguration() throws IssueTrackerException {
        OMElement issueTrackerElement = loadIssueTrackerXML();

        //Initialize secure vault
        SecretManagerInitializer secretManagerInitializer = new SecretManagerInitializer();
        secretManagerInitializer.init();
        secretResolver = SecretResolverFactory.create(issueTrackerElement, true);

        if (!IssueTrackerConstants.CONFIG_NAMESPACE.equals(issueTrackerElement.getNamespace()
                .getNamespaceURI())) {
            String message = "IssueTracker namespace is invalid. Expected ["
                    + IssueTrackerConstants.CONFIG_NAMESPACE + "], received ["
                    + issueTrackerElement.getNamespace() + "]";
            log.error(message);
            throw new IssueTrackerException(message);
        }

        Stack<String> nameStack = new Stack<String>();
        readChildElements(issueTrackerElement, nameStack);

        issueTrackerConfig = new IssueTrackerConfiguration(configurationMap);
    }


    private static OMElement loadIssueTrackerXML() throws IssueTrackerException {
        String fileLocation =
                new StringBuilder().append(CarbonUtils.getCarbonConfigDirPath())
                        .append(File.separator)
                        .append(IssueTrackerConstants.CONFIG_FOLDER)
                        .append(File.separator)
                        .append(IssueTrackerConstants.CONFIG_FILE_NAME).toString();

        File configFile = new File(fileLocation);
        InputStream inputStream = null;
        OMElement configXMLFile = null;
        try {
            inputStream = new FileInputStream(configFile);
            String xmlContent = IOUtils.toString(inputStream);
            configXMLFile = AXIOMUtil.stringToOM(xmlContent);
        } catch (IOException e) {
            String msg =
                    "Unable to read the file " + IssueTrackerConstants.CONFIG_FILE_NAME
                            + " at " + fileLocation;
            log.error(msg, e);
            throw new IssueTrackerException(msg, e);
        } catch (XMLStreamException e) {
            String msg = "Error in parsing " + IssueTrackerConstants.CONFIG_FILE_NAME;
            log.error(msg, e);
            throw new IssueTrackerException(msg, e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                String msg = "Error in closing stream ";
                log.error(msg, e);
            }
        }
        return configXMLFile;
    }

    private static void readChildElements(OMElement serverConfig, Stack<String> nameStack) {
        for (Iterator childElements = serverConfig.getChildElements(); childElements.hasNext(); ) {
            OMElement element = (OMElement) childElements.next();
            nameStack.push(element.getLocalName());

            secureVaultResolve(element);

            String nameAttribute = element.getAttributeValue(new QName("name"));
            if (nameAttribute != null && nameAttribute.trim().length() != 0) {
                //We have some name attribute 
                String key = getKey(nameStack);
                addToConfiguration(key, nameAttribute.trim());

                //all child element will be having this attribute as part of their name
                nameStack.push(nameAttribute.trim());
            }

            String enabledAttribute = element.getAttributeValue(new QName("enabled"));
            if (enabledAttribute != null && enabledAttribute.trim().length() != 0) {
                String key = getKey(nameStack) + ".Enabled";
                addToConfiguration(key, enabledAttribute.trim());

            }

            String text = element.getText();
            if (text != null && text.trim().length() != 0) {
                String key = getKey(nameStack);
                String value = replaceSystemProperty(text.trim());

                //Check wither the value is secured using secure valut
                if (isProtectedToken(key)) {
                    value = getProtectedValue(key);
                }
                addToConfiguration(key, value);
            }
            readChildElements(element, nameStack);

            //If we had a named attribute, we have to pop that out
            if (nameAttribute != null && nameAttribute.trim().length() != 0) {
                nameStack.pop();
            }
            nameStack.pop();
        }
    }

    private static String getKey(Stack<String> nameStack) {
        StringBuffer key = new StringBuffer();
        for (int i = 0; i < nameStack.size(); i++) {
            String name = nameStack.elementAt(i);
            key.append(name).append(".");
        }
        key.deleteCharAt(key.lastIndexOf("."));

        return key.toString();
    }

    private static String replaceSystemProperty(String text) {
        int indexOfStartingChars = -1;
        int indexOfClosingBrace;

        // The following condition deals with properties.
        // Properties are specified as ${system.property},
        // and are assumed to be System properties
        while (indexOfStartingChars < text.indexOf("${")
                && (indexOfStartingChars = text.indexOf("${")) != -1
                && (indexOfClosingBrace = text.indexOf('}')) != -1) { // Is a property used?

            //Get the system property name
            String sysProp = text.substring(indexOfStartingChars + 2,
                    indexOfClosingBrace);

            //Resolve the system property name to a value
            String propValue = System.getProperty(sysProp);

            //If the system property is carbon home and is relative path, 
            //we have to resolve it to absolute path
            if (sysProp.equals("carbon.home") && propValue != null
                    && propValue.equals(".")) {
                propValue = new File(".").getAbsolutePath() + File.separator;
            }

            //Replace the system property with valid value
            if (propValue != null) {
                text = text.substring(0, indexOfStartingChars) + propValue
                        + text.substring(indexOfClosingBrace + 1);
            }

        }
        return text;
    }

    private static boolean isProtectedToken(String key) {
        return secretResolver != null && secretResolver.isInitialized()
                && secretResolver.isTokenProtected("Carbon." + key);
    }

    private static String getProtectedValue(String key) {
        return secretResolver.resolve("Carbon." + key);
    }

    private static void addToConfiguration(String key, String value) {
        List<String> list = configurationMap.get(key);
        if (list == null) {
            list = new ArrayList<String>();
            list.add(value);
            configurationMap.put(key, list);
        } else {
            if (!list.contains(value)) {
                list.add(value);
            }
        }
    }


    private static void secureVaultResolve(OMElement element) {
        String secretAliasAttr = element.getAttributeValue(new QName(IssueTrackerConstants.SECURE_VAULT_NS,
                IssueTrackerConstants.SECRET_ALIAS_ATTR_NAME));
        if (secretAliasAttr != null) {
            element.setText(loadFromSecureVault(secretAliasAttr));
        }
    }

    public static synchronized String loadFromSecureVault(String alias) {
        if (secretResolver == null) {
            secretResolver = SecretResolverFactory.create((OMElement) null, false);
        }
        return secretResolver.resolve(alias);
    }


}

