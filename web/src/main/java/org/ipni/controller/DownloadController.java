package org.ipni.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.ipni.constants.FieldMapping;
import org.ipni.download.Downloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.ImmutableMap;

@Controller
@RequestMapping("/api/1")
public class DownloadController {

	@Autowired
	SolrClient solr;

	@GetMapping("download")
	public void download(HttpServletResponse response, @RequestParam Map<String, String> params) throws IOException, SolrServerException {
		Map<String, List<FieldMapping>> exportFields = ImmutableMap.<String, List<FieldMapping>>of(
				"citations", FieldMapping.citationFields,
				"authors", FieldMapping.authorFields,
				"publications", FieldMapping.publicationFields);

		Downloader downloader = new Downloader(params, exportFields, solr);
		File archive = downloader.buildArchive();

		response.setContentType("application/zip, application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + archive.getName() + "\"");
		response.setContentLength((int)archive.length());
		InputStream in = new BufferedInputStream(new FileInputStream(archive));

		FileCopyUtils.copy(in, response.getOutputStream());

		downloader.cleanup();
	}
}
