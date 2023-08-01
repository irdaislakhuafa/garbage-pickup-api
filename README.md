# Backend API for Garbage Pickup App

This is `Backend API` for `Garbage Pickup App` is a API to manage operations related to Garbage Pickup App throught a
Mobile Application. This API provides two types of API, namely REST API and GraphQL API, which enable client
applications to interact with server efficiently. This application was created as a requirement to complete mu
undergraduate education.

## Overview of app features

This application allows the client to manage some of the data below

- `user`
- `pickup`
- `pickup activity`
- `voucher`
- `user voucher`
- `trash type`
- `role`
- `receipt`
- `contact us`

## Entity design

You can see design of entities for this Backend API [here](https://dbdiagram.io/embed/649fab0202bd1c4a5e55430f)

## Technology used in this API

Below are some of the technologies used or implemented in this application

- `Rest API`
- `GraphQL API`
- `MinIO`
- `Docker` and `Docker Compose`
- `Java`
- `Spring Boot`
    - `Spring Security`
    - `Spring GraphQL`
    - `Spring Data JPA`
    - `Spring Web`
    - `Spring Dev Tools`
    - `Project Lombok`
    - `Spring Doc Open API`
    - `Spring Boot Validation`
- `PostgreSQL` and `H2` (just for dummy data)

## How to run it

This application uses `Docker` to make it easier to development, deployment, and easy to run. First, make sure you
install [`Docker`](https://www.docker.com/get-started/) and [`Docker Compose`](https://docs.docker.com/compose/install/)
according to the Operating System you are using and run

Clone this project the go to this project directory and run the command below

```bash
docker compose up -d
```

Then `Docker` will magically prepare a suitable environment to run this application. You can modify the environment
variables you need in the [`docker-compose.yaml`](./docker-compose.yaml)

## Where is the API Documentation?

As written above that this application implements 2 types of API and each API technology has different documentation

Below is the documentation that you can see after you run this application.

- ### Rest API

  To simplify ad accelerate API development. We created Rest API documentation using `Swagger UI`
  from `Spring Doc Open API`. You can open a web browser and access url below to vew the `Rest API` documentation for
  the `Garbage Pickup Application`

  ```text
  http://localhost:8080/api/rest/docs
  ```

- ### GraphQL API

  Every GraphQL implementation usually has automatic documentation created by GraphQL itself. GraphQL has a built-in
  tool called GraphiQL. GraphiQL is provided by the GraphQL server as a feature to facilitate the development of the
  GraphQL API. In addition, GraphiQL can be used to test the GraphQL API requests and responses, and it is similar to
  using Postman to test the Rest API except that GraphiQL is lighter and more specific to the GraphQL API This
  documentation
  is
  based on the GraphQL schema created during the development of GraphQL API.

  To access GraphiQl you can open a web browser and access url below

  ```text
  http://localhost:8080/api/gql/graphiql
  ```
  And use the url below to make every request to the GraphQL API
  ```text
  http://localhost:8080/api/gql/graphql
  ```
  For example below I made a request to the GraphQL API with `curl`
  ```bash
  curl --request POST \
    --data-raw '{ "query": "query { hello { sayHello ( message: \"World\" ) } }" }' \
    --header 'Content-Type: application/json' \
    --url 'http://localhost:8080/api/gql/graphql'
  ```
  And i get a response like following

  ```json
  {
    "data": {
      "hello": {
        "sayHello": "Hello, World"
      }
    }
  }
  ```