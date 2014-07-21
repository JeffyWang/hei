package com.kzone.util;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilderFactory;

import com.kzone.constants.CommonConstants;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class StringUtil {
    private static Logger log = Logger.getLogger(StringUtil.class);

    /**
     * string转xml
     */
    private static Document stringConvertXML(String data, String code) {

        StringBuilder sXML = new StringBuilder(code);
        sXML.append(data);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document doc = null;
        try {
            InputStream is = new ByteArrayInputStream(sXML.toString().getBytes(
                    "utf-8"));
            doc = dbf.newDocumentBuilder().parse(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }

    /**
     * 获取XML文件中某节点的所有值
     *
     * @param nodeName
     * @param xmlStr
     * @return
     */
    private static List<String> getXMLNodelistString(String nodeName,
                                                     String xmlStr) {
        Document doc = StringUtil.stringConvertXML(xmlStr, "");
        List<String> strList = new ArrayList<String>();
        NodeList list = doc.getElementsByTagName(nodeName);// 根据标签获得列表
        for (int i = 0; i < list.getLength(); i++) {
            Node nodeItem = list.item(i);
            strList.add(nodeItem.getTextContent());
        }
        return strList;
    }

    /**
     * 获取XML文件中某节点的第一个值
     *
     * @param nodeName
     * @param xmlStr
     * @return
     */
    private static String getXMLNodeString(String nodeName, String xmlStr) {
        Document doc = StringUtil.stringConvertXML(xmlStr, "");
        NodeList list = doc.getElementsByTagName(nodeName);// 根据标签获得列表
        Node nodeItem = list.item(0);
        return nodeItem.getTextContent();
    }
    /**
     * getProperty:获取属性的值. <br/>
     * TODO 适用于JavaBean对象.<br/>
     * TODO 注意可能的异常处理.<br/>
     *
     * @author Mike
     * @param obj
     *            JavaBean对象
     * @param name
     *            属性的名称
     * @return
     * @throws Exception
     * @since JDK 1.6
     */
    private static Object getProperty(Object obj, String name) throws Exception {

        PropertyDescriptor descriptor = new PropertyDescriptor(name,
                obj.getClass());
        /**
         *
         * 使用PropertyDescriptor的getReadMethod方法获得只读方法
         */
        Method methodGetX = descriptor.getReadMethod();
        Object resultValue = methodGetX.invoke(obj);
        return resultValue;

    }

    /**
     * setProperty:设置Bean对象的属性值. <br/>
     * TODO 适用于JavaBean对象.<br/>
     * TODO 注意可能的异常处理.<br/>
     *
     * @author Mike
     * @param obj
     *            JavaBean对象
     * @param name
     *            对象的属性
     * @param value
     *            对象属性的值
     * @throws Exception
     * @since JDK 1.6
     */
    private static void setProperty(Object obj, String name, Object value)
            throws Exception {

        PropertyDescriptor descriptor = new PropertyDescriptor(name,
                obj.getClass());
        /**
         *
         * 使用PropertyDescriptor的getWriteMethod方法获得只写方法
         */
        Method methodSetX = descriptor.getWriteMethod();
        methodSetX.invoke(obj, value);

    }


    /**
     * ObjectToXMLString:将JavaBean对象转为XML格式的String对象. <br/>
     * TODO 适用于JavaBean对象.<br/>
     *
     * @author Mike
     * @param obj
     * @return
     * @throws Exception
     * @since JDK 1.6
     */
    public static String objectToXMLString(Object obj) throws Exception {
        StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("<?xml version=\"1.0\"?>\n");
        strBuffer.append("<Parameters>\n");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            String propertyName = field.getName();
            Object resultValue = getProperty(obj, propertyName);
            strBuffer.append("\t<" + propertyName + ">" + resultValue + "</"
                    + propertyName + ">\n");
        }
        strBuffer.append("</Parameters>");
        return strBuffer.toString();
    }

    /**
     * XMLStringToObject:将XML格式的String转换为Object. <br/>
     * TODO 对象属性暂时只支持int、long、String类型.<br/>
     * TODO 注意异常处理.<br/>
     *
     * @author Mike
     * @param xmlStr
     * @param clazz
     * @return
     * @throws Exception
     * @since JDK 1.6
     */
    public static <T> T XMLStringToObject(String xmlStr, Class<T> clazz)
            throws Exception {
        T obj = clazz.newInstance();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            String propertyName = field.getName();
            Class<?> type = field.getType();
            String nodeText = getXMLNodeString(propertyName, xmlStr);
            if (type == int.class||type == Integer.class) {
                Integer value = Integer.parseInt(nodeText);
                setProperty(obj, propertyName, value);
            } else if (type == Double.class||type == double.class) {
                Double value = Double.parseDouble(nodeText);
                setProperty(obj, propertyName, value);
            } else if (type == long.class||type == Long.class) {
                Long value = Long.parseLong(nodeText);
                setProperty(obj, propertyName, value);
            } else {
                setProperty(obj, propertyName, nodeText);
            }

        }
        return obj;
    }

    /**
     * objectToJSONString:将实体JavaBean转换为JSON格式的String对象. <br/>
     *
     * @author Mike
     * @param obj
     * @return
     * @since JDK 1.6
     */
    public static String objectToJSONString(Object obj){
        JSONObject jsonObj = new JSONObject();
        jsonObj=JSONObject.fromObject(obj);
        return jsonObj.toString();
    }

    public static <T> JSONArray objectToJSONArray(List<T> list) {
        JSONArray jsonArray = JSONArray.fromObject(list);
        return jsonArray;
    }


    /**
     * jsonStringToObject:(这里用一句话描述这个方法的作用). <br/>
     * TODO 适用于对象的成员变量不包含集合等复杂类型的情形.<br/>
     * TODO 传入待处理的JSON格式的String变量和返回对象类型.<br/>
     * TODO 传入的String参数必须是JSON格式的.<br/>
     *
     * @author Mike
     * @param jsonStr
     * @param clazz 返回对象的类型
     * @return
     * @throws InstantiationException
     * @throws Exception
     * @since JDK 1.6
     */
    public static <T> T jsonStringToObject(String jsonStr, Class<T> clazz) throws InstantiationException, Exception{
        JSONObject in =  (JSONObject) JSONSerializer.toJSON(jsonStr);
        return (T)JSONObject.toBean(in, clazz);
    }

    /**
     * jsonStringToObject:将JSON格式String转换为JavaBean对象 . <br/>
     * TODO 当被转换对象成员变量中有复杂类型如ArrayList时.<br/>
     * TODO 在JSONObject.toBean的时候如果转换的JavaBean类中有集合变量,
     * 		可以先定义Map<String, Class> classMap = new HashMap<String, Class>();
     * 		在classMap中put你要转换的类中的集合名,像:classMap.put("distribute_api", DistributeAPI.class);
     * 		然后在toBean()的时候把参数加上,.<br/>
     * TODO 解决报错：MorphDynaBean cannot be cast to con.test的异常.<br/>
     * TODO 1.传入的String参数必须是JSON格式的；
     * 		2.classMap必须包含成员变量中所有的集合类型的每项的对象类型.<br/>
     *
     * @author Mike
     * @param jsonStr 待处理JSON格式的String对象
     * @param clazz 返回对象类型
     * @param classMap 集合的成员的对象类型
     * @return
     * @throws InstantiationException
     * @throws Exception
     * @since JDK 1.6
     */
    public static <T> T jsonStringToObject(String jsonStr, Class<T> clazz,Map<String, Class> classMap) throws InstantiationException, Exception{
        JSONObject in =  (JSONObject) JSONSerializer.toJSON(jsonStr);
        return (T)JSONObject.toBean(in, clazz, classMap);
    }

    /**
     * stringToJSON:将JSON格式的String转换成JSONObject对象. <br/>
     * TODO 将JSON格式的String转换成JSONObject对象.<br/>
     *
     * @author Mike
     * @param jsonStr
     * @return
     * @since JDK 1.6
     */
    public static JSONObject stringToJSON(String jsonStr){
        return (JSONObject) JSONSerializer.toJSON(jsonStr);
    }

    public static JSONArray stringToJSONArray(String jsonStr) {
        return JSONArray.fromObject(jsonStr);
    }

    /**
     * stringToJSONArray: 将JSON格式的String转换成JSONArray对象. <br/>
     *
     * @param jsonStr 传入的json字符串
     * @param clazz 传入的对象
     * @return
     */
    public static <T> ArrayList<T> stringToJSONArray(String jsonStr, Class<T> clazz) {
        JSONArray jsonArray=JSONArray.fromObject(jsonStr);
        ArrayList<T> tList = new ArrayList<T>();
        Collection jsonList = JSONArray.toCollection(jsonArray);
        if(jsonList!=null && !jsonList.isEmpty())
        {
            Iterator it=jsonList.iterator();
            while(it.hasNext())
            {
                JSONObject testJSONObj=JSONObject.fromObject(it.next());
                tList.add((T) JSONObject.toBean(testJSONObj,clazz));
            }
        }
        return tList;
    }

    /**
     * 传入验证头信息，获取参数map
     * @param str
     * @return
     */
    public static Map<String, String> getDigestHeader(String str) {
        StringTokenizer st = new StringTokenizer(str, ",");
        int count = st.countTokens();
        String s[] = new String[count];
        for(int i=0; i<(count); i++) {
            s[i] = st.nextToken();
        }
        for(int n = 0; n < count; n++) {
            s[n] = removeSpace(s[n]);
            s[n] = s[n].replaceAll("\"", "");
            s[n] = s[n].replaceAll("\'", "");
        }

        return getDigestMap(s);
    }

    public static Map<String, String> getDigestMap(String[] s) {
        Map<String, String> map = new HashMap<String, String>();
        for(int n = 0; n < s.length; n++) {
            //等号分割，分割成map
            StringTokenizer st = new StringTokenizer(s[n], "=");
            map.put(st.nextToken(), st.nextToken());
        }
        return map;
    }

    /**
     * 去除字符串开头处的空格
     * @param s
     * @return
     */
    public static String removeSpace(String s) {
        do{
            if(s.indexOf(" ") == 0) {
                s = s.replaceFirst(" ", "");
            }
        }while(s.indexOf(" ") == 0);

        return s;
    }

    /**
     * 获取ip和port（根据host=192.168.0.0:8888）
     * @param host
     * @return
     */
    public static Map<String, String> getIpAndPort(String host) {
        host = host.replace("/", "");
        Map<String, String> map = new HashMap<String, String>();
        StringTokenizer st = new StringTokenizer(host, ":");
        map.put("ip", st.nextToken());
        map.put("port", st.nextToken());
        return map;
    }

    /**
     * 获取cos的url列表，分发消息，共享内存中的网络质量数据
     * @param urls
     * @return
     */
    public static List<String> getUrlList(String urls) {
        List<String> urlList = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(urls, ";");
        int count = st.countTokens();
        for(int i=0; i<count; i++) {
            urlList.add(st.nextToken());
        }
        return urlList;
    }

    /**
     * 根据targetApi获取service_api
     * @param targetApi
     * @return
     */
    public static String getServiceApi(String targetApi) {
        StringTokenizer st = new StringTokenizer(targetApi, "/");
        int count = st.countTokens();
        String service = "";
        for(int i=0; i<2; i++) {
            st.nextToken();
        }
        for(int i=2; i<count; i++) {
            service = service + "/" + st.nextToken();
        }
        return service;
    }

    /**
     * 判断字符串是否为null或""
     * @param string
     * @return
     */
    public static boolean isEmpty(String string)
    {
        if(string == null || string.isEmpty())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 将一个json字符串解析为一个Map
     * @param jsonStr
     * @return
     */
    public static Map getJsonMap(String jsonStr){
        Map<String, Object> map = null;
        try{
            ObjectMapper mapper = new ObjectMapper();

            map = (Map<String, Object>) mapper.readValue(jsonStr, Map.class);
        }catch(Exception e){
            e.printStackTrace();
            log.error("解析以下JSON字符串出错：\""+jsonStr+"\"");
        }

        return map;
    }

    public static String stringToHtml(String title, String body) throws UnsupportedEncodingException {
        body = URLDecoder.decode(body, CommonConstants.ENCODE);
        StringBuffer sb = new StringBuffer("<html><head><title>");
        sb.append(title).append("</title></head><body>").append(body).append("</body></html>");

        return sb.toString();
    }
}