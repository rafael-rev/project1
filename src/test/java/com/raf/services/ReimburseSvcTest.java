package com.raf.services;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.raf.dao.ReimburseDaoImpl;
import com.raf.dao.UserDaoImpl;
import com.raf.models.Reimbursement;

public class ReimburseSvcTest {
    UserDaoImpl uDao = Mockito.mock(UserDaoImpl.class);
    ReimburseDaoImpl rDao = Mockito.mock(ReimburseDaoImpl.class);
    UserSvc uSvc = new UserSvc(uDao, rDao);

    @Test
    public void testViewAllReimbRequests() {
        
    }
}
