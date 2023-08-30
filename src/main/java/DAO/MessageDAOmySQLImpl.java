package DAO;

import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;

public class MessageDAOmySQLImpl implements MessageDAO {

    @Override
    public Message createMessage(Message message) {

        try {
            Connection conn = ConnectionUtil.getConnection();

            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();

            while(keys.next()) {
                int message_id = keys.getInt("message_id");
                return new Message(message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }

            return null;
        } catch(SQLException sqle) {
            sqle.printStackTrace();
        }

        return null;
    }
}