// declare package
package DAO;

//import Account class from Model package
import Model.Account;

// AccountDAO interface defines what interaction 
// we can have with the account table
public interface AccountDAO {

    // CRUD Operations necessary for this App

    // CREATE
    public Account createAccount(String username, String password);

    // READ
    public Account getAccountByUsername(String username);
}