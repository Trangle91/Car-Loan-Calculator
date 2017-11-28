import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Document;
import javax.swing.ButtonModel;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Handler;
import java.awt.event.ActionEvent;

public class BonApp{

	private JFrame frame;
	private JTextField textFieldCapital;
	private JTextField textFieldAPR;
	private JTextField textFieldNoOfMonths;
	private JTextField textFieldMP;

	private double capital;
	private int NoOfMonths;
	private double APR;
	private double monthlyPayment;

	private JButton btnCalculate;
	private JButton btnAddToGrid;
	private JButton btnPrintGrid;
	private JButton btnClear;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BonApp window = new BonApp();
					window.frame.setVisible(true);
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BonApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//create a frame and a table
		frame = new JFrame();
		JTable table = new JTable(); 
        
        // create a table model and set a Column Identifiers to this model 
        Object[] columns = {"Capital Amount", "APR", "No Of Months", "Monthly Payment"};
        Object[] rows = new Object[4];
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        
        table.setModel(model);
        
       //set table's properties
        table.setBackground(Color.LIGHT_GRAY);
        table.setForeground(Color.black);
        Font font = new Font("",1,16);
        table.setFont(font);
        table.setRowHeight(20);
        
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(59, 173, 300, 80);     
        frame.add(pane);
		
		frame.setBounds(100,100,467,322);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblCapital = new JLabel("Capital");
		lblCapital.setBounds(52, 23, 61, 16);
		frame.getContentPane().add(lblCapital);
		
		//create textFields && labels
		textFieldCapital = new JTextField();
		textFieldCapital.setBounds(229, 18, 130, 26);
		frame.getContentPane().add(textFieldCapital);
		textFieldCapital.setColumns(10);
		
		JLabel lblApr = new JLabel("APR (%)");
		lblApr.setBounds(52, 51, 61, 16);
		frame.getContentPane().add(lblApr);
		
		textFieldAPR = new JTextField();
		textFieldAPR.setBounds(229, 46, 130, 26);
		frame.getContentPane().add(textFieldAPR);
		textFieldAPR.setColumns(10);
		
		JLabel lblNoofmonths = new JLabel("NoOfMonths");
		lblNoofmonths.setBounds(52, 79, 61, 16);
		frame.getContentPane().add(lblNoofmonths);
		
		textFieldNoOfMonths = new JTextField();
		textFieldNoOfMonths.setBounds(229, 74, 130, 26);
		frame.getContentPane().add(textFieldNoOfMonths);
		textFieldNoOfMonths.setColumns(10);
		
		JLabel lblMonthlyPayment = new JLabel("Monthly Payment");
		lblMonthlyPayment.setBounds(52, 113, 61, 16);
		frame.getContentPane().add(lblMonthlyPayment);
		
		textFieldMP = new JTextField();
		textFieldMP.setBounds(229, 108, 130, 26);
		frame.add(textFieldMP);
		textFieldMP.setColumns(10);

