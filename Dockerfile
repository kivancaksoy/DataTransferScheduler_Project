FROM openjdk:17 as build

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

FROM openjdk:17
WORKDIR datatransfersheduler
COPY --from=build target/*.jar datatransfersheduler.jar
ENTRYPOINT ["java", "-jar", "datatransfersheduler.jar"]