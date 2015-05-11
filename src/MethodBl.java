import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class MethodBl {
	public static void checkMethodCorrelation() {
		String folder="test_png/";
		String name="rope2.png";
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(folder + name));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<Integer> listNumb = new ArrayList<Integer>();
		listNumb = getList(img);
		ArrayList<Integer> listRank = new ArrayList<Integer>();
		listRank = getListRankTwo(img);
		//for ( int i=0;i<5;i++) 
		System.out.println ("Coeficient = " + new Correlation().calculation(listNumb, listRank ));
		
	}
	public static ArrayList<Integer> getList(BufferedImage img) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for ( int i=0;i<img.getHeight();i++){
			for ( int j=0;j<img.getWidth();j++) {
				list.add(Math.abs(img.getRGB(j, i)));
			}
		}
		return list;
	}
	public static ArrayList<Integer> getListRankTwo(BufferedImage img) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for ( int i=0;i<img.getHeight();i++) {
			for ( int j=0;j<img.getWidth();j++){
				list.add(getNumberRed(img.getRGB(j, i)));
				//System.out.println(img.getRGB(j, i);
			}
		}
		return list;
	}
	public static int getNumberRed(int rgb) {
		int b = (rgb & 0x000000FF) >>> 0;
		String s = Integer.toBinaryString(b);
		while (s.length()!=8 || s.length()>10) {
			s = "0"+s;
		}
		String s2 = s.substring(6, 8);
		int n = Integer.parseInt(s2, 2);
		//System.out.println("RGD= "+rgb+" S = "+s+" N = "+n+ " S2 ="+s2);
		return b;
	}
	
	public void rgb (BufferedImage image, int x, int y) {
		int rgb = image.getRGB(x, y);

		int o = (rgb & 0xFF000000) >>> 24;
		int r = (rgb & 0x00FF0000) >>> 16;
		int g = (rgb & 0x0000FF00) >>> 8;
		int b = (rgb & 0x000000FF) >>> 0;

		if (o != 0xFF) {
			System.out.println("���! " + o);
		}
		if (r != g && g != b) {
			System.out.println("�������� �� grayscale. " + r + " " + g + " " + b);
		}
		//��� ���������.
		image.setRGB(x, y, o << 24 | r << 16 | g << 8 | b);
	}
}
