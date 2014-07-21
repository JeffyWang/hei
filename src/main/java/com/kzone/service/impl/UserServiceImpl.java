package com.kzone.service.impl;

import com.kzone.bean.User;
import com.kzone.constants.MongoConstants;
import com.kzone.constants.ParamsConstants;
import com.kzone.dao.UserDao;
import com.kzone.service.UserService;
import com.kzone.util.MD5Util;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jeffy on 14-4-24.
 */
@Service
public class UserServiceImpl extends CommonServiceImpl<User> implements UserService {
    Logger log = Logger.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;

    @Override
    public User getUser(int id) throws Exception {
        User user = userDao.get(id);

        return user;
    }

    @Override
    public User getUser(Map<String, Object> equalCondition) throws Exception {
        return userDao.get(equalCondition);
    }

    @Override
    public User getUser(String uuid) throws Exception {
        Map<String, Object> equalCondition = new HashMap<String, Object>();
        equalCondition.put(ParamsConstants.PARAM_UUID, uuid);
        User user = userDao.get(equalCondition);

        return user;
    }

    @Override
    public List<User> getUsers(Map<String, Object> equalCondition,  Map<String, String> likeCondition) throws Exception {
        List<User> users = userDao.getList(equalCondition, likeCondition);
        return users;
    }

    @Override
    public List<User> getUsersPage(int offset, int length,String orderDesc, Map<String, String> equalCondition, Map<String, String> likeCondition) throws Exception {
        List<User> users = userDao.getListForPage(User.class, offset, length,orderDesc, equalCondition, likeCondition);
        return users;
    }

    @Override
    public User addUser(User user) throws Exception {
        User u = userDao.insert(user);
        return u;
    }

    @Override
    public boolean isExist(String name) throws Exception {
        Map<String, Object> equalCondition = new HashMap<String, Object>();
        equalCondition.put(ParamsConstants.PARAM_USER_NAME, name);

        User user = getUser(equalCondition);
        if(user != null)
            return true;

        return false;
    }

    @Override
    public String encryption(String password) {
        String s = MD5Util.string2MD5(password);
        s = MD5Util.convertMD5(s);
        return s;
    }

    @Override
    public String decryption(String pssword) {
        String s = MD5Util.convertMD5(pssword);
        return s;
    }

    @Override
    public void validateUser(User user) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
