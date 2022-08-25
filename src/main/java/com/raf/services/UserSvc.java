package com.raf.services;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.xml.transform.Source;

import com.raf.dao.ReimburseDaoImpl;
import com.raf.dao.UserDaoImpl;
import com.raf.models.Reimbursement;
import com.raf.models.User;

public class UserSvc {
    UserDaoImpl uDao;
    ReimburseDaoImpl rDao;

    // TODO: Make a logger to track validation

    // Loosely bound constructors for Mockito compatability
    public UserSvc(){
        this.uDao = new UserDaoImpl();
        this.rDao = new ReimburseDaoImpl();
    }
    public UserSvc(UserDaoImpl uDao, ReimburseDaoImpl rDao){
        this.uDao = uDao;
        this.rDao = rDao;
    }
    // get user obj by username string
    public User getUserByUsername(String user){
        return uDao.getUserByUsername(user);
    }

    // user cred validation - GOOD
    public Boolean validateCreds(User credsToCheck){
        // get user from db
        User userFromDB = uDao.getUserByUsername(credsToCheck.getUsername());
        if(userFromDB == null){
            return false;
        }
        // check password
        if(userFromDB.getPassword().equals(credsToCheck.getPassword())){
            //then store user role & username
            return true;
        }
        // if password check fails
        return false;
    }

    // user view all past reimb requests
    public List<Reimbursement> viewPastRequests(User user){
        //DateTimeFormatter dfmt = DateTimeFormatter.ofPattern("uuuu-MM-dd");

        // LocalDateTime now = LocalDateTime.now();
        //System.out.println("Current time: "+dfmt.format(java.time.LocalDateTime.now()));
        return rDao.getReimbsViaNowAndStrCondition(user.getUsername(), "<", "=", "r.reimb_submitted", "u.ers_username");        
    }

    // user create new request
    public void createRequest(Reimbursement input){
        this.rDao.createNewRequestFromReimbObj(input);
    }
    
}
