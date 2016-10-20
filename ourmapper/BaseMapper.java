package com.assist.database.ourmapper;

import java.util.List;

import org.springframework.stereotype.Service;
@Service
public interface BaseMapper {
	
	/**
	 * 添加数据到数据库
	 * @param bean
	 * @return 影响的条数
	 */
	int add(String sql);
	
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
	<T> int updateById(T bean);
	
	/**
	 * 根据id删除数据
	 * @param bean
	 * @return
	 */
	<T> int deleteById(T bean);
	
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
	<T> T findById(Integer id);
	
}
