import java.util.ArrayList;
import java.util.List;

/**
 * @author matok
 *
 */
public class BrickWallOptimizer {

    public static void main(String[] args) {
        int width = 32;
        int height = 10;
        System.out.println(BrickWallOptimizer.calculateWaysToBuildWall(width, height));
    }

    public static int calculateWaysToBuildWall(int width, int height) {
        List<Integer> validRowConfigurations = BrickWallOptimizer.generateValidRowConfigurations(width);
        int numValidRows = validRowConfigurations.size();
        long[][] waysToBuildWall = new long[height][numValidRows];

        for (int i = 0; i < numValidRows; i++) {
            waysToBuildWall[0][i] = 1;
        }

        for (int currentHeight = 1; currentHeight < height; currentHeight++) {
            for (int currentRowIndex = 0; currentRowIndex < numValidRows; currentRowIndex++) {
                for (int previousRowIndex = 0; previousRowIndex < numValidRows; previousRowIndex++) {
                    if (!BrickWallOptimizer.formsRunningCrack(validRowConfigurations.get(currentRowIndex),
                        validRowConfigurations.get(previousRowIndex), width)) {
                        waysToBuildWall[currentHeight][currentRowIndex] +=
                            waysToBuildWall[currentHeight - 1][previousRowIndex];
                    }
                }
            }
        }

        long totalWays = 0;
        for (int i = 0; i < numValidRows; i++) {
            totalWays += waysToBuildWall[height - 1][i];
        }

        return (int)totalWays;
    }

    private static List<Integer> generateValidRowConfigurations(int width) {
        List<Integer> validRows = new ArrayList<>();
        BrickWallOptimizer.helper(0, 0, width, validRows);
        return validRows;
    }

    private static void helper(int pos, int bitmask, int width, List<Integer> validRows) {
        if (pos == width) {
            validRows.add(bitmask);
            return;
        }
        if ((pos + 2) <= width) {
            BrickWallOptimizer.helper(pos + 2, bitmask | (1 << pos), width, validRows);
        }
        if ((pos + 3) <= width) {
            BrickWallOptimizer.helper(pos + 3, bitmask | (1 << pos), width, validRows);
        }
    }

    private static boolean formsRunningCrack(int row1, int row2, int width) {
        for (int i = 1; i < width; i++) {
            if ((((row1 >> i) & 1) == 1) && (((row2 >> i) & 1) == 1)) {
                return true;
            }
        }
        return false;
    }
}