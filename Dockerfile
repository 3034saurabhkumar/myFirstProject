FROM maven

WORKDIR /app

COPY ./target/myFirstProject-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]