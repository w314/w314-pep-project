// declare package
package DAO;

// import ConnectionUtility
import Util.ConnectionUtil;

import java.sql.*;

public class AccountDAOmySQLImpl implements AccountDAO {

    @Override
    public boolean createAccount(String userName, String password) {

        try {
            // use ConnectionUtil to get connection
            // it is a static method I can call on the class
            Connection conn =  ConnectionUtil.getConnection();

            // set sql string
            String sql = "INSERT INTO account (username, password) VALUES (?, ?);";

            // create  a preparedStatement
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, userName);
            ps.setString(2, password);

            // execute preparedStatement
            ResultSet rs = ps.executeQuery(sql);


        
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        }




    }
}