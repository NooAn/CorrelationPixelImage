import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Main extends Analysis {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {	
		solve();	
	
	}
	/**
	 * ������� ��� �������� ������ ����������.
	 * �� ������ ������ ������ �������� 1.0 !
	 */
	public static void checkCorrelation() {
		int a=2;
		int b=30;
		ArrayList<Double> listY = new ArrayList<Double>();
		ArrayList<Double> listX = new ArrayList<Double>();
		for ( int x=2;x<150;x++) {
			listX.add((double) x);
			listY.add((double) (a*x+b));
		}
		System.out.println(" Coeficient = "
				+ new Correlation().calculation(listX, listY));
		
	}

	/**
	 * �������� ����� �������� ���������� ����� �������� � �������� ������
	 * ������ �� ����������� RGB
	 */
	public static void solve() {
		String folder;
		// folder="�����-����� ��� �����/";
		folder = "TestImage/";
	  //folder="Lena/";


		ArrayList<String> nameList = new FileDirectory().get(folder);
		for (String name : nameList) {
			BufferedImage img = null;
			AlgorithmStegoAnalysis algorithm = new AlgorithmStegoAnalysis();
			try {
				img = ImageIO.read(new File(folder + name));
				//Inversion inv = new Inversion(img);

				System.out.println(name);
				System.out.println("        �������� ����� 1 ");
				Analysis.startThreeMethods(name, img);
				System.out.println("		�������� ����� 2 ");
				AlgorithmStegoAnalysis.checkMethodCorrelationPixel(img);
				System.out.println("		�������� ����� 3 ");
				AlgorithmStegoAnalysis.checkMethodCorrelationWithHelpOneByte(img);
				System.out.println("  		�������� ����� 4");
				AlgorithmStegoAnalysis.checkMethodSurroundings(img);
				System.out.println("--------------------------------");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/*
	 * ����� ��� �������� �������� ���� �����������.
	 * ������������ ��� �������� �����-����� ����������� � ��������.
	 * ������������� ����������� ����������.
	 * ���������: ���� ������. ����������� ������ ���������.
	 */
	public static void methodCheckSimilarity() {
		String folder ="�� ���-���/";
		String folder2 = "���-���/";
		//ArrayList<String> nameList2 = new FileDirectory().get(folder2);
		ArrayList<String> nameList = new FileDirectory().get(folder);
		BufferedImage img1 = null;
		BufferedImage img2 = null;
		for (String name : nameList) {
			try {
				System.out.println(name);
				img1 = ImageIO.read(new File(folder + name));
				img2 = ImageIO.read(new File(folder2 + name));
			} catch (IOException e) {
				e.printStackTrace();
			}
			new Similarity(img1, img2).compareOfCorrelation();
		}
				
	}
	public static void print(Object arg) {
		System.out.print(arg + " ");
	}
	public static void println(Object arg) {
		System.out.println(arg + " ");
	}
}
