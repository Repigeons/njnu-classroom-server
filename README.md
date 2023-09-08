# 南师教室

## v4.0.0 后端

### 打包构建

(1) 下载`shell/Makefile`文件至`/opt/njnu-classroom`目录

```shell
mkdir -p /opt/njnu-classroom/
wget https://github.com/Repigeons/njnu-classroom-server/raw/master/shell/Makefile -O /opt/njnu-classroom/Makefile
```

(2) 初始化构建环境

```shell
cd /opt/njnu-classroom
make init
```

(3) 编译、构建并发布到docker-registry

```shell
make env="prd" service="spider"
# 其中env为环境，可选dev和prd，service为服务名称，包括 spider,portal,core,explore
```

```shell
make all env="prd"  # 可直接发布全部模块
```

### 启动服务

(1) 下载`shell/docker-compose.dev.yaml`或`shell/docker-compose.dev.yaml`文件至`/opt/njnu-classroom`目录

```shell
mkdir -p /opt/njnu-classroom/
wget https://github.com/Repigeons/njnu-classroom-server/raw/master/shell/docker-compose.prd.yaml -O /opt/njnu-classroom/docker-compose.yaml
# 或 wget https://github.com/Repigeons/njnu-classroom-server/raw/master/shell/docker-compose.dev.yaml -O /opt/njnu-classroom/docker-compose.yaml （测试环境）
```

(2) 使用`docker-compose`启动服务

```shell
cd /opt/njnu-classroom
# 启动指定服务
docker compose up -d <service>
# 或直接启动全部服务：
# docker compose up -d
```
