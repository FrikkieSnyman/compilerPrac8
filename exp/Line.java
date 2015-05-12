import java.util.LinkedList;

public class Line {
	public String content;

	public LinkedList<Integer> succ;
	public LinkedList<Character> gen;
	public LinkedList<Character> kill;
	public LinkedList<Character> in;
	public LinkedList<Character> out;

	public Line(String content) {
		this.content = content.trim();

		succ = new LinkedList<Integer>();
		gen = new LinkedList<Character>();
		kill = new LinkedList<Character>();
		in = new LinkedList<Character>();
		out = new LinkedList<Character>();
	}
}