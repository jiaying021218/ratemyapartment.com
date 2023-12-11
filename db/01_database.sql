-- This file is the database for ratemyapartment

-- Create the database
DROP DATABASE IF EXISTS ratemyapartment;
CREATE DATABASE ratemyapartment;

-- Grant privileges to 'webapp' user
CREATE USER IF NOT EXISTS 'springapp'@'%';
GRANT ALL PRIVILEGES ON ratemyapartment.* TO 'springapp'@'%';
FLUSH PRIVILEGES;

-- Use the database
USE ratemyapartment;

-- -------------------------------------
-- Table: Apartment
-- -------------------------------------
CREATE TABLE IF NOT EXISTS Apartment (
    -- Identification
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    -- Apartments address
    address_line_1 VARCHAR(255) NOT NULL,
    address_line_2 VARCHAR(255),
    city VARCHAR(50) NOT NULL,
    state VARCHAR(2) NOT NULL,
    zip_code VARCHAR(10) NOT NULL,
    neighborhood VARCHAR(50) DEFAULT NULL,
    -- Rating
    average_rating FLOAT(3,2) DEFAULT NULL,
    -- State
    status ENUM('Submitted', 'Hold', 'Approved', 'Rejected') NOT NULL DEFAULT 'Submitted', -- rating is submitted on default and only have 4 status
    status_note TEXT DEFAULT NULL,
    -- Timestamp
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    -- Unique constraint
    UNIQUE (name, address_line_1, city, state), -- unique apartment at same address to prevent duplication
    -- Indices
    INDEX idx_city (city),
    INDEX idx_state (state),
    INDEX idx_neighborhood (neighborhood)
);

