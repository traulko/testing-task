# testing-task

## How to run application:
##### 1. Clone a project from GITHUB: git clone git@github.com:traulko/testing-task.git
##### 2. Run the command docker-compose up -d in terminal

### Used technologies:
##### Spring (Boot, Data JPA), Java 11, Docker, Gradle, Lombok, Postgresql, Liquibase, Junit5, Mokito, Apache Poi, MapStruct, Google GSON, Jackson, other: (hibernate-jpamodelgen, hibernate-types)

### Status:
##### Core task - completed
##### Extended task:
###### Normalization and completing missing fields are almost done, on the example of the city and countryCode, another fields - the similar implementation way. Filtering and sorting is shown on the example of surname and city. Filters can be easily added if required by a business task, search - by class fields.

#### In the course of solving the problem, I tried to adhere to the TDD strategy.
### How the app can be improved:
#### 1. Instead of **Specifications** can be used **QueryDSL**, but the specifications are built into the framework, queryDsl shows its power on big projects.
#### 2. To read large files with long line lengths, as well as write them to DB, you must use a **batch update**.
#### 3. For convenient use can be added **Swagger**.
#### 4. Javadocs can be added to better understand how the methods work.

#### Endpoints:
##### GET http://localhost:8080/api/v1/person?sortBy=city&surname=s 
##### GET http://localhost:8080/api/v1/person/normalization
##### POST http://localhost:8080/api/v1/person/uploading-file
