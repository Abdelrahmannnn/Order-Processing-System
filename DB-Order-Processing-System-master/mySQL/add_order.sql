DELIMITER //
CREATE PROCEDURE add_order (ISBN char(15), quantity int unsigned)
BEGIN
	insert into book_store.order (ISBN, date, num_of_books) values (ISBN, NOW(), quantity);
END //
DELIMITER ;