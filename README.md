# SM360 Api Application

This document outlines the steps required to install, configure and run a Spring Boot application 

## Documentation

* [Dependencies Required](#Dependencies-Required)
* [Database Creation](#Database-Creation)
* [The application.properties File](#The-application.properties-File)
* [Spring Boot Run Command](#Spring-Boot-Run-Command)
* [Spring Boot Run Test Command](#Spring-Boot-Run-Test-Command)
* [Routes](#Routes)
  - [Dealers](#Dealers-Routes)
  - [Listings](#Listings-Routes)
* [Suggestions](#Suggestions)


## Dependencies Required

Before getting started, you must install the following dependencies:

* [Java (JDK)](https://www.java.com/en/download/)
* [MySQL Server](https://mysql.com/downloads/mysql/)

## Database Creation
Open a terminal (command prompt in Microsoft Windows) and open a MySQL client as a user who can create new users.
For example, on a Linux system, use the following command:
```
$ sudo mysql --password
```
This connects to MySQL as root and allows access to the user from all hosts. This is not the recommended way for a production server.
To create a new database, run the following commands at the mysql prompt:
```
mysql> create database db_example; -- Creates the newdatabase
mysql> create user 'springuser'@'%'identified by 'ThePassword'; -- Creates the user
mysql> grant all on db_example.* to 'springuser'@'%'; -- Gives all privileges to the newuser on the newly created database
```
## The application.properties File
Please make sure to input your enviroment variables as it is in 
```application.example.properties```

## Spring Boot Run Command

To run the Spring Boot application, use the following command:

```
mvn spring-boot:run
```

## Spring Boot Run Test Command

To run the tests for your Spring Boot application, use the following command:

```
mvn test
```
## Routes
### Dealers Routes
The following are examples with request bodies for dealers routes:

- `GET /dealers?page=<page>&size=<size>`
```
{}
```

- `POST /dealers`
```
{
	"name": "A&B Motors",
	"tierLimit": 10
}
```

### Listing Routes
The following are examples with request bodies for listings routes:

- `GET /listings?page=<page>&size=<size>`
```
{
	"dealerId": "208139c7-f6fa-46fc-baa0-2f733132a781",
	"state": "PUBLISHED"
}
```

- `POST /listings`
```
{
	"dealerId": "208139c7-f6fa-46fc-baa0-2f733132a781",
	"vehicle": "GM Onix",
	"price": 25.3,
	"state": "PUBLISHED"
}
```
PS: This route can be also used for updating a listing

- `DELETE /listings`
```
{
	"id": "be2f338d-ef22-44f8-a5a7-9d4177f56df4"
}
```

- `POST /listings/publish`
```
{
	"id": "be2f338d-ef22-44f8-a5a7-9d4177f56df4",
	"dealerId": "208139c7-f6fa-46fc-baa0-2f733132a781"
}
```

- `POST /listings/unpublish`
```
{
	"id": "be2f338d-ef22-44f8-a5a7-9d4177f56df4",
	"dealerId": "208139c7-f6fa-46fc-baa0-2f733132a781"
}
```
## Suggestions 

Suggestions on how to improve the application.

1. Add Plan to Dealer and Integrate with Limit Logic
    - This will give customers more flexibility in choosing their plans and will improve the customer journey. 

2. Let the Dealer choose when they register how they want to deal with their tier limit, wheather limit or unplubish the last ad.
   
3. Add authorization and authentication to routes.

4. Switch status to draft when ad has passed publication date.

5. Amplify and improve test
   - Add new integration, repository and services tests
