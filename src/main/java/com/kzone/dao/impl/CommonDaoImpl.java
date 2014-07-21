package com.kzone.dao.impl;

import com.kzone.dao.CommonDao;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.*;

@Transactional
public class CommonDaoImpl<T> extends HibernateDaoSupport implements CommonDao<T> {
	
	private Logger log = Logger.getLogger(getClass());
	private Class<T> clz;
	
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public T insert(T t) throws Exception {
		try {
			Integer pk = (Integer)getHibernateTemplate().save(t);
			return get(pk);
		} catch (Exception e) {
			log.error("insert exception, insert object["+t+"]", e);
			throw e;
		}
	}
	
	public T update(T t) throws Exception {
		try {
			getHibernateTemplate().update(t);
			return t;
		} catch (Exception e) {
			log.error("update exception, update object["+t+"]", e);
			throw e;
		}
	}


	public boolean delete(T t) throws Exception {
		try {
			getHibernateTemplate().delete(getDAOClass().getName(), t);
		} catch (Exception e) {
			log.error("delete exception, delete object["+t+"]", e);
			throw e;
		}
		return true;
	}

	public boolean deleteById(int id) throws Exception {
		try {
			getHibernateTemplate().delete(getDAOClass().getName(), getById(id));
		} catch (Exception e) {
			log.error("delete by id exception, delete object id:["+id+"]", e);
			throw e;
		}
		return true;
	}

	public T get(int id)throws Exception{
		return getById(id);
	}
	
	public T get(final Map<String, Object> equalCondition)throws Exception{
		List<T> list = getListEqual(equalCondition);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	public T getById(int id)throws Exception {
		return getHibernateTemplate().get(getDAOClass(), id);
	}
	
	

	public List<T> getList()throws Exception {
		return getList(new HashMap<String, Object>(),
				new HashMap<String, String>());
	}

	public List<T> getListEqual(final Map<String, Object> equalCondition) throws Exception{
		// @SuppressWarnings("unchecked")
		List<T> list = getHibernateTemplate().execute(
				new HibernateCallback<List<T>>() {

					@SuppressWarnings("unchecked")
					public List<T> doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria crit = session.createCriteria(getDAOClass());
						if (equalCondition != null) {
							crit.add(Restrictions.allEq(equalCondition));
						}
						crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
						crit.addOrder(Order.desc("id"));
						List<T> page = crit.list();
						return page;
					}

				});
		return list;
	}

