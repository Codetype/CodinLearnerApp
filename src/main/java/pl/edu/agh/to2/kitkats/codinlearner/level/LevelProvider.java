package pl.edu.agh.to2.kitkats.codinlearner.level;

import com.vividsolutions.jts.math.Vector2D;
import javafx.util.Pair;
import pl.edu.agh.to2.kitkats.codinlearner.model.Command;
import pl.edu.agh.to2.kitkats.codinlearner.model.Cursor;
import pl.edu.agh.to2.kitkats.codinlearner.model.DoublePoint;
import pl.edu.agh.to2.kitkats.codinlearner.model.MoveGraph;

import java.util.ArrayList;
import java.util.List;

public class LevelProvider {

    private static List<Level> levels = new ArrayList<>();
    private float moveStep;
//    private Cursor cursor;
//    private Vector2D startVector;

    public LevelProvider(float moveStep) {
        this.moveStep = moveStep;
    }

    private void tmp(Vector2D moveVector) {

    }

//    private

    private Level newLevel(List<Command> commands, String description) {
        MoveGraph graph = new MoveGraph();
//        double moveStep = startVector.length();
//        Vector2D vector = new Vector2D();
//        Vector2D vector = startVector;
        DoublePoint from = new DoublePoint(0.0, 0.0);
        DoublePoint to = new DoublePoint(0.0, 0.0);

        for (Command command : commands) {
            int value = command.getValue();
            switch (command) {
                case FORWARD:
                    break;
                case BACK:
                    break;
                case LEFT:
                    break;
                case RIGHT:
                    break;
            }
        }

        Level result = new Level(graph, description);
        return result;
    }

    private void initializeLevels() {

    }

    public void getLevels() {

    }


    public void initializeLevels2() {
        // level 1
        int commandNumber = 2;
//        float lineLength = commandNumber * arena.getCursor().getMoveStep();
        MoveGraph task1 = new MoveGraph();
        task1.addVertex(0.0, 0.0, 50.0, 0.0);
        task1.addVertex(50.0, 0.0, 100.0, 0.0);
        task1.addVertex(100.0, 0.0, 100.0, 50.0);
        task1.addVertex(100.0, 50.0, 100.0, 100.0);
        task1.addVertex(100.0, 100.0, 50.0, 100.0);
        task1.addVertex(50.0, 100.0, 0.0, 100.0);
        task1.addVertex(0.0, 100.0, 0.0, 50.0);
        task1.addVertex(0.0, 50.0, 0.0, 0.0);
        Level l1 = new Level(task1, "Draw a line (length: " + commandNumber + ")");
//        levelManager.addLevel(l1);
    }
}
