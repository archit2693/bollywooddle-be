# AS <NAME> to name this stage as maven
FROM maven:3.6.3 AS maven
LABEL MAINTAINER="archit2693.aj@gmail.com"

WORKDIR /usr/src/app
COPY . /usr/src/app
# Compile and package the application to an executable JAR
RUN mvn -Dmaven.test.skip=true package

# For Java 16,
FROM --platform=linux/amd64 openjdk:16

ARG JAR_FILE=bollywoodle-be.jar

WORKDIR /opt/app

# Copy the bollywoodle-be.jar from the maven stage to the /opt/app directory of the current stage.
COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app/

ENTRYPOINT ["java","-jar","bollywoodle-be.jar"]