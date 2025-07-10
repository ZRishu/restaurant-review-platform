# Restaurant Review Platform

A modern web application that allows users to discover local restaurants, read and write reviews, and search with advanced filtering and geolocation features. This project integrates Spring Boot, Elasticsearch, Keycloak, and Docker to provide a robust backend foundation for restaurant-based review platforms.

---

## Features

- Search Restaurants using:
  - Name (fuzzy matching)
  - Cuisine type
  - Minimum star rating
  - Location-based proximity (via geolocation)
- Restaurant Management
  - Add, update, and delete restaurant details (only by owners)
  - Upload operating hours, photos, and address with geolocation
- Review System
  - Authenticated users can write, update (within 48hrs), and delete reviews
  - Star ratings and multiple photo uploads supported
- Photo Upload & Retrieval
  - Users can upload and view images attached to reviews or restaurants
- Secure Authentication
  - Powered by Keycloak, using OAuth2 and OpenID Connect
- Geospatial Search
  - Integrated with Elasticsearch to find restaurants near you
- Admin Dashboard (via Kibana)
  - Explore your Elasticsearch data visually in development

---

## Tech Stack

| Layer                          | Technology                               |
|--------------------------------|------------------------------------------|
| Backend                        | Spring Boot, Spring Security             |
| Auth                           | Keycloak (OAuth2, OpenID Connect)        |
| Search Engine                  | Elasticsearch                            |
| Containerization               | Docker, Docker Compose                   |
| Monitoring                     | Kibana                                   |
| Data Exchange                  | RESTful APIs (JSON)                      |
| Storage                        | Elasticsearch (as DB)                    |
| Build Tool                     | Maven                                    |
| API documentation and testing  | Swagger (Springdoc OpenAPI)              |
| Others                         | Multipart file handling for photo upload |

---

## Getting Started

### Prerequisites

Make sure the following are installed on your system:

- Java 21+
- Maven
- Docker & Docker Compose
- Git

---

### Local Setup (Linux/macOS/Windows)

##### 1. Clone the repository
```bash
git clone https://github.com/ZRishu/restaurant-review-platform.git
cd restaurant-review-platform
```
##### 2. Start required services
```bash
docker compose up -d
```
##### 3. Build and run the Spring Boot application
```bash
./mvnw spring-boot:run
```

---

### Keycloak Setup

- Access Keycloak at `http://localhost:9090`
- Realm, client, and user setup can be done manually.
- Use Keycloak's client secret and endpoints in your Spring Boot app config.

---

## REST API Reference

### Authentication

All endpoints requiring login expect an Authorization: Bearer `<token>` header.

---

### Photos

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/api/photos` | Upload a photo | Yes |
| GET | `/api/photos/{photoId}` | Get a photo | No |

---

### Restaurants

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/api/restaurants` | Create restaurant | Yes |
| GET | `/api/restaurants` | Search/list restaurants | No |
| GET | `/api/restaurants/{id}` | Get restaurant details | No |
| PUT | `/api/restaurants/{id}` | Update restaurant | Yes |
| DELETE | `/api/restaurants/{id}` | Delete restaurant | Yes |

#### Search Parameters (GET `/api/restaurants`)

| Param | Description |
|-------|-------------|
| `q` | Search term |
| `latitude`, `longitude` | Geo-coordinates |
| `radius` | Radius in kilometers |
| `cuisineType` | Filter by cuisine |
| `minRating` | Minimum star rating |
| `page`, `size` | Pagination |

---

### Reviews

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/restaurants/{restaurantId}/reviews` | List reviews for a restaurant | No |
| GET | `/api/restaurants/{restaurantId}/reviews/{reviewId}` | Get specific review | No |
| POST | `/api/restaurants/{restaurantId}/reviews` | Add a review | Yes |
| PUT | `/api/restaurants/{restaurantId}/reviews/{reviewId}` | Update review (within 48hrs) | Yes |
| DELETE | `/api/restaurants/{restaurantId}/reviews/{reviewId}` | Delete review | Yes |

#### Review Parameters

| Param | Description |
|-------|-------------|
| `sort` | `date,desc`, `date,asc`, `rating,desc`, `rating,asc` |
| `page`, `size` | Pagination controls |

---

## API Testing

You can easily test all API endpoints in two ways:

### 1. **Using `.http` Files (in `.http` Folder)**

* `.http/auth.http`: Get Bearer Token via Keycloak.
* `.http/restaurant.http`, `review.http`, `photo.http`: Contains pre-written requests for testing APIs.
* Open these files in IntelliJ IDEA or VS Code (with REST Client extension).
* Replace placeholder values (e.g., JWT_TOKEN, IDs) as needed and click “Run”.

### 2. **Using Swagger UI**

Swagger UI is integrated into the project for quick API exploration and testing.
* Swagger UI is available at: `{{BASE_URL}}/swagger-ui.html`
* Explore and test all available endpoints interactively.

---

## Screens (Frontend-Ready API)

The backend is API-first and frontend-agnostic. You can build the frontend using:

- React (with Axios or React Query)
- Next.js
- Vue
- Flutter / Android

---

## Future Enhancements

- Bookmark/Favorite restaurants
- Admin Panel for managing reports
- Machine learning-based recommendations
- Push notifications for offers

---