CREATE TABLE extension(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(5) NOT NULL
);

CREATE TABLE directory (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  parent_id INT NOT NULL,
  FOREIGN KEY (parent_id) REFERENCES directory(id)
);

CREATE TABLE file (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  extension_id INT NOT NULL,
  directory_id INT NOT NULL,
  size INT NOT NULL,
  created_at DATETIME NOT NULL DEFAULT NOW(),
  updated_at DATETIME NOT NULL DEFAULT NOW(),
  FOREIGN KEY (extension_id) REFERENCES extension(id),
  FOREIGN KEY (directory_id) REFERENCES directory(id)
);