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

C:\JavaFxProject\mini-project │ ├── src │ ├── dao │ │ ├── GenericDAO.java│ │ ├── UserDAO.java│ │ ├── EventDAO.java│ │ ├── RoomDAO.java│ │ ├── FieldDAO.java│ │ └── ReservationDAO.java│ ├── model │ │ ├── User.java│ │ ├── Event.java│ │ ├── Room.java│ │ ├── Field.java│ │ └── Reservation.java│ ├── service │ │ ├── UserService.java│ │ ├── EventService.java│ │ ├── RoomService.java│ │ ├── FieldService.java│ │ └── ReservationService.java│ └── main │ └── Main.java│ ├── test │ ├── dao │ │ ├── UserDAOTest.java│ │ ├── EventDAOTest.java│ │ ├── RoomDAOTest.java│ │ ├── FieldDAOTest.java│ │ └── ReservationDAOTest.java│ ├── resources │ └── setup.sql│ └── README.md

## DAO Structure ### Generic DAO Interface The `GenericDAO<T>` interface includes the following methods: - `void add(T entity)` - `T get(int id)` - `List<T> getAll()` - `void update(T entity)` - `void delete(int id)` ### DAO Implementations The following DAOs implement the `GenericDAO<T>` interface: - `UserDAO` - `EvenementDAO` - `TerrainDAO` - `SalleDAO` - `ReservationDAO` ## Transaction Manager The `TransactionManager` class manages JDBC connections and transactions (commit, rollback). ### Example Method 
```java public void executeInTransaction(Runnable operation) { try (Connection conn = DriverManager.getConnection("jdbc:your_database_url", "username", "password")) { conn.setAutoCommit(false); try { operation.run(); conn.commit(); } catch (SQLException e) { conn.rollback(); throw e; } } catch (SQLException e) { e.printStackTrace(); } }
```


