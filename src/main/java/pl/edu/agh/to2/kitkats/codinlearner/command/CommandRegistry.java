package pl.edu.agh.to2.kitkats.codinlearner.command;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CommandRegistry {

	private ObservableList<Command> commandStack = FXCollections
			.observableArrayList();

	private ObservableList<Command> undoCommandStack = FXCollections.observableArrayList();

	public void executeCommand(Command command) {
		command.execute();
		commandStack.add(command);
		this.undoCommandStack = FXCollections.observableArrayList();
	}

	public void redo() {
		if(undoCommandStack.size() != 0) {
			Command lastCommand = undoCommandStack.get(undoCommandStack.size()-1);
			this.undoCommandStack.remove(undoCommandStack.size()-1);
			this.commandStack.add(lastCommand);
			lastCommand.redo();
		}
	}

	public void undo() {
		if(commandStack.size() != 0) {
			Command lastCommand = commandStack.get(commandStack.size()-1);
			commandStack.remove(commandStack.size()-1);
			this.undoCommandStack.add(lastCommand);
			lastCommand.undo();
		}
	}

	public ObservableList<Command> getCommandStack() {
		return commandStack;
	}

	public void redraw(){
		for(Command command : this.commandStack){
			command.redraw();
		}
	}
}
