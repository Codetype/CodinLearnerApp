package pl.edu.agh.to2.kitkats.codinlearner.model;

public class ParameterizedCommand {

    private final Command command;
    private int parameter;

    public ParameterizedCommand(Command command, int parameter) {
        this.command = command;
        this.parameter = parameter;
    }

    public ParameterizedCommand(Command command) {
        this.command = command;
        this.parameter = getDefaultParameter(this.command);
    }

    private int getDefaultParameter(Command command) {
        int result;
        switch (command) {
            case RIGHT: result = 90; break;
            case LEFT: result = 90; break;
            case FORWARD: result = 1; break;
            case BACK: result = 1; break;
            case WRONG: result = 0; break;
            case EMPTY: result = 0; break;
            case REPEAT: result = 0; break;
            default: result = 0; break;
        }
        return result;
    }

    public Command getCommand() {
        return command;
    }

    public int getParameter() {
        return parameter;
    }

    public void setParameter(int parameter) {
        this.parameter = parameter;
    }
}
