FROM openjdk:17-slim

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' > /etc/timezone

ADD /target/njnu-classroom-core-4.0.0.jar /server.jar
