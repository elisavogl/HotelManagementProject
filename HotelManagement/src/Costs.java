import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Costs extends JFrame{
    private DefaultTableModel model;
    private String columnNames[];
    private TableRowSorter<TableModel> sorter;
    private JTable table;
    private JButton back;
    private Hotel hotel;
    private JLabel labelimage,totalcost, costhousekeepingstaff, costreceptionstaff, costgastronomystaff, costbonus, costclient, runningcosts;
    private JTextField searchtext,totalcosttext, costhousekeepingstafftext, costreceptionstafftext, costgatronomystafftext, costbonustext, costclienttext, runningcoststext,
    costText, paymenttypeText, dateText, amountText;
    private JTextArea costcalculations;
    private JComboBox costList;
    Connection con; 
    ResultSet resultset;
    Statement stmt; 
    
    public Costs(){
    	
    	
        JPanel panel=new JPanel();
        

    	
        JPanel TablePanel=new JPanel();
        
        columnNames = new String[] {"Cost Unit Number", "Payment Type","Payment date", "Amount"};
        table = new JTable(new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        
        
        
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                
              
                
                
                int i = table.getSelectedRow();
				if (i < 0) {
					JOptionPane.showMessageDialog(null,
							"Please note that you have to select a row with content inside.");
				} else {

					int modelIndex = table.convertRowIndexToModel(i);

					String costunitnumber = model.getValueAt(modelIndex, 0).toString();
					String paymenttype = model.getValueAt(modelIndex, 1).toString();
					String paymentdate = model.getValueAt(modelIndex, 2).toString();
					String amount = model.getValueAt(modelIndex, 3).toString();
					

					costText.setText(costunitnumber);
					paymenttypeText.setText(paymenttype);
					dateText.setText(paymentdate);
					amountText.setText(amount);
					
				}
			}
            
        });
        table.setBackground(Color.LIGHT_GRAY);
        table.setForeground(Color.BLACK);
        
        int index = 0;
        while (index < 4) {
			TableColumnModel tablecolumn = table.getColumnModel();
			TableColumn a = tablecolumn.getColumn(index);
			a.setPreferredWidth(1210);
			tablecolumn.getColumn(0).setPreferredWidth(300);
			tablecolumn.getColumn(1).setPreferredWidth(260);
			tablecolumn.getColumn(2).setPreferredWidth(320);
			tablecolumn.getColumn(3).setPreferredWidth(210);
			
			
			index++;
		}

        int A = this.getWidth();
        int B = this.getHeight();

        table.setSize(A, B);
        model = (DefaultTableModel) table.getModel();
        try {
            Object rowData[] = new Object[5];
            String sql="select cost_unit_number,payment_type,payment_date"
                    + ",amount from cost";
            con=Hotel.getConnection(); 
            stmt=con.createStatement();
            resultset = stmt.executeQuery(sql);
            
            while(resultset.next()){
                rowData[0] =resultset.getString("cost_unit_number");
                rowData[1] =resultset.getString("payment_type");
                rowData[2] =resultset.getString("payment_date");
                rowData[3] =resultset.getString("amount");
                
                model.addRow(rowData);
                 
            }
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    
        
        sorter = new TableRowSorter<TableModel>(model);
        table.setRowSorter(sorter);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600, 300));
    
        TablePanel.setBackground(Color.LIGHT_GRAY);
        TablePanel.add(scrollPane);
        
        labelimage=new JLabel();
    	labelimage.setIcon(new ImageIcon("cost-management.jpg"));
    	labelimage.setSize(300,44);
    	labelimage.setVisible(true);
    	TablePanel.add(labelimage);
    	
        panel.add(TablePanel);
        
        JPanel costpanel=new JPanel();
        costpanel.setLayout(new BoxLayout(costpanel,BoxLayout.Y_AXIS));
        
        totalcost=new JLabel("Total costs");
        totalcosttext=new JTextField();
        totalcosttext.setEditable(false);
        totalcosttext.setBackground(Color.LIGHT_GRAY);
        
        costhousekeepingstaff=new JLabel("Total costs for Housekeeping Staff");
        costhousekeepingstafftext=new JTextField();
        costhousekeepingstafftext.setEditable(false);
        costhousekeepingstafftext.setBackground(Color.lightGray);
        
        costgastronomystaff=new JLabel("Total costs for Gastronomy Staff");
        costgatronomystafftext=new JTextField();
        costgatronomystafftext.setEditable(false); 
        costgatronomystafftext.setBackground(Color.lightGray);
        
        costreceptionstaff=new JLabel("Total costs for Reception Staff");
        costreceptionstafftext=new JTextField();
        costreceptionstafftext.setEditable(false); 
        costreceptionstafftext.setBackground(Color.lightGray);
       
        costclient=new JLabel("Client costs");
        costclienttext=new JTextField();
        costclienttext.setEditable(false); 
        costclienttext.setBackground(Color.lightGray);
        
        runningcosts=new JLabel("Running costs");
        runningcoststext=new JTextField();
        runningcoststext.setEditable(false); 
        runningcoststext.setBackground(Color.lightGray);
        
         
     ResultSet rs;
	try {
		String sql="select sum(amount) as amount from cost";
		rs = stmt.executeQuery(sql);
		 while (rs.next()) {
	         double i = rs.getDouble("amount");
	         totalcosttext.setText(i+" €");
		 }} catch (SQLException e1) {
		
		e1.printStackTrace();
	}
	
	
	try {
		String sql1="select  sum(c.amount) as amount   from cost c inner join salary s "
				+ "on s.cost_unit_number=c.cost_unit_number inner join gastronomystaff g on g.ssn=s.idssn ";
		
		rs = stmt.executeQuery(sql1);
		 while (rs.next()) {
	         double i = rs.getDouble("amount");
	         costgatronomystafftext.setText(i+" €");
		 }} catch (SQLException e1) {
		
		e1.printStackTrace();
	}
	
	try{
	String sql2="select  sum(c.amount) as amount from cost c inner join salary s "
			+ "on s.cost_unit_number=c.cost_unit_number inner join receptionstaff r on r.ssn=s.idssn ";
	
	rs = stmt.executeQuery(sql2);
	 while (rs.next()) {
        double i = rs.getDouble("amount");
        costreceptionstafftext.setText(i+" €");
}} catch (SQLException e1) {
		
		e1.printStackTrace();
	}

	try{
		String sql3="select  sum(c.amount) as amount from cost c inner join salary s "
				+ "on s.cost_unit_number=c.cost_unit_number inner join housekeepingstaff h on h.ssn=s.idssn ";
		
		rs = stmt.executeQuery(sql3);
		 while (rs.next()) {
	        double i = rs.getDouble("amount");
	        costhousekeepingstafftext.setText(i+" €");
	}} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
	
	
			try{
				String sql4="select  sum(c.amount) as amount from cost c inner join guestpayment g on g.cost_unit_number=c.cost_unit_number ";
				
				rs = stmt.executeQuery(sql4);
				 while (rs.next()) {
			        double i = rs.getDouble("amount");
			        costclienttext.setText(i+" €");
			}} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			
			
			try{
				String sql5="select  sum(c.amount) as amount from cost c inner join running_cost r on r.cost_unit_number=c.cost_unit_number   ";
				
				rs = stmt.executeQuery(sql5);
				 while (rs.next()) {
			        double i = rs.getDouble("amount");
			        runningcoststext.setText(i+" €");
			}} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			
        costpanel.add(totalcost);
        costpanel.add(totalcosttext);
        costpanel.add(costhousekeepingstaff);
        costpanel.add(costhousekeepingstafftext);
        costpanel.add(costgastronomystaff);
        costpanel.add(costgatronomystafftext);
        costpanel.add(costreceptionstaff);
        costpanel.add(costreceptionstafftext);
        costpanel.add(costclient);
        costpanel.add(costclienttext);
        costpanel.add(runningcosts);
        costpanel.add(runningcoststext);
        
        panel.add(costpanel);
        
        JPanel textPanel=new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel,BoxLayout.Y_AXIS));
        
        JLabel costunitnumber=new JLabel("Cost unit number:");
        costText=new JTextField();
        textPanel.add(costunitnumber);
        textPanel.add(costText);
        
        JLabel paymenttype=new JLabel("Payment type:");
        paymenttypeText=new JTextField();
        textPanel.add(paymenttype);
        textPanel.add(paymenttypeText);
        
        JLabel date=new JLabel("Payment date:");
        dateText=new JTextField();
        textPanel.add(date);
        textPanel.add(dateText);
        
        JLabel amount=new JLabel("Amount:");
        amountText=new JTextField();
        textPanel.add(amount);
        textPanel.add(amountText);
        
        
        costText.setEditable(false);
		paymenttypeText.setEditable(false);
		dateText.setEditable(false);
        
        JButton updatebutton=new JButton("Update");
		textPanel.add(updatebutton);
		updatebutton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String cost=costText.getText();
				String paymenttype=paymenttypeText.getText();
				String date=dateText.getText();
				String amount=amountText.getText();
				
				
				
				if (!amount.matches("^[-+]?[0-9]*\\.?[0-9]+$") || amount.isEmpty()){
					JOptionPane.showMessageDialog(null, "Please note that the amount field must hold a floating point number.");
				}else{
					con=Hotel.getConnection(); 
					
					try {
						PreparedStatement ps = con.prepareStatement(
								"UPDATE cost SET  amount = "+amount+" WHERE cost_unit_number ='"+cost+"';");
						
					    ps.executeUpdate();
					    ps.close();
					  
					    int modelindex = table.getSelectedRow();
					    int i=table.convertRowIndexToModel(modelindex);
		                
		                if(i >= 0) 
		                {
		                   model.setValueAt(cost, i, 0);
		                   model.setValueAt(paymenttype, i,1 );
		                   model.setValueAt(date, i, 2);
		                   model.setValueAt(amount, i, 3);
		                  
		                }
		                else{
		                  JOptionPane.showMessageDialog(null, "Please select an entry in the table to update.");
		                }
								
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Please check the fields inserted! Unable to update the file.");
					}

						  
						    
				
					
				}
				
			}});
        
        panel.add(textPanel);
        
        
        JPanel areapanel=new JPanel();
        costcalculations=new JTextArea(10,10);
        costcalculations.setEditable(false);
        areapanel.add(costcalculations);
        
        
        panel.add(areapanel);
        
       JPanel calculatePanel=new JPanel();
       calculatePanel.setLayout(new BoxLayout(calculatePanel,BoxLayout.Y_AXIS));
       
       
        JLabel from=new JLabel("From: ");
        JTextField fromText=new JTextField();
        JLabel to=new JLabel("To: ");
        JTextField toText=new JTextField();
        calculatePanel.add(from);
        calculatePanel.add(fromText);
        calculatePanel.add(to);
        calculatePanel.add(toText);
        
        JButton earningsButton=new JButton("Earnings from clients");
        calculatePanel.add(earningsButton);
        earningsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(fromText.getText().isEmpty() || toText.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Please fill all fields to calculate earnings from client.");
					
				}else if(!fromText.getText().isEmpty() && !toText.getText().isEmpty() && (!fromText.getText().matches("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))") || !toText.getText().matches("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))"))){
					JOptionPane.showMessageDialog(null, "Invalid date format");
				}
				
				
				else if(fromText.getText().matches("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))") && toText.getText().matches("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))")&& 
						(!fromText.getText().isEmpty() || !toText.getText().isEmpty())  ){
					String sql="select sum(amount) as amount from cost c inner join guestpayment g on c.cost_unit_number=g.cost_unit_number where c.payment_date>='"+fromText.getText()+"' and c.payment_date<='"+toText.getText()+"';";
			
					 try{
			        	 PreparedStatement ps = con.prepareStatement(sql);
			             ResultSet rs = ps.executeQuery();

			             while(rs.next()) {
			            	 
			            	
			            	String sqlstring=rs.getString("amount");
			            				
			    			    
			    				costcalculations.setText(sqlstring+" €");           	
			                
			           
			             }
			              



			    }catch (Exception e1) { System.out.println(e1); }
			
			
				
				
				}
				
			}
        	
        });
        
        JButton runningcostButton=new JButton("Running costs");
        calculatePanel.add(runningcostButton);
        runningcostButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		
				if(fromText.getText().isEmpty() || toText.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Please fill all fields to calculate earnings from client.");
					
				}else if(!fromText.getText().isEmpty() && !toText.getText().isEmpty() && (!fromText.getText().matches("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))") || !toText.getText().matches("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))"))){
					JOptionPane.showMessageDialog(null, "Invalid date format");
				}
				
				
				else if(fromText.getText().matches("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))") && toText.getText().matches("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))")&& 
						(!fromText.getText().isEmpty() || !toText.getText().isEmpty())   ){
					String sql="select sum(amount) as amount from cost c inner join running_cost rc on c.cost_unit_number=rc.cost_unit_number where c.payment_date>='"+fromText.getText()+"' and c.payment_date<='"+toText.getText()+"';";
			
					 try{
			        	 PreparedStatement ps = con.prepareStatement(sql);
			             ResultSet rs = ps.executeQuery();

			             while(rs.next()) {
			            	 
			            	
			            	String sqlstring=rs.getString("amount");
			            				
			    			    
			    				costcalculations.setText(sqlstring+" €");           	
			                
			           
			             }
			              



			    }catch (Exception e1) { System.out.println(e1); }
			
			
				
				
				}
				
			}
        	
        });
			
        
        
        panel.add(calculatePanel);
        
		JPanel buttonPanel=new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.Y_AXIS));
		
		JLabel search=new JLabel("Search in the whole list:");
		buttonPanel.add(search);
		
		searchtext = new JTextField("Cost Unit Number");
		searchtext.setForeground(Color.GRAY);
		searchtext.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				String text = searchtext.getText();

				if (text.trim().length() == 0) {
					sorter.setRowFilter(null);
				} else {
					sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0));
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				String text = searchtext.getText();

				if (text.trim().length() == 0) {
					sorter.setRowFilter(null);
				} else {
					sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0));
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods,
																				// choose Tools | Templates.
			}
		});
		searchtext.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {

				searchtext.setText("");
				repaint();
				revalidate();
				searchtext.setForeground(Color.black);

			}

		});
		
		buttonPanel.add(searchtext);
		
		String[] cost = { "Select", "Guest payment", "Salary", "Running costs" };
		costList = new JComboBox(cost);
		costList.setSelectedIndex(0);
		buttonPanel.add(costList);
		
		JButton typecostbutton=new JButton("Show costs");
		buttonPanel.add(typecostbutton);
		typecostbutton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(costList.getSelectedIndex() == 0){
					JOptionPane.showMessageDialog(null, "Please choose a specific parameter");
					
				}else if(costList.getSelectedIndex() == 1){
					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}

					try {
						Object rowData[] = new Object[4];
					String sql="select g.cost_unit_number, c.payment_type, c.payment_date, c.amount from guestpayment g inner join cost c on g.cost_unit_number=c.cost_unit_number";
		            con=Hotel.getConnection(); 
		            stmt=con.createStatement();
		            resultset = stmt.executeQuery(sql);
		            
		            while(resultset.next()){
		                rowData[0] =resultset.getString("cost_unit_number");
		                rowData[1] =resultset.getString("payment_type");
		                rowData[2] =resultset.getString("payment_date");
		                rowData[3] =resultset.getString("amount");
		                
		                model.addRow(rowData);
		                 
		            }
		        } catch (SQLException e1) {
		            // TODO Auto-generated catch block
		            e1.printStackTrace();
		        }
					
					
				}else if(costList.getSelectedIndex() == 2){
					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}

					try {
						Object rowData[] = new Object[4];
					String sql="select s.cost_unit_number, c.payment_type, c.payment_date, c.amount from salary s inner join cost c on s.cost_unit_number=c.cost_unit_number";
		            con=Hotel.getConnection(); 
		            stmt=con.createStatement();
		            resultset = stmt.executeQuery(sql);
		            
		            while(resultset.next()){
		                rowData[0] =resultset.getString("cost_unit_number");
		                rowData[1] =resultset.getString("payment_type");
		                rowData[2] =resultset.getString("payment_date");
		                rowData[3] =resultset.getString("amount");
		                
		                model.addRow(rowData);
		                 
		            }
		        } catch (SQLException e1) {
		            // TODO Auto-generated catch block
		            e1.printStackTrace();
		        }
					
				}else if(costList.getSelectedIndex() == 3){
					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}

					try {
						Object rowData[] = new Object[4];
					String sql="select r.cost_unit_number, c.payment_type, c.payment_date, c.amount from running_cost r inner join cost c on r.cost_unit_number=c.cost_unit_number";
		            con=Hotel.getConnection(); 
		            stmt=con.createStatement();
		            resultset = stmt.executeQuery(sql);
		            
		            while(resultset.next()){
		                rowData[0] =resultset.getString("cost_unit_number");
		                rowData[1] =resultset.getString("payment_type");
		                rowData[2] =resultset.getString("payment_date");
		                rowData[3] =resultset.getString("amount");
		                
		                model.addRow(rowData);
		                 
		            }
		        } catch (SQLException e1) {
		            // TODO Auto-generated catch block
		            e1.printStackTrace();
		        }
					
				}
					
			}
			
		});
		
		JButton showall=new JButton("Show all costs");
		showall.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}
				try {
					Object rowData[] = new Object[4];
					String sql = "select cost_unit_number, payment_type, payment_date, amount from cost";

					con = Hotel.getConnection();
					stmt = con.createStatement();
					resultset = stmt.executeQuery(sql);
					while (resultset.next()) {
						rowData[0] = resultset.getString("cost_unit_number");
						rowData[1] = resultset.getString("payment_type");
						rowData[2] = resultset.getString("payment_date");
						rowData[3] = resultset.getString("amount");

						model.addRow(rowData);

					}}catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			
			}
			
			
		});
		
		
		
		
		buttonPanel.add(showall);
		
		
		panel.add(buttonPanel);
 
    back=new JButton("<<");
    back.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
        
            dispose();
            
            hotel=new Hotel();
            
        }
        
    });
   
    panel.add(back);
    
   
    add(panel);
   
   
    }
}