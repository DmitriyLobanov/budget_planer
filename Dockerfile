FROM openjdk:11
ADD /target/budget_planer-0.0.1-SNAPSHOT.jar budget_planer.jar
ENTRYPOINT ["java", "-jar", "budget_planner.jar"]

