# Run 

DOCKER_IMAGE_NAME := ghcr.io/relbot/backend
GIT_COMMIT := $(shell git rev-parse HEAD)


ifndef DEBUG
.SILENT: 
endif

.DEFAULT_GOAL := all

.PHONY: all
all: setup compile docker env-dev

.PHONY: help
help:
	echo CURRENT_COMMIT ${CURRENT_COMMIT}

.PHONY: setup
setup:
	echo "=> setting up..."
	mvn install
	echo "=> done"

.PHONY: compile
compile:
	mvn compile


.PHONY: docker
docker:
	echo "=> build docker image..."
	docker build -f docker/Dockerfile . --build-arg "GIT_COMMIT=${GIT_COMMIT}" -t  ${DOCKER_IMAGE_NAME}:latest -t  ${DOCKER_IMAGE_NAME}:${GIT_COMMIT}
	echo "=> done"


### Deployments
DEV_DIR := deployments/dev
DEV_FILE_PATH := ${DEV_DIR}/docker-compose.yaml
DEV_ENV_FILE := ${DEV_DIR}/.env

.PHONY: env-dev
env-dev:
	docker-compose \
	--project-directory ${DEV_DIR} \
	--env-file ${DEV_ENV_FILE} \
	--file   ${DEV_FILE_PATH} up  --remove-orphans   


INTEGRATION_DIR := deployments/integration
INTEGRATION_FILE_PATH := ${INTEGRATION_DIR}/docker-compose.yaml
INTEGRATION_ENV_FILE := ${INTEGRATION_DIR}/.env


.PHONY: env-integration
env-integration:
	docker-compose \
	--project-directory ${INTEGRATION_DIR} \
	--env-file ${INTEGRATION_ENV_FILE} \
	--file   ${INTEGRATION_FILE_PATH} up  --remove-orphans   

