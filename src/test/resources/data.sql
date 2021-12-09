-- DROP TABLE IF EXISTS COMMENTS;
-- DROP TABLE IF EXISTS BOOKS;
-- DROP TABLE IF EXISTS AUTHORS;
-- DROP TABLE IF EXISTS GENRES;
-- CREATE TABLE AUTHORS(ID BIGINT AUTO_INCREMENT PRIMARY KEY, F_NAME VARCHAR(255), L_NAME VARCHAR(255));
-- CREATE TABLE GENRES(ID BIGINT AUTO_INCREMENT PRIMARY KEY, GENRE VARCHAR(255));
-- CREATE TABLE BOOKS(ID BIGINT AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(255), ID_AUTHOR BIGINT, ID_GENRE BIGINT, FOREIGN KEY (ID_AUTHOR) REFERENCES AUTHORS(ID),
-- FOREIGN KEY (ID_GENRE) REFERENCES GENRES(ID));
-- CREATE TABLE COMMENTS(ID BIGINT AUTO_INCREMENT PRIMARY KEY, MESSAGE VARCHAR(1000), DATE_MESSAGE DATE, ID_BOOK BIGINT, FOREIGN KEY (ID_BOOK) REFERENCES BOOKS(ID) ON DELETE CASCADE );


insert into authors (f_name, l_name) values ('Stephen', ' King'),
('Suzanne', 'Collins');
insert into genres (genre) values ('Fantasy'),
('Horror'),
('Teaching');
insert into books (`name`, id_author, id_genre) values ('The Hunger Games', 2, 1),
('Pet Sematary', 1, 1),
('Shine', 1, 2);
insert into comments (message, date_message, id_book) values ('Could not tear myself away', '2019-01-15', 1),
('So the book is great', '2021-09-10', 2),
('I was bored sometimes, but overall ok', '2020-12-03', 3);
insert into users (username, password, `name`) values ('admin', 'password', 'Admin'),
('misha','123', 'Misha');

insert into roles(`name`)
  VALUES ('ROLE_USER'), ('ROLE_ADMIN');
insert into users_roles (user_id, roles_id) values (1, 2), (2, 1);