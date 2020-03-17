
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ClientPayment extends JFrame {
 JTextField  idclienttext,cost_unit_numbertext,payment_datetext, 
amounttext, invoicetext;
private JLabel idclientlabel,picture,cost_unit_numberlabel, payment_datelabel,amountlabel, invoicelabel;
private JButton addsalary,back;
private JComboBox typeList;
Connection con = null;
ResultSet resultset = null;
Statement stmt = null;



public ClientPayment(){
	
	JPanel panel= new JPanel();
	JPanel picturepanel=new JPanel();
	picturepanel.setPreferredSize(new Dimension(700,300));
	
	picture=new JLabel();
    picture.setIcon(new ImageIcon("guestpay.jpeg"));
    picture.setSize(90,44);
    picture.setVisible(true);
    picturepanel.add(picture);
    panel.add(picturepanel);
    
	JPanel panelcost=new JPanel();
	panelcost.setLayout(new BoxLayout(panelcost,BoxLayout.Y_AXIS));
	
	idclientlabel=new JLabel("ID");
	idclienttext=new JTextField();
	idclienttext.setEditable(false);
	panelcost.add(idclientlabel);
	panelcost.add(idclienttext);
	
	cost_unit_numberlabel=new JLabel("Cost unit number: ");
	cost_unit_numbertext=new JTextField();
	
	String [] paymenttype={"credit card","bank transfer"};
	typeList=new JComboBox(paymenttype);
	typeList.setSelectedIndex(0);
	
	
	payment_datelabel=new JLabel("Payment date: ");
	payment_datetext=new JTextField();
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Date date = new Date();
	payment_datetext.setText(dateFormat.format(date));
	payment_datetext.setEditable(false);
	
	amountlabel=new JLabel("Amount: ");
	amounttext=new JTextField();
	
	invoicelabel=new JLabel("Invoice number: ");
	invoicetext=new JTextField();
	
	
	panelcost.add(idclientlabel);
	panelcost.add(idclienttext);
	panelcost.add(cost_unit_numberlabel);
	panelcost.add(cost_unit_numbertext);
	panelcost.add(typeList);
	panelcost.add(payment_datelabel);
	panelcost.add(payment_datetext);
	panelcost.add(amountlabel);
	panelcost.add(amounttext);
	panelcost.add(invoicelabel);
	panelcost.add(invoicetext);
	
	addsalary=new JButton("Add guestpayment");
	panelcost.add(addsalary);
	addsalary.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(typeList.getSelectedIndex()==0 && !(cost_unit_numbertext.getText().isEmpty() ||amounttext.getText().isEmpty() 
					|| invoicetext.getText().isEmpty()) && amounttext.getText().matches("-?\\d+(.\\d+)?") ){
				try {
					
					con = Hotel.getConnection();
					
			       String sql= "INSERT INTO COST (cost_unit_number,idvat,payment_type,payment_date,amount)"
							+ "VALUES('" + cost_unit_numbertext.getText() + "','IT00299540211','credit card','"
								+ payment_datetext.getText() + "'," + amounttext.getText() + " ); " +"INSERT INTO GUESTPAYMENT (cost_unit_number, idclient, invoice_number) VALUES ('"+cost_unit_numbertext.getText()+
					    		   "', "+idclienttext.getText()+", '"+invoicetext.getText()+"');";
			       
			        stmt = con.createStatement();
			        PreparedStatement preparedStmt = con.prepareStatement(sql);
			        
			        preparedStmt.execute();
			        
			        con.close();
			        
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		
				
			}else if (typeList.getSelectedIndex()==1 && !(cost_unit_numbertext.getText().isEmpty() ||amounttext.getText().isEmpty() 
					|| invoicetext.getText().isEmpty()) && amounttext.getText().matches("-?\\d+(.\\d+)?") ){
				try {
					
					con = Hotel.getConnection();
					
			       String sql="INSERT INTO COST (cost_unit_number,idvat,payment_type,payment_date,amount)"
							+ "VALUES('" + cost_unit_numbertext.getText() + "','IT00299540211','bank transfer', '"
								+ payment_datetext.getText() + "'," + amounttext.getText() + " ); "+ "INSERT INTO GUESTPAYMENT (cost_unit_number, idclient, invoice_number) VALUES ('"+cost_unit_numbertext.getText()+
					    		   "', "+idclienttext.getText()+", '"+invoicetext.getText()+"');";
			       stmt = con.createStatement();
			        PreparedStatement preparedStmt = con.prepareStatement(sql);
			        
			        preparedStmt.execute();
			        
			        con.close();
			        
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		
				
			}else if (typeList.getSelectedIndex()==0 ||  typeList.getSelectedIndex()==1 && !(cost_unit_numbertext.getText().isEmpty() ||amounttext.getText().isEmpty() 
					|| invoicetext.getText().isEmpty() && amounttext.getText().matches("-?\\d+(.\\d+)?") 
					|| invoicetext.getText().isEmpty()) && amounttext.getText().matches("-?\\d+(.\\d+)?") && !amounttext.getText().matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
				JOptionPane.showMessageDialog(null, "Please take the right format of the amount: 00.00");}
				
		else if(typeList.getSelectedIndex()==0 ||  typeList.getSelectedIndex()==1 && (cost_unit_numbertext.getText().isEmpty() ||amounttext.getText().isEmpty() 
					|| invoicetext.getText().isEmpty() && amounttext.getText().matches("-?\\d+(.\\d+)?") 
					|| invoicetext.getText().isEmpty())){
			JOptionPane.showMessageDialog(null, "Please fill al the fields in order to add the guest payment.");
			
			
		}JOptionPane.showMessageDialog(null, "We have successfully added the salary for client number "+idclienttext.getText());
		}	
		
	});
	
	
	back=new JButton("<<");
	back.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {
		
			dispose();
			
			Employee employee=new Employee();
			employee.setSize(1200,700);
			employee.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			employee.setVisible(true);
			
		}
		
	});
	
	panelcost.add(back);

	panel.add(panelcost);
	add(panel);
}
}