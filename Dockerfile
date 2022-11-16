FROM openjdk:11
ADD target/budget_planer-0.0.1-SNAPSHOT.jar budget_planer-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/budget_planer-0.0.1-SNAPSHOT.jar"]

