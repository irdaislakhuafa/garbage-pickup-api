# Backend API for Garbage Pickup App

This is `Backend API` for `Garbage Pickup App` is a API to manage operations related to Garbage Pickup App throught a Mobile Application. This API provides two types of API, namely REST API and GraphQL API, which enable client applications to interact with server efficiently. This application was created as a requirement to complete mu undergraduate education.

## Entity design

<iframe width="560" height="315" src='https://dbdiagram.io/embed/649fab0202bd1c4a5e55430f'></iframe>

## Overview of app features

This application allows the client to manage some of the data below

- `user`

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

This application uses `Docker` to make it easier to development, deployment, and easy to run. First, make sure you install [`Docker`](https://www.docker.com/get-started/) and [`Docker Compose`](https://docs.docker.com/compose/install/) according to the Operating System you are using and run

Clone this project the go to this project directory and run the command below

```bash
docker compose up -d
```

Then `Docker` will magically prepare a suitable environment to run this application. You can modify the environment variables you need in the [`docker-compose.yaml`](./docker-compose.yaml)

## Where is the API Documentation?

As written above that this application implements 2 types of API and each API technology has different documentation

Below is the documentation that you can see after you run this application.

- ### Rest API

  You can open a web browser and access url below to vew the `Rest API` documentation for the `Garbage Pickup Application`

  ```text
  http://localhost:8080/api/rest/docs
  ```

- ### GraphQL API
