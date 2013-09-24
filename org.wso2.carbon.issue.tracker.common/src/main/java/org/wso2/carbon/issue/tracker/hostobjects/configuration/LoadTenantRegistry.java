package org.wso2.carbon.issue.tracker.hostobjects.configuration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.opensaml.xml.XMLObject;
import org.wso2.carbon.issue.tracker.hostobjects.internal.IssueTrackerManagerComponent;
import org.wso2.carbon.issue.tracker.hostobjects.internal.ServiceReferenceHolder;
import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.exceptions.RegistryException;
import org.wso2.carbon.registry.core.service.TenantRegistryLoader;
import org.wso2.carbon.registry.core.session.UserRegistry;
import org.wso2.carbon.user.api.UserStoreException;
import org.wso2.carbon.utils.multitenancy.MultitenantUtils;
import org.wso2.carbon.hostobjects.sso.internal.util.Util;

import javax.script.ScriptException;
import java.util.HashSet;
import java.util.Set;


public class LoadTenantRegistry extends ScriptableObject {
    private static final String hostObjectName = "LoadTenantRegistry";
    protected Registry registry;

    private static final Log log = LogFactory.getLog(LoadTenantRegistry.class);


    protected static Set<Integer> registryInitializedTenants = new HashSet<Integer>();

    public static boolean jsFunction_loadTenant(Context cx, Scriptable thisObj,
                                                Object[] args,
                                                Function funObj)
            throws Exception {

        int argLength = args.length;
        if (argLength != 1 || !(args[0] instanceof String)) {
            return false;
        }

        String decodedString = Util.decode((String) args[0]);

        XMLObject samlObject = Util.unmarshall(decodedString);
        String tenantDomain = Util.getDomainName(samlObject);

        try {
            int tenantId = ServiceReferenceHolder.getInstance().getRealmService().getTenantManager().getTenantId(tenantDomain);

            TenantRegistryLoader tenantRegistryLoader = IssueTrackerManagerComponent.getTenantRegistryLoader();
            //If the tenant registry has not been previously loaded
            if (!registryInitializedTenants.contains(tenantId)) {
                try {
                    tenantRegistryLoader.loadTenantRegistry(tenantId);
                    registryInitializedTenants.add(tenantId);
                } catch (Exception e) {
                    log.error("Error while loading registry of Tenant + " + tenantId + " " + e.getMessage());
                }
            }
        } catch (UserStoreException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        if (log.isWarnEnabled()) {
            log.warn("SAML response in signature validation is not a SAML Response.");
        }
        return true;
    }

    @Override
    public String getClassName() {
        return hostObjectName;
    }

    public static Scriptable jsConstructor(Context cx, Object[] args, Function ctorObj,
                                           boolean inNewExpr) throws Exception {
        return new LoadTenantRegistry();
    }

}
