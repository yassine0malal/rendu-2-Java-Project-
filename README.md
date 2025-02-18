# rendu-2-Java-Project-

# Mini Project: Data Access Objects (DAO)

## Overview

This project aims to deepen the structure of Data Access Objects (DAO) for better separation of layers in the application. It includes refactoring CRUD operations for each entity, creating a generic DAO interface, implementing a JDBC transaction manager, and providing unit tests for each DAO.

## Objectives

1. Refactor CRUD operations for each entity (Users, Events, Rooms, Fields, Reservations) using a DAO structure.
2. Create a generic DAO interface to standardize data access methods.
3. Implement a JDBC transaction manager to ensure the integrity of multi-query operations.
4. Provide unit tests for each DAO.

## Project Structure

The project is organized into the following packages:

- `dao`: Contains DAO interfaces and implementations.
- `model`: Contains entity classes.
- `service`: Contains business logic classes.
- `main`: Contains the main application class.

### Directory Structure


## DAO Structure

### Generic DAO Interface

The `GenericDAO<T>` interface includes the following methods:
- `void add(T entity)`
- `T get(int id)`
- `List<T> getAll()`
- `void update(T entity)`
- `void delete(int id)`

### DAO Implementations

The following DAOs implement the `GenericDAO<T>` interface:
- `UserDAO`
- `EvenementDAO`
- `TerrainDAO`
- `SalleDAO`
- `ReservationDAO`

## Transaction Manager

The `TransactionManager` class manages JDBC connections and transactions (commit, rollback).

### Example Method

```java
public void executeInTransaction(Runnable operation) {
    try (Connection conn = DriverManager.getConnection("jdbc:your_database_url", "username", "password")) {
        conn.setAutoCommit(false);
        try {
            operation.run();
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


2. **Créez un fichier `pom.xml`** dans le répertoire racine de votre projet et ajoutez-y le contenu suivant :

```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.example</groupId>
  <artifactId>mini-project</artifactId>
  <version>1.0-SNAPSHOT</version>

  <name>mini-project</name>
  <url>http://www.example.com</url>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>22</maven.compiler.source>
    <maven.compiler.target>22</maven.compiler.target>
    <javafx.version>22.0.1</javafx.version> 
  </properties>

  <dependencies>
    <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter-api</artifactId>
      <version>5.7.1</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter-engine</artifactId>
      <version>5.7.1</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.testcontainers</groupId>
      <artifactId>postgresql</artifactId>
      <version>1.17.3</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.postgresql</groupId>
      <artifactId>postgresql</artifactId>
      <version>42.6.0</version>
    </dependency>
    <dependency>
      <groupId>org.openjfx</groupId>
      <artifactId>javafx-controls</artifactId>
      <version>13</version>
    </dependency>
    <dependency>
      <groupId>org.openjfx</groupId>
      <artifactId>javafx-fxml</artifactId>
      <version>13</version>
    </dependency>
  </dependencies>

  <build>
    <pluginManagement>
      <plugins>
        <plugin>
          <artifactId>maven-clean-plugin</artifactId>
          <version>3.1.0</version>
        </plugin>
        <plugin>
          <artifactId>maven-resources-plugin</artifactId>
          <version>3.0.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-compiler-plugin</artifactId>
          <version>3.8.0</version>
        </plugin>
        <plugin>
          <artifactId>maven-surefire-plugin</artifactId>
          <version>2.22.1</version>
        </plugin>
        <plugin>
          <artifactId>maven-jar-plugin</artifactId>
          <version>3.0.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-install-plugin</artifactId>
          <version>2.5.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-deploy-plugin</artifactId>
          <version>2.8.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-site-plugin</artifactId>
          <version>3.7.1</version>
        </plugin>
        <plugin>
          <artifactId>maven-project-info-reports-plugin</artifactId>
          <version>3.0.0</version>
        </plugin>
        <plugin>
          <groupId>org.codehaus.mojo</groupId>
          <artifactId>exec-maven-plugin</artifactId>
          <version>3.1.0</version>
          <executions>
            <execution>
              <goals>
                <goal>java</goal>
              </goals>
            </execution>
          </executions>
          <configuration>
            <mainClass>com.example.JavaFx.MainJavaFx</mainClass>
          </configuration>
        </plugin>
        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-surefire-plugin</artifactId>
          <version>3.0.0-M5</version>
        </plugin>
        <plugin>
          <groupId>org.openjfx</groupId>
          <artifactId>javafx-maven-plugin</artifactId>
          <version>0.0.6</version>
          <executions>
            <execution>
              <id>default-cli</id>
              <configuration>
                <mainClass>com.example.JavaFx.DashbordCode</mainClass>
              </configuration>
            </execution>
          </executions>
        </plugin>
      </plugins>
    </pluginManagement>
  </build>
</project>
```
