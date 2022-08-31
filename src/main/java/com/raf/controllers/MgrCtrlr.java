package com.raf.controllers;
import java.util.List;

import com.raf.models.Approval;
import com.raf.models.Reimbursement;
import com.raf.models.Response;
import com.raf.services.ReimburseSvc;
import io.javalin.http.Context;

public class MgrCtrlr {
    ReimburseSvc rSvc = new ReimburseSvc();
    
    // READ
    public void mgrViewReimbsInSys(Context ctx){
        List<Reimbursement> items = this.rSvc.viewAllReimbRequests("all");

        ctx.json(new Response(true, "retrieving all request records", items));
    }

    // UPDATE
    public void mgrAssessRequest(Context ctx){
        Response response;
        try{
            Approval approval = ctx.bodyAsClass(Approval.class);
            Boolean choice = false;
            if(approval.getAccept() == 0){
                choice = false;
            }else if(approval.getAccept() == 1){
                choice = true;
            }
            response = rSvc.answerRequest(approval.getId(), choice);
            ctx.json(response);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
