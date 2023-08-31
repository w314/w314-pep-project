// declare package
package DAO;

// import ConnectionUtility
import Util.ConnectionUtil;

// import Account Model
import Model.Account;

import java.sql.*;

public class AccountDAOmySQLImpl implements AccountDAO {

    /**
     * Creates new user in the account table 
     * @param Account account:  user to be created
     * @return Account : the user account added to the database
     */
    @Override
    public Account createAccount(Account account) {
        try {
            // use ConnectionUtil to get connection
            // it is a static method I can call on the class
            Connection conn =  ConnectionUtil.getConnection();

            // create sql string
            String sql = "INSERT INTO account (username, password) VALUES (?, ?);";

            // create  a preparedStatement
            // use Statement interface's RETURN_GENERATED_KEYS field
            // to make sure the generated account_id is returned
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // set the parameters
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            // execute preparedStatement
            ps.executeUpdate();      

            // process result
            ResultSet keys = ps.getGeneratedKeys();
            while(keys.next()){
                // get returned account_id
                int account_id = keys.getInt("account_id");                
                // return new account
                return new Account(account_id, account.getUsername(), account.getPassword());
            }
       
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        }

        return null;
    }


    /**
     * Gets user account from account table
     * where username matches the username parameter
     * @param String username
     * @return Account : the user account returned from the database
     */    
    @Override
    public Account getAccountByUsername(String username) {

        try {
            
            Connection conn = ConnectionUtil.getConnection();

            String sql = "SELECT * FROM account WHERE username = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            
            // use executeQuery method to get resultset
            ResultSet rs = ps.executeQuery();

            // process resultset and return account
            while(rs.next()) {
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }

        } catch(SQLException sqle) {
            sqle.printStackTrace();
        }

        return null;
    }


    @Override
    public Account getAccountByAccountId(int accountId) {
        
        try {

            Connection conn = ConnectionUtil.getConnection();

            String sql = "SELECT * FROM account WHERE account_id = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, accountId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
        
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        }

        return null;

    }
}