	public List<T> getListLike(final Map<String, String> likeCondition) throws Exception{
		// @SuppressWarnings("unchecked")
		List<T> list = getHibernateTemplate().execute(
				new HibernateCallback<List<T>>() {

					@SuppressWarnings("unchecked")
					public List<T> doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria crit = session.createCriteria(getDAOClass());
						if (likeCondition != null) {
							Set<String> keySet = likeCondition.keySet();
							Iterator<String> it = keySet.iterator();
							while (it.hasNext()) {
								String key = it.next();
								String value = likeCondition.get(key);
								crit.add(Restrictions.like(key, value,
										MatchMode.ANYWHERE));
							}
						}

						crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
						crit.addOrder(Order.desc("id"));
						List<T> page = crit.list();
						return page;
					}

				});
		return list;
	}

	public List<T> getList(final Map<String, Object> equalCondition,
			final Map<String, String> likeCondition) throws Exception{
		// @SuppressWarnings("unchecked")
		List<T> list = getHibernateTemplate().execute(
				new HibernateCallback<List<T>>() {

					@SuppressWarnings("unchecked")
					public List<T> doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria crit = session.createCriteria(getDAOClass());
						if (equalCondition != null) {
							crit.add(Restrictions.allEq(equalCondition));
						}

						if (likeCondition != null) {
							Set<String> keySet = likeCondition.keySet();
							Iterator<String> it = keySet.iterator();
							while (it.hasNext()) {
								String key = it.next();
								String value = likeCondition.get(key);
								crit.add(Restrictions.like(key, value,
										MatchMode.ANYWHERE));
							}
						}

						crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
						crit.addOrder(Order.desc("id"));
						List<T> page = crit.list();
						return page;
					}

				});
		return list;
	}

    public List<T> getListForPage(final Class<T> clazz, final int offset,
                                  final int length,final String orderDesc, final Map<String, String> equalCondition,
                                  final Map<String, String> likeCondition) {
        return getListForPage(clazz, offset, length,orderDesc, equalCondition,
                likeCondition, null);
    }

    public List<T> getListForPage(final Class<T> clazz, final int offset,
                                  final int length,final String orderDesc, final Map<String, String> equalCondition,
                                  final Map<String, String> likeCondition,
                                  final Map<String, String> gtCondition) {
        // @SuppressWarnings("unchecked")
        List<T> list = getHibernateTemplate().execute(
                new HibernateCallback<List<T>>() {

                    public List<T> doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        Criteria crit = session.createCriteria(clazz);
                        if (equalCondition != null) {
                            crit.add(Restrictions.allEq(equalCondition));
                        }
                        if (likeCondition != null) {
                            Set<String> keySet = likeCondition.keySet();
                            Iterator<String> it = keySet.iterator();
                            String startDate = "";
                            String endDate = "";
                            while (it.hasNext()) {
                                String key = it.next();
                                String value = likeCondition.get(key);
                                if (key.equals("startDate")) {
                                    startDate = value;
                                }
                                if (key.equals("endDate")) {
                                    endDate = value;
                                }
                                if (!endDate.equals("")
                                        && !startDate.equals("")) {
                                    crit.add(Restrictions.between(
                                            "createdatetime", startDate,
                                            endDate));
                                }
                                if (!key.equals("startDate")
                                        && !key.equals("endDate"))
                                    crit.add(Restrictions.like(key, value,
                                            MatchMode.ANYWHERE));

                            }
                        }
                        if (gtCondition != null) {
                            Set<String> keySet = gtCondition.keySet();
                            Iterator<String> it = keySet.iterator();
                            while (it.hasNext()) {
                                String key = it.next();
                                Object value = gtCondition.get(key);
                                crit.add(Restrictions.gt(key, value));
                            }
                        }
                        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                        crit.setFirstResult(offset);
                        if(orderDesc == null)
                            crit.addOrder(Order.desc("id"));
                        else
                            crit.addOrder(Order.desc(orderDesc));
                        crit.setMaxResults(length);
                        List<T> page = crit.list();
                        return page;
                    }

                });
        return list;
    }

    /**
     * only to retrive the special condtion that the same key and must be used
     * as 'or' retrive; e.g. select * from table where id = 1 or id =2
     *
     * @param clazz
     * @param offset
     * @param length
     * @param equalCondition
     *            List type,e.g.(<<id,1>,<id,2>>)
     * @param likeCondition
     * can use in date(between) ,e.g.(<startDate,2011-11-11 00:00:00> <endDate,2012-11-11 00:00:00>)
     * @return List
     */

    public List<T> getListForPage(final Class<T> clazz, final int offset,
                                  final int length,final String orderDesc, final List<Map<String, Object>> equalCondition,
                                  final Map<String, String> likeCondition) {
        // @SuppressWarnings("unchecked")
        List<T> list = getHibernateTemplate().execute(
                new HibernateCallback<List<T>>() {

                    public List<T> doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        Criteria crit = session.createCriteria(clazz);
                        if (equalCondition != null) {
                            if (1 == equalCondition.size())
                                crit.add(Restrictions.allEq(equalCondition
                                        .get(0)));
                            if (2 == equalCondition.size())
                                crit.add(Restrictions.or(Restrictions
                                        .allEq(equalCondition.get(0)),
                                        Restrictions.allEq(equalCondition
                                                .get(1))));
                        }
                        if (likeCondition != null) {
                            Set<String> keySet = likeCondition.keySet();
                            Iterator<String> it = keySet.iterator();
                            String startDate = "";
                            String endDate = "";
                            while (it.hasNext()) {
                                String key = it.next();
                                String value = likeCondition.get(key);
                                if (key.equals("startDate")) {
                                    startDate = value;
                                }
                                if (key.equals("endDate")) {
                                    endDate = value;
                                }
                                if (!endDate.equals("")
                                        && !startDate.equals("")) {
                                    crit.add(Restrictions.between(
                                            "createdatetime", startDate,
                                            endDate));
                                }
                                if (!key.equals("startDate")
                                        && !key.equals("endDate"))
                                    crit.add(Restrictions.like(key, value,
                                            MatchMode.ANYWHERE));

                            }

                        }
                        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                        crit.setFirstResult(offset);
                        if(orderDesc == null)
                            crit.addOrder(Order.desc("id"));
                        else
                            crit.addOrder(Order.desc(orderDesc));
                        crit.setMaxResults(length);
                        List<T> page = crit.list();
                        return page;
                    }

                });
        return list;
    }
	
	public long getListCount(final Map<String, String> equalCondition,
			final Map<String, String> likeCondition) throws Exception{
		Long count = getHibernateTemplate().execute(
				new HibernateCallback<Long>() {

					public Long doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria crit = session.createCriteria(getDAOClass());
						if (equalCondition != null) {
							crit.add(Restrictions.allEq(equalCondition));
						}
						if (likeCondition != null) {
							Set<String> keySet = likeCondition.keySet();
							Iterator<String> it = keySet.iterator();
							while (it.hasNext()) {
								String key = it.next();
								String value = likeCondition.get(key);
								if (value != null) {
									crit.add(Restrictions.like(key, value,
											MatchMode.ANYWHERE));
								}
							}
						}
						crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
						return new Long(crit.list().size());
					}

				});
		return count == null ? 0 : count.longValue();
	}

	public long getListCount(final Map<String, String> equalCondition,
			final Map<String, String> likeCondition,
			final Map<String, String> neCondition)throws Exception {
		Long count = getHibernateTemplate().execute(
				new HibernateCallback<Long>() {

					public Long doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria crit = session.createCriteria(getDAOClass());
						if (equalCondition != null) {
							crit.add(Restrictions.allEq(equalCondition));
						}
						if (likeCondition != null) {
							Set<String> keySet = likeCondition.keySet();
							Iterator<String> it = keySet.iterator();
							while (it.hasNext()) {
								String key = it.next();
								String value = likeCondition.get(key);
								if (value != null) {
									crit.add(Restrictions.like(key, value,
											MatchMode.ANYWHERE));
								}
							}
						}
						try {
							if (neCondition != null) {
								Set<String> keySet = neCondition.keySet();
								Iterator<String> it = keySet.iterator();
								while (it.hasNext()) {
									String key = it.next();
									Object value = neCondition.get(key);
									if (value != null) {
										if (getDAOClass().getDeclaredField(key)
												.getType().getName()
												.equals("int")) {
											crit.add(Restrictions.ne(key,
													Integer.parseInt(value
															.toString())));
										} else if (getDAOClass()
												.getDeclaredField(key)
												.getType().getName()
												.equals("double")) {
											crit.add(Restrictions.ne(key,
													Double.parseDouble(value
															.toString())));
										} else if (getDAOClass()
												.getDeclaredField(key)
												.getType().getName()
												.equals("float")) {
											crit.add(Restrictions.ne(key, Float
													.parseFloat(value
															.toString())));
										} else {
											crit.add(Restrictions
													.ne(key, value));
										}
									}
								}
							}
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						} catch (NoSuchFieldException e) {
							e.printStackTrace();
						}
						crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
						return new Long(crit.list().size());
					}

				});
		return count == null ? 0 : count.longValue();
	}

	public long isExist(final Map<String, String> equalCondition,
			final String id) throws Exception{
		Long count = getHibernateTemplate().execute(
				new HibernateCallback<Long>() {

					public Long doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria crit = session.createCriteria(getDAOClass());
						if (equalCondition != null) {
							crit.add(Restrictions.allEq(equalCondition));
						}

						crit.add(Restrictions.ne("id", new Integer(id)));
						crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
						return new Long(crit.list().size());
					}

				});
		return count == null ? 0 : count.longValue();
	}

	public long countByHQL(String hql)throws Exception {
		long totals = getHibernateTemplate().find(hql).size();
		return totals;
	}

	@SuppressWarnings("unchecked")
	public List<T> execute(String hql) throws Exception{
		return (List<T>) getHibernateTemplate().find(hql);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class<T> getDAOClass() {
		if(null == clz){
			Type genType = getClass().getGenericSuperclass();
			Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
			return (Class) params[0];
		}else{
			return clz;
		}

	}

	public void setDAOClass(Class<T> clz) {
		this.clz = clz;
	}

}