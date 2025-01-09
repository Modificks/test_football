# Football

This project is a REST API that uses PostgreSQL as the database and is containerized with Docker. To run the project, you need to have Docker and Docker Compose installed.

## Requirements

Before running the project, make sure you have:

- Docker
- Docker Compose

## Environment Setup

1. Open the `.env` file and replace the values of the variables with your own:

    - `DATABASE_URL`: the address of the PostgreSQL database.
    - `DATABASE_USERNAME`: the database username.
    - `DATABASE_PASSWORD`: the database password.

2. Save the `.env` file.

**The `.env` file should not be added to Git, so it must be added to `.gitignore`.**

## Running the Project

1. Make sure you have Docker and Docker Compose installed.

2. To run the project using Docker Compose, execute the following command in the console:

    ```bash
    docker-compose up --build
    ```

   This command will build and start the necessary containers for your project, including the PostgreSQL database.

## Testing the API

You can test the API using Postman by using the appropriate collection that can be exported from Postman.

### API Endpoints

- **GET http://localhost:8080/team/get-all**: Get all teams.
- **GET http://localhost:8080/team/get?name=**: Get a team by its name (case-insensitive).
- **POST http://localhost:8080/team/create**: Add a new team.
- **DELETE http://localhost:8080/team/delete?id=**: Delete a team by ID.
- **PATCH http://localhost:8080/team/update**: Update a team.


- **GET http://localhost:8080/player/get-all**: Get all players.
- **GET http://localhost:8080/player/get?id=**: Get a player by ID.
- **POST http://localhost:8080/player/create**: Add a new player.
- **DELETE http://localhost:8080/player/delete?id=**: Delete a player by ID.
- **PATCH http://localhost:8080/player/update**: Update a player.


- **PATCH http://localhost:8080/transfer?playerId=&fromTeamId=&toTeamId=**: Perform a transfer.
