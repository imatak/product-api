# Product API

A simple Spring Boot REST API for managing products.

## Features

* Create new products
* List all products
* Fetch top 3 popular products

## Tech Stack

* **Java 25**
* **Spring Boot 4**
* **Spring Web**
* **Spring Data JPA**
* **H2 Database**
* **JUnit 5** & **Mockito** for testing

## Getting Started

### Prerequisites

* Java 25
* Maven 3+

### Installation

1. Clone the repository:

```bash
git clone https://github.com/imatak/product-api.git
cd product-api
```

2. Build the project:

```bash
mvn clean install
```

3. Run the application:

```bash
mvn spring-boot:run
```

The API will start on `http://localhost:8080`.

---

## API Endpoints

| Method | Endpoint                | Description                |
| ------ | ----------------------- | -------------------------- |
| POST   | `/api/products`         | Create a new product       |
| GET    | `/api/products`         | Get all products           |
| GET    | `/api/products/popular` | Get top 3 popular products |

---

### Example Request

**POST /api/products**

```json
{
  "code": "PROD00000000001",
  "name": "Phone",
  "priceEur": 100,
  "description": "Description of the product"
}
```

**Response**

```json
{
  "id": 1,
  "code": "PROD00000000001",
  "name": "Phone",
  "priceEur": 100,
  "priceUsd": 110,
  "description": "Description of the product"
}
```

---

## Testing

Run unit tests with:

```bash
mvn test
```

* Uses **JUnit 5** and **Mockito**.
* Controller tests with `@WebMvcTest` and `MockMvc`.

---

## License

This project is licensed under the MIT License.
