package dream.transaction;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServe {

    @Autowired
    private UserDao userDao;

    public void insertUser(){

        userDao.insert();

    }
}
