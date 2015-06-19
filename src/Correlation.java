import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.function.BinaryOperator;

public class Correlation {


	/**
	 * Функция возвращает последний бит пикселя (FF FF FF FF) компонетны Blue
	 * 
	 * @param pix
	 * @return int last bit
	 */
	public static int convertInt(int pix) {
		return Integer.valueOf(convertLastBit(pix));
	}

	private static String convertLastBit(int pix) {
		char s = toLastHexByte(pix);
		String binarLastPixel = binaryByte(s); // байт в двоичном разложении
		char lastBit = binarLastPixel.charAt(binarLastPixel.length() - 1);
		return String.valueOf(lastBit);
	}

	/**
	 * @param pix
	 * @return
	 */
	private static char toLastHexByte(int pix) {
		String hex = Long.toHexString(pix);// Переводим из 10 в 16
		char s = hex.charAt(hex.length() - 1); // Берем последний байт.
		return s;
	}

	private static void printHex(int num) {
		String hex = Long.toHexString(num);
		Main.print(hex);
	}

	private static void printLastByte(int num) {
		char s = toLastHexByte(num);
		Main.print(s);
	}

	private static void printBinary(int num) {
		Main.print(binaryByte(toLastHexByte(num)));
	}

	public static String binaryStringLastTwoBit(int num) {
		char s = toLastHexByte(num);
		String binarLastPixel = binaryByte(s); // байт в двоичном разложении
		char lastBit = binarLastPixel.charAt(2);
		char preLastBit = binarLastPixel.charAt(1);
		StringBuilder str = new StringBuilder();
		str.append(lastBit);
		str.append(preLastBit);
		return str.toString();
	}

	private static String binaryByte(char s) {
		String lastHex = String.valueOf(s);
		int x = Integer.parseInt(lastHex, 16);// Перевод в 10ную.
		String binarLastPixel = Integer.toBinaryString(x);
		return binarLastPixel;
	}

	public double calculationOfCorrelation(ArrayList<Integer> bit) {
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
		coefficientCorrelation = (sumCovXY / Math
				.sqrt((double) (sumAverageY * sumAverageX)));
		if (Double.isNaN(coefficientCorrelation))
			coefficientCorrelation = 0.1;
		return coefficientCorrelation;
	}

	public double calculation(ArrayList<Integer> x, ArrayList<Integer> y,
			int size, int k) {
		/*
		 * Получаем массивы значений. Теперь надо считать по формуле
		 * коэффициента линейной корреляции. С = (x-x')(y-y')/sqrt(C(x-x')(x-x')
		 * * (y-y')(y-y'))); где x' = sum(x)/n; где n - количество эллементов. a
		 * sum(x) - function сумма по строке; y'=sum(y)/n;
		 */
		double coefficientCorrelation = 0;
		long sum_x = sumAverage(x);
		long sum_y = sumAverage(y);
		long sumCovXY = 0;
		long sumAverageX = 0;
		long sumAverageY = 0;
		for (int i = 1; i < size; i++) {
			long x2 = x.get(k * size + i);
			long y2 = y.get(k * size + i);
			sumCovXY += ((x2 - sum_x) * (y2 - sum_y));
			sumAverageX += (x2 - sum_x) * (x2 - sum_x);
			sumAverageY += (y2 - sum_y) * (y2 - sum_y);
		}
		coefficientCorrelation = (sumCovXY / Math
				.sqrt((double) (sumAverageY * sumAverageX)));
		if (Double.isNaN(coefficientCorrelation))
			coefficientCorrelation = 0.1;
		return coefficientCorrelation;
	}

	public double calculation(ArrayList<Double> x, ArrayList<Double> y) {

		BigDecimal sum_y = new BigDecimal(bigSumAverage(y));
		BigDecimal sum_x = new BigDecimal(bigSumAverage(x));

		BigDecimal sumCovXY = new BigDecimal("0");
		BigDecimal sumAverageX = new BigDecimal("0");
		BigDecimal sumAverageY = new BigDecimal("0");
		for (int i = 0; i < x.size(); i++) {
			BigDecimal x2 = new BigDecimal(String.valueOf(x.get(i)));
			BigDecimal y2 = new BigDecimal(String.valueOf(y.get(i)));


			BigDecimal buf = new BigDecimal("0");
			buf = y2.subtract(sum_y);
			BigDecimal buf2 = new BigDecimal("0");
			buf2 = x2.subtract(sum_x);

			sumCovXY = sumCovXY.add(buf2.multiply(buf));
			sumAverageX = sumAverageX.add(x2.subtract(sum_x).pow(2));
			sumAverageY = sumAverageY.add(y2.subtract(sum_y).pow(2));

		}
		BigDecimal cov = sumCovXY;
		BigDecimal sqrtSum = sqrt(sumAverageY.multiply(sumAverageX),
				BigDecimal.ROUND_CEILING);
		return cov.doubleValue() / sqrtSum.doubleValue();

	}

	private String bigSumAverage(ArrayList<Double> a) {
		BigDecimal s = new BigDecimal("0");
		int i = 0;
		for (Double value : a) {
			i++;
			s = s.add(new BigDecimal(String.valueOf(value)));
		}
		double sumA = s.doubleValue() / (double) a.size();
		return String.valueOf(sumA);

	}

	private int sumAverage(ArrayList<Integer> a) {
		int s = 0;
		for (Integer ar : a)
			s += ar;
		return s / a.size();
	}
	
	private BigInteger sqrt(BigInteger N) {
		BigInteger result = N.divide(BigInteger.ONE.add(BigInteger.ONE));
		while (result.multiply(result).subtract(N)
				.compareTo(BigInteger.ONE.divide(new BigInteger("100000000"))) > 0)
			result = result.add(N.divide(result)).divide(
					BigInteger.ONE.add(BigInteger.ONE));
		return result;
	}

	private static BigDecimal sqrt(BigDecimal A, final int SCALE) {
		BigDecimal x0 = new BigDecimal("0");
		BigDecimal x1 = new BigDecimal(Math.sqrt(A.doubleValue()));
		while (!x0.equals(x1)) {
			x0 = x1;
			x1 = A.divide(x0, SCALE, BigDecimal.ROUND_HALF_UP);
			x1 = x1.add(x0);
			x1 = x1.divide(new BigDecimal("2"), SCALE, BigDecimal.ROUND_HALF_UP);

		}
		return x1;
	}
}
