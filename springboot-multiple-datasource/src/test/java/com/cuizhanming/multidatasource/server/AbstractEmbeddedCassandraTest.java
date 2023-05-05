package com.cuizhanming.multidatasource.server;

import jakarta.annotation.PostConstruct;
import org.cassandraunit.spring.CassandraDataSet;
import org.cassandraunit.spring.CassandraUnitDependencyInjectionTestExecutionListener;
import org.cassandraunit.spring.EmbeddedCassandra;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.web.util.DefaultUriBuilderFactory;

@TestExecutionListeners(listeners = {
        CassandraUnitDependencyInjectionTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class}
)
@EmbeddedCassandra(timeout = 60000)
@CassandraDataSet(value = { "cassandra/setup.cql" }, keyspace = "mykeyspace")
public class AbstractEmbeddedCassandraTest {

    @Value("${local.server.port}")
    protected int port;

    @Value("${test.url}")
    protected String url;

    protected TestRestTemplate client;

    @PostConstruct
    public void init() {
        DefaultUriBuilderFactory handler = new DefaultUriBuilderFactory(url+":"+port);
        handler.setParsePath(true);
        client = new TestRestTemplate();
        client.setUriTemplateHandler(handler);
    }
}