package application;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnector {

	private static DBConnector instance = null;
	private static Connection connect;
	private static String password;
	private static final String DB_URL = "jdbc:mysql://localhost:3306/book_store";
	private static final String DB_USER_NAME = "root";
	private static final String DB_PASS = "root";

	public static DBConnector getInstance() {
		if (instance == null) {

			instance = new DBConnector();
		}
		if (connect == null) {
			try {
				connect = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				printSQLException(e);
			}
		}
		return instance;
	}


	public boolean Exist(String username) {
		try {
			if (connect == null) {
				connect = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
			}
			String query = "select username from user where username=?";
			PreparedStatement preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, username);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return false;
	}


	public void insertRecord(String userName, String password, String firstName, String lastName, String email,
							 String phone, String shipadd) throws SQLException {
		try {
			if (connect == null) {
				connect = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
			}
			String query = "insert into user values(?,?,?,?,?,?,?,'user')";
			PreparedStatement preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, firstName);
			preparedStatement.setString(4, lastName);
			preparedStatement.setString(5, email);
			preparedStatement.setString(6, phone);
			preparedStatement.setString(7, shipadd);

			preparedStatement.executeUpdate();
			PassValues.setUserName(userName);
			PassValues.setFirstName(firstName);
			PassValues.setSecondName(lastName);
			PassValues.setPhone(phone);
			PassValues.setAddress(shipadd);
			PassValues.setEmail(email);
			DBConnector.password = password;
		} catch (SQLException e) {
			printSQLException(e);
		}
	}


	public boolean is_valid(String username, String password) throws SQLException {
		try {
			if (connect == null) {
				connect = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
			}
			String query = "select * from user where username=? and password=?";
			PreparedStatement preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				PassValues.setUserName(resultSet.getString("username"));
				PassValues.setAddress(resultSet.getString("ship_address"));
				PassValues.setFirstName(resultSet.getString("first_name"));
				PassValues.setSecondName(resultSet.getString("last_name"));
				PassValues.setPhone(resultSet.getString("phone"));
				PassValues.setEmail(resultSet.getString("email"));
				PassValues.setPrivilege(resultSet.getString("privilege"));
				DBConnector.password = password;
				return true;
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return false;
	}

	public void changepass(String password) {
		try {
			if (connect == null) {
				connect = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
			}
			String query = "update user set password = ? where username=?";
			PreparedStatement preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, password);
			preparedStatement.setString(2, PassValues.getUserName());
			preparedStatement.executeUpdate();
			DBConnector.password = password;
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public void editData(String firstname, String lastname, String email, String phone, String shipadd) {
		try {
			if (connect == null) {
				connect = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
			}
			String query = "update user set first_name=?, last_name=?, phone=?, ship_address=?, "
					+ "email=? where username=?";
			PreparedStatement preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, firstname);
			preparedStatement.setString(2, lastname);
			preparedStatement.setString(3, phone);
			preparedStatement.setString(4, shipadd);
			preparedStatement.setString(5, email);
			preparedStatement.setString(6, PassValues.getUserName());
			preparedStatement.executeUpdate();
			PassValues.setFirstName(firstname);
			PassValues.setSecondName(lastname);
			PassValues.setPhone(phone);
			PassValues.setAddress(shipadd);
			PassValues.setEmail(email);

		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public boolean publisherExists(String name) {
		try {
			if (connect == null) {
				connect = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
			}
			String query = "select * from publisher where publisher_name=?";
			PreparedStatement preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, name);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return false;
	}


	public void addPublisher(String name, ArrayList<String> phones, ArrayList<String> addresses) {
		try {
			if (connect == null) {
				connect = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
			}
			connect.setAutoCommit(false);
			String query = "insert into publisher values(?,?,?)";
			PreparedStatement preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, addresses.get(0));
			preparedStatement.setString(3, phones.get(0));
			preparedStatement.executeUpdate();


		//	*********************
			connect.commit();
			connect.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				if (connect != null) {
					connect.rollback();
					connect.setAutoCommit(true);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			printSQLException(e);
		}
	}


	public void addBook(String isbn, String title, String pname, String category, String price,
						String threashold, String availableCopies, String orderQuantity, List<String> authors) {
		try {
			if (connect == null) {
				connect = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
			}
			connect.setAutoCommit(false);
			String query = "insert into book values(?,?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, isbn);
			preparedStatement.setString(2, title);
			preparedStatement.setString(3, pname);
	//		preparedStatement.setString(4, pyear);   //BAHAA **************************************************
			preparedStatement.setDouble(4, Double.valueOf(price));
			preparedStatement.setString(5, category);
			preparedStatement.setInt(6, Integer.valueOf(availableCopies));
			preparedStatement.setInt(7, Integer.valueOf(threashold));
			preparedStatement.setInt(8, Integer.valueOf(orderQuantity));
			preparedStatement.executeUpdate();

			String authorsQuery = "insert into author values(?,?)";

			for (String a : authors) {
				preparedStatement = connect.prepareStatement(authorsQuery);
				preparedStatement.setString(1, a.trim());
				preparedStatement.setString(2, isbn);

				preparedStatement.executeUpdate();
			}
			connect.commit();
			connect.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				if (connect != null) {
					connect.rollback();
					connect.setAutoCommit(true);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			printSQLException(e);
		}
	}

	public boolean Search_book(String isbn) {
		try {
			if (connect == null) {
				connect = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
			}
			String query = "select * from book where ISBN=?";
			PreparedStatement preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, isbn);
			ResultSet result = preparedStatement.executeQuery();
			if (result.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			printSQLException(e);
		}
		return false;
	}

	public void getbook(String isbn) {
		String selectFromBookQuery = "select * from book where ISBN=?";
		String selectFromBookAuthorsQuery = "select * from author where ISBN=?";
		try {
			if (connect == null) {
				connect = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
			}
			/** get book data from book relation **/
			PreparedStatement preparedStatement = connect.prepareStatement(selectFromBookQuery);
			preparedStatement.setString(1, isbn);
			ResultSet result = preparedStatement.executeQuery();
			List<String> dataToModify = new ArrayList<String>();
			if (result.next()) {
				dataToModify.add(result.getString("title"));
				dataToModify.add(result.getString("publisher_name"));
//				Date date = result.getDate("publication_year");   //BAHAA ****************************************************************************
//				SimpleDateFormat formatter = new SimpleDateFormat("yyyy");	//BAHAA
//				dataToModify.add(formatter.format(date));					//BAHAA
				dataToModify.add(String.valueOf(result.getDouble("price")));
				dataToModify.add(result.getString("category"));
				dataToModify.add(String.valueOf(result.getInt("copies")));
				dataToModify.add(String.valueOf(result.getInt("threshold")));
				dataToModify.add(String.valueOf(result.getInt("order_quantity")));
			}

			preparedStatement = connect.prepareStatement(selectFromBookAuthorsQuery);
			preparedStatement.setString(1, isbn);
			result = preparedStatement.executeQuery();
			ArrayList<String> authors = new ArrayList<>();
			while (result.next()) {
				authors.add(result.getString("Author"));
			}
			String listString = "";
			for (int i = 0; i < authors.size(); i++) {
				listString += authors.get(i);
				if (i < authors.size() - 1) {
					listString += ",";
				}
			}
			dataToModify.add(listString);
			PassValues.setDataToModify(dataToModify);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			printSQLException(e);
		}
	}

	public void modifybook(String isbn, String title, String pname, String category, String price,
						   String threashold, String orderQuantity, List<String> authors) {
		try {
			if (connect == null) {
				connect = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
			}
			connect.setAutoCommit(false);
			String query = "update book set title=?, publisher_name=?, price=?,"
					+ "category=?, threshold=?, order_quantity=? where ISBN=?";
			PreparedStatement preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, title);
			preparedStatement.setString(2, pname);
//			preparedStatement.setString(3, pyear);    //BAHAA  *********************************************************************
			preparedStatement.setDouble(3, Double.valueOf(price));
			preparedStatement.setString(4, category);
			preparedStatement.setInt(5, Integer.valueOf(threashold));
			preparedStatement.setInt(6, Integer.valueOf(orderQuantity));
			preparedStatement.setString(7, isbn);
			preparedStatement.executeUpdate();

			String deleteAuthorsQuery = "delete from author where ISBN=?";
			preparedStatement = connect.prepareStatement(deleteAuthorsQuery);
			preparedStatement.setString(1, isbn);
			preparedStatement.executeUpdate();

			String authorsQuery = "insert into author values(?,?)";
			for (String a : authors) {
				preparedStatement = connect.prepareStatement(authorsQuery);
				preparedStatement.setString(1, a.trim());
				preparedStatement.setString(2, isbn);

				preparedStatement.executeUpdate();
			}
			connect.commit();
			connect.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				if (connect != null) {
					connect.rollback();
					connect.setAutoCommit(true);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			printSQLException(e);
		}
	}

	public void addorder(String isbn, String quantity) {
		try {
			if (connect == null) {
				connect = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
			}
			String query = "call add_order(?,?)";
			CallableStatement statement = connect.prepareCall(query);
			statement.setString(1, isbn);
			statement.setInt(2, Integer.valueOf(quantity));
			statement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			printSQLException(e);
		}
	}

	public void getAllOrders() {
		PassValues.clearAllOrder();
		try {
			if (connect == null) {
				connect = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
			}
			String query = "select * from book_store.order";
			PreparedStatement preparedStatement = connect.prepareStatement(query);
			ResultSet result = preparedStatement.executeQuery();
			while (result.next()) {
				PassValues.setOrderID(result.getInt("id"));
				PassValues.setOrderISBN(result.getString("ISBN"));
				PassValues.setOrderDate(result.getTimestamp("date")); // YYYY-MM-DD HH:MM:SS
				PassValues.setOrderQuantity(result.getInt("num_of_books"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			printSQLException(e);
		}
	}

	public void confirmOrder(int id) {
		try {
			if (connect == null) {
				connect = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
			}
			String query = "delete from book_store.order where id=?";
			PreparedStatement preparedStatement = connect.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			printSQLException(e);
		}
	}


	public String getdata(String username) {
		String res = "";
		try {
			if (connect == null) {
				connect = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
			}
			String query = "select * from user where username=?";
			PreparedStatement preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, username);
			ResultSet result = preparedStatement.executeQuery();
			List<String> userdata = new ArrayList<String>();
			if (result.next()) {
				String firstName = result.getString("first_name");
				res += "First Name: " + firstName + "\n";
				userdata.add(firstName);
				String secondName = result.getString("last_name");
				res += "last Name: " + secondName + "\n";
				userdata.add(secondName);
				String phone = result.getString("phone");
				res += "Phone: " + phone + "\n";
				userdata.add(phone);
				String email = result.getString("email");
				res += "E-Mail: " + email + "\n";
				userdata.add(email);
				String address = result.getString("ship_address");
				res += "Address: " + address + "\n";
				userdata.add(address);
				String privilege = result.getString("privilege");
				res += "Privilege: " + privilege + "\n";
				userdata.add(privilege);
				PassValues.setUserdata(userdata);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			printSQLException(e);
		}
		return res;
	}


	public void promote(String username) {
		try {
			if (connect == null) {
				connect = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
			}
			String query = "update user set privilege='manager' where username=?";
			PreparedStatement preparedStatement = connect.prepareStatement(query);
			preparedStatement.setString(1, username);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			printSQLException(e);
		}
	}

	public void bookSearch(String title, String publisher, String category, double sellingPrice,
                           String author) {
		boolean first = true;
		String query;
		if (!author.equals("") && title.equals("") && publisher.equals("") && category.equals("")
				&& sellingPrice == 0) {
			query = "select ISBN from author where author like '%" + author + "%'";
			try {
				if (connect == null) {
					connect = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
				}
				PreparedStatement preparedStatement = connect.prepareStatement(query);
				ResultSet isbnResult = preparedStatement.executeQuery();
				while (isbnResult.next()) {
					String ISBN = isbnResult.getString("ISBN");
					// get book data by ISBN
					String dataQuery = "select * from book where ISBN=?";
					preparedStatement = connect.prepareStatement(dataQuery);
					preparedStatement.setString(1, ISBN);
					ResultSet result = preparedStatement.executeQuery();
					while (result.next()) {
						PassValues.setTitle(result.getString("title"));
						PassValues.setPublisher(result.getString("publisher_name"));
//						Date date = result.getDate("publication_year");      //************************BAHAAAAAAAA************
//						SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
//						PassValues.setPublicationYear(Integer.valueOf(formatter.format(date)));   //***********************************BAAAAAAAAHHHHHHHHAAAAAAAAAAAAAA********
						PassValues.setCategory(result.getString("category"));
						PassValues.setSellingPrice(result.getDouble("price"));
						PassValues.setAvailableCopies(result.getInt("copies"));
						PassValues.setISBN(result.getString("ISBN"));
					}
					// get book authors by ISBN
					String authorsQuery = "select * from author where ISBN=?";
					preparedStatement = connect.prepareStatement(authorsQuery);
					preparedStatement.setString(1, ISBN);
					ResultSet authors = preparedStatement.executeQuery();
					ArrayList<String> Authors = new ArrayList<String>();
					while (authors.next()) {
						Authors.add(authors.getString("Author"));
					}
					PassValues.setAuthors(Authors);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				printSQLException(e);
			}
		} else {
			if (!author.equals("")) {
				query = "select * from book, author where author.ISBN and ";
			} else {
				query = "select * from book where ";
			}
			if (!title.equals("")) {
				query += "title like '%" + title + "%'";
				first = false;
			}
			if (!publisher.equals("")) {
				if (!first)
					query += " and ";
				query += "publisher_name like '%" + publisher + "%'";
				first = false;
			}
			if (!category.equals("")) {
				if (!first)
					query += " and ";
				query += "category='" + category + "'";
				first = false;
			}

			if (sellingPrice != 0) {
				if (!first)
					query += " and ";
				query += "price=" + String.valueOf(sellingPrice);
				first = false;
			}
			if (!author.equals("")) {
				if (!first)
					query += " and ";
				query += "author like '%" + author + "%'";
			}
			try {
				if (connect == null) {
					connect = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
				}
				PreparedStatement preparedStatement = connect.prepareStatement(query);
				ResultSet result = preparedStatement.executeQuery();
				while (result.next()) {
					String ISBN = result.getString("ISBN");
					String authorsQuery = "select * from author where ISBN=?";
					preparedStatement = connect.prepareStatement(authorsQuery);
					preparedStatement.setString(1, ISBN);
					ResultSet authors = preparedStatement.executeQuery();
					ArrayList<String> Authers = new ArrayList<String>();
					while (authors.next()) {
						Authers.add(authors.getString("author"));
					}
					PassValues.setAuthors(Authers);
					PassValues.setTitle(result.getString("title"));
					PassValues.setPublisher(result.getString("publisher_name"));
//					Date date = result.getDate("publication_year");         //BAHAAAAAAAAAA*************************************************
//					SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
//					PassValues.setPublicationYear(Integer.valueOf(formatter.format(date))); //BAHAA********************************
					PassValues.setCategory(result.getString("category"));
					PassValues.setSellingPrice(result.getDouble("price"));
					PassValues.setAvailableCopies(result.getInt("copies"));
					PassValues.setISBN(result.getString("ISBN"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				printSQLException(e);
			}
		}

	}
	//BAHAA all prev DONE
	public void checkOut(List<String> ISBN) {
		String updateBookQuery = "update book set copies = copies - 1 where ISBN=?";
		String addToSalesProcedureCall = "call add_to_cart(?,?)";
		try {
			if (connect == null) {
				connect = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASS);
			}
			connect.setAutoCommit(false);
			for (String s : ISBN) {
				PreparedStatement preparedStatement = connect.prepareStatement(updateBookQuery);
				preparedStatement.setString(1, s);
				preparedStatement.executeUpdate();
				CallableStatement callableStatement = connect.prepareCall(addToSalesProcedureCall);
				callableStatement.setString(1, PassValues.getUserName());
				callableStatement.setString(2, s);
				callableStatement.executeQuery();
			}
			connect.commit();
			connect.setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			printSQLException(e);
			if (connect != null) {
				try {
					connect.rollback();
					connect.setAutoCommit(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					printSQLException(e1);
				}
			}
		}
	}

	public static void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}

	public String getpass() {
		return password;
	}


	public String getcurruser() {
		return PassValues.getUserName();
	}
}