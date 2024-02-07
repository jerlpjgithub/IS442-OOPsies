# Use the official MySQL image
FROM mysql:latest

# Set environment variables
ENV MYSQL_ROOT_PASSWORD=admin
ENV MYSQL_USER=is442
ENV MYSQL_PASSWORD=admin
ENV MYSQL_DATABASE=mydb

# Expose port 3306
EXPOSE 3306

# Build: docker build -t is442-sqldb -f db.Dockerfile .
# Run: docker run --name mysqldb -d -p 3306:3306 is442-sqldb