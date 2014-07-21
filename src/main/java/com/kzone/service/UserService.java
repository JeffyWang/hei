package com.kzone.service;

import com.kzone.bean.User;
import com.kzone.dao.UserDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Jeffy on 14-4-24.
 */
public interface UserService extends CommonService<User> {
    public User getUser(int id) throws Exception;

    public User getUser(Map<String, Object> equalCondition) throws Exception;

    public User getUser(String uuid) throws Exception;

    public List<User> getUsers(Map<String, Object> equalCondition,  Map<String, String> likeCondition) throws Exception;

    public List<User> getUsersPage(int offset, int length,String orderDesc, Map<String, String> equalCondition, Map<String, String> likeCondition) throws Exception ;

    public User addUser(User user) throws Exception;

    public boolean isExist(String name) throws Exception;

    public String encryption(String password);

    public String decryption(String pssword);

    public void validateUser(User user);
}
