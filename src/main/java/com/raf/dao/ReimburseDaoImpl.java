package com.raf.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.raf.controllers.SessionCtrlr;
import com.raf.models.Reimbursement;
import com.raf.utils.ConnectUtil;


public class ReimburseDaoImpl implements ReimburseDao{
    Logger logger = LogManager.getLogger(UserDaoImpl.class);
    ConnectUtil conn = new ConnectUtil();
    
    /////// CREATE
    public void createNewRequestFromReimbObj(Reimbursement reimb_obj){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Integer type_id = 4;
        switch(reimb_obj.getType()){
            case "lodging":
                type_id = 1;
                break;
            case "travel":
                type_id = 2;
                break;
            case "food":
                type_id = 3;
                break;
        }
        //Integer currentUserID = SessionCtrlr.currentUser.getUsers_id();

        try(Connection conn = ConnectUtil.getConnection()){
            String qry = "insert into ers_reimbursement (reimb_amount, reimb_submitted, reimb_description, reimb_author, reimb_status_id, reimb_type_id) values (?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(qry);
            //ps.setInt(1,reimb_obj.getId());
            ps.setDouble(1, reimb_obj.getAmount());
            ps.setTimestamp(2,timestamp);
            //ps.setString(4, reimb_obj.getTime_resolved());
            ps.setString(3,reimb_obj.getDescription());
            //ps.setString(4, reimb_obj.getReceipt());
            ps.setInt(4, SessionCtrlr.currentUser.getUsers_id());
            //ps.setInt(8, reimb_obj.getResolver());
            ps.setInt(5, 1);
            ps.setInt(6, type_id);
            ps.executeUpdate();
            conn.close();
        }catch(SQLException e){
            logger.error("ReimburseDao - createNewRequestFromReimbObj : SQLException", e);
        }
    }
    // public void createNewRequestFromReimbObj(Reimbursement reimb_obj){
    //     Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        
    //     try(Connection conn = ConnectUtil.getConnection()){
    //         String qry = "insert into ers_reimbursement (reimb_amount, reimb_submitted, reimb_description, reimb_receipt, reimb_author, reimb_status_id, reimb_type_id) values (?,?,?,?,?,?,?)";
    //         PreparedStatement ps = conn.prepareStatement(qry);
    //         //ps.setInt(1,reimb_obj.getId());
    //         ps.setDouble(1, reimb_obj.getAmount());
    //         ps.setTimestamp(2,timestamp);
    //         //ps.setString(4, reimb_obj.getTime_resolved());
    //         ps.setString(3,reimb_obj.getDescription());
    //         ps.setString(4, reimb_obj.getReceipt());
    //         ps.setInt(5, reimb_obj.getAuthor());
    //         //ps.setInt(8, reimb_obj.getResolver());
    //         ps.setInt(6, reimb_obj.getStatus_id());
    //         ps.setInt(7, reimb_obj.getType_id());
    //         ps.executeUpdate();
    //         conn.close();
    //     }catch(SQLException e){
    //         logger.error("ReimburseDao - createNewRequestFromReimbObj : SQLException", e);
    //     }
    // }

