package com.kzone.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Jeffy on 14-5-17
 */
public interface CommonService<T> {
    public T add(T t)throws Exception;

    public T update(T t)throws Exception;

    public boolean delete(T t)throws Exception;

    public boolean deleteById(int id)throws Exception;

    public T get(int id)throws Exception;

    public T getById(int id)throws Exception;

    public T get(final Map<String, Object> equalCondition)throws Exception;

    public List<T> getList()throws Exception;

    public List<T> getListEqual(final Map<String, Object> equalCondition) throws Exception;

    public List<T> getListLike(final Map<String, String> likeCondition) throws Exception;

    public List<T> getList(final Map<String, Object> equalCondition,
                           final Map<String, String> likeCondition) throws Exception;

    public List<T> getListForPage(final Class<T> clazz, final int offset,
                                  final int length,final String orderDesc, final Map<String, String> equalCondition,
                                  final Map<String, String> likeCondition);

    public List<T> getListForPage(final Class<T> clazz, final int offset,
                                  final int length,final String orderDesc, final Map<String, String> equalCondition,
                                  final Map<String, String> likeCondition,
                                  final Map<String, String> gtCondition);

    public List<T> getListForPage(final Class<T> clazz, final int offset,
                                  final int length,final String orderDesc, final List<Map<String, Object>> equalCondition,
                                  final Map<String, String> likeCondition);

    long getListCount(final Map<String, String> equalCondition, final Map<String, String> likeCondition) throws Exception;

    public long getListCount(final Map<String, String> equalCondition,
                             final Map<String, String> likeCondition,
                             final Map<String, String> neCondition)throws Exception;

    long isExist(final Map<String, String> equalCondition, final String id)throws Exception;

    long countByHQL(String hql)throws Exception;

    public List<T> execute(String hql)throws Exception;
}
