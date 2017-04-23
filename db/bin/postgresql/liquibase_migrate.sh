#! /bin/bash

# liquibase --changeLogFile=changelog.xml migrate

DB_HOST=$1
DB_NAME=$2
DB_PORT=$3

if [ "$DB_PORT" == "" ]
  then
    DB_PORT=5432
fi

URL="jdbc:postgresql://$DB_HOST:$DB_PORT/$DB_NAME"
echo $URL

PATH=$PATH:~/apps/liquibase-3.5.3-bin
liquibase  --logLevel=debug --url=$URL  migrate
