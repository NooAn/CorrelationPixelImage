
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main extends Analysis {

	private static final int KOL_BMP = 11;
	private static final int KOL_JPEG = 21;

	@SuppressWarnings("resource")
	public static void main(String[] args) {

		//forJpeg(21);
		try {
//			forBmp(11, METHOD.DIAGONAL_ONE);
//		    forJpeg(21, METHOD.DIAGONAL_ONE);
//			forBmp(11, METHOD.STRING_ONE);
//			forJpeg(21,METHOD.STRING_ONE);
			forBmp(KOL_BMP, METHOD.COLUMN_ONE);
			forJpeg(KOL_JPEG, METHOD.COLUMN_ONE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
