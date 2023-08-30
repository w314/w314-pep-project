package Service;

import Model.Message;

import DAO.MessageDAO;
import DAO.MessageDAOmySQLImpl;

public class MessageService {
    MessageDAO messageDAO;
    
    public MessageService() {
        this.messageDAO = new MessageDAOmySQLImpl();
    }

    public Message createMessage(Message message) {
        
        boolean validMessage = validMessageText(message.getMessage_text()) && validPostedBy(message.getPosted_by());
        if(validMessage) {
            Message createdMessage = messageDAO.createMessage(message);
            if(createdMessage != null) return createdMessage;
        }
        return null;
    }

    private boolean validMessageText(String messageText) {
        if(messageText == null) return false;
        if(messageText.length() >= 255) return false;
        return true;
    }

    private boolean validPostedBy(int postedBy) {
        return true;
    }
}