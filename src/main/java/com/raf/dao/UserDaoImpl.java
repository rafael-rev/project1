package com.raf.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.raf.models.User;
import com.raf.utils.ConnectUtil;

public class UserDaoImpl implements UserDao{
    Logger logger = LogManager.getLogger(UserDaoImpl.class);
    ConnectUtil conn = new ConnectUtil();

    /////// RETRIEVE
    // Get User obj by username
    public User getUserByUsername(String username){
        User user = null;
        try(Connection conn = ConnectUtil.getConnection()){
            String qry = "select * from ers_users where ers_username = ?";
            PreparedStatement ps = conn.prepareStatement(qry);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7));
            }
            conn.close();
        }catch(SQLException e){
            logger.error("UserDao - getUserByUsername : SQLException", e);
        }
        return user;
    }
    
    // Get Users List by status (pending, approved, rejected)
    public List<User> getUserListByStatus(Integer status){
        User user = null;
        List<User> status_list = new ArrayList<>();
        try(Connection conn = ConnectUtil.getConnection()){
            String qry = "select * from ers_users u inner join ers_reimbursement er on u.ers_users_id = er.reimb_author where er.reimb_status_id = ?";
            PreparedStatement ps = conn.prepareStatement(qry);
            ps.setInt(1, status);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                status_list.add(user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7)));
            }
            conn.close();
        }catch(SQLException e){
            logger.error("UserDao - getUserListByStatus : SQLException", e);
        }
        return status_list;
    }

    // Get Username from UserID/AuthorID
    public String getUsernameByUserID(Integer userID){
        String output = "";
        try(Connection conn = ConnectUtil.getConnection()){
            String qry = "select ers_username from ers_users u inner join ers_reimbursement er on u.ers_users_id = er.reimb_author where er.reimb_author = ?";
            PreparedStatement ps = conn.prepareStatement(qry);
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                output = rs.getString(1);
            }
            conn.close();
        }catch(SQLException e){
            logger.error("UserDao - getUserListByStatus : SQLException", e);
        }
        return output;

    }
}
