package org.ipni.controller;

import java.io.IOException;
import org.apache.solr.client.solrj.SolrServerException;
import org.ipni.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/1/stats")
public class StatsController {

	@Autowired
	StatsService stats;

	@GetMapping(path = "/namesPublishedIn/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String namesPublished(@PathVariable Integer year) throws SolrServerException, IOException {
		return stats.getNamesPublishedInYear(year);
	}

	@GetMapping(path = "/recordsAdded/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String recordsAdded(@PathVariable Integer year) throws SolrServerException, IOException {
		return stats.getRecordsAdded(year);
	}

	@GetMapping(path = "/recordsUpdated/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String recordsUpdated(@PathVariable Integer year) throws SolrServerException, IOException {
		return stats.getRecordsUpdated(year);
	}

	@GetMapping(path = "/standardization", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String standardization() throws SolrServerException, IOException {
		return stats.getStandardization();
	}
}
