package Service;

import DAO.AccountDAO;
import DAO.AccountDAOmySQLImpl;
import Model.Account;

public class AccountService{

    AccountDAO accountDAO;

    // constructor
    public AccountService() {
        this.accountDAO = new AccountDAOmySQLImpl();
    }

    /**
     * Validates account information received and uses DAO to add it to database.
     * @param Account account : the account information to be processed
     * @return Account : the account created
     */
    public Account createAccount(Account account) {

        // check validity of account fields
        boolean validAccount = validUsername(account.getUsername()) && validPassword(account.getPassword());
        
        // if account if valid create account
        if(validAccount) {
            return accountDAO.createAccount(account);
        }

        // return null if account was invalid
        return null;
    }

    /**
     * Validates username
     * @param String username : the username to be validated
     * @return boolean : true for valid, false for invalid
     */    
    private boolean validUsername(String username) {
        if(username == null) return false;
        // check if username already exists in database
        Account userWithSameUserName = accountDAO.getAccountByUsername(username);
        // if there is alreay a user with the same username return false
        if(userWithSameUserName != null) return false;
        return true;
    }

    /**
     * Validates password
     * @param String password : the password to be validated
     * @return boolean : true for valid, false for invalid
     */    
    private boolean validPassword(String password) {
        if(password == null) return false;
        if(password.length() < 4) return false;
        return true;
    }


    /**
     * Validates username and password of the user trying to log in
     * @param Account account : the account information of the user logging in
     * @return Account : the user account that matches the login information
     */
    public Account login(Account user) {

        // get user from the database with the username provided
        Account userInDatabase = accountDAO.getAccountByUsername(user.getUsername());

        // if username does not exist in the database
        // and no users were returned return null
        if(userInDatabase == null) return null;

        // if password provided matches the one in the database return user
        if(userInDatabase.getPassword() == user.getPassword()) return userInDatabase;

        // if password did not match return null
        return null;

    }
}