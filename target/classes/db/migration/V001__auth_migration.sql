CREATE TABLE user(
	id BIGINT NOT NULL AUTO_INCREMENT,
	first_name VARCHAR(60) NOT NULL,
	last_name VARCHAR(60) NOT NULL,
 	email VARCHAR(60) NOT NULL,
 	password VARCHAR(255) NOT NULL,
 	phone VARCHAR(60) NOT NULL,
 	birth_date DATE NOT NULL,
	created_at TIMESTAMP (0) NOT NULL,
	updated_at TIMESTAMP (0) NOT NULL,
 	PRIMARY KEY (id)
);

CREATE TABLE role(
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(40) NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE permission(
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(40) NOT NULL,
	description VARCHAR(255),
	PRIMARY KEY(id)
);

CREATE TABLE user_role(
	user_id BIGINT NOT NULL,
	role_id BIGINT NOT NULL
);

ALTER TABLE user_role ADD CONSTRAINT fk_user_role_user_id FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE user_role ADD CONSTRAINT fk_user_role_role_id FOREIGN KEY (role_id) REFERENCES role (id);

CREATE TABLE role_permission(
	role_id BIGINT NOT NULL,
	permission_id BIGINT NOT NULL
);

ALTER TABLE role_permission ADD CONSTRAINT fk_role_permission_role_id FOREIGN KEY (role_id) REFERENCES role (id);

ALTER TABLE role_permission ADD CONSTRAINT fk_role_permission_permission_id FOREIGN KEY (permission_id) REFERENCES permission (id);