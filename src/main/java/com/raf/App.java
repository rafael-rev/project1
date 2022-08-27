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

    // SESSION ENDPOINTS - DONE
    app.get("/session", sCtrl::checkSession);
    app.post("/session", sCtrl::login);
    app.delete("/session", sCtrl::logout);
    
    // USER: MAKE NEW REIMB REQUEST - DONE
    app.post("/api/user", uCtrl::makeReimbFromJSON);

    // USER: VIEW past requests - DONE
    app.get("/api/user", uCtrl::viewPastRequests);

    // MANAGER: VIEW ALL reimb requests in system - DONE
    app.get("/api/manager", mCtrl::mgrViewReimbsInSys);

    // MANAGER: APPROVE/DENY pending requests --- prob can be done at one endpoint or in JS
    //app.patch("/manager", );
    // MANAGER: FILTER reimbs --- prob can be done at one endpoint or in JS
    
    }
}
