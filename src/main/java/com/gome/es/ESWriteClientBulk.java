package com.gome.es;

import java.net.InetSocketAddress;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class ESWriteClientBulk {

	public synchronized static Client getClient() {

		Settings settings = Settings.settingsBuilder().put("cluster.name", "gomebigdata-elasticsearch").build();
		return TransportClient.builder().settings(settings).build()
				.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("10.58.72.16", 9901)))
				.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("10.58.72.15", 9901)))
				.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("10.58.72.17", 9901)))
				.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("10.58.72.18", 9901)))
				.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("10.58.72.19", 9901)))
				.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("10.58.72.14", 9901)));
	}

}
