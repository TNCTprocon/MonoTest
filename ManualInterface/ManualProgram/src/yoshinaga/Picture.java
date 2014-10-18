package yoshinaga;

import java.awt.image.BufferedImage;
import org.apache.commons.lang.SerializationUtils;

public class Picture {
	private int width, height;
	private int pictnum;
	private Picture top, under, right, left;
	private BufferedImage image;
	private int[] toppest_pix, underest_pix, rightest_pix, leftest_pix;
	private int top_haming, under_haming, right_haming, left_haming;
	boolean toppest, underest, rightest, leftest;
	private String first_position, current_position;
	private int r, g, b;
	
	Picture(int wid, int hei){
		toppest = false;
		underest = false;
		rightest = false;
		leftest = false;
		width = wid;
		height = hei;
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		toppest_pix = new int[width];
		underest_pix = new int[width];
		rightest_pix = new int[height];
		leftest_pix = new int[height];
		first_position = null;
		current_position = null;
		top_haming = under_haming = right_haming = left_haming = -1;
		
		top = null;
		under = null;
		right = null;
		left = null;
	}
	
	
	/////セッター/////
	public void setImage(int x, int y, int rgb){
		image.setRGB(x, y, rgb);
	}
	public void setImage(BufferedImage img){
		image = img;
	}
	public void setFlag(String str){
		if(str == "top"){
			toppest = true;
		}else if(str == "under"){
			underest = true;
		}else if(str == "right"){
			rightest = true;
		}else if(str == "left"){
			leftest = true;
		}
	}
	public void setRoundPix(){
		for(int i = 0; i < width; i++){
			toppest_pix[i] = image.getRGB(i, 0);
			underest_pix[i] = image.getRGB(i, height-1);
			
			//toppest_pixの正規化？
			r = toppest_pix[i]>>16&0xff;
			g = toppest_pix[i]>>8&0xff;
			b = toppest_pix[i]&0xff;
			if(r >= 0x00 && r <= 0x33){
				r = 0;
			}else if(r > 0x33 && r <= 0x66){
				r = 1;
			}else if(r > 0x66 & r <= 0x99){
				r = 2;
			}else if(r > 0x99 && r <= 0xcc){
				r = 3;
			}else if(r > 0xcc && r <= 0xff){
				r = 4;
			}else{
				r = 5;
			}
			
			if(g >= 0x00 && g <= 0x33){
				g = 0;
			}else if(g > 0x33 && g <= 0x66){
				g = 1;
			}else if(g > 0x66 & g <= 0x99){
				g = 2;
			}else if(g > 0x99 && g <= 0xcc){
				g = 3;
			}else if(g > 0xcc && g <= 0xff){
				g = 4;
			}else{
				g = 5;
			}
			
			if(b >= 0x00 && b <= 0x33){
				b = 0;
			}else if(b > 0x33 && b <= 0x66){
				b = 1;
			}else if(b > 0x66 & b <= 0x99){
				b = 2;
			}else if(b > 0x99 && b <= 0xcc){
				b = 3;
			}else if(b > 0xcc && b <= 0xff){
				b = 4;
			}else{
				b = 5;
			}
			
			toppest_pix[i] = r<<16 | g<<8 | b;
			
			//underest_pixの正規化？
			r = underest_pix[i]>>16&0xff;
			g = underest_pix[i]>>8&0xff;
			b = underest_pix[i]&0xff;
			if(r >= 0x00 && r <= 0x33){
				r = 0;
			}else if(r > 0x33 && r <= 0x66){
				r = 1;
			}else if(r > 0x66 & r <= 0x99){
				r = 2;
			}else if(r > 0x99 && r <= 0xcc){
				r = 3;
			}else if(r > 0xcc && r <= 0xff){
				r = 4;
			}else{
				r = 5;
			}
			
			if(g >= 0x00 && g <= 0x33){
				g = 0;
			}else if(g > 0x33 && g <= 0x66){
				g = 1;
			}else if(g > 0x66 & g <= 0x99){
				g = 2;
			}else if(g > 0x99 && g <= 0xcc){
				g = 3;
			}else if(g > 0xcc && g <= 0xff){
				g = 4;
			}else{
				g = 5;
			}
			
			if(b >= 0x00 && b <= 0x33){
				b = 0;
			}else if(b > 0x33 && b <= 0x66){
				b = 1;
			}else if(b > 0x66 & b <= 0x99){
				b = 2;
			}else if(b > 0x99 && b <= 0xcc){
				b = 3;
			}else if(b > 0xcc && b <= 0xff){
				b = 4;
			}else{
				b = 5;
			}
			
			underest_pix[i] = r<<16 | g<<8 | b;
			//System.out.println(Integer.toHexString(image.getRGB(i, 0)));
			//System.out.println(Integer.toHexString(toppest_pix[i]));
		}
		for(int i = 0; i < height; i++){
			rightest_pix[i] = image.getRGB(width-1, i);
			leftest_pix[i] = image.getRGB(0, i);
			
			//rightestの正規化？
			r = rightest_pix[i]>>16&0xff;
			g = rightest_pix[i]>>8&0xff;
			b = rightest_pix[i]&0xff;
			if(r >= 0x00 && r <= 0x33){
				r = 0;
			}else if(r > 0x33 && r <= 0x66){
				r = 1;
			}else if(r > 0x66 & r <= 0x99){
				r = 2;
			}else if(r > 0x99 && r <= 0xcc){
				r = 3;
			}else if(r > 0xcc && r <= 0xff){
				r = 4;
			}else{
				r = 5;
			}
		
			if(g >= 0x00 && g <= 0x33){
				g = 0;
			}else if(g > 0x33 && g <= 0x66){
				g = 1;
			}else if(g > 0x66 & g <= 0x99){
				g = 2;
			}else if(g > 0x99 && g <= 0xcc){
				g = 3;
			}else if(g > 0xcc && g <= 0xff){
				g = 4;
			}else{
				g = 5;
			}
		
			if(b >= 0x00 && b <= 0x33){
				b = 0;
			}else if(b > 0x33 && b <= 0x66){
				b = 1;
			}else if(b > 0x66 & b <= 0x99){
				b = 2;
			}else if(b > 0x99 && b <= 0xcc){
				b = 3;
			}else if(b > 0xcc && b <= 0xff){
				b = 4;
			}else{
				b = 5;
			}
			rightest_pix[i] = r<<16 | g<<8 | b;
			
			//leftestの正規化？
			r = leftest_pix[i]>>16&0xff;
			g = leftest_pix[i]>>8&0xff;
			b = leftest_pix[i]&0xff;
			if(r >= 0x00 && r <= 0x33){
				r = 0;
			}else if(r > 0x33 && r <= 0x66){
				r = 1;
			}else if(r > 0x66 & r <= 0x99){
				r = 2;
			}else if(r > 0x99 && r <= 0xcc){
				r = 3;
			}else if(r > 0xcc && r <= 0xff){
				r = 4;
			}else{
				r = 5;
			}
		
			if(g >= 0x00 && g <= 0x33){
				g = 0;
			}else if(g > 0x33 && g <= 0x66){
				g = 1;
			}else if(g > 0x66 & g <= 0x99){
				g = 2;
			}else if(g > 0x99 && g <= 0xcc){
				g = 3;
			}else if(g > 0xcc && g <= 0xff){
				g = 4;
			}else{
				g = 5;
			}
		
			if(b >= 0x00 && b <= 0x33){
				b = 0;
			}else if(b > 0x33 && b <= 0x66){
				b = 1;
			}else if(b > 0x66 & b <= 0x99){
				b = 2;
			}else if(b > 0x99 && b <= 0xcc){
				b = 3;
			}else if(b > 0xcc && b <= 0xff){
				b = 4;
			}else{
				b = 5;
			}
			leftest_pix[i] = r<<16 | g<<8 | b;
			//System.out.println(Integer.toHexString(image.getRGB(width-1, i)));
		}
	}
	public void setNumber(int num){
		pictnum = num;
	}
	public void setFirstPosition(String str){
		first_position = str;
	}
	public void setHaming(String mode, int data){
		if(mode == "top"){
			top_haming = data;
		}else if(mode == "under"){
			under_haming = data;
		}else if(mode == "right"){
			right_haming = data;
		}else if(mode == "left"){
			left_haming = data;
		}
	}
	public void setTop(Picture data){
		top = data;
	}
	public void setUnder(Picture data){
		under = data;
	}
	public void setRight(Picture data){
		right = data;
	}
	public void setLeft(Picture data){
		left = data;
	}
	
