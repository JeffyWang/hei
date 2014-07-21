package com.kzone.service.impl;

import com.kzone.dao.CommonDao;
import com.kzone.service.CommonService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Jeffy on 14-5-17
 * eq相等   ne、neq不相等，   gt大于， lt小于 gte、ge大于等于   lte、le 小于等于   not非   mod求模
 */
@Service
public class CommonServiceImpl<T> implements CommonService<T> {
    @Autowired
    private CommonDao<T> commonDao;

    @Override
    public T add(T t) throws Exception {
        return commonDao.insert(t);
    }

    @Override
    public T update(T t) throws Exception {
        return commonDao.update(t);
    }

    @Override
    public boolean delete(T t) throws Exception {
        return commonDao.delete(t);
    }

    @Override
    public boolean deleteById(int id) throws Exception {
        return commonDao.deleteById(id);
    }

    @Override
    public T get(int id) throws Exception {
        return commonDao.get(id);
    }

    @Override
    public T getById(int id) throws Exception {
        return commonDao.getById(id);
    }

    @Override
    public T get(Map<String, Object> equalCondition) throws Exception {
        return commonDao.get(equalCondition);
    }

    @Override
    public List<T> getList() throws Exception {
        return commonDao.getList();
    }

    @Override
    public List<T> getListEqual(Map<String, Object> equalCondition) throws Exception {
        return commonDao.getListEqual(equalCondition);
    }

    @Override
    public List<T> getListLike(Map<String, String> likeCondition) throws Exception {
        return commonDao.getListLike(likeCondition);
    }

    @Override
    public List<T> getList(Map<String, Object> equalCondition, Map<String, String> likeCondition) throws Exception {
        return commonDao.getList(equalCondition, likeCondition);
    }

    @Override
    public List<T> getListForPage(Class<T> clazz, int offset, int length,String orderDesc, Map<String, String> equalCondition, Map<String, String> likeCondition) {
        return commonDao.getListForPage(clazz, offset, length,orderDesc, equalCondition, likeCondition);
    }

    @Override
    public List<T> getListForPage(Class<T> clazz, int offset, int length,String orderDesc, Map<String, String> equalCondition, Map<String, String> likeCondition, Map<String, String> gtCondition) {
        return commonDao.getListForPage(clazz, offset, length,orderDesc, equalCondition, likeCondition, gtCondition);
    }

    @Override
    public List<T> getListForPage(Class<T> clazz, int offset, int length,String orderDesc, List<Map<String, Object>> equalCondition, Map<String, String> likeCondition) {
        return commonDao.getListForPage(clazz, offset, length,orderDesc, equalCondition, likeCondition);
    }

    @Override
    public long getListCount(Map<String, String> equalCondition, Map<String, String> likeCondition) throws Exception {
        return commonDao.getListCount(equalCondition, likeCondition);
    }

    @Override
    public long getListCount(Map<String, String> equalCondition, Map<String, String> likeCondition, Map<String, String> neCondition) throws Exception {
        return commonDao.getListCount(equalCondition, likeCondition, neCondition);
    }

    @Override
    public long isExist(Map<String, String> equalCondition, String id) throws Exception {
        return commonDao.isExist(equalCondition, id);
    }

    @Override
    public long countByHQL(String hql) throws Exception {
        return commonDao.countByHQL(hql);
    }

    @Override
    public List<T> execute(String hql) throws Exception {
        return commonDao.execute(hql);
    }
}
