package com.gome.hdfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HdfsReaderClient {
	FileSystem fs;
	Configuration conf = new Configuration();
	FSDataInputStream fsi;
	BufferedReader br;

	public HdfsReaderClient() {

		conf.set("dfs.nameservices", "gomebigdata-hdfs");
		conf.set("dfs.namenode.rpc-address.gomebigdata-hdfs.nn1", "s3sa112:8020");
		conf.set("dfs.namenode.rpc-address.gomebigdata-hdfs.nn2", "s3sa113:8020");
		conf.set("dfs.ha.namenodes.gomebigdata-hdfs", "nn1,nn2");
		conf.set("dfs.client.failover.proxy.provider.gomebigdata-hdfs",
				"org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
		conf.set("fs.defaultFS", "hdfs://gomebigdata-hdfs");
		conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
	}

	public void close() {
		try {
			br.close();
			fsi.close();
			fs.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Configuration getConf() {
		return conf;
	}

	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	public BufferedReader getBufferedReader(String filePath) {

		try {
			fs = FileSystem.get(conf);

			fsi = fs.open(new Path(filePath));

			br = new BufferedReader(new InputStreamReader(fsi));

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return br;
	}

	public static void main(String[] args) throws Exception {

		HdfsReaderClient c = new HdfsReaderClient();
		BufferedReader br = c.getBufferedReader("/gome-search/data/hotkey/20160825/part-00000");
		String line = null;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
		c.close();
		System.exit(0);

	}
}
