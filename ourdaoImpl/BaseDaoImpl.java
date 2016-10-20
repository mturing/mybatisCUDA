package com.assist.database.ourdaoImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assist.database.dealinterface.InterFaceUtil;
import com.assist.database.ourdao.BaseDao;
import com.assist.database.testBean.ParamBean;
import com.assist.database.util.DateBaseUtil;
import com.assist.mapper.TestMapper;

@Service
public class BaseDaoImpl implements BaseDao {
	@Autowired
	private InterFaceUtil ifUtil;
	@Autowired
	private TestMapper mapper;

	@Override
	public <T> int add(T bean) {
		String sqlBe = "insert into ";
		String sqlLa = " value (";
		String sql = "";
		Integer totla = 0;
		try {
			String tableName = ifUtil.getTableName(bean.getClass());
			Map<String, Object> columnValues = ifUtil
					.getTableColumnNamesAndValues(bean);
			sqlBe = sqlBe + tableName + "(";
			int index = 0;
			for (Entry<String, Object> entry : columnValues.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = "
						+ entry.getValue());
				index++;

				if (index == columnValues.size()) {
					sqlBe = sqlBe + entry.getKey() + ")";// 最后一条数据
					Object value = entry.getValue();
					if (value instanceof String) {
						sqlLa = sqlLa + "'" + value + "'" + ")";
					} else {
						sqlLa = sqlLa + value + ")";
					}
				} else {
					sqlBe = sqlBe + entry.getKey() + ",";
					Object value = entry.getValue();
					if (value instanceof String) {
						sqlLa = sqlLa + "'" + value + "'" + ",";
					} else {
						sqlLa = sqlLa + value + ",";
					}
				}

			}
			sql = sqlBe + sqlLa;
			System.out.println("最后得到的sql---->" + sql);
			ParamBean pbean = new ParamBean();
			pbean.setSql(sql);
			totla = mapper.add(pbean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return totla;
	}

	@Override
	public <T> int addList(List<T> beans) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T> int updateById(T bean, Integer id) {
		String sqlbe = "update ";
		int len = 0;
		try {
			int index = 0;
			String tableName = ifUtil.getTableName(bean.getClass());
			Map<String, Object> columnValues = ifUtil
					.getTableColumnNamesAndValues(bean);

			sqlbe = sqlbe + tableName + " set ";
			for (Entry<String, Object> entry : columnValues.entrySet()) {
				System.out.println("---update获取的值---");
				System.out.println("Key = " + entry.getKey() + ", Value = "
						+ entry.getValue());
				index++;
				Object value = entry.getValue();
				// sqlbe = sqlbe+entry.getKey()+" = "+entry.getValue();
				if (index == columnValues.size()) {
					// sqlbe = sqlbe + " where id=" + id;
					if (value != null) {

						if (value instanceof String) {
							sqlbe = sqlbe + entry.getKey() + " = " + "'"
									+ entry.getValue() + "'";
						} else {
							sqlbe = sqlbe + entry.getKey() + " = "
									+ entry.getValue() + " ";
						}

					}
					sqlbe = sqlbe + " where id=" + id;
				} else {
					if (value != null) {
						if (value instanceof String) {
							sqlbe = sqlbe + entry.getKey() + " = " + "'"
									+ entry.getValue() + "' ,";
						} else {
							sqlbe = sqlbe + entry.getKey() + " = "
									+ entry.getValue() + " ,";
						}
					}

				}
			}
			System.out.println("最后得到的sql---->" + sqlbe);
			ParamBean pbean = new ParamBean();
			pbean.setSql(sqlbe);
			len = mapper.updateById(pbean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return len;
	}

	@Override
	public int deleteById(Class t, Integer id) {
		String sqlbe = "delete from ";
		try {
			String tableName = ifUtil.getTableName(t);
			sqlbe = sqlbe + tableName + " where id=" + id;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ParamBean pbean = new ParamBean();
		pbean.setSql(sqlbe);
		mapper.deleteById(pbean);
		return 0;
	}

	@Override
	public <T> T findById(T bean) {
		// TODO Auto-generated method stub
		return null;
	}

	private <T> String findSql(Class<T> t, Integer id, Map<String, Object> where) {
		Map resultData = null;
		T rBean = null;
		System.out.println("findById name----->" + t.getName());
		String sqlbe = "select ";
		String sqlst = " ";
		try {
			String tableName = ifUtil.getTableName(t);
			Map<String, String> fieldNameAndColumns = ifUtil
					.getFieldNamesAndColumnName(t);
			int i = 0;
			for (Entry<String, String> entry : fieldNameAndColumns.entrySet()) {
				i++;
				if (i != fieldNameAndColumns.size()) {
					sqlbe = sqlbe + entry.getKey() + " AS " + entry.getValue()
							+ " , ";
				} else {
					sqlbe = sqlbe + entry.getKey() + " AS " + entry.getValue();
				}
			}
			if (id == -1) {
				sqlbe = sqlbe + " from " + tableName;
			} else if (id == -2) {
				sqlbe = sqlbe + " from " + tableName;
				String key = "";
				Object value = null;
				int index = 0;
				sqlbe=sqlbe+" where ";
				for (Entry<String, Object> entry : where.entrySet()) {
					index++;
					key = entry.getKey();
					value = entry.getValue();
					if (index != where.size()) {
						if (value instanceof String) {
							sqlst = sqlst + key + " ='" + value + "' and ";
						} else {
							sqlst = sqlst + key + "=" + value + " and ";
						}

					} else {
						if (value instanceof String) {
							sqlst = sqlst + key + " ='" + value + "'";
						} else {
							sqlst = sqlst + key + "=" + value + " ";
						}
					}
				}
				sqlbe = sqlbe+sqlst;
			} else {
				sqlbe = sqlbe + " from " + tableName + " where id=" + id;
			}
			/*
			 * ParamBean pbean = new ParamBean(); pbean.setSql(sqlbe);
			 * pbean.setResultType(t.getName()); resultData=
			 * mapper.findById(pbean); rBean = (T) DateBaseUtil.mapToPojo(t,
			 * resultData);
			 */

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sqlbe;
	}

	private <T> String findSql(Class<T> t, Integer id) {
		return findSql(t, id, null);
	}

	@Override
	public <T> T findById(Class<T> t, Integer id) {
		Map resultData = null;
		T rBean = null;

		String sql = findSql(t, id);
		ParamBean pbean = new ParamBean();
		pbean.setSql(sql);
		resultData = mapper.findById(pbean);
		rBean = (T) DateBaseUtil.mapToPojo(t, resultData);
		return rBean;
	}

	@Override
	public <T> List<T> findAll(Class<T> t) {
		List<T> lastReData = new ArrayList<T>();
		String sql = findSql(t, -1);
		ParamBean pbean = new ParamBean();
		pbean.setSql(sql);
		List<Map> resDates = mapper.findAll(pbean);
		for (Map ma : resDates) {
			lastReData.add((T) DateBaseUtil.mapToPojo(t, ma));
		}
		return lastReData;
	}
	@Override
	public <T> List<T> findBySpWhere(Class<T> t, Map<String, Object> where) {
		List<T> lastReData = new ArrayList<T>();
		String sql = findSql(t, -2, where);
		System.out.println("findBySpWhere---->last sql-->"+sql);
		ParamBean bean = new ParamBean();
		bean.setSql(sql);
		List<Map> resDates = mapper.findAll(bean);
		for (Map ma : resDates) {
			lastReData.add((T) DateBaseUtil.mapToPojo(t, ma));
		}
		return lastReData;
	}
	public <T> List<T> findLeftJoin(){
		return null;
	}
	@Override
	public int deleteById(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
