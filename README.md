# JavaSpringUppgift1

## Description
A minimum viable product for the Java Spring Inlämningsuppgift 1. Created RESTful APIs in Java Spring for a user to create an account and then use that account (as basic authentication) to upload, download, and delete files to the server. This is a similar concept to Dropbox.

## Setup
1. Create a database called "dropbox" in MySQL
2. Import the **database/dropbox_2022-03-11.sql** file from this repository into that database to create necessary tables

## Basic Tests
### Run program
1. Run the **DropboxAppliction.java** file from this repository

### Create User
1. Call GET method on path localhost:8080/user with parameters username and password (no authentication required)
2. Verify that this user now exists in the user table with encrypted password (use this user as basis authentication for the rest of the test calls)

### Upload File
1. Call POST method on path localhost:8080/file with basic authentication (your created user) and with a body parameter of file (can use **test/Gull.jpeg** from this repository as test file)
![image](https://user-images.githubusercontent.com/21995128/159166183-6270a353-6df9-4117-a8f0-4deac1e92703.png)

2. Verify that **userFiles/{user}/{file}** was created in the JavaSpringUppgift1 folder and that it is the same as the file that you uploaded

### View Files
1. Call GET method on path localhost:8080/file/all with basic authentication (your created user)
2. Verify that a String list of uploaded files for this user is returned

### Download File
1. Call GET method on path localhost:8080/file with basic authentication (your created user) and with a parameter of name (your uploaded file)
2. Verify that this file was downloaded to your computer from the server (NOTE: You may need to run this in Google Chrome for the file to download)

### Remove File
1. Call DELETE method on path localhost:8080/file with basic authentication (your created user) and with a parameter of name (your uploaded file)
2. Verify that **userFiles/{user}/{file}** no longer exists in the JavaSpringUppgift1 folder
