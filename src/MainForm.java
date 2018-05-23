import java.awt.GridBagConstraints;

import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainForm extends JFrame
{
	public static String textName, textSalary, textNIN, textEmail, textJobTitle, textAddress, textPostcode, textGenderButton, textDate0, textDate1, textMonth0, textMonth1, textYear0, textYear1;
	public static int id = (EmployeeDAO.NumEmployee+1);
	public static byte[] pic;
	int startid = EmployeeDAO.NumEmployee;
	
	
	EmployeeDAO dao = new EmployeeDAO();
	JPanel pnPanel0, pnPanel1;
	ButtonGroup rbgPanel0;
	
	JButton btClearButton, btEnterButton, btBackButton, btForwardButton, btUpdateButton, btUploadButton, btNewButton;
	
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem displayItem, searchItem, deleteItem;
	JFrame frame;
	String searchID, deleteID;
	
	public static Blob bob;
	public static JLabel lbIDLabel, lbPhoto;
	JLabel lbHeader, lbSalaryLabel, lbNameLabel, lbNumEmployeeLabel, lbGenderLabel, lbNILabel, lbEmailLabel, lbStartDateLabel, lbJobLabel, lbDOBLabel, lbAddressLabel, lbPostcodeLabel;
	
	public static JTextField tfText0, tfText1, tfText2, tfText3, tfText4, tfText5, tfText6;
	
	public static JRadioButton rbMaleRButton, rbFemaleRButton;
	public static JComboBox cmbDateCombo, cmbMonthCombo, cmbYearCombo, cmbDateCombo1, cmbMonthCombo1, cmbYearCombo1;
	
	//Code needed for the Clear Button
	private void clearHandler(){
		tfText0.setText(" ");
		tfText1.setText(" ");
		tfText2.setText(" ");
		tfText3.setText(" ");
		tfText4.setText(" ");
		tfText5.setText(" ");
		tfText6.setText(" ");
		
		rbMaleRButton.setSelected(false);
		rbFemaleRButton.setSelected(false);
		cmbDateCombo.setSelectedItem("01");
		cmbMonthCombo.setSelectedItem("01");
		cmbYearCombo.setSelectedItem("2017");
		cmbDateCombo1.setSelectedItem("01");
		cmbMonthCombo1.setSelectedItem("01");
		cmbYearCombo1.setSelectedItem("2017");
		lbPhoto.setIcon(null);
	}
	
	//Code needed for the Update Button
	private void updateHandler(){
		textName = tfText0.getText();
		
		//IF statement to figure out which Radio Button is selected
		if (rbMaleRButton.isSelected()){
			textGenderButton = "M";
		}else if(rbFemaleRButton.isSelected()){
				textGenderButton = "F";
		}
		
		textDate0 = cmbDateCombo.getSelectedItem().toString();
		textMonth0 = cmbMonthCombo.getSelectedItem().toString();
		textYear0 = cmbYearCombo.getSelectedItem().toString();
		
		textSalary = tfText1.getText();
		textNIN = tfText2.getText();
		textEmail = tfText3.getText();
		
		textDate1 = cmbDateCombo1.getSelectedItem().toString();
		textMonth1 = cmbMonthCombo1.getSelectedItem().toString();
		textYear1 = cmbYearCombo1.getSelectedItem().toString();
		
		textJobTitle = tfText4.getText();
		textAddress = tfText5.getText();
		textPostcode = tfText6.getText();
		
		
		System.out.println(textName);
		System.out.println(textGenderButton);
		System.out.println(textDate0 + "/" + textMonth0 + "/" + textYear0);
		System.out.println("£" + textSalary);
		System.out.println(textNIN);
		System.out.println(textEmail);
		System.out.println(textDate1 + "/" + textMonth1 + "/" + textYear1);
		System.out.println(textJobTitle);
		System.out.println(textAddress);
		System.out.println(textPostcode);
		EmployeeDAO.updateEmployee();
	}
	
	//Code for the search function
	private void search() throws SQLException{
		searchID = JOptionPane.showInputDialog("Please Enter an ID: ");
		EmployeeDAO.MainFormID = Integer.parseInt(searchID);
		id = EmployeeDAO.MainFormID;
		EmployeeDAO.selectEmployeeByID();
	}
	
	//Code for the Upload Picture button
	private byte[] uploadHandler(){
		//Create a file chooser
		final JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
			    "Image files", ImageIO.getReaderFileSuffixes());

			//Attaching Filter to JFileChooser object
			fc.setFileFilter(imageFilter);
		//In response to a button click:
		int returnVal = fc.showOpenDialog(btUploadButton);
		
		if(returnVal==JFileChooser.APPROVE_OPTION)
	    {
	        File file = fc.getSelectedFile();
	        BufferedImage bi;
	        try
	        {
	            bi=ImageIO.read(file);
				Image icon =(new ImageIcon(bi).getImage().getScaledInstance(145, 145, Image.SCALE_DEFAULT));
	            lbPhoto.setIcon(new ImageIcon(icon));
	            ByteArrayOutputStream baos = null;
	            try {
	                baos = new ByteArrayOutputStream();
	                ImageIO.write(bi, "png", baos);
	            } finally {
	                try {
//	                    baos.close();
	                } catch (Exception e) {
	                }
	            }
	          return baos.toByteArray();
	        }
	        catch(IOException e)
	        {
	        }
	        //this.pack();
	    }
		return null;
	}
	
	//Code for delete button
	private void delete(){
		deleteID = JOptionPane.showInputDialog("Please Enter an ID: ");
		EmployeeDAO.MainFormID = Integer.parseInt(deleteID);
		id = EmployeeDAO.MainFormID;
		EmployeeDAO.deleteEmployeeByID();
	}
	
	//Code used to display the first record in the database
	private void display() throws SQLException{
		EmployeeDAO.MainFormID = 1;
		id = EmployeeDAO.MainFormID;
		EmployeeDAO.selectEmployeeByID();
	}
	
	//Code for the back button
	private void backHandler() throws SQLException{
			if (EmployeeDAO.MainFormID>1){
				EmployeeDAO.MainFormID = id -1;
				id = EmployeeDAO.MainFormID;
				EmployeeDAO.selectEmployeeByID();
		}
	}
	
	//code for the forward button
	private void forwardHandler() throws SQLException{
		if (EmployeeDAO.MainFormID <= (EmployeeDAO.NumEmployee-1)){
			EmployeeDAO.MainFormID = id +1;
			id = EmployeeDAO.MainFormID;
			EmployeeDAO.selectEmployeeByID();
		}
	}
	
	//code for the enter button
	public void read() throws SQLException{
		textName = tfText0.getText();
		
		if (rbMaleRButton.isSelected()){
			textGenderButton = "M";
		}else if(rbFemaleRButton.isSelected()){
				textGenderButton = "F";
		}
		
		textDate0 = cmbDateCombo.getSelectedItem().toString();
		textMonth0 = cmbMonthCombo.getSelectedItem().toString();
		textYear0 = cmbYearCombo.getSelectedItem().toString();
		
		textSalary = tfText1.getText();
		textNIN = tfText2.getText();
		textEmail = tfText3.getText();
		
		textDate1 = cmbDateCombo1.getSelectedItem().toString();
		textMonth1 = cmbMonthCombo1.getSelectedItem().toString();
		textYear1 = cmbYearCombo1.getSelectedItem().toString();
		
		textJobTitle = tfText4.getText();
		textAddress = tfText5.getText();
		textPostcode = tfText6.getText();
		EmployeeDAO.insertEmployee();
	}
	public MainForm()
	{
		//What is at the top of the program
		super("Employee Record System");
		
		
		pnPanel0 = new JPanel();
		pnPanel0.setBorder( BorderFactory.createTitledBorder( "" ) );
		rbgPanel0 = new ButtonGroup();
		GridBagLayout gbPanel0 = new GridBagLayout();
		GridBagConstraints gbcPanel0 = new GridBagConstraints();
		pnPanel0.setLayout( gbPanel0 );
		pnPanel1 = new JPanel();
		pnPanel1.setBorder( BorderFactory.createTitledBorder( "" ) );
		
				//Create the menu bar.
				menuBar = new JMenuBar();

				//Build the first menu.
				menu = new JMenu("Record");
				menu.setMnemonic(KeyEvent.VK_A);
				menuBar.add(menu);

				//a group of JMenuItems
				displayItem = new JMenuItem("Display",
				                         KeyEvent.VK_T);
				displayItem.setAccelerator(KeyStroke.getKeyStroke(
				        KeyEvent.VK_1, ActionEvent.ALT_MASK));
				displayItem.getAccessibleContext().setAccessibleDescription("Displays the first employee record in the database");
				displayItem.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						try {
							display();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				menu.add(displayItem);
				deleteItem = new JMenuItem("Delete by ID",
                        	KeyEvent.VK_T);
				deleteItem.setAccelerator(KeyStroke.getKeyStroke(
				       KeyEvent.VK_2, ActionEvent.ALT_MASK));
				deleteItem.getAccessibleContext().setAccessibleDescription("Deletes a record in the database by ID");
				deleteItem.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						delete();
					}
				});
				menu.add(deleteItem);
				searchItem = new JMenuItem("Search by ID",
                    	KeyEvent.VK_T);
			searchItem.setAccelerator(KeyStroke.getKeyStroke(
			       KeyEvent.VK_3, ActionEvent.ALT_MASK));
			searchItem.getAccessibleContext().setAccessibleDescription("Searches for a record in the database by ID");
			searchItem.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						search();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			menu.add(searchItem);
				setJMenuBar(menuBar);
		
		btEnterButton = new JButton( "Enter"  );
		gbcPanel0.gridx = 0;
		gbcPanel0.gridy = 23;
		gbcPanel0.gridwidth = 6;
		gbcPanel0.gridheight = 3;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( btEnterButton, gbcPanel0 );
		pnPanel0.add( btEnterButton );
		btEnterButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					read();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btClearButton = new JButton( "Clear"  );
		gbcPanel0.gridx = 8;
		gbcPanel0.gridy = 23;
		gbcPanel0.gridwidth = 6;
		gbcPanel0.gridheight = 3;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( btClearButton, gbcPanel0 );
		pnPanel0.add( btClearButton );
		btClearButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				clearHandler ();
			}
		});
		
		btUploadButton = new JButton( "Upload Picture"  );
		gbcPanel0.gridx = 8;
		gbcPanel0.gridy = 19;
		gbcPanel0.gridwidth = 11;
		gbcPanel0.gridheight = 2;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( btUploadButton, gbcPanel0 );
		pnPanel0.add( btUploadButton );
		btUploadButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				pic=uploadHandler ();
			}
		});
		
		
		btUpdateButton = new JButton( "Update"  );
		gbcPanel0.gridx = 8;
		gbcPanel0.gridy = 21;
		gbcPanel0.gridwidth = 11;
		gbcPanel0.gridheight = 2;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( btUpdateButton, gbcPanel0 );
		pnPanel0.add( btUpdateButton );
		btUpdateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				updateHandler ();
			}
		});


		btBackButton = new JButton( "Back"  );
		gbcPanel0.gridx = 15;
		gbcPanel0.gridy = 22;
		gbcPanel0.gridwidth = 5;
		gbcPanel0.gridheight = 2;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( btBackButton, gbcPanel0 );
		pnPanel0.add( btBackButton );
		btBackButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					backHandler ();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		btForwardButton = new JButton( "Forward"  );
		gbcPanel0.gridx = 15;
		gbcPanel0.gridy = 24;
		gbcPanel0.gridwidth = 5;
		gbcPanel0.gridheight = 2;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( btForwardButton, gbcPanel0 );
		pnPanel0.add( btForwardButton );
		btForwardButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					forwardHandler ();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		

		lbHeader = new JLabel( "Enter Employee Information"  );
		gbcPanel0.gridx = 2;
		gbcPanel0.gridy = 0;
		gbcPanel0.gridwidth = 1;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 1;
		gbcPanel0.anchor = GridBagConstraints.CENTER;
		gbPanel0.setConstraints( lbHeader, gbcPanel0 );
		pnPanel0.add( lbHeader );

		lbNameLabel = new JLabel( "Enter Name:"  );
		gbcPanel0.gridx = 0;
		gbcPanel0.gridy = 1;
		gbcPanel0.gridwidth = 1;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 1;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( lbNameLabel, gbcPanel0 );
		pnPanel0.add( lbNameLabel );
		
