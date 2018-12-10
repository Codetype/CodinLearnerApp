package pl.edu.agh.to2.kitkats.codinlearner.level;

import com.vividsolutions.jts.algorithm.Angle;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.math.Vector2D;
import javafx.util.Pair;
import pl.edu.agh.to2.kitkats.codinlearner.model.Command;
import pl.edu.agh.to2.kitkats.codinlearner.model.MoveGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LevelProvider {

    private List<Level> levels;
    private double moveStep;

    public LevelProvider(float moveStep) {
        this.moveStep = moveStep;
        this.levels = new ArrayList<>();
    }

    private void newLevel(List<Pair<Command, Integer>> commands, String description) {
        MoveGraph graph = new MoveGraph();
        Coordinate from = new Coordinate(0.0, 0.0);
        Coordinate to = new Coordinate(0.0, 0.0);
        Vector2D moveVector = new Vector2D(moveStep, 0);

        for (Pair<Command, Integer> pair : commands) {
            Command command = pair.getKey();
            int value = pair.getValue();

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
        newLevel(
                Collections.singletonList(new Pair<>(Command.FORWARD, 2)),
                "Draw a line (length: 2)"
        );
        newLevel(
                Arrays.asList(
                        new Pair<>(Command.FORWARD, 2),
                        new Pair<>(Command.LEFT, 90),
                        new Pair<>(Command.FORWARD, 1),
                        new Pair<>(Command.LEFT, 90),
                        new Pair<>(Command.FORWARD, 2),
                        new Pair<>(Command.LEFT, 90),
                        new Pair<>(Command.FORWARD, 1),
                        new Pair<>(Command.LEFT, 90)
                ),
                "Draw a rectangle (width: 2, height: 1)"
        );
        newLevel(
                Arrays.asList(
                        new Pair<>(Command.FORWARD, 1),
                        new Pair<>(Command.LEFT, 90),
                        new Pair<>(Command.FORWARD, 1),
                        new Pair<>(Command.LEFT, 90),
                        new Pair<>(Command.FORWARD, 1),
                        new Pair<>(Command.LEFT, 90),
                        new Pair<>(Command.FORWARD, 1),
                        new Pair<>(Command.LEFT, 90)
                ),
                "Draw a square (size: 1)"
        );
        newLevel(
                Arrays.asList(
                        new Pair<>(Command.FORWARD, 1),
                        new Pair<>(Command.LEFT, 120),
                        new Pair<>(Command.FORWARD, 1),
                        new Pair<>(Command.LEFT, 120),
                        new Pair<>(Command.FORWARD, 1),
                        new Pair<>(Command.LEFT, 120)
                ),
                "Draw an equilateral triangle (size: 1)"
        );
        newLevel(
                Arrays.asList(
                        new Pair<>(Command.FORWARD, 1),
                        new Pair<>(Command.BACK, 1),
                        new Pair<>(Command.RIGHT, 72),
                        new Pair<>(Command.FORWARD, 1),
                        new Pair<>(Command.BACK, 1),
                        new Pair<>(Command.RIGHT, 72),
                        new Pair<>(Command.FORWARD, 1),
                        new Pair<>(Command.BACK, 1),
                        new Pair<>(Command.RIGHT, 72),
                        new Pair<>(Command.FORWARD, 1),
                        new Pair<>(Command.BACK, 1),
                        new Pair<>(Command.RIGHT, 72),
                        new Pair<>(Command.FORWARD, 1),
                        new Pair<>(Command.BACK, 1),
                        new Pair<>(Command.RIGHT, 72)
                ),
                "Draw an asterisk (size: 1, arms: 5)"
        );

        return levels;
    }

}
