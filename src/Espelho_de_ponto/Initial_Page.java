package Espelho_de_ponto;

import java.awt.EventQueue;

import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Iterator;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import static java.time.temporal.ChronoUnit.*;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.CompoundBorder;

public class Initial_Page extends JFrame {

	private JPanel contentPane;
	private JTextField TxtPath;
	private JTable grid;
	private JComboBox sheetCombo;
	private JRadioButton rdbtnMedico;
	private JRadioButton rdbtnACS;
	private JRadioButton rdbtnOthersPositions;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Initial_Page frame = new Initial_Page();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Initial_Page() {
		setTitle("Cálculo de Horas Extras UBS");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Initial_Page.class.getResource("/Images/health-insurance (1).png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1174, 730);
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Espelho de Ponto UBS");
		lblNewLabel.setBounds(417, 23, 323, 36);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Caminho do arquivo Excel:");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 15));
		lblNewLabel_1.setBounds(10, 85, 199, 36);
		contentPane.add(lblNewLabel_1);
		
		TxtPath = new JTextField();
		TxtPath.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		TxtPath.setBounds(235, 89, 896, 29);
		contentPane.add(TxtPath);
		TxtPath.setColumns(10);
		
		JButton btnFindExcel = new JButton("Procurar arquivo Excel");
		btnFindExcel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnFindExcel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String[] SheetNames = new String[99];
		        String[][] Table = new String[99][99];
		        
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(null);
		        if (result == JFileChooser.APPROVE_OPTION) {
		            File selectedDirectory = fileChooser.getSelectedFile();
		            TxtPath.setText(selectedDirectory.getAbsolutePath());
		        }
		        
				
				//Instance of ExcelReader class
				ExcelReader excel = new ExcelReader();
				
				//Use the ExcelReader class function ReadSheet to get all sheets and add to jcombobox
				SheetNames = excel.ReadSheet(TxtPath.getText());
				sheetCombo.setModel(new DefaultComboBoxModel(SheetNames));
				
				//Use the ExcelReader class function ReadExcel to get all data from the sheet
				Table = excel.ReadExcel(TxtPath.getText(), 0);
				String[] columnNames = {"Column1","Column2","Column3","Column4","Column5","Column6","Column7","Column8","Column9","Column10"};
									
				DefaultTableModel model = new DefaultTableModel(Table, columnNames);
				grid.setModel(model);
			}
		});
		btnFindExcel.setFont(new Font("Arial", Font.BOLD, 13));
		btnFindExcel.setBounds(10, 132, 189, 29);
		contentPane.add(btnFindExcel);
		
		
		grid = new JTable();
		grid.setBorder(new LineBorder(new Color(0, 0, 0)));
		grid.setBounds(235, 185, 896, 480);
		contentPane.add(grid);
		
		sheetCombo = new JComboBox();
		sheetCombo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		sheetCombo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String[][] Table = new String[99][99];
				
				//Instance of ExcelReader class
				ExcelReader excel = new ExcelReader();
				
				//Use the ExcelReader class function ReadExcel to get all data from the sheet
				Table = excel.ReadExcel(TxtPath.getText(), sheetCombo.getSelectedIndex());
				String[] columnNames = {"Column1","Column2","Column3","Column4","Column5","Column6","Column7","Column8","Column9","Column10"};
									
				DefaultTableModel model = new DefaultTableModel(Table, columnNames);
				grid.setModel(model);
			}
		});
		sheetCombo.setBounds(10, 185, 189, 22);
		contentPane.add(sheetCombo);
		
		final JRadioButton rdbtnMedico = new JRadioButton("Médico");
		rdbtnMedico.setBackground(Color.PINK);
		rdbtnMedico.setBounds(437, 135, 94, 23);
		contentPane.add(rdbtnMedico);
		
		final JRadioButton rdbtnACS = new JRadioButton("ACS");
		rdbtnACS.setBackground(Color.PINK);
		rdbtnACS.setBounds(545, 135, 59, 23);
		contentPane.add(rdbtnACS);
		
		final JRadioButton rdbtnOthersPositions = new JRadioButton("Demais Cargos");
		rdbtnOthersPositions.setBackground(Color.PINK);
		rdbtnOthersPositions.setSelected(true);
		rdbtnOthersPositions.setBounds(634, 135, 189, 23);
		contentPane.add(rdbtnOthersPositions);
		
		 ButtonGroup myButtonGroup = new ButtonGroup();
		 myButtonGroup.add(rdbtnMedico);
		 myButtonGroup.add(rdbtnACS);
		 myButtonGroup.add(rdbtnOthersPositions);
		
		JButton btnCalc = new JButton("Calcular Horas Extras");
		btnCalc.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnCalc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String CUSTOM_PATTERN = "dd/MM/yyyy";
				DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(CUSTOM_PATTERN);
				LocalDate date = null;
				Integer line = 0, columnBegin = 0, columnEnd = 0;
				long minutesOvertime = 0, hours, minutes;
				String hoursFormatted, minutesFormatted;
				
				//get the interval of lines that have valid date
				for (int i = 1; i <= 31; i++) {
					try {
					  date = LocalDate.parse(grid.getValueAt(i, 1).toString().substring(03, 13) , DATE_TIME_FORMATTER);
					  line = i;
					} catch (DateTimeParseException f) {
					  f.printStackTrace();
					  break;
					}
				}
				
				//calculate overtime based in each position
				if (rdbtnOthersPositions.isSelected()) {
					columnBegin = 2;
					columnEnd = 5;
				} else if (rdbtnMedico.isSelected()) {
					columnBegin = 2;
					columnEnd = 3;
				} else if (rdbtnACS.isSelected()) {
					columnBegin = 2;
					columnEnd = 3;
				} 
				
				
				for (int i = 2; i <= line; i++) {
					//if the cell of begin of day is empty go to next line grid
					if (grid.getValueAt(i, columnBegin).toString().isEmpty() || grid.getValueAt(i, columnEnd).toString().isEmpty()) {
						continue;
					} else {
						try { 
							LocalTime beginOfDay = LocalTime.parse( grid.getValueAt(i, columnBegin).toString());
							LocalTime endOfDay = LocalTime.parse( grid.getValueAt(i, columnEnd).toString());
							if (beginOfDay.until(endOfDay, MINUTES) - 540 > 0)
								minutesOvertime += beginOfDay.until(endOfDay, MINUTES) - 540;
						} catch(Exception except){
							continue;
						}
						
					}
				}
				
				hours = minutesOvertime / 60;
				minutes = minutesOvertime % 60;
				if (hours < 10)
					hoursFormatted = String.format("%02d", hours);
				else 
					hoursFormatted = Long.toString(hours);
				
				if (minutes < 10)
					minutesFormatted = String.format("%02d", minutes);
				else 
					minutesFormatted = Long.toString(minutes);
				
				JOptionPane.showMessageDialog(null, "Minutos extras: " + minutesOvertime + "\n" + "Horas extras: " + hoursFormatted + ":" + minutesFormatted);
			}
		});
		btnCalc.setFont(new Font("Arial", Font.BOLD, 13));
		btnCalc.setBounds(235, 132, 189, 29);
		contentPane.add(btnCalc);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Initial_Page.class.getResource("/Images/health-insurance (1).png")));
		lblNewLabel_2.setBounds(71, 4, 77, 74);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblCreatedBy = new JLabel("Criado Por: Mateus Victorio");
		lblCreatedBy.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblCreatedBy.setBounds(10, 617, 189, 14);
		contentPane.add(lblCreatedBy);
		
		JLabel lblCreatedDate = new JLabel("Data: 04/09/2024");
		lblCreatedDate.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblCreatedDate.setBounds(10, 633, 189, 14);
		contentPane.add(lblCreatedDate);
		
		JLabel lblSys_Version = new JLabel("Versão: 1.0.0");
		lblSys_Version.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblSys_Version.setBounds(10, 651, 189, 14);
		contentPane.add(lblSys_Version);
		
		
	}
}