//		lbNumEmployeeLabel = new JLabel("Number of Employee: " + startid  );
//		gbcPanel0.gridx = 15;
//		gbcPanel0.gridy = 1;
//		gbcPanel0.gridwidth = 1;
//		gbcPanel0.gridheight = 1;
//		gbcPanel0.fill = GridBagConstraints.BOTH;
//		gbcPanel0.weightx = 1;
//		gbcPanel0.weighty = 1;
//		gbcPanel0.anchor = GridBagConstraints.NORTH;
//		gbPanel0.setConstraints( lbNumEmployeeLabel, gbcPanel0 );
//		pnPanel0.add( lbNumEmployeeLabel );
		
		lbIDLabel = new JLabel( "ID: " + (id) );
		gbcPanel0.gridx = 15;
		gbcPanel0.gridy = 2;
		gbcPanel0.gridwidth = 1;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 1;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( lbIDLabel, gbcPanel0 );
		pnPanel0.add( lbIDLabel );	

		lbGenderLabel = new JLabel( "Select Gender"  );
		gbcPanel0.gridx = 0;
		gbcPanel0.gridy = 2;
		gbcPanel0.gridwidth = 1;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 1;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( lbGenderLabel, gbcPanel0 );
		pnPanel0.add( lbGenderLabel );	

		lbDOBLabel = new JLabel( "Select Date Of Birth:"  );
		gbcPanel0.gridx = 0;
		gbcPanel0.gridy = 7;
		gbcPanel0.gridwidth = 1;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 1;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( lbDOBLabel, gbcPanel0 );
		pnPanel0.add( lbDOBLabel );
		
		lbSalaryLabel = new JLabel( "Enter Salary:"  );
		gbcPanel0.gridx = 0;
		gbcPanel0.gridy = 9;
		gbcPanel0.gridwidth = 7;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 1;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( lbSalaryLabel, gbcPanel0 );
		pnPanel0.add( lbSalaryLabel );
		
		ImageIcon icon = new ImageIcon( );
		lbPhoto = new JLabel( );
		lbPhoto.setIcon(icon);
		gbcPanel0.gridx = 15;
		gbcPanel0.gridy = 9;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( lbPhoto, gbcPanel0 );
		pnPanel0.add( lbPhoto );
		
		lbNILabel = new JLabel( "Enter NI Number:"  );
		gbcPanel0.gridx = 0;
		gbcPanel0.gridy = 11;
		gbcPanel0.gridwidth = 7;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 1;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( lbNILabel, gbcPanel0 );
		pnPanel0.add( lbNILabel );

		lbEmailLabel = new JLabel( "Enter Emai:"  );
		gbcPanel0.gridx = 0;
		gbcPanel0.gridy = 13;
		gbcPanel0.gridwidth = 7;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 1;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( lbEmailLabel, gbcPanel0 );
		pnPanel0.add( lbEmailLabel );

		lbStartDateLabel = new JLabel( "Select Start Date:"  );
		gbcPanel0.gridx = 0;
		gbcPanel0.gridy = 15;
		gbcPanel0.gridwidth = 7;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 1;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( lbStartDateLabel, gbcPanel0 );
		pnPanel0.add( lbStartDateLabel );

		lbJobLabel = new JLabel( "Enter Job Title:"  );
		gbcPanel0.gridx = 0;
		gbcPanel0.gridy = 17;
		gbcPanel0.gridwidth = 7;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 1;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( lbJobLabel, gbcPanel0 );
		pnPanel0.add( lbJobLabel );
		
		lbAddressLabel = new JLabel( "Enter Address:"  );
		gbcPanel0.gridx = 0;
		gbcPanel0.gridy = 19;
		gbcPanel0.gridwidth = 7;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 1;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( lbAddressLabel, gbcPanel0 );
		pnPanel0.add( lbAddressLabel );
		
		lbPostcodeLabel = new JLabel( "Enter Postcode:"  );
		gbcPanel0.gridx = 0;
		gbcPanel0.gridy = 21;
		gbcPanel0.gridwidth = 7;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 1;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( lbPostcodeLabel, gbcPanel0 );
		pnPanel0.add( lbPostcodeLabel );

		tfText0 = new JTextField( );
		gbcPanel0.gridx = 1;
		gbcPanel0.gridy = 1;
		gbcPanel0.gridwidth = 3;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( tfText0, gbcPanel0 );
		pnPanel0.add( tfText0 );
		
		tfText1 = new JTextField( );
		gbcPanel0.gridx = 1;
		gbcPanel0.gridy = 9;
		gbcPanel0.gridwidth = 3;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( tfText1, gbcPanel0 );
		pnPanel0.add( tfText1 );

		tfText2 = new JTextField( );
		gbcPanel0.gridx = 1;
		gbcPanel0.gridy = 11;
		gbcPanel0.gridwidth = 3;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( tfText2, gbcPanel0 );
		pnPanel0.add( tfText2 );

		tfText3 = new JTextField( );
		gbcPanel0.gridx = 1;
		gbcPanel0.gridy = 13;
		gbcPanel0.gridwidth = 3;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 3;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( tfText3, gbcPanel0 );
		pnPanel0.add( tfText3 );

		tfText4 = new JTextField( );
		gbcPanel0.gridx = 1;
		gbcPanel0.gridy = 17;
		gbcPanel0.gridwidth = 3;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( tfText4, gbcPanel0 );
		pnPanel0.add( tfText4 );
		
		tfText5 = new JTextField( );
		gbcPanel0.gridx = 1;
		gbcPanel0.gridy = 19;
		gbcPanel0.gridwidth = 3;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( tfText5, gbcPanel0 );
		pnPanel0.add( tfText5 );
		
		tfText6 = new JTextField( );
		gbcPanel0.gridx = 1;
		gbcPanel0.gridy = 21;
		gbcPanel0.gridwidth = 3;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( tfText6, gbcPanel0 );
		pnPanel0.add( tfText6 );
		
		String []dataDateCombo1 = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", 
                "12", "13", "14", "15", "16", "17", "18", "19", "20", 
                "21", "22", "23", "24", "25", "26", "27", "28", "29", 
                "30", "31" };
		cmbDateCombo1 = new JComboBox( dataDateCombo1 );
		gbcPanel0.gridx = 1;
		gbcPanel0.gridy = 15;
		gbcPanel0.gridwidth = 1;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( cmbDateCombo1, gbcPanel0 );
		pnPanel0.add( cmbDateCombo1 );
		
		String []dataMonthCombo1 = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", 
                "11", "12" };
		cmbMonthCombo1 = new JComboBox( dataMonthCombo1 );
		gbcPanel0.gridx = 2;
		gbcPanel0.gridy = 15;
		gbcPanel0.gridwidth = 1;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( cmbMonthCombo1, gbcPanel0 );
		pnPanel0.add( cmbMonthCombo1 );
		
		String []dataYearCombo1 = { "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010", 
		               "2009", "2008", "2007", "2006", "2005", "2004", "2003", 
		               "2002", "2001", "2000", "1999", "1998", "1997", "1996", 
		               "1995", "1994", "1993", "1992", "1991", "1990", "1989", 
		               "1988", "1987", "1986", "1985", "1984", "1983", "1982", 
		               "1981", "1980", "1979", "1978", "1977", "1976", "1975", 
		               "1974", "1973", "1972", "1971", "1970", "1969", "1968", 
		               "1967", "1966", "1965", "1964", "1963", "1962", "1961", 
		               "1960", "1959", "1958", "1957", "1956", "1955", "1954", 
		               "1953", "1952", "1951", "1950", "1949", "1948", "1947", 
		               "1946", "1945", "1944", "1943", "1942", "1941", "1940", 
		               "1939", "1938", "1937", "1936", "1935", "1934", "1933", 
		               "1932", "1931", "1930", "1929", "1928", "1927", "1926", 
		               "1925", "1924", "1923", "1922", "1921", "1920", "1919", 
		               "1918", "1917", "1916", "1915", "1914", "1913", "1912", 
		               "1911", "1910", "1909", "1908", "1907", "1906", "1905", 
		               "1904", "1903", "1902", "1901" };
		cmbYearCombo1 = new JComboBox( dataYearCombo1 );
		gbcPanel0.gridx = 3;
		gbcPanel0.gridy = 15;
		gbcPanel0.gridwidth = 1;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( cmbYearCombo1, gbcPanel0 );
		pnPanel0.add( cmbYearCombo1 );

		rbMaleRButton = new JRadioButton( "Male"  );
		rbgPanel0.add( rbMaleRButton );
		gbcPanel0.gridx = 1;
		gbcPanel0.gridy = 2;
		gbcPanel0.gridwidth = 1;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( rbMaleRButton, gbcPanel0 );
		pnPanel0.add( rbMaleRButton );

		rbFemaleRButton = new JRadioButton( "Female"  );
		rbFemaleRButton.setActionCommand( "" );
		rbFemaleRButton.setSelected( true );
		rbgPanel0.add( rbFemaleRButton );
		gbcPanel0.gridx = 2;
		gbcPanel0.gridy = 2;
		gbcPanel0.gridwidth = 1;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( rbFemaleRButton, gbcPanel0 );
		pnPanel0.add( rbFemaleRButton );

		String []dataDateCombo = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", 
		                           "12", "13", "14", "15", "16", "17", "18", "19", "20", 
		                           "21", "22", "23", "24", "25", "26", "27", "28", "29", 
		                           "30", "31" };
		cmbDateCombo = new JComboBox( dataDateCombo );
		gbcPanel0.gridx = 1;
		gbcPanel0.gridy = 7;
		gbcPanel0.gridwidth = 1;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( cmbDateCombo, gbcPanel0 );
		pnPanel0.add( cmbDateCombo );
		
		String []dataMonthCombo = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", 
                "11", "12" };
		cmbMonthCombo = new JComboBox( dataMonthCombo );
		gbcPanel0.gridx = 2;
		gbcPanel0.gridy = 7;
		gbcPanel0.gridwidth = 1;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( cmbMonthCombo, gbcPanel0 );
		pnPanel0.add( cmbMonthCombo );
		
		String []dataYearCombo = { "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010", 
		               "2009", "2008", "2007", "2006", "2005", "2004", "2003", 
		               "2002", "2001", "2000", "1999", "1998", "1997", "1996", 
		               "1995", "1994", "1993", "1992", "1991", "1990", "1989", 
		               "1988", "1987", "1986", "1985", "1984", "1983", "1982", 
		               "1981", "1980", "1979", "1978", "1977", "1976", "1975", 
		               "1974", "1973", "1972", "1971", "1970", "1969", "1968", 
		               "1967", "1966", "1965", "1964", "1963", "1962", "1961", 
		               "1960", "1959", "1958", "1957", "1956", "1955", "1954", 
		               "1953", "1952", "1951", "1950", "1949", "1948", "1947", 
		               "1946", "1945", "1944", "1943", "1942", "1941", "1940", 
		               "1939", "1938", "1937", "1936", "1935", "1934", "1933", 
		               "1932", "1931", "1930", "1929", "1928", "1927", "1926", 
		               "1925", "1924", "1923", "1922", "1921", "1920", "1919", 
		               "1918", "1917", "1916", "1915", "1914", "1913", "1912", 
		               "1911", "1910", "1909", "1908", "1907", "1906", "1905", 
		               "1904", "1903", "1902", "1901" };
		cmbYearCombo = new JComboBox( dataYearCombo );
		gbcPanel0.gridx = 3;
		gbcPanel0.gridy = 7;
		gbcPanel0.gridwidth = 1;
		gbcPanel0.gridheight = 1;
		gbcPanel0.fill = GridBagConstraints.BOTH;
		gbcPanel0.weightx = 1;
		gbcPanel0.weighty = 0;
		gbcPanel0.anchor = GridBagConstraints.NORTH;
		gbPanel0.setConstraints( cmbYearCombo, gbcPanel0 );
		pnPanel0.add( cmbYearCombo );

		this.getContentPane().add(pnPanel0);
	}
}
