package pl.edu.agh.to2.kitkats.codinlearner.level;

import pl.edu.agh.to2.kitkats.codinlearner.model.Command;
import java.util.List;

public class LevelCheck {

    public static boolean check(Level level, List<Command> commands){
        //TODO commands validation for level

        //simple check for now
        return level.task.equals(commands);
//        return true;
    }
}
