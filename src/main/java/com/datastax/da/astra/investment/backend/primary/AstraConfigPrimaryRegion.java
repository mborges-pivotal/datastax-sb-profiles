package com.datastax.da.astra.investment.backend.primary;

import java.io.File;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.SessionFactory;
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
 * starter so we can use a more recent latest driver with the properties to
 * connect to
 */
@Configuration
// @EnableCassandraRepositories(basePackages = {
// "com.datastax.da.astra.repository" })
@EnableCassandraRepositories(cassandraTemplateRef = "primaryTemplate")
public class AstraConfigPrimaryRegion {

    @Value("${bundle.primary}")
    private File cloudSecureBundle;

    @Value("${keyspace.primary}")
    private String keyspace;

    @Value("${username.primary}")
    private String username;

    @Value("${password.primary}")
    private String password;

    @Bean("primarySession")
    public SessionFactoryFactoryBean sessionFactory(@Qualifier("primaryRegion") final CqlSession session, @Qualifier("primaryConverter") final CassandraConverter converter) {
  
      SessionFactoryFactoryBean sessionFactory = new SessionFactoryFactoryBean();
      sessionFactory.setSession(session);
      sessionFactory.setConverter(converter);
      sessionFactory.setSchemaAction(SchemaAction.NONE);
  
      return sessionFactory;
    }
  
    @Bean("primaryMapping")
    public CassandraMappingContext mappingContext(@Qualifier("primaryRegion") final CqlSession cqlSession) {
  
      CassandraMappingContext mappingContext = new CassandraMappingContext();
      mappingContext.setUserTypeResolver(new SimpleUserTypeResolver(cqlSession));
  
      return mappingContext;
    }
        
    @Bean("primaryConverter")
    public CassandraConverter converter(@Qualifier("primaryMapping") final CassandraMappingContext mappingContext) {
      return new MappingCassandraConverter(mappingContext);
    }
  
    @Bean("primaryTemplate")
    public CassandraOperations cassandraTemplate(@Qualifier("primarySession") final SessionFactory sessionFactory, @Qualifier("primaryConverter") final CassandraConverter converter) {
      return new CassandraTemplate(sessionFactory, converter);
    }
    @Bean("primaryRegion")
    public CqlSession cassandraSession() {
        return CqlSession.builder().withAuthCredentials(username, password).withKeyspace(keyspace).withCloudSecureConnectBundle(cloudSecureBundle.toPath()).build();
    }
}