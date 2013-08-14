/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *   * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.wso2.carbon.issue.tracker.dao;

import java.util.List;

import org.wso2.carbon.issue.tracker.bean.Project;

/**
 * Defines the contract for database operations for a {@link Project}
 * 
 */
public interface ProjectDAO {

    /**
     * Adds a new project to database.
     * 
     * @param project
     *            information about the project.
     */
    public void add(Project project);

    /**
     * Modified a project information. given by the {@link Project}#id
     * 
     * @param project
     *            new information about the project (except the id)
     */
    public void update(Project project);

    /**
     * Retrieves a Project specified by the id
     * 
     * @param id
     *            id of the project
     * @return a {@link Project} or null
     */
    public Project get(int id);

    /**
     * Returns projects owned by a particular organization (specified by
     * organization Id)
     * 
     * @param organizationId
     *            Id of the organization
     * 
     * @return a list of project. ( an empty list if there are no projects)
     */
    public List<Project> getProjectsByOrganizationId(int organizationId);

}