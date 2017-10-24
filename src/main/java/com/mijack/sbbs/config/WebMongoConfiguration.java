package com.mijack.sbbs.config;

import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

/**
 * @author Mr.Yuan
 * @since 2017/10/19
 */
@Configuration
public class WebMongoConfiguration extends AbstractMongoConfiguration {

    @Bean
    public GridFS gridFS(MongoDbFactory dbFactory) {
        return new GridFS(dbFactory.getDb());
    }

    @Override
    protected String getDatabaseName() {
        return "test";
    }

    @Override
    public synchronized MongoClient mongo() throws Exception {
        return new MongoClient("localhost", 27017);
    }

}
