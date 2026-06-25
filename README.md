# 👕 Git Dressed

**Git Dressed** is a full-stack e-commerce web application built with **Spring Boot**, **Java**, **JPA/Hibernate**, **MySQL**, and a responsive HTML/CSS/JavaScript front end. The application allows users to browse products, manage shopping carts, maintain profiles, and place orders, while administrators can manage inventory and categories.

The project demonstrates REST API development, layered architecture, authentication and authorization with Spring Security, database persistence, and CRUD operations.

---

## Features

### Customer Features

- User registration and login with JWT authentication
- Browse all products
- Search and filter products by:
  - Category
  - Minimum price
  - Maximum price
  - Subcategory
- View product details
- View and update personal profile information
- Add products to shopping cart
- Update cart quantities
- Remove all items from shopping cart
- Checkout and convert shopping cart into an order
- Persistent shopping cart stored in the database

### Administrator Features

Administrators have additional permissions to:

- Create categories
- Update categories
- Delete categories
- Create products
- Update products
- Delete products
- Manage inventory and stock quantities

---

## Technologies Used

### Backend

- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Security
- JWT Authentication
- Hibernate

### Database

- MySQL

### Frontend

- HTML5
- CSS3
- JavaScript

### Build Tool

- Maven

---

## Project Structure

```
src/main/java/org/yearup
│
├── controllers/
│   ├── AuthenticationController
│   ├── CategoriesController
│   ├── ProductsController
│   ├── ProfileController
│   ├── ShoppingCartController
│   └── OrdersController
│
├── service/
│   ├── CategoryService
│   ├── ProductService
│   ├── ProfileService
│   ├── ShoppingCartService
│   ├── OrderService
│   └── UserService
│
├── repository/
│   ├── CategoryRepository
│   ├── ProductRepository
│   ├── ProfileRepository
│   ├── ShoppingCartRepository
│   ├── OrderRepository
│   └── OrderLineItemRepository
│
├── models/
│   ├── Product
│   ├── Category
│   ├── Profile
│   ├── CartItem
│   ├── ShoppingCart
│   ├── ShoppingCartItem
│   ├── Order
│   ├── OrderLineItem
│   └── User
│
└── security/
    ├── JWTFilter
    └── TokenProvider
```

---

## REST API Endpoints

### Authentication

| Method | Endpoint | Description |
|----------|-----------------|----------------|
| POST | `/login` | Authenticate user |
| POST | `/register` | Register a new user |

---

### Products

| Method | Endpoint | Description |
|----------|----------------------|----------------------|
| GET | `/products` | Get all products |
| GET | `/products/{id}` | Get product by ID |
| POST | `/products` | Create product (Admin) |
| PUT | `/products/{id}` | Update product (Admin) |
| DELETE | `/products/{id}` | Delete product (Admin) |

Supports query parameters:

```
/products?cat=1
/products?minPrice=50
/products?maxPrice=200
/products?subCategory=Blue
```

---

### Categories

| Method | Endpoint |
|----------|------------------------|
| GET | `/categories` |
| GET | `/categories/{id}` |
| GET | `/categories/{id}/products` |
| POST | `/categories` (Admin) |
| PUT | `/categories/{id}` (Admin) |
| DELETE | `/categories/{id}` (Admin) |

---

### Profile

| Method | Endpoint |
|----------|---------------|
| GET | `/profile` |
| PUT | `/profile` |

Authenticated users can retrieve and update their own profile information.

---

### Shopping Cart

| Method | Endpoint |
|----------|-------------------------------|
| GET | `/cart` |
| POST | `/cart/products/{productId}` |
| PUT | `/cart/products/{productId}` |
| DELETE | `/cart` |

Shopping carts are tied to the authenticated user and persist in the database.

---

### Orders

| Method | Endpoint |
|----------|--------------|
| POST | `/orders` |

Checkout converts the current shopping cart into an order and clears the cart upon successful completion.

---

## Authentication & Authorization

The application uses JWT-based authentication.

### Public Endpoints

- Login
- Register
- Product browsing
- Category browsing

### Authenticated Users

- Manage profile
- Manage shopping cart
- Checkout

### Administrators

- Create products
- Update products
- Delete products
- Create categories
- Update categories
- Delete categories

Role-based authorization is enforced using Spring Security annotations.

---

## Database Entities

- Users
- Profiles
- Categories
- Products
- Shopping Cart
- Cart Items
- Orders
- Order Line Items

Relationships are managed through JPA and persisted in MySQL.

---

## Shopping Cart Workflow

1. User logs in.
2. User adds products to their cart.
3. Cart items are stored in the `shopping_cart` table.
4. Returning users retain their cart contents.
5. Updating quantity modifies the existing cart record.
6. Clearing the cart removes all records associated with the current user.

---

## Checkout Workflow

1. User submits a checkout request.
2. The system verifies the cart is not empty.
3. A new order is created.
4. Each cart item becomes an order line item.
5. The shopping cart is cleared.
6. The completed order is saved in the database.

---

## Error Handling

The API returns appropriate HTTP status codes, including:

| Status Code | Meaning |
|------------|------------------------------|
| `200 OK` | Request completed successfully |
| `201 Created` | Resource created successfully |
| `204 No Content` | Resource deleted successfully |
| `400 Bad Request` | Invalid request |
| `401 Unauthorized` | Authentication required |
| `403 Forbidden` | Insufficient permissions |
| `404 Not Found` | Resource not found |
| `409 Conflict` | Duplicate resource |
| `500 Internal Server Error` | Unexpected server error |

---

## Future Improvements

- Product image uploads
- Order history endpoint
- Wishlist functionality
- Product reviews and ratings
- Discount and coupon system
- Inventory alerts
- Email confirmations
- Admin dashboard analytics
- Enhanced search and filtering
- Responsive mobile-first UI improvements

---

## Learning Outcomes

This project demonstrates proficiency in:

- RESTful API design
- Spring Boot development
- Layered architecture (Controller → Service → Repository)
- Spring Security and JWT authentication
- CRUD operations
- JPA/Hibernate persistence
- MySQL database integration
- Exception handling and HTTP status codes
- Full-stack application development
- Object-oriented programming principles

---

## Author
Michael Okeke
