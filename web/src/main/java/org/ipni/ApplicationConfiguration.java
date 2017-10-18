package org.ipni;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.qos.logback.access.tomcat.LogbackValve;

@Configuration
public class ApplicationConfiguration {

	@Bean
	public SolrClient solrClient() {
		return new HttpSolrClient.Builder("http://solr:8983/solr/ipni_1").build();
	}

	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
		LogbackValve logback = new LogbackValve();
		logback.setFilename("classpath:/logback-access.xml");
		tomcat.addContextValves(logback);

		return tomcat;
	}

}
