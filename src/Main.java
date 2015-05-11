import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Main extends Analysis {

	private static final int KOL_BMP = 11;
	private static final int KOL_JPEG = 2;
	private static final int KOL_GIF = 10;
	private static final int KOL_STEGO_JPEG = 4;
	private static final boolean STEGO = true;

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		// try {
		// println("Start");
		// // bmp();
//		 jpeg();
		//// gif();
		//// jpegStego();
		// saveJpegOnlyLastBlueBit("qw.jpg");
		// } catch (IOException e) {
		// 		e.printStackTrace();
		// }
//	saveTxtOnlyLastBit("2", "2");
//		replaceLastBitTwoImage();
		//pngCorrelationAll();
        
     //   MD5 md5 = new MD5();                               
		new MethodBl().checkMethodCorrelation();
      //  System.out.println(md5.getHash("qwert123")); 
	}

	/**
	 * @throws IOException
	 */
	
	public static void replaceLastBitTwoImage() throws IOException {
		Replace replace = new Replace() ;
		String nameOld="test1.png";
		String folder="test_png/";
		String nameNew="test1blc.png";
		Main.print(folder + nameOld);
		BufferedImage img = ImageIO.read(new File(folder + nameOld));
		BufferedImage imageNew = ImageIO.read(new File(folder + nameNew));
		replace.lastTwoBit(imageNew,replace.byteList(img) );
	}

	private static void jpegStego() throws IOException {
		println("String method : ");
		forJpeg(METHOD.STRING_ONE, STEGO);
		println("Diagonal method : ");
		forJpeg(METHOD.DIAGONAL_ONE, STEGO);
		println("column one method");
		forJpeg(METHOD.COLUMN_ONE, STEGO);
	}

	/**
	 * @throws IOException
	 */
	public static void gif() throws IOException {
		println("column one method");
		forGif(KOL_GIF, METHOD.COLUMN_ONE);
		forGif(KOL_GIF, METHOD.DIAGONAL_ONE);
		forGif(KOL_GIF, METHOD.STRING_ONE);
	}

	/**
	 * @throws IOException
	 */
	public static void bmp() throws IOException {
		forBmp(KOL_BMP, METHOD.DIAGONAL_ONE);
		forBmp(KOL_BMP, METHOD.STRING_ONE);
		forBmp(KOL_BMP, METHOD.COLUMN_ONE);
	}
	public static void pngCorrelationAll() throws IOException {
		forPng("test_png/");
	}

	/**
	 * @throws IOException
	 */
	public static void jpeg() throws IOException {
		forJpeg(METHOD.STRING_ONE);
		println("Diagonal one method");
		forJpeg(METHOD.DIAGONAL_ONE);
		println("column one method");
		forJpeg(METHOD.COLUMN_ONE);
	}

	public static void print(Object arg) {
		System.out.print(arg + " ");
	}

	public static void println(Object arg) {
		System.out.println(arg + " ");
	}

	public static void printf(int x) {
		System.out.printf("Value: %x \n", x);
	}
	public static void saveTxtLastBitAllPicture() {
		FileDirectory file = new FileDirectory();
		String folder = "Android/PocketStego/";
		ArrayList<String> listName = new ArrayList<>();
		try {
			listName = file.get(folder);
			for (String name : listName) { 
				println(name);
				saveTxtOnlyLastBit(name, folder);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
