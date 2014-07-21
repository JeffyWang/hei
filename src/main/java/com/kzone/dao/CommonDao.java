package com.kzone.dao;

import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Map;

public interface CommonDao<T> {
	
	public void setSuperSessionFactory(SessionFactory sessionFactory);
	
	public T insert(T t)throws Exception;
	
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

    //gtCondition 大于
    //GE是大于等于号(>=),GT是大于号(>),LE是小于等于号(<=),LT是小于号(<)
    public List<T> getListForPage(final Class<T> clazz, final int offset,
                                  final int length,final String orderDesc, final Map<String, String> equalCondition,
                                  final Map<String, String> likeCondition,
                                  final Map<String, String> gtCondition);

    public List<T> getListForPage(final Class<T> clazz, final int offset,
                                  final int length,final String orderDesc, final List<Map<String, Object>> equalCondition,
                                  final Map<String, String> likeCondition);

    long getListCount(final Map<String, String> equalCondition, final Map<String, String> likeCondition) throws Exception;

    //neCondition 不等于
	public long getListCount(final Map<String, String> equalCondition,
                             final Map<String, String> likeCondition,
                             final Map<String, String> neCondition)throws Exception;
	
	long isExist(final Map<String, String> equalCondition, final String id)throws Exception;
	
	long countByHQL(String hql)throws Exception;
	
	public List<T> execute(String hql)throws Exception;
	
	public Class<T> getDAOClass();

	public void setDAOClass(Class<T> clz);
}
