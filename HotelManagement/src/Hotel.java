import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Hotel {
	
	private JButton bookbutton, emplbutton,costbutton,roombutton,clientbutton;
	static JFrame frame; 
	private JPanel panel, panel1;
	private JLabel labelimage;
	Booking booking;
	Employee employee;
	RoomOrganization roomorganization;
	Costs costs;
	Client client;
	
	
public static void main (String[] args){
	new Hotel();
	getConnection();
}
	
	


public Hotel (){

	frame = new JFrame ("International Crishelli Hotel");
	frame.setSize(1300,600);
	
	panel= new JPanel ();
	panel.setPreferredSize(new Dimension(800,600));
	
	labelimage=new JLabel();
	labelimage.setIcon(new ImageIcon("Hotel.png"));
	labelimage.setSize(300,44);
	labelimage.setVisible(true);
	panel.add(labelimage);
	
	
	bookbutton=new JButton("Go to hotel booking");
	bookbutton.setPreferredSize(new Dimension(300,50));
	panel.add(bookbutton);
	bookbutton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			
			frame.dispose();
			booking=new Booking();
			booking.setSize(1300,600);
			booking.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			booking.setVisible(true);
			
		}
		
	}
			);

	roombutton=new JButton("Room organization");
	panel.add(roombutton);
	roombutton.setPreferredSize(new Dimension(300,50));
	roombutton.addActionListener(new ActionListener(){

		public void actionPerformed(ActionEvent arg0) {
			frame.dispose();
			roomorganization=new RoomOrganization();
			roomorganization.setSize(1300,700);
			roomorganization.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			roomorganization.setVisible(true);
			
		}
		
		
	});
	

	
	emplbutton=new JButton("Go to employee services");
	emplbutton.setPreferredSize(new Dimension(300,50));
	panel.add(emplbutton);
	emplbutton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			
			frame.dispose();
			employee=new Employee();
			employee.setSize(1200,700);
			employee.setResizable(false);
			employee.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			employee.setVisible(true);
			
		}
		
	}
			);
	
	costbutton=new JButton ("Cost organization");
	costbutton.setPreferredSize(new Dimension(300,50));
	panel.add(costbutton);
	costbutton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			
			frame.dispose();
			costs=new Costs();
			costs.setSize(1300,700);
			costs.setResizable(false);
			costs.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			costs.setVisible(true);
			
		}
		
	}
			);
	
	clientbutton=new JButton("Go to client database");
	clientbutton.setPreferredSize(new Dimension(300,50));
	panel.add(clientbutton);
	clientbutton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			
			frame.dispose();
			client=new Client();
			client.setSize(1200,750);
			client.setResizable(false);
			client.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			client.setVisible(true);
			
		}
		
	}
			);
	
	
	frame.setLocationRelativeTo(null);
	frame.getContentPane().add(panel);

	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	panel.setBackground(Color.LIGHT_GRAY);

	
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);
}
public static Connection getConnection(){
	try {


	Class.forName("org.postgresql.Driver");

} catch (ClassNotFoundException e) {

	System.out.println("Where is your PostgreSQL JDBC Driver? "
			+ "Include in your library path!");
	e.printStackTrace();
	

}

System.out.println("PostgreSQL JDBC Driver Registered!");

Connection connection = null;

try {

	connection = DriverManager.getConnection(
			"jdbc:postgresql://127.0.0.1:5432/HotelManagement", "postgres",
			"Oktober");

} catch (SQLException e) {

	System.out.println("Connection Failed! Check output console");
	e.printStackTrace();
	

}

if (connection != null) {
	System.out.println("You made it, take control your database now!");
} else {
	System.out.println("Failed to make connection!");
}
return connection;}
}
