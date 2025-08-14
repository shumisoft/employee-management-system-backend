FROM eclipse-temurin:21-jdk-alpine-3.22
ENV PORT=1337
EXPOSE 1337
COPY /target/employee-management-system-0.0.1-SNAPSHOT.jar Employee-Management-System.jar
ENTRYPOINT ["java", "-jar", "Employee-Management-System.jar"]