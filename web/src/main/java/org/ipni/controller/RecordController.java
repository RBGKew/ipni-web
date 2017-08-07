package org.ipni.controller;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.ipni.service.AuthorService;
import org.ipni.service.NameService;
import org.ipni.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RecordController {

	@Autowired
	private AuthorService authors;

	@Autowired
	private NameService names;

	@Autowired
	private PublicationService publications;

	@GetMapping("/urn:lsid:ipni.org:names:{identifier}")
	public String showName(@PathVariable String identifier, Model model) throws SolrServerException, IOException {
		model.addAttribute("object", names.load(identifier));
		return "name";
	}

	@GetMapping("/urn:lsid:ipni.org:authors:{identifier}")
	public String showAuthor(@PathVariable String identifier, Model model) throws SolrServerException, IOException {
		model.addAttribute("object", authors.load(identifier));
		return "author";
	}

	@GetMapping("/urn:lsid:ipni.org:publications:{identifier}")
	public String showPublication(@PathVariable String identifier, Model model) throws SolrServerException, IOException {
		model.addAttribute("object", publications.load(identifier));
		return "publication";
	}
}