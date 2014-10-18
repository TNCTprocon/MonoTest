package takashima;

import javax.swing.*;
import java.awt.*;

public class Window {
	public static void main(String[] args){
		JFrame mainFrame = new JFrame("ぱずるずる");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(320, 160);
		mainFrame.setLocationRelativeTo(null);
		//JFrameよりContentPaneを取得
		Container contentPane = mainFrame.getContentPane();
		//ラベルのインスタンスを生成
		JLabel label = new JLabel("SwingLabel");
		//ボタンのインスタンスを生成
		JButton button = new JButton("ファイル選択");
		//ラベルをContentPaneに配置
		contentPane.add(label, BorderLayout.CENTER);
		//ボタンをContentPaneに配置
		contentPane.add(button, BorderLayout.NORTH);
		//button.addActionListener(this);
		
		mainFrame.setVisible(true);
	}
}
