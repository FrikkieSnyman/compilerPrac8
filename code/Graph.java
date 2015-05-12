import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.io.*;

public class Graph{
	Boolean[][] adjacencyMatrix;
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
		vertices = vertex.size();
		vertexList = new HashMap<>();
		
		for (int i = 0; i < vertices; ++i){
			vertexList.put(vertex.get(i),i);
		}
		
		adjacencyMatrix = new Boolean[vertices][vertices];

		for (int instruction = 0; instruction < lines.size(); ++instruction){
			LinkedList<String> killList = kill.get(instruction);
			LinkedList<String> outList = out.get(instruction);
			if (killList != null){
				for (int k = 0; k < killList.size(); ++k){
					String x = killList.get(k);
					if (outList != null){
						for (int l = 0; l < outList.size(); ++l){
							String y = outList.get(l);
							if (!x.equals(y)){
								adjacencyMatrix[vertexList.get(x)][vertexList.get(y)] = true;
							}
						}
					}
				}
			}
		}


		for (int i = 0; i < vertices; ++i){
			System.out.println(Arrays.toString(adjacencyMatrix[i]));
		}

	}

}