import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.io.*;
import java.util.*;



public class Main{
	public static void main(String[] args) {
		InOut inout = new InOut();
		inout.main(args);

		Graph graph = new Graph(inout.kill.kill, inout.out, args[0]);
		graph.generateGraph();
	}
}