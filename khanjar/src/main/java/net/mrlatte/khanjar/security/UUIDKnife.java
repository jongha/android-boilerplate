package net.mrlatte.khanjar.security;

import java.util.UUID;

/**
 * Created by Jongha on 4/14/16.
 */
public class UUIDKnife {
    public static String randomString() {
        return UUID.randomUUID().toString();
    }
}