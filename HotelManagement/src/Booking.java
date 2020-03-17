import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.Icon;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


public class Booking extends JFrame{
	private Hotel hotel;
	private JLabel arrival, departure, roomtype,mealtype,hotelimage;
	private JTable table;
	private JButton bookButton, searchbutton, back;
	private JPanel datePanel, tablePanel,selectPanel;
	private JComboBox roomList;
	public JTextField arrivalText, departureText ;
	private DefaultTableModel model;
	private String columnNames[];
	private TableRowSorter<TableModel> sorter;
	RoomBooking roombooking;
	Connection con=null;
	Statement stmt=null;
	ResultSet resultset=null;
	
	
	public Booking(){
		
		JPanel panel= new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		JPanel picturepanel= new JPanel();
		
		
	
		hotelimage=new JLabel("");
		hotelimage.setIcon(new ImageIcon("hotel booking.jpg"));
		picturepanel.add(hotelimage);
		
		datePanel=new JPanel();
		
		arrival=new JLabel("Arrival date");
		datePanel.add(arrival);
		
		arrivalText=new JTextField();
		arrivalText.setPreferredSize( new Dimension(70, 24 ) );
		datePanel.add(arrivalText);
		
		departure=new JLabel ("Departure date");
		datePanel.add(departure);
		departureText=new JTextField();
		departureText.setPreferredSize( new Dimension(70, 24 ) );
		datePanel.add(departureText);

		selectPanel=new JPanel();
		roomtype=new JLabel ("Roomtype");
		datePanel.add(roomtype);
		
		String [] room={"All rooms","Single Room","Double Room"};
		roomList=new JComboBox(room);
		roomList.setSelectedIndex(0);
		datePanel.add(roomList);
		
		
		searchbutton=new JButton("Search available rooms");
	datePanel.add(searchbutton);
	searchbutton.addActionListener(new ActionListener(){

		public void actionPerformed(ActionEvent e) {
			
			
			
			if(arrivalText.getText().isEmpty() || departureText.getText().isEmpty()){
				JOptionPane.showMessageDialog(null, "Please enter the arrival and departure fields");
			}else{
				if (!arrivalText.getText().matches("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))") && !departureText.getText().matches("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))")
						) {		
					JOptionPane.showMessageDialog(null, "Wrong date format, please enter the following format: YYYY-MM-DD");}
				else{
					
					if (roomList.getSelectedIndex()==0) {
						while(model.getRowCount() > 0)
						{
						    model.removeRow(0);
						}
						
					try {
						Object rowData[] = new Object[3];
						String sql="select rm.room_number, rm.room_type, rm.view from room rm where rm.room_number not in "
								+ "(select distinct idroomno from "
								+ "booking_room bk where (bk.arrival_date between '"+ arrivalText.getText()+"' and '"+
								departureText.getText()+ "') or (bk.departure_date between '"+arrivalText.getText()
								+"' and '"+departureText.getText()+ "') or (bk.arrival_date<'"+arrivalText.getText()  +"'"
										+ "and bk.departure_date>'"+departureText.getText()+"')) " ;
						con=Hotel.getConnection(); 
						stmt=con.createStatement();
						
						resultset = stmt.executeQuery(sql);
						while(resultset.next()){
							rowData[0] =resultset.getString("room_number");
							rowData[1] =resultset.getString("room_type");
							rowData[2]=resultset.getString("View");
							
						
							
							model.addRow(rowData);
							
							 
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	
					}
					else if(roomList.getSelectedIndex()==1){
				while(model.getRowCount() > 0)
				{
				    model.removeRow(0);
				}
				
			try {
				Object rowData[] = new Object[3];
				String sql="select rm.room_number, rm.room_type, rm.view from room rm where rm.room_type='single room' and rm.room_number not in "
						+ "(select distinct idroomno from "
						+ "booking_room bk where (bk.arrival_date between '"+ arrivalText.getText()+"' and '"+
						departureText.getText()+ "') or (bk.departure_date between '"+arrivalText.getText()
						+"' and '"+departureText.getText()+ "') or (bk.arrival_date<'"+arrivalText.getText()  +"'"
								+ "and bk.departure_date>'"+departureText.getText()+"')) " ;
				con=Hotel.getConnection(); 
				stmt=con.createStatement();
				
				resultset = stmt.executeQuery(sql);
				while(resultset.next()){
					rowData[0] =resultset.getString("room_number");
					rowData[1] =resultset.getString("room_type");
					rowData[2]=resultset.getString("View");
					
				
					
					model.addRow(rowData);
					
					 
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}}
			else{
				while(model.getRowCount() > 0)
				{
				    model.removeRow(0);
				}try {
				Object rowData[] = new Object[3];
				String sql="select rm.room_number, rm.room_type, rm.view from room rm where rm.room_type='double room' and rm.room_number not in (select distinct idroomno from "
						+ "booking_room bk where (bk.arrival_date between '"+ arrivalText.getText()+"' and '"+
						departureText.getText()+ "') or (bk.departure_date between '"+arrivalText.getText()
						+"' and '"+departureText.getText()+ "') or (bk.arrival_date<'"+arrivalText.getText()  +"'"
								+ "and bk.departure_date>'"+departureText.getText()+"')) " ;
				con=Hotel.getConnection(); 
				stmt=con.createStatement();
				resultset = stmt.executeQuery(sql);
				while(resultset.next()){
					rowData[0] =resultset.getString("room_number");
					rowData[1] =resultset.getString("room_type");
					rowData[2]=resultset.getString("View");
					
				
					
					model.addRow(rowData);
					 
				}	
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
			}
			
		}
		}
		
	}});
	
		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new BoxLayout(tablePanel,BoxLayout.X_AXIS));
		tablePanel.setPreferredSize(new Dimension(800, 300));
		columnNames = new String[] {"Room number", "Room type", "View","Images"};
		
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
				
				String roomnumber=model.getValueAt(modelIndex, 0).toString();
				String roomtype=model.getValueAt(modelIndex, 1).toString();
				String view=model.getValueAt(modelIndex, 2).toString();
				
				
				
				String arrivaldate=arrivalText.getText();
				String departuredate=departureText.getText();
				
				dispose();
				roombooking=new RoomBooking();
				roombooking.setSize(1300,750);
				roombooking.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				roombooking.setVisible(true);
				
				
				roombooking.roomnoText.setText(roomnumber);
				roombooking.roomtypeText.setText(roomtype);
				roombooking.arrivalText.setText(arrivaldate);
				roombooking.departureText.setText(departuredate);
				roombooking.viewText.setText(view);
			

			}
			}
		});
		table.setBackground(Color.darkGray);
		table.setForeground(Color.white);
		
		model = (DefaultTableModel) table.getModel();
		
		table.setRowHeight(90);
		sorter = new TableRowSorter<TableModel>(model);
		table.setRowSorter(sorter);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(500, 300));
		tablePanel.add(scrollPane);
		
	

		
		back=new JButton("<<");
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
			
				dispose();
				
				hotel=new Hotel();
				
			}
			
		});
		datePanel.add(back);
		
		panel.add(picturepanel);
		panel.add(tablePanel);
		panel.add(datePanel);
		add (panel);
	

		
	}
}