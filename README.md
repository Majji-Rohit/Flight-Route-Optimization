# Flight Route Optimization System

A Java-based flight route optimization application that retrieves airport and flight data from a MySQL database and finds the shortest-distance and cheapest routes using Dijkstra's algorithm.

## Features

- Retrieves airport information from MySQL.
- Retrieves flight connections, distances, and fares from MySQL.
- Uses an adjacency list to represent the flight network.
- Finds the shortest route based on distance.
- Finds the cheapest route based on fare.
- Uses Dijkstra's algorithm with a PriorityQueue.
- Supports case-insensitive airport codes.
- Handles invalid menu inputs and airport codes.

## Technologies Used

- Java
- MySQL
- JDBC (Java Database Connectivity)
- Graphs and Adjacency Lists
- Dijkstra's Algorithm
- PriorityQueue
- Java Collections Framework

## Project Structure

```text
FlightRouteOptimization/
│
├── FlightRouteOptimization.java
├── DatabaseConnection.java
├── AirportDAO.java
├── FlightDAO.java
├── README.md
├── .gitignore
│
└── lib/
    └── mysql-connector-j-26.10.0.jar
```

## Database Design

The project uses a MySQL database named `flight_route_db` with two tables:

**Airports**
- airport_code
- airport_name
- city

**Flights**
- flight_id
- source
- destination
- distance
- fare

The Java application connects to MySQL using JDBC and retrieves the records through DAO classes.

## Algorithms

**Dijkstra's Shortest Path Algorithm**

Finds the route with the minimum total distance between two airports.

**Dijkstra's Cheapest Path Algorithm**

Uses ticket fare as the edge weight to find the route with the minimum total fare.

Both algorithms use a PriorityQueue to efficiently process the next airport with the lowest current cost.

## Complexity

Let V represent the number of airports and E represent the number of flight connections.

- **Time Complexity:** O((V + E) log V)
- **Space Complexity:** O(V + E)

## Requirements

- Java JDK
- MySQL Server
- MySQL Connector/J
- VS Code or any Java IDE

## How to Run

### 1. Configure MySQL

Create the database and tables, and insert airport and flight records.

Configure the database connection in `DatabaseConnection.java`.

### 2. Configure the database password

Set the database password as an environment variable:

```bash
export DB_PASSWORD='your_actual_database_password'
```

Replace the placeholder with your actual MySQL application-user password.

### 3. Compile the Java files

```bash
javac -cp ".:lib/mysql-connector-j-26.10.0.jar" DatabaseConnection.java AirportDAO.java FlightDAO.java FlightRouteOptimization.java
```

### 4. Run the application

```bash
java -cp ".:lib/mysql-connector-j-26.10.0.jar" FlightRouteOptimization
```

## Sample Output

```text
FLIGHT ROUTE OPTIMIZATION SYSTEM

1. Display Airports
2. Display Flight Routes
3. Find Shortest Route
4. Find Cheapest Route
5. Exit

Shortest Route:
DEL -> BLR -> COK
Distance: 2230 km

Cheapest Route:
DEL -> BLR -> COK
Fare: Rs. 9200.00
```

*Note: Flight distances and fares are sample project data, not live airline information.*

## Future Enhancements

- Add airline and flight schedule information.
- Support directed flight connections.
- Develop a graphical user interface.
- Integrate real-time flight information.