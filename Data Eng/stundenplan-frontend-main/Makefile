# Run 

DOCKER_IMAGE_NAME := ghcr.io/relbot/frontend
GIT_COMMIT := $(shell git rev-parse HEAD)


ifndef DEBUG
.SILENT: 
endif

.DEFAULT_GOAL := all

all: setup build docker env-dev

.PHONY: help
help:
	echo CURRENT_COMMIT ${CURRENT_COMMIT}

.PHONY: setup
setup:
	echo "=> setting up..."
	npm install
	echo "=> done"

.PHONY: build
build:
	npm run build


.PHONY: start
start:
	npm run start


.PHONY: docker
docker:
	echo "=> build docker image..."
	docker build --progress=plain  --build-arg "GIT_COMMIT=${GIT_COMMIT}"   -f docker/Dockerfile . -t  ${DOCKER_IMAGE_NAME}:latest -t  ${DOCKER_IMAGE_NAME}:${GIT_COMMIT}
	echo "=> done"


### Environment Development
DEV_DIR := deployments/dev
DEV_FILE_PATH := ${DEV_DIR}/docker-compose.yaml
DEV_ENV_FILE := ${DEV_DIR}/.env
NETWORK := net_frontend_softwareprojekt

BACKEND_DB_HOST := postgres
BACKEND_DB_PORT :=5432
BACKEND_DB_USER := db_username
BACKEND_DB_PASSWORD := db_password
BACKEND_DB_DRIVER := postgresql
BACKEND_DB_NAME := postgres
MIGRATE_DSN := 'postgres://${BACKEND_DB_USER}:${BACKEND_DB_PASSWORD}@${BACKEND_DB_HOST}:${BACKEND_DB_PORT}/${BACKEND_DB_NAME}?sslmode=disable'


.PHONY: env-dev
env-dev: docker-pull-latest up
	echo 
	echo 
	echo
	echo '=> Run "make stop" to stop the docker compose file'
	echo '=> Run "make clean" to remove the docker compose file'

.PHONY: docker-pull-latest
docker-pull-latest:
	docker pull ghcr.io/relbot/backend:latest

.PHONY: migrate-down
migrate-down:
	docker run --rm \
	--network ${NETWORK} \
	--mount 'type=bind,source=${PWD}/migrations,target=/migrations' \
	--env-file ${DEV_ENV_FILE} migrate/migrate:4 \
	--database ${MIGRATE_DSN} --path="/migrations/" down -all

.PHONY: migrate-up
migrate-up:
	docker run --rm	\
	--network ${NETWORK} \
	--mount 'type=bind,source=${PWD}/migrations,target=/migrations' \
	--env-file ${DEV_ENV_FILE} migrate/migrate:4 \
	--database ${MIGRATE_DSN} --path="/migrations/" up 



.PHONY: up
up:
	docker-compose \
	--project-directory ${DEV_DIR} \
	--env-file  ${DEV_ENV_FILE}  \
	--file  ${DEV_FILE_PATH} \
	up \
	--detach \
	--remove-orphans \
	--force-recreate

.PHONY: clean down
down clean:
	docker-compose \
	--project-directory ${DEV_DIR} \
	--env-file  ${DEV_ENV_FILE}  \
	--file  ${DEV_FILE_PATH} \
	down \
	--remove-orphans \
	--volumes

.PHONY: stop
stop:
	docker-compose \
	--project-directory ${DEV_DIR} \
	--env-file  ${DEV_ENV_FILE}  \
	--file  ${DEV_FILE_PATH} \
	stop 


### Environment Integration


