CREATE TABLE Stadium (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    city VARCHAR(255),
    address VARCHAR(255),
    constructionYear INT,
    capacity DOUBLE
);

CREATE table Auth 
(   
    username VARCHAR(50), 
    password VARCHAR(50), 
    PRIMARY KEY (username) 
);

INSERT INTO Auth (username, password) VALUES ('amine', 'amine');

