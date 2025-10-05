# ---- build stage (JDK 24 + Maven) ----
FROM openjdk:24-jdk-slim AS build
RUN apt-get update && apt-get install -y --no-install-recommends maven && rm -rf /var/lib/apt/lists/*
WORKDIR /repo
COPY . .
RUN mvn -B -DskipTests -f services/pom.xml install

# ---- runtime stage (JDK 24) ----
FROM openjdk:24-jdk-slim
WORKDIR /app
COPY --from=build /repo/services/hotel-svc/svc/target/*.jar app.jar
RUN apt-get update && apt-get install -y --no-install-recommends curl && rm -rf /var/lib/apt/lists/*
RUN useradd -ms /bin/bash spring
USER spring
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/app.jar"]
