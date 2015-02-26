import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Method {
	/**
	 * @throws FileNotFoundException
	 */
	private static final int KOL_IMAGE_JPEG = 21;
	private static final int KOL_IMAGE_BMP=2;
	private static Correlation correlation;
	public static void forJpeg(int kolImage){
		try {
			calculation("OutFileMethodForJPEG.txt", kolImage, "image_jpg/", ".jpg");
		} catch (IOException e) {
			e.printStackTrace();
			correlation.printFile("Error"+e);
			correlation.close();
		}
		
	}
	public static void forBmp(int kolImage) {
		try {
			calculation("OutFileMethodForBmp.txt", kolImage, "image_bmp/", ".bmp");
		} catch (IOException e) {
			correlation.printFile("Error"+e);
			correlation.close();
			e.printStackTrace();
		}
	}
	private static void calculation(String outFileName, int kolImage,String folder, String formatImage) throws IOException {		
		correlation = new Correlation();
		double sumAverageImage = 0;
		correlation.openFileOutput(outFileName);
		for (int k = 0; k < kolImage; k++) {
			BufferedImage img = null;
			img = ImageIO.read(new File(folder+ k +formatImage));
			sumAverageImage += correlation.methodForString(img, k);
		}
		correlation.printFile("Average coefficient from "+kolImage+" images = "+sumAverageImage/kolImage);
		correlation.close();
	}
	



}