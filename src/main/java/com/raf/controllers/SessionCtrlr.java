package com.raf.controllers;
import com.raf.models.Response;
import com.raf.models.User;
import com.raf.services.UserSvc;

import io.javalin.http.Context;

public class SessionCtrlr {
    UserSvc uSvc = new UserSvc();

    public void login(Context ctx){
        //get creds and save to new User object
        User creds = ctx.bodyAsClass(User.class);
        //Validate creds
        if(uSvc.validateCreds(creds)){
            // grab user data from DB
            User userFromDb = uSvc.getUserByUsername(creds.getUsername());
            //Store the new User to session
            ctx.sessionAttribute("user", userFromDb);
            //send info to user
            ctx.json(new Response(true, "successful login", null));
        }else{
            ctx.json(new Response(false, "login failed at validation.", null));
        }
        
    }

    public void logout(Context ctx){
        // destroy the login session
        ctx.sessionAttribute("user", null);
        //send info to user
        ctx.json(new Response(true, "logging out", null));
    }

    public void checkSession(Context ctx){
        //look for value of user
        // setAttribute(key) used to GET info
        User user = ctx.sessionAttribute("user");

        if(user == null){
            ctx.json(new Response(false, "no session found", null));
        }else{
            // Found, return User object
            ctx.json(new Response(true, "session found", user));
        }
    }

}
