import java.util.Scanner;
import java.io.File;

public class Main {
	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		String fileName = "";
		Analyzer analyzer = new Analyzer();

		System.out.print("Enter the name of the input file: ");
		fileName = scanner.nextLine();

		while (!fileName.equals("quit")) {
			String fileContent = new Scanner(new File(fileName)).useDelimiter("\\Z").next();

			// System.out.println(fileContent);

			analyzer.generateSets(fileContent);
			Graph graph = new Graph(analyzer.line,fileName);
			graph.generateGraph();
			System.out.print("Enter the name of the input file: ");
			fileName = scanner.nextLine();
		}
	}
}