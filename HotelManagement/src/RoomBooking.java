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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class RoomBooking extends JFrame {
	private JLabel labelimage,mealtype, name, phone, email, street, zip, city, country, roomno, roomtype, arrival, departure,
			numberofguests, bookingdate, view, search, choose;
	private JPanel datePanel;
	private JComboBox mealList;
	private JRadioButton existing, newuser;
	private JTextField nameText, phoneText, emailText, streetText, zipText, cityText, countryText;
	public JTextField roomnoText, roomtypeText, cleaningstatusText, arrivalText, departureText, numberofguestsText,
			bookingdateText, viewText;
	private JTextField searchText;
	private JButton bookButton, back;
	private Hotel hotel;
	private JTable table;
	private String[] columnNames;
	private DefaultTableModel model;
	private Connection con;
	private Statement stmt;
	private ResultSet resultset;
	private TableRowSorter sorter;
	

	public RoomBooking() {

		JPanel panel = new JPanel();

		labelimage=new JLabel();
		labelimage.setIcon(new ImageIcon("bookingroom.jpg"));
		labelimage.setVisible(true);
		panel.add(labelimage);
	

		JPanel tablePanel = new JPanel(new BorderLayout());
		

		
		tablePanel.setPreferredSize(new Dimension(800, 300));
		columnNames = new String[] { "Name", "Phone", "Email", "Street", "Zip Code", "City", "Country" };

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
					
					if (newuser.isSelected()) {
						JOptionPane.showMessageDialog(null, "Please note that you are not select someone in the table. If you want to, choose newuser");
						nameText.setText("");
						phoneText.setText("");
						emailText.setText("");
						streetText.setText("");
						zipText.setText("");
						cityText.setText("");
						zipText.setText("");
						countryText.setText("");
						table.getSelectionModel().clearSelection();
					}else {
						
					int modelIndex = table.convertRowIndexToModel(i);

					String name = model.getValueAt(modelIndex, 0).toString();
					String phone = model.getValueAt(modelIndex, 1).toString();
					String email = model.getValueAt(modelIndex, 2).toString();
					String street = model.getValueAt(modelIndex, 3).toString();
					String zipcode = model.getValueAt(modelIndex, 4).toString();
					String city = model.getValueAt(modelIndex, 5).toString();
					String country = model.getValueAt(modelIndex, 6).toString();

					nameText.setText(name);
					phoneText.setText(phone);
					emailText.setText(email);
					streetText.setText(street);
					zipText.setText(zipcode);
					cityText.setText(city);
					zipText.setText(city);
					countryText.setText(country);
					nameText.setEditable(false);
					phoneText.setEditable(false);
					emailText.setEditable(false);
					streetText.setEditable(false);
					zipText.setEditable(false);
					cityText.setEditable(false);
					zipText.setEditable(false);
					countryText.setEditable(false);
					
				}
			} }

		});
		table.setBackground(Color.darkGray);
		table.setForeground(Color.white);

		model = (DefaultTableModel) table.getModel();
		try {
			Object rowData[] = new Object[7];
			String sql = "select c.name, c.phone, c.email,a.street_streetno,a.zip_code,a.city,a.country from client c,address a where c.id=a.idclient";
			con = Hotel.getConnection();
			stmt = con.createStatement();
			resultset = stmt.executeQuery(sql);
			while (resultset.next()) {
				rowData[0] = resultset.getString("name");
				rowData[1] = resultset.getString("phone");
				rowData[2] = resultset.getString("email");
				rowData[3] = resultset.getString("street_streetno");
				rowData[4] = resultset.getString("zip_code");
				rowData[5] = resultset.getString("city");
				rowData[6] = resultset.getString("country");

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
		tablePanel.add(scrollPane, BorderLayout.CENTER);
		panel.add(tablePanel);

		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));

		search = new JLabel("Please enter the name for searching: ");
		search.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		searchText = new JTextField();
		searchText.setPreferredSize(new Dimension(100, 30));
		searchText.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				String text = searchText.getText();

				if (text.trim().length() == 0) {
					sorter.setRowFilter(null);
				} else {
					sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0));
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				String text = nameText.getText();

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
		searchPanel.add(search);
		searchPanel.add(searchText);

		choose = new JLabel("Please choose if the user already exists");
		choose.setFont(new Font("Arial", Font.BOLD, 15));
		searchPanel.add(choose);

		existing = new JRadioButton("Existing User");
		existing.setFont(new Font("Arial", Font.ITALIC, 15));
		newuser = new JRadioButton("New User");
		newuser.setFont(new Font("Arial", Font.ITALIC, 15));
		ButtonGroup group = new ButtonGroup();
		group.add(existing);
		group.add(newuser);

		existing.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Please choose a client in the table");
				nameText.setEditable(false);
				phoneText.setEditable(false);
				emailText.setEditable(false);
				streetText.setEditable(false);
				zipText.setEditable(false);
				cityText.setEditable(false);
				countryText.setEditable(false);

			}
		});
		newuser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "You are able to enter now in the information in the textfields");
				nameText.setEditable(true);
				phoneText.setEditable(true);
				emailText.setEditable(true);
				streetText.setEditable(true);
				zipText.setEditable(true);
				cityText.setEditable(true);
				countryText.setEditable(true);

			}
		});

		searchPanel.add(existing);
		searchPanel.add(newuser);

		panel.add(searchPanel);
		add(panel);

		JPanel roomPanel = new JPanel();
		roomPanel.setLayout(new BoxLayout(roomPanel, BoxLayout.PAGE_AXIS));

		bookingdate = new JLabel("Booking date: ");
		bookingdateText = new JTextField();
		roomPanel.add(bookingdate);
		roomPanel.add(bookingdateText);

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		bookingdateText.setText(dateFormat.format(date));
		bookingdateText.setEditable(false);

		arrival = new JLabel("Arrival date: ");
		arrivalText = new JTextField();
		arrivalText.setEditable(false);
		roomPanel.add(arrival);
		roomPanel.add(arrivalText);

		departure = new JLabel("Departure date: ");
		departureText = new JTextField();
		departureText.setEditable(false);
		roomPanel.add(departure);
		roomPanel.add(departureText);

		roomno = new JLabel("Room number: ");
		roomnoText = new JTextField();
		roomnoText.setEditable(false);
		roomPanel.add(roomno);
		roomPanel.add(roomnoText);

		roomtype = new JLabel("Room type: ");
		roomtypeText = new JTextField();
		roomtypeText.setEditable(false);
		roomPanel.add(roomtype);
		roomPanel.add(roomtypeText);

		view = new JLabel("View: ");
		viewText = new JTextField();
		viewText.setEditable(false);
		roomPanel.add(view);
		roomPanel.add(viewText);

		numberofguests = new JLabel("Number of guests: ");
		numberofguestsText = new JTextField();
		roomPanel.add(numberofguests);
		roomPanel.add(numberofguestsText);

		panel.add(roomPanel);

		JPanel clientPanel = new JPanel();
		clientPanel.setLayout(new BoxLayout(clientPanel, BoxLayout.PAGE_AXIS));
		name = new JLabel("Name:");
		nameText = new JTextField();
		clientPanel.add(name);
		clientPanel.add(nameText);

		phone = new JLabel("Phone number: ");
		phoneText = new JTextField();
		clientPanel.add(phone);
		clientPanel.add(phoneText);

		email = new JLabel("E-mail:");
		emailText = new JTextField();
		clientPanel.add(email);
		clientPanel.add(emailText);

		street = new JLabel("Street and streetno: ");
		streetText = new JTextField();
		clientPanel.add(street);
		clientPanel.add(streetText);

		zip = new JLabel("ZIP code: ");
		zipText = new JTextField();
		clientPanel.add(zip);
		clientPanel.add(zipText);

		city = new JLabel("City: ");
		cityText = new JTextField();
		clientPanel.add(city);
		clientPanel.add(cityText);

		country = new JLabel("Country: ");
		countryText = new JTextField();
		clientPanel.add(country);
		clientPanel.add(countryText);


		panel.add(clientPanel);

		

		bookButton = new JButton("Book the room");
		panel.add(bookButton);
		bookButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (!numberofguestsText.getText().matches("[0-9]+") || numberofguestsText.getText().isEmpty()
						|| nameText.getText().isEmpty() || zipText.getText().isEmpty() || phoneText.getText().isEmpty()
						|| emailText.getText().isEmpty() || cityText.getText().isEmpty()
						|| countryText.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Make sure that each field is filled and check the format: date: YYYY-MM-DD");}
				else if (!existing.isSelected()&&!newuser.isSelected()) {
						JOptionPane.showMessageDialog(null, "Please choose if the user exists or not");
					}
				 else {
					if (newuser.isSelected()) {
					
						String sql ="INSERT INTO CLIENT " + "(name,phone,email)" + "VALUES('"+ nameText.getText()
						+ "','" + phoneText.getText() + "','" + emailText.getText() + "');"
						
						+ "INSERT INTO ADDRESS(IDclient,street_streetno,zip_code,city,country)"
						+ "SELECT c.id,'" + streetText.getText() + "','" + zipText.getText() + "','"
						+ cityText.getText() + "','" + countryText.getText() + "' from CLIENT c where c.name='"
						+ nameText.getText() + "' and c.phone='" + phoneText.getText() + "' and c.email='"
						+ emailText.getText() + "';";

				
					
						
						
						try {
							
							con = Hotel.getConnection();
							stmt = con.createStatement();
							stmt.executeUpdate(sql);
						} catch (SQLException e1) {
							System.out.println(e1);
						}
						dispose();

						hotel = new Hotel();

					
						
					} else if (existing.isSelected() && table.getSelectedRow() == -1) {
						JOptionPane.showMessageDialog(null, "Please choose the existing user in the table");

					} else if (existing.isSelected() && table.getSelectedRow()!=-1) {

						
						String sql ="INSERT INTO BOOKING(IDclient,number_of_guests,booking_date)"
								+ "VALUES ((SELECT c.id from client c where c.name='"+nameText.getText()+"' and c.phone='"+phoneText.getText()
								+"' and c.email='"+emailText.getText()+"'),"
								 + numberofguestsText.getText() + ",'"
								+ bookingdateText.getText() + "');"

								+ "INSERT INTO BOOKING_ROOM(IDbooking, IDroomno, arrival_date, departure_date)"
								+ "(SELECT b.id from booking b INNER JOIN client c on b.idclient=c.id and c.name='"+nameText.getText()+"')," + roomnoText.getText() + ",'" + arrivalText.getText() + "','"
								+ departureText.getText() + "' from CLIENT c, booking b " + "where b.IDclient=c.id and c.name='"
										+ nameText.getText() + "' and c.phone='" + phoneText.getText() + "'and c.email='"
										+ emailText.getText() + "';";
								
								try {
							
									con = Hotel.getConnection();
									stmt = con.createStatement();
									stmt.executeUpdate(sql);
								} catch (SQLException e1) {
									System.out.println(e1);
								}

								JOptionPane.showMessageDialog(null, "The booking was successfully");
								dispose();

								hotel = new Hotel();
								
					} 
					dispose();
					hotel = new Hotel();

				}

			}
		}

		);

		back = new JButton("<<");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				dispose();

				hotel = new Hotel();

			}

		});
		panel.add(back);

		add(panel);

	}

}