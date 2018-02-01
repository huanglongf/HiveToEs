package com.gome.util;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;

public class Json {
	public static String toJSONString(Object object, SerializerFeature... features) {
		SerializeWriter out = new SerializeWriter();
		String s;

		JSONSerializer serializer = new JSONSerializer(out);
		SerializerFeature aserializerfeature[] = features;
		int i = aserializerfeature.length;
		for (int j = 0; j < i; j++) {
			SerializerFeature feature = aserializerfeature[j];
			serializer.config(feature, true);
		}

		serializer.getValueFilters().add(new ValueFilter() {

			@Override
			public Object process(Object obj, String s, Object obj1) {
				if (obj1 == null)
					return "";
				else
					return obj1;
			}
		});
		serializer.write(object);
		s = out.toString();
		out.close();
		return s;
	}
}
