FROM jboss/keycloak:latest
COPY realm-export.json /tmp/realm-export.json
CMD ["-b", "0.0.0.0", "-Dkeycloak.profile.feature.upload_scripts=enabled", "-Dkeycloak.import=/tmp/realm-export.json"]
