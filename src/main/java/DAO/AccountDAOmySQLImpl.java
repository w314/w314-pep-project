// declare package
package DAO;

// import ConnectionUtility
import Util.ConnectionUtil;

// import Account class
import Model.Account;

import java.sql.*;

public class AccountDAOmySQLImpl implements AccountDAO {

    @Override
    public Account createAccount(String userName, String password) {

        try {
            // use ConnectionUtil to get connection
            // it is a static method I can call on the class
            Connection conn =  ConnectionUtil.getConnection();

            // create sql string
            String sql = "INSERT INTO account (username, password) VALUES (?, ?);";

            // create  a preparedStatement
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, userName);
            ps.setString(2, password);

            // execute preparedStatement
            ResultSet rs = ps.executeQuery(sql);

            // process result
            while(rs.next()){
                int idReturned = rs.getInt("account_id");
                String username = rs.getString("username");
                String userpassword = rs.getString("password");
                
                Account account = new Account(idReturned, username, userpassword);
                // return new Account(idReturned, username, userpassword);
                System.out.println("In DAO returning Accountcretaed");
                System.out.println(account);
                return account;
            }

            // close connection
            conn.close();
        
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        }

        return null;

    }
}