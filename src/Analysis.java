import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Analysis {

	private static BufferedImage img = null;
	/**
	 * @throws FileNotFoundException
	 */
	private static Correlation correlation;

	public static void startThreeMethods(String name, BufferedImage img) {
		correlation = new Correlation();
		try {
			System.out.println("Метод по диагонали\nCoeficient ="+methodForDiagonalOneString(img,name));
			System.out.println("Метод по столбцу\nCoeficient ="+methodForOneColumn(img, name));
			System.out.println("Метод по строке\nCoeficient ="+methodForOneString(img, name));
		} catch(Exception e){
			e.printStackTrace();
		} 
	}
	public static double methodForOneString(BufferedImage img, String index) {
		int height = img.getHeight();
		int width = img.getWidth();
		double sumAverageCorrelation = 0;
		for (int i = 0; i < height; i++) {
			ArrayList<Integer> bit = new ArrayList<>();
			for (int j = 0; j < width; j++) {
				int pix = img.getRGB(j, i);
				bit.add(correlation.convertInt(pix));
			}
			double d = correlation.calculationOfCorrelation(bit);
			sumAverageCorrelation += d;
		}
	//	Main.println("Average Correlation " + index + " image = "
		//		+ sumAverageCorrelation / height);

		return sumAverageCorrelation / height;
	}

	public static double methodForDiagonalOneString(BufferedImage img, String index) {
		int height = img.getHeight();
		int width = img.getWidth();
		double sumAverageCorrelation = 0;
		ArrayList<Integer> bit = new ArrayList<>();
		int pix = 0;
		int i = 0;
		int len = 0;
		if (height < width)
			len = height;
		else
			len = width;
		for (i = 0; i < len; i++) {
			pix = img.getRGB(i, i);
			bit.add(correlation.convertInt(pix));
		}
		double d = correlation.calculationOfCorrelation(bit);
		sumAverageCorrelation += d;
		return sumAverageCorrelation;
	}

	public static double methodForOneColumn(BufferedImage img, String index) {
		int height = img.getHeight();
		int width = img.getWidth();
		double sumAverageCorrelation = 0;
		for (int i = 0; i < width; i++) {
			ArrayList<Integer> bit = new ArrayList<>();
			for (int j = 0; j < height; j++) {
				int pix = img.getRGB(i, j);
				bit.add(Correlation.convertInt(pix));
			}
			double d = correlation.calculationOfCorrelation(bit);
			sumAverageCorrelation += d;

		}
		double c = sumAverageCorrelation / (width);
		return c;
	}

	/**
	 * 
	 * @param outFileName
	 *            файл для сохранения значений
	 * @param folder
	 *            папка где расположена картинка и куда сохранится файл
	 * @param method
	 * @throws IOException
	 */
	private static void calculation(String folder,
			METHOD method) throws IOException {

		double sumAverageImage = 0;

		FileDirectory file = new FileDirectory();
		ArrayList<String> listName = new ArrayList<>();
		listName = file.get(folder);
		int kolImage = 0;

		for (String name : listName) {

			if ( name.charAt(0) == '!') continue;
			Main.print(folder + name);
			img = ImageIO.read(new File(folder + name));
			switch (method) {
			case DIAGONAL_ONE:
				sumAverageImage += methodForDiagonalOneString(img,name);
				break;
			case STRING_ONE:
				sumAverageImage += methodForOneString(img, name);
				break;
			case COLUMN_ONE:
				sumAverageImage += methodForOneColumn(img, name);
			default:
			}
			kolImage++;

		}
		System.out.print("Average coefficient from " + kolImage
				+ " images = " + sumAverageImage / kolImage);
	}





}