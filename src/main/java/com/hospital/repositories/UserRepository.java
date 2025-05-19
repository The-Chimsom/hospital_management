package com.hospital.repositories;

import com.hospital.DatabaseConfig;
import com.hospital.models.User;
import com.hospital.models.Nurse;
import com.hospital.models.Clerk;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private MongoCollection<Document> collection;

    public UserRepository() {
        this.collection = DatabaseConfig.getInstance().getDatabase().getCollection("users");
    }

    public void save(User user) {
        Document doc = new Document("_id", user.getId())
                .append("username", user.getUsername())
                .append("password", user.getPassword())
                .append("email", user.getEmail())
                .append("role", user.getRole());

        if (user instanceof Nurse) {
            Nurse nurse = (Nurse) user;
            doc.append("nurseId", nurse.getNurseId())
                    .append("department", nurse.getDepartment());
        } else if (user instanceof Clerk) {
            Clerk clerk = (Clerk) user;
            doc.append("clerkId", clerk.getClerkId())
                    .append("desk", clerk.getDesk());
        }

        collection.insertOne(doc);
    }

    public User findByUsername(String username) {
        Document doc = collection.find(Filters.eq("username", username)).first();
        if (doc == null) {
            return null;
        }

        String role = doc.getString("role");
        ObjectId id = doc.getObjectId("_id");
        String password = doc.getString("password");
        String email = doc.getString("email");

        if ("NURSE".equals(role)) {
            Nurse nurse = new Nurse(username, password, email, doc.getString("nurseId"), doc.getString("department"));
            nurse.setId(id);
            return nurse;
        } else if ("CLERK".equals(role)) {
            Clerk clerk = new Clerk(username, password, email, doc.getString("clerkId"), doc.getString("desk"));
            clerk.setId(id);
            return clerk;
        }

        return null;
    }

    public List<User> findByRole(String role) {
        List<User> users = new ArrayList<>();
        for (Document doc : collection.find(Filters.eq("role", role))) {
            String username = doc.getString("username");
            String password = doc.getString("password");
            String email = doc.getString("email");
            ObjectId id = doc.getObjectId("_id");

            if ("NURSE".equals(role)) {
                Nurse nurse = new Nurse(username, password, email, doc.getString("nurseId"), doc.getString("department"));
                nurse.setId(id);
                users.add(nurse);
            } else if ("CLERK".equals(role)) {
                Clerk clerk = new Clerk(username, password, email, doc.getString("clerkId"), doc.getString("desk"));
                clerk.setId(id);
                users.add(clerk);
            }
        }

        return users;
    }

    public User findById(ObjectId id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        if (doc == null) {
            return null;
        }

        String username = doc.getString("username");
        String password = doc.getString("password");
        String email = doc.getString("email");
        String role = doc.getString("role");

        if ("NURSE".equals(role)) {
            Nurse nurse = new Nurse(username, password, email, doc.getString("nurseId"), doc.getString("department"));
            nurse.setId(id);
            return nurse;
        } else if ("CLERK".equals(role)) {
            Clerk clerk = new Clerk(username, password, email, doc.getString("clerkId"), doc.getString("desk"));
            clerk.setId(id);
            return clerk;
        }

        return null;
    }
}
