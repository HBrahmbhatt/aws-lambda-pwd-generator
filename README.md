# Serverless Passive-Aggressive Password Generator

Because "password123" is a cry for help.

This project uses the `aws-serverless-java-container` library to run a full Spring Boot 3 application inside AWS Lambda. It demonstrates how to expose standard HTTP endpoints via AWS Lambda Function URLs without managing a dedicated server or complex API Gateway configuration.

## Features

* **Serverless Architecture:** Runs entirely on AWS Lambda.
* **Stateless:** Pure Java logic with no database dependency.
* **REST API:** Exposes endpoints via standard HTTP methods.
* **Roast Engine:** Returns a commentary string with every generated password.

## Prerequisites

* Java 17
* Maven 3.8+
* AWS Account

## Deployment Guide

Follow these steps to deploy the application manually using the AWS Console.

### 1. Build the Application
Clone the repository and build the deployable zip file using Maven.

```bash
git clone https://github.com/HBrahmbhatt/aws-lambda-pwd-generator
cd aws-lambda-pwd-generator
mvn clean install
```


### 2. Create the Lambda Function
1. Log in to the AWS Management Console and navigate to the **Lambda** service.
2. Click **Create function**.
3. Select **Author from scratch**.
4. Enter a **Function name** (e.g., `password-generator`).
5. Set **Runtime** to **Java 17**.
6. Set **Architecture** to **x86_64** (or arm64 if you configured your build for it).
7. Click **Create function**.

### 3. Upload Code
1. Navigate to the **Code** tab.
2. Click **Upload from** and select **.zip or .jar file**.
3. Upload the zip/JAR file generated in Step 1.
4. Click **Save**.

### 4. Configure Runtime Settings
1. In the **Code** tab, scroll down to **Runtime settings** and click **Edit**.
2. Change the **Handler** to match your bridge class:
   `aws_lambda_springboot.aws_lambda_springboot_project.StreamLambdaHandler::handleRequest`
3. Click **Save**.

### 5. Adjust Memory and Timeout
Spring Boot requires more resources than the default Lambda settings provide.

1. Navigate to the **Configuration** tab.
2. Select **General configuration** from the left menu and click **Edit**.
3. Set **Memory** to **512 MB** (or higher).
4. Set **Timeout** to **30 seconds**.
5. Click **Save**.

### 6. Enable Public Access
1. In the **Configuration** tab, select **Function URL** from the left menu.
2. Click **Create function URL**.
3. Set **Auth type** to **NONE** (allows public access).
4. Click **Save**.

### 7. Test
Copy the **Function URL** provided on the screen and open it in your web browser or Postman app. You should receive a JSON response containing a password and a roast as below.
<img width="1721" height="700" alt="image" src="https://github.com/user-attachments/assets/0f3d101d-5bee-4072-ba1b-56cdd707cb6f" />

