# Spring Data Cassandra and Multiple connection
The goal of this project is to show how to manage multiple Astra connections in Spring Boot using Spring Data Cassandra and Astra APIs. 

AWe are using the DataStax Model by Example for the Investment Portfolio sample.

TLDR; **SpringBoot autoconfigurator for Casssandra and Cassandra Data need to be disable**

The assumption is that you have basic understanding of DataStax Astra and Spring Framework, including Spring Boot. We are using Maven but the changes would be equivalent for Gradle. 

This blog on [Multiple keyspaces with Spring Data Cassandra](https://www.codingame.com/playgrounds/13647/multiple-keyspaces-with-spring-data-cassandra), though probably old was very helpful. So was this stackoverflow question regarding [Spring Reactive Cassandra, use custom CqlSession](https://stackoverflow.com/questions/63833915/spring-reactive-cassandra-use-custom-cqlsession)

The [Spring Data for Cassandra documentation](https://docs.spring.io/spring-data/cassandra/docs/current/reference/html/) provides specific on Spring Data Cassandra and is a great place for additional information. 

* [Registering a Session Instance by Uinsg Java-based Metadata](https://docs.spring.io/spring-data/cassandra/docs/current/reference/html/#cassandra.cassandra-java-config)
* [Working with Repositories](https://docs.spring.io/spring-data/cassandra/docs/current/reference/html/#repositories)
* [Query Methods](https://docs.spring.io/spring-data/cassandra/docs/current/reference/html/#repositories.query-methods)

## Application Information

**TODO** Document the various CqlSession objects being created by Spring Data Cassandra and Astra SDK. 

