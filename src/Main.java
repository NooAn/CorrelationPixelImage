
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main extends Method {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		//forJpeg(21);
		forBmp(10);
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
