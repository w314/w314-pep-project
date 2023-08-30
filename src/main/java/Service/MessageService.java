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
        
        boolean validMessage = validMessageText(message.getMessage_text()) && validPostedBy(message.getPosted_by());
        if(validMessage) {
            Message createdMessage = messageDAO.createMessage(message);
            if(createdMessage != null) return createdMessage;
        }
        return null;
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
        return messageDAO.deleteMessageById(messageId);
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

}