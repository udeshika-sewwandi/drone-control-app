# drone-control-app

To start the database service, follow the below steps.

1. Navigate to `drone-control-app/data`, there you find the Dockerfile to run the dockerized MySQL service
2. Execute `docker build -t drone_db:latest .` to build the docker image
3. Then execute `docker run -p 3307:3306 --name=drone_db drone_db:latest` to run the docker container with the database and necessary data
4. Find the docker container id by executing `docker ps`
5. You can view the database by going into interactive mode with the container by executing `docker exec <container_id> -it  /bin/sh`
6. Inside the docker container run `mysql -u root -p`
7. The database user is `root`, password is `welcome1!` and database is `drone_db`
8. Then execute `use drone_db` inside the docker container
9. There are four tables in the database namely, `drone, drone_history, drone_items, medication`

* Only 7 drones are added into the database. Maximum number of drones that can be added to the database is 10.
* When items are loaded into drone, they are saved in `drone_items` table.
  * The periodic task to check drone battery level runs every 5min and records battery level for all drones in the database in `drone_history` table

To run the app, execute the following `mvn` commands.

`mvn clean install`
`mvn spring-boot:run` - this will run the app on port 8080

To test the app, you can use an API testing client like Postman.

* You can access the app on `http:localhost:8080`
* The api documentation can be found at `http://localhost:8080/swagger-ui.html`



