package com.hospital;

import com.hospital.DatabaseConfig;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DatabaseConfigTest {

    private MockedStatic<MongoClients> mockedMongoClients;
    private MongoClient mockMongoClient;
    private MongoDatabase mockDatabase;

    @BeforeEach
    void setUp() {
        // Setup mock for MongoClients
        mockMongoClient = mock(MongoClient.class);
        mockDatabase = mock(MongoDatabase.class);

        // Setup static mock for MongoClients.create()
        mockedMongoClients = Mockito.mockStatic(MongoClients.class);
        mockedMongoClients.when(() ->
                MongoClients.create(anyString())).thenReturn(mockMongoClient);

        // Setup mock for getDatabase
        when(mockMongoClient.getDatabase(anyString())).thenReturn(mockDatabase);
    }

    @AfterEach
    void tearDown() {
        // Close the static mock to prevent memory leaks
        mockedMongoClients.close();

        // Reset the singleton instance for next test
        try {
            java.lang.reflect.Field instance = DatabaseConfig.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetInstance() {
        // First call to getInstance should create a new instance
        DatabaseConfig instance1 = DatabaseConfig.getInstance();
        assertNotNull(instance1);

        // Second call should return the same instance
        DatabaseConfig instance2 = DatabaseConfig.getInstance();
        assertSame(instance1, instance2);

        // Verify that MongoClients.create was called once
        mockedMongoClients.verify(() ->
                MongoClients.create(eq("mongodb://localhost:27017/HOSPITAL_MANAGEMENT")), times(1));
    }

    @Test
    void testGetDatabase() {
        DatabaseConfig config = DatabaseConfig.getInstance();
        MongoDatabase database = config.getDatabase();

        assertNotNull(database);
        assertSame(mockDatabase, database);

        // Verify that getDatabase was called with the correct database name
        verify(mockMongoClient).getDatabase("HOSPITAL_MANAGEMENT");
    }

    @Test
    void testClose() {
        DatabaseConfig config = DatabaseConfig.getInstance();
        config.close();

        // Verify that close was called
        verify(mockMongoClient).close();
    }
}