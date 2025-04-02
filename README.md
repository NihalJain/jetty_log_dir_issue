# Jetty Logs Directory Bug Report

This project demonstrates a bug in Jetty 12 where setting the base resource to a path containing `..` results in a 404 error, whereas the same setup works in Jetty 9.

## Prerequisites

### Environment
* Jetty version: 9.x and 12.x
* Java version: 17
* Operating system: macOS
* Steps to reproduce (as described above)

## Project Structure

- `src/main/java/com/example/JettyServer9.java`: Jetty 9 server setup
- `src/main/java/com/example/JettyServer12.java`: Jetty 12 + EE8 server setup
- `src/main/java/com/example/Util.java`: Utility class to get logs directory
- `pom.xml`: Maven configuration

## Steps to Reproduce

### Setup (common for Jetty 9 and Jetty 12)

* Create a logs directory in the project root.
   - `mkdir logs`
* Ensure that the logs directory exists and is accessible.
* The `..` in the path should be resolved correctly in both Jetty 9 and Jetty 12.

### Running with Jetty 9

1. Navigate to the project directory.
2. Compile the project:
   ```sh
   mvn clean install -Pjetty9
   ```
3. Run the Jetty 9 server:
   ```sh
   java -cp target/jetty-log-dir-issue-1.0-SNAPSHOT.jar io.github.nihaljain.JettyServer9 "src/../logs" false
   ```
   Replace /path/to/logs with the actual path to your logs directory.
4. Open the following URL in a browser: http://localhost:8080/logs
   - You should see the contents of the logs directory.
5. Stop the Jetty 9 server.
6. Rerun the Jetty 9 server with the following command:
   ```sh
   java -cp target/jetty-log-dir-issue-1.0-SNAPSHOT.jar io.github.nihaljain.JettyServer9 "src/../logs" true
   ```
7. Open the following URL in a browser: http://localhost:8080/logs 
   - You should see the contents of the logs directory.
8. Stop the Jetty 9 server.
9. Clean the project:
   ```sh
   mvn clean
   ```


### Running with Jetty 12
1. Navigate to the project directory.
2. Compile the project:
   ```sh
   mvn clean install -Pjetty12
3. Run the Jetty 12 server:
   ```sh
   java -cp target/jetty-log-dir-issue-1.0-SNAPSHOT.jar io.github.nihaljain.JettyServer12 "src/../logs" false 
   ```
   Replace /path/to/logs with the actual path to your logs directory.
4. Open the following URL in a browser: http://localhost:8080/logs
   - You will see a 404 error with Jetty 12 + EE8
5. Stop the Jetty 12 server.
6. Now rerun the Jetty 12 server with the following command:
   ```sh
   java -cp target/jetty-log-dir-issue-1.0-SNAPSHOT.jar io.github.nihaljain.JettyServer12 "src/../logs" true
   ```
7. Open the following URL in a browser: http://localhost:8080/logs
   - You should see the contents of the logs directory.
8. Stop the Jetty 12 server.
9. Clean the project:
   ```sh
   mvn clean
   ```

## Actual Behavior

* Jetty 9: The server starts successfully, and the logs directory is accessible.
* Jetty 12: The server starts successfully, but accessing the logs directory results in a 404 error if the path contains `..`.

## Expected Behavior

* Jetty 9 and Jetty 12 should behave the same way when accessing the logs directory.