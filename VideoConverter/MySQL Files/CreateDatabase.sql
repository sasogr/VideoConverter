# Creates the database for the application
CREATE DATABASE videoconverter;

USE videoconverter;

#Creates the user table
CREATE TABLE USER(
	username VARCHAR(255) not NULL,
	firstName VARCHAR(255),
	lastName VARCHAR(255), 
	password VARCHAR(255),
	birthdate VARCHAR(255),
	email VARCHAR(255),
	PRIMARY KEY (username)
); 

#remove birthdate column
ALTER TABLE USER
DROP COLUMN birthdate

#facebookID column added
ALTER TABLE USER
ADD facebookID VARCHAR(255)