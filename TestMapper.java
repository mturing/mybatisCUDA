package com.assist.mapper;

import java.util.List;
import java.util.Map;

import com.assist.database.testBean.ParamBean;

public interface TestMapper {
	int add(ParamBean bean);
	
	int updateById(ParamBean bean);

	void deleteById(ParamBean pbean);

	<T> T findById(ParamBean pbean);
	
	List<Map> findTest();

	List<Map> findAll(ParamBean pbean);
}
