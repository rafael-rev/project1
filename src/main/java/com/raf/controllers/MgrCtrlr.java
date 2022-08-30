package com.raf.controllers;
import java.util.List;
import com.raf.models.Reimbursement;
import com.raf.models.Response;
import com.raf.services.ReimburseSvc;
import io.javalin.http.Context;

public class MgrCtrlr {
    ReimburseSvc rSvc = new ReimburseSvc();
    
    // public DataCtrlr(){
    //     this.uSvc = new UserSvc();
    // }
    // public DataCtrlr(uSvc){
    //     this.uSvc = uSvc;
    // }


    public void hello(Context ctx){
        System.out.println("Hello endpoint! :)");
        ctx.result("Hello World!");
    }

    

    // READ
    public void mgrViewReimbsInSys(Context ctx){
        List<Reimbursement> items = this.rSvc.viewAllReimbRequests("all");

        ctx.json(new Response(true, "retrieving all request records", items));
    }


    // UPDATE
    public void mgrAssessRequest(Context ctx){
        
    }
}
