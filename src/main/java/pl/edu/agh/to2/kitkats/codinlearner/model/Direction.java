package pl.edu.agh.to2.kitkats.codinlearner.model;

public enum Direction {
    NORTH, SOUTH, WEST, EAST;

    public Direction onTheRight(){
        switch (this){
            case NORTH: return EAST;
            case EAST: return SOUTH;
            case SOUTH: return WEST;
            case WEST: return NORTH;
            default: return EAST;
        }
    }

    public Direction onTheLeft(){
        switch (this){
            case NORTH: return WEST;
            case EAST: return NORTH;
            case SOUTH: return EAST;
            case WEST: return SOUTH;
            default: return EAST;
        }
    }
}
