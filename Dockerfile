# Imagen base de Java con JDK 17
FROM eclipse-temurin:17-jdk-alpine

# Directorio de la aplicación
WORKDIR /app

# Copiar el jar empaquetado
COPY target/nisum-user-registration-1.0.0.jar app.jar

# Exponer el puerto 8080
EXPOSE 8080

# Comando para iniciar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
