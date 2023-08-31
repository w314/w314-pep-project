package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

// import Models
import Model.Account;
import Model.Message;

// import Services
import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    // constructor
    public SocialMediaController() {
        accountService = new AccountService();
        messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        // create javalin app
        Javalin app = Javalin.create();

        // create endpoints
        app.post("/register", this::createUser);
        app.post("/login", this::login);
        app.post("/messages", this::createMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessageByMessageId);
        app.delete("/messages/{message_id}", this::deleteMessageById);
        app.patch("messages/{message_id}", this::updateMessageText);
        app.get("/accounts/{account_id}/messages",this::getAllMessagesOfUser);

        // return javalin app
        return app;
    }

    /**
     * This is the handler for registering a new user.
     * @param ctx The Javalin Context object of the Request
     */
    private void createUser(Context ctx) {
        // get information from request ctx object
        Account account = ctx.bodyAsClass(Account.class);
        
        // call account service to validate input and create account
        Account accountCreated = accountService.createAccount(account);
        
        // send response based on the result of the service call
        // if account was succesfully created
        if( accountCreated != null) {
            // set status to 200
            ctx.status(200);
            // send account added as json in response body
            ctx.json(accountCreated);
        // if there was en error to create the account
        } else {
            // set status to 400
            ctx.status(400);
        }

    }


    /**
     * This is the handler for user login.
     * @param ctx The Javalin Context object of the Request
     */
    private void login(Context ctx) {
        
        // get user information form the request body
        Account user = ctx.bodyAsClass(Account.class);
        
        // validate login with accountService
        Account validatedUser = accountService.login(user);

        // if there is validated user returned
        if(validatedUser != null) {
            // send 200 status code
            ctx.status(200);
            // send user object in response body as json
            ctx.json(validatedUser);

        // if user could not be validated
        } else {
            // send 401 error code
            ctx.status(401);
        }
    }


    /**
     * This is the handler for creating a new message.
     * @param ctx The Javalin Context object of the Request
     */
    private void createMessage(Context ctx) {

        Message message = ctx.bodyAsClass(Message.class);

        Message createdMessage = messageService.createMessage(message);

        if(createdMessage != null) {
            ctx.status(200);
            ctx.json(createdMessage);
        } else {
            ctx.status(400);
        }       
    }


    /**
     * This is the handler for getting all messages
     * @param ctx The Javalin Context object of the Request
     */    
    private void getAllMessages(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.status(200);
        ctx.json(messages);
    }


    /**
     * This is the handler for getting a messages by message id
     * @param ctx The Javalin Context object of the Request
    */    
    private void getMessageByMessageId(Context ctx) {
        
        // get message id from context object and cast it as an integer
        int messageId = Integer.valueOf(ctx.pathParam("message_id"), 10);

        // user messageService to get message
        Message message = messageService.getMessageByMessageId(messageId);

        // set response
        ctx.status(200);
        if(message != null) {
            ctx.json(message);
        }
    }


    /**
     * This is the handler deleting a messages by message id
     * @param ctx The Javalin Context object of the Request
    */      
    private void deleteMessageById(Context ctx) {
        
        // get message id from context objec and cast it as an integer
        int messageId = Integer.valueOf(ctx.pathParam("message_id"), 10);

        Message deletedMessage = messageService.deleteMessageById(messageId);

        // send response
        ctx.status(200);
        // if there was a message deleted send it as a JSON object
        if(deletedMessage != null) {
            ctx.json(deletedMessage);
        }
    }


    /**
     * This is the handler updating a messages's text
     * @param ctx The Javalin Context object of the Request
    */      
    private void updateMessageText(Context ctx) {
        
        // get message id
        int messageId = Integer.valueOf(ctx.pathParam("message_id"), 10);
        // get message text from request body
        Message message = ctx.bodyAsClass(Message.class);
        String messageText = message.getMessage_text();
        
        System.out.println("IN CONTROLLER MESSAGE ID IN REQUEST");
        System.out.println(messageId);
        System.out.println("IN CONTROLLER MESSAGE TEXT IN REQUEST");
        System.out.println(messageText);

        // use message Service to update message
        Message updatedMessage = messageService.updateMessageText(messageId, messageText);

        // send response
        if(updatedMessage == null) {
            ctx.status(400);
        } else {
            ctx.status(200);
            ctx.json(updatedMessage);
        }
    }


    /**
     * This is the handler gets all messages of a user
     * @param ctx The Javalin Context object of the Request
    */     
    private void getAllMessagesOfUser(Context ctx) {
        // get user id
        int userId = Integer.valueOf(ctx.pathParam("account_id"), 10);

        // get messages
        List<Message> messages = messageService.getAllMessagesOfUser(userId);

        // send response
        ctx.status(200);
        ctx.json(messages);
    }
}