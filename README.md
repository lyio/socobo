[![Build Status](https://travis-ci.org/lyio/socobo.svg?branch=master)](https://travis-ci.org/lyio/socobo)

## Contributing

The only thing required is a current JDK (8 preferably) and SBT installed. 

Run `sbt run` in the root directory and SBT should take care of everything.

Test can be run with `sbt test`. 

Play comes bundled with an in memory database for development. The database connection strings are located in the 
`application.conf` and the in memory db can be swapped out for a PostgreSQL database. 
