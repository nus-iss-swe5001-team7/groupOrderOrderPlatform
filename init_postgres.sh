#!/bin/bash

# Define variables
PGHOST="localhost"
PGPORT="5432"
PGUSER="postgres"
PGPASSWORD="password"
SQLFILE="/docker-entrypoint-initdb.d/create_db_and_user.sql"

# Export password so psql can use it without prompting
export PGPASSWORD=$PGPASSWORD

# Execute the SQL script
psql -h $PGHOST -p $PGPORT -U $PGUSER -f $SQLFILE
