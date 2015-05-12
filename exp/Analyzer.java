import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

public class Analyzer {
	public HashMap<Integer, Line> line;
	Integer numOfLines;

	public Analyzer() {
		line = new HashMap<Integer, Line>();
		numOfLines = 0;
	}

	public void generateSets(String input) {
		handleInput(input.split("\n"));

		generateSucc();
		generateGen();
		generateKill();

		printBasicSets();
		System.out.println("");

		generateOutIn();
	}

	public void handleInput(String[] splitInput) {
		numOfLines = splitInput.length;

		for (int i = 0; i < numOfLines; ++i) {
			line.put(Integer.parseInt(splitInput[i].substring(0, splitInput[i].indexOf(" "))),
				new Line(splitInput[i].substring(splitInput[i].indexOf(" ") + 1, splitInput[i].length())));
		}
	}

	public void printBasicSets() {
		System.out.println("\tSucc\t\tGen\t\tKill");

		for (int i = 1; i <= numOfLines; ++i) {
			System.out.println(i + "\t" + 
				line.get(i).succ.toString() + "\t\t" + 
				line.get(i).gen.toString() + "\t\t" + 
				line.get(i).kill.toString());
		}
	}

	public void printInOutSet() {
		System.out.println("\tOut\t\tIn");

		for (int i = 1; i <= numOfLines; ++i) {
			System.out.println(i + "\t" + 
				line.get(i).out.toString() + "\t\t" + 
				line.get(i).in.toString());
		}
	}

	public void generateSucc() {
		String currentLine = "";

		for (int i = 1; i <= numOfLines; ++i) {
			currentLine = line.get(i).content;

			if (currentLine.contains("GOTO")) {
				line.get(i).succ.add(Integer.parseInt(currentLine.substring(currentLine.indexOf(" ") + 1, currentLine.length()).trim()));
			} else if (currentLine.contains("IF") && !currentLine.contains("ELSE")) {
				line.get(i).succ.add(Integer.parseInt(currentLine.substring(currentLine.indexOf("THEN") + 5, currentLine.length()).trim()));
				line.get(i).succ.add(i + 1);
			} else if (currentLine.contains("IF") && currentLine.contains("ELSE")) {
				line.get(i).succ.add(Integer.parseInt(currentLine.substring(currentLine.indexOf("THEN") + 5, currentLine.indexOf("ELSE")).trim()));
				line.get(i).succ.add(Integer.parseInt(currentLine.substring(currentLine.indexOf("ELSE") + 5, currentLine.length()).trim()));
			} else if (currentLine.contains("RETURN")) {
				;
			} else {
				line.get(i).succ.add(i + 1);
			}
		}
	}

	public void generateGen() {
		String currentLine = "";

		for (int i = 1; i <= numOfLines; ++i) {
			currentLine = line.get(i).content;

			if (currentLine.contains("=") && !currentLine.contains("IF")) {
				currentLine = currentLine.substring(currentLine.indexOf("=") + 1, currentLine.length()).trim();

				for (int j = 0; j < currentLine.length(); ++j) {
					if (Character.isLetter(currentLine.charAt(j))) {
						line.get(i).gen.add(currentLine.charAt(j));
					}
				}
			} else if (currentLine.contains("IF")) {
				currentLine = currentLine.substring(2, currentLine.indexOf("THEN")).trim();

				for (int j = 0; j < currentLine.length(); ++j) {
					if (Character.isLetter(currentLine.charAt(j))) {
						line.get(i).gen.add(currentLine.charAt(j));
					}
				}
			} else if (currentLine.contains("RETURN")) {
				if (currentLine.length() != 6) {
					currentLine = currentLine.substring(currentLine.indexOf(" "), currentLine.length()).trim();
					line.get(i).gen.add(currentLine.charAt(0));
				}
			}
 		}
	}

	public void generateKill() {
		String currentLine = "";

		for (int i = 1; i <= numOfLines; ++i) {
			currentLine = line.get(i).content;

			if (currentLine.contains("=") && !currentLine.contains("IF")) {
				line.get(i).kill.add(currentLine.charAt(0));
			}
 		}
	}

	public void generateOutIn() {
		boolean madeAChange = true;
		Integer iteration = 0;

		while (madeAChange) {
			madeAChange = false;
			++iteration;

			System.out.println("Iteration " + iteration);

			for (int i = numOfLines; i > 0; --i) {
				if (generateOut(i)) {
					madeAChange = true;
				}

				if (generateIn(i)) {
					madeAChange = true;
				}
			}

			printInOutSet();
		}

		// for (int i = numOfLines; i > 0; --i) {
		// 	generateOut(i);
		// 	generateIn(i);
		// }
	}

	public boolean generateOut(Integer i) {
		boolean madeAChange = false;

		LinkedList<Integer> succ = line.get(i).succ;

		if (succ != null) {
			ListIterator<Integer> iterator = succ.listIterator();
			Integer j = null;

			while (iterator.hasNext()) {
				j = iterator.next();

				if (addToOut(i, j)) {
					madeAChange = true;
				}
			}
		}

		return madeAChange;
	}

	public boolean addToOut(Integer i, Integer j) {
		// System.out.println("i " + i + " j " + j);

		if (line.get(j) == null) {
			return false;
		}

		boolean madeAChange = false;

		ListIterator<Character> iterator = line.get(j).in.listIterator();
		Character current = null;

		while (iterator.hasNext()) {
			current = iterator.next();

			if (!line.get(i).out.contains(current)) {
				line.get(i).out.add(current);

				madeAChange = true;
			}
		}

		return madeAChange;
	}

	public boolean generateIn(Integer i) {
		boolean madeAChange = false;

		ListIterator<Character> iterator = line.get(i).gen.listIterator();
		Character current = null;

		while (iterator.hasNext()) {
			current = iterator.next();

			if (!line.get(i).in.contains(current)) {
				line.get(i).in.add(current);

				madeAChange = true;
			}
		}

		LinkedList<Character> outSet = new LinkedList<Character>(line.get(i).out);
		LinkedList<Character> killSet = new LinkedList<Character>(line.get(i).kill);

		iterator = outSet.listIterator();
		current = null;

		while (iterator.hasNext()) {
			current = iterator.next();

			if (killSet.contains(current)) {
				iterator.remove();
			}
		}

		iterator = outSet.listIterator();
		current = null;

		while (iterator.hasNext()) {
			current = iterator.next();

			if (!line.get(i).in.contains(current)) {
				line.get(i).in.add(current);

				madeAChange = true;
			}
		}

		return madeAChange;
	}
}