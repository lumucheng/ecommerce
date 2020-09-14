# Getting Started

## About
This Spring Boot project was developed using [IntelliJ IDEA Community Edition 2020](https://www.jetbrains.com/idea/download/#section=windows) and [Spring Initializr](https://start.spring.io/)

## Setting Up

Download the csv file from [Kaggle](https://www.kaggle.com/carrie1/ecommerce-data/download).

*cd* to root path of the project, and run
>`$ ./gradlew bootRun`

After the application has started, assuming default port 8080:
* Visit [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) to view and test the list of API methods.
* Visit [http://localhost:8080/h2-console/](http://localhost:8080/h2-console/) to access the database. The table 'COMMERCE' will be empty if the CSV file has yet to be uploaded.

 ![Settings](./h2_settings.PNG?raw=true "H2 Connection Details")
 
 Username: sa
 
 Password: password
 
 ## Testing
To test the application, use either Postman or the bundled Swagger UI.

In Swagger UI, Click on 'Try it out', enter /select the necessary parameters, and click 'Execute'.

![Swagger UI](./swagger_example.PNG?raw=true "Swagger UI Example")

To run unit tests, *cd* to root path of the project, and run
>`$ ./gradlew test`

## Dependencies
A few of the dependencies that was used:

#### H2 Database Engine
I opted for an in memory SQL database as it would be more efficient to handle the searching and pagination of uploaded CSV data.

#### Spring Data JPA
For ease of database queries and access.

#### OpenCSV
To read csv files.

#### Swagger UI with Springfox
To help generate a clean and easy to navigate API documentation.


