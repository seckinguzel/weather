# Meteorological Weather Service

Meteorological Weather Service is a Spring-based weather service application. This service fetches weather information from the WeatherStack API and stores the data in a database. Users can use this service to retrieve current weather information for a specific city.

## Getting Started

These instructions will guide you through setting up the project on your local machine for development and running. The project requires Java and Spring Boot 2.0+ to run.

### Prerequisites

You will need to have the following software installed:

- Java Development Kit (JDK)
- Maven
- A Java IDE (e.g., Eclipse or IntelliJ IDEA)

### Installation

1. Clone this project:
2. git clone https://github.com/your-github-account/meteorological-weather-service.git

Navigate to the project directory:
cd meteorological-weather-service

Install project dependencies:
mvn clean install

Run the application:
mvn spring-boot:run

The application will run by default at localhost:8080. Open your web browser and visit the following URL:
http://localhost:8080/api/weather?city=CityName
Replace CityName with the name of the city you want to check the weather for.

Usage

Once you have successfully run the application, you can use the following API request to get weather information for a specific city:

GET /api/weather?city=CityName
Example Request
GET /api/weather?city=Istanbul

Example Response
json
{
    "city": "Istanbul",
    "country": "Turkey",
    "temperature": 25.3,
    "localTime": "2023-10-12 15:30"
}
Dockerization

This application is Dockerized. You can build a Docker container and run the application using the provided Dockerfile. Follow the steps below:

Build the Docker image:
docker build -t meteorological-weather-service .

Run the Docker container:
docker run -p 8080:8080 meteorological-weather-service
