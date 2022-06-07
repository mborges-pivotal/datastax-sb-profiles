package com.datastax.da.astra.investment.backend.secondary;

import java.io.File;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.SessionFactory;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
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
// @EnableCassandraRepositories(basePackages = { "com.datastax.da.astra.repository" })
@EnableCassandraRepositories(cassandraTemplateRef = "secondaryTemplate")
public class AstraConfigSecondaryRegion {

    @Value("${bundle.secondary}")
    private File cloudSecureBundle;
    
    @Value("${keyspace.secondary}")
    private String keyspace;

    @Value("${username.secondary}")
    private String username;

    @Value("${password.secondary}")
    private String password;

    @Bean("secondarySession")
    public SessionFactoryFactoryBean sessionFactory(@Qualifier("secondaryRegion") final CqlSession session, @Qualifier("secondaryConverter") final CassandraConverter converter) {
  
      SessionFactoryFactoryBean sessionFactory = new SessionFactoryFactoryBean();
      sessionFactory.setSession(session);
      sessionFactory.setConverter(converter);
      sessionFactory.setSchemaAction(SchemaAction.NONE);
  
      return sessionFactory;
    }
  
    @Bean("secondaryMapping")
    public CassandraMappingContext mappingContext(@Qualifier("secondaryRegion") final CqlSession cqlSession) {
  
      CassandraMappingContext mappingContext = new CassandraMappingContext();
      mappingContext.setUserTypeResolver(new SimpleUserTypeResolver(cqlSession));
  
      return mappingContext;
    }
        
    @Bean("secondaryConverter")
    public CassandraConverter converter(@Qualifier("secondaryMapping") final CassandraMappingContext mappingContext) {
      return new MappingCassandraConverter(mappingContext);
    }
  
    @Bean("secondaryTemplate")
    public CassandraOperations cassandraTemplate(@Qualifier("secondarySession") final SessionFactory sessionFactory, @Qualifier("secondaryConverter") final CassandraConverter converter) {
      return new CassandraTemplate(sessionFactory, converter);
    }
    @Bean("secondaryRegion")
    public CqlSession cassandraSession() {
        return CqlSession.builder().withAuthCredentials(username, password).withKeyspace(keyspace).withCloudSecureConnectBundle(cloudSecureBundle.toPath()).build();
    }
  
}