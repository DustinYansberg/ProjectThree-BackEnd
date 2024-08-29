package com.skillstorm.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.skillstorm.models.Document;

import sailpoint.plugin.PluginBaseHelper;
import sailpoint.plugin.PluginContext;
import sailpoint.tools.GeneralException;
import sailpoint.tools.IOUtil;

public class DocumentService {

    private PluginContext pluginContext;

    public DocumentService(PluginContext pluginContext) {
        this.pluginContext = pluginContext;
    }
    
    public Document createDocument(String identityId, String name, String fileData
    		) throws GeneralException {
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = pluginContext.getConnection();
//            statement = PluginBaseHelper.prepareStatement(connection, "INSERT INTO ep_plugin_document (identityId, name, fileData, isCompleted, assignedDateTime) "
//                                                                    + "VALUES (? ,?, ?, ?, CURRENT_TIMESTAMP)", identityId, name, fileData, false, assignedDateTime);
            Timestamp assignedDateTime = new Timestamp(System.currentTimeMillis());
            statement = PluginBaseHelper.prepareStatement(connection, "INSERT INTO ep_plugin_document (identityId, name, fileData, isCompleted, assignedDateTime) "
                  + "VALUES (? ,?, ?, ?, ?)", identityId , name, fileData, false, assignedDateTime);
            
            statement.executeUpdate();
            
//            return getDocumentsByIdentity(identityId);
            return null;
            
        } catch(SQLException e) {
            throw new GeneralException(e);
        } finally {
            IOUtil.closeQuietly(statement);
            IOUtil.closeQuietly(connection);
        }
        
    }

    
    public List<Document> getDocumentsByIdentity(String identityId) throws GeneralException {
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = pluginContext.getConnection();
            statement = PluginBaseHelper.prepareStatement(connection, 
                "SELECT * FROM ep_plugin_document WHERE identityId = ?", identityId);
            
            ResultSet result = statement.executeQuery();
            List<Document> documents = new LinkedList<>();

            while (result.next()) {
                Document document = new Document(identityId, null, null, false, null);
                document.setId(result.getInt("id"));
                document.setName(result.getString("name"));
                document.setCompleted(result.getBoolean("isCompleted"));
                document.setFileData(result.getString("fileData"));
                document.setAssignedDateTime(result.getTimestamp("assignedDateTime"));
                documents.add(document);
            }

            return documents;
            
        } catch (SQLException e) {
            throw new GeneralException(e);
        } finally {
            IOUtil.closeQuietly(statement);
            IOUtil.closeQuietly(connection);
        }
    }
    
    
    public void completeDocument(int documentId) throws GeneralException {
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = pluginContext.getConnection();
            statement = PluginBaseHelper.prepareStatement(connection, 
                "UPDATE ep_plugin_document SET isCompleted = ? WHERE id = ?", true, documentId);
            
            statement.executeUpdate();
            
        } catch (SQLException e) {
            throw new GeneralException(e);
        } finally {
            IOUtil.closeQuietly(statement);
            IOUtil.closeQuietly(connection);
        }
    }

}
