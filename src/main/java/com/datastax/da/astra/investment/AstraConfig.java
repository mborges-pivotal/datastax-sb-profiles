package com.datastax.da.astra.investment;

import java.io.File;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.SessionFactory;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.SessionFactoryFactoryBean;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import com.datastax.oss.driver.api.core.CqlSession;

/**
 * Taking over the Cassandra Configuration from the Spring Boot Data Cassandra
 * started so we can use the latest driver with the properties to connect to
 * Astra
 * 
 * @see https://docs.spring.io/spring-data/cassandra/docs/1.5.4.RELEASE/reference/html/
 */
@Configuration
@EnableCassandraRepositories(basePackages = { "com.datastax.da.astra.investment.backend.repository" })
public class AstraConfig {

    @Value("${bundle}")
    private File cloudSecureBundle;

    @Value("${keyspace.primary}")
    private String keyspace;

    @Value("${username.primary}")
    private String username;

    @Value("${password.primary}")
    private String password;
  
    @Bean
    public SessionFactoryFactoryBean sessionFactory(@Qualifier("mainRegion") final CqlSession session, CassandraConverter converter) {
  
      SessionFactoryFactoryBean sessionFactory = new SessionFactoryFactoryBean();
      sessionFactory.setSession(session);
      sessionFactory.setConverter(converter);
      sessionFactory.setSchemaAction(SchemaAction.NONE);
  
      return sessionFactory;
    }
  
    @Bean
    public CassandraMappingContext mappingContext(@Qualifier("mainRegion") final CqlSession cqlSession) {
  
      CassandraMappingContext mappingContext = new CassandraMappingContext();
      mappingContext.setUserTypeResolver(new SimpleUserTypeResolver(cqlSession));
  
      return mappingContext;
    }
  
    @Bean
    public CassandraConverter converter(CassandraMappingContext mappingContext) {
      return new MappingCassandraConverter(mappingContext);
    }
  
    @Bean
    public CassandraOperations cassandraTemplate(SessionFactory sessionFactory, CassandraConverter converter) {
      return new CassandraTemplate(sessionFactory, converter);
    }
    
    @Bean("mainRegion")
    public CqlSession cassandraSession() {
        return CqlSession.builder().withAuthCredentials(username, password).withKeyspace(keyspace).withCloudSecureConnectBundle(cloudSecureBundle.toPath()).build();
    }
}
