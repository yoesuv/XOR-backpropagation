package backpro;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

public class F_Main {

	public JFrame frameMain;
	private DefaultTableModel tbmodel;
	private JTable tableXOR;
	private JButton btnTrain,btnTesting;
	
	private String kolom[] = {"X1","X2","Target"};
	private String isi[][] = {{"0","0","0"},{"1","0","1"},{"0","1","1"},{"1","1","0"}};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					F_Main window = new F_Main();
					window.frameMain.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public F_Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameMain = new JFrame();
		frameMain.setTitle("Backpropagation XOR");
		frameMain.setResizable(false);
		frameMain.setBounds(100, 100, 500, 225);
		frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameMain.getContentPane().setLayout(null);
		
		btnTrain = new JButton("TRAINING");
		btnTrain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				F_Training ft = new F_Training();
				ft.frameTraining.setVisible(true);
				frameMain.dispose();
			}
		});
		btnTrain.setBounds(10, 45, 113, 40);
		frameMain.getContentPane().add(btnTrain);
		
		btnTesting = new JButton("TESTING");
		btnTesting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				F_Testing ft = new F_Testing();
				ft.frameTesting.setVisible(true);
				frameMain.dispose();
			}
		});
		btnTesting.setBounds(10, 104, 113, 40);
		frameMain.getContentPane().add(btnTesting);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(142, 24, 331, 146);
		frameMain.getContentPane().add(scrollPane);
		
		tbmodel = new DefaultTableModel(isi, kolom);
		
		tableXOR = new JTable();
		tableXOR.setModel(tbmodel);
		scrollPane.setViewportView(tableXOR);
	}
}
