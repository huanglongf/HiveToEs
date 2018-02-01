package com.gome.allstation;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

public class AllStart {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		AllStationGomeToEs.writeToES();
		// AllStationJDToEs.writeToES();
		/*AllStationJDToEs2.writeToES();
		JdOrderFloatToEs.writeToES();*/
		System.out.println("用时:" + (System.currentTimeMillis() - start));
	}

	public static void deleteData(Client client, String esIndex, String esType) {

		SearchResponse response = client.prepareSearch(esIndex).setTypes(esType).setSize(10000).execute().actionGet();
		int i = 0;
		while (response.getHits().getTotalHits() > 0) {
			SearchHits hits = response.getHits();
			BulkRequestBuilder builder = client.prepareBulk();
			for (SearchHit hit : hits) {
				String id = hit.getId();
				builder.add(client.prepareDelete(esIndex, esType, id));
			}
			BulkResponse br = builder.get();
			if (br.hasFailures()) {
				System.out.println(br.buildFailureMessage());
			}
			System.out.println(i++ + "删除完成!总共!" + hits.getHits().length);
			response = client.prepareSearch(esIndex).setTypes(esType).setSize(10000).execute().actionGet();
		}
		System.out.println("数据删除完成!");
	}
}
