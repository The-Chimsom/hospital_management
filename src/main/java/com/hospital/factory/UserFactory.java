package com.hospital.factory;

import com.hospital.models.Clerk;
import com.hospital.models.Nurse;
import com.hospital.models.User;
import com.hospital.utils.PasswordUtil;

public class UserFactory {

    public static User createUser(String userType, String username, String password, String email, String id, String additionalInfo) {
        // Hash the password before creating the user
        String hashedPassword = PasswordUtil.hashPassword(password);

        if ("nurse".equalsIgnoreCase(userType)) {
            return new Nurse(username, hashedPassword, email, id, additionalInfo);
        } else if ("clerk".equalsIgnoreCase(userType)) {
            return new Clerk(username, hashedPassword, email, id, additionalInfo);
        }

        throw new IllegalArgumentException("Invalid user type: " + userType);
    }
}