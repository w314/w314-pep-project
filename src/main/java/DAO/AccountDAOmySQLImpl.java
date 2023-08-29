// declare package
package DAO;

// import ConnectionUtility
import Util.ConnectionUtil;

// import Account Model
import Model.Account;

import java.sql.*;

public class AccountDAOmySQLImpl implements AccountDAO {

    @Override
    public Account createAccount(String username, String password) {

        System.out.println("IN DAO inputs recieved");
        System.out.println(username);
        System.out.println(password);
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
            ps.setString(1, username);
            ps.setString(2, password);

            // execute preparedStatement
            ps.executeUpdate();      

            // process result
            ResultSet keys = ps.getGeneratedKeys();
            while(keys.next()){
                // get returned account_id
                int account_id = keys.getInt("account_id");                
                // return new account
                return new Account(account_id, username, password);
            }

            // this would be the place to close the connection
            // conn.close();
        
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        }

        return null;

    }
}