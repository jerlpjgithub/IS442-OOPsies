@echo off

REM MySQL Container Name
set MYSQL_CONTAINER=mysqldb

REM MySQL Connection Details
set MYSQL_HOST=localhost
set MYSQL_PORT=3306
set MYSQL_USER=root
set MYSQL_PASSWORD=admin
set MYSQL_DATABASE=mydb

REM SQL Script to Insert Values
set SQL_SCRIPT=setup.sql

REM Command to Execute MySQL Client in Docker Container
set COMMAND=docker exec -i %MYSQL_CONTAINER% mysql -h %MYSQL_HOST% -P %MYSQL_PORT% -u %MYSQL_USER% -p%MYSQL_PASSWORD% %MYSQL_DATABASE%

REM Execute SQL Script
%COMMAND% < %SQL_SCRIPT%