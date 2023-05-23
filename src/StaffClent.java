//       Spring Session 2022
//     Program Assignment PP04
//            CIS611
//      GANG YANG  & Erik Eitel
//           04-29-2022


//add class template
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;
import javax.swing.border.TitledBorder;

class StaffClent extends JFrame implements ActionListener{


	private static String hostname;
	private static int port;
	private Message msg;

	private Socket conn;
	private ObjectOutputStream clientOutputStream;
	private ObjectInputStream clientInputStream;
	static JTextArea resposeText;
	
	// declare UI component objects
	JLabel lblID, lblLname, lblFname, lblMname, lblAddress, lblCity, lblState, lblMphone, lblMphoneCarrier, lblHphone, lblHphoneCarrier, lblStatus;
	JTextField tfID, tfLname, tfFname, tfMname, tfAddress, tfCity, tfState, tfMphone, tfMphoneCarrier, tfHphone, tfHphoneCarrier;
	JButton btnView, btnInsert, btnUpdate, btnDelete, btnClear, btnClose;
		
	
	private final int opTypeClose = 0;
	private final int opTypeView = 1;
	private final int opTypeInsert = 2;
	private final int opTypeUpdate = 3;
	private final int opTypeDelete = 4;
		
public StaffClent(String hostname, int port) throws IOException {
	
	// call these two methods to create user GUI
		initComponenet();
		doTheLayout();
	
	this.port = port;
	this.hostname = hostname;
	
	// Create a connection with the StaffServer server on port number 8000
	conn = new Socket(hostname, port);
	clientOutputStream = new ObjectOutputStream(conn.getOutputStream());
	clientInputStream = new ObjectInputStream(conn.getInputStream());
	
	resposeText.append("Connected");
	
			
}

private void initComponenet(){
	// Initialize the GUI components
	
	//Labels
	lblID = new JLabel("ID ", JLabel.LEFT);
	lblLname = new JLabel("Last Name   ", JLabel.LEFT);
	lblFname = new JLabel("First Name   ", JLabel.LEFT);
	lblMname = new JLabel("mi   ", JLabel.LEFT);
	lblAddress = new JLabel("Address   ", JLabel.LEFT);
	lblCity = new JLabel("City   ", JLabel.LEFT);
	lblState = new JLabel("State   ", JLabel.LEFT);
	lblMphone = new JLabel("Mobile phone number   ", JLabel.LEFT);
	lblMphoneCarrier = new JLabel("Mobile Phone Carrier   ", JLabel.LEFT);
	lblHphone = new JLabel("Home Phone Number    ", JLabel.LEFT);
	lblHphoneCarrier = new JLabel("Home Phone Carrier    ", JLabel.LEFT);
	lblStatus = new JLabel("Connecttion Status:    ", JLabel.LEFT);
	
	
	//Text fields
	tfID = new JTextField(6);
	tfFname = new JTextField(15);
	tfLname = new JTextField(15);
	tfMname = new JTextField(4);
	tfAddress = new JTextField(20);
	tfCity = new JTextField(20);
	tfState = new JTextField(4);
	tfMphone = new JTextField(10);
	tfMphoneCarrier = new JTextField(12);
	tfHphone = new JTextField(10);
	tfHphoneCarrier = new JTextField(12);
	tfMname.setToolTipText("Only one Character allowed to input");
	tfState.setToolTipText("Only two Characters allowed to input");
	tfMphone.setToolTipText("10 digital number input");
	tfHphone.setToolTipText("10 digital number input");
	
	//Buttons
	btnView = new JButton("View");
	btnView.addActionListener(this);
	btnInsert = new JButton("Insert");
	btnInsert.addActionListener(this);
	btnUpdate = new JButton("Update");
	btnUpdate.addActionListener(this);
	btnDelete = new JButton("Delete");
	btnDelete.addActionListener(this);
	btnClear = new JButton("Clear");
	btnClear.addActionListener(this);
	btnClose = new JButton("Close");
	btnClose.addActionListener(this);
	
	resposeText = new JTextArea();
    resposeText.setEditable(false);
			
}

private void doTheLayout(){
	// Arrange the UI components into GUI window
	JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	panel1.add(lblID);
	panel1.add(tfID);
	
	JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	panel2.add(lblLname);
	panel2.add(tfLname);
	panel2.add(lblFname);
	panel2.add(tfFname);
	panel2.add(lblMname);
	panel2.add(tfMname);
	
	JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	panel3.add(lblAddress);
	panel3.add(tfAddress);
	
	JPanel panel4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	panel4.add(lblCity);
	panel4.add(tfCity);
	panel4.add(lblState);
	panel4.add(tfState);
	
	JPanel panel5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	panel5.add(lblMphone);
	panel5.add(tfMphone);
	panel5.add(lblMphoneCarrier);
	panel5.add(tfMphoneCarrier);
	
	JPanel panel6 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	panel6.add(lblHphone);
	panel6.add(tfHphone);
	panel6.add(lblHphoneCarrier);
	panel6.add(tfHphoneCarrier);
	
	JPanel upPanel = new JPanel(new GridLayout(6, 1));
	upPanel.add(panel1);
	upPanel.add(panel2);
	upPanel.add(panel3);
	upPanel.add(panel4);
	upPanel.add(panel5);
	upPanel.add(panel6);
	
	
	upPanel.setBorder(new TitledBorder("Staff Information"));
	
	JPanel bottomPanel = new JPanel();
	bottomPanel.add(btnView);
	bottomPanel.add(btnInsert);
	bottomPanel.add(btnUpdate);
	bottomPanel.add(btnDelete);
	bottomPanel.add(btnClear);
	bottomPanel.add(btnClose);
	
	bottomPanel.setBorder(null);
	
	JPanel footnotePanel = new JPanel();
	footnotePanel.add(lblStatus);
	footnotePanel.add(resposeText);
		
	setLayout( new FlowLayout());
	add(upPanel);
    add(bottomPanel);
    add(footnotePanel);
}	
	
 
@Override
public void actionPerformed(ActionEvent e) {
	
	if(e.getSource() == this.btnView)
		try {
			viewButtonClicked();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	if(e.getSource() == this.btnInsert)
		try {
			insertButtonClicked();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
	if(e.getSource() == this.btnUpdate)
		try {
			updateButtonClicked();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    
	if(e.getSource() == this.btnDelete)
		try {
			deleteButtonClicked();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    
	if(e.getSource() == this.btnClear) 		 
    	clearButtonClicked();
		
	if(e.getSource() == this.btnClose)
		try {
			closeButtonClicked();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
}



private void viewButtonClicked() throws IOException {
	  
	  // handle view button clicked event 
	Integer id = 0;
	
	//Get ID
	try {
	id = Integer.parseInt(this.tfID.getText().trim());
	if (id <= 0)
		throw new Exception();
       } catch (Exception ex) {
	JOptionPane.showMessageDialog(this.tfID, "Invalid staff ID");
	this.tfID.setText("");
	return;			
	  }// end try-catch
	
	msg = new Message(opTypeView, id, "", "", '\u0000', "", "", "", null, null, "", "");
	
	  tfID.setText("");
	  	
	clientOutputStream.writeObject(msg); //send data to Server
	 
  }

private void insertButtonClicked() throws IOException{
	 // handle insert button clicked event 
	Integer id = 0;
	String lastName, firstName;
	char mi;
	String address, city, state; 	
	BigInteger mPhoneNo, hPhoneNo;
	String mPhoneCarrier, hPhoneCarrier;
	
	//Get ID
	try {
	id = Integer.parseInt(this.tfID.getText().trim());
	
	if (id <= 0)
		throw new Exception();
       } catch (Exception ex) {
	JOptionPane.showMessageDialog(this.tfID, "Invalid staff ID");
	this.tfID.setText("");
	return;				
   }// end try-catch
	 
	//Get first Name
	if(this.tfFname.getText().trim().isEmpty()) {
		JOptionPane.showMessageDialog(this.tfFname, "Invalid Fisrt Name");
		this.tfFname.setText("");
		return;				
	    } //end if
	firstName = this.tfFname.getText().trim();
		
	//Get Last Name
	if(this.tfLname.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this.tfLname, "Invalid last Name");
			this.tfLname.setText("");
			return;				
		    } //end if
		lastName = this.tfLname.getText().trim();
	//Get initial middle name 	
		if(this.tfMname.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this.tfMname, "Invalid Middle Name initial");
			this.tfMname.setText("");
			return;				
		    } //end if
		mi = this.tfMname.getText().trim().charAt(0);
		
	//Get address
		if(this.tfAddress.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this.tfAddress, "Invalid Address");
			this.tfAddress.setText("");
			return;				
		    } //end if
		address = this.tfAddress.getText().trim();
		
	//Get city name
		if(this.tfCity.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this.tfCity, "Invalid City");
			this.tfCity.setText("");
			return;				
		    } //end if
		city = this.tfCity.getText().trim();
		
	//Get State
		if(this.tfState.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this.tfState, "Invalid State");
			this.tfState.setText("");
			return;				
		    } //end if
		state = this.tfState.getText().trim();
		
	//Get mobile phone number
		if(this.tfMphone.getText().trim().isEmpty() || this.tfMphone.getText().trim().length() != 10 ) {
				JOptionPane.showMessageDialog(this.tfMphone, "Invalid Mobile phone number");
				this.tfMphone.setText("");
				return;				
			    } //end if
			mPhoneNo = new BigInteger(this.tfMphone.getText().trim());
			
				
   	//Get Mobile phone carrier
		if(this.tfMphoneCarrier.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this.tfMphoneCarrier, "Invalid State");
			this.tfMphoneCarrier.setText("");
			return;				
		    } //end if
		mPhoneCarrier = this.tfMphoneCarrier.getText().trim();
		
