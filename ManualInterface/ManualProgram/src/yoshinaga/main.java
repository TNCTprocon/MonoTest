package yoshinaga;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import org.apache.commons.lang.SerializationUtils;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.imageio.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class main {
	
	public static ArrayList<Picture> pictArray = null;
	public static Picture[] pict;
	public static int spritline, spritrow, selectnumber, selectcost, changecost, 
	heigth, width, light;
	
	public static void main(String[] args){
		try{
			String filename = "prob01.ppm";
//			Picture[] pict = null;
			byte[] btype = new byte[2];
			byte[] bspritline = new byte[2];
			byte[] bspritrow = new byte[2];
			byte[] bselectnumber = new byte[3];
			byte[] bselectcost = new byte[3];
			byte[] bchangecost = new byte[3];
			byte[] bheigth = new byte[4];
			byte[] bwidth = new byte[4];
			byte[] blight = new byte[3];
			byte[] three = new byte[3];
			byte[] twe = new byte[2];
			byte[] tmp = new byte[1];
			String type, sspritline, sspritrow, sselectnumber, sselectcost, 
					schangecost, sheigth, swidth, slight;
			
			byte[] brgb = new byte[3];
			
			
			BufferedInputStream fis = null;
			File file = new File("/Users/hayato/workspaceprocpn1/AutoProgram/src/takashima/" + filename);
			fis = new BufferedInputStream(new FileInputStream(file));
			
			//タイプの取得
			fis.read(btype);
			type = String.valueOf((char)btype[0]) + String.valueOf((char)btype[1]);
			
			fis.read(three);
			fis.read(bspritrow);
			
			//分割の列数の取得
			if(bspritrow[1] == 0x20){
				bspritrow[1] = 0;
				fis.read(bspritline);
				sspritrow = String.valueOf((char)bspritrow[0]);
			}else{
				fis.read();
				fis.read(bspritline);
				sspritrow = String.valueOf((char)bspritrow[0]) + String.valueOf((char)bspritrow[1]);
			}
			System.out.println("Spritrow = " + sspritrow);
			
			//分割の行数の取得
			if(bspritline[1] == 0x0A){
				bspritline[1] = 0;
				fis.read(twe);
				fis.read(bselectnumber);
				sspritline = String.valueOf((char)bspritline[0]);
			}else{
				fis.read(three);
				fis.read(bselectnumber);
				sspritline = String.valueOf((char)bspritline[0]) + String.valueOf((char)bspritline[1]);
			}
			System.out.println("spritline = " + sspritline);
			
			//選択回数の取得
			if(bselectnumber[1] == 0x0A){
				bselectnumber[1] = 0;
				bselectnumber[2] = 0;
				fis.read();
				fis.read(bselectcost);
				sselectnumber = String.valueOf((char)bselectnumber[0]);
			}else if(bselectnumber[2] == 0x0A){
				bselectnumber[2] = 0;
				fis.read(twe);
				fis.read(bselectcost);
				sselectnumber = String.valueOf((char)bselectnumber[0]) + String.valueOf((char)bselectnumber[1]);
			}else{
				fis.read(three);
				fis.read(bselectcost);
				sselectnumber = String.valueOf((char)bselectnumber[0]) + String.valueOf((char)bselectnumber[1]) + String.valueOf((char)bselectnumber[2]);
			}
			System.out.println("selectnumber = " + sselectnumber);
			
			//コストの取得
			if(bselectcost[1] == 0x20){
				bselectcost[1] = 0;
				bchangecost[0] = bselectcost[2];
				bselectcost[2] = 0;
				fis.read(tmp);
				if(tmp[0] == 0x0A){
					fis.read(bwidth);
					schangecost = String.valueOf((char)bchangecost[0]);
				}else{
					bchangecost[1] = tmp[0];
					fis.read(tmp);
					if(tmp[0] == 0x0A){
						fis.read(bwidth);
						schangecost = String.valueOf((char)bchangecost[0]) + String.valueOf((char)bchangecost[1]);
					}else{
						bchangecost[2] = tmp[0];
						fis.read();
						fis.read(bwidth);
						schangecost = String.valueOf((char)bchangecost[0]) + String.valueOf((char)bchangecost[1]) + String.valueOf((char)bchangecost[2]);
					}
				}
				sselectcost = String.valueOf((char)bselectcost[0]);
			}else if(bselectcost[2] == 0x20){
				bselectcost[2] = 0;
				fis.read(tmp);
				bchangecost[0] = tmp[0];
				fis.read(tmp);
				if(tmp[0] == 0x0A){
					fis.read(bwidth);
					schangecost = String.valueOf((char)bchangecost[0]);
				}else{
					bchangecost[1] = tmp[0];
					fis.read(tmp);
					if(tmp[0] == 0x0A){
						fis.read(bwidth);
						schangecost = String.valueOf((char)bchangecost[0]) + String.valueOf((char)bchangecost[1]);
					}else{
						bchangecost[2] = tmp[0];
						fis.read();
						fis.read(bwidth);
						schangecost = String.valueOf((char)bchangecost[0]) + String.valueOf((char)bchangecost[1]) + String.valueOf((char)bchangecost[2]);
					}
				}
				sselectcost = String.valueOf((char)bselectcost[0]) + String.valueOf((char)bselectcost[1]);
			}else{
				fis.read();
				fis.read(tmp);
				bchangecost[0] = tmp[0];
				fis.read(tmp);
				if(tmp[0] == 0x0A){
					fis.read(bwidth);
					schangecost = String.valueOf((char)bchangecost[0]);
				}else{
					bchangecost[1] = tmp[0];
					fis.read(tmp);
					if(tmp[0] == 0x0A){
						fis.read(bwidth);
						schangecost = String.valueOf((char)bchangecost[0]) + String.valueOf((char)bchangecost[1]);
					}else{
						bchangecost[2] = tmp[0];
						fis.read();
						fis.read(bwidth);
						schangecost = String.valueOf((char)bchangecost[0]) + String.valueOf((char)bchangecost[1]) + String.valueOf((char)bchangecost[2]);
					}
				}
				sselectcost = String.valueOf((char)bselectcost[0]) + String.valueOf((char)bselectcost[1]) + String.valueOf((char)bselectcost[2]);
			}
			System.out.println("selectcost = " + sselectcost + "\nchangecost = " + schangecost);
			
			//画像サイズ,最大輝度の取得
			 if(bwidth[1] == 0x20){
				 swidth = String.valueOf((char)bwidth[0]);
				 
				 bheigth[0] = bwidth[2];
				 if(bwidth[3] != 0x0A){
					 bheigth[1] = bwidth[3];
					 fis.read(tmp);
					 if(tmp[0] != 0x0A){
						 bheigth[2] = tmp[0];
						 fis.read(tmp);
						 if(tmp[0] != 0x0A){
							 bheigth[3] = tmp[0];
							 sheigth = String.valueOf((char)bheigth[0]) + String.valueOf((char)bheigth[1]) + String.valueOf((char)bheigth[2]) + String.valueOf((char)bheigth[3]);
							 
							 //輝度の取得
							 fis.read();
							 fis.read(tmp);
							 blight[0] = tmp [0];
							 fis.read(tmp);
							 if(tmp[0] == 0x0A){
								 slight = String.valueOf((char)blight[0]);
							 }else{
								 blight[1] = tmp[0];
								 fis.read(tmp);
								 if(tmp[0] == 0x0A){
									 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]);
								 }else{
									 blight[2] = tmp[0];
									 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]) + String.valueOf((char)blight[2]);
									 fis.read();
								 }
							 }
						 }else{
							 sheigth = String.valueOf((char)bheigth[0]) + String.valueOf((char)bheigth[1]) + String.valueOf((char)bheigth[2]);
							 
							 //輝度の取得
							 fis.read(tmp);
							 blight[0] = tmp [0];
							 fis.read(tmp);
							 if(tmp[0] == 0x0A){
								 slight = String.valueOf((char)blight[0]);
							 }else{
								 blight[1] = tmp[0];
								 fis.read(tmp);
								 if(tmp[0] == 0x0A){
									 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]);
								 }else{
									 blight[2] = tmp[0];
									 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]) + String.valueOf((char)blight[2]);
									 fis.read();
								 }
							 }
						 }
					 }else{
						 sheigth = String.valueOf((char)bheigth[0]) + String.valueOf((char)bheigth[1]);
						 
						//輝度の取得
						 fis.read(tmp);
						 blight[0] = tmp [0];
						 fis.read(tmp);
						 if(tmp[0] == 0x0A){
							 slight = String.valueOf((char)blight[0]);
						 }else{
							 blight[1] = tmp[0];
							 fis.read(tmp);
							 if(tmp[0] == 0x0A){
								 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]);
							 }else{
								 blight[2] = tmp[0];
								 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]) + String.valueOf((char)blight[2]);
								 fis.read();
							 }
						 }
					 }
				 }else{
					sheigth = String.valueOf((char)bheigth[0]);
					
					//輝度の取得
					 fis.read(tmp);
					 blight[0] = tmp [0];
					 fis.read(tmp);
					 if(tmp[0] == 0x0A){
						 slight = String.valueOf((char)blight[0]);
					 }else{
						 blight[1] = tmp[0];
						 fis.read(tmp);
						 if(tmp[0] == 0x0A){
							 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]);
						 }else{
							 blight[2] = tmp[0];
							 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]) + String.valueOf((char)blight[2]);
							 fis.read();
						 }
					 }
				 }
			 }else if(bwidth[2] == 0x20){
				 swidth = String.valueOf((char)bwidth[0]) + String.valueOf((char)bwidth[1]);
				 
				 bheigth[0] = bwidth[3];
				 fis.read(tmp);
				 if(tmp[0] == 0x0A){
					 sheigth = String.valueOf((char)bheigth[0]);
					 
					 //輝度の取得
					 fis.read(tmp);
					 blight[0] = tmp [0];
					 fis.read(tmp);
					 if(tmp[0] == 0x0A){
						 slight = String.valueOf((char)blight[0]);
					 }else{
						 blight[1] = tmp[0];
						 fis.read(tmp);
						 if(tmp[0] == 0x0A){
							 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]);
						 }else{
							 blight[2] = tmp[0];
							 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]) + String.valueOf((char)blight[2]);
							 fis.read();
						 }
					 }
				 }else{
					 bheigth[1] = tmp[0];
					 fis.read(tmp);
					 if(tmp[0] == 0x0A){
						 sheigth = String.valueOf((char)bheigth[0]) + String.valueOf((char)bheigth[1]);
						 
						//輝度の取得
						 fis.read(tmp);
						 blight[0] = tmp [0];
						 fis.read(tmp);
						 if(tmp[0] == 0x0A){
							 slight = String.valueOf((char)blight[0]);
						 }else{
							 blight[1] = tmp[0];
							 fis.read(tmp);
							 if(tmp[0] == 0x0A){
								 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]);
							 }else{
								 blight[2] = tmp[0];
								 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]) + String.valueOf((char)blight[2]);
								 fis.read();
							 }
						 }
					 }else{
						 bheigth[2] = tmp[0];
						 fis.read(tmp);
						 if(tmp[0] == 0x0A){
							 sheigth = String.valueOf((char)bheigth[0]) + String.valueOf((char)bheigth[1]) + String.valueOf((char)bheigth[2]);
							 
							 //輝度の取得
							 fis.read(tmp);
							 blight[0] = tmp [0];
							 fis.read(tmp);
							 if(tmp[0] == 0x0A){
								 slight = String.valueOf((char)blight[0]);
							 }else{
								 blight[1] = tmp[0];
								 fis.read(tmp);
								 if(tmp[0] == 0x0A){
									 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]);
								 }else{
									 blight[2] = tmp[0];
									 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]) + String.valueOf((char)blight[2]);
									 fis.read();
								 }
							 }
						 }else{
							 bheigth[3] = tmp[0];
							 sheigth = String.valueOf((char)bheigth[0]) + String.valueOf((char)bheigth[1]) + String.valueOf((char)bheigth[2]) + String.valueOf((char)bheigth[3]);
							 
							//輝度の取得
							 fis.read();
							 fis.read(tmp);
							 blight[0] = tmp [0];
							 fis.read(tmp);
							 if(tmp[0] == 0x0A){
								 slight = String.valueOf((char)blight[0]);
							 }else{
								 blight[1] = tmp[0];
								 fis.read(tmp);
								 if(tmp[0] == 0x0A){
									 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]);
								 }else{
									 blight[2] = tmp[0];
									 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]) + String.valueOf((char)blight[2]);
									 fis.read();
								 }
							 }
						 }
					 }
				 }
			 }else if(bwidth[3] == 0x20){
				 swidth = String.valueOf((char)bwidth[0]) + String.valueOf((char)bwidth[1]) + String.valueOf((char)bwidth[2]);
				 
				 fis.read(bheigth);
				 if(bheigth[1] == 0x0A){
					 sheigth = String.valueOf((char)bheigth[0]);
					 
					 blight[0] = bheigth[2];
					 if(bheigth[3] == 0x0A){
						 slight  = String.valueOf((char)blight[0]);
					 }else{
						 blight[1] = bheigth[3];
						 fis.read(tmp);
						 if(tmp[0] == 0x0A){
							 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]);
						 }else{
							 blight[2] = tmp[0];
							 fis.read(tmp);
							 if(tmp[0] == 0x0A){
								 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]) + String.valueOf((char)blight[2]);
							 }else{
								 blight[3] = tmp[0];
								 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]) + String.valueOf((char)blight[2]) + String.valueOf((char)blight[3]);
								 fis.read();
							 }
						 }
					 }
				 }else if(bheigth[2] == 0x0A){
					 sheigth = String.valueOf((char)bheigth[0]) + String.valueOf((char)bheigth[1]);
					 
					 blight[0] = bheigth[3];
					 fis.read(tmp);
					 if(tmp[0] == 0x0A){
						 slight = String.valueOf((char)blight[0]);
					 }else{
						 blight[1] = tmp[0];
						 fis.read(tmp);
						 if(tmp[0] == 0x0A){
							 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]);
						 }else{
							 blight[2] = tmp[0];
							 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]) + String.valueOf((char)blight[2]);
							 fis.read();
						 }
					 }
				 }else if(bheigth[3] == 0x0A){
					 sheigth = String.valueOf((char)bheigth[0]) + String.valueOf((char)bheigth[1]) + String.valueOf((char)bheigth[2]);
					 
					 //輝度の取得
					 fis.read(tmp);
					 blight[0] = tmp [0];
					 fis.read(tmp);
					 if(tmp[0] == 0x0A){
						 slight = String.valueOf((char)blight[0]);
					 }else{
						 blight[1] = tmp[0];
						 fis.read(tmp);
						 if(tmp[0] == 0x0A){
							 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]);
						 }else{
							 blight[2] = tmp[0];
							 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]) + String.valueOf((char)blight[2]);
							 fis.read();
						 }
					 }
				 }else{
					 sheigth = String.valueOf((char)bheigth[0]) + String.valueOf((char)bheigth[1]) + String.valueOf((char)bheigth[2]) + String.valueOf((char)bheigth[3]);
					 
					 //輝度の取得
					 fis.read();
					 fis.read(tmp);
					 blight[0] = tmp [0];
					 fis.read(tmp);
					 if(tmp[0] == 0x0A){
						 slight = String.valueOf((char)blight[0]);
					 }else{
						 blight[1] = tmp[0];
						 fis.read(tmp);
						 if(tmp[0] == 0x0A){
							 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]);
						 }else{
							 blight[2] = tmp[0];
							 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]) + String.valueOf((char)blight[2]);
							 fis.read();
						 }
					 }
				 }
			 }else{
				swidth = String.valueOf((char)bwidth[0]) + String.valueOf((char)bwidth[1]) + String.valueOf((char)bwidth[2]) + String.valueOf((char)bwidth[3]);
				
				fis.read();
				fis.read(bheigth);
				if(bheigth[1] == 0x0A){
					 sheigth = String.valueOf((char)bheigth[0]);
					 
					 blight[0] = bheigth[2];
					 if(bheigth[3] == 0x0A){
						 slight  = String.valueOf((char)blight[0]);
					 }else{
						 blight[1] = bheigth[3];
						 fis.read(tmp);
						 if(tmp[0] == 0x0A){
							 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]);
						 }else{
							 blight[2] = tmp[0];
							 fis.read(tmp);
							 if(tmp[0] == 0x0A){
								 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]) + String.valueOf((char)blight[2]);
							 }else{
								 blight[3] = tmp[0];
								 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]) + String.valueOf((char)blight[2]) + String.valueOf((char)blight[3]);
								 fis.read();
							 }
						 }
					 }
				 }else if(bheigth[2] == 0x0A){
					 sheigth = String.valueOf((char)bheigth[0]) + String.valueOf((char)bheigth[1]);
					 
					 blight[0] = bheigth[3];
					 fis.read(tmp);
					 if(tmp[0] == 0x0A){
						 slight = String.valueOf((char)blight[0]);
					 }else{
						 blight[1] = tmp[0];
						 fis.read(tmp);
						 if(tmp[0] == 0x0A){
							 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]);
						 }else{
							 blight[2] = tmp[0];
							 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]) + String.valueOf((char)blight[2]);
							 fis.read();
						 }
					 }
				 }else if(bheigth[3] == 0x0A){
					 sheigth = String.valueOf((char)bheigth[0]) + String.valueOf((char)bheigth[1]) + String.valueOf((char)bheigth[2]);
					 
					 //輝度の取得
					 fis.read(tmp);
					 blight[0] = tmp [0];
					 fis.read(tmp);
					 if(tmp[0] == 0x0A){
						 slight = String.valueOf((char)blight[0]);
					 }else{
						 blight[1] = tmp[0];
						 fis.read(tmp);
						 if(tmp[0] == 0x0A){
							 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]);
						 }else{
							 blight[2] = tmp[0];
							 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]) + String.valueOf((char)blight[2]);
							 fis.read();
						 }
					 }
				 }else{
					 sheigth = String.valueOf((char)bheigth[0]) + String.valueOf((char)bheigth[1]) + String.valueOf((char)bheigth[2]) + String.valueOf((char)bheigth[3]);
					 
					 //輝度の取得
					 fis.read();
					 fis.read(tmp);
					 blight[0] = tmp [0];
					 fis.read(tmp);
					 if(tmp[0] == 0x0A){
						 slight = String.valueOf((char)blight[0]);
					 }else{
						 blight[1] = tmp[0];
						 fis.read(tmp);
						 if(tmp[0] == 0x0A){
							 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]);
						 }else{
							 blight[2] = tmp[0];
							 slight = String.valueOf((char)blight[0]) + String.valueOf((char)blight[1]) + String.valueOf((char)blight[2]);
							 fis.read();
						 }
					 }
				 }
			 }
			 System.out.println("width = " + swidth);
			 System.out.println("heith = " + sheigth);
			 System.out.println("light = " + slight);
			 
			 //文字列から整数型への変換
			 spritline = Integer.parseInt(sspritline);
			 spritrow = Integer.parseInt(sspritrow);
			 selectnumber = Integer.parseInt(sselectnumber);
			 selectcost = Integer.parseInt(sselectcost);
			 changecost = Integer.parseInt(schangecost);
			 width = Integer.parseInt(swidth);
			 heigth = Integer.parseInt(sheigth);
			 light = Integer.parseInt(slight);
			 
			 
			 //画像データの取得
			 BufferedImage image = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_BGR);
			 for(int i = 0; i < heigth; i++){
				 for(int j = 0; j < width; j++){
					 fis.read(brgb);
					 image.setRGB(j, i, createRGB(brgb[0], brgb[1], brgb[2]));
				 }
			 }
			 
			 String cfilename = "test.jpg";
			 File f = new File("/Users/hayato/workspaceprocpn1/AutoProgram/src/takashima/" + cfilename);
			 ImageIO.write(image, "jpg", f);
			
			//System.out.println(String.valueOf((char)spritline[0]) + String.valueOf((char)spritline[1]));
			
			byte[] test = new byte[1];
			fis.read(test);
			System.out.println(Integer.toHexString(test[0]));
			
			//分割画像の初期化
			pict = new Picture[spritrow*spritline];
			for(int i = 0; i < spritrow*spritline; i++){
				pict[i] = new Picture(width/spritrow, heigth/spritline);
			}
			
			int count = 0;
			int row_num, line_num;
			String position;
			for(int i = 0; i < heigth; i = i + heigth/spritline){
				for(int j = 0; j < width; j = j + width/spritrow){
					pict[count].setNumber(count);
					for(int k = 0; k < heigth/spritline; k++){
						for(int l = 0; l < width/spritrow; l++){
							pict[count].setImage(l, k, image.getRGB(j+l, i+k));
						}
					}
					row_num = count%spritrow;
					line_num = count/spritrow;
					position = Integer.toHexString(row_num) + Integer.toHexString(line_num);
					pict[count].setFirstPosition(position);
					pict[count].setRoundPix();
					count++;
				}
			}
			for(int i = 0; i < spritrow*spritline; i++){
				pict[i].setRoundPix();
			}
			
			/*
			//画像の並べ替え
			int[] tmp_top = new int[width/spritrow];
			int[] tmp_under = new int[width/spritrow];
			int[] tmp_right = new int[heigth/spritline];
			int[] tmp_left = new int[heigth/spritline];
			
			for(int i = 0; i < spritrow*spritline; i++){
				for(int k = 0; k < spritrow*spritline; k++){
					if(i != k){
						tmp_top = pict[i].getRoundPix("top");
						tmp_under = pict[i].getRoundPix("under");
						tmp_right = pict[i].getRoundPix("right");
						tmp_left = pict[i].getRoundPix("left");
					    
						//-------分割画像の上の部分と近い下の部分を持つ画像を検索
						int min_haming = -1;
						
						//右にずらしていく
						tmp_top = pict[i].getRoundPix("top");
						System.out.println("i = " + i);
							count = 0;
							for(int l = 0; l < width/spritrow; l++){
								if(tmp_top[l] != pict[k].getRoundPix("under", l))
									count++;
							}
							if(min_haming == -1 || count < min_haming)
								min_haming = count;
						System.out.println("k = " + k + "  min_haming = " + min_haming);
						if(pict[i].getHaming("top") == -1 || pict[i].getHaming("top") > min_haming){
							pict[k].setUnder(pict[i]);
							pict[k].setHaming("under", min_haming);
							pict[i].setHaming("top", min_haming);
							pict[i].setTop(pict[k]);
						}
						
						//-------分割画像の右の部分と近い左の部分を持つ画像を検索
						min_haming = -1;
						//上にずらしていく
						tmp_right = pict[i].getRoundPix("right");
							count = 0;
							for(int l = 0; l < heigth/spritline; l++){
								if(tmp_right[l] != pict[k].getRoundPix("left", l))
									count++;
							}
							if(min_haming == -1 || count < min_haming)
								min_haming = count;
						if(pict[i].getHaming("right") == -1 || pict[i].getHaming("right") > min_haming){
							pict[i].setHaming("right", min_haming);
							pict[i].setRight(pict[k]);
							pict[k].setLeft(pict[i]);
							pict[k].setHaming("left", min_haming);
						}
					}
				}
			}
			
			setRoundFlag(pict, spritline, spritrow);
			
			for(int i = 0; i < spritrow*spritline; i++){
				System.out.println("pictnum = " + i + 
						"\ntop = " + pict[i].getTop().getNumber() + 
						"\tunder = " + pict[i].getUnder().getNumber() + 
						"\tright = " + pict[i].getRight().getNumber() + 
						"\tleft = " + pict[i].getLeft().getNumber() + 
						"\nflag = " + pict[i].getFlag("top") + 
						"\t" + pict[i].getFlag("under") + 
						"\t" + pict[i].getFlag("right") + 
						"\t" + pict[i].getFlag("left"));
			}
			 
			System.out.println("pict[1].getTop() = " + pict[8].getHaming("left"));
			System.out.println(Integer.toHexString((btype[0])));
			System.out.println(type);
			*/
			fis.close();
			
			
			// window
			
			//フレームの作成
			JFrame frame = new JFrame();
			 
			//タイトルを設定
			frame.setTitle("View3156");
	
			//フレーム（ウィンドウ）のサイズを設定
			frame.setBounds(0,0,AppCanvas.scale * main.spritline + 6, AppCanvas.scale * main.spritrow + 28);
			//×ボタンを押したときの処理を記述
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
			//ウィンドウサイズは固定
			frame.setResizable(false);
	
			//レイアウトは手動
			frame.setLayout(null);
			
			//コンテントペインの作成
			JPanel cp = new JPanel();

			//レイアウトは手動
			cp.setLayout(null);

			//フレームにコンテントペインを登録
			frame.add(cp);

			//コンテントペインの貼り付け位置
			cp.setBounds( 0, 0, main.width, main.heigth);
			  
			AppCanvas canvas = new AppCanvas();
			
			// pict配列の更新を適応する
			canvas.updatePictArray();
			cp.add(canvas);
			canvas.setBounds(0, 0, main.width, main.heigth);
			
			frame.setVisible(true);	// 描画
			
			
			// ここからデバッグ用
			
			mouse_Frame mouse = new mouse_Frame("mouse1",192,192);
			mouse.setVisible(true);
			
			Picture tmpPict = pict[0];
			pict[0] = pict[1];
			pict[1] = tmpPict;
			
			// アップデート
			canvas.updatePictArray();
			
			// 再描画
			canvas.repaint();
			
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//表示用のキャンパス
	public static class AppCanvas extends Canvas{
		/**
		 * 
		 */
		private static final long serialVersionUID = 8671443010880497048L;
		static BufferedImage img;
		static Image baseImage;
		
		public static int scale = 64;
		private int currentPos = 0;
		private int currentNum = 0;
		
		public AppCanvas(){	
			img = pict[this.currentNum].getImage();
			baseImage = new BufferedImage(main.width, main.heigth, img.getType());
		}
		
		public void updatePictArray() {
			Graphics g = baseImage.getGraphics();
			Image bufImage = new BufferedImage(scale, scale, img.getType());
			int x = 0;
			int y = 0;
			for(Picture picture : pict) {
				img = picture.getImage();
				bufImage = img.getScaledInstance(scale, scale, Image.SCALE_FAST);
				g.drawImage(bufImage, scale * x, scale * y, scale, scale, null);
				x++;
				if(x == main.spritline) {
					x = 0;
					y++;
				}
			}
		}
		
		public BufferedImage loadImage(String name){
			try{
				FileInputStream in = new FileInputStream(name);
				BufferedImage rv = ImageIO.read(in);
				in.close();
				return rv;
			}catch(IOException e){
				System.out.println("Err e="+e);
				return null;
			}
		}
		//表示の要求があれば実行されるメソッド
		public void paint(Graphics g){
			g.drawImage(baseImage, 0, 0, main.width, main.heigth, this);
		}
	}
	
	static class Adapter extends WindowAdapter {
		public void windowClosing(WindowEvent e){
			System.exit(0);
		}
	}
	
	//RGB情報をint型に変換するメソッド
	public static int createRGB(byte r, byte g, byte b){
		
		return r <<16 | g << 8 | b;
		
	}
	
	public static void setRoundFlag(Picture[] pict, int spritline, int spritrow){
		for(int i = 0; i < spritline*spritrow; i++){
			for(int j = 0; j < spritrow*spritline; j++){
				if(i != j){
					//上端の判定
					System.out.println("pict[" + i  +"].number = " + pict[i].getTop().getNumber() + "pict[" + j + "].number = " + pict[j].getTop().getNumber());
					if(pict[i].getTop().getNumber() == pict[j].getTop().getNumber()){
						if(pict[i].getHaming("top") < pict[j].getHaming("top")){
							System.out.println("\n\n\n-------------");
							pict[j].setFlag("top");
						}else{
							pict[i].setFlag("top");
						}
					}
					//下端の判定
					if(pict[i].getUnder() == pict[j].getUnder()){
						if(pict[i].getHaming("under") < pict[j].getHaming("under")){
							pict[j].setFlag("under");
						}else{
							pict[i].setFlag("under");
						}
					}
					//右端の判定
					if(pict[i].getRight() == pict[j].getRight()){
						if(pict[i].getHaming("right") < pict[j].getHaming("right")){
							pict[j].setFlag("right");
						}else{
							pict[i].setFlag("right");
						}
					}
					//左端の判定
					if(pict[i].getLeft() == pict[j].getLeft()){
						if(pict[i].getHaming("left") < pict[j].getHaming("left")){
							pict[j].setFlag("left");
						}else{
							pict[i].setFlag("left");
						}
					}
				}
			}
		}
	}
}



