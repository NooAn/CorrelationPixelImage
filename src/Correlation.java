import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.function.BinaryOperator;

public class Correlation {
	private PrintWriter pw;

	public void openFileOutput(String str) throws FileNotFoundException {
		pw = new PrintWriter(new File(str));
	}

	public double methodForOneString(BufferedImage img, String index) {
		int height = img.getHeight();
		int width = img.getWidth();
		double sumAverageCorrelation = 0;
		Main.println("Processing Image " + index + "  Height = " + height
				+ " Width= " + width + "");
		//Main.print(img.getRGB(width, height));
		for (int i = 0; i < height; i++) {
			ArrayList<Integer> bit = new ArrayList<>();
			for (int j = 0; j < width; j++) {
				int pix = img.getRGB(j, i);
				bit.add(convertInt(pix));
			}
			double d = calculationOfCorrelation(bit);
			sumAverageCorrelation += d;
		}
		pw.println("Average Correlation " + index + " image = "
				+ sumAverageCorrelation / height);

		return sumAverageCorrelation / height;
	}

	public double methodForDiagonalOneString(BufferedImage img, String index) {
		int height = img.getHeight();
		int width = img.getWidth();
		double sumAverageCorrelation = 0;
		Main.println("Processing Image " + index + "  Height = " + height
				+ " Width= " + width + "");
		ArrayList<Integer> bit = new ArrayList<>();
		int pix = 0;
		int i=0;
		int len = 0;
		if ( height<width) len = height; else len = width; 
		for (i = 0; i < len; i++) {
			pix = img.getRGB(i, i);
			bit.add(convertInt(pix));
		}
		double d = calculationOfCorrelation(bit);
		sumAverageCorrelation += d;
		pw.println("Average Correlation for " + index + " for image = "
				+ sumAverageCorrelation);
		return sumAverageCorrelation;
	}
	public double methodForOneColumn(BufferedImage img, String index) {
		int height = img.getHeight();
		int width = img.getWidth();
		double sumAverageCorrelation = 0;
		Main.println("Processing Image " + index + "  Height = " + height
				+ " Width= " + width + "");
		for (int i = 0; i < width; i++) {
			ArrayList<Integer> bit = new ArrayList<>();
			for (int j = 0; j < height; j++) {
				int pix = img.getRGB(i, j);
				bit.add(convertInt(pix));
			}
			double d = calculationOfCorrelation(bit);
			sumAverageCorrelation += d;

		}
		double c = sumAverageCorrelation / (width);
		pw.println("Average Correlation " + index + " for image = "
				+ c);
		return c;
	}

	public void close() {
		pw.close();
	}
/**
 * Функция возвращает последний бит пикселя
 * @param pix
 * @return int last bit
 */
	public static int convertInt(int pix) {
		return Integer.valueOf(convertLastBit(pix));
	}

	public static String convertLastBit(int pix) {
		char s = toLastHexByte(pix);
		String binarLastPixel = binaryByte(s); // байт в двоичном разложении
		char lastBit = binarLastPixel.charAt(binarLastPixel.length() - 1);
		return String.valueOf(lastBit);
	}

	/**
	 * @param pix
	 * @return
	 */
	public static char toLastHexByte(int pix) {
		String hex = Long.toHexString(pix);// Переводим из 10 в 16
		char s = hex.charAt(hex.length() - 1); // Берем последний байт.		
		return s;
	}
	public static void printBit(int num) {
		
	}
	public static void printHex(int num){
		String hex = Long.toHexString(num);
		Main.print(hex);
	}
	public static void printLastByte(int num){
		char s = toLastHexByte(num);
		Main.print(s);
	}
	public static void printBinary(int num){
		Main.print(binaryByte(toLastHexByte(num)));
	}
	public static String binaryStringLastTwoBit(int num ) {
		//num = pixel
		char s = toLastHexByte(num);
		String binarLastPixel = binaryByte(s); // байт в двоичном разложении
		char lastBit = binarLastPixel.charAt(2);	
		char preLastBit = binarLastPixel.charAt(1);
		StringBuilder str = new StringBuilder();
		str.append(lastBit);
		str.append(preLastBit);
		//Main.print(str.toString());
		return str.toString();
	}
	private static String binaryByte(char s){
		String lastHex = String.valueOf(s);
		int x = Integer.parseInt(lastHex, 16);//Перевод в 10ную.
		String binarLastPixel = Integer.toBinaryString(x);
		return binarLastPixel;
	}
	 

	private double calculationOfCorrelation(ArrayList<Integer> bit) {
		/*
		 * Получаем массив значений. Теперь надо считать по формуле коэффициента
		 * линейной корреляции. С = (x-x')(y-y')/sqrt(C(x-x')(x-x') *
		 * (y-y')(y-y'))); где x' = sum(x)/n; где n - количество эллементов. a
		 * sum(x) - function сумма по строке; y'=sum(y)/n;
		 */
		double coefficientCorrelation = 0;
		int length = bit.size();
		int sum_x = sumAverage(bit) - bit.get(0);
		int sum_y = sumAverage(bit) - bit.get(length - 1);
		int sumCovXY = 0;
		int sumAverageX = 0;
		int sumAverageY = 0;
		for (int i = 1; i < length; i++) {
			int x = bit.get(i);
			int y = bit.get(i - 1);
			sumCovXY += (x - sum_x) * (y - sum_y);
			sumAverageX += (x - sum_x) * (x - sum_x);
			sumAverageY += (y - sum_y) * (y - sum_y);
		}
		//pw.println(" cov= "+sumCovXY+" Sqrt = "+Math.sqrt((double) (sumAverageY * sumAverageX))+" y = "+sumAverageY +" x ="+ sumAverageX);
		coefficientCorrelation = (sumCovXY / Math
				.sqrt((double) (sumAverageY * sumAverageX)));
		if ( Double.isNaN(coefficientCorrelation)) coefficientCorrelation=0.1;
		return coefficientCorrelation;
	}

	private int sumAverage(ArrayList<Integer> a) {
		int s = 0;
		for (Integer ar : a)
			s += ar;
		return s / a.size();
	}

	public void printFile(String string) {
		pw.println(string);
	}

}
