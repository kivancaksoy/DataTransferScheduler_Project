FROM openjdk:17 as build

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src

ARG DB_URL_ARG="jdbc:postgresql://host.docker.internal:5432/DataTransferDB"
ARG SCHEDULER_ARG="http://host.docker.internal:9090/v1/baseCustomer/getAllCustomerWithVersion?version="

ENV DB_URL=$DB_URL_ARG
ENV SCHEDULER_SERVICE_URL=$SCHEDULER_ARG

RUN ./mvnw package

FROM openjdk:17
WORKDIR datatransfersheduler
COPY --from=build target/*.jar datatransfersheduler.jar

ARG DB_URL_ARG="jdbc:postgresql://host.docker.internal:5432/DataTransferDB"
ARG SCHEDULER_ARG="http://host.docker.internal:9090/v1/baseCustomer/getAllCustomerWithVersion?version="

ENV DB_URL=$DB_URL_ARG
ENV SCHEDULER_SERVICE_URL=$SCHEDULER_ARG

ENTRYPOINT ["java", "-jar", "datatransfersheduler.jar"]