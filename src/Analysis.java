import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Analysis {
	/**
	 * @throws FileNotFoundException
	 */
	private static Correlation correlation;

	public static void forJpeg(int kolImage, METHOD method) throws IOException {
		calculationJpeg("Out File For JPEG Method " + method.getValue() + ".txt",
				kolImage, method);
	}
	public static void forJpeg(int kolImage, METHOD method, boolean stego) throws IOException {
		calculation("Out File For Stego JPEG Method " + method.getValue() + ".txt",
				kolImage,"stego_image_jpg/",".jpg", method);
	}
	public static void forBmp(int kolImage, METHOD method) throws IOException {
		calculationBmp("Out File For Bmp Method" + method.getValue() + ".txt",
				kolImage, method);
	}
	public static void forGif(int kolImage, METHOD method) throws IOException {
		calculationGif("Out File For Gif Method "+method.getValue()+".txt",kolImage, method);
	}

	private static void calculationGif(String string, int kolImage,
			METHOD method) throws IOException {
		calculation(string, kolImage, "image_gif/", ".gif" ,method);
		
	}

	private static void calculationJpeg(String string, int kolImage,
			METHOD method) throws IOException {
		calculation(string, kolImage, "image_jpg/", ".jpg", method);
	}

	private static void calculationBmp(String string, int kolImage,
			METHOD method) throws IOException {
		calculation(string, kolImage, "image_bmp/", ".bmp", method);
	}

	private static void calculation(String outFileName, int kolImage,
			String folder, String formatImage, METHOD method)
			throws IOException {
		correlation = new Correlation();
		double sumAverageImage = 0;
		correlation.openFileOutput(outFileName);
		for (int k = 0; k < kolImage; k++) {
			BufferedImage img = null;
			Main.print(folder + k + formatImage);
			img = ImageIO.read(new File(folder + k + formatImage));
			switch (method) {
			case DIAGONAL_ONE:
				sumAverageImage += correlation.methodForDiagonalOneString(img,k);
				break;
			case STRING_ONE:
				sumAverageImage += correlation.methodForOneString(img, k);
				break;
			case COLUMN_ONE:
				sumAverageImage += correlation.methodForOneColumn(img, k);
			default:
			}

		}
		correlation.printFile("Average coefficient from " + kolImage
				+ " images = " + sumAverageImage / kolImage);
		correlation.close();
	}

}