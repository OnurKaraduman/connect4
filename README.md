# RESTful Connect4 Game Web Service

[![Build Status](https://api.travis-ci.org/repositories/OnurKaraduman/connect4.svg)](https://travis-ci.org/OnurKaraduman/connect4)


This is a sample of a Connect4 game RESTful Web Service service based on Spring Boot platform using OAuth2 for protecting the endpoint and Mongo DB as a NoSQL storage.

### Technology stack POC based on Spring Boot
##### Spring projects used:
* [Spring Boot](http://projects.spring.io/spring-boot/)
* [Spring Data Mongo DB](http://projects.spring.io/spring-data-mongodb/)
* [Spring Data REST](http://projects.spring.io/spring-data-rest/)
* [Spring Security OAuth](http://projects.spring.io/spring-security-oauth/)
* [Spring HATEOAS](http://projects.spring.io/spring-hateoas/)
 
### Building

##### Using Maven

````sh
mvn clean install
````

### Running 

##### Using Maven

````sh
mvn spring-boot:run
`````

### Packaging

##### Using Maven

```` sh
mvn package
````

To define custom mongo DB URI use spring.data.mongodb.uri argument.
````sh
java -jar target/connect4-1.0.0.war --spring.data.mongodb.uri=
````

### Usage

:information_source: For testing purposes curl is used.

##### Let's start with **ROOT** API resource.

The REST service strongly **Hypermedia-driven** and Content Type is **application/hal+json**.

````sh
curl -X GET http://localhost:8080/api/v1.0.0/ -k
````

You will received hal+json body containing public resource(s).
````json
{
    "_links": {
        "users": {
            "href": "http://localhost:8080/api/games"
        }
    }
}
````
##### Now let's **Start Game** .

````sh
curl -X POST http://localhost:8080/api/v1.0.0/games -k -d '{"userName":"onur","color":"BLACK"}' -H 'Content-Type: application/json'
````

You will receive game status with game token.
````
{  
   "board":{  
      "points":null
   },
   "score":0,
   "gameToken":"baf1bc31-e822-4809-9daf-351384e7fe98",
   "playerColor":BLACK,
   "state":"WAITING"
}
````

##### Now let's read the game you've created

````sh
curl -X GET http://localhost:8080/api/users/ales -k -v
````

You will received **HTTP/1.1 401 Unauthorized** status and a following JSON body:

````json
{  
   "error":"unauthorized",
   "error_description":"Full authentication is required to access this resource"
}
````

##### It's time to authenticate with new player's credentials

Put the game token you have received before to password
````sh
curl -X POST -vu webui:webuisecret http://localhost:8080/api/v1.0.0/oauth/token -k -H "Accept: application/json" -d "password=baf1bc31-e822-4809-9daf-351384e7fe98&username=onur&grant_type=password&scope=read%20write&client_secret=webuisecret&client_id=webui"
````
You will received JSON containing access and refresh tokens.
````json
{  
   "access_token":"94b246f6-48d5-464a-855b-b25c5dcecae0",
   "token_type":"bearer",
   "refresh_token":"6ce329f4-4c81-42fc-a427-3d4e44ad828b",
   "expires_in":43199,
   "scope":"read write"
}
````

#### Get game's status
````sh
curl -X GET http://localhost:8080/api/v1.0.0/games/baf1bc31-e822-4809-9daf-351384e7fe98 -k -v -H "Authorization: Bearer 94b246f6-48d5-464a-855b-b25c5dcecae0"
````

You will received basic game status and hypermedia links pointing to game related resources.

````json
{  
   "board":{  
      "points":null
   },
   "score":0,
   "gameToken":"baf1bc31-e822-4809-9daf-351384e7fe98",
   "playerColor":null,
   "state":"WAITING"
}
````

#### Join the game

````sh
curl -X POST http://localhost:8080/api/v1.0.0/games/baf1bc31-e822-4809-9daf-351384e7fe98/players -k -d '{"userName":"jack","color":"BLACK"}' -H 'Content-Type: application/json'
````

You will received game status

````sh
{  
   "board":{  
      "points":null
   },
   "score":0,
   "gameToken":"baf1bc31-e822-4809-9daf-351384e7fe98",
   "playerColor":"WHITE",
   "state":"CONTINUE"
}
````
#### For more details about web services, you can use swagger documentation without authentication
````sh
http://localhost:8080/api/v1.0.0/swagger-ui.html
````