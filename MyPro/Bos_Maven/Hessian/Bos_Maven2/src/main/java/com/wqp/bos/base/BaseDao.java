package com.wqp.bos.base;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.wqp.bos.page.PageBean;

public interface BaseDao<T> {
	
	public void save(T t);
	public void update(T t);
	public void delete(T t);
	public void saveOrUpdate(T t);
	
	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	public T findById(java.io.Serializable id);
	/**
	 * 查询所有
	 * @return
	 */
	public List<T> findAll();
	
	/**
	 * 条件查询
	 * @param condition ，格式：" and ..? and ..."
	 * @param params
	 * @return
	 */
	public List<T> findAll(String condition, Object[] params);
	

	/**
	 * 分页，查询总记录数
	 * @param condition
	 * @param params
	 * @return
	 */
	public int getTotalRecord(String condition, Object[] params);

	/**
	 * 离线条件查询，使用QBC
	 * @param criteria
	 * @return
	 */
	public List<T> findAll(DetachedCriteria criteria);
	/**
	 * 分页
	 * @param criteria
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<T> findAll(DetachedCriteria criteria , int startIndex ,int pageSize);
	
	/**
	 * 命名查询
	 */
	void executeUpdate(String queryName, Object... objects);
	
	void pageQuery(PageBean pb);
}
