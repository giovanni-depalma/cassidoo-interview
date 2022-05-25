package interview;

import java.text.MessageFormat;
import java.util.*;

public class StartToEnd {
    enum CellType {
        FREE, START, END, WALL;
    }

    enum Movement {
        UP, RIGHT, DOWN, LEFT
    }

    record Item(int row, int col, List<Movement> movements) {

        Item move(Movement movement) {
            var newMovements = new ArrayList<>(this.movements);
            newMovements.add(movement);
            return switch (movement) {
                case UP -> new Item(row - 1, col, newMovements);
                case DOWN -> new Item(row + 1, col, newMovements);
                case RIGHT -> new Item(row, col + 1, newMovements);
                case LEFT -> new Item(row, col - 1, newMovements);
                default -> throw new IllegalArgumentException(MessageFormat.format("unexpected movement {0}", movement));
            };
        }

        boolean isInside(int height, int width) {
            return row >= 0 && col >= 0 && row < height && col < width;
        }
    }

    private static List<Movement> startToEnd(int[][] grid) {
        return findStart(grid).map(start -> {
            var queue = new LinkedList<Item>();
            queue.add(start);
            boolean[][] visited = new boolean[grid.length][grid[0].length];
            while (!queue.isEmpty()) {
                var item = queue.pollFirst();
                if (CellType.END.equals(cellType(grid[item.row()][item.col()])))
                    return item.movements();
                Arrays.stream(Movement.values()).map(item::move)
                        .filter(m -> m.isInside(grid.length, grid[0].length))
                        .filter(m -> isNotWall(m, grid))
                        .filter(m -> isNotVisited(m, visited))
                        .forEach(m -> {
                            visited[m.row][m.col()] = true;
                            queue.add(m);
                        });
            }
            return new ArrayList<Movement>();
        }).orElse(List.of());
    }

    private static Optional<Item> findStart(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (CellType.START.equals(cellType(grid[i][j])))
                    return Optional.of(new Item(i, j, List.of()));
            }
        }
        return Optional.empty();
    }

    private static boolean isNotWall(Item item, int[][] grid) {
        return !CellType.WALL.equals(cellType(grid[item.row()][item.col()]));
    }

    private static boolean isNotVisited(Item m, boolean[][] visited) {
        return visited[m.row()][m.col()] == false;
    }

    static CellType cellType(int value) {
        return CellType.values()[value];
    }

    public static void main(String[] args) {
        System.out.println(startToEnd(new int[][]{
                {1, 0, 0},
                {0, 0, 2}
        }));//[RIGHT, RIGHT, DOWN]
        System.out.println(startToEnd(new int[][]{
                {1, 3, 0},
                {0, 0, 2}
        }));//[DOWN, RIGHT, RIGHT]
        System.out.println(startToEnd(new int[][]{
                {0, 0, 3, 1},
                {3, 0, 0, 0},
                {0, 0, 0, 3},
                {2, 3, 3, 0}
        }));//[DOWN, LEFT, DOWN, LEFT, LEFT, DOWN]

        //start border cases
        System.out.println(startToEnd(new int[][]{
                {1, 3, 0},
                {3, 3, 2}
        }));//[] no possible movements
        System.out.println(startToEnd(new int[][]{
                {0, 0, 0},
                {0, 0, 0}
        }));//[] no start
        System.out.println(startToEnd(new int[][]{
                {0, 1, 0},
                {0, 0, 0}
        }));//[] no end
        System.out.println(startToEnd(new int[][]{}));//[] empty grid

    }


}
