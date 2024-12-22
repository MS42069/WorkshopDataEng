# ARGUMENTS
ARG GIT_COMMIT=""
ARG JQ_VERSION=1.6

# First stage to install node packages and build react app
# Can be referenced by using --from=build  
FROM docker.io/node:16-alpine as build
ARG JQ_VERSION

# Create working directory and install node packages to ist
WORKDIR /app
COPY package.json .
COPY package-lock.json .
RUN npm install

# Copy all necessary files & source code
COPY tsconfig.json . 
COPY public public
COPY src src

# Dont let ESLINT bother your build
ENV DISABLE_ESLINT_PLUGIN=true

# Build react app
RUN npm run build
  
######################################################################
###                         NGINX                                  ###
######################################################################

# Nginx build stage does build the main container image to run from   
FROM nginx:mainline-alpine
ENV JSFOLDER=/usr/share/nginx/html/static/js/*.js

# Create working directory '/dozent' to place app into
# Has to be '/dozent' sub directory because the app should run as subpage
WORKDIR /usr/share/nginx/html/dozent/

# Get the build folder from the first step (see above)
COPY --from=build  /app/build .
# Get start script (see script for further information)
COPY scripts/start-nginx.sh .

RUN eval $(awk  '{print "export "$1 "\n"}' .env)
# Use the template configuration to substitute a random port for the upstream backend server
ENV NGINX_ENVSUBST_TEMPLATE_DIR="/etc/nginx/templates"
ENV NGINX_ENVSUBST_TEMPLATE_SUFFIX=".template"
# Output here so we get a new, substituted nginx.conf (entry configuration)
ENV NGINX_ENVSUBST_OUTPUT_DIR="/etc/nginx"
COPY config/nginx.tpl.conf "${NGINX_ENVSUBST_TEMPLATE_DIR}/nginx.conf.template"

RUN chmod +x start-nginx.sh
ENV REACT_APP_BASE_URL=/api/v1

ENTRYPOINT ["./start-nginx.sh"]
CMD ["nginx", "-g", "daemon off;"]
