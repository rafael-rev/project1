package com.raf.dao;
import java.util.List;

import com.raf.models.Reimbursement;


public interface ReimburseDao {

    ////////////////// FOLLOW CRUD

    /////// CREATE
    void createNewRequestFromReimbObj(Reimbursement reimb_obj);


    /////// RETRIEVE
    // get list of reimbursement objects
    List<Reimbursement> getListOfAllReimbObjs();
    // get reimburse obj via status
    List<Reimbursement> getAllReimbViaStatus(String status);
    // get reimburse obj via author
    List<Reimbursement> getAllReimbsViaAuthor(Integer auth_id);
    // get reimburse obj via resolver
    List<Reimbursement> getAllReimbsViaResolver(Integer resolver_id);
    // get reimburse obj via username 
    List<Reimbursement> getAllReimbsViaUsername(String username);
    // get reimbs via 2 string conditions
    List<Reimbursement> getReimbsViaTwoStrConditions(String whereTgtOne, String whereTgtTwo, String whereCondOne, String whereCondTwo);
    // get reimbs via 2 int conditions
    List<Reimbursement> getReimbsViaTwoIntConditions(Integer whereTgtOne, Integer whereTgtTwo, String whereCondOne, String whereCondTwo);
    // get reimbs via 1 int & 1 str conditions
    List<Reimbursement> getReimbsViaTwoMixedConditions(Integer whereTgtOne, String whereTgtTwo, String whereCondOne, String whereCondTwo);


    /////// UPDATE
    // update request entry by ID for fields (resolved time, resolver, status_id)
    void modifyRequestViaReimbID(Integer reimb_id, Integer status_id);

    /////// DELETE
    //  -- probably not required as denied requests persist in db
    
    
}
