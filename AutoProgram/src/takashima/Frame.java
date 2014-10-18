package takashima;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Button;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JFileChooser;

import java.io.File;
import java.awt.TextField;
import java.awt.Color;
import javax.swing.JTextField;


public class Frame extends JFrame {

	private JPanel contentPane;
	private final Action action = new SwingAction();
	private JTextField textField;
	private final Action action_1 = new SwingAction_1();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
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
	public Frame() {
		setTitle("ぱずるずる");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setToolTipText("");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ファイルを選択してください、");
		lblNewLabel.setBounds(6, 6, 201, 16);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("参照");
		btnNewButton.setAction(action);
		btnNewButton.setBounds(327, 27, 117, 29);
		contentPane.add(btnNewButton);
		
		textField = new JTextField("");
		textField.setBounds(6, 26, 309, 28);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton button = new JButton("並べ替え開始");
		button.setAction(action_1);
		button.setBounds(6, 54, 117, 29);
		contentPane.add(button);
	}
	
	//参照ボタンが押されたとき
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "参照");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			JFileChooser filechooser = new JFileChooser();	//ファイル選択用のクラス
			
			int selected = filechooser.showOpenDialog(null);
			if(selected == JFileChooser.APPROVE_OPTION){
				File file = filechooser.getSelectedFile();
				textField.setText(file.getPath());
			}
		}
	}
	
	//実行ボタンが押されたとき
	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "並べ替え開始");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
