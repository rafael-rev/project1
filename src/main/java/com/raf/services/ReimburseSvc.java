package com.raf.services;
import java.util.ArrayList;
import java.util.List;

import com.raf.dao.ReimburseDaoImpl;
import com.raf.models.Reimbursement;
import com.raf.models.Response;

public class ReimburseSvc {
    ReimburseDaoImpl rDao;

    // Loosely bound constructors for Mockito compatability
    public ReimburseSvc(){
        this.rDao = new ReimburseDaoImpl();
    }
    public ReimburseSvc(ReimburseDaoImpl rDao){
        this.rDao = rDao;
    }

    public Reimbursement getReimbByReimbID(Integer reimb_id){
        return rDao.getOneReimbByID(reimb_id);
    }

    // manager view all reimb requests
    public List<Reimbursement> viewAllReimbRequests(String type){
        List<Reimbursement> reimbs = new ArrayList<>();
        switch(type){
            case "all":
                reimbs = rDao.getListOfAllReimbObjs();
                break;
            case "pending":
                reimbs = rDao.getAllReimbViaStatus(1);
                break;
        }
        return reimbs;
    }

    // manager approve/deny pending req
    public Response answerRequest(Integer reimb_id, Boolean approved){
        Response response;
        if(approved == true){
            rDao.modifyRequestViaReimbID(reimb_id, 2);
            response = new Response(true, "Approved Request.", null);
        }else{
            rDao.modifyRequestViaReimbID(reimb_id, 3);
            response = new Response(false, "Request Rejected.", null);
        }
        return response;
    }


    // manager filter reimb by status
    public void seeReimbsByStatus(Integer status_id){
        // Probably don't need this, func supplied by viewAllREimbRequests
    }
}
