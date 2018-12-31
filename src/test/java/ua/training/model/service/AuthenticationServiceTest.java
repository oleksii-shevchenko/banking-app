package ua.training.model.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.model.dao.UserDao;
import ua.training.model.entity.User;
import ua.training.model.exception.WrongPasswordException;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest {
    @Mock
    private UserDao dao;


    @Test(expected = WrongPasswordException.class)
    public void givenWrongPasswordWhenAuthenticateUserThenThrowException() throws Exception {
        User user = User.getBuilder()
                .setLogin("test.login")
                .setPasswordHash(DigestUtils.sha256Hex("test.pass"))
                .build();
        when(dao.getUserByLogin("test.login")).thenReturn(user);

        new AuthenticationService(dao).authenticate("test.login", "test.pass.wrong");
    }
}
