# Customer Satisfaction App Challenge v.1.2 - Back End

_Restfull project developed in Java with Spring Boot, used to list, register and modify evaluations._
* Documentation of rest api with swagger
* Unit and integration testing using maven
* Deployment using docker and docker-compose

## API documentation

_The documentation is generated from the application itself using "SpringFox"._
Once the project is up, you can enter the documentation through the link:
[http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/)


## Invoke Http Rest Api using commands

1. List of evaluations by date range
```
curl -X GET "http://localhost:8080/api/v1/evaluaciones/listadoRangoFechas/19-09-2020/19-09-2020" -H "accept: application/json"
```

2. Register a new evaluation
```
curl -X POST "http://localhost:8080/api/v1/evaluaciones/registros" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"email\": \"string\", \"nombres\": \"string\", \"calificacion\": 0}"
```

3. Modify an evaluation
```
curl -X PUT "http://localhost:8080/api/v1/evaluaciones/modificaciones" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"idEvaluacion\": 1, \"email\": \"string\", \"nombres\": \"string\", \"calificacion\": 5}"
```

## Deployment Docker

_The project is deployed using docker and docker-compose, run the following commands from the root of the project_
```
cd /CustomerSatisfactionAppCallenge
cd apiEvaluacion
mvn clean install
cd ../
docker-compose up -d
```

- The application will be exposed through port 8080, you can view it from the following link [http://localhost:8080/](http://localhost:8080/)

- The collection created in Postman to test the Api Rest are available from the following link [https://www.getpostman.com/collections/ee2ec0456105e589b64e](https://www.getpostman.com/collections/ee2ec0456105e589b64e)


## Built with

_The project is developed in Java using Spring Boot, Mysql database and deployment with docker._

* [Java 1.8](https://www.java.com/es/download/faq/java8.xml/) - Programming language
* [Spring 2.3.4](https://spring.io/blog/2020/09/17/spring-boot-2-3-4-available-now/) - Framework
* [Maven](https://maven.apache.org/) - Dependency manager
* [SpringFox 3.0](https://springfox.github.io/springfox/) - Automated JSON API documentation for API's built with Spring
* [SpringFox-Swagger 3.0](https://github.com/springfox/springfox-demos/tree/master/spring-java-swagger) - An java configured spring web mvc app with Swagger2 and Swagger UI
* [Dockerize 0.6.1](https://github.com/jwilder/dockerize/) - Utility to simplify running applications in docker containers


## Mejoras por aplicar:
_En el transcurso del desarrollo del proyecto se detectaron algunas mejoras que quedaron pendientes de aplicar para un siguiente sprint:_
* Listado con paginación
* Clases para el manejo de Excepciones de la aplicación
* Utilizar programación reactiva con Spring WebFlux o RxJava para optimizar las peticiones
* Capa de seguridad para las API utilizando Json Web Tokens
* Agregar comentarios en las clases Java para uso de Javadoc
* Docker, crear volumen de datos para la BD
* Probar la aplicación en Heroku
