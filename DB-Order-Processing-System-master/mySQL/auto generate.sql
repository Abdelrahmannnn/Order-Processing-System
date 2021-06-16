-- MySQL Workbench Forward Engineering
-- select * from book;
-- drop trigger place_order;
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema book_store
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema book_store
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `book_store` DEFAULT CHARACTER SET utf8 ;
USE `book_store` ;

-- -----------------------------------------------------
-- Table `book_store`.`publisher`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_store`.`publisher` (
  `publisher_name` VARCHAR(50) NOT NULL,
  `address` VARCHAR(100) NOT NULL,
  `phone` VARCHAR(15),
  PRIMARY KEY (`publisher_name`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `book_store`.`book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_store`.`book` (
  `ISBN` VARCHAR(15) NOT NULL,
  `title` VARCHAR(50) NOT NULL,
  `publisher_name` VARCHAR(50) NOT NULL,
  `price` DOUBLE NOT NULL,
  `category` ENUM('Science', 'Art', 'Religion', 'History', 'Geography') NOT NULL,
  `copies` INT NOT NULL DEFAULT 0,
  `threshold` INT NOT NULL DEFAULT 5,
  `order_quantity` INT NOT NULL,
  PRIMARY KEY (`ISBN`),
  INDEX `fk_book_publisher_idx` (`publisher_name` ASC) VISIBLE,
  INDEX `title_index` (`title` ASC) INVISIBLE,
  INDEX `category_index` (`category` ASC) INVISIBLE,
  CONSTRAINT `fk_book_publisher`
    FOREIGN KEY (`publisher_name`)
    REFERENCES `book_store`.`publisher` (`publisher_name`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `book_store`.`author`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_store`.`author` (
  `author` VARCHAR(100) NOT NULL,
  `ISBN` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`author`, `ISBN`),
  INDEX `fk_author_book1_idx` (`ISBN` ASC) VISIBLE,
  CONSTRAINT `fk_author_book1`
    FOREIGN KEY (`ISBN`)
    REFERENCES `book_store`.`book` (`ISBN`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `book_store`.`order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_store`.`order` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `ISBN` VARCHAR(15) NOT NULL,
  `date` DATETIME NULL,
  `num_of_books` INT NOT NULL DEFAULT 10,
  PRIMARY KEY (`id`),
  INDEX `fk_order_book1_idx` (`ISBN` ASC) VISIBLE,
  CONSTRAINT `fk_order_book`
    FOREIGN KEY (`ISBN`)
    REFERENCES `book_store`.`book` (`ISBN`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `book_store`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_store`.`user` (
  `username` VARCHAR(30) NOT NULL,
  `password` VARCHAR(30) NOT NULL,
  `first_name` VARCHAR(30) NOT NULL,
  `last_name` VARCHAR(30) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `phone` VARCHAR(15) NULL,
  `ship_address` VARCHAR(100) NOT NULL,
  `privilege` ENUM('user', 'manager') NOT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `book_store`.`cart`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `book_store`.`cart` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(30) NOT NULL,
  `ISBN` VARCHAR(15) NOT NULL,
  `quantity` INT NULL DEFAULT 1,
  `price` DOUBLE NOT NULL,
  `date` DATE NULL,
  INDEX `fk_cart_user1_idx` (`username` ASC) VISIBLE,
  INDEX `fk_cart_book1_idx` (`ISBN` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_cart_user1`
    FOREIGN KEY (`username`)
    REFERENCES `book_store`.`user` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_cart_book1`
    FOREIGN KEY (`ISBN`)
    REFERENCES `book_store`.`book` (`ISBN`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `book_store`;

DELIMITER $$
USE `book_store`$$
CREATE DEFINER = CURRENT_USER TRIGGER `check_books_num` BEFORE UPDATE ON `book` FOR EACH ROW
BEGIN
	IF
		new.copies < 0 
    THEN
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Number of books is not available by now';
	END IF;

END$$

USE `book_store`$$
CREATE DEFINER = CURRENT_USER TRIGGER `place_order` AFTER UPDATE ON `book` FOR EACH ROW
BEGIN
	IF 
		new.copies < new.threshold 
	THEN
 		insert into book_store.order(ISBN, date, num_of_books) values(new.ISBN, NOW(), new.order_quantity);
     END IF;

END$$


USE `book_store`$$
CREATE DEFINER = CURRENT_USER TRIGGER `confirm_order` BEFORE DELETE ON `order` FOR EACH ROW
BEGIN
	update Book set copies = copies + old.num_of_books 
     where Book.ISBN = old.ISBN;

END$$

DELIMITER ;

insert into user values('bahaa', '123456', 'bahaa', 'khalf', 'bbb@gmail.com', null, 'alex st.', 'manager');
insert into publisher values('Pub','alex st.',NULL);
insert into Book values('0123456789','DB','Pub','500.5', 'Science',50, 5, 5);
insert into Book values('1123456789','title','Pub','500.5', 'Art',50, 5, 5);
insert into Book values('1123478589','new','Pub','500', 'Art',50, 5, 5);
-- delete  from user where username ='bahaa' ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
