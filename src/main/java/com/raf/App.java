package com.raf;
import com.raf.controllers.MgrCtrlr;
import com.raf.controllers.SessionCtrlr;
import com.raf.controllers.UserCtrlr;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class App {
    public static void main( String[] args ){
        MgrCtrlr mCtrl = new MgrCtrlr();
        UserCtrlr uCtrl = new UserCtrlr();
        SessionCtrlr sCtrl = new SessionCtrlr();
        // add the static web files to Javalin
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/frontend", Location.CLASSPATH);
        }).start(8999);

    // SESSION ENDPOINTS
    app.get("/session", sCtrl::checkSession);
    app.post("/session", sCtrl::login);
    app.delete("/session", sCtrl::logout);
    
    // USER: MAKE NEW REIMB REQUEST
    app.post("/api/user", uCtrl::makeReimbFromJSON);
    // USER: VIEW past requests
    app.get("/api/user", uCtrl::viewPastRequests);

    // MANAGER: VIEW ALL reimb requests in system
    app.get("/api/manager", mCtrl::mgrViewReimbsInSys);
    // MANAGER-UTIL: Get Full User name via ID (Path Param)
    app.get("/api/manager/{id}", uCtrl::getFullNameViaID);
    // MANAGER: APPROVE/DENY pending requests (Query Param)
    app.patch("/api/manager", mCtrl::mgrAssessRequest);
    }
}