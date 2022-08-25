package com.raf.controllers;
import java.util.List;

import com.raf.models.Reimbursement;
import com.raf.models.Response;
import com.raf.models.User;
import com.raf.services.UserSvc;
import io.javalin.http.Context;

public class UserCtrlr {
    UserSvc uSvc = new UserSvc();

    // CREATE - DONE
    public void makeReimbFromJSON(Context ctx){
        Reimbursement newReimb = ctx.bodyAsClass(Reimbursement.class);
        try{
            uSvc.createRequest(newReimb);
            ctx.json(new Response(true, "Request submitted.", newReimb));
            System.out.println("Request success.");
        }catch(Exception e){
            System.out.println("Request failed.");
            e.printStackTrace();
        }
    }

    // READ
    public void viewPastRequests(Context ctx){
        User user = ctx.sessionAttribute("user");
        System.out.println(user.toString());
        List<Reimbursement> items = uSvc.viewPastRequests(user);
        System.out.println("User: "+user.getUsername());
        if(items.isEmpty()){
            ctx.json(new Response(false, "user not found error", null ));
        }else{
            ctx.json(new Response(true, "User requests returned.", items));
        }
    }
}
