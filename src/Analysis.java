import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Analysis {
	/**
	 * @throws FileNotFoundException
	 */
	private static final int KOL_IMAGE_JPEG = 21;
	private static final int KOL_IMAGE_BMP=2;
	private static Correlation correlation;
	public static void forJpeg(int kolImage, METHOD method) throws IOException{
			calculationJpeg("OutFileMethod"+method.getValue()+"ForJPEG.txt", kolImage, method);	
	}
	public static void forBmp(int kolImage, METHOD method) throws IOException {
		calculationBmp("OutFileMethod"+method.getValue()+"ForBmp.txt", kolImage,method);
}
	private static void calculationJpeg(String string, int kolImage, METHOD method) throws IOException {
		calculation(string, kolImage, "image_jpg/", ".jpg", method);	
	}
	private static void calculationBmp(String string, int kolImage, METHOD method) throws IOException {
		calculation(string, kolImage, "image_bmp/", ".bmp", method);	
	}

	private static void calculation(String outFileName, int kolImage,String folder, String formatImage, METHOD method) throws IOException {		
		correlation = new Correlation();
		double sumAverageImage = 0;
		correlation.openFileOutput(outFileName);
		for (int k = 0; k < kolImage; k++) {
			BufferedImage img = null;
			img = ImageIO.read(new File(folder+ k +formatImage));
			switch(method) {
			case DIAGONAL_ONE:	
				sumAverageImage += correlation.methodForDiagonalOneString(img, k);
				break;
			case STRING_ONE:
				sumAverageImage += correlation.methodForOneString(img, k);
				break;
			case COLUMN_ONE:
				sumAverageImage+=correlation.methodForOneColumn(img, k);
				default:
			}
			
		}
		correlation.printFile("Average coefficient from "+kolImage+" images = "+sumAverageImage/kolImage);
		correlation.close();
	}
	



}