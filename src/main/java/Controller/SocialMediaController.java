package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

// import Account Model
import Model.Account;

// import Account Service
import Service.AccountService;
import Service.AccountServiceImpl;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;

    // constructor
    public SocialMediaController() {
        accountService = new AccountServiceImpl();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        // create endpoints
        app.post("/register", this::createUser);
        return app;
    }

    /**
     * This is the handler for registering a new user.
     * @param context The Javalin Context object of the Request
     */
    private void createUser(Context ctx) {
        // get information from request ctx object
        Account account = ctx.bodyAsClass(Account.class);

        System.out.println("REQUEST BODY RECEIVED");
        System.out.println(account);
        
        // call account service to validate input and create account
        Account accountCreated = accountService.createAccount(account);

        System.out.println("In Controller after service call");
        System.out.println(accountCreated);
        
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
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


}