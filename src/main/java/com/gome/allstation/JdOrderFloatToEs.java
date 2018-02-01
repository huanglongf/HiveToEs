package com.gome.allstation;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.gome.es.ESWriteClientBulk;
import com.gome.hdfs.HdfsReaderClient;
import com.gome.util.DateUtils;

public class JdOrderFloatToEs {

	private static Logger logger = LoggerFactory.getLogger(JdOrderFloatToEs.class);

	private static FileSystem fs;
	private static String esIndex = "all_station_jd_order_float";
	private static String esType = "search";

	/**
	 * 查看文件是否存在
	 */
	public static boolean checkFileExist(String path) {
		try {

			Path f = new Path(path);
			return fs.exists(f);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			return false;
		}
	}

	public static void writeToES() {

		System.out.println("JdOrderFloatToEs.....Start");

		Client esClient = ESWriteClientBulk.getClient();

		AllStart.deleteData(esClient, esIndex, esType);

		HdfsReaderClient client = new HdfsReaderClient();

		Map<String, String> map = new HashMap<String, String>();

		try {
			fs = FileSystem.get(client.getConf());

			BulkRequestBuilder builder = esClient.prepareBulk();

			List<String> days = DateUtils.get10days();

			for (String day : days) {

				System.out.println("文件目录:/gome/nrdmbs/logs/gomeIdentify_beta2/" + day);

				boolean b = checkFileExist("/gome/nrdmbs/logs/gomeIdentify_beta2/" + day);
				if (!b) {
					continue;
				}

				FileStatus[] files = fs.listStatus(new Path("/gome/nrdmbs/logs/gomeIdentify_beta2/" + day));

				for (FileStatus fileStatus : files) {

					BufferedReader br = client.getBufferedReader(fileStatus.getPath().toString());

					String line = null;

					while ((line = br.readLine()) != null) {

						JSONObject json = JSONObject.parseObject(line);

						String skuid = json.get("skuid").toString();

						String position = day + "#" + json.get("position").toString();

						String value = map.get(skuid);

						if (StringUtils.isBlank(value)) {
							map.put(skuid, position);
						} else {
							map.put(skuid, value + "," + position);
						}
					}
				}
			}

			for (Map.Entry<String, String> entry : map.entrySet()) {

				String data = "{\"skuid\":\"" + entry.getKey() + "\",\"position\":\"" + entry.getValue() + "\"}";

				builder.add(esClient.prepareIndex(esIndex, esType).setSource(data));
			}

			BulkResponse response = builder.get();

			if (response.hasFailures()) {
				System.out.println(response.buildFailureMessage());
			}
			client.close();
			System.out.println("JdOrderFloatToEs.....End");
		} catch (IOException e) {
			logger.error("导出JD近十天的排名出错!" + e.getMessage(), e);
		}
	}

	static boolean isSkuNull(String sku) {
		return "\\N".equals(sku);
	}

	public static void main(String[] args) {
		writeToES();
	}
}
