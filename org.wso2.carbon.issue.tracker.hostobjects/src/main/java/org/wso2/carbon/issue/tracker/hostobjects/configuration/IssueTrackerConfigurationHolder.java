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


import org.wso2.carbon.issue.tracker.common.IssueTrackerConfiguration;

public class IssueTrackerConfigurationHolder {
    private IssueTrackerConfiguration issueTrackerConfiguration;
    private static org.wso2.carbon.issue.tracker.hostobjects.configuration.IssueTrackerConfigurationHolder instance = new org.wso2.carbon.issue.tracker.hostobjects.configuration.IssueTrackerConfigurationHolder();

    public static org.wso2.carbon.issue.tracker.hostobjects.configuration.IssueTrackerConfigurationHolder getInstance() {
        return instance;
    }

    public IssueTrackerConfiguration getIssueTrackerConfiguration() {
        return this.issueTrackerConfiguration;
    }

    public void registerIssueTrackerConfiguration(IssueTrackerConfiguration issueTrackerConfiguration) {
        this.issueTrackerConfiguration = issueTrackerConfiguration;
    }

    public void unRegisterIssueTrackerConfiguration(IssueTrackerConfiguration issueTrackerConfiguration) {
        this.issueTrackerConfiguration = null;
    }
}