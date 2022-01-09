FROM openjdk:8-jre-alpine

RUN mkdir /app

WORKDIR /app

ADD ./target/ui-1.0.0-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "ui-1.0.0-SNAPSHOT.jar"]