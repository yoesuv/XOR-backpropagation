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
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextArea;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;

public class F_Training {

	public JFrame frameTraining;
	private JTextField text_learnRate,text_max,text_targeterr;
	private JLabel lblLarningRate,lblMaxEpoh,lblTargetError,lblJumlahHiddenLayer;
	private JTextArea textArea_Result;
	private JComboBox<String> comboBoxLayer;
	private JButton btnTrain,btnBack;
	
	private double LRate,targetErr,err;
	private int hl,MaxEpoh,epoh;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					F_Training window = new F_Training();
					window.frameTraining.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public F_Training() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameTraining = new JFrame();
		frameTraining.setTitle("Training");
		frameTraining.setResizable(false);
		frameTraining.setBounds(100, 100, 439, 327);
		frameTraining.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameTraining.getContentPane().setLayout(null);
		
		JPanel panelParameter = new JPanel();
		panelParameter.setBorder(new TitledBorder(null, "Parameter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelParameter.setBounds(10, 11, 263, 124);
		frameTraining.getContentPane().add(panelParameter);
		panelParameter.setLayout(null);
		
		lblLarningRate = new JLabel("Larning Rate");
		lblLarningRate.setBounds(10, 22, 92, 14);
		panelParameter.add(lblLarningRate);
		
		lblMaxEpoh = new JLabel("Max Epoh");
		lblMaxEpoh.setBounds(10, 44, 92, 14);
		panelParameter.add(lblMaxEpoh);
		
		lblTargetError = new JLabel("Target Error");
		lblTargetError.setBounds(10, 66, 92, 14);
		panelParameter.add(lblTargetError);
		
		text_learnRate = new JTextField();
		text_learnRate.setBounds(130, 19, 113, 20);
		panelParameter.add(text_learnRate);
		text_learnRate.setColumns(10);
		
		text_max = new JTextField();
		text_max.setColumns(10);
		text_max.setBounds(130, 41, 113, 20);
		panelParameter.add(text_max);
		
		text_targeterr = new JTextField();
		text_targeterr.setColumns(10);
		text_targeterr.setBounds(130, 63, 113, 20);
		panelParameter.add(text_targeterr);
		
		comboBoxLayer = new JComboBox<String>();
		comboBoxLayer.addItem("2");
		comboBoxLayer.addItem("3");
		comboBoxLayer.addItem("4");
		comboBoxLayer.addItem("5");
		comboBoxLayer.addItem("6");
		comboBoxLayer.setBounds(130, 85, 113, 20);
		panelParameter.add(comboBoxLayer);
		
		lblJumlahHiddenLayer = new JLabel("Jumlah Hiden Layer");
		lblJumlahHiddenLayer.setBounds(10, 88, 110, 14);
		panelParameter.add(lblJumlahHiddenLayer);
		
		btnTrain = new JButton("TRAINING");
		btnTrain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					// Proses Trainning
					LRate = Double.parseDouble(text_learnRate.getText());
					MaxEpoh = Integer.parseInt(text_max.getText());
					targetErr = Double.parseDouble(text_targeterr.getText());
					hl = Integer.parseInt(comboBoxLayer.getSelectedItem().toString());
					textArea_Result.setText("");
					//dataset dengan 2 input dan 1 output
					DataSet trainingSet = new DataSet(2, 1);
					
					//isi dataset dengan XOR				
					trainingSet.addRow(new DataSetRow(new double[]{0,0}, new double[]{0}));
					trainingSet.addRow(new DataSetRow(new double[]{1,0}, new double[]{1}));
					trainingSet.addRow(new DataSetRow(new double[]{0,1}, new double[]{1}));
					trainingSet.addRow(new DataSetRow(new double[]{1,1}, new double[]{0}));
					
					//Jaringan multilayer dengan 2 input, hidden layer yang disesuaikan, 1 output
					// dengan fungsi aktifasi sigmoid
					MultiLayerPerceptron mp = new MultiLayerPerceptron(TransferFunctionType.SIGMOID,2,hl,1);
					
					//melihat proses training
					LearningEventListener l = new LearningEventListener() {
						@Override
						public void handleLearningEvent(LearningEvent le) {
							BackPropagation bp = (BackPropagation) le.getSource();
							epoh = bp.getCurrentIteration();
							err = bp.getTotalNetworkError();
							System.out.println("Iterasi ke : "+bp.getCurrentIteration()+" Target Error : "+bp.getTotalNetworkError());
						}
					};
					
					//set parameter learning rate, max epoh dan target error
					MomentumBackpropagation mBack = (MomentumBackpropagation) mp.getLearningRule();
					mBack.setLearningRate(LRate);
					mBack.setMaxIterations(MaxEpoh);
					mBack.setMaxError(targetErr);
					mBack.addListener(l);
					
					mp.learn(trainingSet);
					mp.save("XOR.nnet");
					
					textArea_Result.setText("Selesai dengan "+epoh+" Iterasi\nTarget Error : "+err+"\n");
					HasilTraining(mp, trainingSet);
					
					JOptionPane.showMessageDialog(frameTraining, "Training Selesai","DONE",JOptionPane.INFORMATION_MESSAGE);
				}catch(Exception err){
					JOptionPane.showMessageDialog(frameTraining, "Terjadi Kesalahan","ERROR",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnTrain.setBounds(283, 21, 123, 47);
		frameTraining.getContentPane().add(btnTrain);
		
		textArea_Result = new JTextArea();
		textArea_Result.setBounds(10, 146, 405, 136);
		frameTraining.getContentPane().add(textArea_Result);
		
		btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				F_Main fm = new F_Main();
				fm.frameMain.setVisible(true);
				frameTraining.dispose();
			}
		});
		btnBack.setBounds(283, 79, 123, 47);
		frameTraining.getContentPane().add(btnBack);
		
		setDefault();
	}
	
	private void setDefault(){
		text_learnRate.setText("0.01");
		text_max.setText("100000");
		text_targeterr.setText("0.01");
		comboBoxLayer.setSelectedIndex(2);
	}
	
	private void HasilTraining(NeuralNetwork<?> nnet,DataSet dset){
		double[] networkOutput;
		for(DataSetRow ds:dset.getRows()){
			nnet.setInput(ds.getInput());
			nnet.calculate();
			networkOutput = nnet.getOutput();
			textArea_Result.setText(textArea_Result.getText()+"\nResult : "+Arrays.toString(networkOutput));
		}
	}
}