	/////ゲッター/////
	public BufferedImage getImage(){
		return image;
	}
	public int getRoundPix(String mode, int index){
		if(mode == "top"){
			//System.out.println(Integer.toHexString(toppest_pix[index]));
			return toppest_pix[index];
		}else if(mode == "under"){
			return underest_pix[index];
		}else if(mode == "right"){
			return rightest_pix[index];
		}else if(mode == "left"){
			return leftest_pix[index];
		}else{
			return 0;
		}
	}
	public int[] getRoundPix(String mode, int start, int end){
		int[] result = new int[end - start + 1];
		if(mode == "top"){
			for(int i = start, j = 0; i <= end; i++, j++){
				result[j] = toppest_pix[i];
			}
			return (int[])SerializationUtils.clone(result);
		}else if(mode == "under"){
			for(int i = start, j = 0; i <= end; i++, j++){
				result[j] = underest_pix[i];
			}
			return (int[])SerializationUtils.clone(result);
		}else if(mode == "right"){
			for(int i = start, j = 0; i <= end; i++, j++){
				result[j] = rightest_pix[i];
			}
			return (int[])SerializationUtils.clone(result);
		}else if(mode == "left"){
			for(int i = start, j = 0; i <= end; i++, j++){
				result[j] = leftest_pix[i];
			}
			return (int[])SerializationUtils.clone(result);
		}else{
			return null;
		}
	}
	public int[] getRoundPix(String mode){
		if(mode == "top"){
			return (int[])SerializationUtils.clone(toppest_pix);
		}else if(mode == "under"){
			return (int[])SerializationUtils.clone(underest_pix);
		}else if(mode == "right"){
			return (int[])SerializationUtils.clone(rightest_pix);
		}else if(mode == "left"){
			return (int[])SerializationUtils.clone(leftest_pix);
		}else{
			return null;
		}
	}
	public int getHaming(String mode){
		if(mode == "top"){
			return top_haming;
		}else if(mode == "under"){
			return under_haming;
		}else if(mode == "right"){
			return right_haming;
		}else if(mode == "left"){
			return left_haming;
		}else{
			return 0;
		}
	}
	public Picture getTop(){
		return top;
	}
	public Picture getUnder(){
		return under;
	}
	public Picture getRight(){
		return right;
	}
	public Picture getLeft(){
		return left;
	}
	public int getNumber(){
		return pictnum;
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public boolean getFlag(String mode){
		if(mode == "top"){
			return toppest;
		}else if(mode == "under"){
			return underest;
		}else if(mode == "right"){
			return rightest;
		}else if(mode == "left"){
			return leftest;
		}else{
			return false;
		}
	}
	public String getPosition(){
		return first_position;
	}
}
