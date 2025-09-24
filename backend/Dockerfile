FROM maven:3.9.11-amazoncorretto-24-debian AS build

WORKDIR /app
COPY pom.xml .
RUN mvn -B -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -B -DskipTests package

FROM amazoncorretto:24.0.2-alpine AS run

WORKDIR /app

RUN apk add --no-cache curl

RUN addgroup --system spring && adduser --system --ingroup spring spring
USER spring:spring

COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]
