OfflinePayEngine

A backend-focused offline payment and deferred-settlement engine built with Java, JDBC, MySQL, and Spring Boot.







About

OfflinePayEngine is a learning-focused backend project that models a payment system capable of processing payments and supporting a future offline/deferred-settlement workflow.

The project is designed around backend engineering concepts rather than a frontend:

Transactional money movement

Database consistency

Idempotent payment processing

Concurrency handling

Ledger-based accounting

REST APIs

JPA/Hibernate

Testing

Docker

Security

Future offline/deferred settlement simulation

Note: This is an educational payment-engine simulation. It is not a real UPI implementation and is not connected to any banking or payment network.

Project Goals

The main goal is to build a backend that demonstrates how reliable financial operations can be designed.

Core principles

Correctness over unnecessary complexity

Database consistency

Atomic payment processing

Clear separation of responsibilities

Testable backend components

Understanding the implementation instead of blindly generating code

Current Architecture

The project is being developed incrementally.

Current JDBC architecture

Java
│
▼
Service Layer
│
▼
Repository Layer
│
▼
JDBC
│
▼
MySQL

Payment transaction flow

Payment Request
│
▼
PaymentService
│
├── Validate payment
│
├── Read sender account
├── Read receiver account
│
├── Debit sender
├── Credit receiver
│
├── Create payment record
├── Create debit ledger entry
├── Create credit ledger entry
│
▼
COMMIT

If any operation fails:

Error
│
▼
ROLLBACK
│
▼
No partial payment is saved

Database Design

Current database:

offline_pay_engine
│
├── users
│
├── accounts
│
├── payments
│
└── ledger_entries

Relationships

User
│
└── Account
│
├── Payment
│
└── LedgerEntry

Money representation

Financial amounts use:

MySQL      → DECIMAL(19,2)
Java       → BigDecimal

double is intentionally not used for monetary values.

Current Features

JDBC V1

MySQL database schema

Java domain models

JDBC connection

User repository

Account repository

Payment repository

Ledger repository

Shared JDBC connection for transactions

Transaction management

Commit / rollback

Payment validation

Sender balance validation

Debit / credit processing

Payment record creation

Double-entry-style debit/credit ledger records

End-to-end payment test

Rollback failure test

Roadmap

Phase 1 — JDBC Foundation

Database design

Domain models

JDBC

Repositories

Transactions

Payment processing

Phase 2 — Spring Boot REST API

Spring Boot configuration

REST controllers

Request/response DTOs

Service layer

Exception handling

Validation

API documentation

Phase 3 — JPA / Hibernate

Entity mapping

Relationships

Spring Data JPA

Repository abstraction

Transaction management with @Transactional

Phase 4 — Reliability

Idempotency

Concurrency control

Race-condition testing

State-machine validation

Duplicate payment handling

Phase 5 — Offline / Deferred Settlement

Offline payment simulation

Deferred payment records

Delivery/reconciliation simulation

Retry handling

Settlement workflow

Phase 6 — Production-Oriented Engineering

Spring Security

Automated tests

Docker

CI/CD

Redis where justified

PostgreSQL/MySQL production considerations

Logging and observability

API documentation

Tech Stack

Technology

Purpose

Java

Core backend language

Spring Boot

Backend framework

JDBC

Initial database access layer

MySQL

Relational database

JPA / Hibernate

ORM layer

Maven

Dependency management and build

Git

Version control

GitHub

Source control and portfolio

Docker

Containerization

JUnit

Testing

Postman

API testing

Redis

Optional caching/idempotency support

GitHub Actions

CI/CD

Project Structure

Current structure:

offline-pay-engine/
│
├── database/
│   └── schema.sql
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── offlinepay/
│   │   │           └── engine/
│   │   │               ├── OfflinePayEngineApplication.java
│   │   │               │
│   │   │               ├── model/
│   │   │               │   ├── User.java
│   │   │               │   ├── Account.java
│   │   │               │   ├── Payment.java
│   │   │               │   ├── LedgerEntry.java
│   │   │               │   ├── PaymentStatus.java
│   │   │               │   └── LedgerEntryType.java
│   │   │               │
│   │   │               ├── database/
│   │   │               │   └── DBConnection.java
│   │   │               │
│   │   │               ├── repository/
│   │   │               │   ├── UserRepository.java
│   │   │               │   ├── AccountRepository.java
│   │   │               │   ├── PaymentRepository.java
│   │   │               │   └── LedgerEntryRepository.java
│   │   │               │
│   │   │               └── service/
│   │   │                   └── PaymentService.java
│   │   │
│   │   └── resources/
│   │
│   └── test/
│
├── pom.xml
└── README.md

Example Payment

Suppose:

Alice balance = ₹9,500
Bob balance   = ₹2,500

Alice sends Bob ₹500

After successful processing:

Alice = ₹9,000
Bob   = ₹3,000

The database also records:

Payment
└── ₹500

Ledger
├── Alice → DEBIT  ₹500
└── Bob   → CREDIT ₹500

All of these operations belong to the same database transaction.

Transaction Safety

A payment is treated as an atomic operation.

START TRANSACTION
│
├── Debit sender
├── Credit receiver
├── Create payment
├── Create ledger entries
│
├── Success → COMMIT
│
└── Failure → ROLLBACK

This prevents partial payment states such as:

Alice charged
Bob not credited

Security Notes

Database credentials are not stored directly in source code.

The local MySQL password is loaded through an environment variable:

MYSQL_PASSWORD

Never commit:

Database passwords

API keys

Access tokens

Private credentials

.env files containing secrets

Running Locally

1. Clone the repository

Replace the placeholder with your repository URL:

git clone <YOUR_GITHUB_REPOSITORY_URL>
cd offline-pay-engine

2. Create the database

Create:

offline_pay_engine

Then run:

database/schema.sql

3. Configure MySQL credentials

Set the environment variable:

MYSQL_PASSWORD=<your_mysql_password>

Do not put the password inside the Java source code.

4. Build

mvn clean install

5. Run

mvn spring-boot:run

Testing

The project currently verifies:

Successful payment

Insufficient balance

Invalid payment amount

Self-payment rejection

Missing account handling

Transaction commit

Transaction rollback

Ledger creation

API-level testing will be added during the Spring Boot REST phase.

Learning Resources

Official documentation used during development:

Java Documentation: https://docs.oracle.com/en/java/

JDBC Documentation: https://docs.oracle.com/javase/tutorial/jdbc/

Spring Boot: https://spring.io/projects/spring-boot

Spring Documentation: https://docs.spring.io/

MySQL Documentation: https://dev.mysql.com/doc/

Maven: https://maven.apache.org/

JUnit: https://junit.org/junit5/

Docker: https://docs.docker.com/

Postman: https://learning.postman.com/

Why This Project?

Many beginner backend projects stop at:

CRUD API → Database → Done

OfflinePayEngine is intended to go further into backend engineering:

CRUD
↓
Transactions
↓
Consistency
↓
Idempotency
↓
Concurrency
↓
Failure handling
↓
Deferred settlement
↓
Offline payment simulation

The goal is not to build a banking product, but to use a payment-like domain to demonstrate backend engineering concepts that matter in real systems.

Disclaimer

This project is an educational software-engineering project.

It does not implement real UPI, banking infrastructure, regulated payment processing, or real-world financial settlement.

Author

Shrey

Built as a backend engineering learning project with a focus on:

Java
Spring Boot
MySQL
JPA / Hibernate
Distributed-system concepts
Reliable backend design

License

This project is currently intended as a personal learning and portfolio project.

License details can be added when the repository is made public.