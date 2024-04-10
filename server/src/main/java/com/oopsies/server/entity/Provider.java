package com.oopsies.server.entity;

/**
 * Enumeration representing the different authentication providers a user can use.
 * This is used to manage authentication in the application.
 */
public enum Provider {
    /**
     * Represents local authentication, i.e., authentication performed by the application itself.
     */
    LOCAL,

    /**
     * Represents authentication via Google.
     */
    GOOGLE
}