package pl.edu.agh.to2.kitkats.codinlearner.command;

import java.util.List;

public interface Command {

	void execute();

	void undo();

	void redo();

	String getName();

	void redraw();
}
