1. create .env file
2. copy .env.example in .env
3. install docker
4. run docker compose up -d
5. open mysql on the exposed port (MYSQLDB_LOCAL_PORT)
6. create db from sql-scripts/instructor-export.sql
7. Endpoints:

Get: localhost:8090/api/v1/instructor/
Get: localhost:8090/api/v1/instructor/paging
Get: localhost:8090/api/v1/instructor/:id
Get: localhost:8090/api/v1/instructor/name/:firstName
Post: localhost:8090/api/v1/instructor
Delete: localhost:8090/api/v1/instructor/:id
PATCH: localhost:8090/api/v1/instructor/:id