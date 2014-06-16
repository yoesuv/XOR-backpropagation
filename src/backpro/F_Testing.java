package backpro;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

public class F_Testing {

	public JFrame frameTesting;
	private JLabel lblInputSatu,lblInputDua;
	private JTextField text_input1,text_input2;
	private JButton btnProses,btnBack;
	private JTextArea textArea_Result;
	
	private double x1,x2;
	private double[] networkOutput;
	private String hasil;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					F_Testing window = new F_Testing();
					window.frameTesting.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public F_Testing() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameTesting = new JFrame();
		frameTesting.setTitle("Testing");
		frameTesting.setResizable(false);
		frameTesting.setBounds(100, 100, 296, 348);
		frameTesting.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameTesting.getContentPane().setLayout(null);
		
		JPanel panelInput = new JPanel();
		panelInput.setBorder(new TitledBorder(null, "Input", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelInput.setBounds(10, 11, 266, 103);
		frameTesting.getContentPane().add(panelInput);
		panelInput.setLayout(null);
		
		lblInputSatu = new JLabel("Input 1");
		lblInputSatu.setBounds(15, 27, 63, 14);
		panelInput.add(lblInputSatu);
		
		text_input1 = new JTextField();
		text_input1.setBounds(102, 22, 114, 25);
		panelInput.add(text_input1);
		text_input1.setColumns(10);
		
		text_input2 = new JTextField();
		text_input2.setColumns(10);
		text_input2.setBounds(102, 58, 114, 25);
		panelInput.add(text_input2);
		
		lblInputDua = new JLabel("Input 2");
		lblInputDua.setBounds(15, 63, 63, 14);
		panelInput.add(lblInputDua);
		
		btnProses = new JButton("PROSES");
		btnProses.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					textArea_Result.setText("");
					
					x1 = Double.parseDouble(text_input1.getText());
					x2 = Double.parseDouble(text_input2.getText());
					hasil = "";
					
					@SuppressWarnings("deprecation")
					NeuralNetwork<?> nn = NeuralNetwork.load("XOR.nnet");
					DataSet ds = new DataSet(2);
					ds.addRow(new DataSetRow(x1,x2));
					
					nn.setInput(x1,x2);
					nn.calculate();
					
					networkOutput = nn.getOutput();
					textArea_Result.setText(" Result : "+Arrays.toString(networkOutput));
					HitungOutput(Double.parseDouble(Arrays.toString(networkOutput).substring(1, 4)));
					textArea_Result.setText(textArea_Result.getText()+"\n Jadi Hasilnya adalah "+hasil);
					
				}catch(Exception err){
					System.out.println(err.getMessage());
					JOptionPane.showMessageDialog(frameTesting, "Terjadi Kesalahan","ERROR",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnProses.setBounds(40, 120, 95, 32);
		frameTesting.getContentPane().add(btnProses);
		
		btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				F_Main fm = new F_Main();
				fm.frameMain.setVisible(true);
				frameTesting.dispose();
			}
		});
		btnBack.setBounds(145, 120, 95, 32);
		frameTesting.getContentPane().add(btnBack);
		
		textArea_Result = new JTextArea();
		textArea_Result.setBounds(10, 162, 270, 136);
		frameTesting.getContentPane().add(textArea_Result);
	}
	
	private void HitungOutput(double d){
		//System.out.println("Input : "+d);
		if(d>=0.5){
			hasil = "Hasil 1";
		}else{
			hasil = "Hasil 0";
		}
	}
}
