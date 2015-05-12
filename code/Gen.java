import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.io.*;
import java.util.*;

public class Gen{
	public static HashMap<Integer,LinkedList<String>> gen = new HashMap<>();
	public static void main(String[] args) {
		List<String> lines = null;
		
		try{
			lines = Files.readAllLines(Paths.get(args[0]), Charset.defaultCharset());
		} catch(IOException e){
			e.printStackTrace();
		}

		for (int i = 0; i < lines.size(); ++i){
			String analyse = lines.get(i);
			LinkedList<String> tmp = gen.get(i+1);
			if (tmp == null){
				tmp = new LinkedList<String>();
			}

			if (analyse.contains("=")){
				if (!analyse.contains("IF")){
					String[] split = analyse.split("=");		// index 0 = left of '=', 1 = right of '='
					for (int k = 0; k < split.length; ++k){
						split[k] = split[k].trim();
					}
					String[] right = split[1].split(" ");
					for (int j = 0; j < right.length; ++j){
						if (Character.isLetter(right[j].charAt(0))){	// variable name
							tmp.add(right[j]);
						}
					}
				} else if (analyse.contains("THEN")){
					String[] split = analyse.split("THEN");
					String ifPart = split[0].trim();
					split = ifPart.split("IF");
					String conditionalPart = split[1].trim();
					split = conditionalPart.split(" ");
					for (int j = 0; j < split.length; ++j){
						if (Character.isLetter(split[j].charAt(0))){	// variable name
							tmp.add(split[j]);
						}
					}

				}
			} else if (analyse.contains("RETURN")){
				String[] split = analyse.split("RETURN ");
				if (Character.isLetter(split[1].charAt(0))){
					tmp.add(split[1]);
				}
			}

			gen.put(i+1,tmp);
		}

		// System.out.println("GEN: " + gen.toString());
	}
}