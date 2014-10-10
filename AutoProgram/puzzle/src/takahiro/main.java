package takahiro;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

public class main {
	public static void main(String[] args){
		try{
			String filename = "prob01.ppm";
			byte[] type = new byte[2];
			byte[] spritline = new byte[2];
			byte[] spritrow = new byte[2];
			byte[] selectnumber = new byte[3];
			byte[] selectcost = new byte[3];
			byte[] changecost = new byte[3];
			byte[] heigth = new byte[4];
			byte[] width = new byte[4];
			byte[] light = new byte[3];
			
			BufferedInputStream fis = null;
			File file = new File("/Users/takahiro/Git/AutoProgram/puzzle/src/takahiro/picture/" + filename);
			fis = new BufferedInputStream(new FileInputStream(file));
			
			fis.read(type);
			
			while((char)fis.read() != ' ');
			
			
			String sprit, select, cost, pix;
			
			for(int i = 0; i < 5; i++){
				if(i == 0){
					//while(fileinput.read() != );
				}
			}
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
