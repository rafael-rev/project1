package com.raf.services;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Mockito;

import com.raf.dao.ReimburseDaoImpl;
import com.raf.dao.UserDaoImpl;
import com.raf.models.Reimbursement;

public class ReimburseSvcTest {
    UserDaoImpl uDao = Mockito.mock(UserDaoImpl.class);
    ReimburseDaoImpl rDao = Mockito.mock(ReimburseDaoImpl.class);
    UserSvc uSvc = new UserSvc(uDao, rDao);
    ReimburseSvc rSvc = new ReimburseSvc(rDao);

    @Test
    public void testgetReimbByReimbID(){
        Integer reimb_id = 4;
        Reimbursement expectedReimb = new Reimbursement(4, 100.00, "2022-08-10 00:00:00.000", "2022-08-11 00:00:00.000", "emergency printer toner", "/resources/images/jlee11_4.png",	1,	5,	2,	4);

        Mockito.when(rDao.getOneReimbByID(reimb_id)).thenReturn(expectedReimb);
        Reimbursement actualResult = rSvc.getReimbByReimbID(reimb_id);

        assertEquals(expectedReimb, actualResult);
    }
}
