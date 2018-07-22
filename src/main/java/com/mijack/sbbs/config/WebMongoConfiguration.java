package com.mijack.sbbs.config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
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
    @Override
    public MongoClient mongoClient() {
        return new MongoClient("localhost", 27017);
    }

    @Override
    protected String getDatabaseName() {
        return "test";
    }

    @Bean
    public MongoDatabase mongoDatabase(MongoDbFactory mongoDbFactory) {
        return mongoDbFactory.getDb();
    }

    @Bean
    public GridFSBucket gridFSBucket(MongoDatabase mongoDatabase) {
        return GridFSBuckets.create(mongoDatabase, getDatabaseName());
    }

}
