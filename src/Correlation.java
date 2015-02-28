import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Correlation {
	private PrintWriter pw;

	public void openFileOutput(String str) throws FileNotFoundException {
		pw = new PrintWriter(new File(str));
	}

	public double methodForOneString(BufferedImage img, int index) {
		int height = img.getHeight();
		int width = img.getWidth();
		double sumAverageCorrelation = 0;
		Main.println("Download Image " + index + "  Height = " + height
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

	public double methodForDiagonalOneString(BufferedImage img, int index) {
		int height = img.getHeight();
		int width = img.getWidth();
		double sumAverageCorrelation = 0;
		Main.println("Download Image " + index + "  Height = " + height
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
		pw.println("Average Correlation for " + index + " image = "
				+ sumAverageCorrelation);
		return sumAverageCorrelation;
	}
	public double methodForOneColumn(BufferedImage img, int index) {
		int height = img.getHeight();
		int width = img.getWidth();
		double sumAverageCorrelation = 0;
		Main.println("Download Image " + index + "  Height = " + height
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
		pw.println("Average Correlation " + index + " image = "
				+ c);
		return c;
	}

	public void close() {
		pw.close();
	}

	private int convertInt(int pix) {
		return Integer.valueOf(convert(pix));
	}

	private static String convert(int pix) {
		String hex = Long.toHexString(pix);// ��������� �� 10 � 16
		char s = hex.charAt(hex.length() - 1); // ����� ��������� ����.
		String lastHex = String.valueOf(s);
		int x = Integer.parseInt(lastHex, 16);
		String binarLastPixel = Integer.toBinaryString(x); // ���� � ��������
															// ����������
		char lastBit = binarLastPixel.charAt(binarLastPixel.length() - 1);
		return String.valueOf(lastBit);
		// return "";
	}

	private double calculationOfCorrelation(ArrayList<Integer> bit) {
		/*
		 * �������� ������ ��������. ������ ���� ������� �� ������� ������������
		 * �������� ����������. � = (x-x')(y-y')/sqrt(C(x-x')(x-x') *
		 * (y-y')(y-y'))); ��� x' = sum(x)/n; ��� n - ���������� ����������. a
		 * sum(x) - function ����� �� ������; y'=sum(y)/n;
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
