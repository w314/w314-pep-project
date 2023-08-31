// declare package
package DAO;

// import Message Model
import Model.Message;

// import the List Interface
import java.util.List;

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
    // get all message of a user
    public List<Message> getAllMessagesOfUser(int userId);

    // UPDATE
    // update message by id
    public Message updateMessageById(int messageId, String messageText);

    // DELETE
    // delete message by id
    public boolean deleteMessageById(int messageId);
    
}