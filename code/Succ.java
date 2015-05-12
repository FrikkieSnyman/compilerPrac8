import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.io.*;
import java.util.*;

public class Succ{
	public static HashMap<Integer,LinkedList<Integer>> succ = new HashMap<>();
	public static void main(String[] args) {
		List<String> lines = null;
		
		try{
			lines = Files.readAllLines(Paths.get(args[0]), Charset.defaultCharset());
		} catch(IOException e){
			e.printStackTrace();
		}

		for (int i = 0; i < lines.size(); ++i){
			String analyse = lines.get(i);

			if (analyse.contains("GOTO")){				// i is a GOTO
				String[] split = analyse.split(" "); 	// index 0 = line number, 1 = GOTO, 2 = line number where goto goes
				Integer j = Integer.parseInt(split[2]);
				LinkedList<Integer> tmp = succ.get(i+1);
				if (tmp == null){
					tmp = new LinkedList<>();
				}
				tmp.add(j);
				succ.put(i+1,tmp);

			} else if (analyse.contains("IF")){		// i is an IF-THEN-ELSE
				String[] split = analyse.split("THEN ");	// index 0 = line number IF evaluation, 1 = line number to go (ELSE line number to go)
				
				if (split[1].contains("ELSE")){
					String[] el = split[1].split("ELSE ");
					LinkedList<Integer> tmp = succ.get(i+1);
					if (tmp == null){
						tmp = new LinkedList<>();
					}
					tmp.add(Integer.parseInt(el[0].trim()));
					tmp.add(Integer.parseInt(el[1].trim()));
					succ.put(i+1,tmp);
				} else{
					Integer j = Integer.parseInt(split[1].trim());
					LinkedList<Integer> tmp = succ.get(i+1);
					if (tmp == null){
						tmp = new LinkedList<>();
					}
					tmp.add(j);
					succ.put(i+1,tmp);
				}


			} else if (analyse.contains("RETURN")){		// i is a RETURN
				succ.put(i+1,new LinkedList<Integer>());
			} else {	// Instruction j in Succ(i) because i != GOTO|IF-THEN-ELSE|RETURN
				int j = i + 2;
				if (j < lines.size()){ // there is a line j
					// succ.put(i,j);
					LinkedList<Integer> tmp = succ.get(i+1);
					if (tmp == null){
						tmp = new LinkedList<>();
					}
					tmp.add(j);
					succ.put(i+1,tmp);
				}
			}
		}
		// System.out.println("SUCC: " + succ.toString());
	}
}