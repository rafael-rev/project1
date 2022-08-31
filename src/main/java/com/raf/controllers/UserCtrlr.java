package com.raf.controllers;
import java.util.List;

import com.raf.models.Reimbursement;
import com.raf.models.Response;
import com.raf.models.User;
import com.raf.services.UserSvc;
import io.javalin.http.Context;

public class UserCtrlr {
    UserSvc uSvc = new UserSvc();

    // CREATE
    public void makeReimbFromJSON(Context ctx){
        Reimbursement newReimb = ctx.bodyAsClass(Reimbursement.class);
        try{
            uSvc.createRequest(newReimb);
            ctx.json(new Response(true, "Request submitted.", newReimb));
            ctx.status(200);
        }catch(Exception e){
            ctx.json(new Response(false, "mkFromJSON-REQ: Request failed.", null));
            System.out.println("makeReimbFromJSON: Request failed.");
            e.printStackTrace();
            ctx.status(500);
        }
    }

    // READ
    public void viewPastRequests(Context ctx){
        User user = ctx.sessionAttribute("user");
        List<Reimbursement> items = uSvc.viewPastRequests(user);
        if(items.isEmpty()){
            ctx.json(new Response(false, "user not found error", null ));
        }else{
            ctx.json(new Response(true, "User requests returned.", items));
        }
    }

    public void getFullNameViaID(Context ctx){
        Integer id = Integer.parseInt(ctx.pathParam("id"));
        String fullname = uSvc.getFullNameByID(id);
        ctx.result(fullname);
    }
}
