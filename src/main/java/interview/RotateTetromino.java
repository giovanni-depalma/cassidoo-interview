package interview;

import interview.util.BitMatrixHelper;
import interview.util.BitUtility;
import lombok.Value;

import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class RotateTetromino {

    enum Shape{
        T,J,L,Z,S,I,O
    }

    enum Direction {
        CLOCKWISE, COUNTER_CLOCKWISE;
    }

    enum State{
        SPAWN, LEFT, DOUBLE_ROTATION, RIGHT;

        public State next(Direction d){
            int offset = Direction.CLOCKWISE.equals(d) ? 1 : -1;
            State[] states = State.values();
            int idx = (this.ordinal()+offset) % states.length;
            if(idx < 0)
                idx += states.length;
            return states[idx];
        }
    }


    @Value
    static class ShapeEntry{
        Shape shape;
        State state;
    }

    private static final int[][][] OFFSET_DATA = new int[][][]{
            {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}},    //SPAWN
            {{0, 0}, {1, 0}, {1, -1}, {0, 2}, {1, 2}},   //RIGHT
            {{0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}},    //DOUBLE_ROTATION
            {{0, 0}, {-1, 0}, {-1, -1}, {0, 2}, {-1, 2}} //LEFT
    };

    private Map<Integer, ShapeEntry> mapShapeByInteger;
    private Map<ShapeEntry, Integer> mapIntegerByShape;
    private BitMatrixHelper bitMatrixHelper;

    public RotateTetromino(){
        bitMatrixHelper = new BitMatrixHelper();
        mapShapeByInteger = new HashMap<>();
        mapIntegerByShape = new HashMap<>();

        addShape(114, new ShapeEntry(Shape.T, State.SPAWN));
        addShape(305, new ShapeEntry(Shape.T, State.LEFT));
        addShape(39, new ShapeEntry(Shape.T, State.DOUBLE_ROTATION));
        addShape(562, new ShapeEntry(Shape.T, State.RIGHT));

        addShape(275, new ShapeEntry(Shape.J, State.SPAWN));
        addShape(71, new ShapeEntry(Shape.J, State.LEFT));
        addShape(802, new ShapeEntry(Shape.J, State.DOUBLE_ROTATION));
        addShape(113, new ShapeEntry(Shape.J, State.RIGHT));

        addShape(547, new ShapeEntry(Shape.L, State.SPAWN));
        addShape(116, new ShapeEntry(Shape.L, State.LEFT));
        addShape(785, new ShapeEntry(Shape.L, State.DOUBLE_ROTATION));
        addShape(23, new ShapeEntry(Shape.L, State.RIGHT));

        addShape(99, new ShapeEntry(Shape.Z, State.SPAWN));
        addShape(306, new ShapeEntry(Shape.Z, State.LEFT));

        addShape(54, new ShapeEntry(Shape.S, State.SPAWN));
        addShape(561, new ShapeEntry(Shape.S, State.LEFT));

        addShape(15, new ShapeEntry(Shape.I, State.SPAWN));
        addShape(4369, new ShapeEntry(Shape.I, State.LEFT));

        addShape(51, new ShapeEntry(Shape.O, State.SPAWN));
    }

    private void addShape(int value, ShapeEntry shape){
        mapShapeByInteger.put(value, shape);
        mapIntegerByShape.put(shape, value);
    }

    public void printAllShape() {
        for(Map.Entry<Integer, ShapeEntry> entry: mapShapeByInteger.entrySet()){
            printShape(entry.getKey(),entry.getValue());
        }
    }

    public void printShape(int value, ShapeEntry shape) {
        System.out.println("--------"+value+":" +shape+"--------");
        int[][] matrix = BitUtility.toBitMatrix(value,4,4);
        for(int[]row: matrix){
            System.out.println(Arrays.toString(row));
        }
        System.out.println("--------");
    }

    public void printShape(ShapeEntry shape) {
        printShape(mapIntegerByShape.get(shape), shape);
    }


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
        BitSet bitSet = new BitSet();
        int cnt = 0;
        for (int i = grid.length - 1; i >= 0; i--){
            for(int j = grid[i].length - 1; j >= 0; j--){
                bitSet.set(cnt, grid[i][j] == 1);
                cnt++;
            }
        }
        int value = BitUtility.toInteger(bitSet);
        int right = bitMatrixHelper.mostRight(value, grid.length, grid[0].length);
        int bottomRight = bitMatrixHelper.mostBottom(right, grid.length);
        ShapeEntry original = mapShapeByInteger.get(bottomRight);
        System.out.println(original);
        printShape(bottomRight, original);
        ShapeEntry rotated = new ShapeEntry(original.shape, original.state.next(direction));
        int valueRotated = mapIntegerByShape.get(rotated);
        printShape(valueRotated, rotated);
        System.out.println("riight" + bitMatrixHelper.getRightShift());
        valueRotated = valueRotated << bitMatrixHelper.getRightShift();
        printShape(valueRotated, rotated);
        System.out.println("bottom" + bitMatrixHelper.getBottomShift());
        valueRotated = valueRotated << bitMatrixHelper.getBottomShift()*4;
        printShape(valueRotated, rotated);
       // int[][] offsets = getOffsets(direction);
       // System.out.println(Arrays.deepToString(offsets));
    }


    public static void main(String[] args) {
        int[][] grid = new int[][]{
                {0, 1, 0, 0},
                {0, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        RotateTetromino t = new RotateTetromino();
        for(State state: State.values()){
            //t.printShape(new ShapeEntry(Shape.J, state));
        }

        new RotateTetromino().rotateTetromino(grid, Direction.CLOCKWISE);
    }


}
