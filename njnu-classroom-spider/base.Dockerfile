# docker build -f base.Dockerfile -t njnu-classroom-spider:base .
FROM openjdk:17-slim

RUN sed -i "s@http://deb.debian.org@https://mirrors.tuna.tsinghua.edu.cn@g" /etc/apt/sources.list \
 && sed -i "s@http://ftp.debian.org@https://mirrors.tuna.tsinghua.edu.cn@g" /etc/apt/sources.list \
 && sed -i "s@http://security.debian.org@https://mirrors.tuna.tsinghua.edu.cn@g" /etc/apt/sources.list \
 && apt-get update \
 && apt-get -y install chromium chromium-driver \
 && apt-get clean all
