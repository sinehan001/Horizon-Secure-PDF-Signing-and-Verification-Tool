# Horizon Secure PDF Signing and Verification Tool

## Overview

The Horizon Secure PDF Signing and Verification Tool empower users to sign and verify PDF files securely using the SHA256 ECDSA (Elliptic Curve Digital Signature Algorithm) cryptographic algorithm. Ensure the authenticity and integrity of your PDF documents with this reliable and easy-to-use tool.

## Getting Started

Follow the steps below to seamlessly integrate the Horizon Secure PDF Signing and Verification Tool into your workflow.

### Prerequisites

Make sure your system meets the following requirements:

- [JDK 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [MongoDB](https://www.mongodb.com/try/download/community)


## Installation

### Method 1

1. Clone the Horizon repository to your local machine:

   ```bash
   git clone https://github.com/sinehan001/Horizon-Secure-PDF-Signing-and-Verification-Tool.git
   cd horizon-pdf-signing
   ```

2. Clean, Build and run the application:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

3. Access the application:

Visit http://localhost:8080 in your web browser.

### Method 2
 
 1. Install the Intellij IDEA

   - [Intellij IDEA](https://www.jetbrains.com/idea/)

 2. Setup the Intellij IDEA

 3. Search **Git** in the Toolbar (or) Search **Help -> Git** in Toolbar 

 4. Find the **GitHub -> Create Pull Request**, then Enter the Git URL - https://github.com/sinehan001/Horizon-Secure-PDF-Signing-and-Verification-Tool.git, click OK, Then click on Create Pull Request.

 5. Once, all files has been pulled from GitHub, Click the **Maven** tab, which is located top right side of the page.

 6. Then **Package Name -> LifeCycle**, inside that click **clean**, once the project files has been clean, then click on **install**.

 7. Then, click ▶ to run the project, To access the application: Visit http://localhost:8080 in your web browser.

## Dependencies

### Spring Boot Starter Web

Starter for building web, including RESTful, applications using Spring MVC.

   ```bash 
   <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
   </dependency>
   ```

### Spring Boot Starter Data MongoDB

Starter for using MongoDB as the data store.

   ```bash 
   <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-mongodb</artifactId>
   </dependency>
   ```

### MongoDB Java Driver

The MongoDB Java Driver is used for connecting to the MongoDB database.

   ```bash 
	<dependency>
		<groupId>org.mongodb</groupId>
		<artifactId>mongodb-driver-sync</artifactId>
	</dependency>
   ```

### Thymeleaf

Thymeleaf, a server-side Java-based template engine for web and standalone applications, with Spring Boot.

   ```bash
   <dependency>
		<groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-thymeleaf</artifactId>
	</dependency>
   ```

### Bouncy Castle

Bouncy Castle is included for cryptographic functionalities.

   ```bash 
   <dependency>
      <groupId>org.bouncycastle</groupId>
      <artifactId>bcprov-jdk15on</artifactId>
      <version>1.69</version> <!-- Adjust the version as needed -->
   </dependency>
   ```

## Application Structure

- `src/`: Contains the source code of the Spring Boot application.

- `pom.xml`: Maven Project Object Model file that contains project configuration information
## URL Reference

#### Sign the PDF

```http
  GET http://localhost:8080/
```

#### Verify the PDF

```http
  GET http://localhost:8080/check
```
## Authors

- [@sinehan001](https://www.github.com/sinehan001)


## Contributing

Feel free to contribute to the project by opening issues or submitting pull requests. Your feedback is highly appreciated!


## License 

[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)


This project is licensed under the [MIT License](https://choosealicense.com/licenses/mit/). See the LICENSE file for details.



## FAQ

#### 1. Is this app safe to use?

Yes, it is entirely safe to use this application.

#### 2. Is it free or does it cost anything to use?

It is completely free to use.

#### 3. Where the PDF files are stored?

Signed PDF files are stored on the Server, But they will automatically deleted within an hour.

#### 4. Is the data stored in database safe?
Absolutely! The signed PDF file data are encrypted and stored inside the MongoDB database. So, Don't worry about the security.


## Feedback

If you have any feedback, please reach out to us at sinehan22@gmail.com

