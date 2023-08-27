// declare package
package DAO;

//import Account class from Model package
import Model.Account;

// AccountDAO interface defines what interaction we can have
// with the Account table
public interface AccountDAO {

    // CRUD Operations necessary for this App

    // CREATE
    public Account createAccount(String userName, String password);

    // READ
    // public Account getAccountById();
}