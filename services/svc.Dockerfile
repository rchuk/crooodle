# syntax=docker/dockerfile-upstream:master-labs

FROM maven:3.9.11-amazoncorretto-24-debian AS build

ARG MODULE_PATH

WORKDIR /app

COPY --parents pom.xml **/pom.xml ./
RUN --mount=type=cache,dst=/root/.m2 \
    mvn -q -ntp -B -pl ${MODULE_PATH} -am dependency:go-offline -f services/pom.xml

COPY services ./services
RUN --mount=type=cache,dst=/root/.m2 \
    mvn -B -DskipTests package -pl ${MODULE_PATH} -am -f services/pom.xml

FROM amazoncorretto:24.0.2-alpine AS run

ARG MODULE_PATH

WORKDIR /app

RUN addgroup --system spring && adduser --system --ingroup spring spring
USER spring:spring

COPY --from=build /app/services/${MODULE_PATH}/target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
