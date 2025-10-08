# 🧾 VStore - Order Service

This is the **Order Service** for the VStore microservices-based e-commerce platform. It is responsible for handling order creation and management.

This service communicates with the **Cart** and **Product** services to finalize an order. It registers with the **Eureka Server** and is a core part of the [VStore project](https://github.com/your-username/VStore).

---

## ⚙️ Core Technologies

* **Backend**: Java 17, Spring Boot
* **Database**: MySQL
* **Security**: Spring Security, JWT
* **Service Discovery**: Spring Cloud Eureka Client

---

## 🔑 Environment Variables

* **`PORT`**: `9093`
* **`EUREKA_SERVER`**: URL of the Eureka registry.
* **`DB_URL`**: MySQL connection URL.
* **`DB_USERNAME`**: Database username.
* **`DB_PASSWORD`**: Database password.
* **`JWT_SECRET`**: Secret key for validating JWT tokens.

---

## 🚀 API Endpoints

* **POST `/api/orders/order`**: Creates a new order from the user's current cart. (Access: Authenticated)
* **GET `/api/orders/orders`**: Retrieves the user's order history. (Access: Authenticated)

---

## ▶️ How to Run

1.  Ensure **MySQL** and the **VStore-EurekaServer** are running. The **Product and Cart Services** should also be running for full functionality.
2.  Navigate to the `VStore-OrderService/` directory.
3.  Run the service using Maven:
    ```bash
    mvn spring-boot:run
    ```
4.  The service will register with Eureka and be available on port `9093`.
