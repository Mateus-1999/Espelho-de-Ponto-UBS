package Espelho_de_ponto;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

public class CustomFileDialog {

	public static File showDialog(JFrame parent) {

	    JDialog dialog = new JDialog(parent, "Selecione o arquivo Excel", true);

	    dialog.setLayout(new BorderLayout(10, 10));

	    String downloadsPath = System.getProperty("user.home")
	            + File.separator
	            + "Downloads";

	    File downloadsFolder = new File(downloadsPath);

	    // Get XLS/XLSX files
	    File[] files = downloadsFolder.listFiles((dir, name) ->
	            name.toLowerCase().endsWith(".xlsx")
	            || name.toLowerCase().endsWith(".xls")
	    );

	    // Sort newest first
	    Arrays.sort(files, (f1, f2) ->
	            Long.compare(f2.lastModified(), f1.lastModified())
	    );

	    // Table model
	    DefaultTableModel model = new DefaultTableModel();

	    model.addColumn("Nome do Arquivo");
	    model.addColumn("Data de modificação");
	    model.addColumn("Tamanho do arquivo (KB)");

	    SimpleDateFormat sdf =
	            new SimpleDateFormat("dd/MM/yyyy HH:mm");

	    for (File file : files) {

	        model.addRow(new Object[] {
	                file.getName(),
	                sdf.format(file.lastModified()),
	                file.length() / 1024
	        });
	    }

	    JTable table = new JTable(model) {

	        @Override
	        public boolean isCellEditable(int row, int column) {

	            return false;
	        }
	    };

	    // STYLE TABLE
	    table.setRowHeight(28);
	    table.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
	    table.getTableHeader().setFont(
	            new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14)
	    );

	    table.getTableHeader().setBackground(
	            new java.awt.Color(45, 45, 45)
	    );

	    table.getTableHeader().setForeground(
	            java.awt.Color.WHITE
	    );

	    table.setSelectionBackground(
	            new java.awt.Color(0, 120, 215)
	    );

	    table.setSelectionForeground(java.awt.Color.WHITE);

	    // Column sizes
	    table.getColumnModel().getColumn(0).setPreferredWidth(350);
	    table.getColumnModel().getColumn(1).setPreferredWidth(180);
	    table.getColumnModel().getColumn(2).setPreferredWidth(100);

	    JScrollPane scrollPane = new JScrollPane(table);

	    // OPEN BUTTON
	    JButton btnOpen = new JButton("Open");

	    btnOpen.setFocusPainted(false);

	    btnOpen.setFont(
	            new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14)
	    );

	    btnOpen.setPreferredSize(new Dimension(120, 40));

	    final File[] selectedFile = new File[1];

	    btnOpen.addActionListener(ae -> {

	        int row = table.getSelectedRow();

	        if (row >= 0) {

	            selectedFile[0] = files[row];

	            dialog.dispose();
	        }
	    });

	    // DOUBLE CLICK SUPPORT
	    table.addMouseListener(new MouseAdapter() {

	        @Override
	        public void mouseClicked(MouseEvent e) {

	            if (e.getClickCount() == 2) {

	                btnOpen.doClick();
	            }
	        }
	    });

	    // TITLE PANEL
	    javax.swing.JLabel title =
	            new javax.swing.JLabel(" Arquivos Excel Recentes");

	    title.setFont(
	            new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18)
	    );

	    title.setBorder(
	            javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)
	    );

	    // BOTTOM PANEL
	    javax.swing.JPanel bottomPanel =
	            new javax.swing.JPanel();

	    bottomPanel.add(btnOpen);

	    dialog.add(title, BorderLayout.NORTH);
	    dialog.add(scrollPane, BorderLayout.CENTER);
	    dialog.add(bottomPanel, BorderLayout.SOUTH);

	    dialog.setSize(new Dimension(930, 500));

	    dialog.setLocationRelativeTo(parent);

	    dialog.setVisible(true);

	    return selectedFile[0];
	}
}
