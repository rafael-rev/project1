package com.raf.services;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Mockito;
import com.raf.dao.ReimburseDaoImpl;
import com.raf.dao.UserDaoImpl;
import com.raf.models.User;

public class UserSvcTest {
    UserDaoImpl uDao = Mockito.mock(UserDaoImpl.class);
    ReimburseDaoImpl rDao = Mockito.mock(ReimburseDaoImpl.class);
    UserSvc uSvc = new UserSvc(uDao, rDao);

    @Test
    public void testGetFullNameByID() {
        String expectedName = "Terry Walker";
        Integer uid = 2;

        Mockito.when(uDao.getFullnameByUserID(uid)).thenReturn(expectedName);
        String actualResult = uSvc.getFullNameByID(uid);

        assertEquals(expectedName, actualResult);
    }

    @Test
    public void testGetUserByUsername() {
        User expectedUser = new User(2, "terrycup", "z123", "Terry", "Walker", "tcup12@openstar.net", 1);
        String username = "terrycup";

        Mockito.when(uDao.getUserByUsername(username)).thenReturn(expectedUser);
        User actualResult = uSvc.getUserByUsername(username);

        assertEquals(expectedUser, actualResult);
    }

    @Test
    public void testValidateCreds() {
        User expectedUser = new User(2, "terrycup", "z123", "Terry", "Walker", "tcup12@openstar.net", 1);
        String username = "terrycup";
        String password = "z123";
        User creds = new User(username, password, 1);
        Boolean expected = true;

        Mockito.when(uDao.getUserByUsername(username)).thenReturn(expectedUser);
        Boolean actualResult = uSvc.validateCreds(creds);

        assertEquals(expected, actualResult);
    }
}
