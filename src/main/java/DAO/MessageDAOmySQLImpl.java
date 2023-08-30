package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import Model.Message;
import Util.ConnectionUtil;

public class MessageDAOmySQLImpl implements MessageDAO {


    /**
     * Creates new message in the message table 
     * @param Message message: message to be created
     * @return Message : the message account added to the database
     */
    @Override
    public Message createMessage(Message message) {

        try {
            Connection conn = ConnectionUtil.getConnection();

            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();

            while(keys.next()) {
                int message_id = keys.getInt("message_id");
                return new Message(message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }

            return null;
            
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        }

        return null;
    }


    /**
     * Get all messages from the message table 
     * @return List<Message> : List of all the messages
     */    
    @Override
    public List<Message> getAllMessages() {

        // create empty list for messages
        List<Message> messages = new ArrayList<>();

        try {
            Connection conn = ConnectionUtil.getConnection();

            // using statement as there are no query parameters
            Statement stmt = conn.createStatement();
            // sql string
            String sql = "SELECT * FROM message;";
            // execute query
            ResultSet rs = stmt.executeQuery(sql);

            // process resultSet
            while(rs.next()) {
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }           
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        }
        
        // return list of messages
        return messages;
    }


    /**
     * Gets message by message id 
     * @param int messageId
     * @return Message : the message returned
     */   
    @Override
    public Message getMessageByMessageId(int messageId) {
        try {
            Connection conn = ConnectionUtil.getConnection();

            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, messageId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                return new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            }
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        }

        return null;
    }


    /**
     * Deletes message by message id 
     * @param int messageId
     * @return Message : the message deleted
     */
    @Override
    public Message deleteMessageById(int messageId) {
        
        // check if message in database
        Message message = this.getMessageByMessageId(messageId);

        // if message is in database delete it
        if(message != null) {
            try {
                Connection conn = ConnectionUtil.getConnection();
                String sql = "DELETE FROM message WHERE message_id = ?;";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, messageId);
    
                ps.executeUpdate();
            } catch(SQLException sqle) {
                sqle.printStackTrace();
            }
        }

        return message;
    }  
    
    
    /**
     * Updates text of existing message
     * @param int messageId: id message to update
     * @return Message : the message updated
     */    
    @Override
    public Message updateMessageById(int messageId, String messageText) {

        // if there is a message by the id provided update the message text
        try {
            Connection conn = ConnectionUtil.getConnection();

            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, messageId);
            ps.setString(2, messageText);

            ps.executeUpdate();
            
            // get updated message from database and return it
            return this.getMessageByMessageId(messageId);

        } catch(SQLException sqle) {
            sqle.printStackTrace();
        }

        return null;
    }


    /**
     * Gets all messages of a user
     * @param int userId: id of user
     * @return List<Message> : list of messages of user
     */    
    @Override
    public List<Message> getAllMessagesOfUser(int userId) {

        List<Message> messages = new ArrayList<>();

        try {
            Connection conn = ConnectionUtil.getConnection();

            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            PreparedStatement ps = conn. prepareStatement(sql);
            ps.setInt(1, userId);


            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        }

        return messages;
    }
}