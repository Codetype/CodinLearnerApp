package pl.edu.agh.to2.kitkats.codinlearner.parser;

import javafx.scene.input.KeyCode;

import java.util.*;

public class InstructionHistory {

    private List<String> textInputs;
    private ListIterator<String> iter;
    private KeyCode lastKeyCode;
    private String lastConsumed;

    public void setLastKeyCode(KeyCode keyCode) {
        this.lastKeyCode = keyCode;
    }

    public InstructionHistory() {
        this.textInputs = new LinkedList<>();
        iter = textInputs.listIterator(textInputs.size());
        lastKeyCode = null;
        resetIterator();
    }

    public void add(String s) {
        textInputs.add(s);
    }

    public String next() {
        if (iter.hasNext()) {
            if (lastKeyCode == KeyCode.UP) {
                iter.next();
            }

            if (iter.hasNext()) {
                lastConsumed = iter.next();
                return lastConsumed;
            } else {
                lastConsumed = null;
                return "";
            }
        } else {
            lastConsumed = null;
            return "";
        }
    }

    public String previous() {
        if (iter.hasPrevious()) {
            if (lastKeyCode == KeyCode.DOWN && lastConsumed != null) {
                iter.previous();
            }

            lastConsumed = iter.previous();
            return lastConsumed;
        } else {
            lastConsumed = null;
            if (iter.hasNext()) {
                return null;
            } else {
                return "";
            }
        }
    }

    public boolean isIterated() {
        return lastKeyCode != null;
    }

    public void resetIterator() {
        lastKeyCode = null;
        iter = textInputs.listIterator(textInputs.size());
    }

}
