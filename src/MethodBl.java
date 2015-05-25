import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

public class MethodBl {
	private final static int BLUE = 0x000000FF;
	private final static int RED = 0x00FF0000;
	private final static int GREEN = 0x0000FF00;

	/**
	 * Основной метод проверки коррелеции между пиксилем и младшими битами
	 * одного из компонентов RGB
	 */
	public static void method() {
		String folder;
		// folder="черно-белые без стего/";
		// folder= "чб стего/";
		folder = "stegoPhotoHiddenData/";
		// folder ="test_jpg/";

		ArrayList<String> nameList = new FileDirectory().get(folder);
		for (String name : nameList) {
			BufferedImage img = null;
			try {
				img = ImageIO.read(new File(folder + name));
				System.out.println(name);
				System.out.println("		Работает метод 1 ");
				// checkMethodCorrelationPixel(img);
				System.out.println("		Работает метод 2 ");
				checkMethodCorrelationWithHelpOneByte(img);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public static void checkMethodCorrelationPixel(BufferedImage img) {
		ArrayList<Integer> listNumb = getListRGB(img);
		for (int pos = 1; pos <= 8; pos++) {
			// ArrayList<Integer> listRank = new ArrayList<Integer>();
			// listRank = getListComponent(img, pos);
		//	System.out.print(convertInBinaryValue(999, pos));
			// System.out.println(pos + " Coeficient = "
			// + new Correlation().calculation(listNumb, listRank));
		}
	}

	public static void checkMethodCorrelationWithHelpOneByte(BufferedImage img) {
		ArrayList<Integer> listByte = new ArrayList<Integer>();
		ArrayList<Integer> listBit = new ArrayList<Integer>();
	//	System.out.print(getSet(img, BLUE).size());
		for (int pos = 1; pos <= 7; pos++) {
			for (Integer integer : getSet(img, BLUE)) {
				listByte.add(integer);				
				listBit.add(convertInBinaryValue(integer, pos));
				//System.out.println("X= "+integer+" Y= "+convertInBinaryValue(integer, pos));
			}
			System.out.println(pos + " Coeficient = "
					+ new Correlation().calculation(averageValue(listByte),  averageValue(listBit)));
		}
	}

	private static ArrayList<Integer> averageValue(ArrayList<Integer> list) {
		ArrayList<Integer> listAver=new ArrayList<Integer>();
		int index=0;
		int sum=0;
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
	public static ArrayList<Integer> getListRGB(BufferedImage img) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				list.add(Math.abs(img.getRGB(j, i)));
			}
		}
		return list;
	}

	/**
	 * Получение выборки одной компоненты
	 */
	public static ArrayList<Integer> getListComponent(BufferedImage img, int pos) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				list.add(getNumberComponent(BLUE, img.getRGB(j, i), pos));
				// System.out.println(img.getRGB(j, i);
			}
		}
		return list;
	}

	public static HashSet<Integer> getSet(BufferedImage img, int color) {
		HashSet<Integer> set = new HashSet<Integer>();
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				set.add(getByte(img.getRGB(j, i), color));
				//System.out.print("Byte="+getByte(img.getRGB(j, i), color));
			}
		}
		return set;
	}

	public static int getNumberComponent(int color, int rgb, int pos) {
		int b = (rgb & color) >>> 0;
		int n = convertInBinaryValue(pos, b);
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

	public static int getByte(int rgb, int color) {
		//return (rgb & color) >>> 0;
		//return(rgb & 0x00FF0000) >>> 16;
		return(rgb & 0x0000FF00) >>> 8;
			
				
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
