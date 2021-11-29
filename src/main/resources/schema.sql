DROP TABLE IF EXISTS COMMENTS;
DROP TABLE IF EXISTS BOOKS;
DROP TABLE IF EXISTS AUTHORS;
DROP TABLE IF EXISTS GENRES;
DROP TABLE IF EXISTS USERS;
CREATE TABLE AUTHORS(ID BIGINT AUTO_INCREMENT PRIMARY KEY, F_NAME VARCHAR(255), L_NAME VARCHAR(255));
CREATE TABLE GENRES(ID BIGINT AUTO_INCREMENT PRIMARY KEY, GENRE VARCHAR(255));
CREATE TABLE BOOKS(ID BIGINT AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(255), ID_AUTHOR BIGINT, ID_GENRE BIGINT, FOREIGN KEY (ID_AUTHOR) REFERENCES AUTHORS(ID),
FOREIGN KEY (ID_GENRE) REFERENCES GENRES(ID));
CREATE TABLE COMMENTS(ID BIGINT AUTO_INCREMENT PRIMARY KEY, MESSAGE VARCHAR(1000), DATE_MESSAGE DATE, ID_BOOK BIGINT, FOREIGN KEY (ID_BOOK) REFERENCES BOOKS(ID) ON DELETE CASCADE );
CREATE TABLE USERS(ID BIGINT AUTO_INCREMENT PRIMARY KEY, LOGIN VARCHAR(15), PASSWORD VARCHAR(30), NAME VARCHAR(100));

