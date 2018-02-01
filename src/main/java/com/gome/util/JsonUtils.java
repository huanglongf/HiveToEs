package com.gome.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtils {

	public static boolean isBadJson(String json) {
		return !isGoodJson(json);
	}

	public static boolean isGoodJson(String json) {
		if (StringUtils.isBlank(json)) {
			return false;
		}
		try {
			new JsonParser().parse(json);
			return true;
		} catch (JsonParseException e) {
			return false;
		}
	}

	public static String addAttribute(String source, String param) {
		if (StringUtils.isBlank(param))
			return source;

		JSONObject jsonObject = null;
		if (StringUtils.isBlank(source)) {
			jsonObject = new JSONObject();
		} else {
			jsonObject = JSONObject.fromObject(source);
		}

		String[] paramArray = param.split("&");
		for (String paramValue : paramArray) {
			String temp[] = paramValue.split("=");
			if (temp == null || temp.length != 2)
				continue;
			if (StringUtils.equalsIgnoreCase(temp[0], "sn"))
				continue;
			if (StringUtils.isBlank(temp[1]))
				continue;
			jsonObject.put(temp[0], temp[1]);
		}
		return jsonObject.toString();
	}

	/**
	 * 
	 * @Title: addAttribute @Description: TODO(给原有的json中增加属性) @param @param
	 *         soruce @param @param param @param @return 设定文件 @return String
	 *         返回类型 @throws
	 */
	public static String replaceAttribute(String source, String paramKey, String paramValue) {
		if (StringUtils.isBlank(paramValue))
			return source;

		JSONObject jsonObject = JSONObject.fromObject(source);

		jsonObject.put(paramKey, paramValue);
		return jsonObject.toString();
	}

	public static String getCookieJsonMap(String cookie) {
		if (StringUtils.isNumeric(cookie))
			return cookie;

		List<String> cookieList = Arrays.asList(cookie.split(";"));
		Map<String, String> resultMap = new HashMap<String, String>();
		for (String keyValue : cookieList) {
			resultMap.put(StringUtils.substringBefore(keyValue, "=").trim(),
					StringUtils.substringAfter(keyValue, "=").trim());
		}

		return hashMap2Json(resultMap);
	}

	/**
	 * 将java对象转化为json
	 * 
	 * @param object
	 *            要转化的Java对象
	 * @throws IOException
	 */
	public static String object2Json(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		JsonGenerator jsonGenerator = new JsonFactory().createGenerator(sw);
		mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true); // 允许出现单引号
		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);// 允许出现特殊字符和转义符
		mapper.configure(Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true); // 反斜杠
		mapper.writeValue(jsonGenerator, object);
		jsonGenerator.close();
		return sw.toString();
	}

	/**
	 * 将java对象转化为jsonArray
	 * 
	 * @param object
	 *            要转化的Java对象
	 * @throws IOException
	 */
	public static Object object2JsonArray(Object object) throws Exception {
		JSONArray array = JSONArray.fromObject(object);
		return array;
	}

	/**
	 * 将json数据转为List<?>
	 * 
	 * @param json
	 *            json字符串
	 * @param clazz
	 *            需要将json转化为的对象
	 * @return
	 * @throws Exception
	 */
	public static List<?> json2List(String json, Class<?> clazz) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, clazz);
		mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true); // 允许出现单引号
		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);// 允许出现特殊字符和转义符
		mapper.configure(Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true); // 反斜杠
		return mapper.readValue(json, javaType);
	}

	/**
	 * 将json数据转为List<?>
	 * 
	 * @param json
	 *            json字符串
	 * @param clazz
	 *            需要将json转化为的对象
	 * @return
	 * @throws Exception
	 */
	public static Object json2Object(String json, Class<?> clazz) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true); // 允许出现单引号
		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);// 允许出现特殊字符和转义符
		mapper.configure(Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true); // 反斜杠
		return mapper.readValue(json, clazz);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> json2Map(String json) throws Exception {
		synchronized (JsonUtils.class) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true); // 允许出现单引号
			mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);// 允许出现特殊字符和转义符
			mapper.configure(Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true); // 反斜杠
			return mapper.readValue(json, Map.class);
		}
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> json2MapObject(String json) throws Exception {
		synchronized (JsonUtils.class) {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true); // 允许出现单引号
			mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);// 允许出现特殊字符和转义符
			mapper.configure(Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true); // 反斜杠
			return mapper.readValue(json, Map.class);
		}
	}

	/**
	 * 将json格式的字符串解析成Map对象
	 * <li>json格式：{"name":"admin","retries":"3fff","testname"
	 * :"ddd","testretries":"fffffffff"}
	 */
	public static HashMap<String, String> json2HashMap(Object object) {
		HashMap<String, String> data = new HashMap<String, String>();
		// 将json字符串转换成jsonObject
		JSONObject jsonObject = JSONObject.fromObject(object);
		Iterator it = jsonObject.keys();
		// 遍历jsonObject数据，添加到Map对象
		while (it.hasNext()) {
			String key = String.valueOf(it.next());
			if (key != null && key.length() > 0 && jsonObject.get(key) != null
					&& jsonObject.get(key).toString().length() > 0) {
				String value = jsonObject.get(key).toString();
				if (key != null && key.length() > 0) {
					data.put(key, value);
				} else {
					data.put(key, "");
				}
			}
		}
		return data;
	}

	/**
	 * 将json格式的字符串解析成Map对象
	 * <li>json格式：{"name":"admin","retries":"3fff","testname"
	 * :"ddd","testretries":"fffffffff"}
	 */
	public static String hashMap2Json(Map<String, String> map) {
		StringBuffer resultString = new StringBuffer();
		resultString.append("{");
		int i = 0;
		for (String key : map.keySet()) {
			resultString.append("\"" + key + "\":\"" + map.get(key) + "\"");
			i++;
			if (i < map.keySet().size()) {
				resultString.append(",");
			}
		}
		resultString.append("}");
		return resultString.toString();
	}

	public static List<Map<String, String>> json2ListHashMap(String jsonArrayString) {
		/*
		 * List<Map<String, String>> dataList = new ArrayList<Map<String,
		 * String>>(); String tmp=jsonArrayString.replace("[", "");
		 * tmp=tmp.replace("]", ""); tmp=tmp.replace("},{", "}@@{"); String[]
		 * jsonArray=tmp.split("@@"); for (String object : jsonArray) {
		 * HashMap<String, String> data = new HashMap<String, String>(); //
		 * 将json字符串转换成jsonObject JSONObject jsonObject =
		 * JSONObject.fromObject(object); Iterator it = jsonObject.keys(); //
		 * 遍历jsonObject数据，添加到Map对象 while (it.hasNext()) { String key =
		 * String.valueOf(it.next()); if(key!=null && key.length()>0 &&
		 * jsonObject.get(key)!=null &&
		 * jsonObject.get(key).toString().length()>0){ String value =
		 * jsonObject.get(key).toString(); if(key!=null && key.length()>0){
		 * data.put(key, value); }else{ data.put(key, ""); } } }
		 * dataList.add(data) ; }
		 */

		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		JSONArray jsonArray = JSONArray.fromObject(jsonArrayString);
		int size = jsonArray.size();
		Iterator it = null;
		String key = null;
		String value = null;
		Map<String, String> map = null;
		JSONObject jsonObject = null;
		for (int i = 0; i < size; i++) {
			// JSONObject 转化成map
			map = new HashMap<String, String>();
			jsonObject = jsonArray.getJSONObject(i);
			if (jsonObject != null) {
				it = jsonObject.keys();
				while (it.hasNext()) {
					key = String.valueOf(it.next());
					if (!StringUtils.isBlank(key)) {
						try {
							value = String.valueOf(jsonObject.get(key));
						} catch (Exception e) {
							value = "";
						}
						map.put(key, value);
					}
				}
			}
			// 将转化完成后的放入list
			if (map != null) {
				dataList.add(map);
			}
		}

		return dataList;
	}

	/**
	 * 
	 * @Title: getJsonStr @Description: TODO(将key和value组装成json的格式) @param @param
	 *         key @param @param value @param @return 设定文件 @return String
	 *         返回类型 @throws
	 */
	public static String getJsonStr(String key, String value) {
		if (StringUtils.isEmpty(value)) {
			value = "0";
		}
		String result = "\"" + key + "\":" + "\"" + value + "\"";
		return result;
	}

	/**
	 * @throws Exception
	 * 
	 * @Title: parseRule
	 * @Description: TODO(解析json)
	 * @param @param
	 *            rule
	 * @param @return
	 *            设定文件
	 * @return Map<String,String> 返回类型
	 * @throws @author
	 *             yuhaijun
	 */
	public static Map<String, String> parseRule(String rule) throws Exception {
		Map<String, String> ruleMap = new HashMap<String, String>();
		if (StringUtils.isEmpty(rule))
			return ruleMap;
		List<Map<String, Object>> ruleList = (List<Map<String, Object>>) json2List(rule, Map.class);
		if (ruleList == null || ruleList.size() == 0)
			return ruleMap;
		for (Map<String, Object> model : ruleList) {
			for (Map.Entry<String, Object> temp : model.entrySet()) {
				if (temp.getValue() instanceof Map) {
					Map<String, String> mapObj = (Map<String, String>) temp.getValue();
					StringBuilder jsonStr = new StringBuilder("{");
					for (Map.Entry<String, String> mode : mapObj.entrySet()) {
						if (jsonStr.toString().equals("{")) {
							jsonStr.append("\"" + mode.getKey() + "\":\"" + mode.getValue() + "\"");
						} else {
							jsonStr.append(",\"" + mode.getKey() + "\":\"" + mode.getValue() + "\"");
						}
					}
					jsonStr.append("}");
					ruleMap.put(temp.getKey(), jsonStr.toString());
				} else {
					ruleMap.put(temp.getKey(), temp.getValue().toString());
				}
			}
		}
		return ruleMap;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		/*
		 * String testStr =
		 * "{\"META-DATABASE\":\"LIVE_PROD\",\"META-PERATEIONTYPE\":\"UPDATE_FIELDCOMP\",\"META-OWNER\":\"LIVE_PROD\",\"META-FILEDVALUE\":{\"OPERATE_TYPE_BEFORE\":\"NULL\",\"RELATED_ID\":\"NULL\",\"CREATED_DATE\":\"2016-03-11 17:33:26\",\"CREATED_USER\":\"NULL\",\"OPERATE_TYPE\":\"NULL\",\"CREATED_USER_BEFORE\":\"NULL\",\"CLAIMABLE_ID\":\"187352025271\",\"CLAIMABLE_ID_BEFORE\":\"187352025271\",\"RELATED_ID_BEFORE\":\"NULL\",\"ID_BEFORE\":\"11032833925\",\"REMARK_BEFORE\":\"NULL\",\"CREATED_DATE_BEFORE\":\"NULL\",\"ID\":\"11032833925\",\"REMARK\":\"NULL\"},\"META-PRIMARYKEY\":\"ID\",\"META-TABLE\":\"GOME_CLAIMABLE_DETAIL\"}"
		 * ; System.out.println( JsonUtils.json2Map(testStr)); String database =
		 * StringUtils.substringBetween(testStr , "\"META-DATABASE\":", ",");
		 * String table =
		 * StringUtils.substringBetween(testStr,"\"META-TABLE\":", "}");
		 * 
		 * System.out.println( database + "." +table );
		 */

		// String json =
		// "{\"s\":\"4bo9dmdc\\x5c/kpwctx3cryrnkz5nsoqcrkiqoihyfpgysp9gkhr0cjrw==\"
		// }" ;
		// List list = json2List(json, String.class);
		// System.out.println( list.get( 0 ) );

		// Map map = json2MapObject(json) ;
		// System.out.println( map.get( "aa" ) );

		// String param = " { \"systemNo\" : \"0201\", \"profileID\" :
		// \"72053462888\", \"key\" : \"10D3611229202F18F6F72A9B6DF617EE\",
		// \"version\" : \"0.2\"}";
		// System.out.println( param );
		//
		// System.out.println( JsonUtils.replaceAttribute(param,
		// "systemNo","222"));

		// String result = JsonUtils.addAttribute( "{\"aa\":11}" ,
		// "otime=2&len=10&curpg=1&ostat=0&sn=581da745a8eb8dea835a0587c2cba08a3e71101a");
		// System.out.println( result );

		System.out.println(StringUtils.isNumeric(""));
	}

}
