package com.kzone.dao.impl;

import com.kzone.bean.User;
import com.kzone.dao.UserDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Jeffy on 14-4-24.
 */
public class UserDaoImpl extends CommonDaoImpl<User> implements UserDao {
}
