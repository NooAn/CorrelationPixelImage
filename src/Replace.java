import java.util.ArrayList;
import java.util.function.BinaryOperator;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Replace {
	/**
	 * ImageNew картинка которая выйдет на выходе, сохраненая. Все значения
	 * младших бит берем из imageReplace;
	 * 
	 * @param imageNew
	 * @param ArrayList
	 *            <byte> byteReplace
	 * @throws IOException 
	 */
	public void lastTwoBit(BufferedImage imageNew, ArrayList<Byte> listReplace) throws IOException {
		int height = imageNew.getHeight();
		int width = imageNew.getWidth();
		int k = 0;
		/*
		 * Лист байт.содержит 10,11,00,01
		 */
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				byte lastBitsReplace = 0;
				lastBitsReplace = listReplace.get(k);
				k++;
				int bitNum = imageNew.getRGB(j, i);
				bitNum = bitNum & 0b11111111111111111111111111111100; // Обнуляем
																		// последний
																		// два!!!!!!
																		// бита

				bitNum = bitNum | lastBitsReplace;// Устанавливаем последние
				// биты в соответствие с lastBitsReplace;
				imageNew.setRGB(j, i,bitNum);
			}	
			Main.println(i);
		}
		Analysis.savePicurePng(imageNew, "newTestBlc.png"); 
	}

	public ArrayList<Byte> byteList(BufferedImage img) {
		int height = img.getHeight();
		int width = img.getWidth();
		ArrayList<Byte> listByte = new ArrayList<Byte>();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				byte b = (byte) img.getRGB(j, i);
				b = (byte) (b & 0b11);
				listByte.add(b);
			}
		}
		return listByte;
	}
}
