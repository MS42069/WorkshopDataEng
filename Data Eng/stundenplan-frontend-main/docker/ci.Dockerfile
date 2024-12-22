# This Dockerfile is intended to be used in the CI/CD pipeline and excludes the build step
FROM nginx:mainline-alpine
ENV JSFOLDER=/usr/share/nginx/html/static/js/*.js

ARG GIT_COMMIT

WORKDIR /usr/share/nginx/html
COPY build .
COPY scripts/start-nginx.sh .

COPY .env .
RUN eval $(awk  '{print "export "$1 "\n"}' .env)
# Use the template configuration to substitute a random port for the upstream backend server
ENV NGINX_ENVSUBST_TEMPLATE_DIR="/etc/nginx/templates"
ENV NGINX_ENVSUBST_TEMPLATE_SUFFIX=".template"
# Output here so we get a new, substituted nginx.conf (entry configuration)
ENV NGINX_ENVSUBST_OUTPUT_DIR="/etc/nginx"
COPY config/nginx.tpl.conf "${NGINX_ENVSUBST_TEMPLATE_DIR}/nginx.conf.template"

RUN chmod +x start-nginx.sh
ENV VERSION=${GIT_COMMIT}
ENV REACT_APP_BASE_URL=/api/v1

LABEL git_commit="${GIT_COMMIT}"

ENTRYPOINT ["./start-nginx.sh"]
CMD ["nginx", "-g", "daemon off;"]
