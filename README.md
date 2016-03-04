# Bands and Venues - Many to Many

#### A list of bands and venues and their relations

#### By Jacob Steinberg

## Description

This band/venue app allows your to organize your favorite band and venues, and list which bands have played at which venues and vice versa.

## Setup/Installation Requirements


In PSQL:
```
CREATE DATABASE band_venues;
CREATE TABLE bands (id serial PRIMARY KEY, name varchar);
CREATE TABLE venues (id serial PRIMARY KEY, name varchar);
CREATE TABLE bands_venues (id serial PRIMARY KEY, band_id int, venue_id int);
```

Clone this repository:
```
$ cd ~/Desktop
$ git clone https://github.com/jakemento/band_venues.git
$ cd folder-name
```

Open terminal and run Postgres:
```
$ postgres
```

Open a new tab in terminal and create the `band_venues` database:
```
$ psql
$ CREATE DATABASE band_venues;
$ psql band_venues < band_venues.sql
```

Navigate back to the directory where this repository has been cloned and run gradle:
```
$ gradle run
```
## Known Bugs



## Technologies Used

* Java
* junit
* Gradle
* Spark
* fluentlenium
* PostgreSQL
* Bootstrap

### License

Licensed under the GPL.

Copyright (c) 2016 **Jacob Steinberg**
