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

package org.wso2.carbon.issue.tracker.hostobjects.configuration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.issue.tracker.common.IssueTrackerConfiguration;

/**
 * this class is used to get the IssueTracker Configuration.
 *
 * @scr.component name="org.wso2.carbon.issue.tracker.hostobjects.configuration.IssueTrackerConfigurationHostObjectServiceComponent" immediate="true"
 * @scr.reference name="issue.tracker.common"
 * interface="org.wso2.carbon.issue.tracker.common.IssueTrackerConfiguration" cardinality="1..1"
 * policy="dynamic" bind="setIssueTrackerConfiguration" unbind="unsetIssueTrackerConfiguration"
 */
public class IssueTrackerConfigurationHostObjectServiceComponent {

    private Log log = LogFactory.getLog(IssueTrackerConfigurationHostObjectServiceComponent.class);

    protected void activate(ComponentContext ctxt) {
        if (log.isDebugEnabled()) {
            log.debug("Activated IssueTrackerConfigurationHostObjectServiceComponent");
        }
    }

    protected void deactivate(ComponentContext ctxt) {
        if (log.isDebugEnabled()) {
            log.debug("Deactivated IssueTrackerConfigurationHostObjectServiceComponent");
        }
    }

    protected void setIssueTrackerConfiguration(IssueTrackerConfiguration issueTrackerConfiguration) {
        IssueTrackerConfigurationHolder.getInstance().registerIssueTrackerConfiguration(issueTrackerConfiguration);
    }

    protected void unsetIssueTrackerConfiguration(IssueTrackerConfiguration issueTrackerConfiguration) {
        IssueTrackerConfigurationHolder.getInstance().unRegisterIssueTrackerConfiguration(issueTrackerConfiguration);
    }


}