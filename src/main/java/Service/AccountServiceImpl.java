package Service;

import DAO.AccountDAO;
import DAO.AccountDAOmySQLImpl;
import Model.Account;

public class AccountServiceImpl implements AccountService{

    AccountDAO accountDAO;

    public AccountServiceImpl() {
        this.accountDAO = new AccountDAOmySQLImpl();
    }

    @Override
    public Account createAccount(Account account) {
        
        // check validity of account fields
        boolean validAccount = validUsername(account.getUsername()) && validPassword(account.getPassword());
        
        // if account if valid create account
        if(validAccount) {
            return accountDAO.createAccount(account.username, account.password);
        }

        // return null if account was invalid
        return null;
    }

    private boolean validUsername(String username) {
        System.out.println("IN VALIDUSERNAME METHOD:");
        System.out.println(username);
        if(username == null) return false;
        return true;
    }

    private boolean validPassword(String password) {
        if(password == null) return false;
        if(password.length() < 4) return false;
        return true;
    }
}