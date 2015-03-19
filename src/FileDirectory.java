import java.util.*;
import java.io.*;

public class FileDirectory {
	// main procedure
//	public void get(String path) {
//		//Scanner scanner = new Scanner(System.in);
//
//		System.out.println("Enter directory: ");
//		// String Path=scanner.nextLine();
//		String Path;
//		Path = "image_jpg/";
//		printTreeFiles("stego_image_jpg/");
//	}

	public  ArrayList<String> get(String Path) {
		File Directory = new File(Path);
		if (Directory.exists()) {
			return getContent(Directory);
		} else{	
			System.out.println("Directory is not found...");
			return null;
		}
	}
	
	private  ArrayList<String> getContent(File directory) {
			ArrayList<String> list = new ArrayList<>();
			System.out.println("Directory is "+directory.getName());
			File[] SubDirectory = directory.listFiles();
			for (File SubWay : SubDirectory)
				list.add(SubWay.getName());
				//getContent(SubWay); для рекурсии
		return list;
	}
}