# Sample Integration with Elasticsearch Engine in a Spring Boot Project

This project demonstrates a basic integration with Elasticsearch using Spring Boot. It serves as a sample application for testing **Spring Data Elasticsearch** and **ElasticsearchOperations** with custom queries and various search functionalities.

## Overview

The project provides a simple structure for interacting with an Elasticsearch cluster, showcasing:

- **Spring Data Elasticsearch Repositories**: Using built-in repository methods and custom `@Query` annotations.
- **ElasticsearchOperations**: Executing more complex or flexible queries, including bulk operations.
- **Custom Queries**: Fuzzy search and other query types to demonstrate different search capabilities in Elasticsearch.

## Key Features

- **Bulk Indexing**: Adds multiple documents to Elasticsearch in a single bulk request.
- **Fuzzy Search**: Finds articles based on approximate matches to the title.
- **Custom Queries**: Uses both repository-based and programmatic query building with `ElasticsearchOperations`.
- **Basic CRUD Operations**: Demonstrates indexing, searching, and deleting documents in Elasticsearch.

## Endpoints

The following endpoints are exposed by the `ArticleController`:

| HTTP Method | Endpoint                     | Description                                        |
|-------------|------------------------------|----------------------------------------------------|
| `POST`      | `/api/v1/articles/bulk`      | Bulk indexes a list of articles.                   |
| `DELETE`    | `/api/v1/articles`           | Deletes a specific article by request body.        |
| `GET`       | `/api/v1/articles/authors`   | Finds articles by author’s name with pagination.   |
| `GET`       | `/api/v1/articles/authors/custom` | Custom query to find articles by author’s name. |
| `GET`       | `/api/v1/articles/title/fuzzy` | Fuzzy search by title (repository).              |
| `GET`       | `/api/v1/articles/title/fuzzy/operations` | Fuzzy search by title using ElasticsearchOperations. |

## Setup and Requirements

For building and running the application you need:
- [JDK 17](https://www.oracle.com/java/technologies/downloads/#java17)
- [Maven 3.6](https://maven.apache.org)
- [Elasticsearch](https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html)

## Running the Application

1. Clone the repository.
2. Update the Elasticsearch configuration in `application.properties` if necessary:
   ```properties
   spring.elasticsearch.uris=http://localhost:9200



## Running the Project
1. Run Elasticsearch cluster. The easiest way is to install ES with Docker. Start a single-node cluster:
```shell
docker network create elastic
```

```shell
docker pull docker.elastic.co/elasticsearch/elasticsearch:8.15.3
```

```shell
docker run --name es01 --net elastic -p 9200:9200 -it -m 1GB docker.elastic.co/elasticsearch/elasticsearch:8.15.3
```

Now it's important to store credentials in environment variable in your shell:
Export your username (by default it's just "elastic"):
```shell
export ELASTIC_USERNAME="your_username"
```
Export your password:
```shell
export ELASTIC_PASSWORD="your_password"
```
Export your HTTP CA certificate SHA-256 fingerprint:
```shell
export ELASTIC_FINGERPRINT="your_fingerprint"
```

2. There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.example.elasticintegration.ElasticIntegrationApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:
```shell
mvn spring-boot:run
```

3. Now you can call endpoint with prompt in body, you can do this in for example in Postman or from terminal using curl:
```shell
curl -X POST http://localhost:8080/api/v1/articles/bulk -H "Content-Type: application/json" -d '[
    {"id": "1", "title": "First Article", "authors": [{"name": "Author1"}]},
    {"id": "2", "title": "Second Article", "authors": [{"name": "Author2"}]}
]'
```

