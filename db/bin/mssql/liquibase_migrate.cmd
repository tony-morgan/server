rem liquibase --changeLogFile=changelog.xml migrate

set DBSERVER=%1%

set DB=%2%

set PORT=%3%

IF "%PORT%"=="" (
  SET PORT=1433
)

set URL="jdbc:sqlserver://%DBSERVER%:%PORT%;databaseName=%DB%"

echo %URL%

liquibase  --url=%URL%  migrate