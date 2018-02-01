package com.gome.allstation;

import java.io.BufferedReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gome.bean.AllStationGome;
import com.gome.es.ESWriteClientBulk;
import com.gome.hdfs.HdfsReaderClient;
import com.google.gson.Gson;

public class AllStationGomeToEs {
	private static Logger logger = LoggerFactory.getLogger(AllStationGomeToEs.class);
	static FileSystem fs;
	static Configuration conf = new Configuration();
	static String esIndex = "test11111";
	static String esType = "search";

	public static void writeToES() {
		System.out.println("AllStationGomeToEs.....Start");
		try {
			Client esClient = ESWriteClientBulk.getClient();
			// AllStart.deleteData(esClient, esIndex, esType);
			Gson g = new Gson();
			HdfsReaderClient client = new HdfsReaderClient();
			BulkRequestBuilder builder = esClient.prepareBulk();
			fs = FileSystem.get(client.getConf());
			FileStatus[] files = fs.listStatus(new Path("/apps/hive/warehouse/mds.db/all_station_temp"));
			int i = 0;
			Integer temp = null;
			int order = 0;
			for (FileStatus fileStatus : files) {
				BufferedReader br = client.getBufferedReader(fileStatus.getPath().toString());

				String line = null;
				while ((line = br.readLine()) != null) {
					String[] array = line.split("#");
					if (isSkuNull(array[0])) {
						continue;
					}
					++i;
					AllStationGome as = new AllStationGome(line);
					if (temp == null) {
						as.setOrder(String.valueOf(++order));
						temp = Integer.parseInt(as.getSales1week());
					} else if (temp == Integer.parseInt(as.getSales1week())) {
						as.setOrder(String.valueOf(order));
					} else {
						as.setOrder(String.valueOf(i));
						order = i;
						temp = Integer.parseInt(as.getSales1week());
					}

					String esjson = g.toJson(as);

					builder.add(esClient.prepareIndex(esIndex, esType).setSource(esjson));
				}

				BulkResponse response = builder.get();
				if (response.hasFailures()) {
					System.out.println(response.buildFailureMessage());
				}
				client.close();
			}
			System.out.println("AllStationGomeToEs.....End");
		} catch (Exception e) {
			logger.error("全网数据Gome入es失败:" + e.getMessage(), e);
		}
	}

	static boolean isSkuNull(String sku) {
		return "\\N".equals(sku);
	}

	public static void main(String[] args) {
		writeToES();
	}
}
