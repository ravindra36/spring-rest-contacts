DROP TABLE IF EXISTS contacts;
DROP TABLE IF EXISTS contact_details;
DROP TABLE IF EXISTS addresses;
DROP TABLE IF EXISTS phones;


CREATE TABLE contacts (
  id BIGINT AUTO_INCREMENT  PRIMARY KEY,
  email VARCHAR(250) NOT NULL
);

CREATE TABLE contact_details (
  id BIGINT AUTO_INCREMENT  PRIMARY KEY,
  first VARCHAR(250) NOT NULL,
  middle VARCHAR(250),
  last VARCHAR(250) NOT NULL,
  contact_id BIGINT NOT NULL 
);

alter table contact_details add constraint cdt_ct foreign key (contact_id) references contacts(id);

CREATE TABLE addresses (
  id BIGINT AUTO_INCREMENT  PRIMARY KEY,
  street VARCHAR(250) NOT NULL,
  city VARCHAR(250) NOT NULL,
  state VARCHAR(250) NOT NULL,
  zip VARCHAR(250) NOT NULL, 
  contact_id BIGINT NOT NULL 
  
);

alter table addresses add constraint adr_ct foreign key (contact_id) references contacts(id);


CREATE TABLE phones (
  id BIGINT AUTO_INCREMENT  PRIMARY KEY,
  number VARCHAR(250) NOT NULL,
  type VARCHAR(250) NOT NULL  
  
);

