/**
 * 
 */
package org.wso2.carbon.issue.tracker.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.wso2.carbon.issue.tracker.bean.Project;
import org.wso2.carbon.issue.tracker.dao.ProjectDAO;
import org.wso2.carbon.issue.tracker.util.DBConfiguration;

/**
 * Implementation of {@link ProjectDAO}
 * 
 */
public class ProjectDAOImpl implements ProjectDAO {

    private static Logger logger = Logger.getLogger(ProjectDAOImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public int add(Project project) throws SQLException {

        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        boolean isInserted = false;
        String insertTableSQL =
                                "INSERT INTO PROJECT (PROJECT_NAME,OWNER,DESCRIPTION,ORGANIZATION_ID,PROJECT_KEY) VALUES (?,?,?,?,?)";

        int projectId=0;
        try {
            dbConnection = DBConfiguration.getDBConnection();
            preparedStatement = dbConnection.prepareStatement(insertTableSQL, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, project.getName());
            preparedStatement.setString(2, project.getOwner());
            preparedStatement.setString(3, project.getDescription());
            preparedStatement.setInt(4, project.getOrganizationId());
            preparedStatement.setString(5, project.getKey());

            // execute insert SQL stetement
            isInserted = preparedStatement.executeUpdate()==1 ? true:false;

            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            projectId = rs.getInt(1);

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
        return projectId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(Project project) throws SQLException {

        String updateTableSQL =
                                "UPDATE PROJECT SET PROJECT_NAME = ?, OWNER = ?, DESCRIPTION = ? WHERE PROJECT_KEY = ? AND ORGANIZATION_ID = ?";
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        int effectedRows = -1;
        try {
            dbConnection = DBConfiguration.getDBConnection();
            preparedStatement = dbConnection.prepareStatement(updateTableSQL);

            preparedStatement.setString(1, project.getName());
            preparedStatement.setString(2, project.getOwner());
            preparedStatement.setString(3, project.getDescription());
            preparedStatement.setString(4, project.getKey());
            preparedStatement.setInt(5, project.getOrganizationId());

            // execute update SQL stetement
            effectedRows = preparedStatement.executeUpdate();
            
            
        } catch (SQLException e) {

            logger.error(e.getMessage(), e);
            throw e;

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }
        return effectedRows > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Project get(String key, int tenantId) throws SQLException {

        String selectSQL =
                           "SELECT PROJECT_ID, PROJECT_NAME, OWNER, DESCRIPTION, ORGANIZATION_ID FROM PROJECT WHERE PROJECT_KEY = ? AND ORGANIZATION_ID=?" ;
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        Project project = null;

        try {
            dbConnection = DBConfiguration.getDBConnection();
            preparedStatement = dbConnection.prepareStatement(selectSQL);
            preparedStatement.setString(1, key);
            preparedStatement.setInt(2, tenantId);

            preparedStatement.setMaxRows(1);

            // execute select SQL stetement
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.first()) {

                project = new Project();
                project.setId(rs.getInt("PROJECT_ID"));
                project.setName(rs.getString("PROJECT_NAME"));
                project.setOwner(rs.getString("OWNER"));
                project.setDescription(rs.getString("DESCRIPTION"));
                project.setOrganizationId(rs.getInt("ORGANIZATION_ID"));

            }

        } catch (SQLException e) {

            logger.error(e.getMessage(), e);

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }

        return project;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Project> getProjectsByOrganizationId(int organizationId) throws SQLException {

        String selectSQL =
                           "SELECT PROJECT_ID, PROJECT_NAME, OWNER, DESCRIPTION, ORGANIZATION_ID, PROJECT_KEY FROM PROJECT WHERE ORGANIZATION_ID = ?";
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        List<Project> projects = new ArrayList<Project>();
        try {
            dbConnection = DBConfiguration.getDBConnection();
            preparedStatement = dbConnection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, organizationId);

            // execute select SQL stetement
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Project project = new Project();
                project.setId(rs.getInt("PROJECT_ID"));
                project.setName(rs.getString("PROJECT_NAME"));
                project.setOwner(rs.getString("OWNER"));
                project.setDescription(rs.getString("DESCRIPTION"));
                project.setOrganizationId(rs.getInt("ORGANIZATION_ID"));
                project.setKey(rs.getString("PROJECT_KEY"));
                projects.add(project);
            }

        } catch (SQLException e) {

            logger.error(e.getMessage(), e);

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }

        return projects;
    }

}