		//calculate button
		btnCalculate = new JButton("Calculate");
		btnCalculate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
			 if(textFieldCapital.getText().trim().isEmpty())
					calculateCapital( APR, NoOfMonths,monthlyPayment);
				else if(textFieldAPR.getText().trim().isEmpty())
					calculateAPR	(capital,NoOfMonths,monthlyPayment);
				else if (textFieldNoOfMonths.getText().trim().isEmpty())
					calculateNoOfMonths(capital,APR,monthlyPayment);
				else if (textFieldMP.getText().trim().isEmpty()) {
					if(textFieldAPR.getText().equals("0"))
						APRIs0(capital,NoOfMonths);
					else
						calculateMP(capital,APR,NoOfMonths);
				}
			}
		});
		btnCalculate.setBounds(290, 139, 117, 29);
		frame.getContentPane().add(btnCalculate);
	
	
		//add to grid button
		btnAddToGrid = new JButton("Add To Grid");
		
		btnAddToGrid.addActionListener(new ActionListener(){

	            @Override
	            public void actionPerformed(ActionEvent e) {	             
	                rows[0] = textFieldCapital.getText();
	                rows[1] = textFieldAPR.getText();
	                rows[2] = textFieldNoOfMonths.getText();
	                rows[3] = textFieldMP.getText();
	                
	                // add row to the model
	                model.addRow(rows);
	            }
	        });
		
	
		btnAddToGrid.setBounds(182, 139, 117, 29);
		frame.getContentPane().add(btnAddToGrid);
		
		//print button
		btnPrintGrid = new JButton("Print grid");
		btnPrintGrid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				    if (!table.print()) {
				        System.err.println("User cancelled printing");
				    }
				} catch (java.awt.print.PrinterException ex1) {
				    System.err.format("Cannot print %s%n", ex1.getMessage());
				}
			}
		});
		btnPrintGrid.setBounds(329, 265, 117, 29);
		frame.getContentPane().add(btnPrintGrid);
		
		//clear button
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldCapital.setText("");
				textFieldAPR.setText("");
				textFieldNoOfMonths.setText("");
				textFieldMP.setText("");
			}
		});
		btnClear.setBounds(74, 139, 117, 29);
		frame.getContentPane().add(btnClear);
		
		//enable the calculate button
				ButtonModel modell = btnCalculate.getModel();
				Document document1 = textFieldCapital.getDocument();
				Document document2 = textFieldAPR.getDocument();
				Document document3 = textFieldNoOfMonths.getDocument();
				Document document4 = textFieldMP.getDocument();
				
				bEnabling buttonEnablement = new bEnabling(modell);
				buttonEnablement.addDocument(document1);
				buttonEnablement.addDocument(document2);
				buttonEnablement.addDocument(document3);
				buttonEnablement.addDocument(document4);
			
	}

	//calculate the monthly payment
	public void calculateMP(double capital,double APR,int NoOfMonths) {
		try {
			capital = Double.parseDouble(textFieldCapital.getText());
			NoOfMonths = Integer.parseInt(textFieldNoOfMonths.getText());
			APR = Double.parseDouble(textFieldAPR.getText());
			double r = APR / 100 / 12;
			double a = capital * r / ( 1 - Math.pow(1+r, -NoOfMonths));
			String result = roundResult(a);
			textFieldMP.setText(result);
			}
			catch(Exception ex2) {
				JOptionPane.showMessageDialog(null, "Enter valid value!");
			}
	}
	
	//calculate capital
	public void calculateCapital(double APR,int NoOfMonths,double monthlyPayment) {
		try {
			NoOfMonths = Integer.parseInt(textFieldNoOfMonths.getText());
			APR = Double.parseDouble(textFieldAPR.getText());
			monthlyPayment = Double.parseDouble(textFieldMP.getText());
			double r = APR / 100 / 12;
			double a = monthlyPayment*(Math.pow(1+r,NoOfMonths) - 1);
			double b = a /(r* Math.pow(1+r, NoOfMonths));
			String result = roundResult(b);
			textFieldCapital.setText(result);
			}
			catch(Exception ex2) {
				JOptionPane.showMessageDialog(null, "Enter valid value!");
			}
	}
	
	//calculate APR
	public void calculateAPR(double capital,int NoOfMonths,double monthlyPayment) {
		try {
			capital = Double.parseDouble(textFieldCapital.getText());
			NoOfMonths = Integer.parseInt(textFieldNoOfMonths.getText());
			monthlyPayment = Double.parseDouble(textFieldMP.getText());
			
			double a = ((monthlyPayment * NoOfMonths / capital) - 1) / NoOfMonths;
			double apr = a*100;
			String result = roundResult(apr);
			textFieldAPR.setText(result);
		}
		catch(Exception ex3) {
				JOptionPane.showMessageDialog(null, "Enter valid value!");
		}
	}
	
	//calculate NoOfMonths
	public void calculateNoOfMonths(double capital,double APR, double monthlyPayment) {
		try {
			capital = Double.parseDouble(textFieldCapital.getText());
			APR = Double.parseDouble(textFieldAPR.getText());
			monthlyPayment = Double.parseDouble(textFieldMP.getText());
			double r = APR / 100 / 12;
			double a = ((monthlyPayment * 12 * NoOfMonths / capital) - 1) /r;
			String result = roundResult(a);
			textFieldAPR.setText(result);
			
		}
		catch(Exception ex4) {
				JOptionPane.showMessageDialog(null, "Enter valid value!");
		}
	}
	
	//find MP with APR = 0
	public void APRIs0(double capital, int NoOfMonths) {
		try {
		capital = Double.parseDouble(textFieldCapital.getText());
		NoOfMonths = Integer.parseInt(textFieldNoOfMonths.getText());
		textFieldMP.setText(Double.toString((capital / NoOfMonths) + 0.01));
		}
		catch(Exception ex5) {
			JOptionPane.showMessageDialog(null, "Enter valid value!");
	}
	}
	
	//round the results 
	public String roundResult(double d) {
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
		return df.format(d);
	}

	private class bEnabling implements DocumentListener{
		private ButtonModel buttonModel;
		private ArrayList<Document> documents = new ArrayList<Document>();
		public bEnabling (ButtonModel buttonModel) {
			this.buttonModel = buttonModel;
		}
		public void addDocument(Document document) {
			document.addDocumentListener(this);
			this.documents.add(document);
			documentChanged();
		}
		public void documentChanged() {
			boolean buttonEnabled = false;
			for(Document document: documents) {
				if(document.getLength() > 0) {
					buttonEnabled = true;
					break;
				}
				} 
			buttonModel.setEnabled(buttonEnabled);
		}
		@Override
		public void insertUpdate(DocumentEvent e) {
			documentChanged();
			
		}
		@Override
		public void removeUpdate(DocumentEvent e) {
			documentChanged();
			
		}
		@Override
		public void changedUpdate(DocumentEvent e) {
			documentChanged();
			
		}
	}
	
}
