package urahman_p1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Nondeterministic_Automata {

	private static int numberOfStates = 1001;
	ArrayList<State> states = new ArrayList<>();
	ArrayList<State> transitionStates = new ArrayList<>();
	private ArrayList<Integer> currentState = new ArrayList<>();

	@SuppressWarnings("unused")
	private final String seq;

	public Nondeterministic_Automata(File aFile, String aSeq) throws Exception {
		readFile(aFile);
		this.seq = aSeq;

	}

	// allows user to change the number of starts
	public static void setNumOfState(int num) {
		numberOfStates = num;
		System.out.println(numberOfStates);
	}

	// keeps track of the ongoing sequence and transition
	public void seqRun(String seq) {
		ArrayList<Integer> qState = new ArrayList<>();

		for (char i : seq.toCharArray()) {
			// keeps on adding the transition states to qState for each iteration. then
			// clears before next

			while (currentState.size() != 0) {

				State sample = transitionStates.get(currentState.get(0));
				if(sample.getTransitions(i) == null) {
					currentState.clear();
					continue;
				}
				qState.addAll(sample.getTransitions(i));
				currentState.remove(0);
			}
			currentState.addAll(qState);
			qState.clear();
		}

		//
		//System.out.println(currentState);

	}

	public void printInfo(String seq) {
		seqRun(seq);

		ArrayList<Integer> acceptList = new ArrayList<>();
		ArrayList<Integer> rejectList = new ArrayList<>();

		// used for printing the states only once;
		// checks if index is null, if not, proceeds
		for (int i : currentState) {
			if (states.get(i) != null) {
				if (checkState(states.get(i), i) == true) {
					if (!acceptList.contains(i))
						acceptList.add(i);

				} else if (checkState(states.get(i), i) == false) {
					if (!rejectList.contains(i))
						rejectList.add(i);

				}
			} else {
				// if the current state is a null state on the state array, auto add to reject
				// list
				if (!rejectList.contains(i))
					rejectList.add(i);
			}
		}
		//System.out.println(rejectList);

		int holder = currentState.size() - 1;


		if(currentState.isEmpty()) {
			System.out.println("Reject");
			System.out.println();
		}
		else if (checkState(states.get(currentState.get(holder)), currentState.get(holder)) == true) {

			if (!acceptList.isEmpty()) {
				System.out.print("Accept ");
				for (int i : acceptList) {
					System.out.print(i + " ");
				}
				System.out.println();
			}
		} else if((checkState(states.get(currentState.get(holder)), currentState.get(holder)) == false) ){
			if (!rejectList.isEmpty()) {
				System.out.print("Reject ");
				for (int i : rejectList) {
					System.out.print(i + " ");
				}
			}
			System.out.println();

		}

	}

	public boolean checkState(State aState, int numState) {
		if(aState == null) return false;
		if (aState.getState().equals("start")) {
			return false;
		} else if (aState.getState().equals("transition, Dont Count")) {
			return false;
		}

		if (aState.getStateNum() == numState) {
			return true;
		}

		return false;
	}

	/*
	 * this method reads the file, breaks it up into an array line by line then
	 * analyzes each line based on if its a "state" or "transition and processes it
	 *
	 */
	public void readFile(File aFileName) throws FileNotFoundException {
		Scanner file = new Scanner(aFileName);

		// This sets the max number of states for the arraylist

		for (int i = 0; i < numberOfStates; i++) {
			states.add(null);
		}
		for (int x = 0; x < numberOfStates; x++) {
			transitionStates.add(null);
		}

		// reads and breaks down files into an array
		while (file.hasNextLine()) {
			String[] elements = file.nextLine().split("\\s+");

			// for states
			if (elements[0].equals("state")) {
				int stateNumber = Integer.parseInt(elements[1]);
				// used for "start accept"
				if (elements.length == 4) {
					currentState.add(stateNumber);

					states.set(stateNumber, new State(stateNumber, "acceptstart"));
				} else if (elements[2].equals("start")) {
					currentState.add(stateNumber);

					states.set(stateNumber, new State(stateNumber, "start"));

				} else if (elements[2].equals("accept")) {

					states.set(stateNumber, new State(stateNumber, "accept"));

				} else if (elements[2].equals("acceptstart")) {

					currentState.add(stateNumber);

					states.set(stateNumber, new State(stateNumber, "acceptstart"));
				} else {
					break;
				}
				// for transitions
			} else if (elements[0].equals("transition")) {
				int p = Integer.parseInt(elements[1]);
				char x = elements[2].charAt(0);
				int q = Integer.parseInt(elements[3]);

				// process each transition
				if (transitionStates.get(p) == null) {
					transitionStates.set(p, new State(p, "transition, Dont Count"));
					transitionStates.get(p).addTransitions(x, q);
				} else {
					transitionStates.get(p).addTransitions(x, q);
				}

			}

		}

		file.close();

	}

	public static void main(String[] args) {
			boolean exitVal = false;
			
		
			Scanner userInput = new Scanner(System.in);
			System.out.println("Enter a file name or enter 'exit' to exit: ");
			String val = userInput.nextLine();
			
			if(val.contains("exit")) {
				exitVal = true;
				System.exit(0);
			}
			
			while(exitVal == false) {
			
				File newFile = new File(val);
				System.out.println("Enter a seqeunce or 'exit' to exit");
				String seqTest = userInput.nextLine();
				if(seqTest.contains("exit")) {
					System.exit(0);
				}else {
				if (seqTest.isEmpty() || seqTest == " ") {
					userInput.close();
					throw new IllegalArgumentException("enter a input and try again");
				}

				// setNumOfState(25);
				try {
					Nondeterministic_Automata tester = new Nondeterministic_Automata(newFile, seqTest);
					tester.printInfo(seqTest);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				}
			
			}
			userInput.close();
	
	}

}
