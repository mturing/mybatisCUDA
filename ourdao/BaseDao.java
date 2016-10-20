package com.assist.database.ourdao;

import java.util.List;
import java.util.Map;

import com.assist.database.testBean.TangTestUser;

public interface BaseDao {
	/*public static Integer FIND_ALL=101;
	public static Integer FIND*/
	/**
	 * 添加数据到数据库
	 * @param bean
	 * @return 影响的条数
	 */
	<T> int add(T bean);
	
	/**
	 * 批量添加到数据库
	 * @param beans
	 * @return
	 */
	<T> int addList(List<T> beans);
	/**
	 * 根据id更新数据
	 * @param bean
	 * @return
	 */
	<T> int updateById(T bean,Integer id);
	
	/**
	 * 根据id删除数据
	 * @param bean
	 * @return
	 */
	int deleteById(Class t,Integer id);
	
	/**
	 * 根据id删除数据
	 * @param id
	 * @return
	 */
	int deleteById(Integer id);
	
	/**
	 * 根据id查询数据
	 * @param bean
	 * @return
	 */
	<T> T findById(T bean);
	
	/**
	 * 根据id查询数据
	 * @param id
	 * @return
	 */
	<T> T findById(Class<T> t,Integer id);
	/**
	 * 查询总的数目
	 * @param id
	 * @return
	 */
	<T> List<T> findAll(Class<T> t);
	/**
	 * 根据自己的条件查询
	 * @param id
	 * @param where key是字段名 value是字段值
	 * @return
	 */
	<T> List<T> findBySpWhere(Class<T> t, Map<String, Object> where);

}
