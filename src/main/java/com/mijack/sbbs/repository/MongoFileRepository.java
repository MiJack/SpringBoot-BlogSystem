package com.mijack.sbbs.repository;

import com.mijack.sbbs.model.MongoFile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoFileRepository extends MongoRepository<MongoFile, String> {
}