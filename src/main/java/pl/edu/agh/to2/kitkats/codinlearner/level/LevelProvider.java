package pl.edu.agh.to2.kitkats.codinlearner.level;

import com.vividsolutions.jts.algorithm.Angle;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.math.Vector2D;
import pl.edu.agh.to2.kitkats.codinlearner.model.Command;
import pl.edu.agh.to2.kitkats.codinlearner.model.MoveGraph;

import java.util.ArrayList;
import java.util.List;

public class LevelProvider {

    private List<Level> levels;
    private double moveStep;

    public LevelProvider(float moveStep) {
        this.moveStep = moveStep;
        this.levels = new ArrayList<>();
    }

    private void newLevel(List<Command> commands, String description) {
        MoveGraph graph = new MoveGraph();
        Coordinate from = new Coordinate(0.0, 0.0);
        Coordinate to = new Coordinate(0.0, 0.0);
        Vector2D moveVector = new Vector2D(moveStep, 0);

        for (Command command : commands) {
            int value = command.getValue();

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

    private void initializeLevels() {
    }

}
