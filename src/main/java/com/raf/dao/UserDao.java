package com.raf.dao;
import java.util.List;
import com.raf.models.User;

public interface UserDao {

    ////////////////// FOLLOW CRUD

    /////// CREATE
    //  -- creating new Users not required


    /////// RETRIEVE
    // Get User obj by username
    User getUserByUsername(String username);
    // Get Users List by status (pending, approved, rejected)
    List<User> getUserListByStatus(String status);
    // 

    /////// UPDATE
    //  -- User entries modification not required

    /////// DELETE
}   //  -- User entry deletion not required
