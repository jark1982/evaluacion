# 📌 Proyecto Spring Boot 3.5 con Maven, Swagger, Lombok, Circuit Breaker, Retry y Docker

Este es un proyecto basado en **Spring Boot 3.5** con las siguientes características:

- 🚀 **Maven** como herramienta de construcción.
- 🛠️ **Lombok** para simplificar la gestión de modelos.
- 📜 **Swagger** para documentación de API interactiva.
- 🔄 **Circuit Breaker y Retry** usando Resilience4j para mayor tolerancia a fallos.
- ❤️ **Health Check** con Actuator para monitoreo del sistema.
- 🐳 **Docker** para despliegue en contenedores.
- 🔧 **Configuración en YAML** 

---

## 📌 Requisitos previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- ✅ [JDK 17](https://adoptopenjdk.net/)
- ✅ [Maven 3.8+](https://maven.apache.org/download.cgi)
- ✅ [Docker](https://www.docker.com/get-started) (opcional para despliegue en contenedores)

---

## 🚀 Instalación y ejecución

##### 🔹 1. Clonar el repositorio

	git clone https://github.com/jark1982/evaluacion.git

##### 🔹 2. Compilar y construir el proyecto

	mvn clean install
	
##### 🔹 3. Ejecutar la aplicación localmente

	mvn spring-boot:run

### 📌 Acceso a la API y documentación
📢 Una vez que la aplicación esté corriendo, puedes acceder a los siguientes recursos:

📡 API Base: http://localhost:8080/api

📖 Swagger UI: http://localhost:8080/swagger-ui/index.html

🏥 Health Check: http://localhost:8080/actuator/health

#### 🐳 Uso de Docker
🔹 1. Construir la imagen de Docker

	docker build -t spring-boot-app .
🔹 2. Ejecutar el contenedor

	docker run -p 8080:8080 spring-boot-app

🔧 Configuración personalizada

	Puedes modificar los valores en el archivo application.yml para personalizar la configuración del proyecto:
	yaml
	server:
	  port: 8080
	
	spring:
	  application:
	    name: spring-boot-app
	
	management:
	  endpoints:
	    web:
	      exposure:
	        include: health, info
### 🛠️ Pruebas
Para ejecutar las pruebas unitarias:

	mvn test
	
### 🔍 Circuit Breaker y Retry
Este proyecto usa Resilience4j para manejar fallos en servicios externos.
Los tiempos de reintento y la lógica de circuit breaker se configuran en application.yml.

##### Ejemplo de configuración:

	yaml
	resilience4j:
	  circuitbreaker:
	    instances:
	      miServicio:
	        failureRateThreshold: 50
	        waitDurationInOpenState: 10s
	        permittedNumberOfCallsInHalfOpenState: 2
	  retry:
	    instances:
	      miServicio:
	        maxAttempts: 3
	        waitDuration: 2s

### 📜 Licencia
Este proyecto se distribuye bajo la licencia MIT.

✨ Autor: Juan Rios C

📧 Contacto: jarc82@gmail.com

📌 Repositorio: https://github.com/jark1982/evaluacion



