FROM maven:3.9.9-eclipse-temurin-21 AS provider-build
WORKDIR /workspace
COPY keycloak-provider/pom.xml .
COPY keycloak-provider/src ./src
RUN mvn -DskipTests clean package

FROM quay.io/keycloak/keycloak:26.2
USER root
RUN mkdir -p /opt/keycloak/providers/ /opt/keycloak/data/import/

# Copy compiled jar
COPY --from=provider-build /workspace/target/keycloak-provider-1.0.0-jar-with-dependencies.jar /opt/keycloak/providers/

RUN chown -R keycloak:keycloak /opt/keycloak/providers/ /opt/keycloak/data/import/
USER keycloak

ENV KC_HEALTH_ENABLED=true
ENV KC_METRICS_ENABLED=true
ENV KC_HOSTNAME_STRICT=false
ENV KC_BOOTSTRAP_ADMIN_USERNAME=admin
ENV KC_BOOTSTRAP_ADMIN_PASSWORD=admin123

# Environment variables for your Custom User Provider (DatabaseUtil.java)
ENV USER_STORAGE_JDBC_URL=jdbc:postgresql://host.docker.internal:5432/training_auth
ENV USER_STORAGE_JDBC_USER=postgres
ENV USER_STORAGE_JDBC_PASSWORD=123321

EXPOSE 8080
ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start-dev"]