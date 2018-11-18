package pl.edu.agh.to2.kitkats.codinlearner.level;

import pl.edu.agh.to2.kitkats.codinlearner.model.Command;
import java.util.List;

public class LevelCheck {

    public static boolean check(Level level, List<Command> commands){
        //TODO commands validation for level

        //simple check for now
        int index = 0;
        for(Command command : commands){
            if(level.task.get(index).equals(command)) index++;
            else index = 0;
            if(index == level.task.size()) return true;
        }
        return false;
//        return true;
    }
}
