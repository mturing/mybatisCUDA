package com.assist.database.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

import com.assist.database.ourinterface.Column;
import com.assist.database.ourinterface.Table;

@Table
public class DateBaseUtil {
	@Column("nameedee") private String name="namev";
	@Column("test") private String test="testv";
	@Column private String hahh;
	@Column private String testName="testNamev";
	@Column private Integer ti=1;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	public String getHahh() {
		return hahh;
	}
	public void setHahh(String hahh) {
		this.hahh = hahh;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public Integer getTi() {
		return ti;
	}
	public void setTi(Integer ti) {
		this.ti = ti;
	}
	/**
	 * map转成实体类
	 * @param t 要转换的实体类的Class
	 * @param map
	 * @return T t
	 */
	public static <T> T mapToPojo(Class<T> t,Map<Object,Object> map){
		//T result = null;
		System.out.println("mapToPojo t name-->"+t.getName());
		T result = null;
		try {
			result = t.newInstance();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		Field[] fields = t.getDeclaredFields();
		for(Entry entry:map.entrySet()){
			System.out.println("mapToPojo---"+entry.getKey());
			System.out.println("mapToPojo---"+fields.length);
			for(Field field : fields){
				System.out.println("mapToPojo field---"+field.getName());
				if(field.getName().equals(entry.getKey())){
					System.out.println("mapToPojo---"+"进入设置");
					System.out.println("mapToPojo field---"+field.getName());
					System.out.println("mapToPojo getKey---"+entry.getKey());
					
					try {
						PropertyDescriptor pd = new PropertyDescriptor(field.getName(), t);
						Method method = pd.getWriteMethod();
						method.invoke(result, entry.getValue());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return result;
	}
	
}
