package org.ipni.indexer;

import java.util.TimeZone;

import org.ipni.indexer.loader.Loader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class IndexerRunner implements CommandLineRunner {

	@Autowired
	Loader loader;

	@Value("${build.onstartup}")
	Boolean buildOnStartup;

	@Value("${build.schedule}")
	String cronExpression;

	@Override
	public void run(String... arg0) throws Exception {
		if(buildOnStartup) {
			loader.run();
		} else {
			TaskScheduler scheduler = new ConcurrentTaskScheduler();
			log.info("Starting scheduler for indexer with cron: {}", cronExpression);
			scheduler.schedule(loader, new CronTrigger(cronExpression, TimeZone.getTimeZone("GMT")));
		}
	}
}
