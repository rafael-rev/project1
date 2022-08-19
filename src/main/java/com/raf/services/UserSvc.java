package com.raf.services;
import java.util.List;
import com.raf.dao.UserDaoImpl;
import com.raf.models.Reimbursement;
import com.raf.models.User;

public class UserSvc {
    UserDaoImpl uDao;

    // Mockito constructors
    public UserSvc(){
        this.uDao = new UserDaoImpl();
    }
    public UserSvc(UserDaoImpl uDao){
        this.uDao = uDao;
    }

    // user cred validation
    public Boolean validateCreds(User credsToCheck){

        return null;
    }

    // user view all past reimb requests
    public void viewPastRequests(User user){
        
        
    }

    // user create new request
    public Reimbursement createRequest(){

        return null;
    }
    
}
