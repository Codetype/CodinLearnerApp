package pl.edu.agh.to2.kitkats.codinlearner.parser;

import java.util.*;

public class InstructionHistory {

    private List<String> textInputs;
//    private Deque<String> textInputs;
    private ListIterator<String> iter;

    public InstructionHistory() {
//        this.textInputs = new Stack<>();
        this.textInputs = new LinkedList<>();
//        this.textInputs = new ArrayDeque<>();
        iter = textInputs.listIterator(textInputs.size());
        reset();
//        List<String>a;
//        a.pre
    }

    public void add(String s) {
        textInputs.add(s);
    }

    public String next() {
        if (iter.hasNext()) {
            return iter.next();
        } else {
            return "";
        }
    }

    public String previous() {
        if (iter.hasPrevious()) {
            return iter.previous();
        } else {
            return "";
        }
    }

    public void reset() {
        iter = textInputs.listIterator(textInputs.size());
    }

}
