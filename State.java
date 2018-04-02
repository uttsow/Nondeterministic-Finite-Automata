 package urahman_p1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class State {

	private String state;
	private int stateNum;
	private Map<Character, ArrayList<Integer>> transitions = new HashMap<>();


/*Constructor for creating new State objects
 * Each state can be either a transition state or
 * regular, accept, start	accept, acceptstart, state
 */
	public State(int stateNum, String aState) {
		this.state = aState;
		this.stateNum = stateNum;
	}

	//Returns the state info (start, accept, acceptStart)
	public String getState() {
		return state;
	}

	//returns the number of the state or the q;
	public int getStateNum() {
		return stateNum;
	}



	// adds transitions to the state. If the keyVal is not in the map
	//then add it

	public void addTransitions(char keyVal, int stateVal) {
		if(transitions.containsKey(keyVal)) transitions.get(keyVal).add(stateVal);
		if(!transitions.containsKey(keyVal)) {
			transitions.put(keyVal, new ArrayList<>());
			transitions.get(keyVal).add(stateVal);
		}
	}

	// returns transitions from specified key
	public ArrayList<Integer> getTransitions(char c) {
		return transitions.get(c);
	}


//	//return all transitions. Used for debugging
//	public Map<Character, ArrayList<Integer>> getAllTransitons() {
//		return transitions;
//	}

}
