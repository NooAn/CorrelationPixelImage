import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Utils {
	/*
	 * Функция для сохранения последних бит в текстовом файле.
	 */
	public static void saveTxtLastBitAllPicture() {
		FileDirectory file = new FileDirectory();
		String folder = "Android/PocketStego/";
		ArrayList<String> listName = new ArrayList<>();
		try {
			listName = file.get(folder);
			for (String name : listName) { 
				Main.println(name);
				saveTxtOnlyLastBit(name, folder);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("resource")
	protected static void saveTxtOnlyLastBit(String name, String folder)
			throws IOException {

		PrintWriter pw;
		pw = new PrintWriter(new File("testEm.txt"));
		BufferedImage img = null;
		img = ImageIO.read(new File("testEm.png"));

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
	public void saveLsbInTxt(BufferedImage img, String name) throws FileNotFoundException{
		int height = img.getHeight();
		int width = img.getWidth();
		PrintWriter pw;
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
	public static void savePicureJpeg(BufferedImage image, String name)
			throws IOException {
		ImageIO.write(image, "jpg", new File(name));
	}

	public static void savePicurePng(BufferedImage image, String name)
			throws IOException {
		ImageIO.write(image, "png", new File(name));
	}

	protected static void saveJpegOnlyLastBlueBit(String folder, String name) throws IOException {
		BufferedImage img = null;
		img = ImageIO.read(new File(folder+"/"+name));
		int height = img.getHeight();
		int width = img.getWidth();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int pix = img.getRGB(i, j);
				if (Correlation.convertInt(pix) != 0)
					img.setRGB(i, j, 0);
				else
					img.setRGB(i, j,  0b000011110000000000000000001111 ); // Изменяем цвет в зависимости от
											// младшего бита!
			}
		}
	//	savePicureJpeg(img, "testJpeg"+name);
		savePicurePng(img, "test"+name);
	}
}
