FROM eclipse-temurin:21-jre
WORKDIR /app

COPY build/libs/*.jar app.jar

EXPOSE 8080
ARG SPRING_PROFILE=staging
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILE}

ENTRYPOINT ["java","-jar","/app/app.jar"]