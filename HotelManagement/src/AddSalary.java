import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class AddSalary extends JFrame {
private JTextField cost_unit_numbertext, payment_typetext,payment_datetext, 
amounttext;
public JTextField ssnText,nameText;
private JLabel ssn,name,picture,cost_unit_numberlabel,payment_typelabel, payment_datelabel,amountlabel;
private JButton addsalary,back;
private JComboBox typeList;
Connection con;
Statement stmt = null;


public AddSalary(){
	
	JPanel panel= new JPanel();
	JPanel picturepanel=new JPanel();
	picturepanel.setPreferredSize(new Dimension(700,300));
	
	picture=new JLabel();
    picture.setIcon(new ImageIcon("salary.jpeg"));
    picture.setSize(90,44);
    picture.setVisible(true);
    picturepanel.add(picture);
    panel.add(picturepanel);
    
	JPanel panelsalary=new JPanel();
	panelsalary.setLayout(new BoxLayout(panelsalary,BoxLayout.Y_AXIS));
	
	name=new JLabel("Name");
	nameText=new JTextField();
	nameText.setEditable(false);
	panelsalary.add(name);
	panelsalary.add(nameText);
	
	ssn=new JLabel("SSN");
	ssnText=new JTextField();
	ssnText.setEditable(false);
	panelsalary.add(ssn);
	panelsalary.add(ssnText);
	
	cost_unit_numberlabel=new JLabel("Cost unit number: ");
	cost_unit_numbertext=new JTextField();
	
	String [] room={"credit card","bank transfer"};
	typeList=new JComboBox(room);
	typeList.setSelectedIndex(0);
	
	
	payment_datelabel=new JLabel("Payment date: ");
	payment_datetext=new JTextField();
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Date date = new Date();

	payment_datetext.setText(dateFormat.format(date));
	payment_datetext.setEditable(false);
	
	amountlabel=new JLabel("Amount: ");
	amounttext=new JTextField();
	
	
	
	panelsalary.add(cost_unit_numberlabel);
	panelsalary.add(cost_unit_numbertext);
	panelsalary.add(typeList);
	panelsalary.add(payment_datelabel);
	panelsalary.add(payment_datetext);
	panelsalary.add(amountlabel);
	panelsalary.add(amounttext);
	
	addsalary=new JButton("Add salary");
	panelsalary.add(addsalary);
	addsalary.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {

				if(amounttext.getText().isEmpty()||cost_unit_numbertext.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill out all the fields");
				}else {
	
			if(!amounttext.getText().matches("^[-+]?[0-9]*\\.?[0-9]+$")) {
				JOptionPane.showMessageDialog(null, "Please take the right format of the amount: 00.00");}

			else {
				if(typeList.getSelectedIndex()==0) {
				
					String sql="INSERT INTO COST (COST_UNIT_NUMBER,IDVAT,payment_type,payment_date,amount)"
							+ "VALUES('" + cost_unit_numbertext.getText() + "','IT00299540211','bank transfer','"
							+ payment_datetext.getText() + "',"+ amounttext.getText() +"); "
									+ "INSERT INTO SALARY (COST_UNIT_NUMBER,idssn)"
									+ "VALUES('" +cost_unit_numbertext.getText() +"','"+ssnText.getText()+"');";

				try {
					
					
					con = Hotel.getConnection();
					stmt = con.createStatement();
					stmt.executeUpdate(sql);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}JOptionPane.showMessageDialog(null, "We have successfully added the salary for SSN "+ssnText.getText());
			}
				else if(typeList.getSelectedIndex()==1) {
					String sql="INSERT INTO COST (COST_UNIT_NUMBER,IDVAT,payment_type,payment_date,amount)"
							+ "VALUES('" + cost_unit_numbertext.getText() + "','IT00299540211','bank transfer','"
							+ payment_datetext.getText() + "',"+ amounttext.getText() +"); "
									+ "INSERT INTO SALARY (COST_UNIT_NUMBER,idssn)"
									+ "VALUES('" +cost_unit_numbertext.getText() +"','"+ssnText.getText()+"');";
					
					try {
						con = Hotel.getConnection();
						stmt = con.createStatement();
						stmt.executeUpdate(sql);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}JOptionPane.showMessageDialog(null, "We have successfully added the salary for SSN "+ssnText.getText());
					
					
				}
				
			}
				
			}}
			
		}
		
	);
	
	
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
	
	panelsalary.add(back);

	panel.add(panelsalary);
	add(panel);
}
}