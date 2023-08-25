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
    public boolean createMessage();

    // READ
    // get message by id
    public Message getAccountById(int messageId);
    // get all messages of a user
    public List<Message> getAllMessagesByUserId(int userId);
    // get all messages
    public List<Message> getAllMessages();

    // UPDATE
    public boolean updateMessageById(int messageId, String messageBody);

    // DELETE
    public boolean deleteMessageById(int messageId);
}