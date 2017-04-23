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

cd ../data/development
psql -h $DB_HOST -p $DB_PORT -U dbuser -d $DB_NAME -f runall.sql
