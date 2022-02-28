1. create .env file
2. copy .env.example in .env
3. install docker
4. run docker compose up -d
5. open mysql on the exposed port (MYSQLDB_LOCAL_PORT)
6. with root user execute sql-scripts/instructor-export.sql
7. with root user execute sql-scripts/instructor-version.sql
8. Endpoints: in postman-collection/Java.postman_collection.json
9. Features/Technologies examined:

    Spring boot (maven project),
    hibernate,
    rest mvc,
    entities (relations),
    config as a code (multiple db connections),
    jpa data,
    tests(end to end, unit),
    event/listeners,
    queue jobs (database storage),
    mysql in docker container,
    custom logging,
    custom exception handler (global with @ControllerAdvice)

10. Job Dashboard on: http://localhost:8000/dashboard/jobs

