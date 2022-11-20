FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /opt/gym-buddy

COPY pom.xml .
COPY backend/pom.xml backend/
COPY frontend/pom.xml frontend/
COPY frontend/*.json frontend/
# TODO: Add npm install here as well
RUN mvn dependency:go-offline

COPY backend/src backend/src
COPY frontend/src frontend/src
COPY frontend/public frontend/public
COPY frontend/index.html frontend/index.html

RUN mvn package

FROM gcr.io/distroless/java17-debian11
ENV PORT 8000
EXPOSE $PORT

COPY --from=build /opt/gym-buddy/backend/target/backend-1.0-SNAPSHOT.jar gym-buddy.jar
CMD ["gym-buddy.jar"]
