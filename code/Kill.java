import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.io.*;
import java.util.*;

public class Kill{
	public static HashMap<Integer,LinkedList<String>> kill = new HashMap<>();
	public static void main(String[] args) {
		List<String> lines = null;
		
		try{
			lines = Files.readAllLines(Paths.get(args[0]), Charset.defaultCharset());
		} catch(IOException e){
			e.printStackTrace();
		}

		for (int i = 0; i < lines.size(); ++i){
			String analyse = lines.get(i);
			LinkedList<String> tmp = kill.get(i+1);
			if (tmp == null){
				tmp = new LinkedList<String>();
			}

			if (analyse.contains("=")){
				if (!analyse.contains("IF")){
					String[] split = analyse.split("=");		// index 0 = left of '=', 1 = right of '='
					for (int k = 0; k < split.length; ++k){
						split[k] = split[k].trim();
					}
					String[] left = split[0].split(" ");
					if (Character.isLetter(left[1].charAt(0))){	// variable name
						tmp.add(left[1]);
					}
				}
			}
			kill.put(i+1,tmp);
		}
		System.out.println("KILL: " + kill.toString());
	}
}