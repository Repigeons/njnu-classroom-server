## /opt/njnu-classroom/Makefile

env=
# 可设置变量或从命令行传递, 命令行传递会覆盖变量
ifndef env
$(error "Undefined property: env")
else ifeq (${env}, "dev")
branch="develop"
else ifeq (${env}, "prd")
branch="master"
else $(error "Unresolved property: env")
endif
# module=
# 从命令行传递, such as: make module="njnu-classroom-spider"
ifndef module
$(error "Undefined property: module")
else ifeq (${module}, "njnu-classroom-spider")
else ifeq (${module}, "njnu-classroom-portal")
else ifeq (${module}, "njnu-classroom-core")
else ifeq (${module}, "njnu-classroom-explore")
else $(error "Unresolved property: module")
endif

## github仓库
url = "https://github.com/Repigeons/njnu-classroom-server.git"
## gitee镜像
# url = "https://gitee.com/Repigeons/njnu-classroom-server.git"

.PHONY: deploy
deploy: .init
	# Update code
	@git init /opt/njnu-classroom-server.git
	@cd /opt/njnu-classroom-server.git && git fetch ${url} --depth=1 ${branch} && git reset --hard FETCH_HEAD
	# Prepare application.yml before package
	@cp /opt/njnu-classroom/application/spider-${env}.yml  /opt/njnu-classroom-server.git/njnu-classroom-spider/src/main/resources/application-${env}.yml
	@cp /opt/njnu-classroom/application/portal-${env}.yml  /opt/njnu-classroom-server.git/njnu-classroom-portal/src/main/resources/application-${env}.yml
	@cp /opt/njnu-classroom/application/core-${env}.yml    /opt/njnu-classroom-server.git/njnu-classroom-core/src/main/resources/application-${env}.yml
	@cp /opt/njnu-classroom/application/explore-${env}.yml /opt/njnu-classroom-server.git/njnu-classroom-explore/src/main/resources/application-${env}.yml
	# Compile & Package
	@cd /opt/njnu-classroom-server.git && mvn clean kotlin:compile package -D maven.test.skip=true -am -pl ${module}
	# Build docker image & Startup
	@docker-compose -f /opt/njnu-classroom-server.git/docker-compose.yaml up -d --build ${module}

init: .init
	@echo "Initialized."
.init:
	@echo "Initializing..."
	@git clone --single-branch --depth=1 ${url} /opt/njnu-classroom-server.git
	@mkdir -p /opt/njnu-classroom/application
	@cp /opt/njnu-classroom-server.git/njnu-classroom-spider/src/main/resources/application.yml  /opt/njnu-classroom/application/spider.yml
	@cp /opt/njnu-classroom-server.git/njnu-classroom-portal/src/main/resources/application.yml  /opt/njnu-classroom/application/portal.yml
	@cp /opt/njnu-classroom-server.git/njnu-classroom-core/src/main/resources/application.yml    /opt/njnu-classroom/application/core.yml
	@cp /opt/njnu-classroom-server.git/njnu-classroom-explore/src/main/resources/application.yml /opt/njnu-classroom/application/explore.yml
	# Copy docker-compose.yaml
	@cp /opt/njnu-classroom-server.git/docker-compose.yaml /opt/njnu-classroom/
	# Build base image
	@docker build -f /opt/njnu-classroom-server.git/njnu-classroom-spider/base.Dockerfile -t njnu-classroom-spider:base  /opt/njnu-classroom
	@docker-compose -f /opt/njnu-classroom-server.git/docker-compose.yaml build nginx
	# Completed
	@touch .init
