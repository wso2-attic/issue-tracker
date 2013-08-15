/**
 * 
 */
package org.wso2.carbon.issue.tracker.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public void add(Project project) throws SQLException {

        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String insertTableSQL =
                                "INSERT INTO PROJECT (PROJECT_NAME,OWNER,DESCRIPTION,ORGANIZATION_ID) VALUES (?,?,?,?)";

        try {

            dbConnection = DBConfiguration.getDBConnection();
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);

            preparedStatement.setString(1, project.getName());
            preparedStatement.setString(2, project.getOwner());
            preparedStatement.setString(3, project.getDescription());
            preparedStatement.setInt(4, project.getOrganizationId());

            // execute insert SQL stetement
            preparedStatement.executeUpdate();

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
    }

    /**
     * {@inheritDoc}
     */
    public void update(Project project) throws SQLException {

        String updateTableSQL =
                                "UPDATE PROJECT SET PROJECT_NAME = ?, OWNER = ?, DESCRIPTION = ?, WHERE PROJECT_ID = ? AND ORGANIZATION_ID = ?";
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            dbConnection = DBConfiguration.getDBConnection();
            preparedStatement = dbConnection.prepareStatement(updateTableSQL);

            preparedStatement.setString(1, project.getName());
            preparedStatement.setString(2, project.getOwner());
            preparedStatement.setString(3, project.getDescription());
            preparedStatement.setInt(4, project.getId());
            preparedStatement.setInt(5, project.getOrganizationId());

            // execute update SQL stetement
            preparedStatement.executeUpdate();

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

    }

    /**
     * {@inheritDoc}
     */
    public Project get(int id) throws SQLException {

        String selectSQL =
                           "SELECT PROJECT_ID, PROJECT_NAME, OWNER, DESCRIPTION, ORGANIZATION_ID FROM PROJECT WHERE PROJECT_ID = ?";
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        Project project = null;

        try {
            dbConnection = DBConfiguration.getDBConnection();
            preparedStatement = dbConnection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, id);
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
    public List<Project> getProjectsByOrganizationId(int organizationId) throws SQLException {

        String selectSQL =
                           "SELECT PROJECT_ID, PROJECT_NAME, OWNER, DESCRIPTION, ORGANIZATION_ID FROM PROJECT WHERE ORGANIZATION_ID = ?";
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
