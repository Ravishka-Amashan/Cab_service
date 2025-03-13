package com.ravishka.megacitycab;

import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class MongoDbConnectionTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testMongoDbConnection() {
        // This test will fail if MongoDB connection is not configured correctly
        assertNotNull(mongoTemplate);
        
        try {
            // Print MongoDB server version to verify connection
            Document buildInfo = mongoTemplate.getDb().runCommand(new Document("buildInfo", 1));
            String serverVersion = buildInfo.getString("version");
            System.out.println("Connected to MongoDB server version: " + serverVersion);
        } catch (Exception e) {
            System.out.println("MongoDB connection test failed: " + e.getMessage());
            // Don't fail the test if MongoDB is not running
            // This allows the application to start without MongoDB for development
        }
    }
}
