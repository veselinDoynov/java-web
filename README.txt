1. create .env file

2. copy .env.example in .env

3. install docker

4. run docker compose up -d

5. open mysql on the exposed port (MYSQLDB_LOCAL_PORT)

6. with root user execute sql-scripts/instructor-export.sql in your mysql Client

 or import it with docker command:
docker exec -i YOUR_MYSQL_CONTAINER_NAME_HERE bash -l -c "mysql -uroot -pYOUR_PASSWORD_HERE < /docker-entrypoint-initdb.d/sql-script/instructor-export.sql"

7. with root user execute sql-scripts/instructor-testdb.sql in your mysql Client

or import it with docker command:
docker exec -i YOUR_MYSQL_CONTAINER_NAME_HERE bash -l -c "mysql -uroot -pYOUR_PASSWORD_HERE < /docker-entrypoint-initdb.d/sql-script/instructor-testdb.sql"

8. with root user execute sql-scripts/instructor-version.sql in your mysql Client

or import it with docker command:
docker exec -i YOUR_MYSQL_CONTAINER_NAME_HERE bash -l -c "mysql -uroot -pYOUR_PASSWORD_HERE < /docker-entrypoint-initdb.d/sql-script/instructor-version.sql"

9. Endpoints: in postman-collection/Java.postman_collection.json

10. Features/Technologies examined:

    Spring boot (maven project),
    hibernate,
    rest mvc,
    entities (relations),
    config as a code (multiple db connections),
    jpa data,
    tests(end to end, unit) executed against java-instructor-test database,
    event/listeners,
    queue jobs (database storage),
    mysql in docker container,
    custom logging,
    custom exception handler (global with @ControllerAdvice)

  Job Dashboard on: http://localhost:8000/dashboard/jobs

