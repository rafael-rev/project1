package com.raf.dao;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.raf.models.Reimbursement;
import com.raf.utils.ConnectUtil;


public class ReimburseDaoImpl {
    Logger logger = LogManager.getLogger(UserDaoImpl.class);
    ConnectUtil conn = new ConnectUtil();
    
    /////// CREATE
    public void createNewRequestFromReimbObj(Reimbursement reimb_obj){

        
    }


    /////// RETRIEVE
    // get list of reimbursement objects
    public List<Reimbursement> getListOfAllReimbObjs(){

        return null;
    }
    // get reimburse obj via status
    public List<Reimbursement> getAllReimbViaStatus(String status){

        return null;
    }
    // get reimburse obj via author
    public List<Reimbursement> getAllReimbsViaAuthor(Integer auth_id){

        return null;
    }
    // get reimburse obj via resolver
    public List<Reimbursement> getAllReimbsViaResolver(Integer resolver_id){

        return null;
    }
    // get reimburse obj via username 
    public List<Reimbursement> getAllReimbsViaUsername(String username){

        return null;
    }
    // get reimbs via 2 string conditions
    public List<Reimbursement> getReimbsViaTwoStrConditions(String whereTgtOne, String whereTgtTwo, String whereCondOne, String whereCondTwo){

        return null;
    }
    // get reimbs via 2 int conditions
    public List<Reimbursement> getReimbsViaTwoIntConditions(Integer whereTgtOne, Integer whereTgtTwo, String whereCondOne, String whereCondTwo){

        return null;
    }
    // get reimbs via 1 int & 1 str conditions
    public List<Reimbursement> getReimbsViaTwoMixedConditions(Integer whereTgtOne, String whereTgtTwo, String whereCondOne, String whereCondTwo){

        return null;
    }


    /////// UPDATE
    // update request entry by ID for fields (resolved time, resolver, status_id)
    public void modifyRequestViaReimbID(Integer reimb_id, Integer status_id){

        
    }
}
