FROM maven:3.6.3-jdk-11 AS build
WORKDIR /opt/gym-buddy

COPY pom.xml .
COPY backend/pom.xml backend/
COPY frontend/pom.xml frontend/
COPY frontend/*.json frontend/
RUN mvn dependency:go-offline

COPY backend/src backend/src
COPY frontend/src frontend/src
COPY frontend/public frontend/public

RUN mvn package

FROM gcr.io/distroless/java:11
ENV PORT 8000
EXPOSE $PORT

COPY --from=build /opt/gym-buddy/backend/target/backend-1.0-SNAPSHOT.jar gym-buddy.jar
CMD ["gym-buddy.jar"]
