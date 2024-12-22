#!/bin/sh
# Pls Dont add Carriage return in file
export FILES=$(grep -El -e 'process\.env\.([a-zA-Z_0-9-]+)' $(find .  -not -path "./node_modules/*" -not -path "./build/*"  -name "*.tsx"  -o -not -path "./node_modules/*" -not -path "./build/*" -name '*.ts'))
for file in $FILES
do

  echo "=> Found env access for file: $file" 
  cat "$file" | sed -E -e 's/process\.env\.([a-zA-Z_0-9-]+)/"$\1"/g' | tee  "$file.tmp"; mv "$file.tmp" "$file"
done

# sed -E -e 's/process.env.([^,.]*)/"$\1"/g' "$1"
# grep -El -e 'process\.env\.([^.,]+)' $(./find .  -regextype posix-extended  -regex '.*\.(tsx|ts|js|jsx)' -not -path "./node_modules/*" -not -path "./build/*")
#  find .  -regextype posix-extended  -regex '.*\.(tsx|ts|js|jsx)' -not -path "./node_modules/*" -not -path "./build/*"