package pl.edu.agh.to2.kitkats.codinlearner.command;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MoveRegistry {

	private ObservableList<Move> moveStack = FXCollections
			.observableArrayList();

	private ObservableList<Move> undoMoveStack = FXCollections.observableArrayList();

	public void execute(Move move) {
		move.execute();
		moveStack.add(move);
		this.undoMoveStack = FXCollections.observableArrayList();
	}

	public void redo() {
		if(undoMoveStack.size() != 0) {
			Move lastMove = undoMoveStack.get(undoMoveStack.size()-1);
			this.undoMoveStack.remove(undoMoveStack.size()-1);
			this.moveStack.add(lastMove);
			lastMove.redo();
		}
	}

	public void undo() {
		if(moveStack.size() != 0) {
			Move lastMove = moveStack.get(moveStack.size()-1);
			moveStack.remove(moveStack.size()-1);
			this.undoMoveStack.add(lastMove);
			lastMove.undo();
		}
	}

	public ObservableList<Move> getMoveStack() {
		return moveStack;
	}

	public void redraw(){
		for(Move move : this.moveStack){
			move.redraw();
		}
	}

	public void reset(){
		this.moveStack = FXCollections
				.observableArrayList();
		this.undoMoveStack = FXCollections
				.observableArrayList();
	}
}
