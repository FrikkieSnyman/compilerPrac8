import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.io.*;

public class Graph{
	Integer[][] adjacencyMatrix;
	HashMap<String, Integer> vertexList;
	HashMap<Integer, LinkedList<String>> kill;
	HashMap<Integer, LinkedList<String>> out;
	LinkedList<String> vertex = new LinkedList<>();
	String filePath;

	public Graph(HashMap<Integer, LinkedList<String>> kill, HashMap<Integer,LinkedList<String>> out, String filePath){
		this.kill = kill;
		this.out = out;
		this.filePath = filePath;
	}

	public void generateGraph(){
		int vertices = 0;
		List<String> lines = null;
		try{
			lines = Files.readAllLines(Paths.get(filePath), Charset.defaultCharset());
		} catch(IOException e){
			e.printStackTrace();
		}
		
		for (int i = 1; i+1 < lines.size(); ++i){
			LinkedList<String> tmp = kill.get(i);
			for (int j =0; j < tmp.size(); ++j){
				if (!vertex.contains(tmp.get(j))){
					vertex.add(tmp.get(j));
				}
			}
		}



		adjacencyMatrix = new Integer[vertices][vertices];
		vertexList = new HashMap<>();
	}

}