package takahiro;

import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class main {
	public static void main(String[] args){
		try{
			String filename = "prob01.ppm";
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
			File file = new File("/Users/takahiro/Git/AutoProgram/puzzle/src/takahiro/picture/" + filename);
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
			 File f = new File("/Users/takahiro/Git/AutoProgram/puzzle/src/takahiro/picture/" + cfilename);
			 ImageIO.write(image, "jpg", f);
			
			//System.out.println(String.valueOf((char)spritline[0]) + String.valueOf((char)spritline[1]));
			
			byte[] test = new byte[1];
			fis.read(test);
			System.out.println(Integer.toHexString(test[0]));
			
			
			
			//BufferedImage bufimg = new BufferedImage((int)width, (int)heigth, BufferedImage.TYPE_INT_BGR);
			//while()
			
			//System.out.println(strwidth);
			System.out.println(Integer.toHexString((btype[0])));
			System.out.println(type);
			
			
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
}
