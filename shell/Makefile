## /opt/njnu-classroom/Makefile

env=
# 可设置变量或从命令行传递, 命令行传递会覆盖变量
ifndef env
$(error "Undefined property: env, as can be used as 'make env='.")
else ifeq (${env}, dev)
branch="develop"
else ifeq (${env}, prd)
branch="master"
else
$(error "Unresolved property: env, which should be in [dev, prd].")
endif
# service=
# 从命令行传递, such as: make service="spider"
ifndef service
else ifeq (${service}, spider)
else ifeq (${service}, portal)
else ifeq (${service}, core)
else ifeq (${service}, explore)
else ifeq (${service}, gateway)
else
$(error "Unresolved property: service, which should be in [spider, portal, core, explore].")
endif

## github仓库
url = "https://github.com/Repigeons/njnu-classroom-server.git"
## gitee镜像
# url = "https://gitee.com/Repigeons/njnu-classroom-server.git"

.PHONY: publish
publish: .init
	# Update code
	@git init /opt/njnu-classroom-server.git
	@cd /opt/njnu-classroom-server.git && git fetch ${url} --depth=1 ${branch} && git reset --hard FETCH_HEAD
	# Compile & Package
	@cd /opt/njnu-classroom-server.git && mvn clean package -Dmaven.test.skip=true -am -pl "njnu-classroom-${service}"
	# Build docker image & Startup
	@docker compose -f /opt/njnu-classroom-server.git/shell/docker-compose.build.yaml build "${service}_${env}"
	@docker compose -f /opt/njnu-classroom-server.git/shell/docker-compose.build.yaml push "${service}_${env}"

all: .init
	# Update code
	@git init /opt/njnu-classroom-server.git
	@cd /opt/njnu-classroom-server.git && git fetch ${url} --depth=1 ${branch} && git reset --hard FETCH_HEAD
	# Compile & Package
	@cd /opt/njnu-classroom-server.git && mvn clean package -Dmaven.test.skip=true
	# Build docker image & Startup
	@docker compose -f /opt/njnu-classroom-server.git/shell/docker-compose.build.yaml build
	@docker compose -f /opt/njnu-classroom-server.git/shell/docker-compose.build.yaml push

init: .init
	# Initialized.
.init:
	# Initializing...
	@git init /opt/njnu-classroom-server.git
	@cd /opt/njnu-classroom-server.git && git fetch ${url} --depth=1 ${branch} && git reset --hard FETCH_HEAD
	@docker build -f /opt/njnu-classroom-server.git/njnu-classroom-spider/base.Dockerfile -t njnu-classroom.io/njnu-classroom-spider:base /opt/njnu-classroom
	# Completed
	@touch .init
