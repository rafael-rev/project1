package com.raf.services;
import com.raf.dao.ReimburseDaoImpl;
import com.raf.models.Response;

public class ReimburseSvc {
    ReimburseDaoImpl rDao;

    // Mockito constructors
    public ReimburseSvc(){
        this.rDao = new ReimburseDaoImpl();
    }
    public ReimburseSvc(ReimburseDaoImpl rDao){
        this.rDao = rDao;
    }

    // manager view all reimb requests
    public void viewAllReimbRequests(){

    }

    // manager approve/deny pending req
    public Response answerRequest(){

        return null;
    }


    // manager filter reimb by status
    public void seeReimbsByStatus(Integer status_id){
        
    }
}