-- -------------------------------------
-- Table: Apartment_Profile_Image
-- -------------------------------------
CREATE TABLE IF NOT EXISTS Apartment_Profile_Image(
    -- Identification
    id INT AUTO_INCREMENT PRIMARY KEY,
    apartment_id INT UNIQUE, -- each apartment can only have 1 profile image
    image_path VARCHAR(512) NOT NULL,
    -- Timestamp
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- -------------------------------------
-- Table: Apartment_Detail_Image
-- -------------------------------------
CREATE TABLE IF NOT EXISTS Apartment_Detail_Image(
    -- Identification
    id INT AUTO_INCREMENT PRIMARY KEY,
    apartment_id INT NOT NULL,
    image_path VARCHAR(512) NOT NULL,
    -- Timestamp
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- -------------------------------------
-- Table: Rating
-- -------------------------------------
CREATE TABLE IF NOT EXISTS Rating (
    -- Identification
    id INT AUTO_INCREMENT PRIMARY KEY,
    apartment_id INT NOT NULL,
    -- Rating
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    value INT NOT NULL CHECK (value BETWEEN 1 AND 5), -- rating can only be between 1 to 5
    would_recommend ENUM('Yes', 'No') NOT NULL, -- rating can only be recommended or not recommended
    -- State
    status ENUM('Submitted', 'Hold', 'Approved', 'Rejected') NOT NULL DEFAULT 'Submitted', -- rating is submitted on default and only have 4 status
    status_note TEXT DEFAULT NULL,
    -- Timestamp
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- -------------------------------------
-- Table: Rating_Images
-- -------------------------------------
CREATE TABLE IF NOT EXISTS Rating_Image (
    -- Identification
    id INT AUTO_INCREMENT PRIMARY KEY,
    rating_id INT NOT NULL,
    image_path VARCHAR(512) NOT NULL,
    -- Timestamp
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- -------------------------------------
-- Table: Tag
-- -------------------------------------
CREATE TABLE IF NOT EXISTS Tag (
    -- Identification
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) UNIQUE,
    positive_negative ENUM('Positive', 'Negative') NOT NULL, -- tag can only be positive or negative
    -- Timestamp
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- -------------------------------------
-- Table: Rating_Tag
-- -------------------------------------
CREATE TABLE IF NOT EXISTS Rating_Tag (
    -- Identification
    rating_id INT NOT NULL,
    tag INT NOT NULL,
    -- Timestamp
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    -- Unique constraint
    UNIQUE (rating_id, tag) -- rating cannot have duplicated tags
);

-- -------------------------------------
-- Table: Admin
-- -------------------------------------
CREATE TABLE IF NOT EXISTS Admin (
    -- Identification
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone VARCHAR(12) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    -- Login information
    password VARCHAR(255) NOT NULL, -- TODO
    -- Timestamp
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- -------------------------------------
-- Foreign Key
-- -------------------------------------
ALTER TABLE Apartment_Profile_Image
ADD FOREIGN KEY (apartment_id) REFERENCES Apartment(id) ON DELETE CASCADE;

ALTER TABLE Apartment_Detail_Image
ADD FOREIGN KEY (apartment_id) REFERENCES Apartment(id) ON DELETE CASCADE;

ALTER TABLE Rating
ADD FOREIGN KEY (apartment_id) REFERENCES Apartment(id) ON DELETE CASCADE;

ALTER TABLE Rating_Image
ADD FOREIGN KEY (rating_id) REFERENCES Rating(id) ON DELETE CASCADE;

ALTER TABLE Rating_Tag
ADD FOREIGN KEY (rating_id) REFERENCES Rating(id) ON DELETE CASCADE,
ADD FOREIGN KEY (tag) REFERENCES Tag(id) ON DELETE CASCADE;

-- -------------------------------------
-- Trigger for each rating having at most 3 positive tags and 3 negative tags
-- -------------------------------------
DELIMITER //
CREATE TRIGGER check_tag_count_before_insert BEFORE INSERT ON Rating_Tag
FOR EACH ROW
BEGIN
  DECLARE positive_count INT;
  DECLARE negative_count INT;

  SELECT COUNT(*) INTO positive_count
  FROM Rating_Tag
  JOIN Tag ON Rating_Tag.tag = Tag.name
  WHERE Rating_Tag.rating_id = NEW.rating_id AND Tag.positive_negative = 'Positive';

  SELECT COUNT(*) INTO negative_count
  FROM Rating_Tag
  JOIN Tag ON Rating_Tag.tag = Tag.name
  WHERE Rating_Tag.rating_id = NEW.rating_id AND Tag.positive_negative = 'Negative';

  IF (NEW.tag IN (SELECT name FROM Tag WHERE positive_negative = 'Positive') AND positive_count >= 3) THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'A rating can have at most 3 positive tags';
  END IF;

  IF (NEW.tag IN (SELECT name FROM Tag WHERE positive_negative = 'Negative') AND negative_count >= 3) THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'A rating can have at most 3 negative tags';
  END IF;

END;
//
DELIMITER ;
-- -------------------------------------
-- Trigger for each rating having at most 5 pictures
-- -------------------------------------
DELIMITER //
CREATE TRIGGER check_image_count_before_insert BEFORE INSERT ON Rating_Image
FOR EACH ROW
BEGIN
  DECLARE image_count INT;

  SELECT COUNT(*) INTO image_count
  FROM Rating_Image
  WHERE Rating_Image.rating_id = NEW.rating_id;

  IF (image_count >= 5) THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'A rating can have at most 5 images';
  END IF;

END;
//
DELIMITER ;
-- -------------------------------------
-- Trigger for each apartment having at most 10 detailed pictures
-- -------------------------------------
DELIMITER //
CREATE TRIGGER check_detail_image_count_before_insert BEFORE INSERT ON Apartment_Detail_Image
FOR EACH ROW
BEGIN
  DECLARE image_count INT;

  SELECT COUNT(*) INTO image_count
  FROM Apartment_Detail_Image
  WHERE Apartment_Detail_Image.apartment_id = NEW.apartment_id;

  IF (image_count >= 10) THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'An apartment can have at most 10 detail images';
  END IF;

END;
//
DELIMITER ;

-- -------------------------------------
-- Inserting the Tag
-- -------------------------------------
INSERT INTO Tag (name, positive_negative)
VALUES
('Convenient Location', 'Positive'),
('Poor Location', 'Negative'),
('Safe Neighborhood', 'Positive'),
('Sketchy Neighborhood', 'Negative'),
('Accessible Gym', 'Positive'),
('No Gym', 'Negative'),
('In-Unit Laundry', 'Positive'),
('Inaccessible Laundry', 'Negative'),
('24/7 Doorman', 'Positive'),
('No Doorman', 'Negative'),
('Daytime Doorman', 'Positive'),
('Amazing View', 'Positive'),
('Pet Friendly', 'Positive'),
('Disabled Accessible', 'Positive'),
('Built-In A/C', 'Positive'),
('No A/C', 'Negative'),
('Concierge', 'Positive');

-- -------------------------------------
-- Inserting the Apartment
-- -------------------------------------
INSERT INTO Apartment (name, address_line_1, city, state, zip_code, neighborhood, status)
VALUES
('Greenhouse Apartment', '150 Huntington Ave', 'Boston', 'MA', '02115', 'Suffolk', 'Approved'),
('Pierce Boston', '200 Brookline Ave', 'Boston', 'MA', '02215', 'Fenway', 'Approved'),
('Church Park Apartment', '211 Massachusetts Ave', 'Boston', 'MA', '02115', 'Fenway', 'Approved'),
('Van Ness', '1325 Boylstion St', 'Boston', 'MA', '02215', 'Fenway', 'Submitted'),
('345 Harrison', '345 Harrison Ave', 'Boston', 'MA', '02118', 'South Bay', 'Submitted'),
('The Viridian', '1282 Boylston St', 'Boston', 'MA', '02215', 'Fenway', 'Submitted');

-- -------------------------------------
-- Inserting the Rating
-- -------------------------------------
INSERT INTO Rating (apartment_id, title, description, value, would_recommend, status)
VALUES
(1, 'Nice Place', 'The apartment was spacious and clean.', 4, 'Yes', 'Submitted'),
(1, 'Great Location', 'Loved the proximity to public transit.', 5, 'Yes', 'Submitted'),
(1, 'Noisy Neighbors', 'Had some trouble with loud neighbors.', 2, 'No', 'Submitted'),
(1, 'Affordable', 'Reasonable price for the location.', 4, 'Yes', 'Submitted'),
(1, 'Old Building', 'The building is a bit old but well-maintained.', 3, 'Yes', 'Submitted'),
(1, 'Limited Parking', 'Struggled with parking sometimes.', 3, 'No', 'Submitted'),
(1, 'Friendly Community', 'Made a lot of friends in the building.', 5, 'Yes', 'Submitted'),
(1, 'Good Management', 'The property management was responsive.', 4, 'Yes', 'Submitted'),
(1, 'Lacking Amenities', 'Wish there was a gym in the building.', 3, 'No', 'Submitted'),
(1, 'Cozy and Quiet', 'Perfect for a small family.', 4, 'Yes', 'Submitted');

INSERT INTO Rating (apartment_id, title, description, value, would_recommend, status)
VALUES
(1, 'Bright and Airy', 'Lots of natural light in the living room.', 4, 'Yes', 'Approved'),
(1, 'Close to Shops', 'Walking distance from multiple shops and restaurants.', 5, 'Yes', 'Approved'),
(1, 'Thin Walls', 'Could hear my neighbors talking at times.', 2, 'No', 'Approved'),
(1, 'Good for Students', 'Lived here during college and had a good experience.', 4, 'Yes', 'Approved'),
(1, 'Maintenance Issues', 'Had some issues with plumbing that took a while to fix.', 2, 'No', 'Approved'),
(1, 'Safe Area', 'Felt safe walking around the neighborhood.', 5, 'Yes', 'Approved'),
(1, 'Pet-Friendly', 'Great place for pet owners with a nearby park.', 5, 'Yes', 'Approved'),
(1, 'Convenient Location', 'Easy to commute to work from here.', 4, 'Yes', 'Approved'),
(1, 'Spacious Kitchen', 'Loved cooking in the large kitchen.', 5, 'Yes', 'Approved'),
(1, 'Overall Positive', 'Had a generally positive experience living here.', 4, 'Yes', 'Approved');