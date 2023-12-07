FROM maven:3.8.6-jdk-11 AS build
COPY src /home/app/src
COPY pom.xml /home/app/
RUN mvn -f /home/app/pom.xml clean install -DskipTests

FROM amazoncorretto:11
COPY opentelemetry-javaagent.jar opentelemetry-javaagent.jar
COPY --from=build /home/app/target/*.jar app.jar

ENV JAVA_TOOL_OPTIONS=-javaagent:opentelemetry-javaagent.jar
ENV OTEL_EXPORTER_OTLP_ENDPOINT=http://collector.text-mining-kp.org:4317
ENV OTEL_METRICS_EXPORTER=none
ENV OTEL_SERVICE_NAME=text-mining-provider-cooccurrence

ENTRYPOINT ["java","-Xmx16G","-jar","/app.jar"]