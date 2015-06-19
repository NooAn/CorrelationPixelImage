import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Inversion {
	private BufferedImage img;
	public Inversion(BufferedImage img) {
		this.img = img;
	}
	public void save () throws Exception {
		BufferedImage newImage = new BufferedImage(img.getWidth(),
				img.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		newImage.getGraphics().drawImage(img, 0, 0, null);
		for ( int i=0;i<img.getHeight();i++) {
			for ( int j=0;j<img.getWidth();j++) {
				newImage.setRGB(j, i, inverseBit(img.getRGB(j, i)));
			}
		}
		new Utils().savePicurePng(newImage,"inverse");
	}
	private int inverseBit(int rgb) throws Exception {
		int o = (rgb & 0xFF000000) >>> 24;
		int r = (rgb & 0x00FF0000) >>> 16;
		int g = (rgb & 0x0000FF00) >>> 8;
		int b = (rgb & 0x000000FF) >>> 0;
		
		String strRedInv = new StringBuffer(Integer.toBinaryString(r)).reverse().toString();
		String strGreenInv = new StringBuffer(Integer.toBinaryString(g)).reverse().toString();
		String strBlueInv = new StringBuffer(Integer.toBinaryString(b)).reverse().toString();
		
		strRedInv = corector(strRedInv);
		strGreenInv = corector(strGreenInv);
		strBlueInv = corector(strBlueInv);
		
		r =Integer.parseInt(strRedInv, 2);
		g =Integer.parseInt(strGreenInv, 2);
		b = Integer.parseInt(strBlueInv, 2);
		//System.out.println("rgb"+ (o << 24 | r << 16 | g << 8 | b)+" "+ r + " "+g +" "+ b);// перевод r g and b
		
		return o << 24 | r << 16 | g << 8 | b;
	}
	private String corector(String s) {
		while (s.length() != 8) {
			s = s+"0";
		}
		return s;
	}

}
