package mainPackage;

import java.io.*;

public class FileIO {

	public static String read(String path) {
		try {
			BufferedReader in = new BufferedReader(
				new InputStreamReader(
				new FileInputStream(path),"UTF8"));
			StringBuilder text = new StringBuilder();
			String line = null;
			while((line=in.readLine())!=null) {
				text.append(line);
				text.append("\n");
			}
			in.close();
			return text.toString();
		} catch(Exception e) {}
		return null;
	}
	
	public static void write(String path, String text) {
		try {
			BufferedWriter out = new BufferedWriter(
				new OutputStreamWriter(
				new FileOutputStream(path),"UTF8"));
			out.write(text);
			out.close();
		} catch(Exception e) {}
	}
	
}
