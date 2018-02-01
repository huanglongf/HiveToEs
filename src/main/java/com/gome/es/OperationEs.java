package com.gome.es;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

public class OperationEs {

	static String index = "all_station_gome";
	static String type = "search";

	public static void delete(Client client) {

		// 开始结束时间
		RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("time");
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

//		boolQuery.must(rangeQuery.gte("2016-09-1").lte("2016-09-08"));

		SearchResponse response = client.prepareSearch(index).setTypes(type).setQuery(rangeQuery).setSize(10000)
				.execute().actionGet();

		SearchHits hits = response.getHits();

		System.out.println("总共！" + hits.getHits().length);

		if (hits != null && hits.getHits().length > 0) {
			BulkRequestBuilder builder = client.prepareBulk();

			for (SearchHit hit : hits) {
				String id = hit.getId();
				builder.add(client.prepareDelete(index, type, id));
			}

			BulkResponse br = builder.get();
			if (br.hasFailures()) {

				System.out.println(br.buildFailureMessage());
			}
		}
		System.out.println("删除完成！");
	}

	public static void main(String[] args) {
		Client client = ESWriteClientBulk.getClient();
//		for (int i = 0; i < 61; i++) {
			delete(client);
//		}
	}
}