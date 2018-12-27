package pl.edu.agh.to2.kitkats.codinlearner.level;

import com.vividsolutions.jts.algorithm.Angle;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.math.Vector2D;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import pl.edu.agh.to2.kitkats.codinlearner.model.Instruction;
import pl.edu.agh.to2.kitkats.codinlearner.model.MoveGraph;
import pl.edu.agh.to2.kitkats.codinlearner.model.ParameterizedInstruction;
import pl.edu.agh.to2.kitkats.codinlearner.model.RepeatedInstructions;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.parser.*;
import java.util.Iterator;

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
        //level parsing
        JSONParser parser = new JSONParser();
        try{
            Object obj = parser.parse(new FileReader(
                    "./src/main/resources/MoveConfiguration/Levels.json"));
            JSONObject jsonObj = (JSONObject) obj;
            JSONArray jsonLevels = (JSONArray) jsonObj.get("levels");

            for(Object level : jsonLevels){
                JSONObject jsonLevel = (JSONObject) level;
                final String desc = (String) jsonLevel.get("description");
                final Long repeats = (Long) jsonLevel.get("repeats");

                JSONArray moves = (JSONArray) jsonLevel.get("moves");
                Iterator<String> movesIterator = moves.iterator();
                JSONArray units = (JSONArray) jsonLevel.get("units");
                Iterator<Long> unitsIterator = units.iterator();

                RepeatedInstructions instructions = new RepeatedInstructions(repeats.intValue());
                while(movesIterator.hasNext() && unitsIterator.hasNext()){
                    instructions.add(parseInstructionFromString(movesIterator.next()),unitsIterator.next().intValue());
                }

                newLevel(
                        instructions.getAll(),
                        desc
                );
            }
        } catch (Exception e){
            System.out.println("Error during parsing JSON file.");
            //e.printStackTrace();
        }
        return levels;
    }

    public Instruction parseInstructionFromString(String value) {
        Instruction instruction = Instruction.WRONG;
        switch(value){
            case "FORWARD": instruction = Instruction.FORWARD; break;
            case "LEFT": instruction = Instruction.LEFT; break;
            case "RIGHT": instruction = Instruction.RIGHT; break;
            default: break;
        }
        return instruction;
    }
}
