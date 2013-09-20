/*
*  Copyright (c) 2005-2011, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.wso2.carbon.issue.tracker.hostobjects.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.registry.core.service.RegistryService;
import org.wso2.carbon.registry.core.service.TenantRegistryLoader;
import org.wso2.carbon.user.core.service.RealmService;

/**
 * this class is used to get the IssueTracker Configuration.
 *
 * @scr.component name="org.wso2.carbon.issue.tracker.hostobjects.internal.IssueTrackerManagerComponent" immediate="true"
 * @scr.reference name="tenant.registryloader"
 * interface="org.wso2.carbon.registry.core.service.TenantRegistryLoader"
 * cardinality="1..1" policy="dynamic"
 * bind="setTenantRegistryLoader"
 * unbind="unsetTenantRegistryLoader"
 * @scr.reference name="user.realm.service"
 * interface="org.wso2.carbon.user.core.service.RealmService"
 * cardinality="1..1" policy="dynamic" bind="setRealmService" unbind="unsetRealmService"
 */

public class IssueTrackerManagerComponent {
    private Log log = LogFactory.getLog(IssueTrackerManagerComponent.class);
    private static TenantRegistryLoader tenantRegistryLoader;

    protected void activate(ComponentContext ctxt) {
        if (log.isDebugEnabled()) {
            log.debug("Activated IssueTrackerManagerComponent");
        }
    }

    protected void deactivate(ComponentContext ctxt) {
        if (log.isDebugEnabled()) {
            log.debug("Deactivated IssueTrackerManagerComponent");
        }
    }

    protected void setTenantRegistryLoader(TenantRegistryLoader tenantRegistryLoader) {
        this.tenantRegistryLoader = tenantRegistryLoader;
    }

    protected void unsetTenantRegistryLoader(TenantRegistryLoader tenantRegistryLoader) {
        this.tenantRegistryLoader = null;
    }

    public static TenantRegistryLoader getTenantRegistryLoader() {
        return tenantRegistryLoader;
    }

    public void setRegistryService(RegistryService registryService) {
        ServiceReferenceHolder.getInstance().setRegistryService(registryService);
    }

    public RealmService getRealmService() {
        return ServiceReferenceHolder.getInstance().getRealmService();
    }

    public void setRealmService(RealmService realmService) {
        ServiceReferenceHolder.getInstance().setRealmService(realmService);
    }

    protected void unsetRealmService(RealmService realmService) {
        ServiceReferenceHolder.getInstance().setRealmService(null);
    }

}
