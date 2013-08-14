/**
 * 
 */
package org.wso2.carbon.issue.tracker.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.wso2.carbon.issue.tracker.bean.Project;
import org.wso2.carbon.issue.tracker.dao.ProjectDAO;

/**
 * Implementation of {@link ProjectDAO}
 * 
 */
public class ProjectDAOImpl implements ProjectDAO {

	/**
	 * {@inheritDoc}
	 */
	public void add(Project project) {

	}

	/**
	 * {@inheritDoc}
	 */
	public void update(Project project) {

	}

	/**
	 * {@inheritDoc}
	 */
	public Project get(int id) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<Project> getProjectsByOrganizationId(int organizationId) {
		return new ArrayList<Project>();
	}

}
