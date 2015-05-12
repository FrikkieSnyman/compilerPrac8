import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.io.*;

public class Graph{
	Boolean[][] adjacencyMatrix;
	HashMap<Character, Integer> vertexList;
	HashMap<Integer, Line> sets;
	LinkedList<Character> vertex = new LinkedList<>();
	String filePath;

	public Graph(HashMap<Integer, Line> sets, String filePath){
		this.sets = sets;
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
			LinkedList<Character> tmp = sets.get(i).kill;
			for (int j =0; j < tmp.size(); ++j){
				if (!vertex.contains(tmp.get(j))){
					vertex.add(tmp.get(j));
				}
			}
		}
		vertices = vertex.size();
		vertexList = new HashMap<>();
		
		for (int i = 0; i < vertices; ++i){
			vertexList.put(vertex.get(i),i);
		}
		
		adjacencyMatrix = new Boolean[vertices][vertices];

		for (int instruction = 1; instruction <= lines.size(); ++instruction){
			LinkedList<Character> killList = (sets.get(instruction)).kill;
			LinkedList<Character> outList = (sets.get(instruction)).out;
			if (killList != null){
				for (int k = 0; k < killList.size(); ++k){
					Character x = killList.get(k);
					if (outList != null){
						for (int l = 0; l < outList.size(); ++l){
							Character y = outList.get(l);
							String notThisInstruction = x + " = " + y;
							if (!x.equals(y) && !lines.get(instruction).contains(notThisInstruction)){
								adjacencyMatrix[vertexList.get(x)][vertexList.get(y)] = true;
								adjacencyMatrix[vertexList.get(y)][vertexList.get(x)] = true;
							}
						}
					}
				}
			}
		}


		for (int i = 0; i < vertices; ++i){
			System.out.println(vertex.get(i) + ": " + Arrays.toString(adjacencyMatrix[i]));
		}

	}

}