package takashima;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;

import org.apache.commons.lang.SerializationUtils;

import javax.imageio.ImageIO;

public class main {
	public static void main(String[] args){
		try{
			String filename = "prob10.ppm";
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
			int spritline, spritrow, selectnumber, selectcost, changecost, 
					heigth, width, light;
			byte[] brgb = new byte[3];
			
			
			BufferedInputStream fis = null;
			File file = new File("/Users/takahiro/Git/AutoProgram/src/takashima/" + filename);
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
			 
			 String cfilename = "test2.jpg";
			 File f = new File(/*"/Users/takahiro/Git/AutoProgram/src/takashima/" + */cfilename);
			 ImageIO.write(image, "jpg", f);
			
			//System.out.println(String.valueOf((char)spritline[0]) + String.valueOf((char)spritline[1]));
			
			byte[] test = new byte[1];
			fis.read(test);
			System.out.println(Integer.toHexString(test[0]));
			
			//分割画像の初期化
			Picture[] pict = new Picture[spritrow*spritline];
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
						tmp_top = pict[i].getRoundPix("top");
						System.out.println("i = " + i);
							count = 0;
							for(int l = 0; l < width/spritrow; l++){
								if(tmp_top[l] != pict[k].getRoundPix("under", l))
									count++;
							}
							if(min_haming == -1 || count < min_haming)
								min_haming = count;
						//System.out.println("Top\nk = " + k + "  min_haming = " + min_haming);
						if(pict[i].getHaming("top") == -1 || pict[i].getHaming("top") > min_haming){
							if(pict[k].getHaming("under") == -1 || pict[k].getHaming("under") > min_haming){
								pict[k].setUnder(pict[i]);
								pict[k].setHaming("under", min_haming);
							}
							pict[i].setHaming("top", min_haming);
							pict[i].setTop(pict[k]);
						}
						if(pict[i].getHaming("under") == -1){
							pict[i].setUnder(pict[k]);
							pict[i].setHaming("under", min_haming);
						}
						/*
						//-------分割画像の下の部分と近い上の部分を持つ画像を検索
						min_haming = -1;
						count = 0;
						for(int l = 0; l < width/spritrow; l++){
							if(tmp_under[l] != pict[k].getRoundPix("top", l))
								count++;
						}
						if(min_haming == -1 || count < min_haming)
							min_haming = count;
						System.out.println("k = " + k + "  min_haming = " + min_haming);
						if(pict[i].getHaming("under") == -1 || pict[i].getHaming("under") > min_haming){
							pict[i].setHaming("under", min_haming);
							pict[i].setUnder(pict[k]);
							pict[k].setHaming("top", min_haming);
							pict[k].setTop(pict[i]);
						}
						*/
						//-------分割画像の右の部分と近い左の部分を持つ画像を検索
						min_haming = -1;
						//下にずらしていく
						/*
						//for(int j = 0; j < (heigth/spritline)/3; j++){
							count = 0;
							for(int l = 0; l < heigth/spritline; l++){
								if(tmp_right[l] != pict[k].getRoundPix("left", l))
									count++;
							}
							if(min_haming == -1 || count < min_haming)
								min_haming = count;
							/*for(int m = 0; m < heigth/spritline - 1; m++){
								tmp_right[m] = tmp_right[m+1];
							}*/
						//}
						
						//上にずらしていく
						tmp_right = pict[i].getRoundPix("right");
						//for(int j = 0; j < (heigth/spritline)/3; j++){
							count = 0;
							for(int l = 0; l < heigth/spritline; l++){
								if(tmp_right[l] != pict[k].getRoundPix("left", l))
									count++;
							}
							if(min_haming == -1 || count < min_haming)
								min_haming = count;
							/*for(int m = heigth/spritline; m < 0; m--){
								tmp_right[m] = tmp_right[m-1];
							}*/
						//}
							System.out.println("k = " + k + "  min_haming = " + min_haming);
						if(pict[i].getHaming("right") == -1 || pict[i].getHaming("right") > min_haming){
							if(pict[k].getHaming("left") == -1 || pict[k].getHaming("left") > min_haming){
								pict[k].setLeft(pict[i]);
								pict[k].setHaming("left", min_haming);
							}
							pict[i].setHaming("right", min_haming);
							pict[i].setRight(pict[k]);
						}
						
						/*
						//-------分割画像の左の部分と近い右の部分を持つ画像を検索
						min_haming = -1;
						//下にずらしていく
						for(int j = 0; j < (heigth/spritline)/3; j++){
							count = 0;
							for(int l = 0; l < heigth/spritline; l++){
								if(tmp_left[l] != pict[k].getRoundPix("right", l))
									count++;
							}
							if(min_haming == -1 || count < min_haming)
								min_haming = count;
							for(int m = 0; m < heigth/spritline - 1; m++){
								tmp_left[m] = tmp_left[m+1];
							}
						}
						
						//上にずらしていく
						tmp_left = pict[i].getRoundPix("left");
						for(int j = 0; j < (heigth/spritline)/3; j++){
							count = 0;
							for(int l = 0; l < heigth/spritline; l++){
								if(tmp_left[l] != pict[k].getRoundPix("right", l))
									count++;
							}
							if(min_haming == -1 || count < min_haming)
								min_haming = count;
							for(int m = heigth/spritline; m < 0; m--){
								tmp_left[m] = tmp_left[m-1];
							}
						}
						if(pict[i].getHaming("left") == -1 || pict[i].getHaming("left") > min_haming){
							pict[i].setHaming("left", min_haming);
							pict[i].setLeft(pict[k]);
						}
						*/
					}
				}
			}
			
			//-------上端の設定
			/*
			for(int j = 0; j < spritrow; j++){
				int max = -1;
				int maxnum = -1;
				for(int i = 0; i < spritline*spritrow; i++){
					if((spritline != 1) && (max == -1 || max < pict[i].getHaming("top")) && !pict[i].getFlag("under")){
						max = pict[i].getHaming("top");
						maxnum = i;
					}
				}
				System.out.println("maxnum = " + maxnum + "\ttopnum = " + pict[maxnum].getTop().getNumber());
				pict[maxnum].getTop().setHaming("under", 0);
				pict[maxnum].getTop().setFlag("under");
				pict[maxnum].getTop().setUnder(null);
				pict[maxnum].setTop(null);
				pict[maxnum].setFlag("top");
				pict[maxnum].setHaming("top", 0);
			}
			*/
			
			setRoundFlag(pict, spritline, spritrow);
			
			//System.out.println("pict[11].under = " + pict[11].getUnder());
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
			
			Picture[] result = new Picture[spritrow*spritline];
			result = createImage(pict, spritrow, spritline);
			BufferedImage newImage = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_BGR);
			Graphics g = newImage.getGraphics();
			for(int i = 0; i < spritline; i++){
				for(int j = 0; j < spritrow; j++){
					g.drawImage(result[i*spritrow+j].getImage(), j*result[0].getWidth(), i*result[0].getHeight(), result[0].getWidth(), result[0].getHeight(), null);
				}
			}
			
			/*
			for(int i = 0; i < 16; i++){
				System.out.println("pict[" + i + "] =\ntop = " + pict[i].getHaming("top") + "\tunder = " + pict[i].getHaming("under") + "\tright = " + pict[i].getHaming("right") + "\tleft = " + pict[i].getHaming("left"));
			}*/
			/*
			for(int i = 0; i < heigth/spritline; i++){
				System.out.println(
						Integer.toHexString(pict[7].getRoundPix("right", i)) + "\t" + 
						Integer.toHexString(pict[8].getRoundPix("left", i)) + "\t" + 
						Integer.toHexString(pict[4].getRoundPix("right", i)));
			}*/
			/*for(int i = 0; i < 100; i++){
				for(int j = 0; j < 100; j++){
					System.out.print(Integer.toHexString(pict[7].getImage().getRGB(j, i)) + "  ");
				}
				System.out.println();
			}*/
			//System.out.println(pict[7].getWidth() + "  " + pict[8].getHeight());
			System.out.println("pict[1].getTop() = " + pict[8].getHaming("left"));//pict[3].getTop().getNumber());
			ImageIO.write(pict[8].getImage(), "jpg", f);
			ImageIO.write(newImage, "jpg", f);
			
			//BufferedImage bufimg = new BufferedImage((int)width, (int)heigth, BufferedImage.TYPE_INT_BGR);
			//while()
			/*
			Picture test1 = new Picture(1, 1), test2 = new Picture(1, 1);
			test1.setHaming("top", 1);
			test2.setTop(test1);
			System.out.println("test1.haming = " + test2.getTop().getHaming("top"));
			test1.setHaming("top", 2);
			System.out.println("test1.haming = " + test2.getTop().getHaming("top"));
			*/
			//System.out.println(strwidth);
			System.out.println(Integer.toHexString((btype[0])));
			System.out.println(type);
			
			fis.close();
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//RGB情報をint型に変換するメソッド
	public static int createRGB(byte r, byte g, byte b){
		
		return r <<16 | g << 8 | b;
		
	}
	
	public static void setRoundFlag(Picture[] pict, int spritline, int spritrow){
		System.out.println("width = " + pict[0].getWidth() + "  height = " + pict[0].getHeight());
		for(int i = 0; i < spritline*spritrow; i++){
			//for(int j = 0; j < spritrow*spritline; j++){
				//if(i != j){
					//上端の判定
					/*
					System.out.println("Top   : pict[" + i  +"].number = " + pict[i].getTop().getNumber() + "  pict[" + j + "].number = " + pict[j].getTop().getNumber());
					if(pict[i].getTop().getNumber() == pict[j].getTop().getNumber()){
						System.out.println("Top\npict[" + i + "].haming = " + pict[i].getHaming("top") + 
								"  pict[" + j + "].haming = " + pict[j].getHaming("top"));
						//if(pict[i].getHaming("top") < pict[j].getHaming("top")){
						if(pict[j].getHaming("top") > 100)
							//System.out.println("\n\n\n-------------");
							pict[j].setFlag("top");
						//}else{
						if(pict[i].getHaming("top") > 100)
							pict[i].setFlag("top");
						//}
					}
					*/
			if(pict[i].getHaming("top") > pict[0].getWidth()/3)
				pict[i].setFlag("top");
					//下端の判定
					/*
					System.out.println("Under : pict[" + i  +"].number = " + pict[i].getUnder().getNumber() + "  pict[" + j + "].number = " + pict[j].getUnder().getNumber());
					if(pict[i].getUnder() == pict[j].getUnder()){
						System.out.println("Under\npict[" + i + "].haming = " + pict[i].getHaming("under") + 
								"  pict[" + j + "].haming = " + pict[j].getHaming("under"));
						//if(pict[i].getHaming("under") < pict[j].getHaming("under")){
						if(pict[j].getHaming("under") > 100)
							pict[j].setFlag("under");
						//}else{
						if(pict[i].getHaming("under") > 100)
							pict[i].setFlag("under");
						//}
					}
					*/
			if(pict[i].getHaming("under") > pict[0].getWidth()/3)
				pict[i].setFlag("under");
					//右端の判定
					/*
					System.out.println("Right : pict[" + i  +"].number = " + pict[i].getRight().getNumber() + "  pict[" + j + "].number = " + pict[j].getRight().getNumber());
					if(pict[i].getRight() == pict[j].getRight()){
						//if(pict[i].getHaming("right") < pict[j].getHaming("right")){
						System.out.println("Right\npict[" + i + "].haming = " + pict[i].getHaming("right") + 
								"  pict[" + j + "].haming = " + pict[j].getHaming("right"));
						if(pict[j].getHaming("right") > 100)
							pict[j].setFlag("right");
						//}else{
						if(pict[i].getHaming("right") > 100)
							pict[i].setFlag("right");
						//}
					}
					*/
			if(pict[i].getHaming("right") > pict[0].getHeight()/3)
				pict[i].setFlag("right");
			//左端の判定
			/*
			System.out.println("Left  : pict[" + i  +"].number = " + pict[i].getLeft().getNumber() + "  pict[" + j + "].number = " + pict[j].getLeft().getNumber());
			if(pict[i].getLeft() == pict[j].getLeft()){
				//if(pict[i].getHaming("left") < pict[j].getHaming("left")){
				System.out.println("Left\npict[" + i + "].haming = " + pict[i].getHaming("left") + 
						"  pict[" + j + "].haming = " + pict[j].getHaming("left"));
				if(pict[j].getHaming("left") > 100)
					pict[j].setFlag("left");
				//}else{
				if(pict[i].getHaming("left") > 100)
					pict[i].setFlag("left");
				//}
			//}
			 * 
			 */
			if(pict[i].getHaming("left") > pict[0].getHeight()/3)
				pict[i].setFlag("left");
				
		}
	}
	public static Picture[] createImage(Picture[] pict, int spritrow, int spritline){
		int start = 0;
		Picture[] tmp_pict = new Picture[spritline*spritrow];
		Picture current_pict = null;
		for(int i = 0; i < spritline*spritrow; i++)
			if(pict[i].getFlag("left") && pict[i].getFlag("top"))
				start = i;
		for(int i = 0; i < spritline; i++){
			for(int j = 0; j < spritrow; j++){
				if(i == 0 && j == 0){
					tmp_pict[0] = pict[start];
					current_pict = pict[start];
				}else if(j == 0){
					tmp_pict[i*spritrow] = current_pict;
				}else{
					tmp_pict[i*spritrow + j] = current_pict.getRight();
					current_pict = current_pict.getRight();
				}
			}
			current_pict = tmp_pict[i*spritrow].getUnder();
		}
		return tmp_pict;
	}
}



