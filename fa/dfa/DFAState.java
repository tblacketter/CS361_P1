package fa.dfa;

import fa.State;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a state in a DFA.
 *
 * Each state stores its outgoing transitions.
 *
 * @author
 */
public class DFAState extends State {

    /* Map: input symbol -> destination state */
    private Map<Character, DFAState> transitions;

    /**
     * Create a DFA state with given name.
     *
     * @param name State name
     */
    public DFAState(String name) {
        super(name);
        transitions = new HashMap<>();
    }

    /**
     * Add a transition from this state.
     *
     * @param c Input symbol
     * @param to Destination state
     */
    public void addTransition(char c, DFAState to) {
        transitions.put(c, to);
    }

    /**
     * Get next state for given symbol.
     *
     * @param c Input symbol
     * @return Destination state
     */
    public DFAState getTransition(char c) {
        return transitions.get(c);
    }

    /**
     * Get all transitions.
     *
     * @return Transition map
     */
    public Map<Character, DFAState> getTransitions() {
        return transitions;
    }
}
