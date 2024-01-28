# Credit Card Service

## Details
- Credit Card Service is a microservice that collates financial products from a small selection of partners. 
- The application has been built using Java and Spring Boot Framework. 
- The service returns back a list of credit card recommendations from two providers when a valid request is made. 
- Errors are logged when there are exceptions.

## Installation

````
# Pull project from repository
git clone https://github.com/PamelaUjah/credit-cards-service.git

# Build project
mvn clean package

# Run
./start.sh
````

#### Make POST Request
- Send POST request to **/api/v1/creditcards**
- Send JSON body with the following format:

````
{
  "name": "Janet Lovelace",
  "creditScore": 401,
  "salary": 87000
}
````
#### Responses
- 200 OK response will be returned in case of a successful response.
- 400 response will be returned in the case of invalid parameters.

### Change Environment Variables
Please note .env file has been included in project files.

- To change HTTP Port, please modify .env file and replace **HTTP_PORT** environment variable with available port.
- To change CS Cards URL, please modify **CSCARDS_ENDPOINT** environment variable.
- To change Scored Cards URL, please modify **SCOREDCARDS_ENDPOINT** environment variable.

### Additional Notes
Please note the following areas where the application could be developed further:
  - The use of retries using exponential backoff would be beneficial to handle server errors better. 
  - More testcases could be added to test for 404 Not Found and server errors when making requests to external APIs.
  - Profiles for dev and production could be added to application.yml to change configurations based on environment
