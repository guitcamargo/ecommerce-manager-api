FROM gradle:8.13-jdk21 AS build
WORKDIR /app

COPY . .

RUN ./gradlew clean build -x test

FROM openjdk:21-jdk
WORKDIR /app

COPY --from=build /app/build/libs/ecommerce-manager-api-1.0.0.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]