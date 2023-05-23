//       Spring Session 2022
//     Program Assignment PP04
//            CIS611
//      GANG YANG  & Erik Eitel
//           04-29-2022


// add the class template 


//Please enter your own account name and password on line 66.
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.applet.*;
import javax.swing.*;

import java.sql.*;

public class StaffServer {
 
  private int port;
  private Message msg;
private ServerSocket serverSocket;
private Socket conn;
private ObjectInputStream serverInputStream;
private ObjectOutputStream serverOutputStream;

private Connection connect = null;
private Statement statement = null;
private PreparedStatement preparedStatement = null;

private final int opTypeClose = 0;
private final int opTypeView = 1;
private final int opTypeInsert = 2;
private final int opTypeUpdate = 3;
private final int opTypeDelete = 4;
  
  public StaffServer (int port) throws IOException, ClassNotFoundException, SQLException{
	
	  // create the server
	  
	  this.port = port;
	  initializeDB(port);
  }

  
  private void initializeDB(int port) throws IOException, ClassNotFoundException, SQLException {
    
       	  	    
	  try {
			
			ServerSocket serverSocket = new ServerSocket(port);
			
			// this will load the MySQL driver, each DB has its own driver
		      Class.forName("com.mysql.jdbc.Driver");
		     
		      // Connect to your database using your credentials
		      connect = DriverManager.getConnection("jdbc:mysql://BUSCISMYSQL01.busdom.colostate.edu:3306/team8db", "gangyang", "c611c!63557");
		      
			JOptionPane.showMessageDialog(null, "Server Is Listening ON Port:  " + port + "\n");
			
			// loops for ever waiting for the client connection requests
			 while (true) {
			        // Listen for a new connection request
			        Socket socket = serverSocket.accept();
			        
			        JOptionPane.showMessageDialog(null, "Connection Request ....... Connection Accepted" + "\n");

			        // create a thread for each client connection request using Runnable class HandleAClient 
			        HandleAClient task = new HandleAClient(socket);

			        // Start the new thread
			        new Thread(task).start();
			       
			      }
			
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage()+"   :It couldn't listen on the port "+port + "\n");
			
		}
		
  }

 

  /**View record by ID
 * @throws SQLException */
  private void view(Message message) throws SQLException {
   // Build a SQL SELECT statement
	  
	// statements allow to issue SQL queries to the database
      statement = connect.createStatement();
      // gets the result of the SQL query
      statement.executeQuery("select * from team8db.staff Where id = " + message.getId());
      
      JOptionPane.showMessageDialog(null, "Staff id: " + message.getId() +" is Selected for view");
      
      }

  /**Insert a new record
 * @throws SQLException */
  private void insert(Message message) throws SQLException {
  // Build a SQL UPDATE statement
	// statements allow to issue SQL queries to the database
      
	  statement = connect.createStatement();
       		
       	  preparedStatement = connect.prepareStatement("insert into  `team8db`.`staff` values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
	      preparedStatement.setInt(1, message.getId());
	      preparedStatement.setString(2, message.getFirstName());
	      preparedStatement.setString(3, message.getLastName());
	      preparedStatement.setString(4, String.valueOf(message.getMi()));
	      preparedStatement.setString(5, message.getAddress());
	      preparedStatement.setString(6, message.getCity());
	      preparedStatement.setString(7, message.getState());
	      preparedStatement.setBigDecimal(8, new BigDecimal(message.getmPhoneNo()));
	      preparedStatement.setBigDecimal(9, new BigDecimal(message.gethPhoneNo()));
	      preparedStatement.executeUpdate();
	      preparedStatement.close();
	      
	      preparedStatement = connect.prepareStatement("insert into  `team8db`.`telephone` values (?, ?)");
	      preparedStatement.setBigDecimal(1, new BigDecimal(message.getmPhoneNo()));
	      preparedStatement.setString(2, message.getmPhoneCarrier());
	      preparedStatement.executeUpdate();
	      preparedStatement.close();
	      
	      preparedStatement = connect.prepareStatement("insert into  `team8db`.`telephone` values (?, ?)");
	      preparedStatement.setBigDecimal(1, new BigDecimal(message.gethPhoneNo()));
	      preparedStatement.setString(2, message.gethPhoneCarrier());
	      preparedStatement.executeUpdate();
	      preparedStatement.close();
	      
	         	 
      JOptionPane.showMessageDialog(null, "Staff id: " + message.getId() +" is inserted");
      	
  }
  
  /**Update a record
 * @throws SQLException */
  private void update(Message message) throws SQLException {
    // Build a SQL INSERT statement
	// statements allow to issue SQL queries to the database
      statement = connect.createStatement();
      
      preparedStatement = connect.prepareStatement("update `team8db`.`staff` SET address = ?, city = ?, state = ? WHERE id = ? ");
	      preparedStatement.setString(1, message.getAddress());
	      preparedStatement.setString(2, message.getCity());
	      preparedStatement.setString(3, message.getState());
	      preparedStatement.setInt(4, message.getId());
	      preparedStatement.executeUpdate();
	      preparedStatement.close();
	      
	      JOptionPane.showMessageDialog(null, "The address of Staff id: " + message.getId() +" is updated");
   }
  
  /**Clear text fields
 * @throws SQLException */
  private void delete(Message message) throws SQLException {
	// Build a SQL DELETE statement
	  
	  statement = connect.createStatement();
      statement.executeUpdate("DELETE from team8db.staff WHERE id = " + message.getId());
      statement.executeUpdate("DELETE from team8db.telephone WHERE phone = " + message.getmPhoneNo());
      statement.executeUpdate("DELETE from team8db.telephone WHERE phone = " + message.gethPhoneNo());
      
      JOptionPane.showMessageDialog(null, "Staff id: " + message.getId() +" is deleted");
      
  }
  
  private void close(Message message) throws SQLException, IOException {
		// Build a SQL DELETE statement
		  
	  statement.close();  
	  connect.close();
	  
	   }
  
      // inner Runnable class handle a client connection
	class HandleAClient implements Runnable {
	    private Socket socket; // A connected socket

	    /** Construct a thread */
	    public HandleAClient(Socket socket) {
	      this.socket = socket;
	    }

	    /** Run a thread */
	    public void run() {
	    	
	    	// write the code to call a proper method to process the client request

		      try {
		        // Create data input and output streams
		        ObjectInputStream inputFromClient = new ObjectInputStream(
		          socket.getInputStream());
		        ObjectOutputStream outputToClient = new ObjectOutputStream(
		          socket.getOutputStream());

		        // Continuously serve the client
		        while (true) {
		        	Message message = null;	
		    		message = (Message)inputFromClient.readObject();
		    		
		    		
		            switch (message.getOpType()) { 
		    		
		    		case opTypeView: view(message);
		    				        break; 
		    				        
		    		case opTypeUpdate: update(message);
			                        break; 
		    				        
		    	    case opTypeInsert: insert(message);
		                                break; 

		    	    case opTypeDelete:  delete(message);
		                                 break; 
		                                 
		    	    case opTypeClose:  close(message);
                        break; 
		    		 
		    		 }
		            outputToClient.writeObject(message);	
		             
		    	  }
		        
		       
		      }catch(IOException e) {
		        System.err.println(e);
		      } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
	    }
	    
	   }// end of class Runnable 
  
  public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
    
	  StaffServer server = new StaffServer(8000);
    
  }
}
