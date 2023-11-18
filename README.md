# Web Application

## Quick run

cd frontend/comparison-shopper
npm start

cd backend/products
mvn spring-boot:run 

## Code documentation 
* Feature 1: User Login
* Feature 2: Add Products to Cart
* Feature 3: Searching Products
* Feature 4: Filtering Products
* Feature 5: Comparing Product Prices


# Run Instructions
## Backend - Docker (run in backend/products)
Build docker image

```docker build --platform linux/amd64 -t p03-08-backend .```

Run docker image

```docker run --platform linux/amd64 -p 8080:8080 p03-08-backend```

## Frontend - Docker (run in frontend/comparison-shopper)
Build docker image

```docker build --platform linux/amd64 -t p03-08-frontend .```

Run docker image

```docker run --platform linux/amd64 -p 3000:3000 p03-08-frontend```

## Docker Compose
```docker-compose build```


```docker-compose up```


# Initial Setup

## Setup your environment
You will need to have in your system

- Java 17.0 or higher
- Node and npm
- Apache Maven
- IDE or Editor

Other tools will be required to complete the project (e.g., Docker)

## Backend
- Delete any unused services (i.e. backend/movies). They are there only for an initial reference.
- Use [Spring initializr](https://start.spring.io/) to create your (micro)services
- Place any new backend service in its own directory (i.e., backend/<service-name>)
- Confirm you can run your applicaiton (./mvnw package && java -jar target/[microservice]-0.0.1-SNAPSHOT.jar)

## Frontend
- cd into frontend/
- Install dependencies "npm install"
- Run the app with "npm run dev"
