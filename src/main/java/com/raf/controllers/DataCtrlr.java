package com.raf.controllers;
import com.raf.models.User;
import io.javalin.http.Context;

public class DataCtrlr {
    public void hello(Context ctx){
        System.out.println("Hello endpoint! :)");
        ctx.result("Hello World!");
    }
}