    /////// RETRIEVE
    public Reimbursement getOneReimbByID(Integer reimb_id){
        Reimbursement reimb = null;
        try(Connection conn = ConnectUtil.getConnection()){
            String qry = "select * from ers_reimbursement where reimb_id = ?";
            PreparedStatement ps = conn.prepareStatement(qry);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                reimb = new Reimbursement(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8),rs.getInt(9),rs.getInt(10), rs.getString(11), rs.getString(12));
            }
            conn.close();
        }catch(SQLException e){
            logger.error("ReimburseDao - getOneReimbByID : SQLException", e);
        }
        return reimb;
    }
    // get list of reimbursement objects
    public List<Reimbursement> getListOfAllReimbObjs(){
        Reimbursement reimb = null;
        List<Reimbursement> allReimbs = new ArrayList<>();
        try(Connection conn = ConnectUtil.getConnection()){
            String qry = "select reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, reimb_resolver, er.reimb_status_id, er.reimb_type_id, reimb_status, reimb_type from ers_reimbursement er inner join ers_reimbursement_status rs on er.reimb_status_id = rs.reimb_status_id inner join ers_reimbursement_type rt on er.reimb_type_id = rt.reimb_type_id inner join ers_users u on er.reimb_author =u.ers_users_id order by reimb_id";
            PreparedStatement ps = conn.prepareStatement(qry);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                allReimbs.add(reimb = new Reimbursement(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8),rs.getInt(9),rs.getInt(10), rs.getString(11), rs.getString(12)));
            }
            conn.close();
        }catch(SQLException e){
            logger.error("ReimburseDao - getListOfAllReimbObjs : SQLException", e);
        }
        return allReimbs;
    }
    // get reimburse obj via status
    public List<Reimbursement> getAllReimbViaStatus(Integer status){
        Reimbursement reimb = null;
        List<Reimbursement> statusReimbs = new ArrayList<>();
        try(Connection conn = ConnectUtil.getConnection()){
            String qry = "select * from ers_reimbursement where reimb_status_id = ?";
            
            PreparedStatement ps = conn.prepareStatement(qry);
            ps.setInt(1, status);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                statusReimbs.add(reimb = new Reimbursement(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8),rs.getInt(9),rs.getInt(10), rs.getString(11), rs.getString(12)));
            }
            conn.close();
        }catch(SQLException e){
            logger.error("ReimburseDao - getAllReimbViaStatus : SQLException", e);
        }
        return statusReimbs;
    }
    // get reimburse obj via author
    public List<Reimbursement> getAllReimbsViaAuthor(Integer auth_id){
        Reimbursement reimb = null;
        List<Reimbursement> authorReimbs = new ArrayList<>();
        try(Connection conn = ConnectUtil.getConnection()){
            String qry = "select * from ers_reimbursement where reimb_author = ?";
            
            PreparedStatement ps = conn.prepareStatement(qry);
            ps.setInt(1, auth_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                authorReimbs.add(reimb = new Reimbursement(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8),rs.getInt(9),rs.getInt(10), rs.getString(11), rs.getString(12)));
            }
            conn.close();
        }catch(SQLException e){
            logger.error("ReimburseDao - getAllReimbsViaAuthor : SQLException", e);
        }
        return authorReimbs;
    }
    // get reimburse obj via resolver
    public List<Reimbursement> getAllReimbsViaResolver(Integer resolver_id){
        Reimbursement reimb = null;
        List<Reimbursement> resolverReimbs = new ArrayList<>();
        try(Connection conn = ConnectUtil.getConnection()){
            String qry = "select * from ers_reimbursement where reimb_resolver = ?";
            
            PreparedStatement ps = conn.prepareStatement(qry);
            ps.setInt(1, resolver_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                resolverReimbs.add(reimb = new Reimbursement(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8),rs.getInt(9),rs.getInt(10), rs.getString(11), rs.getString(12)));
            }
            conn.close();
        }catch(SQLException e){
            logger.error("ReimburseDao - getAllReimbsViaResolver : SQLException", e);
        }
        return resolverReimbs;
    }
    // get reimburse obj via username 
    public List<Reimbursement> getAllReimbsViaUsername(String username){
        Reimbursement reimb = null;
        List<Reimbursement> resolverReimbs = new ArrayList<>();
        try(Connection conn = ConnectUtil.getConnection()){
            String qry = "select reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, er.reimb_status_id, er.reimb_type_id, reimb_status, reimb_type from ers_reimbursement er inner join ers_reimbursement_status rs on er.reimb_status_id = rs.reimb_status_id inner join ers_reimbursement_type rt on er.reimb_type_id = rt.reimb_type_id inner join ers_users u on er.reimb_author =u.ers_users_id  where u.ers_username = ?;";
            
            PreparedStatement ps = conn.prepareStatement(qry);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                resolverReimbs.add(reimb = new Reimbursement(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8),rs.getInt(9),rs.getInt(10), rs.getString(11), rs.getString(12)));
            }
            conn.close();
        }catch(SQLException e){
            logger.error("ReimburseDao - getAllReimbsViaUsername : SQLException", e);
        }
        return resolverReimbs;
    }
    // get reimbs via 2 string conditions
    public List<Reimbursement> getReimbsViaTwoStrConditions(String whereTgtOne, String whereTgtTwo, String operatorOne, String operatorTwo, String whereCondOne, String whereCondTwo){
        Reimbursement reimb = null;
        List<Reimbursement> twoStrReimbs = new ArrayList<>();
        try(Connection conn = ConnectUtil.getConnection()){
            String qry = "select reimb_id, reimb_amount, reimb_submitted, reimb_resolved,reimb_description, reimb_receipt, reimb_author, er.reimb_status_id, er.reimb_type_id, reimb_status, reimb_type from ers_reimbursement er inner join ers_reimbursement_status rs on er.reimb_status_id = rs.reimb_status_id inner join ers_reimbursement_type rt on er.reimb_type_id = rt.reimb_type_id inner join ers_users u on er.reimb_author = u.ers_users_id where "+whereCondOne+" "+operatorOne+" (?) and "+whereCondTwo+" "+operatorTwo+" (?)";
            System.out.println(qry);
            
            PreparedStatement ps = conn.prepareStatement(qry);
            ps.setString(1, whereTgtOne);
            ps.setString(2, whereTgtTwo);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                twoStrReimbs.add(reimb = new Reimbursement(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8),rs.getInt(9),rs.getInt(10), rs.getString(11), rs.getString(12)));
            }
            conn.close();
        }catch(SQLException e){
            logger.error("ReimburseDao - getReimbsViaTwoStrConditions : SQLException", e);
        }
        return twoStrReimbs;
    }
    // get reimbs via 1 timestap, 1 string conditions
    public List<Reimbursement> getReimbsViaTimeAndStrConditions(Timestamp whereTgtOne, String whereTgtTwo, String operatorOne, String operatorTwo, String whereCondOne, String whereCondTwo){
        Reimbursement reimb = null;
        List<Reimbursement> tsAndStrReimbs = new ArrayList<>();
        try(Connection conn = ConnectUtil.getConnection()){
            String qry = "select reimb_id, reimb_amount, reimb_submitted, reimb_resolved,reimb_description, reimb_receipt, reimb_author, er.reimb_status_id, er.reimb_type_id, reimb_status, reimb_type from ers_reimbursement er inner join ers_reimbursement_status rs on er.reimb_status_id = rs.reimb_status_id inner join ers_reimbursement_type rt on er.reimb_type_id = rt.reimb_type_id inner join ers_users u on er.reimb_author = u.ers_users_id where "+whereCondOne+" "+operatorOne+" (?) and "+whereCondTwo+" "+operatorTwo+" (?)";
            System.out.println(qry);
            
            PreparedStatement ps = conn.prepareStatement(qry);
            ps.setTimestamp(1, whereTgtOne);
            ps.setString(2, whereTgtTwo);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                tsAndStrReimbs.add(reimb = new Reimbursement(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8),rs.getInt(9),rs.getInt(10), rs.getString(11), rs.getString(12)));
            }
            conn.close();
        }catch(SQLException e){
            logger.error("ReimburseDao - getReimbsViaTwoStrConditions : SQLException", e);
        }
        return tsAndStrReimbs;
    }
    // get reimbs via NOW time, 1 string conditions
    public List<Reimbursement> getReimbsViaNowAndStrCondition(String whereTgt, String operatorOne, String operatorTwo, String whereCondOne, String whereCondTwo){
        Reimbursement reimb = null;
        List<Reimbursement> tsAndStrReimbs = new ArrayList<>();
        try(Connection conn = ConnectUtil.getConnection()){
            String qry = "select reimb_id, reimb_amount, reimb_submitted, reimb_resolved,reimb_description, reimb_receipt, reimb_author, reimb_resolver, er.reimb_status_id, er.reimb_type_id, reimb_status, reimb_type from ers_reimbursement er inner join ers_reimbursement_status rs on er.reimb_status_id = rs.reimb_status_id inner join ers_reimbursement_type rt on er.reimb_type_id = rt.reimb_type_id inner join ers_users u on er.reimb_author = u.ers_users_id where "+whereCondOne+" "+operatorOne+" now() and "+whereCondTwo+" "+operatorTwo+" (?)";
            System.out.println(qry);
            
            PreparedStatement ps = conn.prepareStatement(qry);
            ps.setString(1, whereTgt);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                tsAndStrReimbs.add(reimb = new Reimbursement(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8),rs.getInt(9),rs.getInt(10), rs.getString(11), rs.getString(12)));
            }
            conn.close();
        }catch(SQLException e){
            logger.error("ReimburseDao - getReimbsViaNowAndStrCondition : SQLException", e);
        }
        return tsAndStrReimbs;
    }
    // get reimbs via 2 int conditions
    public List<Reimbursement> getReimbsViaTwoIntConditions(Integer whereTgtOne, Integer whereTgtTwo, String operatorOne, String operatorTwo, String whereCondOne, String whereCondTwo){
        Reimbursement reimb = null;
        List<Reimbursement> twoIntReimbs = new ArrayList<>();
        try(Connection conn = ConnectUtil.getConnection()){
            String qry = "select reimb_id, reimb_amount, reimb_submitted, reimb_resolved,reimb_description, reimb_receipt, reimb_author, er.reimb_status_id, er.reimb_type_id, reimb_status, reimb_type from ers_reimbursement er inner join ers_reimbursement_status rs on er.reimb_status_id = rs.reimb_status_id inner join ers_reimbursement_type rt on er.reimb_type_id = rt.reimb_type_id inner join ers_users u on er.reimb_author = u.ers_users_id where "+whereCondOne+" "+operatorOne+" ? and "+whereCondTwo+" "+operatorTwo+" ?";
            
            PreparedStatement ps = conn.prepareStatement(qry);
            ps.setInt(1, whereTgtOne);
            ps.setInt(2, whereTgtTwo);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                twoIntReimbs.add(reimb = new Reimbursement(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8),rs.getInt(9),rs.getInt(10), rs.getString(11), rs.getString(12)));
            }
            conn.close();
        }catch(SQLException e){
            logger.error("ReimburseDao - getReimbsViaTwoIntConditions : SQLException", e);
        }
        return twoIntReimbs;
    }
    // get reimbs via 1 int & 1 str conditions
    public List<Reimbursement> getReimbsViaTwoMixedConditions(Integer whereTgtOne, String whereTgtTwo, String operatorOne, String operatorTwo, String whereCondOne, String whereCondTwo){
        Reimbursement reimb = null;
        List<Reimbursement> twoMixedCondReimbs = new ArrayList<>();
        try(Connection conn = ConnectUtil.getConnection()){
            String qry = "select reimb_id, reimb_amount, reimb_submitted, reimb_resolved,reimb_description, reimb_receipt, reimb_author, er.reimb_status_id, er.reimb_type_id, reimb_status, reimb_type from ers_reimbursement er inner join ers_reimbursement_status rs on er.reimb_status_id = rs.reimb_status_id inner join ers_reimbursement_type rt on er.reimb_type_id = rt.reimb_type_id inner join ers_users u on er.reimb_author = u.ers_users_id where "+whereCondOne+" "+operatorOne+" ? and "+whereCondTwo+" "+operatorTwo+" ?";
            
            PreparedStatement ps = conn.prepareStatement(qry);
            ps.setInt(1, whereTgtOne);
            ps.setString(2, whereTgtTwo);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                twoMixedCondReimbs.add(reimb = new Reimbursement(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8),rs.getInt(9),rs.getInt(10), rs.getString(11), rs.getString(12)));
            }
            conn.close();
        }catch(SQLException e){
            logger.error("ReimburseDao - getReimbsViaTwoMixedConditions : SQLException", e);
        }
        return twoMixedCondReimbs;
    }
    // get reimbs via 1 str condition
    public List<Reimbursement> getReimbsViaOneStrCondition(String whereTgt, String operator, String whereCond){
        Reimbursement reimb = null;
        List<Reimbursement> oneStrCondReimbs = new ArrayList<>();
        try(Connection conn = ConnectUtil.getConnection()){
            String qry = "select reimb_id, reimb_amount, reimb_submitted, reimb_resolved,reimb_description, reimb_receipt, reimb_author, er.reimb_status_id, er.reimb_type_id, reimb_status, reimb_type from ers_reimbursement er inner join ers_reimbursement_status rs on er.reimb_status_id = rs.reimb_status_id inner join ers_reimbursement_type rt on er.reimb_type_id = rt.reimb_type_id inner join ers_users u on er.reimb_author = u.ers_users_id where "+whereCond+" "+operator+" ?";
            
            PreparedStatement ps = conn.prepareStatement(qry);
            ps.setString(1, whereTgt);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                oneStrCondReimbs.add(reimb = new Reimbursement(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8),rs.getInt(9),rs.getInt(10), rs.getString(11), rs.getString(12)));
            }
            conn.close();
        }catch(SQLException e){
            logger.error("ReimburseDao - getReimbsViaOneStrCondition : SQLException", e);
        }
        return oneStrCondReimbs;
    }
    // get reimbs via 1 int condition
    public List<Reimbursement> getReimbsViaOneIntCondition(Integer whereTgt, String operator, String whereCond){
        Reimbursement reimb = null;
        List<Reimbursement> oneIntCondReimbs = new ArrayList<>();
        try(Connection conn = ConnectUtil.getConnection()){
            String qry = "select reimb_id, reimb_amount, reimb_submitted, reimb_resolved,reimb_description, reimb_receipt, reimb_author, er.reimb_status_id, er.reimb_type_id, reimb_status, reimb_type from ers_reimbursement er inner join ers_reimbursement_status rs on er.reimb_status_id = rs.reimb_status_id inner join ers_reimbursement_type rt on er.reimb_type_id = rt.reimb_type_id inner join ers_users u on er.reimb_author = u.ers_users_id where "+whereCond+" "+operator+" ?";
            
            PreparedStatement ps = conn.prepareStatement(qry);
            ps.setInt(1, whereTgt);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                oneIntCondReimbs.add(reimb = new Reimbursement(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8),rs.getInt(9),rs.getInt(10), rs.getString(11), rs.getString(12)));
            }
            conn.close();
        }catch(SQLException e){
            logger.error("ReimburseDao - getReimbsViaOneIntCondition : SQLException", e);
        }
        return oneIntCondReimbs;
    }

    /////// UPDATE
    // update request entry by ID for fields (resolved time, resolver, status_id)
    // resolverID will be stored upon manager login
    public void modifyRequestViaReimbID(Integer reimb_id, Integer status_id){
        try(Connection conn = ConnectUtil.getConnection()){
            String qry = "update ers_reimbursement set (reimb_resolved, reimb_resolver, reimb_status_id) = (now(),?,?) where reimb_id = ?";
            
            PreparedStatement ps = conn.prepareStatement(qry);
            // NOTE need to set one to Java gen "now" timestamp
            // NOTE need to change 2 to reflect stored manager ID
            //ps.setString(1, "");
            ps.setInt(1, SessionCtrlr.currentUserID);
            ps.setInt(2, status_id);
            ps.setInt(3, reimb_id);
            ps.executeUpdate();
            conn.close();
        }catch(SQLException e){
            logger.error("ReimburseDao - modifyRequestViaReimbID : SQLException", e);
        }
    }
}
