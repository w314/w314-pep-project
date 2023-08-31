package Service;

import java.util.List;

// import Models
import Model.Message;
import Model.Account;

// import DAO
import DAO.MessageDAO;
import DAO.MessageDAOmySQLImpl;
import DAO.AccountDAO;
import DAO.AccountDAOmySQLImpl;

public class MessageService {
    
    MessageDAO messageDAO;
    AccountDAO accountDAO;
    
    // constructor
    public MessageService() {
        this.messageDAO = new MessageDAOmySQLImpl();
        this.accountDAO = new AccountDAOmySQLImpl();
    }

    /**
     * Validates message received and uses DAO to add it to database.
     * @param Message message: the message to be processed
     * @return Message : the message added to the database
     */    
    public Message createMessage(Message message) {

        if(validMessage(message)) {
            Message createdMessage = messageDAO.createMessage(message);
            if(createdMessage != null) return createdMessage;
        }

        return null;
    }


    /**
     * Validates message received
     * @param Message message
     * @return boolean : true for valid message
     */    
    private boolean validMessage(Message message) {
        // validate message text
        if(!validMessageText(message.getMessage_text())) return false;
        // validate user
        if(!validPostedBy(message.getPosted_by())) return false;

        return true;
    }


    /**
     * Validates messageText
     * @param String messageText: text of the message
     * @return boolean: true for valid, false for invalid
     */    
    private boolean validMessageText(String messageText) {
        if(messageText == null) return false;
        // check for blank message
        if(messageText.length() == 0) return false;
        // check for too long message
        if(messageText.length() > 254) return false;
        
        return true;
    }


    /**
     * Validates user who posted the message
     * @param int postedBy: account_id of user posting the message
     * @return boolean: true for valid, false for invalid
     */    
    private boolean validPostedBy(int postedBy) {
        // check if postedBy account id exist in the account table
        Account userInDatabase = accountDAO.getAccountByAccountId(postedBy);
        // if account is is not found in the database return false
        if(userInDatabase == null) return false;

        return true;
    }


    /**
     * Uses the DAO to get all messages from the message table 
     * @return List<Message> : List of all the messages
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }    
    
    
    /**
     * Gets message by message id
     * @param int messageId: id of the message
     * @return Message: message found
     */       
    public Message getMessageByMessageId(int messageId) {
        return messageDAO.getMessageByMessageId(messageId);
    }


    /**
     * Deletes message by message id
     * @param int messageId: id of the message
     * @return Message: message deleted
     */   
    public Message deleteMessageById(int messageId) {
              
        // check if message in database
        Message retreivedMessage = this.getMessageByMessageId(messageId);

        // if message is in the database delete it
        if(retreivedMessage != null) {
            // if deletion is succesfull return message
            if(messageDAO.deleteMessageById(messageId)) return retreivedMessage;
        }

        // in all other cases return null
        return null;
    }    

    /**
     * Updates the message text of a message
     * @param int messageId: id of the message
     * @return Message: message updated
     */   
    public Message updateMessageText(int messageId, String messageText) {
        // get current message
        Message originalMessage = this.getMessageByMessageId(messageId);

        // if there was no original message return null
        if(originalMessage == null) return originalMessage;

        // if original message was found check validity of message text
        if(validMessageText(messageText)) {
            // return updated message if text was valid
            return messageDAO.updateMessageById(messageId, messageText);
        }
        
        // return null if messaget text is invalid
        return null;
    }


    /**
     * Gets all messages of a user
     * @param int userId: id of the user
     * @return List<Message>: messages of user
     */   
    public List<Message> getAllMessagesOfUser(int userId) {
        return messageDAO.getAllMessagesOfUser(userId);
    }

}