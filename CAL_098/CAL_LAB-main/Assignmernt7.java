<project xmlns="http://maven.apache.org/POM/4.0.0" ...>
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.example</groupId>
  <artifactId>courier-crud</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <name>Courier CRUD</name>
  <properties>
    <java.version>17</java.version>
  </properties>

  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
      <groupId>com.h2database</groupId>
      <artifactId>h2</artifactId>
      <scope>runtime</scope>
    </dependency>
  </dependencies>

  <build>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven

Pom.xml
package com.example.courier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CourierCrudApplication {
    public static void main(String[] args) {
        SpringApplication.run(CourierCrudApplication.class, args);
    }
}


Couriertrack.jaav
package com.example.courier.model;

import jakarta.persistence.*;

@Entity
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trackingNumber;
    private String sender;
    private String receiver;
    private String status;

    // Getters and Setters
}
model courier.java
package com.example.courier.model;

import jakarta.persistence.*;

@Entity
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trackingNumber;
    private String sender;
    private String receiver;
    private String status;

    // Getters and Setters
}

controller.java
