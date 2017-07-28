package org.ipni.indexer;

import org.ipni.indexer.loader.Loader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
public class IndexerRunner implements CommandLineRunner {

	@Autowired
	Loader loader;

	@Value("${build.onstartup}")
	Boolean buildOnStartup;

	@Override
	public void run(String... arg0) throws Exception {
		if(buildOnStartup) {
			loader.load();
		}
	}

}
