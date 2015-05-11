import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
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
		// Main.print(img.getRGB(width, height)); //Корреляция по компоненте
		// Blue
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
		int i = 0;
		int len = 0;
		if (height < width)
			len = height;
		else
			len = width;
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
		pw.println("Average Correlation " + index + " for image = " + c);
		return c;
	}

	public void close() {
		pw.close();
	}

	/**
	 * Функция возвращает последний бит пикселя (FF FF FF FF) компонетны Blue
	 * 
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

	public static void printHex(int num) {
		String hex = Long.toHexString(num);
		Main.print(hex);
	}

	public static void printLastByte(int num) {
		char s = toLastHexByte(num);
		Main.print(s);
	}

	public static void printBinary(int num) {
		Main.print(binaryByte(toLastHexByte(num)));
	}

	public static String binaryStringLastTwoBit(int num) {
		// num = pixel
		char s = toLastHexByte(num);
		String binarLastPixel = binaryByte(s); // байт в двоичном разложении
		char lastBit = binarLastPixel.charAt(2);
		char preLastBit = binarLastPixel.charAt(1);
		StringBuilder str = new StringBuilder();
		str.append(lastBit);
		str.append(preLastBit);
		// Main.print(str.toString());
		return str.toString();
	}

	private static String binaryByte(char s) {
		String lastHex = String.valueOf(s);
		int x = Integer.parseInt(lastHex, 16);// Перевод в 10ную.
		String binarLastPixel = Integer.toBinaryString(x);
		return binarLastPixel;
	}

	private double calculationOfCorrelation(ArrayList<Integer> bit) {
		/*
		 * Получаем массив значений. Теперь надо считать по формуле коэффициента
		 * линейной корреляции. С = (x-x')(y-y')/sqrt(((x-x')(x-x') *
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
		// pw.println(" cov= "+sumCovXY+" Sqrt = "+Math.sqrt((double)
		// (sumAverageY * sumAverageX))+" y = "+sumAverageY +" x ="+
		// sumAverageX);
		coefficientCorrelation = (sumCovXY / Math
				.sqrt((double) (sumAverageY * sumAverageX)));
		if (Double.isNaN(coefficientCorrelation))
			coefficientCorrelation = 0.1;
		return coefficientCorrelation;
	}

	public double calculation(ArrayList<Integer> x, ArrayList<Integer> y, int size, int k) {
		/*
		 * Получаем массивы значений. Теперь надо считать по формуле
		 * коэффициента линейной корреляции. 
		 * С = (x-x')(y-y')/sqrt(C(x-x')(x-x')
		 * * (y-y')(y-y')));
		 *  где x' = sum(x)/n; где n - количество эллементов. a
		 * sum(x) - function сумма по строке; y'=sum(y)/n;
		 */
		double coefficientCorrelation = 0;
		long sum_x = sumAverage(x);
		long sum_y = sumAverage(y);
		System.out.println ("Sum_X="+ sum_x + " Sum_y="+sum_y+"x="+x.size() );
		//BigInteger sumCovXY =  new BigInteger("0");
		long sumCovXY=0;
		long sumAverageX = 0;
		long sumAverageY = 0;
		for (int i = 1; i < size; i++) {
			long x2 = x.get(k*size+i);
			long y2 = y.get(k*size+i);
		//	sumCovXY.add();
			sumCovXY += ((x2 - sum_x) * (y2 - sum_y));
			sumAverageX += (x2 - sum_x) * (x2 - sum_x);
			sumAverageY += (y2 - sum_y) * (y2 - sum_y);
		}
		coefficientCorrelation = (sumCovXY / Math
				.sqrt((double) (sumAverageY * sumAverageX)));
		//System.out.println("SumCovXY = "+sumCovXY);
		//System.out.println ("SQRT = "+Math.sqrt((double) (sumAverageY * sumAverageX)));
		
		if (Double.isNaN(coefficientCorrelation))
			coefficientCorrelation = 0.1;
		return coefficientCorrelation;
	}
	public double calculation(ArrayList<Integer> x, ArrayList<Integer> y) {
		BigDecimal coefficientCorrelation;
		
		BigInteger sum_x = new BigInteger(bigSumAverage(x));
		BigInteger sum_y = new BigInteger(bigSumAverage(y));
		BigInteger sumCovXY= new BigInteger("0");
		BigInteger sumAverageX = new BigInteger("0");
		BigInteger sumAverageY = new BigInteger("0");
		
		for (int i=0; i<x.size();i++) {
			BigInteger x2 = new BigInteger (String.valueOf(x.get(i)));
			BigInteger y2 = new BigInteger (String.valueOf(y.get(i)));
		//	System.out.println(y2+" = "+x2);

			BigInteger buf = new BigInteger("0");
			buf = y2.subtract(sum_y);
			BigInteger buf2 = new BigInteger("0");
			buf2 = x2.subtract(sum_x);
			sumCovXY = sumCovXY.add(buf2.multiply(buf));
			
			sumAverageX = sumAverageX.add(x2.subtract(sum_x).pow(2));

			//sumAverageY = sumAverageY.add(sumAverageY);
			
			sumAverageY	= sumAverageY.add(y2.subtract(sum_y).pow(2));
			//System.out.println(y2+" "+sum_y);
			
		}
		BigDecimal cov = new BigDecimal(sumCovXY);
		BigDecimal sqrtSum = new BigDecimal(sqrt(sumAverageY.multiply(sumAverageX)));
		System.out.println(cov.doubleValue()/sqrtSum.doubleValue());
		
		//coefficientCorrelation = cov.divide(sqrtSum, BigDecimal.ROUND_FLOOR );
		//System.out.println(sumCovXY.divide(sqrt(sumAverageY.multiply(sumAverageX))).toString()+" = "+sumAverageY.multiply(sumAverageX));

		//System.out.println(coefficientCorrelation+" #");
		return cov.doubleValue()/sqrtSum.doubleValue();
		
	}
	private String bigSumAverage(ArrayList<Integer> a){
		BigInteger s =  new BigInteger("0");
		int i=0;
		for (Integer ar : a){
			//	s += ar;
			s = s.add(new BigInteger(String.valueOf(ar)));
		}
		//System.out.println("%"+String.valueOf(s.divide(new BigInteger ("6") )));
		return String.valueOf(s.divide(new BigInteger (String.valueOf(a.size()) ) ));

	//	return String.valueOf(s.divide (new BigInteger ("6")));
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
	public BigInteger sqrt(BigInteger N)
	{
	    BigInteger result=N.divide(BigInteger.ONE.add(BigInteger.ONE));
	    while(result.multiply(result).subtract(N).compareTo(BigInteger.ONE.divide(new BigInteger("100000000")))>0)
	        result=result.add(N.divide(result)).divide(BigInteger.ONE.add(BigInteger.ONE));
	    return result;
	}
}
