import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Analysis {
	public static final String FOLDER_BMP = "image_bmp/";
	public static final String FOLDER_GIF = "image_gif/";
	public static final String FOLDER_JPEG = "image_jpg/";
	public static final String FOLDER_PNG = "image_png/";
	public static final String STEGO_FOLDER_JPG = "stego_image_jpg/";
	private static BufferedImage img = null;
	/**
	 * @throws FileNotFoundException
	 */
	private static Correlation correlation;

	public static void forJpeg(METHOD method) throws IOException {
		calculationJpeg("Out File For JPEG Method " + method.getValue()
				+ ".txt", method);
	}

	public static void forJpeg(METHOD method, boolean stego) throws IOException {
		calculation("Out File For Stego JPEG Method " + method.getValue()
				+ ".txt", "stego_image_jpg/", method);
	}

	public static void forBmp(int kolImage, METHOD method) throws IOException {
		calculationBmp("Out File For Bmp Method" + method.getValue() + ".txt",
				kolImage, method);
	}

	public static void forGif(int kolImage, METHOD method) throws IOException {
		calculationGif("Out File For Gif Method " + method.getValue() + ".txt",
				kolImage, method);
	}

	public static void forPng(String folder) throws IOException {
		String outFileName = "Out File For Gif Method ";
		calculation(outFileName + METHOD.DIAGONAL_ONE + ".txt", folder,
				METHOD.DIAGONAL_ONE);
		calculation(outFileName + METHOD.STRING_ONE + ".txt", folder,
				METHOD.STRING_ONE);
		calculation(outFileName + METHOD.COLUMN_ONE + ".txt", folder,
				METHOD.COLUMN_ONE);
	}

	private static void calculationGif(String string, int kolImage,
			METHOD method) throws IOException {
		calculation(string, FOLDER_GIF, method);

	}

	private static void calculationJpeg(String string, METHOD method)
			throws IOException {
		calculation(string, FOLDER_JPEG, method);
	}

	private static void calculationBmp(String string, int kolImage,
			METHOD method) throws IOException {
		calculation(string, FOLDER_BMP, method);
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
	private static void calculation(String outFileName, String folder,
			METHOD method) throws IOException {

		correlation = new Correlation();
		double sumAverageImage = 0;
		correlation.openFileOutput(folder+"!txt_correlation/"+ outFileName);

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
				sumAverageImage += correlation.methodForDiagonalOneString(img,name);
				break;
			case STRING_ONE:
				sumAverageImage += correlation.methodForOneString(img, name);
				break;
			case COLUMN_ONE:
				sumAverageImage += correlation.methodForOneColumn(img, name);
			default:
			}
			kolImage++;

		}
		correlation.printFile("Average coefficient from " + kolImage
				+ " images = " + sumAverageImage / kolImage);
		correlation.close();
	}

	protected static void saveJpegOnlyLastBit(String name) throws IOException {
		BufferedImage img = null;
		img = ImageIO.read(new File(name));
		int height = img.getHeight();
		int width = img.getWidth();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int pix = img.getRGB(i, j);
				if (Correlation.convertInt(pix) == 0)
					img.setRGB(i, j, 65534);
				else
					img.setRGB(i, j, 0); // Изменяем цвет в зависимости от
											// младшего бита!
			}
		}
		savePicureJpeg(img, "LastBIT" + name);
	}

	public static void savePicture(String format, BufferedImage image,
			String name) throws IOException {
		ImageIO.write(image, format, new File(name));
	}

	public static void savePicureJpeg(BufferedImage image, String name)
			throws IOException {
		ImageIO.write(image, "jpg", new File(name));
	}

	public static void savePicurePng(BufferedImage image, String name)
			throws IOException {
		ImageIO.write(image, "png", new File(name));
	}

	@SuppressWarnings("resource")
	protected static void saveTxtOnlyLastBit(String name, String folder)
			throws IOException {

		PrintWriter pw;
		pw = new PrintWriter(new File("test_png/test1.txt"));
		BufferedImage img = null;
		img = ImageIO.read(new File("test_png/test1.png"));

		Main.print("Prossecing... into " + folder + name);

		int height = img.getHeight();
		int width = img.getWidth();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int pix = img.getRGB(i, j);
				int bit = Correlation.convertInt(pix); // Возвращает послед бит
				pw.print(bit);
			}
			pw.println();
		}
		pw.flush();
		if (pw.checkError())
			pw.print("Error!");
		pw.close();
	}
}