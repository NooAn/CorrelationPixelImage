
import java.io.IOException;

public class Main extends Analysis {

	private static final int KOL_BMP = 11;
	private static final int KOL_JPEG = 22;
	private static final int KOL_GIF = 10;

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {
			bmp();
		//	jpeg();
			gif();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @throws IOException
	 */
	public static void gif() throws IOException {
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
		forJpeg(KOL_JPEG, METHOD.DIAGONAL_ONE);
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
