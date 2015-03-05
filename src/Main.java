
import java.io.IOException;

public class Main extends Analysis {

	private static final int KOL_BMP = 11;
	private static final int KOL_JPEG = 22;
	private static final int KOL_GIF = 10;
	private static final int KOL_STEGO_JPEG = 4;
	private static final boolean STEGO = true;

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {
		//	bmp();
		//	jpeg();
		//	gif();
			jpegStego();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void jpegStego() throws IOException {
		println("String method : ");
		forJpeg(KOL_STEGO_JPEG,METHOD.STRING_ONE, STEGO);
		println("Diagonal method : ");
		forJpeg(KOL_STEGO_JPEG, METHOD.DIAGONAL_ONE, STEGO);
		println("column one method");
		forJpeg(KOL_STEGO_JPEG, METHOD.COLUMN_ONE, STEGO);
		
	}

	/**
	 * @throws IOException
	 */
	public static void gif() throws IOException {
		println("column one method");
		forGif(KOL_GIF, METHOD.COLUMN_ONE);
		forGif(KOL_GIF,METHOD.DIAGONAL_ONE);		
		forGif(KOL_GIF,METHOD.STRING_ONE);
	}

	/**
	 * @throws IOException
	 */
	public static void bmp() throws IOException {
		forBmp(KOL_BMP, METHOD.DIAGONAL_ONE);
		forBmp(KOL_BMP, METHOD.STRING_ONE);
		forBmp(KOL_BMP, METHOD.COLUMN_ONE);
	}

	/**
	 * @throws IOException
	 */
	public static void jpeg() throws IOException {
		forJpeg(KOL_JPEG,METHOD.STRING_ONE);
		println("Diagonal one method");
		forJpeg(KOL_JPEG, METHOD.DIAGONAL_ONE);
		println("column one method");
		forJpeg(KOL_JPEG, METHOD.COLUMN_ONE);
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

}
