FROM openjdk:17

ARG APP_VERSION=*

# Metadata
LABEL org.label-schema.name="springboot-multiple-datasource" \
      org.label-schema.description="This is sample Spring Boot project with multiple data store"\
      org.label-schema.vendor="cuizhanming.com"\
      org.label-schema.vcs-url="https://github.com" \
      org.label-schema.docker.dockerfile="/Dockerfile" \
      org.label-schema.docker.params="MYSQL_ENDPOINT=MySQL URI and port" \
      org.label-schema.docker.cmd="docker run -d --name famunity-multiple-data-source \
                                   -p 8080:8080 \
                                   -e MYSQL_ENDPOINT=\"127.0.0.1:3306/test\" \
                                   -e MYSQL_ENDPOINT_OPTION=\"useSSL=false\" \
                                   famunity/famunity-multiple-data-source"

COPY target/springboot-multiple-datasource-${APP_VERSION}-exec.jar /springboot-multiple-datasource.jar

EXPOSE 8080

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/springboot-multiple-datasource.jar"]