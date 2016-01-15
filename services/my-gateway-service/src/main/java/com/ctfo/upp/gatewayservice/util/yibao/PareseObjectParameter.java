package com.ctfo.upp.gatewayservice.util.yibao;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.lang.StringUtils;

public class PareseObjectParameter {
	/**
	 * 获取对象所有属性的内容，返回拼接字符串
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static String getObjectParameterContentString(Object object) throws Exception{
		String contents = "";
		List<Field> fields = new ArrayList<Field>();
		fields.addAll(Arrays.asList(object.getClass().getDeclaredFields()));
		for(Field f : fields){
			PropertyDescriptor pd = new PropertyDescriptor(f.getName(), object.getClass());  
            Method getMethod = pd.getReadMethod();
            Object o = getMethod.invoke(object);
            if(StringUtils.isNotBlank((String)o)){
            	contents += (String)o;
            }
		}
		return contents;
	}
	
	/**
	 * 获取对象所有属性的内容，返回拼接根据指定内容间隔的字符串，例如aaa&bbb&sss
	 * @param object
	 * @param ch 间隔字符
	 * @return
	 * @throws Exception
	 */
	public static String getObjectParameterContentStringByChar(Object object, String ch) throws Exception{
		String contents = "";
		List<Field> fields = new ArrayList<Field>();
		fields.addAll(Arrays.asList(object.getClass().getDeclaredFields()));
		for(int i = 0 ; i < fields.size() ; i++){
			Field f = fields.get(i);
			PropertyDescriptor pd = new PropertyDescriptor(f.getName(), object.getClass());  
            Method getMethod = pd.getReadMethod();
            Object o = getMethod.invoke(object);
            if(StringUtils.isNotBlank((String)o)){
            	contents += (String)o;
            	if(i != (fields.size() - 1) && fields.size() != 1){
            		contents += ch;
            	}
            }
		}
		return contents;
	}
	
	/**
	 * 获取对象所有属性的内容，返回拼接字符串，格式“属性=内容&属性=内容&属性=内容...”
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static String getParameterString(Object object) throws Exception{
		String contents = "";
		List<Field> fields = new ArrayList<Field>();
		fields.addAll(Arrays.asList(object.getClass().getDeclaredFields()));
		for(Field f : fields){
			PropertyDescriptor pd = new PropertyDescriptor(f.getName(), object.getClass());  
            Method getMethod = pd.getReadMethod();
            Object o = getMethod.invoke(object);
            if(o == null || ((String)o).equals("")){
            	contents += f.getName() + "=&";
            }else{
            	contents += f.getName() + "=" + (URLEncoder.encode((String)o, "GBK")) + "&";
            }
		}
		contents = contents.substring(0, contents.length() - 1);//去除掉最后的“&”
		return contents;
	}
	
	/**
	 * 将字符串内容转化为对应的对象，request替换成response
	 * @param content
	 * @param objectName
	 * @return
	 * @throws Exception
	 */
	public static Object pareseStringToObject(List content, String objectName) throws Exception{
		objectName = objectName.replace("Request", "Response");
		Class claz = Class.forName(objectName);
		Object object = claz.newInstance();
		for(int i = 0 ; i < content.size() ; i++){
			String[] param = content.get(i).toString().split("=", 2);
			if(param.length > 1){
				String key = param[0];
				String value = param[1];
				PropertyDescriptor pd = new PropertyDescriptor(key, object.getClass());  
	            Method writeMethod = pd.getWriteMethod();
	            writeMethod.invoke(object, value);
			}
		}
		return object;
	}
	
	/**
	 * 将字符串内容转化为对应的对象
	 * @param content
	 * @param objectName
	 * @return
	 * @throws Exception
	 */
	public static Object pareseStringToObject2(Map<String, String> content, String objectName) throws Exception{
		Class claz = Class.forName(objectName);
		Object object = claz.newInstance();
		Set<String> s = content.keySet();
		Iterator<String> it = s.iterator();
		while(it.hasNext()){
			String key = it.next();
			String value = content.get(key);
			PropertyDescriptor pd = new PropertyDescriptor(key, object.getClass());  
            Method writeMethod = pd.getWriteMethod();
            writeMethod.invoke(object, value);
		}
		return object;
	}
	/**
	 * 委托结算组装xml
	 */
	public static String convertObjectToXML(Object object) throws Exception {
		JAXBContext context = JAXBContext.newInstance(object.getClass());
		XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
		Marshaller jaxbMarshaller = context.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");
        jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(baos, (String) jaxbMarshaller.getProperty(Marshaller.JAXB_ENCODING));
        xmlStreamWriter.writeStartDocument((String) jaxbMarshaller.getProperty(Marshaller.JAXB_ENCODING), "1.0");
        jaxbMarshaller.marshal(object, xmlStreamWriter);
        xmlStreamWriter.writeEndDocument();
        xmlStreamWriter.close();
        return new String(baos.toByteArray());
	}
	
	public static void convertXMLToObject(String xml, Object object) throws Exception {
		JAXBContext context = JAXBContext.newInstance(object.getClass());
        Unmarshaller unmarshaller = context.createUnmarshaller();
        object = unmarshaller.unmarshal(new StringReader(xml));
	}
}
