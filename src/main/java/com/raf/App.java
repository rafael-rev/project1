package com.raf;

import javax.swing.plaf.basic.BasicTabbedPaneUI.MouseHandler;

import com.raf.controllers.MgrCtrlr;
import com.raf.controllers.SessionCtrlr;
import com.raf.controllers.UserCtrlr;
import com.raf.dao.UserDaoImpl;
import com.raf.models.Reimbursement;

import io.javalin.Javalin;

/**
 * Hello world!
 *
 */
public class App 
{
    
    public static void main( String[] args ){
        MgrCtrlr mCtrl = new MgrCtrlr();
        UserCtrlr uCtrl = new UserCtrlr();
        SessionCtrlr sCtrl = new SessionCtrlr();
        Javalin app = Javalin.create().start(8999);
        
    Reimbursement tmp = new Reimbursement(1, 400.00, "2020-08-15", null, "Train fares", "/resources/media/1.jpg", 2, 1, 1, 2);

    // Send Test
    //app.get("/", ctx -> ctx.result("RequestID: "+tmp.getId()+" Employee: "+tmp.getAuthor()));

    // HOMEPAGE
    app.get("/", mCtrl::hello);

    // SESSION ENDPOINTS - ALMOST DONE (LOGIN)
    app.get("/session", sCtrl::checkSession);
    app.post("/session", sCtrl::login);
    app.delete("/session", sCtrl::logout);
    
    // USER: MAKE NEW REIMB REQUEST - DONE
    app.post("/user", uCtrl::makeReimbFromJSON);

    // USER: VIEW past requests
    app.get("/user/{user}", uCtrl::viewPastRequests);

    // MANAGER: VIEW ALL reimb requests in system - DONE
    app.get("/manager", mCtrl::mgrViewReimbsInSys);

    // MANAGER: APPROVE/DENY pending requests --- prob can be done at one endpoint
    //app.patch("/manager", );
    // MANAGER: FILTER reimbs --- prob can be done at one endpoint
    
    }
}
