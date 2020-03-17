import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Employee extends JFrame {
	private DefaultTableModel model;
	private String EmployeeList[];
	private TableRowSorter<TableModel> sorter;
	private JTable table;
	private JButton showButton, back, deletebutton, updatebutton, addbutton, resetButton, addnewemployeebutton, salarybutton;
	private JComboBox staffList, contractList;
	private Hotel hotel;
	private AddSalary addsalary;
	private AddNewEmployee addemployeeclass;
	private JTextField searchtext, ssntext, nametext, professiontext, salarytext;
	private JLabel ssn, name, profession, salary;
	Connection con = null;
	ResultSet resultset = null;

	Statement stmt = null;

	public Employee() {

		JPanel panel = new JPanel();
		
		JPanel picturePanel=new JPanel();
		
		JLabel employeephoto=new JLabel("");
		employeephoto.setIcon(new ImageIcon("hotelstaff.jpg"));
		employeephoto.setSize(300,44);
		employeephoto.setVisible(true);
		picturePanel.add(employeephoto);
		
		JLabel employee=new JLabel("Our Team");
		employee.setFont(new Font("Serif", Font.BOLD,30));
		picturePanel.add(employee);
		
		panel.add(picturePanel);

		
		JPanel employeePanel = new JPanel();

		EmployeeList = new String[] { "SSN", "Name", "Profession"};
		table = new JTable(new DefaultTableModel(EmployeeList, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				ssntext.setEditable(false);
				int i = table.getSelectedRow();
				if (i < 0) {
					JOptionPane.showMessageDialog(null,
							"Please note that you have to select a row with content inside.");
				} else {

					int modelIndex = table.convertRowIndexToModel(i);

					String ssn = model.getValueAt(modelIndex, 0).toString();
					String name = model.getValueAt(modelIndex, 1).toString();
					String profession = model.getValueAt(modelIndex, 2).toString();
					
					

					ssntext.setText(ssn);
					nametext.setText(name);
					professiontext.setText(profession);

					
				}
			}
		});
		table.setBackground(Color.lightGray);
		table.setForeground(Color.black);

		model = (DefaultTableModel) table.getModel();
		try {
			Object rowData[] = new Object[5];
			String sql = "select ssn, name, profession from employee";
			con = Hotel.getConnection();
			stmt = con.createStatement();
			resultset = stmt.executeQuery(sql);
			while (resultset.next()) {
				rowData[0] = resultset.getString("ssn");
				rowData[1] = resultset.getString("name");
				rowData[2] = resultset.getString("profession");
				model.addRow(rowData);

			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		sorter = new TableRowSorter<TableModel>(model);
		table.setRowSorter(sorter);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(1000, 300));
		employeePanel.add(scrollPane);
		panel.add(employeePanel);

		JPanel symbolPanel = new JPanel();
		symbolPanel.setLayout(new BoxLayout(symbolPanel, BoxLayout.PAGE_AXIS));
		

		searchtext = new JTextField("Name, SSN");
		searchtext.setForeground(Color.GRAY);

		searchtext.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {

				searchtext.setText("");
				repaint();
				revalidate();
				searchtext.setForeground(Color.black);

			}

		});
		symbolPanel.add(searchtext);
		
		showButton = new JButton("Search");
		

		

		showButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(staffList.getSelectedIndex() == 0 && contractList.getSelectedIndex() == 0){
					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}
					try {
						Object rowData[] = new Object[4];
						String sql = "select e.ssn, e.name, e.profession, s.cost_unit_number from employee e inner join  salary s on s.idssn=e.ssn";

						con = Hotel.getConnection();
						stmt = con.createStatement();
						resultset = stmt.executeQuery(sql);
						while (resultset.next()) {
							rowData[0] = resultset.getString("ssn");
							rowData[1] = resultset.getString("name");
							rowData[2] = resultset.getString("profession");
							rowData[3] = resultset.getString("cost_unit_number");

							model.addRow(rowData);

						}}catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				}
				if(staffList.getSelectedIndex() == 0 && contractList.getSelectedIndex() == 0 && 
						!(searchtext.getText().contains("Name, SSN") || searchtext.getText().isEmpty())){
					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}
					try {
						Object rowData[] = new Object[4];
						String sql = "select e.ssn, e.name, e.profession, s.cost_unit_number from employee e inner join  salary s on s.idssn=e.ssn and (upper(e.name)  LIKE '%"+
						searchtext.getText()+"%'"+"or lower (e.name) LIKE '%"+ searchtext.getText()+"%')";

						con = Hotel.getConnection();
						stmt = con.createStatement();
						resultset = stmt.executeQuery(sql);
						while (resultset.next()) {
							rowData[0] = resultset.getString("ssn");
							rowData[1] = resultset.getString("name");
							rowData[2] = resultset.getString("profession");
							rowData[3] = resultset.getString("cost_unit_number");

							model.addRow(rowData);

						}}catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				}

				else if (staffList.getSelectedIndex() == 1 && contractList.getSelectedIndex() == 0
						&& !(searchtext.getText().contains("Name, SSN") || searchtext.getText().isEmpty())) {

					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}

					try {
						Object rowData[] = new Object[4];
						String sql = "select hk.ssn, s.cost_unit_number, e.name, e.profession from housekeepingstaff hk inner join employee e on hk.ssn=e.ssn inner join salary s on hk.ssn=s.idssn and (upper(e.name)  LIKE '%"+
						searchtext.getText()+"%'"+"or lower (e.name) LIKE '%"+ searchtext.getText()+"%')";

						con = Hotel.getConnection();
						stmt = con.createStatement();
						resultset = stmt.executeQuery(sql);
						while (resultset.next()) {
							rowData[0] = resultset.getString("ssn");
							rowData[1] = resultset.getString("name");
							rowData[2] = resultset.getString("profession");
							rowData[3] = resultset.getString("cost_unit_number");

							model.addRow(rowData);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (staffList.getSelectedIndex() == 1 && contractList.getSelectedIndex() == 0) {

					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}

					try {
						Object rowData[] = new Object[4];
						String sql = "select hk.ssn, s.cost_unit_number, e.name, e.profession from housekeepingstaff hk inner join employee e on hk.ssn=e.ssn inner join salary s on hk.ssn=s.idssn";

						con = Hotel.getConnection();
						stmt = con.createStatement();
						resultset = stmt.executeQuery(sql);
						while (resultset.next()) {
							rowData[0] = resultset.getString("ssn");
							rowData[1] = resultset.getString("name");
							rowData[2] = resultset.getString("profession");
							rowData[3] = resultset.getString("cost_unit_number");

							model.addRow(rowData);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (staffList.getSelectedIndex() == 2 && contractList.getSelectedIndex() == 0
						&& !(searchtext.getText().contains("Name, SSN") || searchtext.getText().isEmpty())) {

					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}

					try {
						Object rowData[] = new Object[4];
						String sql = "select gs.ssn, s.cost_unit_number, e.name, e.profession from gastronomystaff gs inner join employee e on gs.ssn=e.ssn inner join salary s on gs.ssn=s.idssn and (upper(e.name)  LIKE '%"+
						searchtext.getText()+"%'"+"or lower (e.name) LIKE '%"+ searchtext.getText()+"%')";

						con = Hotel.getConnection();
						stmt = con.createStatement();
						resultset = stmt.executeQuery(sql);
						while (resultset.next()) {
							rowData[0] = resultset.getString("ssn");
							rowData[1] = resultset.getString("name");
							rowData[2] = resultset.getString("profession");
							rowData[3] = resultset.getString("cost_unit_number");

							model.addRow(rowData);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (staffList.getSelectedIndex() == 2 && contractList.getSelectedIndex() == 0) {

					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}

					try {
						Object rowData[] = new Object[4];
						String sql = "select gs.ssn, s.cost_unit_number, e.name, e.profession from gastronomystaff gs inner join employee e on gs.ssn=e.ssn inner join salary s on gs.ssn=s.idssn";

						con = Hotel.getConnection();
						stmt = con.createStatement();
						resultset = stmt.executeQuery(sql);
						while (resultset.next()) {
							rowData[0] = resultset.getString("ssn");
							rowData[1] = resultset.getString("name");
							rowData[2] = resultset.getString("profession");
							rowData[3] = resultset.getString("cost_unit_number");

							model.addRow(rowData);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				else if (staffList.getSelectedIndex() == 3 && contractList.getSelectedIndex() == 0
						&& !(searchtext.getText().contains("Name, SSN") || searchtext.getText().isEmpty())) {

					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}

					try {
						Object rowData[] = new Object[4];
						String sql = "select rs.ssn, s.cost_unit_number, e.name, e.profession from receptionstaff rs inner join employee e on rs.ssn=e.ssn inner join salary s on rs.ssn=s.idssn and (upper(e.name)  LIKE '%"+
						searchtext.getText()+"%'"+"or lower (e.name) LIKE '%"+ searchtext.getText()+"%')";

						con = Hotel.getConnection();
						stmt = con.createStatement();
						resultset = stmt.executeQuery(sql);
						while (resultset.next()) {
							rowData[0] = resultset.getString("ssn");
							rowData[1] = resultset.getString("name");
							rowData[2] = resultset.getString("profession");
							rowData[3] = resultset.getString("cost_unit_number");

							model.addRow(rowData);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (staffList.getSelectedIndex() == 3 && contractList.getSelectedIndex() == 0) {

					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}

					try {
						Object rowData[] = new Object[4];
						String sql = "select rs.ssn, s.cost_unit_number, e.name, e.profession from receptionstaff rs inner join employee e on rs.ssn=e.ssn inner join salary s on rs.ssn=s.idssn";

						con = Hotel.getConnection();
						stmt = con.createStatement();
						resultset = stmt.executeQuery(sql);
						while (resultset.next()) {
							rowData[0] = resultset.getString("ssn");
							rowData[1] = resultset.getString("name");
							rowData[2] = resultset.getString("profession");
							rowData[3] = resultset.getString("cost_unit_number");

							model.addRow(rowData);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				else if (staffList.getSelectedIndex() == 1 && contractList.getSelectedIndex() == 1
						&& !(searchtext.getText().contains("Name, SSN") || searchtext.getText().isEmpty())) {

					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}

					try {
						Object rowData[] = new Object[4];
						String sql="select hk.ssn, hk.contract_type, eh.contract_type, e.name, e.profession, s.cost_unit_number from housekeepingstaff hk inner join employee_hotel eh on hk.ssn=eh.idssn inner join employee e on e.ssn=hk.ssn inner join salary s on s.idssn=hk.ssn where eh.contract_type='fixed term' and (upper(e.name)  LIKE '%"+
						searchtext.getText()+"%'"+"or lower (e.name) LIKE '%"+ searchtext.getText()+"%')";

						con = Hotel.getConnection();
						stmt = con.createStatement();
						resultset = stmt.executeQuery(sql);
						while (resultset.next()) {
							rowData[0] = resultset.getString("ssn");
							rowData[1] = resultset.getString("name");
							rowData[2] = resultset.getString("profession");
							rowData[3] = resultset.getString("contract_type");

							model.addRow(rowData);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (staffList.getSelectedIndex() == 1 && contractList.getSelectedIndex() == 1) {

					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}

					try {
						Object rowData[] = new Object[4];
						String sql = "select hk.ssn, hk.contract_type, eh.contract_type, e.name, e.profession, s.cost_unit_number from housekeepingstaff hk inner join employee_hotel eh on hk.ssn=eh.idssn inner join employee e on e.ssn=hk.ssn inner join salary s on s.idssn=hk.ssn where eh.contract_type='fixed term'";

						con = Hotel.getConnection();
						stmt = con.createStatement();
						resultset = stmt.executeQuery(sql);
						while (resultset.next()) {
							rowData[0] = resultset.getString("ssn");
							rowData[1] = resultset.getString("name");
							rowData[2] = resultset.getString("profession");
							rowData[3] = resultset.getString("cost_unit_number");

							model.addRow(rowData);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (staffList.getSelectedIndex() == 1 && contractList.getSelectedIndex() == 2
						&& !(searchtext.getText().contains("Name, SSN") || searchtext.getText().isEmpty())) {

					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}

					try {
						Object rowData[] = new Object[4];
						String sql = "select hk.ssn, hk.contract_type, eh.contract_type, e.name, e.profession, s.cost_unit_number from housekeepingstaff hk inner join employee_hotel eh on hk.ssn=eh.idssn inner join employee e on e.ssn=hk.ssn inner join salary s on s.idssn=hk.ssn where eh.contract_type='permanent' and (upper(e.name)  LIKE '%"+
						searchtext.getText()+"%'"+"or lower (e.name) LIKE '%"+ searchtext.getText()+"%')";

						con = Hotel.getConnection();
						stmt = con.createStatement();
						resultset = stmt.executeQuery(sql);
						while (resultset.next()) {
							rowData[0] = resultset.getString("ssn");
							rowData[1] = resultset.getString("name");
							rowData[2] = resultset.getString("profession");
							rowData[3] = resultset.getString("cost_unit_number");

							model.addRow(rowData);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (staffList.getSelectedIndex() == 1 && contractList.getSelectedIndex() == 2) {

					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}

					try {
						Object rowData[] = new Object[4];
						String sql = "select hk.ssn, hk.contract_type, eh.contract_type, e.name, e.profession, s.cost_unit_number from housekeepingstaff hk inner join employee_hotel eh on hk.ssn=eh.idssn inner join employee e on e.ssn=hk.ssn inner join salary s on s.idssn=hk.ssn where eh.contract_type='permanent'";

						con = Hotel.getConnection();
						stmt = con.createStatement();
						resultset = stmt.executeQuery(sql);
						while (resultset.next()) {
							rowData[0] = resultset.getString("ssn");
							rowData[1] = resultset.getString("name");
							rowData[2] = resultset.getString("profession");
							rowData[3] = resultset.getString("cost_unit_number");

							model.addRow(rowData);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (staffList.getSelectedIndex() == 2 && contractList.getSelectedIndex() == 1
						&& !(searchtext.getText().contains("Name, SSN") || searchtext.getText().isEmpty())) {

					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}

					try {
						Object rowData[] = new Object[4];
						String sql = "select gr.ssn, gr.full_or_part_time, eh.contract_type, e.name, e.profession, s.cost_unit_number from gastronomystaff gr inner join employee_hotel eh on gr.ssn=eh.idssn inner join employee e on e.ssn=gr.ssn inner join salary s on s.idssn=gr.ssn where eh.contract_type='fixed term' and (upper(e.name)  LIKE '%"+
						searchtext.getText()+"%'"+"or lower (e.name) LIKE '%"+ searchtext.getText()+"%')";

						con = Hotel.getConnection();
						stmt = con.createStatement();
						resultset = stmt.executeQuery(sql);
						while (resultset.next()) {
							rowData[0] = resultset.getString("ssn");
							rowData[1] = resultset.getString("name");
							rowData[2] = resultset.getString("profession");
							rowData[3] = resultset.getString("cost_unit_number");

							model.addRow(rowData);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (staffList.getSelectedIndex() == 2 && contractList.getSelectedIndex() == 1) {

					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}

					try {
						Object rowData[] = new Object[4];
						String sql = "select gr.ssn, gr.full_or_part_time, eh.contract_type, e.name, e.profession, s.cost_unit_number from gastronomystaff gr inner join employee_hotel eh on gr.ssn=eh.idssn inner join employee e on e.ssn=gr.ssn inner join salary s on s.idssn=gr.ssn where eh.contract_type='fixed term' ";

						con = Hotel.getConnection();
						stmt = con.createStatement();
						resultset = stmt.executeQuery(sql);
						while (resultset.next()) {
							rowData[0] = resultset.getString("ssn");
							rowData[1] = resultset.getString("name");
							rowData[2] = resultset.getString("profession");
							rowData[3] = resultset.getString("cost_unit_number");

							model.addRow(rowData);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (staffList.getSelectedIndex() == 2 && contractList.getSelectedIndex() == 2
						&& !(searchtext.getText().contains("Name, SSN") || searchtext.getText().isEmpty())) {

					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}

					try {
						Object rowData[] = new Object[4];
						String sql = "select gr.ssn, gr.full_or_part_time, eh.contract_type, e.name, e.profession, s.cost_unit_number from gastronomystaff gr inner join employee_hotel eh on gr.ssn=eh.idssn "
								+ "inner join employee e on e.ssn=gr.ssn inner join salary s on s.idssn=gr.ssn where eh.contract_type='permanent' and (upper(e.name)  LIKE '%"+
						searchtext.getText()+"%'"+"or lower (e.name) LIKE '%"+ searchtext.getText()+"%')";

						con = Hotel.getConnection();
						stmt = con.createStatement();
						resultset = stmt.executeQuery(sql);
						while (resultset.next()) {
							rowData[0] = resultset.getString("ssn");
							rowData[1] = resultset.getString("name");
							rowData[2] = resultset.getString("profession");
							rowData[3] = resultset.getString("cost_unit_number");

							model.addRow(rowData);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (staffList.getSelectedIndex() == 2 && contractList.getSelectedIndex() == 2) {

					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}

					try {
						Object rowData[] = new Object[4];
						String sql = "select gr.ssn, gr.full_or_part_time, eh.contract_type, e.name, e.profession, s.cost_unit_number from gastronomystaff gr inner join employee_hotel eh on gr.ssn=eh.idssn"
								+ " inner join employee e on e.ssn=gr.ssn inner join salary s on s.idssn=gr.ssn where eh.contract_type='permanent'";

						con = Hotel.getConnection();
						stmt = con.createStatement();
						resultset = stmt.executeQuery(sql);
						while (resultset.next()) {
							rowData[0] = resultset.getString("ssn");
							rowData[1] = resultset.getString("name");
							rowData[2] = resultset.getString("profession");
							rowData[3] = resultset.getString("cost_unit_number");

							model.addRow(rowData);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} 
				else if (staffList.getSelectedIndex() == 3 && contractList.getSelectedIndex() == 2
						&& !(searchtext.getText().contains("Name, SSN") || searchtext.getText().isEmpty())) {

					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}

					try {
						Object rowData[] = new Object[4];
						String sql = "select rc.ssn, rc.full_or_part_time, eh.contract_type, e.name, e.profession, s.cost_unit_number from receptionstaff rc inner join employee_hotel eh on (rc.ssn=eh.idssn)"
								+ " inner join employee e on e.ssn=rc.ssn inner join salary s on s.idssn=rc.ssn where eh.contract_type='permanent' and (upper(e.name)  LIKE '%"+
						searchtext.getText()+"%'"+"or lower (e.name) LIKE '%"+ searchtext.getText()+"%')";

						con = Hotel.getConnection();
						stmt = con.createStatement();
						resultset = stmt.executeQuery(sql);
						while (resultset.next()) {
							rowData[0] = resultset.getString("ssn");
							rowData[1] = resultset.getString("name");
							rowData[2] = resultset.getString("profession");
							rowData[3] = resultset.getString("cost_unit_number");

							model.addRow(rowData);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (staffList.getSelectedIndex() == 3 && contractList.getSelectedIndex() == 2) {

					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}

					try {
						Object rowData[] = new Object[4];
						String sql = "select rc.ssn, rc.full_or_part_time, eh.contract_type, e.name, e.profession, s.cost_unit_number from receptionstaff rc inner join employee_hotel eh on (rc.ssn=eh.idssn)"
								+ "inner join employee e on e.ssn=rc.ssn inner join salary s on s.idssn=rc.ssn where eh.contract_type='permanent'";

						con = Hotel.getConnection();
						stmt = con.createStatement();
						resultset = stmt.executeQuery(sql);
						while (resultset.next()) {
							rowData[0] = resultset.getString("ssn");
							rowData[1] = resultset.getString("name");
							rowData[2] = resultset.getString("profession");
							rowData[3] = resultset.getString("cost_unit_number");

							model.addRow(rowData);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				else if (staffList.getSelectedIndex() == 3 && contractList.getSelectedIndex() == 1
						&& !(searchtext.getText().contains("Name, SSN") || searchtext.getText().isEmpty())) {

					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}

					try {
						Object rowData[] = new Object[4];
						String sql = "select rc.ssn, rc.full_or_part_time, eh.contract_type, e.name, e.profession, s.cost_unit_number from receptionstaff rc inner join employee_hotel eh on (rc.ssn=eh.idssn)"
								+ "inner join employee e on e.ssn=rc.ssn inner join salary s on s.idssn=rc.ssn where eh.contract_type='fixed term' and (upper(e.name)  LIKE '%"+
						searchtext.getText()+"%'"+"or lower (e.name) LIKE '%"+ searchtext.getText()+"%')";

						con = Hotel.getConnection();
						stmt = con.createStatement();
						resultset = stmt.executeQuery(sql);
						while (resultset.next()) {
							rowData[0] = resultset.getString("ssn");
							rowData[1] = resultset.getString("name");
							rowData[2] = resultset.getString("profession");
							rowData[3] = resultset.getString("cost_unit_number");

							model.addRow(rowData);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (staffList.getSelectedIndex() == 3 && contractList.getSelectedIndex() == 1) {

					while (model.getRowCount() > 0) {
						model.removeRow(0);
					}

					try {
						Object rowData[] = new Object[4];
						String sql = "select rc.ssn, rc.full_or_part_time, eh.contract_type, e.name, e.profession, s.cost_unit_number from receptionstaff rc inner join employee_hotel eh on (rc.ssn=eh.idssn)"
								+ "inner join employee e on e.ssn=rc.ssn inner join salary s on s.idssn=rc.ssn where eh.contract_type='fixed term'";

						con = Hotel.getConnection();
						stmt = con.createStatement();
						resultset = stmt.executeQuery(sql);
						while (resultset.next()) {
							rowData[0] = resultset.getString("ssn");
							rowData[1] = resultset.getString("name");
							rowData[2] = resultset.getString("profession");
							rowData[3] = resultset.getString("cost_unit_number");

							model.addRow(rowData);

						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		
		
		
		

		String[] staff = { "Select", "Housekeeping Staff", "Gastronomy Staff", "Reception Staff" };
		staffList = new JComboBox(staff);
		staffList.setSelectedIndex(0);
		symbolPanel.add(staffList);

		String[] contract = { "Select", "Fixed Term", "Permanent" };
		contractList = new JComboBox(contract);
		contractList.setSelectedIndex(0);
		symbolPanel.add(contractList);
		
		symbolPanel.add(showButton);
		
		JPanel textPanel=new JPanel();

		
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
		
		
		
		ssn=new JLabel("SSN: ");
		
		ssntext=new JTextField( );
		textPanel.add(ssn );
		textPanel.add(ssntext);
		
		name=new JLabel("Name: ");
		nametext=new JTextField( );
		textPanel.add(name);
		textPanel.add(nametext);
		
		profession=new JLabel("Profession: ");
		professiontext=new JTextField( );
		textPanel.add(profession);
		textPanel.add(professiontext);
		

		panel.add(symbolPanel);
		panel.add(textPanel ,BorderLayout.PAGE_END);

		JPanel buttonPanel=new JPanel();
		
		
		updatebutton=new JButton("Update");
		buttonPanel.add(updatebutton);
		updatebutton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				
				
				if ( ssntext.getText().isEmpty()||nametext.getText().isEmpty() || professiontext.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Please note that all the fields must be completed");
				}
				else{
				
					
				con=Hotel.getConnection(); 
				
			
				try {
					PreparedStatement ps = con.prepareStatement(
						      "UPDATE employee SET name = '"+nametext.getText()+"', profession= '"+professiontext.getText()+"' WHERE ssn ='"+ssntext.getText()+"';");
					
					

				    // call executeUpdate to execute our sql update statement
				    ps.executeUpdate();
				    ps.close();
				  
				    int modelindex = table.getSelectedRow();
				    int i=table.convertRowIndexToModel(modelindex);
	                
	                if(i >= 0) 
	                {
	                	model.setValueAt(ssntext.getText(), i, 0);
		                   model.setValueAt(nametext.getText(), i,1 );
		                   model.setValueAt(professiontext.getText(), i, 2);
		                  
	                
	                  
	                  
	                }
	                else{
	                  JOptionPane.showMessageDialog(null, "Please choose an employee to update");
	                }
							
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Please check the fields inserted!Unable to update the file");
				}

					  
					    
			
				
			}
			
		}});
		
		
		deletebutton=new JButton("Delete");
		
		buttonPanel.add(deletebutton);
		
		deletebutton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				

				ssntext.setEditable(false);
				nametext.setEditable(false);
				professiontext.setEditable(false);
				
				
				if (table.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Please choose a room in the table.");
				}else{
					
			
				
			try {
					PreparedStatement ps = con.prepareStatement("DELETE FROM employee WHERE ssn='"+ssntext.getText()+"';");
					ps.executeUpdate();
				    ps.close();
				  
				    int modelindex = table.getSelectedRow();
				    int i=table.convertRowIndexToModel(modelindex);
	                
	               

			            JOptionPane.showMessageDialog(null,"We have successfully deleted the ssn "+ssntext.getText());
				} catch (SQLException e1) {
				
					e1.printStackTrace();
				}
				
				

				
			
				
			}
			
		}});
		
		addnewemployeebutton=new JButton("Add new Employee");
		buttonPanel.add(addnewemployeebutton);
		addnewemployeebutton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
				addemployeeclass=new AddNewEmployee();
				addemployeeclass.setSize(700,700);
				addemployeeclass.setResizable(false);
				addemployeeclass.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				addemployeeclass.setVisible(true);
			}
			
		});
		
		
		
		salarybutton=new JButton("Add Salary");
		buttonPanel.add(salarybutton);
		salarybutton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				int i = table.getSelectedRow();
				if (i<0){
					JOptionPane.showMessageDialog(null, "Please note that you have to select a row with content inside.");
				}else{
				int modelIndex = table.convertRowIndexToModel(i);
				dispose();
				addsalary=new AddSalary();
				addsalary.ssnText.setText(model.getValueAt(modelIndex, 0).toString());
				addsalary.nameText.setText(model.getValueAt(modelIndex, 1).toString());
				addsalary.setSize(700,700);
				addsalary.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				addsalary.setVisible(true);
				
			}}
			
		});
		
		panel.add(buttonPanel);

		back = new JButton("<<");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				dispose();

				hotel = new Hotel();

			}

		});
		
		resetButton=new JButton("Reset fields");
		resetButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
			ssntext.setEditable(true);
			ssntext.setText("");
			nametext.setText("");
			professiontext.setText("");
				
			}
			
		});
		panel.add(back);
		panel.add(resetButton);
		

		add(panel);

	}

	

}