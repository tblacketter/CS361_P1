package fa.dfa;

import fa.State;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a state in a Deterministic Finite Automaton (DFA).
 * Extends the abstract State class and stores transition information
 * for this state as a map from input symbols to destination state names.
 *
 * @author Lance
 */
public class DFAState extends State {

    /** Maps input symbols to the name of the destination state. */
    private Map<Character, String> transitions;

    /**
     * Constructs a DFAState with the given name.
     * Initializes an empty transition map.
     *
     * @param name the unique label for this state
     */
    public DFAState(String name) {
        super(name);
        transitions = new HashMap<>();
    }

    /**
     * Adds or updates a transition from this state on the given symbol.
     *
     * @param symbol  the input symbol triggering this transition
     * @param toState the name of the destination state
     */
    public void addTransition(char symbol, String toState) {
        transitions.put(symbol, toState);
    }

    /**
     * Returns the name of the state this state transitions to on the given symbol.
     *
     * @param symbol the input symbol
     * @return the name of the destination state, or null if no transition exists
     */
    public String getTransition(char symbol) {
        return transitions.get(symbol);
    }

    /**
     * Returns the full transition map for this state.
     *
     * @return a map from input symbols to destination state names
     */
    public Map<Character, String> getTransitions() {
        return transitions;
    }
}