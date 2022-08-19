package com.raf.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.raf.models.Reimbursement;
import com.raf.utils.ConnectUtil;


public class ReimburseDaoImpl implements ReimburseDao{
    Logger logger = LogManager.getLogger(UserDaoImpl.class);
    ConnectUtil conn = new ConnectUtil();
    
    /////// CREATE
    public void createNewRequestFromReimbObj(Reimbursement reimb_obj){
        try(Connection conn = ConnectUtil.getConnection()){
            String qry = "insert into ers_reimbursement (reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id) values (?,?,now(),null,?,?,?,null,?,?)";
            PreparedStatement ps = conn.prepareStatement(qry);
            ps.setInt(1,reimb_obj.getId());
            ps.setDouble(2, reimb_obj.getAmount());
            //ps.setString(3,reimb_obj.getTime_submitted());
            //ps.setString(4, reimb_obj.getTime_resolved());
            ps.setString(5,reimb_obj.getDescription());
            ps.setString(6, reimb_obj.getReceipt());
            ps.setInt(7, reimb_obj.getAuthor());
            //ps.setInt(8, reimb_obj.getResolver());
            ps.setInt(9, reimb_obj.getStatus_id());
            ps.setInt(10, reimb_obj.getType_id());
            ps.executeUpdate();
            conn.close();
        }catch(SQLException e){
            logger.error("ReimburseDao - createNewRequestFromReimbObj : SQLException", e);
        }
    }

    /////// RETRIEVE
    // get list of reimbursement objects
    public List<Reimbursement> getListOfAllReimbObjs(){
        Reimbursement reimb = null;
        List<Reimbursement> allReimbs = new ArrayList<>();
        try(Connection conn = ConnectUtil.getConnection()){
            String qry = "select * from ers_reimbursement";
            PreparedStatement ps = conn.prepareStatement(qry);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                allReimbs.add(reimb = new Reimbursement(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8),rs.getInt(9),rs.getInt(10)));
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
                statusReimbs.add(reimb = new Reimbursement(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8),rs.getInt(9),rs.getInt(10)));
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
                authorReimbs.add(reimb = new Reimbursement(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8),rs.getInt(9),rs.getInt(10)));
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
                resolverReimbs.add(reimb = new Reimbursement(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8),rs.getInt(9),rs.getInt(10)));
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
            String qry = "select * from ers_reimbursement r inner join ers_users u on r.reimb_author = u.ers_users_id where u.ers_username = ?";
            
            PreparedStatement ps = conn.prepareStatement(qry);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                resolverReimbs.add(reimb = new Reimbursement(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8),rs.getInt(9),rs.getInt(10)));
            }
            conn.close();
        }catch(SQLException e){
            logger.error("ReimburseDao - getAllReimbsViaUsername : SQLException", e);
        }
        return resolverReimbs;
    }
    // get reimbs via 2 string conditions
    public List<Reimbursement> getReimbsViaTwoStrConditions(String whereTgtOne, String whereTgtTwo, String whereCondOne, String whereCondTwo){
        Reimbursement reimb = null;
        List<Reimbursement> twoStrReimbs = new ArrayList<>();
        try(Connection conn = ConnectUtil.getConnection()){
            String qry = "select * from ers_reimbursement r inner join ers_users u on r.reimb_author = u.ers_users_id where u."+whereCondOne+" = ? and "+whereCondTwo+" = ?";
            
            PreparedStatement ps = conn.prepareStatement(qry);
            ps.setString(1, whereTgtOne);
            ps.setString(2, whereTgtTwo);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                twoStrReimbs.add(reimb = new Reimbursement(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8),rs.getInt(9),rs.getInt(10)));
            }
            conn.close();
        }catch(SQLException e){
            logger.error("ReimburseDao - getReimbsViaTwoStrConditions : SQLException", e);
        }
        return twoStrReimbs;
    }
    // get reimbs via 2 int conditions
    public List<Reimbursement> getReimbsViaTwoIntConditions(Integer whereTgtOne, Integer whereTgtTwo, String whereCondOne, String whereCondTwo){
        Reimbursement reimb = null;
        List<Reimbursement> twoIntReimbs = new ArrayList<>();
        try(Connection conn = ConnectUtil.getConnection()){
            String qry = "select * from ers_reimbursement r inner join ers_users u on r.reimb_author = u.ers_users_id where u."+whereCondOne+" = ? and "+whereCondTwo+" = ?";
            
            PreparedStatement ps = conn.prepareStatement(qry);
            ps.setInt(1, whereTgtOne);
            ps.setInt(2, whereTgtTwo);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                twoIntReimbs.add(reimb = new Reimbursement(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8),rs.getInt(9),rs.getInt(10)));
            }
            conn.close();
        }catch(SQLException e){
            logger.error("ReimburseDao - getReimbsViaTwoIntConditions : SQLException", e);
        }
        return twoIntReimbs;
    }
    // get reimbs via 1 int & 1 str conditions
    public List<Reimbursement> getReimbsViaTwoMixedConditions(Integer whereTgtOne, String whereTgtTwo, String whereCondOne, String whereCondTwo){
        Reimbursement reimb = null;
        List<Reimbursement> twoMixedCondReimbs = new ArrayList<>();
        try(Connection conn = ConnectUtil.getConnection()){
            String qry = "select * from ers_reimbursement r inner join ers_users u on r.reimb_author = u.ers_users_id where u."+whereCondOne+" = ? and "+whereCondTwo+" = ?";
            
            PreparedStatement ps = conn.prepareStatement(qry);
            ps.setInt(1, whereTgtOne);
            ps.setString(2, whereTgtTwo);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                twoMixedCondReimbs.add(reimb = new Reimbursement(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8),rs.getInt(9),rs.getInt(10)));
            }
            conn.close();
        }catch(SQLException e){
            logger.error("ReimburseDao - getReimbsViaTwoMixedConditions : SQLException", e);
        }
        return twoMixedCondReimbs;
    }

    /////// UPDATE
    // update request entry by ID for fields (resolved time, resolver, status_id)
    // resolverID will be stored upon manager login
    public void modifyRequestViaReimbID(Integer reimb_id, Integer status_id){
        try(Connection conn = ConnectUtil.getConnection()){
            String qry = "update ers_reimbursement set (reimb_resolved, reimb_resolver, reimb_status_id) = (?,?,?)";
            
            PreparedStatement ps = conn.prepareStatement(qry);
            // NOTE need to set one to Java gen "now" timestamp
            // NOTE need to change 2 to reflect stored manager ID
            ps.setString(1, "");
            ps.setInt(2, 0);
            ps.setInt(3, status_id);
            ps.executeUpdate();
            conn.close();
        }catch(SQLException e){
            logger.error("ReimburseDao - modifyRequestViaReimbID : SQLException", e);
        }
    }
}
