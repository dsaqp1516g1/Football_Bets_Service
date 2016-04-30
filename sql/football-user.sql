drop user 'football'@'localhost';
create user 'football'@'localhost' identified by 'football';
grant all privileges on football.* to 'football'@'localhost';
flush privileges;
