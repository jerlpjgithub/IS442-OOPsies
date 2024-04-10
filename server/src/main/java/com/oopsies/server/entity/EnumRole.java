package com.oopsies.server.entity;

/**
 * Enumeration representing the different roles a user can have.
 * This is used to manage access control in the application.
 */
public enum EnumRole {
    /**
     * Role representing a regular user.
     */
    ROLE_USER,

    /**
     * Role representing an officer.
     * Officers have more privileges than regular users.
     */
    ROLE_OFFICER,

    /**
     * Role representing a manager.
     * Managers have the most privileges.
     */
    ROLE_MANAGER
}