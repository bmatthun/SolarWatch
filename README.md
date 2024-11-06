# SolarWatch

This project is a comprehensive full-stack application that provides sunset and sunrise data for any city and date through a REST API. The application includes a backend that interacts with a sunset and sunrise data source, as well as a frontend for user interaction. The app is fully containerized with Docker for easy deployment and scalability.

## Table of Contents
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Docker Commands](#docker-commands)
- [Contact](#contact)

## Tech Stack

- ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
- ![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
- ![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)
- ![Vite](https://img.shields.io/badge/Vite-646CFF?style=for-the-badge&logo=vite&logoColor=white)
- ![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

## Prerequisites

- [Docker](https://www.docker.com/) and [Docker Compose](https://docs.docker.com/compose/) must be installed on your machine.

## Installation

1. Clone the project to your local machine:
    ```bash
    git clone https://github.com/bmatthun/solarwatch.git
    cd solarwatch
    ```

2. Build and start the application using Docker Compose:
    ```bash
    docker-compose up --build
    ```
   This command will build both backend and frontend services if needed, and start them in detached mode.

3. Verify that both services are running:
   - **Backend**: Accessible at [http://localhost:8080](http://localhost:8080)
   - **Frontend**: Accessible at [http://localhost:5173](http://localhost:5173)

## Usage

- To start the entire application stack:
    ```bash
    docker-compose up
    ```
- To stop the services:
    ```bash
    docker-compose down
    ```

## Docker Commands

While Docker Compose manages most commands for the multi-container setup, here are some additional commands:

- **Rebuild images**: `docker-compose up --build`
- **View logs**: `docker-compose logs`
- **Stop containers**: `docker-compose down`
