import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddNewEmployee extends JFrame{
	private JTextField nametext, ssntext, professiontext, contracttypetext, contractstarttext, contractendtext, street_streetnotext, zip_codetext, citytext, countrytext;
	private JLabel namelabel, ssnlabel,professionlabel,contracttypelabel, contractstartlabel, contractendlabel, picture, street_streetnolabel, zip_codelabel, citylabel,countrylabel ;
private JButton addemployeebutton, back;
private JComboBox employeeList;
private Employee employeeclass;
Connection con = null;
ResultSet resultset = null;

Statement stmt = null;
	
	
	public AddNewEmployee(){
		
		JPanel panel= new JPanel();
		
		JPanel picturepanel=new JPanel();
		picturepanel.setPreferredSize(new Dimension(700,300));
		
		picture=new JLabel();
        picture.setIcon(new ImageIcon("team.png"));
       picture.setSize(90,44);
        picture.setVisible(true);
        
		
		JPanel paneltext=new JPanel();
		paneltext.setPreferredSize(new Dimension(300,300));
		paneltext.setLayout(new BorderLayout());
		paneltext.setLayout(new BoxLayout( paneltext,BoxLayout.Y_AXIS));
		
		namelabel=new JLabel("Name: ");
		nametext=new JTextField();
		
		ssnlabel=new JLabel("Ssn: ");
		ssntext=new JTextField();
		
		professionlabel=new JLabel("Profession: ");
		professiontext=new JTextField();

		contracttypelabel=new JLabel("Cotract type: ");
		contracttypetext=new JTextField();
		
		contractstartlabel=new JLabel("Contract start: ");
		contractstarttext=new JTextField();
		
		contractendlabel=new JLabel("Contract end: ");
		contractendtext=new JTextField();
		
		
		street_streetnolabel=new JLabel("Street and street number: ");
		street_streetnotext=new JTextField();
		
		zip_codelabel=new JLabel("Zip code: ");
		zip_codetext=new JTextField();
		
		citylabel=new JLabel("City: ");
		citytext=new JTextField(); 
		
		countrylabel=new JLabel("Country: ");
		countrytext =new JTextField();
		
		
		String [] employee={"Select Staff","Housekeeping Staff","Gastronomy Staff", "Reception Staff"};
		employeeList=new JComboBox(employee);
		employeeList.setSelectedIndex(0);
		
		
		addemployeebutton=new JButton("Add new employee");
		addemployeebutton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
			
				if(employeeList.getSelectedIndex() == 0  &&(
						nametext.getText().isEmpty() || ssntext.getText().isEmpty()||
						professiontext.getText().isEmpty() || contracttypetext.getText().isEmpty() || contractstarttext.getText().isEmpty()
						|| contractendtext.getText().isEmpty() 
						|| street_streetnotext.getText().isEmpty() || zip_codetext.getText().isEmpty() ||
						citytext.getText().isEmpty() || countrytext.getText().isEmpty())){
					JOptionPane.showMessageDialog(null, "Please fill all the fields and select the right staff in which to insert the new employee. ");
				} else if(employeeList.getSelectedIndex() == 1 &&(
						nametext.getText().isEmpty() || ssntext.getText().isEmpty()||
						professiontext.getText().isEmpty() || contracttypetext.getText().isEmpty() || contractstarttext.getText().isEmpty()
						|| contractendtext.getText().isEmpty()  || street_streetnotext.getText().isEmpty() || zip_codetext.getText().isEmpty() ||
						citytext.getText().isEmpty() || countrytext.getText().isEmpty())){
					JOptionPane.showMessageDialog(null, "Please fill all the required fields in order to add a new employee.");
					
				}else if(employeeList.getSelectedIndex() == 1 && !(
						nametext.getText().isEmpty() || ssntext.getText().isEmpty()||
						professiontext.getText().isEmpty() || contracttypetext.getText().isEmpty() || contractstarttext.getText().isEmpty()
						|| contractendtext.getText().isEmpty() || street_streetnotext.getText().isEmpty() || zip_codetext.getText().isEmpty() ||
						citytext.getText().isEmpty() || countrytext.getText().isEmpty())){
					
					
					
					try {
						
						con = Hotel.getConnection();
						
				       String sql=" INSERT INTO EMPLOYEE (ssn,idsupervisor, idvat, name, profession) VALUES ('"+ssntext.getText()+"','PLARSL70E19Z404Q',"+"'IT00299540211', '"+
				    		   nametext.getText()+"', '"+professiontext.getText()+"');"+"INSERT INTO HOUSEKEEPINGSTAFF (ssn,contract_type) VALUES ('"+nametext.getText()+"','"+ssntext.getText()+"');"
				    		   +"INSERT INTO EMPLOYEE_HOTEL (idssn,idvat,contract_type,contract_start, contract_end) VALUES ('"+ssntext.getText()+"',"+
				    		   "'IT00299540211', '"+contracttypetext.getText()+"', '"+contractstarttext.getText()+"', '"+contractendtext.getText()+
				    		   "');" + "INSERT INTO ADDRESS(idssn, street_streetno, zip_code, city,country) VALUES ('"+ssntext.getText()+"', '"+street_streetnotext.getText()+
				    		   "', '"+zip_codetext.getText()+"', '"+citytext.getText()+"', '"+countrytext.getText()+"');";
				       
				        stmt = con.createStatement();
				        PreparedStatement preparedStmt = con.prepareStatement(sql);
				        
				        preparedStmt.execute();
				        
				        con.close();
				        
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					
				}else if(employeeList.getSelectedIndex() == 2 && !(
						nametext.getText().isEmpty() || ssntext.getText().isEmpty()||
						professiontext.getText().isEmpty() || contracttypetext.getText().isEmpty() || contractstarttext.getText().isEmpty()
						|| contractendtext.getText().isEmpty()  || street_streetnotext.getText().isEmpty() || zip_codetext.getText().isEmpty() ||
						citytext.getText().isEmpty() || countrytext.getText().isEmpty())){
					
					if(contracttypetext.getText().equals("full time") ||contracttypetext.getText().equals("part time") ){
						
					try {
						
						con = Hotel.getConnection();
						
				       String sql=" INSERT INTO EMPLOYEE (ssn,idsupervisor, idvat, name, profession) VALUES ('"+ssntext.getText()+"','PLARSL70E19Z404Q',"+"'IT00299540211', '"+
				    		   nametext.getText()+"', '"+professiontext.getText()+"');"+"INSERT INTO GASTRONOMYSTAFF (ssn,assignment_type, full_or_part_time) VALUES ('"+ssntext.getText()+"','"+professiontext.getText()+"', '"
				    		   +contracttypetext.getText()+"');"
				    		   +"INSERT INTO EMPLOYEE_HOTEL (idssn,idvat,contract_type,contract_start, contract_end) VALUES ('"+ssntext.getText()+"',"+
				    		   "'IT00299540211', '"+contracttypetext.getText()+"', '"+contractstarttext.getText()+"', '"+contractendtext.getText()+
				    		   "');" + "INSERT INTO ADDRESS(idssn, street_streetno, zip_code, city,country) VALUES ('"+ssntext.getText()+"', '"+street_streetnotext.getText()+
				    		   "', '"+zip_codetext.getText()+"', '"+citytext.getText()+"', '"+countrytext.getText()+"');";
				    		 
				       
				        stmt = con.createStatement();
				        PreparedStatement preparedStmt = con.prepareStatement(sql);
				        
				        preparedStmt.execute();
				        
				        con.close();
				        
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}}else{
						
							JOptionPane.showMessageDialog(null, "The field contract type support only 'full time' or 'part time' words." );
						
						
						
					}
					
					
					
				}else if(employeeList.getSelectedIndex() == 3 && !(
						nametext.getText().isEmpty() || ssntext.getText().isEmpty()||
						professiontext.getText().isEmpty() || contracttypetext.getText().isEmpty() || contractstarttext.getText().isEmpty()
						|| contractendtext.getText().isEmpty() || street_streetnotext.getText().isEmpty() || zip_codetext.getText().isEmpty() ||
						citytext.getText().isEmpty() || countrytext.getText().isEmpty())){
					
					if(contracttypetext.getText().equals("full time") ||contracttypetext.getText().equals("part time") ){
					
					try {
						
						con = Hotel.getConnection();
						
				       String sql=" INSERT INTO EMPLOYEE (ssn,idsupervisor, idvat, name, profession) VALUES ('"+ssntext.getText()+"','PLARSL70E19Z404Q',"+"'IT00299540211', '"+
				    		   nametext.getText()+"', '"+professiontext.getText()+"');"+"INSERT INTO RECEPTIONSTAFF (ssn, full_or_part_time) VALUES ('"+ssntext.getText()+"', '"
				    		   +contracttypetext.getText()+"');"
				    		   +"INSERT INTO EMPLOYEE_HOTEL (idssn,idvat,contract_type,contract_start, contract_end) VALUES ('"+ssntext.getText()+"',"+
				    		   "'IT00299540211', '"+contracttypetext.getText()+"', '"+contractstarttext.getText()+"', '"+contractendtext.getText()+
				    		   "');" + "INSERT INTO ADDRESS(idssn, street_streetno, zip_code, city,country) VALUES ('"+ssntext.getText()+"', '"+street_streetnotext.getText()+
				    		   "', '"+zip_codetext.getText()+"', '"+citytext.getText()+"', '"+countrytext.getText()+"')";
				       
				        stmt = con.createStatement();
				        PreparedStatement preparedStmt = con.prepareStatement(sql);
				        
				        preparedStmt.execute();
				        
				        con.close();
				        
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}}else{
						JOptionPane.showMessageDialog(null, "The field contract type support only 'full time' or 'part time' words." );
					}
					
					
					
				}
			}
			
			});
		
		back=new JButton("<<");
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
			
				dispose();
				
				employeeclass=new Employee();
				employeeclass.setSize(1200,700);
				employeeclass.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				employeeclass.setVisible(true);
				
			}
			
		});
		
		
		paneltext.add(ssnlabel);
		paneltext.add(ssntext);
		paneltext.add(namelabel);
		paneltext.add(nametext);
		paneltext.add(professionlabel);
		paneltext.add(professiontext);
		paneltext.add(contracttypelabel);
		paneltext.add(contracttypetext);
		paneltext.add(contractstartlabel);
		paneltext.add(contractstarttext);
		paneltext.add(contractendlabel);
		paneltext.add(contractendtext);
		
		
		JPanel paneltext2=new JPanel();
		paneltext2.setLayout(new BoxLayout(paneltext2,BoxLayout.Y_AXIS));
		paneltext2.setPreferredSize(new Dimension(300,300));
		
		
		paneltext2.add(street_streetnolabel);
		paneltext2.add(street_streetnotext);
		paneltext2.add(zip_codelabel);
		paneltext2.add(zip_codetext);
		paneltext2.add(citylabel);
		paneltext2.add(citytext);
		paneltext2.add(countrylabel);
		paneltext2.add(countrytext);
		paneltext2.add(employeeList);
		paneltext2.add(addemployeebutton);
		paneltext2.add(back);
		
		picturepanel.add(picture);
		
		panel.add(picturepanel);
		panel.add(paneltext);
		panel.add(paneltext2);
		
		
		add(panel);
		
		
	}
}
