# ARGUMENTS
ARG GIT_COMMIT=""
ARG JQ_VERSION=1.6


FROM docker.io/node:16-alpine as build
ARG JQ_VERSION


RUN wget --no-check-certificate https://github.com/stedolan/jq/releases/download/jq-${JQ_VERSION}/jq-linux64 -O /tmp/jq-linux64
RUN cp /tmp/jq-linux64 /usr/bin/jq
RUN chmod +x /usr/bin/jq


WORKDIR /app
COPY package.json .
COPY package-lock.json .
RUN npm install

#COPY .eslintrc.yml .
COPY .prettierrc.json .
COPY tsconfig.json .

COPY public public
COPY src src

# Replace every entry of config.json with environment variable replacement
# taken from: https://developers.redhat.com/blog/2021/03/04/making-environment-variables-accessible-in-front-end-containers#inject_the_environment_variables


# RUN jq 'to_entries | map_values({ (.key) : ("$" + .key) }) | reduce .[] as $item ({}; . + $item)' ./src/config.json > ./src/config.tmp.json && mv ./src/config.tmp.json ./src/config.json

COPY ./scripts/replace.env.sh replace.env.sh
# Wenn es irgendwie nicht funktioniert dann muss die 755 durch -x wieder ersetzt werden
RUN chmod 755 ./replace.env.sh
RUN ./replace.env.sh


ENV DISABLE_ESLINT_PLUGIN=true
RUN npm run build

# ENV NODE_ENV=production
# ENTRYPOINT ["npm", "start"]

######################################################################
###                         NGINX                                  ###
######################################################################

FROM docker.io/nginx:1.22.1-alpine
ENV JSFOLDER=/usr/share/nginx/html/static/js/*.js

ARG GIT_COMMIT

WORKDIR /usr/share/nginx/html
COPY --from=build  /app/build .
COPY scripts/start-nginx.sh .

ENV NGINX_ENVSUBST_TEMPLATE_DIR="/etc/nginx/templates"
ENV NGINX_ENVSUBST_TEMPLATE_SUFFIX=".template"
ENV NGINX_ENVSUBST_OUTPUT_DIR="/etc/nginx"
COPY .env .
RUN eval $(awk  '{print "export "$1 "\n"}' .env)
RUN rm /etc/nginx/conf.d/default.conf  
COPY config/nginx.conf "${NGINX_ENVSUBST_TEMPLATE_DIR}/nginx.conf.template"

RUN chmod +x start-nginx.sh
ENV VERSION=${GIT_COMMIT}
ENV REACT_APP_BASE_URL=/api/v1


LABEL git_commit="${GIT_COMMIT}"


ENTRYPOINT ["./start-nginx.sh"]
CMD ["nginx", "-g", "daemon off;"]  
