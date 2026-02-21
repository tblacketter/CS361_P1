package fa.dfa;

import fa.State;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Implementation of a Deterministic Finite Automaton (DFA).
 * Implements the DFAInterface and stores the DFA's 5-tuple:
 * (Q, Sigma, delta, q0, F) where:
 *  Q is the set of states (LinkedHashSet to preserve insertion order)
 *  Sigma is the alphabet (LinkedHashSet to preserve insertion order)
 *  delta is the transition function (stored per-state in DFAState)
 *  q0 is the start state
 *  F is the set of final/accepting states
 *
 *
 * @author Lance, Teryn
 */
public class DFA implements DFAInterface {

    /** The set of all states, in insertion order. */
    private LinkedHashSet<DFAState> states;

    /** The input alphabet, in insertion order. */
    private LinkedHashSet<Character> sigma;

    /** The name of the start state. */
    private String startState;

    /** The set of accepting state names, in insertion order. */
    private LinkedHashSet<String> finalStates;

    /**
     * Constructs an empty DFA with no states, no alphabet, no start state,
     * and no final states.
     */
    public DFA() {
        states = new LinkedHashSet<>();
        sigma = new LinkedHashSet<>();
        startState = null;
        finalStates = new LinkedHashSet<>();
    }

    /**
     * Adds a new state with the given name to the DFA.
     *
     * @param name the label for the new state
     * @return true if the state was added successfully; false if a state with that name already exists
     */
    @Override
    public boolean addState(String name) {
        // Check for duplicates
        if (getState(name) != null) {
            return false;
        }
        states.add(new DFAState(name));
        return true;
    }

    /**
     * Marks an existing state as a final (accepting) state.
     *
     * @param name the label of the state to mark as final
     * @return true if successful; false if no state with that name exists
     */
    @Override
    public boolean setFinal(String name) {
        if (getState(name) == null) {
            return false;
        }
        finalStates.add(name);
        return true;
    }

    /**
     * Sets the start state of the DFA.
     *
     * @param name the label of the state to set as the start state
     * @return true if successful; false if no state with that name exists
     */
    @Override
    public boolean setStart(String name) {
        if (getState(name) == null) {
            return false;
        }
        startState = name;
        return true;
    }

    /**
     * Adds a symbol to the DFA's alphabet (Sigma).
     *
     * @param symbol the character to add to the alphabet
     */
    @Override
    public void addSigma(char symbol) {
        sigma.add(symbol);
    }

    /**
     * Simulates the DFA on input string s.
     * The special string "e" represents the empty string epsilon.
     *
     * @param s the input string to test
     * @return true if the DFA accepts s; false otherwise
     */
    @Override
    public boolean accepts(String s) {
        if (startState == null) {
            return false;
        }

        String current = startState;

        // Handle empty string "e" as epsilon
        if (s.equals("e")) {
            return finalStates.contains(current);
        }

        // Process each character
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!sigma.contains(c)) {
                return false;
            }
            DFAState currentState = (DFAState) getState(current);
            if (currentState == null) {
                return false;
            }
            String next = currentState.getTransition(c);
            if (next == null) {
                return false;
            }
            current = next;
        }

        return finalStates.contains(current);
    }

    /**
     * Returns the alphabet of this DFA.
     *
     * @return a Set of characters representing Sigma
     */
    @Override
    public Set<Character> getSigma() {
        return sigma;
    }

    /**
     * Returns the state with the given name, or null if it doesn't exist.
     *
     * @param name the name of the state to retrieve
     * @return the State object with the given name, or null
     */
    @Override
    public State getState(String name) {
        for (DFAState s : states) {
            if (s.getName().equals(name)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Determines whether the state with the given name is a final state.
     *
     * @param name the name of the state
     * @return true if the state exists and is final; false otherwise
     */
    @Override
    public boolean isFinal(String name) {
        return finalStates.contains(name);
    }

    /**
     * Determines whether the state with the given name is the start state.
     *
     * @param name the name of the state
     * @return true if the state exists and is the start state; false otherwise
     */
    @Override
    public boolean isStart(String name) {
        if (startState == null) return false;
        return startState.equals(name);
    }

    /**
     * Adds a transition to the DFA's transition function delta.
     *
     * @param fromState the label of the source state
     * @param toState   the label of the destination state
     * @param onSymb    the input symbol triggering this transition
     * @return true if successful; false if either state doesn't exist or the symbol is not in Sigma
     */
    @Override
    public boolean addTransition(String fromState, String toState, char onSymb) {
        // Validate both states exist and symbol is in alphabet
        if (getState(fromState) == null || getState(toState) == null || !sigma.contains(onSymb)) {
            return false;
        }
        DFAState from = (DFAState) getState(fromState);
        from.addTransition(onSymb, toState);
        return true;
    }

    /**
     * Creates a deep copy of this DFA with the transition labels for symb1 and symb2 swapped.
     * The original DFA is not modified.
     *
     * @param symb1 the first symbol to swap
     * @param symb2 the second symbol to swap
     * @return a new DFA that is a deep copy with those transitions swapped
     */
    @Override
    public DFA swap(char symb1, char symb2) {
        DFA copy = new DFA();

        // Copy alphabet
        for (char c : sigma) {
            copy.addSigma(c);
        }

        // Copy states
        for (DFAState s : states) {
            copy.addState(s.getName());
        }

        // Copy start and final states
        if (startState != null) {
            copy.setStart(startState);
        }
        for (String f : finalStates) {
            copy.setFinal(f);
        }

        // Copy transitions with symb1 and symb2 swapped
        for (DFAState s : states) {
            for (char c : sigma) {
                String dest = s.getTransition(c);
                if (dest != null) {
                    // Determine which symbol to use in the copy
                    char copySymbol;
                    if (c == symb1) {
                        copySymbol = symb2;
                    } else if (c == symb2) {
                        copySymbol = symb1;
                    } else {
                        copySymbol = c;
                    }
                    copy.addTransition(s.getName(), dest, copySymbol);
                }
            }
        }

        return copy;
    }

    /**
     * Returns a textual representation of this DFA showing all 5-tuple components.
     * States and alphabet symbols appear in insertion order.
     *
     * @return a formatted string representation of the DFA
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Q = { states }
        sb.append("Q = { ");
        for (DFAState s : states) {
            sb.append(s.getName()).append(" ");
        }
        sb.append("}\n");

        // Sigma = { symbols }
        sb.append("Sigma = { ");
        for (char c : sigma) {
            sb.append(c).append(" ");
        }
        sb.append("}\n");

        // delta table header
        sb.append("delta =\n");
        sb.append("\t\t");
        for (char c : sigma) {
            sb.append(c).append("\t");
        }
        sb.append("\n");

        // delta table rows
        for (DFAState s : states) {
            sb.append("\t").append(s.getName()).append("\t");
            for (char c : sigma) {
                String dest = s.getTransition(c);
                sb.append(dest != null ? dest : "?").append("\t");
            }
            sb.append("\n");
        }

        // q0 = start
        sb.append("q0 = ").append(startState).append("\n");

        // F = { final states }
        sb.append("F = { ");
        for (String f : finalStates) {
            sb.append(f).append(" ");
        }
        sb.append("}");

        return sb.toString();
    }
}