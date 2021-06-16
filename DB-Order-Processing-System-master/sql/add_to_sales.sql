CREATE DEFINER=`root`@`localhost` PROCEDURE `add_to_sales`(user_name varchar(20), ISBN char(10))
BEGIN
	insert into Sales(username, ISBN_number, price, checkout_date) 
		values (user_name, ISBN, (select selling_price from Book 
			where Book.ISBN_number = ISBN), CURDATE());
END