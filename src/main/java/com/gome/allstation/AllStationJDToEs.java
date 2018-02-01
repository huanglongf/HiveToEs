// package com.gome.allstation;
//
// import java.io.BufferedReader;
// import java.io.IOException;
//
// import org.apache.hadoop.conf.Configuration;
// import org.apache.hadoop.fs.FileStatus;
// import org.apache.hadoop.fs.FileSystem;
// import org.apache.hadoop.fs.Path;
// import org.elasticsearch.action.bulk.BulkRequestBuilder;
// import org.elasticsearch.action.bulk.BulkResponse;
// import org.elasticsearch.client.Client;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
//
// import com.alibaba.fastjson.JSONObject;
// import com.gome.es.ESWriteClientBulk;
// import com.gome.hdfs.HdfsReaderClient;
// import com.gome.util.DateUtils;
//
// public class AllStationJDToEs {
// private static Logger logger =
// LoggerFactory.getLogger(AllStationJDToEs.class);
//
// static FileSystem fs;
// static Configuration conf = new Configuration();
// static String esIndex = "all_station_jd";
// static String esType = "search";
//
// public static void writeToES() {
// System.out.println("AllStationJDToEs.....Start");
// Client esClient = ESWriteClientBulk.getClient();
// AllStart.deleteData(esClient, esIndex, esType);
// HdfsReaderClient client = new HdfsReaderClient();
// try {
// fs = FileSystem.get(client.getConf());
//
// BulkRequestBuilder builder = esClient.prepareBulk();
// String yesterday = DateUtils.getYesterday();
// System.out.println("文件目录:/gome/nrdmbs/logs/gomeIdentify_beta2/" + yesterday);
// FileStatus[] files = fs.listStatus(new
// Path("/gome/nrdmbs/logs/gomeIdentify_beta2/" + yesterday));
// for (FileStatus fileStatus : files) {
// BufferedReader br =
// client.getBufferedReader(fileStatus.getPath().toString());
// String line = null;
// while ((line = br.readLine()) != null) {
// JSONObject json = JSONObject.parseObject(line);
// json.remove("_id");
// json.remove("salesVolume");
// json.remove("lastTime");
// String jdcatNameInfo = json.get("jdcatNameInfo").toString();
//
// String[] split = jdcatNameInfo.split("\\+");
// if (split.length > 2) {
// json.put("jdcatNameInfo", split[2]);
// }
//
// String gome = json.get("gome").toString();
// String detail = "null";
// if (gome.contains("detail")) {
// JSONObject jsongome = JSONObject.parseObject(gome);
// detail = jsongome.get("detail").toString();
// if (detail.equals("[]")) {
// detail = "null";
// }
// }
// json.remove("gome");
// json.put("goemdetail", detail);
//
// builder.add(esClient.prepareIndex(esIndex,
// esType).setSource(json.toString()));
// }
// BulkResponse response = builder.get();
// if (response.hasFailures()) {
// System.out.println(response.buildFailureMessage());
// }
// client.close();
// }
// System.out.println("AllStationJDToEs.....End");
// } catch (IOException e) {
// logger.error("全网数据JD入es失败:" + e.getMessage(), e);
// }
// }
//
// }
