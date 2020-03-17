import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.ScrollPane;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Client extends JFrame{
    private DefaultTableModel model;
    private String columnNames[];
    private TableRowSorter<TableModel> sorter;
    private JTable table; 
    private JLabel searchLabel,name,id,phone,email,street,zip,city,country,picture,title;
    private JTextField searchText,idText,nameText,phoneText,emailText,streetText,zipText,cityText,countryText; 
    private JTextArea showbookingarea;
    private JButton search, back, alllist,delete,update,showbooking, clientcostbutton; 
    private Hotel hotel;
    private JComboBox searchList;
    Connection con=null;
    Statement stmt=null;
    ResultSet resultset=null;
    
    
    public Client(){
        
        JPanel panel=new JPanel(new BorderLayout());
        
        JPanel picturePanel=new JPanel();
        picturePanel.setLayout(new FlowLayout());
        
        picture=new JLabel();
        picture.setIcon(new ImageIcon("reception.jpg"));
        picture.setVisible(true);
    	
        
        picturePanel.add(picture);

        columnNames = new String[]
                {"ID","Name", "Phone","E-Mail","Street","ZIP","City","Country"};
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
				
				idText.setText(model.getValueAt(modelIndex, 0).toString());
				nameText.setText(model.getValueAt(modelIndex, 1).toString());
				phoneText.setText(model.getValueAt(modelIndex, 2).toString());
				emailText.setText(model.getValueAt(modelIndex, 3).toString());
				streetText.setText(model.getValueAt(modelIndex, 4).toString());
				zipText.setText(model.getValueAt(modelIndex, 5).toString());
				cityText.setText(model.getValueAt(modelIndex, 6).toString());
				countryText.setText(model.getValueAt(modelIndex, 7).toString());

			}
			}
		});
        table.setBackground(Color.darkGray);
        table.setForeground(Color.white);
        
        int index = 0;
        while (index < 8) {
			TableColumnModel tablecolumn = table.getColumnModel();
			TableColumn a = tablecolumn.getColumn(index);
			a.setPreferredWidth(1210);
			
			tablecolumn.getColumn(0).setPreferredWidth(100);
			tablecolumn.getColumn(1).setPreferredWidth(850);
			tablecolumn.getColumn(2).setPreferredWidth(900);
			tablecolumn.getColumn(3).setPreferredWidth(1100);
			tablecolumn.getColumn(4).setPreferredWidth(1100);
			tablecolumn.getColumn(5).setPreferredWidth(850);
			tablecolumn.getColumn(6).setPreferredWidth(700);
			tablecolumn.getColumn(7).setPreferredWidth(300);
			
			index++;
		}

        model = (DefaultTableModel) table.getModel();
        try {
            Object rowData[] = new Object[8];
            String sql="select c.id,c.name,c.phone,c.email,a.street_streetno,a.zip_code,a.city,a.country"
                    + " from client c,address a where c.id=a.idclient";
            con=Hotel.getConnection(); 
            stmt=con.createStatement();
            resultset = stmt.executeQuery(sql);
            while(resultset.next()){
                rowData[0] =resultset.getString("id");
                rowData[1] =resultset.getString("name");
                rowData[2] =resultset.getString("phone");
                rowData[3] =resultset.getString("email");
                rowData[4] =resultset.getString("street_streetno");
                rowData[5] =resultset.getString("zip_code");
                rowData[6] =resultset.getString("city");
                rowData[7] =resultset.getString("country");
                
                model.addRow(rowData);
                 
            }
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        sorter = new TableRowSorter<TableModel>(model);
        table.setRowSorter(sorter);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600,350));
        picturePanel.add(scrollPane,BorderLayout.NORTH);
           
        panel.add(picturePanel,BorderLayout.WEST);
        
        FlowLayout layoutManager = new FlowLayout(FlowLayout.LEFT);
        layoutManager.setHgap(5);
        layoutManager.setVgap(5);
        JPanel updatepanel=new JPanel(layoutManager);
        

        showbookingarea=new JTextArea(10,40);
        showbookingarea.setEditable(false);
        showbookingarea.setBounds(20, 10, 10, 30);
       JScrollPane scroll = new JScrollPane(showbookingarea);
       scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
       scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        updatepanel.add(scroll);
        
       
       update=new JButton("Update the client");

       update.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
			String id=idText.getText();
			String name=nameText.getText();
			String phone=phoneText.getText();
			String email=emailText.getText();
			String street=streetText.getText();
			String zip=zipText.getText();
			String city=cityText.getText();
			String country=countryText.getText();
			
			
			
			if (name.isEmpty()||phone.isEmpty() || email.isEmpty() || street.isEmpty()||
					zip.isEmpty()||city.isEmpty()||country.isEmpty()){
				JOptionPane.showMessageDialog(null, "Please note that all the fields must be completed");
			}
			else{
			
			con=Hotel.getConnection(); 

			try {
				PreparedStatement ps = con.prepareStatement(
					      "UPDATE client SET name = '"+name+"', phone = '"+phone+"', email ='"
				+email+ "'WHERE id ="+id+";");
				
				PreparedStatement ps1=con.prepareStatement( 
	"UPDATE address SET street_streetno='"+street+
	"',zip_code='"+zip+"',city='"+city+"',country='"
			+country +"'"
					+ "WHERE idclient ="+id+";");
	

			    // call executeUpdate to execute our sql update statement
			    ps.executeUpdate();
			    ps1.executeUpdate();
			    ps.close();
			    ps1.close();
			    int modelindex = table.getSelectedRow();
			    int i=table.convertRowIndexToModel(modelindex);
                
                if(i >= 0) 
                {
                   model.setValueAt(name, i, 1);
                   model.setValueAt(phone, i, 2);
                   model.setValueAt(email, i, 3);
                   model.setValueAt(street, i, 4);
                   model.setValueAt(zip, i, 5);
                   model.setValueAt(city, i, 6);
                   model.setValueAt(country, i, 7);
                   
                   
                }
                else{
                  JOptionPane.showMessageDialog(null, "Please choose a client to update");
                }
						
			} catch (SQLException e1) {
				System.out.println(e1);
			}

				  
				    
		
			
			
		}
    	   
    	   
    	   
		}});
       
       delete=new JButton("Delete the client");
       delete.addActionListener(new ActionListener() {

		public void actionPerformed(ActionEvent arg0) {
			String id=idText.getText();
			String name=nameText.getText();
			String phone=phoneText.getText();
			String email=emailText.getText();
			String street=streetText.getText();
			String zip=zipText.getText();
			String city=cityText.getText();
			String country=countryText.getText();
			
			
			
			if (name.isEmpty()||phone.isEmpty() || email.isEmpty() || street.isEmpty()||
					zip.isEmpty()||city.isEmpty()||country.isEmpty()){
				JOptionPane.showMessageDialog(null, "Please note that all the fields must be completed,thus choose a client in the table");
			}else {
				
			
			 int modelindex = table.getSelectedRow();
			    int i=table.convertRowIndexToModel(modelindex);
             
             if(i < 0) 
             {JOptionPane.showMessageDialog(null, "Please choose a client in the table to delete");}
             else {

				int result = JOptionPane.showConfirmDialog(null,
						"Are you sure to delete the client?");
				if (result == JOptionPane.OK_OPTION) {
				
				
					try {
						String selected = model.getValueAt(i, 0).toString();
						model.removeRow(i);
						
						PreparedStatement ps = con.prepareStatement(
							      "DELETE FROM client WHERE id="+selected+";");
					
						ps.executeUpdate();
						JOptionPane.showMessageDialog(null,"We have successfully deleted the client with idno: "+selected);
						
						idText.setText("");
						nameText.setText("");
						phoneText.setText("");
						emailText.setText("");
						streetText.setText("");
						zipText.setText("");
						cityText.setText("");
						countryText.setText("");
						
					} catch (SQLException e1) {
					
						e1.printStackTrace();
					}
					
					

				} 

		}}
		
	}});
       
       showbooking=new JButton("Show booking");
       updatepanel.add(showbooking);
       showbooking.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "Please choose a client in the table");
			}else{
				String sql="select br.arrival_date, br.departure_date,r.room_type,r.view "
						+ " from booking bk inner join client c on bk.idclient=c.id"+
						" inner join booking_room br on bk.id=br.idbooking"+
						" inner join room r on br.idroomno=r.room_number where c.id="+idText.getText();
				
				
		         try{
		        	 PreparedStatement ps = con.prepareStatement(sql);
		             ResultSet rs = ps.executeQuery();

		             while(rs.next()) {
		            	 
		            	 String newline = "\n\r";
		            	String sqlstring="Date: "+rs.getString("arrival_date")+", "+ rs.getString("departure_date")
		            	+" Meals: "+"Room: "+ rs.getString("room_type")+", "+ rs.getString("view")+
		            	newline;
		            				
		    			    
		    				showbookingarea.setText(sqlstring);           	
		                
		           
		             }
		              



		    }catch (Exception e1) { System.out.println(e1); }
		
				
			}
		}
    	   
       });
	
    	   updatepanel.add(delete);
    	   updatepanel.add(update);

       JPanel searchPanel=new JPanel();
       searchPanel.setLayout(new BoxLayout(searchPanel,BoxLayout.Y_AXIS));
       
        
        searchLabel=new JLabel("Search: ");
        searchPanel.add(searchLabel);
        
        searchText=new JTextField();
      
        searchPanel.add(searchText);
       
        
        String [] room={"Select","ID","Name","Phone number","E-Mail","Street","PLZ","City","Country"};
		searchList=new JComboBox(room);
		searchList.setSelectedIndex(0);
		searchPanel.add(searchList);

        search=new JButton("Search");
        searchPanel.add(search);
        search.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
            if(searchText.getText().isEmpty()) {
            	JOptionPane.showMessageDialog(null, "Please insert some information to search");
            }else if(searchList.getSelectedIndex()==0) {
            	JOptionPane.showMessageDialog(null, "Please choose some entry for searching");
            }
            else {
            	
   
            	
            	if (searchList.getSelectedIndex()==1 && searchText.getText().matches("[0-9]+")){
        	while (model.getRowCount() > 0) {
				model.removeRow(0);
			}

		
        	
        	Object rowData[] = new Object[8];
        	String sql="select c.id,c.name,c.phone,c.email,a.street_streetno,a.zip_code,a.city,a.country"
                    + " from client c,address a where c.id=a.idclient and c.id='"+searchText.getText()+"'";
        	
        	con = Hotel.getConnection();
			try {
				stmt = con.createStatement();
			
			resultset = stmt.executeQuery(sql);
			while (resultset.next()) {
				 rowData[0] =resultset.getString("id");
	                rowData[1] =resultset.getString("name");
	                rowData[2] =resultset.getString("phone");
	                rowData[3] =resultset.getString("email");
	                rowData[4] =resultset.getString("street_streetno");
	                rowData[5] =resultset.getString("zip_code");
	                rowData[6] =resultset.getString("city");
	                rowData[7] =resultset.getString("country");

				model.addRow(rowData);	
			}} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        
	        
	        }else if((searchList.getSelectedIndex()==1  &&(!searchText.getText().isEmpty() || !searchText.getText().matches("[0-9]+")))){
	        	JOptionPane.showMessageDialog(null, "Invalid insertion. Please insert an integer ");
	        }
        else if(searchList.getSelectedIndex()==2&&!searchText.getText().isEmpty()){
        	while (model.getRowCount() > 0) {
				model.removeRow(0);
			}

		
        	
        	Object rowData[] = new Object[8];
        	String sql="select c.id,c.name,c.phone,c.email,a.street_streetno,a.zip_code,a.city,a.country"
                    + " from client c,address a where c.id=a.idclient and (upper(c.name) LIKE'%"+searchText.getText()+"%'"
                    		+ "or lower (c.name) LIKE '%"+searchText.getText()+"%')";
        	

        	
        	con = Hotel.getConnection();
			try {
				stmt = con.createStatement();
			
			resultset = stmt.executeQuery(sql);
			while (resultset.next()) {
				 rowData[0] =resultset.getString("id");
	                rowData[1] =resultset.getString("name");
	                rowData[2] =resultset.getString("phone");
	                rowData[3] =resultset.getString("email");
	                rowData[4] =resultset.getString("street_streetno");
	                rowData[5] =resultset.getString("zip_code");
	                rowData[6] =resultset.getString("city");
	                rowData[7] =resultset.getString("country");

				model.addRow(rowData);	
        
        }} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        
        }
        else if(searchList.getSelectedIndex()==3&&!searchText.getText().isEmpty()){
        	while (model.getRowCount() > 0) {
				model.removeRow(0);
			}

        	Object rowData[] = new Object[8];
        	String sql="select c.id,c.name,c.phone,c.email,a.street_streetno,a.zip_code,a.city,a.country"
                    + " from client c,address a where c.id=a.idclient and c.phone LIKE '"+searchText.getText()+"%'";
        	
        	
        	
        	con = Hotel.getConnection();
			try {
				stmt = con.createStatement();
			
			resultset = stmt.executeQuery(sql);
			while (resultset.next()) {
				 rowData[0] =resultset.getString("id");
	                rowData[1] =resultset.getString("name");
	                rowData[2] =resultset.getString("phone");
	                rowData[3] =resultset.getString("email");
	                rowData[4] =resultset.getString("street_streetno");
	                rowData[5] =resultset.getString("zip_code");
	                rowData[6] =resultset.getString("city");
	                rowData[7] =resultset.getString("country");

				model.addRow(rowData);	
        
        }} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        
        }
        
        else if(searchList.getSelectedIndex()==4&&!searchText.getText().isEmpty()){
        	while (model.getRowCount() > 0) {
				model.removeRow(0);
			}

		
        	
        	Object rowData[] = new Object[8];
        	String sql="select c.id,c.name,c.phone,c.email,a.street_streetno,a.zip_code,a.city,a.country"
                    + " from client c,address a where c.id=a.idclient and (lower(c.email) LIKE'"+searchText.getText()+"%'"
                    		+ "or lower(c.email)LIKE '"+searchText.getText()+"%')";
        	
        	
        	
        	
        	con = Hotel.getConnection();
			try {
				stmt = con.createStatement();
			
			resultset = stmt.executeQuery(sql);
			while (resultset.next()) {
				 rowData[0] =resultset.getString("id");
	                rowData[1] =resultset.getString("name");
	                rowData[2] =resultset.getString("phone");
	                rowData[3] =resultset.getString("email");
	                rowData[4] =resultset.getString("street_streetno");
	                rowData[5] =resultset.getString("zip_code");
	                rowData[6] =resultset.getString("city");
	                rowData[7] =resultset.getString("country");

				model.addRow(rowData);	
        
        }} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        
        }
        else if(searchList.getSelectedIndex()==5&&!searchText.getText().isEmpty()){
        	while (model.getRowCount() > 0) {
				model.removeRow(0);
			}

		
        	
        	Object rowData[] = new Object[8];
        	String sql="select c.id,c.name,c.phone,c.email,a.street_streetno,a.zip_code,a.city,a.country"
                    + " from client c,address a where c.id=a.idclient and (lower(a.street_streetno) LIKE '"+searchText.getText()+"%'"
                    		+ "or lower(a.street_streetno) LIKE '"+searchText.getText()+"%')";
        	
        	
        	con = Hotel.getConnection();
			try {
				stmt = con.createStatement();
			
			resultset = stmt.executeQuery(sql);
			while (resultset.next()) {
				 rowData[0] =resultset.getString("id");
	                rowData[1] =resultset.getString("name");
	                rowData[2] =resultset.getString("phone");
	                rowData[3] =resultset.getString("email");
	                rowData[4] =resultset.getString("street_streetno");
	                rowData[5] =resultset.getString("zip_code");
	                rowData[6] =resultset.getString("city");
	                rowData[7] =resultset.getString("country");

				model.addRow(rowData);	
        
        }} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        
        
            
        }
        
        else if(searchList.getSelectedIndex()==6&&!searchText.getText().isEmpty()){
        	while (model.getRowCount() > 0) {
				model.removeRow(0);
			}

		
        	
        	Object rowData[] = new Object[8];
        	String sql="select c.id,c.name,c.phone,c.email,a.street_streetno,a.zip_code,a.city,a.country"
                    + " from client c,address a where c.id=a.idclient and a.zip_code LIKE'"+searchText.getText()+"%'";
        	
        	
        	con = Hotel.getConnection();
			try {
				stmt = con.createStatement();
			
			resultset = stmt.executeQuery(sql);
			while (resultset.next()) {
				 rowData[0] =resultset.getString("id");
	                rowData[1] =resultset.getString("name");
	                rowData[2] =resultset.getString("phone");
	                rowData[3] =resultset.getString("email");
	                rowData[4] =resultset.getString("street_streetno");
	                rowData[5] =resultset.getString("zip_code");
	                rowData[6] =resultset.getString("city");
	                rowData[7] =resultset.getString("country");

				model.addRow(rowData);	
        
        }} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        
        
            
        }
        else if(searchList.getSelectedIndex()==7&&!searchText.getText().isEmpty()){
        	while (model.getRowCount() > 0) {
				model.removeRow(0);
			}

		
        	
        	Object rowData[] = new Object[8];
        	String sql="select c.id,c.name,c.phone,c.email,a.street_streetno,a.zip_code,a.city,a.country"
                    + " from client c,address a where c.id=a.idclient and (upper(a.city)LIKE '"+searchText.getText()+"%'"
                    		+ "or lower(a.city)LIKE '"+searchText.getText()+"%')";
  
        	
        	con = Hotel.getConnection();
			try {
				stmt = con.createStatement();
			
			resultset = stmt.executeQuery(sql);
			while (resultset.next()) {
				 rowData[0] =resultset.getString("id");
	                rowData[1] =resultset.getString("name");
	                rowData[2] =resultset.getString("phone");
	                rowData[3] =resultset.getString("email");
	                rowData[4] =resultset.getString("street_streetno");
	                rowData[5] =resultset.getString("zip_code");
	                rowData[6] =resultset.getString("city");
	                rowData[7] =resultset.getString("country");

				model.addRow(rowData);	
        
        }} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        
        }  
        else if(searchList.getSelectedIndex()==8&&!searchText.getText().isEmpty()){
        	while (model.getRowCount() > 0) {
				model.removeRow(0);
			}

		
        	
        	Object rowData[] = new Object[8];
        	String sql="select c.id,c.name,c.phone,c.email,a.street_streetno,a.zip_code,a.city,a.country"
                    + " from client c,address a where c.id=a.idclient and (upper(a.country)LIKE '"+searchText.getText()+"%'"
                    		+ "or lower(a.country)LIKE '"+searchText.getText()+"%')";
        	con = Hotel.getConnection();
			try {
				stmt = con.createStatement();
			
			resultset = stmt.executeQuery(sql);
			while (resultset.next()) {
				 rowData[0] =resultset.getString("id");
	                rowData[1] =resultset.getString("name");
	                rowData[2] =resultset.getString("phone");
	                rowData[3] =resultset.getString("email");
	                rowData[4] =resultset.getString("street_streetno");
	                rowData[5] =resultset.getString("zip_code");
	                rowData[6] =resultset.getString("city");
	                rowData[7] =resultset.getString("country");

				model.addRow(rowData);	
        
        }} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        
        }
            }
        	
            
            }});
        
        alllist=new JButton("List of all clients");
        searchPanel.add(alllist);
        alllist.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}
				try {
		            Object rowData[] = new Object[8];
		            String sql="select c.id,c.name,c.phone,c.email,a.street_streetno,a.zip_code,a.city,a.country"
		                    + " from client c,address a where c.id=a.idclient";
		            con=Hotel.getConnection(); 
		            stmt=con.createStatement();
		            resultset = stmt.executeQuery(sql);
		            while(resultset.next()){
		                rowData[0] =resultset.getString("id");
		                rowData[1] =resultset.getString("name");
		                rowData[2] =resultset.getString("phone");
		                rowData[3] =resultset.getString("email");
		                rowData[4] =resultset.getString("street_streetno");
		                rowData[5] =resultset.getString("zip_code");
		                rowData[6] =resultset.getString("city");
		                rowData[7] =resultset.getString("country");
		                
		                model.addRow(rowData);
		                 
		            }
		        } catch (SQLException e1) {
		            // TODO Auto-generated catch block
		            e1.printStackTrace();
		        }
				
			}
        	
        });
   
        id=new JLabel("ID:");
        
        idText=new JTextField();
        idText.setPreferredSize(new Dimension(50,25));
        idText.setEditable(false);
        searchPanel.add(id);
        searchPanel.add(idText);
        
        
        name=new JLabel("Name:");
        nameText=new JTextField();
        nameText.setPreferredSize(new Dimension(100,25));
        searchPanel.add(name);
        searchPanel.add(nameText);
        
        phone=new JLabel("Phone number: ");
        phoneText=new JTextField();
        phoneText.setPreferredSize(new Dimension(100,25));
        searchPanel.add(phone);
        searchPanel.add(phoneText);
        
        email=new JLabel("E-mail:");
        emailText=new JTextField();
        emailText.setPreferredSize(new Dimension(100,25));
        searchPanel.add(email);
        searchPanel.add(emailText);
        
       street=new JLabel("Street:");
       streetText=new JTextField();
       streetText.setPreferredSize(new Dimension(100,25));
       searchPanel.add(street);
       searchPanel.add(streetText);
       
       zip=new JLabel("ZIP code: ");
       zipText=new JTextField();
       zipText.setPreferredSize(new Dimension(100,25));
       searchPanel.add(zip);
       searchPanel.add(zipText);
       
       city=new JLabel("City: ");
       cityText=new JTextField();
       cityText.setPreferredSize(new Dimension(100,25));
       searchPanel.add(city);
       searchPanel.add(cityText);
       
       country=new JLabel("Country: ");
       countryText=new JTextField();
       countryText.setPreferredSize(new Dimension(100,25));
       searchPanel.add(country);
       searchPanel.add(countryText);
       
       clientcostbutton=new JButton("Add client payment");
       clientcostbutton.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			
			int i = table.getSelectedRow();
			if (i<0){
				JOptionPane.showMessageDialog(null, "Please note that you have to select a row with content inside.");
			}else{
			int modelIndex = table.convertRowIndexToModel(i);
			dispose();
			ClientPayment clientpay=new ClientPayment();
			clientpay.idclienttext.setText(model.getValueAt(modelIndex, 0).toString());
			clientpay.setSize(700,700);
			clientpay.setResizable(false);
			clientpay.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			clientpay.setVisible(true);
			
		}
		
		}});
			
			
       
    
       
       back=new JButton("<<");
        back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            
                dispose();
                
                hotel=new Hotel();
                
            }
            
        });
        searchPanel.add(back);
        searchPanel.add(clientcostbutton);        
        
     
       panel.add(updatepanel,BorderLayout.SOUTH);
       
       panel.add(searchPanel,BorderLayout.CENTER);
        
        add(panel,BorderLayout.CENTER);
        
    }
}