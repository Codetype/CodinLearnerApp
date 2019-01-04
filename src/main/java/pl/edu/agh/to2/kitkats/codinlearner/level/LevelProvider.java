package pl.edu.agh.to2.kitkats.codinlearner.level;

import com.vividsolutions.jts.algorithm.Angle;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.math.Vector2D;
import pl.edu.agh.to2.kitkats.codinlearner.model.Instruction;
import pl.edu.agh.to2.kitkats.codinlearner.model.MoveGraph;
import pl.edu.agh.to2.kitkats.codinlearner.model.ParameterizedInstruction;
import pl.edu.agh.to2.kitkats.codinlearner.model.RepeatedInstructions;
import java.util.ArrayList;
import java.util.List;

public class LevelProvider {

    private List<Level> levels;
    private double moveStep;

    public LevelProvider(float moveStep) {
        this.moveStep = moveStep;
        this.levels = new ArrayList<>();
    }

    private void newLevel(List<ParameterizedInstruction> commands, String description) {
        MoveGraph graph = new MoveGraph();
        Coordinate from = new Coordinate(0.0, 0.0);
        Coordinate to = new Coordinate(0.0, 0.0);
        Vector2D moveVector = new Vector2D(moveStep, 0);

        for (ParameterizedInstruction parameterizedInstruction : commands) {
            Instruction instruction = parameterizedInstruction.getInstruction();
            int value = parameterizedInstruction.getParameter();

            if (instruction == Instruction.LEFT) {
                moveVector = moveVector.rotate(Angle.toRadians(-value));
            } else if (instruction == Instruction.RIGHT) {
                moveVector = moveVector.rotate(Angle.toRadians(value));
            } else {
                if (instruction != Instruction.FORWARD && instruction != Instruction.BACK) {
                    continue;
                }
                for (int i = 0; i < value; i++) {
                    switch (instruction) {
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
        RepeatedInstructions level1 = new RepeatedInstructions();
        level1.add(Instruction.FORWARD, 2);
        newLevel(
                level1.getAll(),
                "Draw a line (length: 2)"
        );

        RepeatedInstructions level2 = new RepeatedInstructions(2);
        level2.add(Instruction.FORWARD, 2);
        level2.add(Instruction.LEFT, 90);
        level2.add(Instruction.FORWARD, 1);
        level2.add(Instruction.LEFT, 90);
        newLevel(
                level2.getAll(),
                "Draw a rectangle (width: 2, height: 1)"
        );

        RepeatedInstructions level3 = new RepeatedInstructions(4);
        level3.add(Instruction.FORWARD, 1);
        level3.add(Instruction.LEFT, 90);
        newLevel(
                level3.getAll(),
                "Draw a square (size: 1)"
        );

        RepeatedInstructions level4 = new RepeatedInstructions(3);
        level4.add(Instruction.FORWARD, 1);
        level4.add(Instruction.LEFT, 120);
        newLevel(
                level4.getAll(),
                "Draw an equilateral triangle (size: 1)"
        );

        RepeatedInstructions level5 = new RepeatedInstructions(5);
        level5.add(Instruction.FORWARD, 1);
        level5.add(Instruction.BACK, 1);
        level5.add(Instruction.RIGHT, 72);
        newLevel(
                level5.getAll(),
                "Draw an asterisk (size: 1, arms: 5)"
        );

        return levels;
    }

}
