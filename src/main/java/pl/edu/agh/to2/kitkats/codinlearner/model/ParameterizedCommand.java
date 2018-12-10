package pl.edu.agh.to2.kitkats.codinlearner.model;

public class ParameterizedCommand {

    private Command command;
    private int parameter;

    public ParameterizedCommand(Command command, int parameter) {
        this.command = command;
        this.parameter = parameter;
    }

    public void setDefaultsValues(){
        switch(this.command){
            case RIGHT: this.setParameter(90); break;
            case LEFT: this.setParameter(90); break;
            case FORWARD: this.setParameter(1); break;
            case BACK: this.setParameter(1); break;
            case WRONG: this.setParameter(0); break;
            case EMPTY: this.setParameter(0); break;
            case REPEAT: this.setParameter(0); break;
        }

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
