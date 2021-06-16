CREATE DEFINER=`root`@`localhost` FUNCTION `total_sales`() RETURNS double
BEGIN
	declare total double;
	select sum(price) into total from Sales where checkout_date <= DATE_SUB(NOW(), INTERVAL 1 MONTH);
RETURN 1;
END