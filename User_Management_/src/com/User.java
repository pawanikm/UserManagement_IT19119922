package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class User {
	public Connection connect()
	{
		Connection con = null;

		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/userapiproject", "root", "");
			//For testing
			System.out.print("Successfully connected");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return con;
	}
	
	public String insertUser( String fname, String lname, String address, String email, String contact, String username, String password) {
		// TODO Auto-generated method stub
		String output = "";
		try
		 {
		 Connection con = connect();
		 if (con == null)
		 {
		 return "Error while connecting to the database";
		 }
		 // create a prepared statement
		 String query = " insert into users(`id`, `fname`, `lname`, `address`, `email`, `contact`, `username`, `password`) VALUES (?,?,?,?,?,?,?,?)";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 // binding values
		 preparedStmt.setInt(1, 0);
		 preparedStmt.setString(2, fname);
		 preparedStmt.setString(3, lname);
		 preparedStmt.setString(4, address);
		 preparedStmt.setString(5, email);
		 preparedStmt.setInt(6, Integer.parseInt(contact));
		 preparedStmt.setString(7, username);
		 preparedStmt.setString(8, password);
		//execute the statement
		 preparedStmt.execute();
		 con.close();
		 String newItems = readUsers();
		 output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
		 }
		catch (Exception e)
		{
		 output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}";
		 System.err.println(e.getMessage());
		}
		return output;
	}
	
	
	public String readUsers()
	{
	 String output = "";
	try
	 {
	 Connection con = connect();
	 if (con == null)
	 {
	 return "Error while connecting to the database for reading.";
	 }
	 // Prepare the html table to be displayed
	 output = "<table border='1'><tr><th>User ID</th>" +
			 "<th>first Name </th>" + 
			 "<th>Last Name</th>" +
			 "<th>Address</th>" +
			 "<th>Email</th>" +
			 "<th>Contact No</th>" +
			 "<th>Username</th>" +
			 "<th>Password</th>" +
			 "<th>Update</th><th>Remove</th></tr>"; 
			 
			 String query = "SELECT * FROM `users`"; 
			 Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query); 
			 // iterate through the rows in the result set
			 while (rs.next()) 
			 { 
			 String id = Integer.toString(rs.getInt("id")); 
			 String fname = rs.getString("fname"); 
			 String lname = rs.getString("lname"); 
			 String address = rs.getString("address"); 
			 String email = rs.getString("email"); 
			 String contact = Integer.toString(rs.getInt("contact")); 
			 String username = rs.getString("username"); 
			 String password = rs.getString("password"); 
			
			 // Add into the html table
			 output += "<tr><td>" + id + "</td>"; 
			 output += "<td>" + fname + "</td>"; 
			 output += "<td>" + lname + "</td>"; 
			 output += "<td>" + address + "</td>"; 
			 output += "<td>" + email + "</td>"; 
			 output += "<td>" + contact + "</td>"; 
			 output += "<td>" + username + "</td>"; 
			 output += "<td>" + password + "</td>"; 
			 // buttons
			 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"+ "<td><form method='post' action='index.jsp'>"+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"+ "<input name='id' type='hidden' value='" + id + "'>" + "</form></td></tr>"; 
			 } 
			 con.close(); 
			 // Complete the html table
			 output += "</table>"; 
			 } 
			 catch (Exception e) 
			 { 
			 output = "Error while reading the items."; 
			 System.err.println(e.getMessage()); 
			 } 
			 return output; 
			 }

	
	public String updateUser( String fname, String lname, String address, String email, String contact, String username, String password, String id  )
	{
	String output = "";
		try
		{
			Connection con = connect();
		if (con == null)
		{
			return "Error while connecting to the database";
		}
		// create a prepared statement
		String updateUser = " UPDATE `users` SET `fname`='"+fname+"',`lname`='"+lname+"',`address`='"+address+"',`email`='"+email+"',`contact`='"+contact+"',`username`='"+username+"',`password`='"+password+"' WHERE `id`='"+id+"'";
		PreparedStatement preparedStmt = con.prepareStatement(updateUser);
	
		//execute the statement
		preparedStmt.execute();
		con.close();
		output = "Update successfully";
		}
		catch (Exception e)
		{
			output = "Error while inserting";
			System.err.println(e.getMessage());
		}
		return output;
	}

	
	public String deleteItem(String itemID)
	{
	String output = "";
		try
		{
			Connection con = connect();
		if (con == null)
		{
			return "Error while connecting to the database for deleting.";
		}
		// create a prepared statement
		String query = "delete from users where id=?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		// binding values
		preparedStmt.setInt(1, Integer.parseInt(itemID));
		// execute the statement
		preparedStmt.execute();
		con.close();
		String newItems = readUsers();
		output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
		}
		
		catch (Exception e)
		{
			 output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}";
			 System.err.println(e.getMessage());
		}
	return output;
	}


	
}
