CREATE TABLE main
(
url varchar(255) NOT NULL,
deviceid varchar(16) NOT NULL,
words varchar(255),
img boolean,
video boolean,
audio boolean,
link boolean,
active boolean,
sha varchar(40),
primary key (url, deviceid)
);