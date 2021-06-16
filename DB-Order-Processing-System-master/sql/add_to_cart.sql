DELIMITER //
CREATE PROCEDURE add_to_cart(user_name varchar(30), ISBN char(15))
BEGIN
	insert into cart(username, ISBN, price, date) 
		values (user_name, ISBN, (select price from Book 
			where Book.ISBN = ISBN), CURDATE());
END //
DELIMITER ;