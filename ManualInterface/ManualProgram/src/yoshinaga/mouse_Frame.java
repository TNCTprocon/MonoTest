package yoshinaga;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class mouse_Frame extends JFrame {
	
	int x,y;
	JLabel x_label,y_label;
	
	public mouse_Frame(String title,int x_size,int y_size){
		 setSize(x_size,y_size);
		 setTitle(title);
		 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		 Font font = new Font("MS ゴッシク",Font.PLAIN,20);
		 
		 x_label = new JLabel();
		 x_label.setFont(font);
		 x_label.setText("X座標:0");
		 
		 y_label = new JLabel();
		 y_label.setFont(font);
		 y_label.setText("Y座標:0");
		 
		 JPanel cp = new JPanel();
		 
		 cp.add(x_label);
		 cp.add(y_label);
		 
		 setContentPane(cp);
		 
		 cp.setLayout(null);
		 
		 x_label.setBounds(5,5,300,24);
		 y_label.setBounds(5,55,300,24);
		 
		 mouse_class mouse = new mouse_class();
		 addMouseMotionListener(mouse);
	}
	
public class mouse_class implements MouseListener,MouseMotionListener,MouseWheelListener{
	//マウスがクリックされたとき
	public void mouseClicked(MouseEvent event){
		x = event.getX();
		y = event.getY();
		
		x_label.setText("X座標:" + x);
		y_label.setText("Y座標:" + y);
	}
	
	//マウスが画面内に入ったとき
	public void mouseEntered(MouseEvent e){
		
	}
	
	//マウスが画面外に出たとき
	public void mouseExited(MouseEvent e){
		
	}
	
	//マウスのボタンが押されたとき
	public void mousePressed(MouseEvent event){
		
	}
	
	//マウスのボタンが離されたとき
	public void mouseReleased(MouseEvent event){
		
	}
	
	//マウスがドラッグされたとき
	public void mouseDragged(MouseEvent event){
		
	}
	
	//マウスが動かされたとき
	public void mouseMoved(MouseEvent event){
		
	}
	
	//マウスのホイールが回転したとき
	public void mouseWheelMoved(MouseWheelEvent event){
		
	}
}

}