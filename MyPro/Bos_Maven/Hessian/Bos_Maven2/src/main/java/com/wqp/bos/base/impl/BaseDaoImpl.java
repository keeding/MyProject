package com.wqp.bos.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cache.spi.Region;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.wqp.bos.base.BaseDao;
import com.wqp.bos.page.PageBean;

public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {
	
	@Autowired
	public void setSessionFactry(SessionFactory sf){
		super.setSessionFactory(sf);
	}

	private Class clazz;
	
	public BaseDaoImpl(){
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class) type.getActualTypeArguments()[0];
	}
	
	@Override
	public void save(T t) {
		this.getHibernateTemplate().save(t);
	}

	@Override
	public void update(T t) {
		this.getHibernateTemplate().update(t);
	}

	@Override
	public void delete(T t) {
		this.getHibernateTemplate().delete(t);
	}

	@Override
	public void saveOrUpdate(T t) {
		this.getHibernateTemplate().saveOrUpdate(t);
	}

	@Override
	public T findById(Serializable id) {
		return (T) this.getHibernateTemplate().get(clazz, id);
	}

	@Override
	public List<T> findAll() {
		String hql = " from "+clazz.getName();
		return (List<T>) this.getHibernateTemplate().find(hql);
	}

	@Override
	public List<T> findAll(String condition, Object[] params) {
		String hql = " from "+clazz.getName()+" where  1=1"+condition;
		return (List<T>) this.getHibernateTemplate().find(hql, params);
	}

	@Override
	public int getTotalRecord(String condition, Object[] params) {
		String hql = "select count(*) from "+clazz.getName()+" where 1=1"+condition;
		List<Long> findAll = (List<Long>) this.getHibernateTemplate().find(hql, params);
		return findAll.get(0).intValue();
	}

	@Override
	public List<T> findAll(DetachedCriteria criteria) {
		return (List<T>) this.getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public List<T> findAll(DetachedCriteria criteria, int startIndex, int pageSize) {
		return (List<T>) this.getHibernateTemplate().findByCriteria(criteria, startIndex, pageSize);
	}

	@Override
	public void executeUpdate(String queryName, Object... objects){
		Session session = this.currentSession();
		Query query = session.getNamedQuery(queryName);
		int i = 0;
		for(Object arg : objects){
			query.setParameter(i++, arg);
		}
		query.executeUpdate();
	}

	@Override
	public void pageQuery(PageBean pb) {
		try {
			int currentPage = pb.getCurrentPage();
			int pageSize = pb.getPageSize();
			DetachedCriteria detachedCriteria = pb.getDetachedCriteria();
			//总数据量----select count(*) from bc_staff
			//改变Hibernate框架发出的sql形式
			detachedCriteria.setProjection(Projections.rowCount());//select count(*) from bc_staff
			List<Long> list = (List<Long>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
			Long total = list == null ? 0 : list.get(0);
			pb.setTotal(total.intValue());//设置总数据量
			detachedCriteria.setProjection(null);//修改sql的形式为select * from ....
			//重置表和类的映射关系
			detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
			//当前页展示的数据集合
			int firstResult = (currentPage - 1) * pageSize;
			int maxResults = pageSize;
			List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
			pb.setRows(rows);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
