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

        System.out.println("Account in accountService:");
        System.out.println(account);

        // Account accountCreated = null;
        if(account.password.length() >= 4) {
            return accountDAO.createAccount(account.username, account.password);
        }
        // System.out.println(accountCreated);

        return null;
    }
}