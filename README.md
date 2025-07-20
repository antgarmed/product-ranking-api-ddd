# Product Ranking API

[![Java](https://img.shields.io/badge/Java-17-blue.svg)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)

A backend service that provides a ranked list of products based on a weighted sum of configurable criteria. This project is a practical exercise focused on building a robust, extensible, and well-tested application following modern software architecture principles.

## Project Description

The core functionality of this API is to sort a list of T-shirts based on a set of ranking criteria. The final score for each product is calculated as a weighted sum of scores from different criteria, such as:

1.  **Sales Units:** A score based on the total number of units sold.
2.  **Stock Ratio:** A score based on the number of available sizes in stock.

A key requirement is that the system must be **open and extensible**, allowing new ranking criteria to be added in the future with minimal changes to the existing codebase.

## Architecture

This project was built with a strong emphasis on clean architecture and design patterns to ensure maintainability and scalability.

- **Hexagonal Architecture (Ports and Adapters):** The application is structured into three distinct layers: `domain`, `application`, and `infrastructure`. This isolates the core business logic from external concerns like web frameworks or data persistence.

  - `ProductRepository` and `RankingCriteriaPort` are examples of **output ports** defined by the application.
  - `InMemoryProductRepository`, `RankingCriteriaAdapter`, and `GetRankedProductsEndpoint` are **adapters** that implement or use these ports.

- **Domain-Driven Design (DDD):** The core logic is modeled using DDD concepts. `Product` is treated as an **Aggregate Root**, and `ProductStock` is a **Value Object**, ensuring business invariants are always protected.

- **CQRS (Command Query Responsibility Segregation):** The application layer is organized following a CQRS pattern, with a clear separation of Query and Command objects. For this project, the primary use case is a **Query** (`GetRankedProductsQuery`) handled by a dedicated `GetRankedProductsQueryHandler`.

  - **No Query Bus:** For this specific, simple, read-only use case, a dedicated query bus was not implemented. The `GetRankedProductsQueryHandler` is directly invoked by the endpoint. While a query bus provides benefits like further decoupling between request and handler, and enables advanced cross-cutting concerns (e.g., caching, logging) at the dispatch level, its introduction for such a minimal scope would be over-engineering. The architecture, however, is prepared for its addition should the complexity or requirements evolve.

- **Strategy Pattern:** To achieve extensibility for ranking criteria, the **Strategy Pattern** is used. The `RankingCriterion` interface defines the contract, and concrete classes like `SalesCriterion` and `StockRatioCriterion` provide a specific implementation. This makes adding new criteria as simple as creating a new class.

- **API-First Approach:** The API contract is defined in a static `openapi.yaml` file, which serves as the single source of truth. The application is then implemented to match this contract. This promotes parallel development and a clear, stable API.

## Tech Stack

- **Java 17**
- **Spring Boot 3.x**
- **Gradle**
- **JUnit 5 & Mockito** for testing
- **Springdoc OpenAPI** for Swagger UI documentation
- **Lombok** for reducing boilerplate code

## Getting Started

### Prerequisites

- JDK 17 or higher

### Running the Application

1.  Clone the repository.
2.  Run the application:
    ```sh
    ./gradlew bootRun
    ```
3.  The API will be available at `http://localhost:8080`.

### API Documentation (Swagger UI)

Once the application is running, you can access the interactive Swagger UI documentation at:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

This UI is generated directly from the `openapi.yaml` file and allows you to explore and test the API from your browser.

## API Usage

The main endpoint is `GET /products`. It requires a `weights` query parameter.

The `weights` parameter should be a comma-separated string of `key:value` pairs, where `key` is the name of the criterion (`sales`, `stock`) and `value` is its numeric weight.

### Testing the API with `requests.http`

For easy testing and to provide executable examples, a `requests.http` file is included in the project root. This file can be used with IDEs like IntelliJ IDEA (native support) or VS Code (with the "REST Client" extension).

Just open the `requests.http` file in your IDE and click on the "Send Request" button above each request block.

#### Example Response Body

The API returns a list of ranked products. Each item in the list contains the original product data and its calculated score.

```json
[
  {
    "product": {
      "id": 5,
      "name": "CONTRASTING LACE T-SHIRT",
      "salesUnits": 650,
      "productStock": {
        "S": 0,
        "M": 1,
        "L": 0
      }
    },
    "score": 520.2
  },
  {
    "product": {
      "id": 1,
      "name": "V-NECH BASIC SHIRT",
      "salesUnits": 100,
      "productStock": {
        "S": 4,
        "M": 9,
        "L": 0
      }
    },
    "score": 80.4
  }
]
```

## Running the Tests

The project has a comprehensive test suite, including unit tests for each layer and a full integration test.

To run all tests:

```sh
./gradlew test
```

A test report can be found in the `build/reports/tests/test` directory.
