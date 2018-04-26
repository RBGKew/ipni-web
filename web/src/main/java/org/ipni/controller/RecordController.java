package org.ipni.controller;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.ipni.service.AuthorService;
import org.ipni.service.NameService;
import org.ipni.service.PublicationService;
import org.ipni.util.IdUtil;
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

	@GetMapping(path = { "/n/{identifier}", "/urn:lsid:ipni.org:names:{identifier}" })
	public String showName(@PathVariable String identifier, Model model) throws SolrServerException, IOException {
		model.addAttribute("object", names.load(IdUtil.fqName(identifier)));
		return "name";
	}

	@GetMapping(path = { "/a/{identifier}", "/urn:lsid:ipni.org:authors:{identifier}" })
	public String showAuthor(@PathVariable String identifier, Model model) throws SolrServerException, IOException {
		model.addAttribute("object", authors.load(IdUtil.fqAuthor(identifier)));
		return "author";
	}

	@GetMapping(path = { "/p/{identifier}", "/urn:lsid:ipni.org:publications:{identifier}" })
	public String showPublication(@PathVariable String identifier, Model model) throws SolrServerException, IOException {
		model.addAttribute("object", publications.load(IdUtil.fqPublication(identifier)));
		return "publication";
	}
}