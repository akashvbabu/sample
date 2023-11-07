## Sample News Service

Serves latest news using keyword and/or title from GNews API

### Dependencies Required
```
java17 - Download from https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html
```
### Building the service
```
./gradlew clean build
```

### Running the service
Replace `API_KEY` with your gnews.io apikey in `application.properties` and run the following command
```
java -jar ./build/libs/sample-0.0.1-SNAPSHOT.jar
```

### OpenAPI/Swagger Docs
```
http://localhost:8080/api/v1/swagger-ui/index.html - UI
http://localhost:8080/api/v1/api-docs - openapi 3.0 spec
```

### Sample Requests
Run the service using the above reference and execute the following http requests using curl for smoke tests
```
curl --location 'localhost:8080/api/v1/news?keyword=norway' //keyword only
curl --location 'localhost:8080/api/v1/news?title=iron%20dome' //title only
curl --location 'localhost:8080/api/v1/news?title=iron%20dome&keyword=norway' //keyword and title
curl --location 'localhost:8080/api/v1/news/headlines' // headlines
```