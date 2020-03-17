import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class RoomOrganization extends JFrame {
	private JPanel panel;
	private JButton back, availablebutton, occupiedbutton, allbutton, updatebutton, deletebutton, addroombutton;
	private JTextField arrivaltext, departuretext, roomnotext, roomtypetext, viewtext, pricetext;
	private Hotel hotel;
	private TableRowSorter<TableModel> sorter;
	private DefaultTableModel model;
	private JTable table;
	private JLabel labelimage,title,arrivallabel, departurelabel, roomnolabel, roomtypelabel, viewlabel, pricelabel;
	private String [] columnNames;
	private JComboBox roomList, viewList;
	Connection con;
	ResultSet resultset;
	Statement stmt;
	
	public RoomOrganization(){

		panel=new JPanel();
	
		JPanel picturepanel=new JPanel ();
		
	

		labelimage=new JLabel();
		labelimage.setIcon(new ImageIcon("room.jpg"));
		labelimage.setSize(300,44);
		labelimage.setVisible(true);

		
		panel.add(labelimage);
		
		
		
		JPanel textpanel=new JPanel();
		textpanel.setLayout(new BoxLayout(textpanel,BoxLayout.Y_AXIS));
		roomnolabel=new JLabel("Room number: ");
		roomnolabel.setFont(new Font("Serif", Font.BOLD,16));
		roomnotext=new JTextField();
		textpanel.add(roomnolabel);
		textpanel.add(roomnotext);
		
		roomtypelabel=new JLabel("Room type: ");
		roomtypelabel.setFont(new Font("Serif", Font.BOLD,16));
		roomtypetext=new JTextField();
		textpanel.add(roomtypelabel);
		textpanel.add(roomtypetext);
		
		viewlabel=new JLabel("View: ");
		viewlabel.setFont(new Font("Serif", Font.BOLD,16));
		viewtext=new JTextField();
		textpanel.add(viewlabel);
		textpanel.add(viewtext);
		
		pricelabel=new JLabel("Price: ");
		pricelabel.setFont(new Font("Serif", Font.BOLD,16));
		pricetext=new JTextField();
		textpanel.add(pricelabel);
		textpanel.add(pricetext);
		
		updatebutton=new JButton("Update");
		updatebutton.setFont(new Font("Serif", Font.BOLD,16));
		textpanel.add(updatebutton);
		updatebutton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				String roomno=roomnotext.getText();
				String roomtype=roomtypetext.getText();
				String viewstring=viewtext.getText();
				String price=pricetext.getText();
				
				
				if (!price.matches("^[-+]?[0-9]*\\.?[0-9]+$") || roomno.isEmpty()||roomtype.isEmpty() || viewstring.isEmpty() || price.isEmpty()){
					JOptionPane.showMessageDialog(null, "Please note that all the fields must be completed and the price field must hold a floating point number");
				}
				else{
				
				con=Hotel.getConnection(); 
				JOptionPane.showMessageDialog(null, "Please note that the field id cannot be changed");
				roomnotext.setEditable(false);
				try {
					PreparedStatement ps = con.prepareStatement(
						      "UPDATE room SET room_type = '"+roomtype+"', view = '"+viewstring+"', price ="+price+" WHERE room_number ="+roomno+";");
					
					

				    // call executeUpdate to execute our sql update statement
				    ps.executeUpdate();
				    ps.close();
				  
				    int modelindex = table.getSelectedRow();
				    int i=table.convertRowIndexToModel(modelindex);
	                
	                if(i >= 0) 
	                {
	                   model.setValueAt(roomtype, i, 1);
	                   model.setValueAt(viewstring, i, 2);
	                   model.setValueAt(price, i, 3);
	                  
	                  
	                }
	                else{
	                  JOptionPane.showMessageDialog(null, "Please choose a room to update");
	                }
							
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Please check the fields inserted!Unable to update the file");
				}

					  
					    
			
				
			}
			
		}});
		
		deletebutton=new JButton("Delete");
		deletebutton.setFont(new Font("Serif", Font.BOLD,16));
		textpanel.add(deletebutton);
		deletebutton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				if (table.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Please choose a room in the table.");
				}else{
					
				
			
				String roomno=roomnotext.getText();
				String roomtype=roomtypetext.getText();
				String viewstring=viewtext.getText();
				String price=pricetext.getText();
				
				roomnotext.setEditable(false);
				roomtypetext.setEditable(false);
				viewtext.setEditable(false);
				pricetext.setEditable(false);
				
				try {
					PreparedStatement ps = con.prepareStatement(
						      "DELETE FROM room WHERE room_number="+roomno+" and room_type ='"+roomtype+"'and view ='"+viewstring+"' and price="+price+";");
					
					ps.executeUpdate();
					
					int modelIndex = table.convertRowIndexToModel(table.getSelectedRow());
					model.removeRow(modelIndex);

			            JOptionPane.showMessageDialog(null,"We have successfully deleted the room "+roomno);
				} catch (SQLException e1) {
				
					e1.printStackTrace();
				}
				
				

				
			
				
			}
			
		}});
		
		addroombutton=new JButton("Add new room");
		addroombutton.setFont(new Font("Serif", Font.BOLD,16));
		textpanel.add(addroombutton);
		addroombutton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				String roomno=roomnotext.getText();
				String roomtype=roomtypetext.getText();
				String viewstring=viewtext.getText();
				String price=pricetext.getText();
			
				
				if(!roomno.matches("-?\\d+")||!price.matches("^[-+]?[0-9]*\\.?[0-9]+$")||roomno.isEmpty() || roomtype.isEmpty() || viewstring.isEmpty() || price.isEmpty()){
					JOptionPane.showMessageDialog(null, "Please note that you have to complete all the fields");
					
				}
				else{
					Object[] row = new Object[4];

				row[0] = roomno;
				row[1] = roomtype;
				row[2] = viewstring;
				row[3] = price;
				model.addRow(row);
					PreparedStatement ps;
					try {
						ps = con.prepareStatement(
							      "INSERT INTO room (room_number,room_type,view,price)VALUES("+roomno+",'"+roomtype+"','"+viewstring+"',"+price+");");
						ps.executeUpdate();
						JOptionPane.showMessageDialog(null, "We have added the room "+roomno+" successfully");
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "It was not possible to add the room. Please try again and check your fields");
					}
					
				
				}
				
			}
			
		});
		
		panel.add(textpanel);
		
		JPanel tablePanel = new JPanel(new BorderLayout());
		tablePanel.setPreferredSize(new Dimension(800, 300));
		columnNames = new String[] {"Room number", "Room type", "View", "Price"};
		
		table = new JTable(new DefaultTableModel(columnNames, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				

int i = table.getSelectedRow();
				if (i<0){
					JOptionPane.showMessageDialog(null, "Please note that you have to select a row with content inside.");
				}else{
				
					
				int modelIndex = table.convertRowIndexToModel(i);
				
				roomnotext.setText(model.getValueAt(modelIndex, 0).toString());
				roomtypetext.setText(model.getValueAt(modelIndex, 1).toString());
				viewtext.setText(model.getValueAt(modelIndex, 2).toString());
				pricetext.setText(model.getValueAt(modelIndex, 3).toString());
				
				
				
			

			}
			}
		});
		table.setBackground(Color.darkGray);
		table.setForeground(Color.white);

		model = (DefaultTableModel) table.getModel();
		try {
			Object rowData[] = new Object[4];
			String sql="select * from room";
			con=Hotel.getConnection(); 
			stmt=con.createStatement();
			resultset = stmt.executeQuery(sql);
			while(resultset.next()){
				rowData[0] =resultset.getString("room_number");
				rowData[1] =resultset.getString("room_type");
				rowData[2] =resultset.getString("view");
				rowData[3] =resultset.getString("price");
				

				
				model.addRow(rowData);
				 
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		sorter = new TableRowSorter<TableModel>(model);
		table.setRowSorter(sorter);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(500, 300));
		tablePanel.add(scrollPane);
		panel.add(tablePanel);
		
		JPanel searchPanel=new JPanel();
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
		
		/*Border border = searchPanel.getBorder();
		Border margin = new LineBorder(Color.gray,4);
		searchPanel.setBorder(new CompoundBorder(border, margin));*/
		
		arrivallabel=new JLabel("Arrival date: ");
		arrivallabel.setFont(new Font("Serif", Font.BOLD,16));
		arrivaltext=new JTextField();
		searchPanel.add(arrivallabel);
		searchPanel.add(arrivaltext);
		
		departurelabel=new JLabel("Departure date: ");
		departurelabel.setFont(new Font("Serif", Font.BOLD,16));
		departuretext=new JTextField();
		searchPanel.add(departurelabel);
		searchPanel.add(departuretext);
		
		String [] view={"Select","Cathedral","Hotel Park", "Parking"};
		viewList=new JComboBox(view);
		viewList.setFont(new Font("Serif", Font.BOLD,16));
		viewList.setSelectedIndex(0);
		searchPanel.add(viewList);
		
		String [] room={"Select","Single Room","Double Room"};
		roomList=new JComboBox(room);
		roomList.setFont(new Font("Serif", Font.BOLD,16));
		roomList.setSelectedIndex(0);
		searchPanel.add(roomList);
		
		panel.add(searchPanel);
		
		
		
		
		availablebutton=new JButton("Show available rooms");
		availablebutton.setFont(new Font("Serif", Font.BOLD,16));
		searchPanel.add(availablebutton);
		availablebutton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				
				if(arrivaltext.getText().isEmpty() || departuretext.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Please enter the arrival and departure fields");
				}else{
					if (!arrivaltext.getText().matches("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))") && !departuretext.getText().matches(".([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))")) {		
						JOptionPane.showMessageDialog(null, "Wrong date format, please enter the following format: YYYY-MM-DD");}
					else{
				if(roomList.getSelectedIndex()==0 && viewList.getSelectedIndex()==0){
					while(model.getRowCount() > 0)
					{
					    model.removeRow(0);
					}
					
				try {
					Object rowData[] = new Object[4];
					String sql="select * from room rm where rm.room_number not in (select distinct idroomno from "
							+ "booking_room bk where (bk.arrival_date between '"+ arrivaltext.getText()+"' and '"+
							departuretext.getText()+ "') or (bk.departure_date between '"+arrivaltext.getText()
							+"' and '"+departuretext.getText()+ "') or (bk.arrival_date<'"+arrivaltext.getText()  +"'"
									+ "and bk.departure_date>'"+departuretext.getText()+"')) " ;


					con=Hotel.getConnection(); 
					stmt=con.createStatement();
					resultset = stmt.executeQuery(sql);
					while(resultset.next()){
						rowData[0] =resultset.getString("room_number");
						rowData[1] =resultset.getString("room_type");
						rowData[2]=resultset.getString("view");
						rowData[3]=resultset.getString("price");
						
						model.addRow(rowData);
						
						 
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}}
				else if (roomList.getSelectedIndex()==1 && viewList.getSelectedIndex()==0){
					while(model.getRowCount() > 0)
					{
					    model.removeRow(0);
					}try {
					Object rowData[] = new Object[4];
					String sql1="select * from room rm where rm.room_type='single room' and rm.room_number not in (select distinct idroomno from "
							+ "booking_room bk where (bk.arrival_date between '"+ arrivaltext.getText()+"' and '"+
							departuretext.getText()+ "') or (bk.departure_date between '"+arrivaltext.getText()
							+"' and '"+departuretext.getText()+ "') or (bk.arrival_date<'"+arrivaltext.getText()  +"'"
									+ "and bk.departure_date>'"+departuretext.getText()+"')) " ;
					con=Hotel.getConnection(); 
					stmt=con.createStatement();
					resultset = stmt.executeQuery(sql1);
					while(resultset.next()){
						rowData[0] =resultset.getString("room_number");
						rowData[1] =resultset.getString("room_type");
						rowData[2]=resultset.getString("view");
						rowData[3]=resultset.getString("price");
						
						model.addRow(rowData);
						 
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					
				}else if(roomList.getSelectedIndex()==2 && viewList.getSelectedIndex()==0){
					while(model.getRowCount() > 0)
					{
					    model.removeRow(0);
					}
					
					try {
						Object rowData[] = new Object[4];
						
						String sql3="select * from room rm where rm.room_type='double room' and rm.room_number not in (select distinct idroomno from "
								+ "booking_room bk where (bk.arrival_date between '"+ arrivaltext.getText()+"' and '"+
								departuretext.getText()+ "') or (bk.departure_date between '"+arrivaltext.getText()
								+"' and '"+departuretext.getText()+ "') or (bk.arrival_date<'"+arrivaltext.getText()  +"'"
										+ "and bk.departure_date>'"+departuretext.getText()+"')) " ;

						con=Hotel.getConnection(); 
						stmt=con.createStatement();
						resultset = stmt.executeQuery(sql3);
						
						while(resultset.next()){
							rowData[0] =resultset.getString("room_number");
							rowData[1] =resultset.getString("room_type");
							rowData[2]=resultset.getString("view");
							rowData[3]=resultset.getString("price");
						
							
							model.addRow(rowData);
						}} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				}else if(roomList.getSelectedIndex()==1 && viewList.getSelectedIndex()==1){
					while(model.getRowCount() > 0)
					{
					    model.removeRow(0);
					}
					
					try {
						Object rowData[] = new Object[4];
						

String sql2="select * from room rm where rm.room_type='single room' and rm.view='cathedral' and rm.room_number not in (select distinct idroomno from "
							+ "booking_room bk where (bk.arrival_date between '"+ arrivaltext.getText()+"' and '"+
							departuretext.getText()+ "') or (bk.departure_date between '"+arrivaltext.getText()
							+"' and '"+departuretext.getText()+ "') or (bk.arrival_date<'"+arrivaltext.getText()  +"'"
									+ "and bk.departure_date>'"+departuretext.getText()+"')) " ;

						con=Hotel.getConnection(); 
						stmt=con.createStatement();
						resultset = stmt.executeQuery(sql2);
						
						while(resultset.next()){
							rowData[0] =resultset.getString("room_number");
							rowData[1] =resultset.getString("room_type");
							rowData[2]=resultset.getString("view");
							rowData[3]=resultset.getString("price");
						
							
							model.addRow(rowData);
						}} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}else if(roomList.getSelectedIndex()==2 && viewList.getSelectedIndex()==2){
					while(model.getRowCount() > 0)
					{
					    model.removeRow(0);
					}
					
					try {
						Object rowData[] = new Object[4];
						

						String sql7="select * from room rm where rm.room_type='double room' and rm.view='hotel park'and rm.room_number not in (select distinct idroomno from "
								+ "booking_room bk where (bk.arrival_date between '"+ arrivaltext.getText()+"' and '"+
								departuretext.getText()+ "') or (bk.departure_date between '"+arrivaltext.getText()
								+"' and '"+departuretext.getText()+ "') or (bk.arrival_date<'"+arrivaltext.getText()  +"'"
										+ "and bk.departure_date>'"+departuretext.getText()+"')) " ;

						con=Hotel.getConnection(); 
						stmt=con.createStatement();
						resultset = stmt.executeQuery(sql7);
						
						while(resultset.next()){
							rowData[0] =resultset.getString("room_number");
							rowData[1] =resultset.getString("room_type");
							rowData[2]=resultset.getString("view");
							rowData[3]=resultset.getString("price");
						
							
							model.addRow(rowData);
						}} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else if(roomList.getSelectedIndex()==2 && viewList.getSelectedIndex()==3){
					while(model.getRowCount() > 0)
					{
					    model.removeRow(0);
					}
					
					try {
						Object rowData[] = new Object[4];
						

						String sql6="select * from room rm where rm.room_type='double room' and rm.view='parking'and rm.room_number not in (select distinct idroomno from "
								+ "booking_room bk where (bk.arrival_date between '"+ arrivaltext.getText()+"' and '"+
								departuretext.getText()+ "') or (bk.departure_date between '"+arrivaltext.getText()
								+"' and '"+departuretext.getText()+ "') or (bk.arrival_date<'"+arrivaltext.getText()  +"'"
										+ "and bk.departure_date>'"+departuretext.getText()+"')) " ;

						con=Hotel.getConnection(); 
						stmt=con.createStatement();
						resultset = stmt.executeQuery(sql6);
						
						while(resultset.next()){
							rowData[0] =resultset.getString("room_number");
							rowData[1] =resultset.getString("room_type");
							rowData[2]=resultset.getString("view");
							rowData[3]=resultset.getString("price");
						
							
							model.addRow(rowData);
						}} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else if(roomList.getSelectedIndex()==1 && viewList.getSelectedIndex()==2){
					while(model.getRowCount() > 0)
					{
					    model.removeRow(0);
					}
					
					try {
						Object rowData[] = new Object[4];
						

						String sql4="select * from room rm where rm.room_type='single room' and rm.view='hotel park'and rm.room_number not in (select distinct idroomno from "
								+ "booking_room bk where (bk.arrival_date between '"+ arrivaltext.getText()+"' and '"+
								departuretext.getText()+ "') or (bk.departure_date between '"+arrivaltext.getText()
								+"' and '"+departuretext.getText()+ "') or (bk.arrival_date<'"+arrivaltext.getText()  +"'"
										+ "and bk.departure_date>'"+departuretext.getText()+"')) " ;

						con=Hotel.getConnection(); 
						stmt=con.createStatement();
						resultset = stmt.executeQuery(sql4);
						
						while(resultset.next()){
							rowData[0] =resultset.getString("room_number");
							rowData[1] =resultset.getString("room_type");
							rowData[2]=resultset.getString("view");
							rowData[3]=resultset.getString("price");
							
							model.addRow(rowData);
						}} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else if(roomList.getSelectedIndex()==1 && viewList.getSelectedIndex()==3){
					while(model.getRowCount() > 0)
					{
					    model.removeRow(0);
					}
					
					try {
						Object rowData[] = new Object[4];
						

						String sql5="select * from room rm where rm.room_type='single room' and rm.view='parking'and rm.room_number not in (select distinct idroomno from "
								+ "booking_room bk where (bk.arrival_date between '"+ arrivaltext.getText()+"' and '"+
								departuretext.getText()+ "') or (bk.departure_date between '"+arrivaltext.getText()
								+"' and '"+departuretext.getText()+ "') or (bk.arrival_date<'"+arrivaltext.getText()  +"'"
										+ "and bk.departure_date>'"+departuretext.getText()+"')) " ;

						con=Hotel.getConnection(); 
						stmt=con.createStatement();
						resultset = stmt.executeQuery(sql5);
						
						while(resultset.next()){
							rowData[0] =resultset.getString("room_number");
							rowData[1] =resultset.getString("room_type");
							rowData[2]=resultset.getString("view");
							rowData[3]=resultset.getString("price");
						
							
							model.addRow(rowData);
						}} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else if(roomList.getSelectedIndex()==0 && viewList.getSelectedIndex()==1){
					while(model.getRowCount() > 0)
					{
					    model.removeRow(0);
					}
					
					try {
						Object rowData[] = new Object[4];
						

						String sql5="select * from room rm where rm.view='cathedral'and rm.room_number not in (select distinct idroomno from "
								+ "booking_room bk where (bk.arrival_date between '"+ arrivaltext.getText()+"' and '"+
								departuretext.getText()+ "') or (bk.departure_date between '"+arrivaltext.getText()
								+"' and '"+departuretext.getText()+ "') or (bk.arrival_date<'"+arrivaltext.getText()  +"'"
										+ "and bk.departure_date>'"+departuretext.getText()+"')) " ;

						con=Hotel.getConnection(); 
						stmt=con.createStatement();
						resultset = stmt.executeQuery(sql5);
						
						while(resultset.next()){
							rowData[0] =resultset.getString("room_number");
							rowData[1] =resultset.getString("room_type");
							rowData[2]=resultset.getString("view");
							rowData[3]=resultset.getString("price");
						
							
							model.addRow(rowData);
						}} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else if(roomList.getSelectedIndex()==0 && viewList.getSelectedIndex()==2){
					while(model.getRowCount() > 0)
					{
					    model.removeRow(0);
					}
					
					try {
						Object rowData[] = new Object[4];
						

						String sql5="select * from room rm where rm.view='hotel park'and rm.room_number not in (select distinct idroomno from "
								+ "booking_room bk where (bk.arrival_date between '"+ arrivaltext.getText()+"' and '"+
								departuretext.getText()+ "') or (bk.departure_date between '"+arrivaltext.getText()
								+"' and '"+departuretext.getText()+ "') or (bk.arrival_date<'"+arrivaltext.getText()  +"'"
										+ "and bk.departure_date>'"+departuretext.getText()+"')) " ;

						con=Hotel.getConnection(); 
						stmt=con.createStatement();
						resultset = stmt.executeQuery(sql5);
						
						while(resultset.next()){
							rowData[0] =resultset.getString("room_number");
							rowData[1] =resultset.getString("room_type");
							rowData[2]=resultset.getString("view");
							rowData[3]=resultset.getString("price");
						
							
							model.addRow(rowData);
						}} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else if(roomList.getSelectedIndex()==0 && viewList.getSelectedIndex()==3){
					while(model.getRowCount() > 0)
					{
					    model.removeRow(0);
					}
					
					try {
						Object rowData[] = new Object[4];
						

						String sql5="select * from room rm where rm.view='parking'and rm.room_number not in (select distinct idroomno from "
								+ "booking_room bk where (bk.arrival_date between '"+ arrivaltext.getText()+"' and '"+
								departuretext.getText()+ "') or (bk.departure_date between '"+arrivaltext.getText()
								+"' and '"+departuretext.getText()+ "') or (bk.arrival_date<'"+arrivaltext.getText()  +"'"
										+ "and bk.departure_date>'"+departuretext.getText()+"')) " ;

						con=Hotel.getConnection(); 
						stmt=con.createStatement();
						resultset = stmt.executeQuery(sql5);
						
						while(resultset.next()){
							rowData[0] =resultset.getString("room_number");
							rowData[1] =resultset.getString("room_type");
							rowData[2]=resultset.getString("view");
							rowData[3]=resultset.getString("price");
							
						
							
							model.addRow(rowData);
						}} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
			}
			
				}});
		

		
		JLabel showLabel=new JLabel("Date for occupied rooms: ");
		showLabel.setFont(new Font("Serif", Font.BOLD,16));
		searchPanel.add(showLabel);
		JTextField showText=new JTextField();
		searchPanel.add(showText);
		
		occupiedbutton=new JButton("Show occupied rooms");
		occupiedbutton.setFont(new Font("Serif", Font.BOLD,16));
		searchPanel.add(occupiedbutton);
		occupiedbutton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(showText.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Please enter the date");
				}else{
					if (!showText.getText().matches("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))")) {		
						JOptionPane.showMessageDialog(null, "Wrong date format, please enter the following format: YYYY-MM-DD");}
					else{
				if(roomList.getSelectedIndex()==0 && viewList.getSelectedIndex()==0){
					while(model.getRowCount() > 0)
					{
					    model.removeRow(0);
					}
					
				try {
					Object rowData[] = new Object[4];
					String sql="select * from room rm,booking_room bk where rm.room_number=bk.idroomno and bk.arrival_date <= '"+ arrivaltext.getText()+"' and bk.departure_date >= '"+
							arrivaltext.getText()+"' and bk.departure_date!='"+showText.getText()+"';";


					con=Hotel.getConnection(); 
					stmt=con.createStatement();
					resultset = stmt.executeQuery(sql);
					while(resultset.next()){
						rowData[0] =resultset.getString("room_number");
						rowData[1] =resultset.getString("room_type");
						rowData[2]=resultset.getString("view");
						rowData[3]=resultset.getString("price");
						
						model.addRow(rowData);
						
						 
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}}
				else if (roomList.getSelectedIndex()==1 && viewList.getSelectedIndex()==0){
					while(model.getRowCount() > 0)
					{
					    model.removeRow(0);
					}try {
					Object rowData[] = new Object[4];
					String sql="select * from room rm,booking_room bk where rm.room_type='single room' and rm.room_number=bk.idroomno and bk.arrival_date <= '"+ arrivaltext.getText()+"' and bk.departure_date >= '"+
							arrivaltext.getText()+"' and bk.departure_date!='"+showText.getText()+"';";
					con=Hotel.getConnection(); 
					stmt=con.createStatement();
					resultset = stmt.executeQuery(sql);
					while(resultset.next()){
						rowData[0] =resultset.getString("room_number");
						rowData[1] =resultset.getString("room_type");
						rowData[2]=resultset.getString("view");
						rowData[3]=resultset.getString("price");
						
						model.addRow(rowData);
						 
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					
				}else if(roomList.getSelectedIndex()==2 && viewList.getSelectedIndex()==0){
					while(model.getRowCount() > 0)
					{
					    model.removeRow(0);
					}
					
					try {
						Object rowData[] = new Object[4];
						
						String sql="select * from room rm,booking_room bk where rm.room_type='double room' and rm.room_number=bk.idroomno and bk.arrival_date <= '"+ arrivaltext.getText()+"' and bk.departure_date >= '"+
								arrivaltext.getText()+"' and bk.departure_date!='"+showText.getText()+"';";

						con=Hotel.getConnection(); 
						stmt=con.createStatement();
						resultset = stmt.executeQuery(sql);
						
						while(resultset.next()){
							rowData[0] =resultset.getString("room_number");
							rowData[1] =resultset.getString("room_type");
							rowData[2]=resultset.getString("view");
							rowData[3]=resultset.getString("price");
						
							
							model.addRow(rowData);
						}} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				}else if(roomList.getSelectedIndex()==1 && viewList.getSelectedIndex()==1){
					while(model.getRowCount() > 0)
					{
					    model.removeRow(0);
					}
					
					try {
						Object rowData[] = new Object[4];


String sql="select * from room rm,booking_room bk where rm.room_type='single room' and rm.view='cathedral' and rm.room_number=bk.idroomno and bk.arrival_date <= '"+ arrivaltext.getText()+"' and bk.departure_date >= '"+
		arrivaltext.getText()+"' and bk.departure_date!='"+showText.getText()+"';";



						con=Hotel.getConnection(); 
						stmt=con.createStatement();
						resultset = stmt.executeQuery(sql);
						
						while(resultset.next()){
							rowData[0] =resultset.getString("room_number");
							rowData[1] =resultset.getString("room_type");
							rowData[2]=resultset.getString("view");
							rowData[3]=resultset.getString("price");
						
							
							model.addRow(rowData);
						}} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}else if(roomList.getSelectedIndex()==2 && viewList.getSelectedIndex()==2){
					while(model.getRowCount() > 0)
					{
					    model.removeRow(0);
					}
					
					try {
						Object rowData[] = new Object[4];
						

						String sql="select * from room rm,booking_room bk whererm.room_type='double room' and rm.view='hotel park' and rm.room_number=bk.idroomno and bk.arrival_date <= '"+ arrivaltext.getText()+"' and bk.departure_date >= '"+
								arrivaltext.getText()+"' and bk.departure_date!='"+showText.getText()+"';";


						con=Hotel.getConnection(); 
						stmt=con.createStatement();
						resultset = stmt.executeQuery(sql);
						
						while(resultset.next()){
							rowData[0] =resultset.getString("room_number");
							rowData[1] =resultset.getString("room_type");
							rowData[2]=resultset.getString("view");
							rowData[3]=resultset.getString("price");
						
							
							model.addRow(rowData);
						}} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else if(roomList.getSelectedIndex()==2 && viewList.getSelectedIndex()==3){
					while(model.getRowCount() > 0)
					{
					    model.removeRow(0);
					}
					
					try {
						Object rowData[] = new Object[4];
						

						

						String sql="select * from room rm,booking_room bk whererm.room_type='double room' and rm.view='parking' and rm.room_number=bk.idroomno and bk.arrival_date <= '"+ arrivaltext.getText()+"' and bk.departure_date >= '"+
								arrivaltext.getText()+"' and bk.departure_date!='"+showText.getText()+"';";


						con=Hotel.getConnection(); 
						stmt=con.createStatement();
						resultset = stmt.executeQuery(sql);
						
						while(resultset.next()){
							rowData[0] =resultset.getString("room_number");
							rowData[1] =resultset.getString("room_type");
							rowData[2]=resultset.getString("view");
							rowData[3]=resultset.getString("price");
						
							
							model.addRow(rowData);
						}} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else if(roomList.getSelectedIndex()==1 && viewList.getSelectedIndex()==2){
					while(model.getRowCount() > 0)
					{
					    model.removeRow(0);
					}
					
					try {
						Object rowData[] = new Object[4];
						

			
						

						String sql="select * from room rm,booking_room bk where  rm.room_type='single room' and rm.view='hotel park' and rm.room_number=bk.idroomno and bk.arrival_date <= '"+ arrivaltext.getText()+"' and bk.departure_date >= '"+
								arrivaltext.getText()+"' and bk.departure_date!='"+showText.getText()+"';";


						con=Hotel.getConnection(); 
						stmt=con.createStatement();
						resultset = stmt.executeQuery(sql);
						
						while(resultset.next()){
							rowData[0] =resultset.getString("room_number");
							rowData[1] =resultset.getString("room_type");
							rowData[2]=resultset.getString("view");
							rowData[3]=resultset.getString("price");
							
							model.addRow(rowData);
						}} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else if(roomList.getSelectedIndex()==1 && viewList.getSelectedIndex()==3){
					while(model.getRowCount() > 0)
					{
					    model.removeRow(0);
					}
					
					try {
						Object rowData[] = new Object[4];
						



						String sql="select * from room rm,booking_room bk where rm.room_type='single room' and rm.view='parking' and rm.room_number=bk.idroomno and bk.arrival_date <= '"+ arrivaltext.getText()+"' and bk.departure_date >= '"+
								arrivaltext.getText()+"' and bk.departure_date!='"+showText.getText()+"';";

						
						con=Hotel.getConnection(); 
						stmt=con.createStatement();
						resultset = stmt.executeQuery(sql);
						
						while(resultset.next()){
							rowData[0] =resultset.getString("room_number");
							rowData[1] =resultset.getString("room_type");
							rowData[2]=resultset.getString("view");
							rowData[3]=resultset.getString("price");
						
							
							model.addRow(rowData);
						}} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else if(roomList.getSelectedIndex()==0 && viewList.getSelectedIndex()==1){
					while(model.getRowCount() > 0)
					{
					    model.removeRow(0);
					}
					
					try {
						Object rowData[] = new Object[4];
						


						String sql="select * from room rm,booking_room bk where rm.view='cathedral' and rm.room_number=bk.idroomno and bk.arrival_date <= '"+ arrivaltext.getText()+"' and bk.departure_date >= '"+
								arrivaltext.getText()+"' and bk.departure_date!='"+showText.getText()+"';";


						con=Hotel.getConnection(); 
						stmt=con.createStatement();
						resultset = stmt.executeQuery(sql);
						
						while(resultset.next()){
							rowData[0] =resultset.getString("room_number");
							rowData[1] =resultset.getString("room_type");
							rowData[2]=resultset.getString("view");
							rowData[3]=resultset.getString("price");
						
							
							model.addRow(rowData);
						}} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else if(roomList.getSelectedIndex()==0 && viewList.getSelectedIndex()==2){
					while(model.getRowCount() > 0)
					{
					    model.removeRow(0);
					}
					
					try {
						Object rowData[] = new Object[4];
						

						String sql="select * from room rm,booking_room bk where rm.view='hotel park' and rm.room_number=bk.idroomno and bk.arrival_date <= '"+ arrivaltext.getText()+"' and bk.departure_date >= '"+
								arrivaltext.getText()+"' and bk.departure_date!='"+showText.getText()+"';";


						con=Hotel.getConnection(); 
						stmt=con.createStatement();
						resultset = stmt.executeQuery(sql);
						
						while(resultset.next()){
							rowData[0] =resultset.getString("room_number");
							rowData[1] =resultset.getString("room_type");
							rowData[2]=resultset.getString("view");
							rowData[3]=resultset.getString("price");
						
							
							model.addRow(rowData);
						}} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else if(roomList.getSelectedIndex()==0 && viewList.getSelectedIndex()==3){
					while(model.getRowCount() > 0)
					{
					    model.removeRow(0);
					}
					
					try {
						Object rowData[] = new Object[4];
						
						String sql="select * from room rm,booking_room bk where rm.view='parking' and rm.room_number=bk.idroomno and bk.arrival_date <= '"+ arrivaltext.getText()+"' and bk.departure_date >= '"+
								arrivaltext.getText()+"' and bk.departure_date!='"+showText.getText()+"';";


						con=Hotel.getConnection(); 
						stmt=con.createStatement();
						resultset = stmt.executeQuery(sql);
						
						while(resultset.next()){
							rowData[0] =resultset.getString("room_number");
							rowData[1] =resultset.getString("room_type");
							rowData[2]=resultset.getString("view");
							rowData[3]=resultset.getString("price");
							
						
							
							model.addRow(rowData);
						}} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
			}
			
				
			}
			
		});
		
		allbutton=new JButton("Show all rooms");
		allbutton.setFont(new Font("Serif", Font.BOLD,16));
		searchPanel.add(allbutton);
		allbutton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				while(model.getRowCount() > 0)
				{
				    model.removeRow(0);
				}
				Object rowData[] = new Object[4];
				String sql="select * from room";
				con=Hotel.getConnection(); 
				try {
					stmt=con.createStatement();
					resultset = stmt.executeQuery(sql);
					while(resultset.next()){
						rowData[0] =resultset.getString("room_number");
						rowData[1] =resultset.getString("room_type");
						rowData[2] =resultset.getString("view");
						rowData[3] =resultset.getString("price");
						

						
						model.addRow(rowData);
					}} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
					 
				
			}}
			
		);
		

		
		
		
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