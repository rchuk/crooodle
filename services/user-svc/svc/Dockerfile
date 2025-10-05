# ---- build stage (JDK 24 + Maven) ----
FROM openjdk:24-jdk-slim AS build
RUN apt-get update && apt-get install -y --no-install-recommends maven && rm -rf /var/lib/apt/lists/*
WORKDIR /repo
COPY . .
# Збираємо весь multi-module, щоб parent-и та артефакти потрапили в ~/.m2
RUN mvn -B -DskipTests -f services/pom.xml install

# ---- runtime stage (JDK 24) ----
FROM openjdk:24-jdk-slim
WORKDIR /app
# Копіюємо зібраний JAR конкретного сервісу
COPY --from=build /repo/services/user-svc/svc/target/*.jar app.jar
# менш привілейований запуск (опційно)
RUN apt-get update && apt-get install -y --no-install-recommends curl && rm -rf /var/lib/apt/lists/*
RUN useradd -ms /bin/bash spring
USER spring
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]
