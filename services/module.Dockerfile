FROM maven:3.9.11-amazoncorretto-24-debian AS build
ARG MODULE_PATH
WORKDIR /app

COPY services ./services

RUN mvn -q -ntp -B -U -f services/parents/client-parent/pom.xml install -DskipTests
RUN mvn -q -ntp -B -U -f services/parents/dto-parent/pom.xml    install -DskipTests
RUN mvn -q -ntp -B -U -f services/parents/svc-parent/pom.xml    install -DskipTests

RUN mvn -q -ntp -B -U -pl ${MODULE_PATH} -am -f services/pom.xml dependency:go-offline
RUN mvn -q -ntp -B    -DskipTests package -pl ${MODULE_PATH} -am -f services/pom.xml

RUN ls -lah services/${MODULE_PATH}/target


FROM amazoncorretto:24.0.2-alpine AS run
ARG MODULE_PATH
WORKDIR /app
RUN apk add --no-cache curl
RUN addgroup --system spring && adduser --system --ingroup spring spring
USER spring:spring
COPY --from=build /app/services/${MODULE_PATH}/target/${MODULE_PATH}.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

