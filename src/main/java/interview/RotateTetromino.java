package interview;

import interview.util.BitUtility;
import lombok.Value;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RotateTetromino {

    enum Shape{
        T,J,L,Z,S,I,O
    }

    enum State{
        SPAWN, RIGHT, DOUBLE_ROTATION, LEFT;
    }

    enum Direction {
        CLOCKWISE, COUNTER_CLOCKWISE
    }


    @Value
    class ShapeEntry{
        Shape shape;
        State state;
    }

    private Map<Integer, ShapeEntry> mapShape;

    public RotateTetromino(){
        mapShape = new HashMap<>();
        mapShape.put(114, new ShapeEntry(Shape.T, State.SPAWN));
        mapShape.put(305, new ShapeEntry(Shape.T, State.LEFT));
        mapShape.put(39, new ShapeEntry(Shape.T, State.DOUBLE_ROTATION));
        mapShape.put(562, new ShapeEntry(Shape.T, State.RIGHT));

        mapShape.put(275, new ShapeEntry(Shape.J, State.SPAWN));
        mapShape.put(71, new ShapeEntry(Shape.J, State.LEFT));
        mapShape.put(802, new ShapeEntry(Shape.J, State.DOUBLE_ROTATION));
        mapShape.put(113, new ShapeEntry(Shape.J, State.RIGHT));

        mapShape.put(547, new ShapeEntry(Shape.L, State.SPAWN));
        mapShape.put(116, new ShapeEntry(Shape.L, State.LEFT));
        mapShape.put(785, new ShapeEntry(Shape.L, State.DOUBLE_ROTATION));
        mapShape.put(23, new ShapeEntry(Shape.L, State.RIGHT));

        mapShape.put(99, new ShapeEntry(Shape.Z, State.SPAWN));
        mapShape.put(306, new ShapeEntry(Shape.Z, State.LEFT));

        mapShape.put(54, new ShapeEntry(Shape.S, State.SPAWN));
        mapShape.put(561, new ShapeEntry(Shape.S, State.LEFT));

        mapShape.put(15, new ShapeEntry(Shape.I, State.SPAWN));
        mapShape.put(4369, new ShapeEntry(Shape.I, State.LEFT));

        mapShape.put(51, new ShapeEntry(Shape.O, State.SPAWN));
    }

    private void printAllShape() {
        for(Map.Entry<Integer, ShapeEntry> entry: mapShape.entrySet()){
            System.out.println("--------"+entry.getKey()+": "+entry.getValue()+"--------");
            int[][] matrix = BitUtility.toBitMatrix(entry.getKey(),4,4);
            for(int[]row: matrix){
                System.out.println(Arrays.toString(row));
            }
            System.out.println("--------");
        }
    }


    private static final int[][][] OFFSET_DATA = new int[][][]{
            {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}},    //SPAWN
            {{0, 0}, {1, 0}, {1, -1}, {0, 2}, {1, 2}},   //RIGHT
            {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}},    //DOUBLE_ROTATION
            {{0, 0}, {-1, 0}, {-1, -1}, {0, 2}, {-1, 2}} //LEFT
    };

    public int[][] getOffsets(Direction direction){
        final State initialState = State.SPAWN;
        final State finalState = Direction.CLOCKWISE.equals(direction) ? State.RIGHT : State.LEFT;
        final int max = OFFSET_DATA[initialState.ordinal()].length;
        int[][] offsets = new int[max][];
        for(int i = 0; i < max; i++){
            int o = OFFSET_DATA[initialState.ordinal()][i][0]- OFFSET_DATA[finalState.ordinal()][i][0];
            offsets[i] = new int[]{
                    OFFSET_DATA[initialState.ordinal()][i][0]- OFFSET_DATA[finalState.ordinal()][i][0],
                    OFFSET_DATA[initialState.ordinal()][i][1]- OFFSET_DATA[finalState.ordinal()][i][1]
            };
        }
        return offsets;
    }

    public int rotate(Direction direction){
        //R'= (y, -x).
        return 0;
    }

    public void rotateTetromino(int[][] grid, Direction direction) {
        int[][] offsets = getOffsets(direction);
        System.out.println(Arrays.deepToString(offsets));
    }


    public static void main(String[] args) {
        int[][] grid = new int[][]{
                {0, 1, 0, 0},
                {0, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        new RotateTetromino().printAllShape();
                //rotateTetromino(grid, Direction.CLOCKWISE);
    }


}
