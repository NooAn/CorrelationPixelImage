import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;


public class FileTxt extends Analysis {
	private BufferedImage img;
	private String name;
	private PrintWriter pw;
	FileTxt(String name){
		this.name = name;
	}
	public void jpeg() throws IOException{ 
		System.out.print(Analysis.FOLDER_JPEG+name+".jpeg");
		img = ImageIO.read(new File(FOLDER_JPEG+name+".jpg"));
	}
	public void bmp() throws IOException {
		img = ImageIO.read(new File(FOLDER_BMP + name+".bmp"));
	}
	public void png () throws IOException {
		img = ImageIO.read(new File(FOLDER_PNG+name+".png"));
	}
	public void gif() throws IOException {
		img = ImageIO.read(new File(FOLDER_GIF+name+".gif"));
	}
	public void stegoJpeg() throws IOException {
		System.out.println(STEGO_FOLDER_JPG+name+".jpg");
		img = ImageIO.read(new File(STEGO_FOLDER_JPG+name+".jpg"));
	}
	public void save() throws FileNotFoundException{
		int height = img.getHeight();
		int width = img.getWidth();
		
		pw = new PrintWriter(new File(name+".txt"));
		for ( int i=0;i<width;i++){ 
			for (int j=0;j<height; j++) {
				int pix = img.getRGB(i, j);
				pw.print(Correlation.convertInt(pix)+" ");
			}
			pw.println();
		}
		pw.close();
	}
	
}
