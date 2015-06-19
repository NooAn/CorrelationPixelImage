import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

public class AlgorithmStegoAnalysis {
	private final static int BLUE = 0x000000FF;
	private final static int RED = 0x00FF0000;
	private final static int GREEN = 0x0000FF00;

	/**
	 * Метод подсчета корреляции на основе 
	 * окружения близких пикселей. Выборка X строится по всем пикселям изображения.
	 * А выборка Y строится по его окружению.
	 * То есть берутся все соседние пиксели. Таких пикселов будет восемь.
	 * Считается среднее значение и заносится в Y. Считается корреляция.
	 * 
	  @		@	  @
	  @		X	  @	
	  @		@	  @
	 */
	public static void checkMethodSurroundings(BufferedImage img) {
		ArrayList<Double> listNumb = getListRGB(img);
		ArrayList<Double> listSurroundings =getAverage8Pix(img);
		System.out.println("	Method Surroundings\nCoeficient = "
				+ new Correlation().calculation(listNumb, listSurroundings));
	}
	private static ArrayList<Double> getAverage8Pix(BufferedImage img) {
	double sum = 0;
	double sum1=0, sum2=0, sum3=0;
	ArrayList<Double> list = new ArrayList<>();
		for ( int i=1;i<img.getWidth()-1;i++) {
			for ( int j=1;j<img.getHeight()-1;j++){
				BigDecimal bigSum = new BigDecimal(0);
				
				bigSum = bigSum.add(new BigDecimal(img.getRGB(i-1, j-1)));
				bigSum = bigSum.add(new BigDecimal(img.getRGB(i, j-1)));
				bigSum = bigSum.add(new BigDecimal((img.getRGB(i+1, j-1))));
				bigSum = bigSum.add(new BigDecimal(img.getRGB(i+1, j)));
				bigSum = bigSum.add(new BigDecimal(img.getRGB(i+1, j+1)));
				bigSum = bigSum.add(new BigDecimal(img.getRGB(i, j+1)));
				bigSum = bigSum.add(new BigDecimal(img.getRGB(i-1, j+1)));
				bigSum = bigSum.add(new BigDecimal(img.getRGB(i-1, j)));
				bigSum = bigSum.divide(new BigDecimal(8));
				list.add(bigSum.doubleValue());
				
			}
		}
		return list;
	}

/**
 * 	Выборка X строится по всем пикселям.
 *  Выборка Y это значение последних пикселов по одной из компоненты RGB.
 *  Количество последних пикселов перебирается от 1 до 7.
 *  Подсчитывается корреляция.
 * @param img графический контейнер
 */
	public static void checkMethodCorrelationPixel(BufferedImage img) {
		ArrayList<Double> x = getListRGB(img);
		for (int pos = 1; pos <= 7; pos++) {
			 ArrayList<Double> y = new ArrayList<Double>();
			 y = getListComponent(img, pos);
			System.out.println(pos + " lsb Coeficient = "
				+ new Correlation().calculation(x, y));
		}
	}
/**
 * Выборка X строится по следующему правилу.
 *  Каждый пиксель изображения разбивается на три компоненты Red, Green, Blue.
 *  Берем одну из компонент и заносим ее в X. Выборка Y строится извлечение последних бит из данной компоненты.
 *  Подсчитывается корреляция.
 *  В программе это реализовано в методе checkMethodCorrelationWithHelpOneByte
 * @param img графический контейнер
 */
	public static void checkMethodCorrelationWithHelpOneByte(BufferedImage img) {
		for (int pos = 1; pos <= 7; pos++) {
			ArrayList<Integer> listByte = new ArrayList<Integer>();
			ArrayList<Integer> listBit = new ArrayList<Integer>();
			for (Integer d : getSet(img, BLUE)) {
				listByte.add(d);				
				listBit.add(convertInBinaryValue(d, pos));
			}
			System.out.println(pos + " lsb Coeficient = "
					+ new Correlation().calculation(averageValue(listByte),  averageValue(listBit)));
		}
	}

	private static ArrayList<Double> averageValue(ArrayList<Integer> list) {
		ArrayList<Double> listAver=new ArrayList<Double>();
		int index=0;
		double sum=0;
		int average = list.size()/16;
		for (Integer value : list){
			if ( index ==  average) {
				index=0;
				listAver.add(sum/average);
				sum=0;
			}
			sum+=value;
			index++;
		}
		return listAver;
	}

	/**
	 * Получение всех значений RGB.
	 */
	public static ArrayList<Double> getListRGB(BufferedImage img) {
		ArrayList<Double> list = new ArrayList<Double>();
		for (int i = 1; i < img.getHeight()-1; i++) {
			for (int j = 1; j < img.getWidth()-1; j++) {
				list.add((double)(Math.abs(img.getRGB(j, i))));
			}
		}
		return list;
	}

	/**
	 * Получение выборки одной компоненты
	 */
	public static ArrayList<Double> getListComponent(BufferedImage img, int pos) {
		ArrayList<Double> list = new ArrayList<Double>();
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				list.add((double)getNumberComponent(BLUE, img.getRGB(j, i), pos));
			}
		}
		return list;
	}

	private static HashSet<Integer> getSet(BufferedImage img, int color) {
		HashSet<Integer> set = new HashSet<Integer>();
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				set.add(getByte(img.getRGB(j, i), color));
			}
		}
		return set;
	}

	public static int getNumberComponent(int color, int rgb, int pos) {
	//	int b = (rgb & color) >>> 0;
	//	int b = (rgb & 0x000000FF) >>> 0;
		int b = (rgb & 0x00FF0000) >>> 16;
		int n = convertInBinaryValue(b, pos);
		return n;
	}

	/**
	 * @param pos
	 * @param b
	 * @return
	 * @throws NumberFormatException
	 */
	private static int convertInBinaryValue(int b, int pos)
			throws NumberFormatException {
		String s = Integer.toBinaryString(b);

		while (s.length() != 8) {
			s = "0" + s;
		}
		String s2 = s.substring(8 - pos, 8);
		int n = Integer.parseInt(s2, 2);
		return n;
	}

	private static int getByte(int rgb, int color) {
		//return (rgb & color) >>> 0;
		return(rgb & 0x00FF0000) >>> 16;
		//return(rgb & 0x0000FF00) >>> 8;
	}

	public void rgb(BufferedImage image, int x, int y) {
		int rgb = image.getRGB(x, y);

		int o = (rgb & 0xFF000000) >>> 24;
		int r = (rgb & 0x00FF0000) >>> 16;
		int g = (rgb & 0x0000FF00) >>> 8;
		int b = (rgb & 0x000000FF) >>> 0;

		if (o != 0xFF) {
			System.out.println("пїЅпїЅпїЅ! " + o);
		}
		if (r != g && g != b) {
			System.out.println("пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ grayscale. "
					+ r + " " + g + " " + b);
		}
		image.setRGB(x, y, o << 24 | r << 16 | g << 8 | b);
	}
}
