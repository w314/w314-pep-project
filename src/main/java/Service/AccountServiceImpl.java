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

    }
}