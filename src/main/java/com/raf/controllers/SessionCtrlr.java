package com.raf.controllers;
import com.raf.models.Response;
import com.raf.models.User;
import io.javalin.http.Context;

public class SessionCtrlr {
    public void login(Context ctx){
        //get creds and save to new User object
        User creds = ctx.bodyAsClass(User.class);
        //Store the new User to session
        ctx.sessionAttribute("user", creds);
        //send info to user
        ctx.json(new Response(true, "successful login", null));
    }

    public void logout(Context ctx){
        // destroy the login session
        ctx.sessionAttribute("user", null);
        //send info to user
        ctx.json(new Response(true, "logging out", null));
    }

    public User checkSession(Context ctx){
        //look for value of user
        // setAttribute(key) used to GET info
        User user = ctx.sessionAttribute("user");

        If(user == null){
            ctx.json(new Response(false, "no session found", null));
        }else{
            // Found, return User object
            ctx.json(new Response(true, "session found", user));
        }
    }

}
