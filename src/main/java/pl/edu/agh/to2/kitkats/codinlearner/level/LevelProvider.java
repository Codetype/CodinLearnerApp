package pl.edu.agh.to2.kitkats.codinlearner.level;

import com.vividsolutions.jts.algorithm.Angle;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.math.Vector2D;
import pl.edu.agh.to2.kitkats.codinlearner.model.Command;
import pl.edu.agh.to2.kitkats.codinlearner.model.MoveGraph;
import pl.edu.agh.to2.kitkats.codinlearner.model.ParameterizedCommand;
import pl.edu.agh.to2.kitkats.codinlearner.model.RepeatedCommands;
import java.util.ArrayList;
import java.util.List;

public class LevelProvider {

    private List<Level> levels;
    private double moveStep;

    public LevelProvider(float moveStep) {
        this.moveStep = moveStep;
        this.levels = new ArrayList<>();
    }

    private void newLevel(List<ParameterizedCommand> commands, String description) {
        MoveGraph graph = new MoveGraph();
        Coordinate from = new Coordinate(0.0, 0.0);
        Coordinate to = new Coordinate(0.0, 0.0);
        Vector2D moveVector = new Vector2D(moveStep, 0);

        for (ParameterizedCommand parameterizedCommand : commands) {
            Command command = parameterizedCommand.getCommand();
            int value = parameterizedCommand.getParameter();

            if (command == Command.LEFT) {
                moveVector = moveVector.rotate(Angle.toRadians(-value));
            } else if (command == Command.RIGHT) {
                moveVector = moveVector.rotate(Angle.toRadians(value));
            } else {
                if (command != Command.FORWARD && command != Command.BACK) {
                    continue;
                }
                for (int i = 0; i < value; i++) {
                    switch (command) {
                        case FORWARD:
                            to.x += moveVector.getX();
                            to.y += moveVector.getY();
                            break;
                        case BACK:
                            to.x -= moveVector.getX();
                            to.y -= moveVector.getY();
                            break;
                    }
                    graph.addVertex(from.x, from.y, to.x, to.y);
                    from.setCoordinate(to);
                }
            }
        }

        levels.add(new Level(graph, description));
    }

    public List<Level> getLevels() {
        RepeatedCommands level1 = new RepeatedCommands();
        level1.add(Command.FORWARD, 2);
        newLevel(
                level1.getAll(),
                "Draw a line (length: 2)"
        );

        RepeatedCommands level2 = new RepeatedCommands(2);
        level2.add(Command.FORWARD, 2);
        level2.add(Command.LEFT, 90);
        level2.add(Command.FORWARD, 1);
        level2.add(Command.LEFT, 90);
        newLevel(
                level2.getAll(),
                "Draw a rectangle (width: 2, height: 1)"
        );

        RepeatedCommands level3 = new RepeatedCommands(4);
        level3.add(Command.FORWARD, 1);
        level3.add(Command.LEFT, 90);
        newLevel(
                level3.getAll(),
                "Draw a square (size: 1)"
        );

        RepeatedCommands level4 = new RepeatedCommands(3);
        level4.add(Command.FORWARD, 1);
        level4.add(Command.LEFT, 120);
        newLevel(
                level4.getAll(),
                "Draw an equilateral triangle (size: 1)"
        );

        RepeatedCommands level5 = new RepeatedCommands(5);
        level5.add(Command.FORWARD, 1);
        level5.add(Command.BACK, 1);
        level5.add(Command.RIGHT, 72);
        newLevel(
                level5.getAll(),
                "Draw an asterisk (size: 1, arms: 5)"
        );

        return levels;
    }

}
