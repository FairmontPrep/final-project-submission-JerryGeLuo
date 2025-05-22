import java.util.*;

public class Client {
    // Global variable for the path
    static ArrayList<String> answerList = new ArrayList<>();
    
    // Directions: up, down, left, right
    static int[][] directions = { {-1,0}, {1,0}, {0,-1}, {0,1} };

    public static void main(String[] args) {
        // Sample maze
        ArrayList<ArrayList<Integer>> test_array_2 = new ArrayList<>();
        test_array_2.add(new ArrayList<>(Arrays.asList(1, 0, 0, 1, 0, 0, 0, 0 )));
        test_array_2.add(new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 0, 0 )));
        test_array_2.add(new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 1, 0 )));
        test_array_2.add(new ArrayList<>(Arrays.asList(9, 0, 0, 1, 0, 0, 0, 0 )));
        test_array_2.add(new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 0, 0 )));
        test_array_2.add(new ArrayList<>(Arrays.asList(0, 0, 0, 1, 0, 0, 0, 0 )));
        test_array_2.add(new ArrayList<>(Arrays.asList(0, 0, 0, 1, 2, 0, 0, 0 )));
        test_array_2.add(new ArrayList<>(Arrays.asList(1, 0, 0, 1, 1, 1, 1, 1 )));

        // Find the starting point (any wall '1')
        outer:
        for (int i = 0; i < test_array_2.size(); i++) {
            for (int j = 0; j < test_array_2.get(0).size(); j++) {
                if (isWall(i, j, test_array_2) && test_array_2.get(i).get(j) == 1) {
                    boolean[][] visited = new boolean[test_array_2.size()][test_array_2.get(0).size()];
                    if (dfs(i, j, test_array_2, visited, new ArrayList<>(), -1)) {
                        break outer;
                    }
                }
            }
        }

        // Print path coordinates
        System.out.println("Path coordinates:");
        System.out.println(answerList);

        // Print map with path
        printPathMap(test_array_2, answerList);
    }

    // Check if the point is on the edge of the grid (wall)
    public static boolean isWall(int i, int j, ArrayList<ArrayList<Integer>> grid) {
        return i == 0 || j == 0 || i == grid.size()-1 || j == grid.get(0).size()-1;
    }

    // Depth-First Search with 90-degree turn tracking
    public static boolean dfs(int i, int j, ArrayList<ArrayList<Integer>> grid, boolean[][] visited, ArrayList<String> path, int prevDir) {
        if (i < 0 || j < 0 || i >= grid.size() || j >= grid.get(0).size()) return false;
        if (grid.get(i).get(j) != 1 || visited[i][j]) return false;

        visited[i][j] = true;
        path.add("A[" + i + "][" + j + "]");

        // End condition: another wall cell that isn't the start
        if (path.size() > 1 && isWall(i, j, grid)) {
            answerList = new ArrayList<>(path);
            return true;
        }

        for (int d = 0; d < directions.length; d++) {
            int[] dir = directions[d];
            int ni = i + dir[0];
            int nj = j + dir[1];

            // Skip if direction is same (no turn yet)
            if (path.size() == 1 || d != prevDir) {
                if (dfs(ni, nj, grid, visited, path, d)) {
                    return true;
                }
            }
        }

        path.remove(path.size() - 1); // backtrack
        return false;
    }

    // Print grid with only path shown
    public static void printPathMap(ArrayList<ArrayList<Integer>> grid, ArrayList<String> path) {
        Set<String> pathSet = new HashSet<>(path);
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(0).size(); j++) {
                if (pathSet.contains("A[" + i + "][" + j + "]")) {
                    System.out.print("1 ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }
}
