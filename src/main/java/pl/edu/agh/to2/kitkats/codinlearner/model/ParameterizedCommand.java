package pl.edu.agh.to2.kitkats.codinlearner.model;

public class ParameterizedCommand {

    private Command command;
    private int parameter;

    public ParameterizedCommand(Command command, int parameter) {
        this.command = command;
        this.parameter = parameter;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public int getParameter() {
        return parameter;
    }

    public void setParameter(int parameter) {
        this.parameter = parameter;
    }
}