	//Get mobile phone number
		if(this.tfHphone.getText().trim().isEmpty() || this.tfHphone.getText().trim().length() != 10 ) {
					JOptionPane.showMessageDialog(this.tfHphone, "Invalid Mobile phone number");
					this.tfHphone.setText("");
					return;				
				    } //end if
			hPhoneNo = new BigInteger(this.tfHphone.getText().trim());
			  
				
				//Get Mobile phone carrier
				if(this.tfHphoneCarrier.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(this.tfHphoneCarrier, "Invalid State");
					this.tfHphoneCarrier.setText("");
					return;				
				    } //end if
			hPhoneCarrier = this.tfHphoneCarrier.getText().trim();
			
			msg = new Message(opTypeInsert, id, lastName, firstName, mi, address, city, state,
					mPhoneNo, hPhoneNo, mPhoneCarrier, hPhoneCarrier);
			
			  tfID.setText("");
			  tfLname.setText("");
			  tfFname.setText("");
			  tfMname.setText("");
			  tfAddress.setText("");
			  tfCity.setText("");
			  tfState.setText("");
			  tfMphone.setText("");
			  tfMphoneCarrier.setText("");
			  tfHphone.setText("");
			  tfHphoneCarrier.setText("");
			
			clientOutputStream.writeObject(msg); //send data to Server
		
				
}

