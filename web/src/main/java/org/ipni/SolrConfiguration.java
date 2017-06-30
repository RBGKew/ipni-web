package org.ipni;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolrConfiguration {

	@Bean
	public SolrClient solrClient() {
		return new HttpSolrClient.Builder("http://solr:8983/solr/ipni_view").build();
	}

}
