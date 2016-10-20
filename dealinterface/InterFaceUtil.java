package com.assist.database.dealinterface;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.assist.database.ourexception.AssistDataBaseDoNotHaveColumnException;
import com.assist.database.ourexception.AssistDataBaseNotIsTableException;
import com.assist.database.ourinterface.Column;
import com.assist.database.ourinterface.Table;
import com.assist.databasea.commonUtil.CamelCaseUtils;
@Service
public class InterFaceUtil {
	private static Logger logger = LoggerFactory.getLogger(InterFaceUtil.class);
	
	/**
	 * 获取表名
	 * @param t
	 * @return
	 * @throws AssistDataBaseNotIsTableException
	 */
	public String getTableName(Class<?> t) throws AssistDataBaseNotIsTableException{
		String lastTableName="";
		if(t.isAnnotationPresent(Table.class)){
			Table table = t.getAnnotation(Table.class);
			String value = table.value();
			String taleName = table.tableName();
			if(!value.equals("")){
				lastTableName = value;
			}else if(!taleName.equals("")){
				lastTableName = taleName;
			}else{
				logger.debug("-----"+t.getName());
				
				lastTableName =CamelCaseUtils.toUnderlineName(t.getSimpleName());
			}
			
		}else{
			throw new AssistDataBaseNotIsTableException(t.getName()+"----没有用table注解---");
		}
		return lastTableName;
	}
	/**
	 * 获取数据库对应的字段名
	 * @param t
	 * @return
	 * @throws AssistDataBaseDoNotHaveColumnException
	 */
	public List<String> getTableColumnNames(Class<?> t) throws AssistDataBaseDoNotHaveColumnException{
		List<String> columnNames = new ArrayList<String>();
		
		Field[] fields = t.getDeclaredFields();
		for(Field field : fields){
			if(field.isAnnotationPresent(Column.class)){
				Column column = field.getAnnotation(Column.class);
				String value = column.value();
				String columnName = column.columnName();
				if(!value.equals("")){
					columnNames.add(value);
				}else if(!columnName.equals("")){
					columnNames.add(columnName);
				}else{
					String fildName=CamelCaseUtils.toUnderlineName(field.getName());
					columnNames.add(fildName);
				}
			}
		}
		if(columnNames.size()==0){
			throw new AssistDataBaseDoNotHaveColumnException(t.getName()+"----没有用column标识的数据库列");
		}
		return columnNames;
	}
	/**
	 * 获取数据库列明和对应的值
	 * @param object
	 * @return
	 * @throws AssistDataBaseDoNotHaveColumnException 
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public Map<String,Object> getTableColumnNamesAndValues(Object object) throws AssistDataBaseDoNotHaveColumnException, IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class t = object.getClass();
		Map<String,Object> columnNameValues = new HashMap<String, Object>();
		//List<String> columnNames = getTableColumnNames(t);
		Field[] fields = t.getDeclaredFields();
		for(Field fild : fields){
			if(fild.isAnnotationPresent(Column.class)){
				PropertyDescriptor pd = new PropertyDescriptor(fild.getName(), t);
				Method method = pd.getReadMethod();
				Object fieldValue = method.invoke(object);
				
				Column column = fild.getAnnotation(Column.class);
				String value = column.value();
				String columnName = column.columnName();
				
				if(!value.equals("")){
					columnNameValues.put(value, fieldValue);
				}else if(!columnName.equals("")){
					columnNameValues.put(columnName,fieldValue);
				}else{
					String fildName=CamelCaseUtils.toUnderlineName(fild.getName());
					columnNameValues.put(fildName,fieldValue);
				}
				//Class type = fild.getType();
			}
		}
		if(columnNameValues.size()==0){
			throw new AssistDataBaseDoNotHaveColumnException(t.getName()+"----没有用column标识的数据库列");
		}
		return columnNameValues;
	}
	public Map<String,String> getFieldNamesAndColumnName(Class t) throws AssistDataBaseDoNotHaveColumnException{
		Map<String,String> fieldColumnName = new HashMap<String, String>();
		Field[] fields = t.getDeclaredFields();
		for(Field field:fields){
			if(field.isAnnotationPresent(Column.class)){
				
				Column column = field.getAnnotation(Column.class);
				String value = column.value();
				String columnName = column.columnName();
				
				if(!value.equals("")){
					fieldColumnName.put(value, field.getName());
				}else if(!columnName.equals("")){
					fieldColumnName.put(columnName,field.getName());
				}else{
					String fildName=CamelCaseUtils.toUnderlineName(field.getName());
					fieldColumnName.put(fildName,field.getName());
				}
			}
		}
		if(fieldColumnName.size()==0){
			throw new AssistDataBaseDoNotHaveColumnException(t.getName()+"----没有用column标识的数据库列");
		}
		return fieldColumnName;
	}
}
