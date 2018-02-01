package com.gome.hotkey;

import java.io.BufferedReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;

import com.gome.bean.HotKey;
import com.gome.es.ESWriteClientBulk;
import com.gome.hdfs.HdfsReaderClient;
import com.gome.util.DateUtils;
import com.google.gson.Gson;

public class HotKeyToEs {

	FileSystem fs;
	Configuration conf = new Configuration();
	String esIndex = "hot-key";
	String esTypeing = "search";

	public void writeToES() throws Exception {
		String yesterday = DateUtils.getYesterday();
		HdfsReaderClient client = new HdfsReaderClient();
		fs = FileSystem.get(client.getConf());

		Gson g = new Gson();
		FileStatus[] files = fs.listStatus(new Path("/apps/hive/warehouse/hot_key/dt=20160908")); // +
																									// yesterday));
		Client esClient = ESWriteClientBulk.getClient();
		BulkRequestBuilder builder = esClient.prepareBulk();
		int i = 0;
		long start = 0l;
		for (FileStatus fileStatus : files) {
			BufferedReader br = client.getBufferedReader(fileStatus.getPath().toString());
			start = System.currentTimeMillis();
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] array = line.split(",");
				if (isSkuNull(array[0])) {
					continue;
				}
				HotKey hk = new HotKey(line);
				String esjson = g.toJson(hk);
				// System.out.println(esjson);

				builder.add(esClient.prepareIndex(esIndex, esTypeing).setSource(esjson));

				System.out.println("==" + i++);
			}

			BulkResponse response = builder.get();
			if (response.hasFailures()) {
				System.out.println(response.buildFailureMessage());
			}
			client.close();
		}
		System.out.println("用时:" + (System.currentTimeMillis() - start));
	}

	public static void main(String[] args) throws Exception {

		HotKeyToEs hte = new HotKeyToEs();
		hte.writeToES();

	}

	static boolean isSkuNull(String sku) {
		return "\\N".equals(sku);
	}
}
