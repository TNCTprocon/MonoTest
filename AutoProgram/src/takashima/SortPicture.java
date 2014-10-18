package takashima;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;

import org.apache.commons.lang.SerializationUtils;

import javax.imageio.ImageIO;


public class SortPicture {
	private String filename = "prob10.ppm";
	private byte[] btype = new byte[2];
	private byte[] bsplitline = new byte[2];
	private byte[] bsplitrow = new byte[2];
	private byte[] bselectnumber = new byte[3];
	private byte[] bselectcost = new byte[3];
	private byte[] bchangecost = new byte[3];
	private byte[] bheight = new byte[4];
	private byte[] bwidth = new byte[4];
	private byte[] blight = new byte[3];
	private byte[] three = new byte[3];
	private byte[] twe = new byte[2];
	private byte[] tmp = new byte[1];
	private String type, ssplitline, ssplitrow, sselectnumber, sselectcost, 
			schangecost, sheight, swidth, slight;
	private int splitline, splitrow, selectnumber, selectcost, changecost, 
			heigth, width, light;
	private byte[] brgb = new byte[3];
	private BufferedInputStream fis;
	private BufferedImage image;
	private Picture[] pict;
	private int count;
	
	SortPicture(File input_file){
		try{
			fis = new BufferedInputStream(new FileInputStream(input_file));
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void setData(){
		setTagData();
		setImage();
		cutImage();
		sortImage();
		
	}
	
	//Input画像のタグ部分のデータを読み込む
	private void setTagData(){
		try{
			//タイプの取得
			fis.read(btype);
			type = String.valueOf((char)btype[0]) + String.valueOf((char)btype[1]);
			
			fis.read(three);
			fis.read(bsplitrow);
			
			//分割の列数の取得
			if(bsplitrow[1] == 0x20){
				bsplitrow[1] = 0;
				fis.read(bsplitline);
				ssplitrow = String.valueOf((char)bsplitrow[0]);
			}else{
				fis.read();
				fis.read(bsplitline);
				ssplitrow = String.valueOf((char)bsplitrow[0]) + String.valueOf((char)bsplitrow[1]);
			}
			System.out.println("Spritrow = " + ssplitrow);
			
			//分割の行数の取得
			if(bsplitline[1] == 0x0A){
				bsplitline[1] = 0;
				fis.read(twe);
				fis.read(bselectnumber);
				ssplitline = String.valueOf((char)bsplitline[0]);
			}else{
				fis.read(three);
				fis.read(bselectnumber);
				ssplitline = String.valueOf((char)bsplitline[0]) + String.valueOf((char)bsplitline[1]);
			}
			System.out.println("spritline = " + ssplitline);
			
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
				 
				 bheight[0] = bwidth[2];
				 if(bwidth[3] != 0x0A){
					 bheight[1] = bwidth[3];
					 fis.read(tmp);
					 if(tmp[0] != 0x0A){
						 bheight[2] = tmp[0];
						 fis.read(tmp);
						 if(tmp[0] != 0x0A){
							 bheight[3] = tmp[0];
							 sheight = String.valueOf((char)bheight[0]) + String.valueOf((char)bheight[1]) + String.valueOf((char)bheight[2]) + String.valueOf((char)bheight[3]);
							 
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
							 sheight = String.valueOf((char)bheight[0]) + String.valueOf((char)bheight[1]) + String.valueOf((char)bheight[2]);
							 
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
						 sheight = String.valueOf((char)bheight[0]) + String.valueOf((char)bheight[1]);
						 
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
					sheight = String.valueOf((char)bheight[0]);
					
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
				 
				 bheight[0] = bwidth[3];
				 fis.read(tmp);
				 if(tmp[0] == 0x0A){
					 sheight = String.valueOf((char)bheight[0]);
					 
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
					 bheight[1] = tmp[0];
					 fis.read(tmp);
					 if(tmp[0] == 0x0A){
						 sheight = String.valueOf((char)bheight[0]) + String.valueOf((char)bheight[1]);
						 
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
						 bheight[2] = tmp[0];
						 fis.read(tmp);
						 if(tmp[0] == 0x0A){
							 sheight = String.valueOf((char)bheight[0]) + String.valueOf((char)bheight[1]) + String.valueOf((char)bheight[2]);
							 
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
							 bheight[3] = tmp[0];
							 sheight = String.valueOf((char)bheight[0]) + String.valueOf((char)bheight[1]) + String.valueOf((char)bheight[2]) + String.valueOf((char)bheight[3]);
							 
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
				 
				 fis.read(bheight);
				 if(bheight[1] == 0x0A){
					 sheight = String.valueOf((char)bheight[0]);
					 
					 blight[0] = bheight[2];
					 if(bheight[3] == 0x0A){
						 slight  = String.valueOf((char)blight[0]);
					 }else{
						 blight[1] = bheight[3];
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
				 }else if(bheight[2] == 0x0A){
					 sheight = String.valueOf((char)bheight[0]) + String.valueOf((char)bheight[1]);
					 
					 blight[0] = bheight[3];
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
				 }else if(bheight[3] == 0x0A){
					 sheight = String.valueOf((char)bheight[0]) + String.valueOf((char)bheight[1]) + String.valueOf((char)bheight[2]);
					 
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
					 sheight = String.valueOf((char)bheight[0]) + String.valueOf((char)bheight[1]) + String.valueOf((char)bheight[2]) + String.valueOf((char)bheight[3]);
					 
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
				fis.read(bheight);
				if(bheight[1] == 0x0A){
					 sheight = String.valueOf((char)bheight[0]);
					 
					 blight[0] = bheight[2];
					 if(bheight[3] == 0x0A){
						 slight  = String.valueOf((char)blight[0]);
					 }else{
						 blight[1] = bheight[3];
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
				 }else if(bheight[2] == 0x0A){
					 sheight = String.valueOf((char)bheight[0]) + String.valueOf((char)bheight[1]);
					 
					 blight[0] = bheight[3];
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
				 }else if(bheight[3] == 0x0A){
					 sheight = String.valueOf((char)bheight[0]) + String.valueOf((char)bheight[1]) + String.valueOf((char)bheight[2]);
					 
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
					 sheight = String.valueOf((char)bheight[0]) + String.valueOf((char)bheight[1]) + String.valueOf((char)bheight[2]) + String.valueOf((char)bheight[3]);
					 
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
			 System.out.println("heith = " + sheight);
			 System.out.println("light = " + slight);
			 
			 //文字列から整数型への変換
			 splitline = Integer.parseInt(ssplitline);
			 splitrow = Integer.parseInt(ssplitrow);
			 selectnumber = Integer.parseInt(sselectnumber);
			 selectcost = Integer.parseInt(sselectcost);
			 changecost = Integer.parseInt(schangecost);
			 width = Integer.parseInt(swidth);
			 heigth = Integer.parseInt(sheight);
			 light = Integer.parseInt(slight);
			 
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//画像データの取得
	private void setImage(){
		try{
			//画像データの取得
			 image = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_BGR);
			 for(int i = 0; i < heigth; i++){
				 for(int j = 0; j < width; j++){
					 fis.read(brgb);
					 image.setRGB(j, i, createRGB(brgb[0], brgb[1], brgb[2]));
				 }
			 }
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//画像の分割
	private void cutImage(){
		//分割画像の初期化
		pict = new Picture[splitrow*splitline];
		for(int i = 0; i < splitrow*splitline; i++){
			pict[i] = new Picture(width/splitrow, heigth/splitline);
		}
		
		int count = 0;
		int row_num, line_num;
		String position;
		for(int i = 0; i < heigth; i = i + heigth/splitline){
			for(int j = 0; j < width; j = j + width/splitrow){
				pict[count].setNumber(count);
				for(int k = 0; k < heigth/splitline; k++){
					for(int l = 0; l < width/splitrow; l++){
						pict[count].setImage(l, k, image.getRGB(j+l, i+k));
					}
				}
				row_num = count%splitrow;
				line_num = count/splitrow;
				position = Integer.toHexString(row_num) + Integer.toHexString(line_num);
				pict[count].setFirstPosition(position);
				pict[count].setRoundPix();
				count++;
			}
		}
		for(int i = 0; i < splitrow*splitline; i++){
			pict[i].setRoundPix();
		}
	}
	
	//分割画像の並べ替え
	private void sortImage(){
		//画像の並べ替え
		int[] tmp_top = new int[width/splitrow];
		int[] tmp_right = new int[heigth/splitline];
		
		for(int i = 0; i < splitrow*splitline; i++){
			for(int k = 0; k < splitrow*splitline; k++){
				if(i != k){
					tmp_top = pict[i].getRoundPix("top");
					tmp_right = pict[i].getRoundPix("right");
				    
					//-------分割画像の上の部分と近い下の部分を持つ画像を検索
					int min_haming = -1;
					tmp_top = pict[i].getRoundPix("top");
						count = 0;
						for(int l = 0; l < width/splitrow; l++){
							if(tmp_top[l] != pict[k].getRoundPix("under", l))
								count++;
						}
						if(min_haming == -1 || count < min_haming)
							min_haming = count;
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
					//-------分割画像の右の部分と近い左の部分を持つ画像を検索
					min_haming = -1;
					tmp_right = pict[i].getRoundPix("right");
						count = 0;
						for(int l = 0; l < heigth/splitline; l++){
							if(tmp_right[l] != pict[k].getRoundPix("left", l))
								count++;
						}
						if(min_haming == -1 || count < min_haming)
							min_haming = count;
					if(pict[i].getHaming("right") == -1 || pict[i].getHaming("right") > min_haming){
						if(pict[k].getHaming("left") == -1 || pict[k].getHaming("left") > min_haming){
							pict[k].setLeft(pict[i]);
							pict[k].setHaming("left", min_haming);
						}
						pict[i].setHaming("right", min_haming);
						pict[i].setRight(pict[k]);
					}
				}
			}
		}
		setRoundFlag(pict, splitline, splitrow);
		
		Picture[] result = new Picture[splitrow*splitline];
		result = createImage(pict, splitrow, splitline);
		BufferedImage newImage = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_BGR);
		Graphics g = newImage.getGraphics();
		for(int i = 0; i < splitline; i++){
			for(int j = 0; j < splitrow; j++){
				g.drawImage(result[i*splitrow+j].getImage(), j*result[0].getWidth(), i*result[0].getHeight(), result[0].getWidth(), result[0].getHeight(), null);
			}
		}
	}
	
	//RGB情報をint型に変換するメソッド
	private int createRGB(byte r, byte g, byte b){
		
		return r <<16 | g << 8 | b;
			
	}
	
	//ソート後の分割画像配列を作成する
	private Picture[] createImage(Picture[] pict, int splitrow, int splitline){
		int start = 0;
		Picture[] tmp_pict = new Picture[splitline*splitrow];
		Picture current_pict = null;
		for(int i = 0; i < splitline*splitrow; i++)
			if(pict[i].getFlag("left") && pict[i].getFlag("top"))
				start = i;
		for(int i = 0; i < splitline; i++){
			for(int j = 0; j < splitrow; j++){
				if(i == 0 && j == 0){
					tmp_pict[0] = pict[start];
					current_pict = pict[start];
				}else if(j == 0){
					tmp_pict[i*splitrow] = current_pict;
				}else{
					tmp_pict[i*splitrow + j] = current_pict.getRight();
					current_pict = current_pict.getRight();
				}
			}
			current_pict = tmp_pict[i*splitrow].getUnder();
		}
		return tmp_pict;
	}
	
	//端の画像片を探す
	private void setRoundFlag(Picture[] pict, int splitline, int splitrow){
		System.out.println("width = " + pict[0].getWidth() + "  height = " + pict[0].getHeight());
		for(int i = 0; i < splitline*splitrow; i++){
			//上端の判定
			if(pict[i].getHaming("top") > pict[0].getWidth()/3)
				pict[i].setFlag("top");
			//下端の判定
			if(pict[i].getHaming("under") > pict[0].getWidth()/3)
				pict[i].setFlag("under");
			//右端の判定
			if(pict[i].getHaming("right") > pict[0].getHeight()/3)
				pict[i].setFlag("right");
			//左端の判定
			if(pict[i].getHaming("left") > pict[0].getHeight()/3)
				pict[i].setFlag("left");	
		}
	}
}
