package fa.dfa;



import fa.FAInterface;
import fa.State;

import java.util.*;

/**
 * Deterministic Finite Automaton implementation.
 *
 * Implements DFAInterface.
 *
 * @author Lance Loper
 */
public class DFA implements DFAInterface {

    /* Alphabet */
    private LinkedHashSet<Character> sigma;

    /* States */
    private LinkedHashSet<DFAState> states;

    /* Lookup table for states by name */
    private Map<String, DFAState> stateMap;

    /* Start state */
    private DFAState startState;

    /* Final states */
    private LinkedHashSet<DFAState> finalStates;

    /**
     * Construct empty DFA.
     */
    public DFA() {
        sigma = new LinkedHashSet<>();
        states = new LinkedHashSet<>();
        stateMap = new HashMap<>();
        finalStates = new LinkedHashSet<>();
        startState = null;
    }

    /* ================= Alphabet ================= */

    @Override
    public void addSigma(char symbol) {

        if (sigma.contains(symbol)) {
            return;
        }

        sigma.add(symbol);
    }

    /* ================= States ================= */

    @Override
    public boolean addState(String name) {

        if (stateMap.containsKey(name)) {
            return false;
        }

        DFAState s = new DFAState(name);

        states.add(s);
        stateMap.put(name, s);

        return true;
    }

    @Override
    public boolean setStart(String name) {

        DFAState s = stateMap.get(name);

        if (s == null) {
            return false;
        }

        startState = s;
        return true;
    }

    @Override
    public boolean setFinal(String name) {

        DFAState s = stateMap.get(name);

        if (s == null) {
            return false;
        }

        finalStates.add(s);
        return true;
    }

    /* ================= Transitions ================= */

    @Override
    public boolean addTransition(String fromState,
                                 String toState,
                                 char symbol) {

        if (!sigma.contains(symbol)) {
            return false;
        }

        DFAState from = stateMap.get(fromState);
        DFAState to = stateMap.get(toState);

        if (from == null || to == null) {
            return false;
        }

        from.addTransition(symbol, to);

        return true;
    }

    /* ================= Acceptance ================= */

    @Override
    public boolean accepts(String input) {

        if (startState == null) {
            return false;
        }

        DFAState current = startState;

        /* Empty string */
        if (input.equals("e")) {
            return finalStates.contains(current);
        }

        for (int i = 0; i < input.length(); i++) {

            char c = input.charAt(i);

            if (!sigma.contains(c)) {
                return false;
            }

            DFAState next = current.getTransition(c);

            if (next == null) {
                return false;
            }

            current = next;
        }

        return finalStates.contains(current);
    }

    @Override
    public Set<Character> getSigma() {
        return Set.of();
    }

    @Override
    public State getState(String name) {
        return null;
    }

    @Override
    public boolean isFinal(String name) {
        return false;
    }

    @Override
    public boolean isStart(String name) {
        return false;
    }

    /* ================= Swap ================= */

    @Override
    public DFA swap(char symb1, char symb2) {

        DFA newDFA = new DFA();

        /* Copy alphabet */
        for (char c : sigma) {
            newDFA.addSigma(c);
        }

        /* Copy states */
        for (DFAState s : states) {
            newDFA.addState(s.getName());
        }

        /* Copy start */
        if (startState != null) {
            newDFA.setStart(startState.getName());
        }

        /* Copy finals */
        for (DFAState s : finalStates) {
            newDFA.setFinal(s.getName());
        }

        /* Copy transitions with swap */
        for (DFAState s : states) {

            DFAState from = s;

            for (Map.Entry<Character, DFAState> e
                    : s.getTransitions().entrySet()) {

                char c = e.getKey();
                DFAState to = e.getValue();

                char newChar = c;

                if (c == symb1) {
                    newChar = symb2;
                } else if (c == symb2) {
                    newChar = symb1;
                }

                newDFA.addTransition(
                        from.getName(),
                        to.getName(),
                        newChar
                );
            }
        }

        return newDFA;
    }

    /* ================= toString ================= */

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        /* Q */
        sb.append("Q = { ");
        for (DFAState s : states) {
            sb.append(s.getName()).append(" ");
        }
        sb.append("}\n");

        /* Sigma */
        sb.append("Sigma = { ");
        for (char c : sigma) {
            sb.append(c).append(" ");
        }
        sb.append("}\n");

        /* delta */
        sb.append("delta =\n");

        /* Header */
        sb.append("  ");
        for (char c : sigma) {
            sb.append(c).append(" ");
        }
        sb.append("\n");

        /* Rows */
        for (DFAState s : states) {

            sb.append(s.getName()).append(" ");

            for (char c : sigma) {

                DFAState dest = s.getTransition(c);

                if (dest != null) {
                    sb.append(dest.getName()).append(" ");
                } else {
                    sb.append("  ");
                }
            }

            sb.append("\n");
        }

        /* q0 */
        sb.append("q0 = ");

        if (startState != null) {
            sb.append(startState.getName());
        }

        sb.append("\n");

        /* F */
        sb.append("F = { ");

        for (DFAState s : finalStates) {
            sb.append(s.getName()).append(" ");
        }

        sb.append("}");

        return sb.toString();
    }
}
