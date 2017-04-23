#! /bin/bash

DB_HOST=$1
DB_NAME=$2
DB_PORT=$3

if [ "$DB_PORT" == "" ]
  then
    DB_PORT=5432
fi

URL="jdbc:postgresql://$DB_HOST:$DB_PORT/$DB_NAME"
echo $URL

dropdb -h $DB_HOST -U dbuser $DB_NAME
