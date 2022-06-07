package com.datastax.da.astra.investment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;

// @SpringBootApplication
@SpringBootApplication(exclude = { 
		CassandraDataAutoConfiguration.class, 
		CassandraAutoConfiguration.class})
	// It was not taking the secure bundle contact points
// @EnableAutoConfiguration(exclude = { CassandraDataAutoConfiguration.class })
public class AstraApplication implements CommandLineRunner {

	/** Logger for the class. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AstraApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AstraApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		LOGGER.info("CLR - Nothing to do");
	}

}
