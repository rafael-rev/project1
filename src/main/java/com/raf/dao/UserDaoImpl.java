package com.raf.dao;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.raf.models.User;
import com.raf.utils.ConnectUtil;

public class UserDaoImpl {
    Logger logger = LogManager.getLogger(UserDaoImpl.class);
    ConnectUtil conn = new ConnectUtil();

    /////// RETRIEVE
    // Get User obj by username
    public User getUserByUsername(String username){

        return null;
    }
    
    // Get Users List by status (pending, approved, rejected)
    public List<User> getUserListByStatus(String status){

        return null;
    }
}
