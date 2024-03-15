#!/bin/bash

# MySQL Container Name
MYSQL_CONTAINER="mysqldb"

# MySQL Connection Details
MYSQL_HOST="localhost"
MYSQL_PORT="3306"
MYSQL_USER="root"
MYSQL_PASSWORD="admin"
MYSQL_DATABASE="mydb"

# SQL Script to Insert Values
SQL_SCRIPT="setup.sql"

# Command to Execute MySQL Client in Docker Container
COMMAND="docker exec -i $MYSQL_CONTAINER mysql -h $MYSQL_HOST -P $MYSQL_PORT -u $MYSQL_USER -p$MYSQL_PASSWORD $MYSQL_DATABASE"

# Execute SQL Script
$COMMAND < $SQL_SCRIPT
