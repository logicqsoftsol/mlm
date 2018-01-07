package com.logicq.mlm.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AbstractDAO<T> {

	@Autowired
	private SessionFactory sessionFactory;
	
/**
 * 
 * @return
 */
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	
	/**
	 * 
	 * @param p_abstractentity
	 * @param p_session
	 */
	public void save(T p_abstractentity) {
             getCurrentSession().save(p_abstractentity);
	}

	public void saveList(List<T> abstractentitylist) {
		for (T abstarctentity : abstractentitylist) {
			getCurrentSession().save(abstarctentity);
		}
	}
	
	public void saveOrUpdateList(List<T> abstractentitylist) {
		for (T abstarctentity : abstractentitylist) {
			getCurrentSession().saveOrUpdate(abstarctentity);
		}
	}
	
	public void saveOrUpdate(T p_abstractentity) {
        getCurrentSession().saveOrUpdate(p_abstractentity);
    }

	
/**
 * 
 * @param p_abstractentity
 * @param p_session
 */
	public void update(T p_abstractentity)  {
	
		getCurrentSession().update(p_abstractentity);
	}
	



/**
 * 	
 * @param p_abstractentity
 * @param p_session
 */
	public void delete(T p_abstractentity) {
        if(null != p_abstractentity){
        	getCurrentSession().delete(p_abstractentity);
        }
	}
	

	
/**
 * 
 * @param query
 * @param paramMap
 * @return
 */
	public List<?> executeNamedQuery(String query, Map<String, Object> paramMap) {
		Query qry = getCurrentSession().getNamedQuery(query);
		bindParameters(qry, paramMap);
		return qry.list();
	}
/**
 * 
 * @param qry
 * @param paramMap
 */
	private void bindParameters(Query qry, Map<String, Object> paramMap) {

		for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
			if (entry.getValue() instanceof Collection) {
				qry.setParameterList(entry.getKey(), (Collection) entry.getValue());
			} else {
				qry.setParameter(entry.getKey(), entry.getValue());
			}
		}
	}
/**
 * 
 * @param clazz
 * @param id
 * @return
 */
	public Object load(Class<T> clazz, Serializable id) {
		Session session = getCurrentSession();
		return session.load(clazz, id);
	}
	
/**
 * 
 * @param clazz
 * @return
 */
	public List<?> loadClass(Class clazz) {

		Session session = getCurrentSession();
		Query qry = session.createQuery("from " + clazz.getName());
		return qry.list();
	}
/**
 * 
 * @param query
 * @param paramMap
 * @return
 */
	public Object executeNamedQueryForSingleRow(String query, Map<String, Object> paramMap) {
		Query qry = getCurrentSession().getNamedQuery(query);
		bindParameters(qry, paramMap);
		List list = qry.list();
		if (list != null && !list.isEmpty() && (list.size() > 0)) {
			return list.get(0);
		}
		return null;
	}
/**
 * 
 * 
 * @param clazz
 * @param params
 * @return
 */
	public List<?> executeCriteriaForSingleColumn(Class<T> clazz, Object[] params) {
		Criteria criteria = getCurrentSession().createCriteria(clazz);
		 for (Object columnName : params) {
			criteria.setProjection(Projections.property(String.valueOf(columnName)));
		} 
		return criteria.list();
	}
	
	public List<?> execcuteQuery(String queryString) {
		Query query = getCurrentSession().createQuery(queryString);
		return query.list();
	}
	
	public T execcuteQueryForUnique(String queryString) {
		Query query = getCurrentSession().createQuery(queryString);
		return (T) query.uniqueResult();
	}
	
	
	public List<?> execcuteSQLQuery(String queryString) {
		Query query = getCurrentSession().createSQLQuery(queryString);
		return query.list();
	}
	
	public List<T> executeQueryWithPagination(String queryString,int pagenumber,int pagesize) {
		Query query = getCurrentSession().createQuery(queryString);
		query.setFirstResult((pagenumber - 1) * pagesize);
		query.setMaxResults(pagesize);
		return query.list();
	}
	
	public List<T> executeQueryWithPaginationAndLimitation(String queryString, int limitValue) {
		Query query = getCurrentSession().createQuery(queryString);
		query.setMaxResults(limitValue);
		return query.list();
	}
	
	
	public List<T> loadClassWithPagination(Class claz,int pagenumber,int pagesize) {
		Query query = getCurrentSession().createQuery("from " + claz.getName());
		query.setFirstResult((pagenumber - 1) * pagesize);
		query.setMaxResults(pagesize);
		return query.list();
	}
	
}
