# Mini Product Inventory API — Projet G2

Projet académique L3 GL — Architecture microservices avec Spring Boot, Nexus, Docker et Ansible.

---

## Architecture

```
product-core  ──────►  Nexus (maven-releases)
                              │
                              ▼
product-api  ──────►  Docker Hub  ──────►  VM Ubuntu (Ansible)
```

| Module | Rôle | Port |
|---|---|---|
| `product-core` | Bibliothèque métier (lib JAR) | — |
| `product-api` | REST API Spring Boot | 8086 |

---

## product-core

**Coordonnées Maven :**
```xml
<groupId>sn.isi.l3gl.core</groupId>
<artifactId>product-core</artifactId>
```

**Entité :** `Product` (id, name, quantity, price)

**Services disponibles :**

| Version | Méthode | Branche Git |
|---|---|---|
| 0.0.1-SNAPSHOT | `createProduct()` | feature/create-product |
| 0.1.0-SNAPSHOT | `listProducts()` | feature/list-products |
| 0.2.0-SNAPSHOT | `updateQuantity()` | feature/update-quantity |
| 0.3.0-SNAPSHOT | `countLowStockProducts()` | feature/count-low-stock |
| **0.3.0** (RELEASE) | — | tag v0.3.0 |

**Déploiement sur Nexus :**
```bash
mvn clean deploy -DskipTests
```

---

## product-api

**Coordonnées Maven :**
```xml
<groupId>sn.isi.l3gl.api</groupId>
<artifactId>product-api</artifactId>
<version>0.3.0</version>
```

**Dépendances principales :**
- `product-core:0.3.0` (depuis Nexus)
- Spring Boot Web
- Spring Data JPA
- MySQL Connector

### Endpoints REST

Base URL : `http://localhost:8086`

| Méthode | URL | Description |
|---|---|---|
| POST | `/api/products` | Créer un produit |
| GET | `/api/products` | Lister tous les produits |
| PUT | `/api/products/{id}?quantity=X` | Mettre à jour la quantité |
| GET | `/api/products/low-stock/count` | Compter les produits en stock faible |

### Exemple de requête POST

```json
POST{
  "name": "Laptop",
  "description": "PC portable",
  "price": 850.0,
  "quantity": 10
}
```

---

## Configuration

### application.properties

```properties
server.port=8086
spring.datasource.url=jdbc:mysql://host.docker.internal:3308/product_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

---

## Docker

### Image Docker Hub
```
mdamalick/product-api:latest
mdamalick/product-api:latest
```

### Build & Push

```bash
mvn clean package -DskipTests
docker build -t mdamalick/product-api:latest .
docker login
docker push mdamalick/product-api:latest
---------------------
mvn clean package -DskipTests
docker build -t christianwdck/product-api:latest .
docker login
docker push christianwdc/product-api:latest
```

### Lancer le conteneur

```bash
docker run --name devopsExam -p 8086:8086 mdamalick/product-api:latest
docker run -d --name product-api --restart always -p 8086:8086 -e SPRING_DATASOURCE_URL="jdbc:mysql://192.168.1.108:3307/product_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true" -e SPRING_DATASOURCE_USERNAME=root -e SPRING_DATASOURCE_PASSWORD="Wadja12345?!" christianwdc/product-api:latest
```

### Dockerfile

```dockerfile
# Étape 1 : Build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY settings.xml /root/.m2/settings.xml
RUN mvn package -DskipTests

# Étape2 : Runtime
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=build /app/target/product-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "app.jar"]

## Ansible

Déploiement automatisé sur VM Ubuntu depuis WSL.

```bash
ansible-playbook -i inventory.ini playbook.yaml
```

---

## Prérequis

- Java 17
- Maven 3.x
- MySQL 8.0 (port 3307)
- Docker Desktop
- Nexus Repository Manager (localhost:8081)
- Ansible (WSL)

---

## Auteur
**christianwdc** -L3 Génie Logiciel
**mdamalick** — L3 Génie Logiciel