private void updateButtonClicked() throws IOException{
	
	// handle update button clicked event
	  // handle view button clicked event 
	    Integer id = 0;
		String address, city, state;
		
		//Get ID
		try {
		id = Integer.parseInt(this.tfID.getText().trim());
		if (id <= 0)
			throw new Exception();
	       } catch (Exception ex) {
		JOptionPane.showMessageDialog(this.tfID, "Invalid staff ID");
		this.tfID.setText("");
		return;			
		  }// end try-catch
		
		//Get address
		if(this.tfAddress.getText().trim().isEmpty()) {
		JOptionPane.showMessageDialog(this.tfAddress, "Invalid Address");
			this.tfAddress.setText("");
			return;				
	    } //end if
		address = this.tfAddress.getText().trim();		
		
		//Get city name
		if(this.tfCity.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this.tfCity, "Invalid City");
			this.tfCity.setText("");
			return;				
		    } //end if
		city = this.tfCity.getText().trim();
				
		//Get State
			if(this.tfState.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this.tfState, "Invalid State");
			this.tfState.setText("");
			return;				
		    } //end if
		state = this.tfState.getText().trim();
		
		msg = new Message(opTypeUpdate, id, "", "", '\u0000', address, city, state, null, null, "", "");
		
		  tfID.setText("");
		  tfAddress.setText("");
		  tfCity.setText("");
		  tfState.setText("");
		  	
		clientOutputStream.writeObject(msg); //send data to Server
		
  }

private void deleteButtonClicked() throws IOException{
	  
	// handle delete button clicked event
	Integer id = 0;
	BigInteger mPhoneNo, hPhoneNo;
		
	//Get ID
	try {
	id = Integer.parseInt(this.tfID.getText().trim());
	if (id <= 0)
		throw new Exception();
       } catch (Exception ex) {
	JOptionPane.showMessageDialog(this.tfID, "Invalid staff ID");
	this.tfID.setText("");
	return;				
   }// end try-catch
		
	//Get mobile phone number
		if(this.tfMphone.getText().trim().isEmpty() || this.tfMphone.getText().trim().length() != 10 ) {
				JOptionPane.showMessageDialog(this.tfMphone, "Invalid Mobile phone number");
				this.tfMphone.setText("");
				return;				
			    } //end if
			mPhoneNo = new BigInteger(this.tfMphone.getText().trim());
			
	//Get mobile phone number
		if(this.tfHphone.getText().trim().isEmpty() || this.tfHphone.getText().trim().length() != 10 ) {
					JOptionPane.showMessageDialog(this.tfHphone, "Invalid Mobile phone number");
					this.tfHphone.setText("");
					return;				
				    } //end if
			hPhoneNo = new BigInteger(this.tfHphone.getText().trim());
	  
		msg = new Message(opTypeDelete, id, "", "", '\u0000', "", "", "", mPhoneNo, hPhoneNo, "", "");
			
			  tfID.setText("");
			  tfMphone.setText("");
			  tfHphone.setText("");
			  			
			clientOutputStream.writeObject(msg); //send data to Server
	
  }
  
  
  void clearButtonClicked(){
	  
	// handle clear button clicked event
	  tfID.setText("");
	  tfLname.setText("");
	  tfFname.setText("");
	  tfMname.setText("");
	  tfAddress.setText("");
	  tfCity.setText("");
	  tfState.setText("");
	  tfMphone.setText("");
	  tfMphoneCarrier.setText("");
	  tfHphone.setText("");
	  tfHphoneCarrier.setText("");
	 
  }
  

  void closeButtonClicked() throws IOException{
	  
	// handle close button clicked event
	  msg = new Message(opTypeClose, -1, "", "", '\u0000', "", "", "", null, null, "", "");
	  clientOutputStream.writeObject(msg); 
	  
	  conn.close();
	  System.exit(0);
	 	 
	
	   }
  
  /**Main method*/
  public static void main(String[] args) throws IOException {
	  // create the user GUI
	  hostname = "localhost";
	  port = 8000;
	  StaffClent frame = new StaffClent(hostname, port);
	  frame.setTitle("Staff Table GUI");
      frame.pack();
      frame.setSize(600, 350);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
      
        }
}