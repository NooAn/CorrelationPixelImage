import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Similarity {
	private BufferedImage img1;
	private BufferedImage img2;
	public Similarity(BufferedImage img1, BufferedImage img2) {
		this.img1 = img1;
		this.img2 = img2; 
	}

	public void compareOfCorrelation() {
		System.out.println("	Method 'Similarity'");
		// От одного до 7 бита с конца.
		for (int pos = 1; pos <= 7; pos++) {
			ArrayList<Double> listImg1 = AlgorithmStegoAnalysis.getListComponent(img1, pos);
			ArrayList<Double> listImg2 = AlgorithmStegoAnalysis.getListComponent(img2, pos);
			System.out.println(pos + "lsb Coeficient = "
				+ new Correlation().calculation(listImg1, listImg2));
		}
	}
}
