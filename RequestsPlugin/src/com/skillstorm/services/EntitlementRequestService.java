package com.skillstorm.services;

import com.skillstorm.model.EntitlementRequest;
import sailpoint.plugin.PluginBaseHelper;
import sailpoint.plugin.PluginContext;
import sailpoint.tools.GeneralException;
import sailpoint.tools.IOUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntitlementRequestService {

    private PluginContext context;

    public EntitlementRequestService(PluginContext context) {
        this.context = context;
    }

    /**
     * Creates a new entitlement request and inserts it into the database.
     * Validates that both owner ID and requester ID are provided.
     * Returns an EntitlementRequest object representing the created request.
     */

    /**
     * Processes an entitlement request by updating its processed and approved status.
     * Updates the request in the database based on the given request
     * ID and approval status.
     */
    public void processRequest(int requestId, boolean approved) throws GeneralException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = context.getConnection();
            String sql = "UPDATE ep_plugin_entitlement_request SET processed = ?, approved = ? WHERE request_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setBoolean(1, true);
            statement.setBoolean(2, approved);
            statement.setInt(3, requestId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new GeneralException("No request found with the given requestId: " + requestId);
            }
        } catch(SQLException e) {
            throw new GeneralException(e);
        } finally {
            IOUtil.closeQuietly(statement);
            IOUtil.closeQuietly(connection);
        }
    }

    public EntitlementRequest createRequest(String ownerId, String requesterId, String entitlementId, String description) throws GeneralException {
        if (ownerId == null || requesterId == null || ownerId.isEmpty() || requesterId.isEmpty()) {
            throw new IllegalArgumentException("Owner ID and Requester ID must be provided.");
        }

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = context.getConnection();
            stmt = PluginBaseHelper.prepareStatement(conn,
                    "INSERT INTO ep_plugin_entitlement_request (owner_id, requester_id, entitlement_id, description, processed, approved) "
                            + "VALUES (?, ?, ?, ?, ?, ?)",
                    ownerId, requesterId, entitlementId, description, false, false);

            stmt.executeUpdate();

            EntitlementRequest rq = new EntitlementRequest();
            rq.setOwnerId(ownerId);
            rq.setRequesterId(requesterId);
            rq.setEntitlementId(entitlementId);
            rq.setDescription(description);
            rq.setProcessed(false);
            rq.setApproved(false);

            return rq;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtil.closeQuietly(stmt);
            IOUtil.closeQuietly(conn);
        }
    }

    /**
     * Retrieves a list of entitlement requests based on the owner's ID.
     * Returns a list of EntitlementRequest objects that match the given owner ID.
     */
    public List<EntitlementRequest> getRequestsByOwnerId(String ownerId) throws GeneralException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<EntitlementRequest> requests = new ArrayList<>();
        try {
            connection = context.getConnection();
            String sql = "SELECT * FROM ep_plugin_entitlement_request WHERE owner_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, ownerId);
            resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                System.out.println("HIT");
                EntitlementRequest request =
                        new EntitlementRequest
                                (       resultSet.getString("request_id"),
                                        resultSet.getString("owner_id"),
                                        resultSet.getString("requester_id"),
                                        resultSet.getString("entitlement_id"),
                                        resultSet.getBoolean("processed"),
                                        resultSet.getBoolean("approved"),
                                        resultSet.getString("description"));
                requests.add(request);
            }


            return requests;

        } catch(SQLException e) {
            throw new GeneralException(e);
        } finally {
            IOUtil.closeQuietly(resultSet);
            IOUtil.closeQuietly(statement);
            IOUtil.closeQuietly(connection);
        }

    }

    /**
     * Retrieves a list of entitlement requests based on the owner's ID and processed status.
     * Returns a list of EntitlementRequest objects that match the given criteria.
     */
    public List<EntitlementRequest> getRequestByManagerAndStatus(String ownerId, boolean status) throws GeneralException
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<EntitlementRequest> requests = new ArrayList<>();

        try {
            connection = context.getConnection();
            String sql = "SELECT * FROM ep_plugin_entitlement_request WHERE owner_id = ? AND processed = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, ownerId);
            statement.setBoolean(2, status);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                EntitlementRequest request = new EntitlementRequest(
                        resultSet.getString("request_id"),
                        resultSet.getString("owner_id"),
                        resultSet.getString("requester_id"),
                        resultSet.getString("entitlement_id"),
                        resultSet.getBoolean("processed"),
                        resultSet.getBoolean("approved"),
                        resultSet.getString("description")
                );
                requests.add(request);
            }

            return requests;

        } catch(SQLException e) {
            throw new GeneralException(e);
        } finally {
            IOUtil.closeQuietly(resultSet);
            IOUtil.closeQuietly(statement);
            IOUtil.closeQuietly(connection);
        }
    }



    /**
     * Deletes an entitlement request based on the given request ID.
     * Removes the request from the database and throws an exception if no request was found.
     */
    public void deleteRequestById(int requestId) throws GeneralException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = context.getConnection();
            String sql = "DELETE FROM ep_plugin_entitlement_request WHERE request_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, requestId);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted == 0) {
                throw new GeneralException("No request found with the given requestId: " + requestId);
            }
        } catch(SQLException e) {
            throw new GeneralException(e);
        } finally {
            IOUtil.closeQuietly(statement);
            IOUtil.closeQuietly(connection);
        }
    }
}
