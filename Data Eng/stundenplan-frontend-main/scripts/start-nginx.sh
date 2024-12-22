#!/bin/sh
# taken from 
# https://developers.redhat.com/blog/2021/03/04/making-environment-variables-accessible-in-front-end-containers#inject_the_environment_variables
# Takes the config json file and converts its to a "environment var" aware variable
# which can be used to inject environment information

export EXISTING_VARS="$(printenv | awk -F= '{print $1}' | sed 's/^/\$/g' | paste -sd,)";
for file in $JSFOLDER;
do
  cat "$file" | envsubst "$EXISTING_VARS" > "$file.tmp" && mv "$file.tmp" "$file" 
done

# Call the original entrypoint script to process templates (Added by minf104071)
/docker-entrypoint.sh "$@"
