FROM amazoncorretto:11

WORKDIR /app

COPY target/startupone-0.0.1-SNAPSHOT.jar /app/startupone.jar

EXPOSE 4000

CMD ["java", "-jar", "startupone.jar"]