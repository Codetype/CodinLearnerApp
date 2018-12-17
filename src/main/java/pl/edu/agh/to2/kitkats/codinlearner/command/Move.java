package pl.edu.agh.to2.kitkats.codinlearner.command;

public interface Move {

	void execute();

	void undo();

	void redo();

	String getName();

	void redraw();
}
