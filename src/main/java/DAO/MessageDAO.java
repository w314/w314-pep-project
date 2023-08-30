// declare package
package DAO;

// import Account class from Model package
import Model.Message;

// import java.util.* to use the List Interface
import java.util.*;

// MessageDAO interface defines what interaction we can have
// with the Message table
public interface MessageDAO {

    // CRUD Operations necessary for this App

    // CREATE
    public Message createMessage(Message message);

    // READ
    // get all messages
    public List<Message> getAllMessages();
    // get message by id
    public Message getMessageByMessageId(int messageId);

    
    // get all messages of a user
    // public List<Message> getAllMessagesByUserId(int userId);

    // UPDATE
    // public boolean updateMessageById(int messageId, String messageBody);

    // DELETE
    // public boolean deleteMessageById(int messageId);
}