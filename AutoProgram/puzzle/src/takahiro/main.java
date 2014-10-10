package takahiro;

import java.awt.image.BufferedImage;
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
			
			int cnt = 0;
			boolean wflag = false;
			boolean hflag = false;
			while(true){
				if(cnt < 5){
					while(fis.read() != 0x20);
				}else if((cnt == 5) && !wflag){
					while(fis.read() != 0x20);
				}else if((cnt > 6) && !hflag){
					while(fis.read() != 0x20);
				}
				
				switch(cnt){
				case 0:
					fis.read(spritrow);
					break;
				case 1:
					fis.read(spritline);
					break;
				case 2:
					fis.read(selectnumber);
					break;
				case 3:
					fis.read(selectcost);
					break;
				case 4:
					for(int i = 0; i < 4; i++){
						if((char)(width[i] = (byte)fis.read()) == ' '){
							wflag = true;
							break;
						}
					}
					break;
				case 5:
					for(int i = 0; i < 4; i++){
						if((char)(heigth[i] = (byte)fis.read()) == ' '){
							hflag = true;
							break;
						}
					}
					break;
				}
				cnt++;
				if(cnt > 5)
					break;
			}
			fis.read();
			fis.read(light);
			fis.read();
			String strwidth = String.valueOf((char)width[0]) + String.valueOf((char)width[1]) + String.valueOf((char)width[2]);
			
			//BufferedImage bufimg = new BufferedImage((int)width, (int)heigth, BufferedImage.TYPE_INT_BGR);
			//while()
			
			System.out.println(strwidth);
			System.out.println(Integer.toHexString((type[0])));
			System.out.println(Integer.toHexString((width[0])));
			System.out.println(Integer.toHexString((width[1])));
			System.out.println(Integer.toHexString((width[2])));
			
			
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
