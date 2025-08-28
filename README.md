# Kafka Spring Boot Application

A comprehensive Spring Boot application demonstrating Apache Kafka integration with producer and consumer functionality.

## Overview

This application showcases a complete Kafka implementation using Spring Boot, featuring message production, consumption, and REST API endpoints for Kafka operations. It includes Docker Compose setup for local Kafka infrastructure and comprehensive test coverage.

## Features

- 🚀 **Kafka Producer Service** - Send messages to Kafka topics
- 📡 **Kafka Consumer Service** - Consume messages from Kafka topics
- 🌐 **REST API** - HTTP endpoints for sending messages
- 🐳 **Docker Compose** - Local Kafka and Zookeeper setup
- 🧪 **Comprehensive Testing** - Unit and integration tests with embedded Kafka
- ⚙️ **Configurable** - External configuration via application.yml

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Kafka**
- **Apache Kafka**
- **Maven**
- **Docker & Docker Compose**
- **JUnit 5**

## Project Structure

```
kafka-spring-boot-app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/kafka/
│   │   │       ├── KafkaSpringBootAppApplication.java
│   │   │       ├── config/
│   │   │       │   └── KafkaConfig.java
│   │   │       ├── controller/
│   │   │       │   └── KafkaController.java
│   │   │       └── service/
│   │   │           ├── KafkaProducerService.java
│   │   │           └── KafkaConsumerService.java
│   │   └── resources/
│   │       └── application.yml
│   └── test/
│       └── java/
│           └── com/example/kafka/
│               ├── KafkaApplicationTest.java
│               └── KafkaIntegrationTest.java
├── docker-compose.yml
├── pom.xml
└── README.md
```

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker and Docker Compose

## Quick Start

### 1. Clone the Repository

```bash
git clone <repository-url>
cd kafka-spring-boot-app
```

### 2. Start Kafka Infrastructure

Start Kafka and Zookeeper using Docker Compose:

```bash
docker-compose up -d
```

This will start:
- **Zookeeper** on port `2181`
- **Kafka** on port `9092`

### 3. Build and Run the Application

```bash
mvn clean install
mvn spring-boot:run
```

The application will start on port `8082`.

## API Endpoints

### Send Message

Send a simple message to the Kafka topic:

```bash
POST /api/kafka/send?message=Hello World
```

Example:
```bash
curl -X POST "http://localhost:8082/api/kafka/send?message=Hello%20Kafka"
```

### Send Message with Key

Send a message with a specific key:

```bash
POST /api/kafka/send/{key}?message=Hello World
```

Example:
```bash
curl -X POST "http://localhost:8082/api/kafka/send/user123?message=Hello%20User"
```

## Configuration

### Application Configuration (`application.yml`)

```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
    consumer:
      group-id: sample-group
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer

server:
  port: 8082

logging:
  level:
    com.example.kafka: INFO
```

### Key Configuration Properties

- **bootstrap-servers**: Kafka broker connection string
- **group-id**: Consumer group identifier
- **auto-offset-reset**: Strategy for handling missing offsets
- **serializers/deserializers**: Message format handling

## Core Components

### KafkaConfig (`src/main/java/com/example/kafka/config/KafkaConfig.java`)

Central configuration class that sets up:
- Producer factory and KafkaTemplate
- Consumer factory and listener container factory
- Connection properties and serialization settings

### KafkaProducerService (`src/main/java/com/example/kafka/service/KafkaProducerService.java`)

Service responsible for:
- Sending messages to the `sample-topic`
- Supporting both key-value and value-only messages
- Logging message production activities

### KafkaConsumerService (`src/main/java/com/example/kafka/service/KafkaConsumerService.java`)

Service that:
- Listens to the `sample-topic`
- Processes incoming messages
- Logs consumption activities

### KafkaController (`src/main/java/com/example/kafka/controller/KafkaController.java`)

REST controller providing:
- `/api/kafka/send` - Send message endpoint
- `/api/kafka/send/{key}` - Send message with key endpoint

## Testing

The application includes comprehensive test coverage:

### Running Tests

```bash
mvn test
```

### Test Types

1. **KafkaApplicationTest**: Tests REST API endpoints with embedded Kafka
2. **KafkaIntegrationTest**: Tests end-to-end message flow between producer and consumer

### Test Features

- Embedded Kafka broker for isolated testing
- REST API endpoint testing
- Producer-consumer integration testing
- Automatic test cleanup with `@DirtiesContext`

## Docker Compose Setup

The included `docker-compose.yml` provides:

- **Zookeeper**: Cluster coordination service
- **Kafka Broker**: Message streaming platform
- **Proper networking**: Container communication setup
- **Port mapping**: External access to services

### Docker Commands

```bash
# Start services
docker-compose up -d

# View logs
docker-compose logs -f kafka

# Stop services
docker-compose down

# Stop and remove volumes
docker-compose down -v
```

## Development

### Building the Project

```bash
mvn clean compile
```

### Running Tests

```bash
mvn test
```

### Creating JAR

```bash
mvn clean package
```

### Running the JAR

```bash
java -jar target/kafka-spring-boot-app-0.0.1-SNAPSHOT.jar
```

## Monitoring and Debugging

### Application Logs

The application logs Kafka operations at INFO level. Check logs for:
- Message production activities
- Message consumption activities
- Connection status

### Kafka Topic Management

Create topics manually if needed:

```bash
# Connect to Kafka container
docker exec -it kafka bash

# Create topic
kafka-topics --create --topic sample-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

# List topics
kafka-topics --list --bootstrap-server localhost:9092

# Describe topic
kafka-topics --describe --topic sample-topic --bootstrap-server localhost:9092
```

### Consumer Groups

Monitor consumer group status:

```bash
kafka-consumer-groups --bootstrap-server localhost:9092 --describe --group sample-group
```

## Troubleshooting

### Common Issues

1. **Connection Refused**: Ensure Kafka is running (`docker-compose up -d`)
2. **Topic Not Found**: Topics are auto-created, but check Kafka logs if issues persist
3. **Port Conflicts**: Ensure ports 2181, 9092, and 8082 are available

### Verifying Setup

1. Check Docker containers are running:
   ```bash
   docker-compose ps
   ```

2. Test Kafka connectivity:
   ```bash
   docker exec kafka kafka-topics --list --bootstrap-server localhost:9092
   ```

3. Test application health:
   ```bash
   curl http://localhost:8082/api/kafka/send?message=test
   ```

## License

This project is licensed under the MIT License.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## Additional Resources

- [Apache Kafka Documentation](https://kafka.apache.org/documentation/)
- [Spring Kafka Reference](https://docs.spring.io/spring-kafka/docs/current/reference/html/)
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)