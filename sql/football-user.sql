drop user 'football'@'localhost';
create user 'football'@'localhost' identified by 'football';
grant all privileges on footballdb.* to 'football'@'localhost';
flush privileges;
