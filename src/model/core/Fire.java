package model.core;

/** Simulates a fire using one temperature value for each matrix cell. */
public class Fire {
    private static final int MAX_TEMPERATURE = 255;

    private final int rows;
    private final int columns;
    private int[][] temperatures;
    private double coolPointPercentage = 0.2;
    private double sparkPercentage = 0.2;

    public Fire(int columns, int rows) {
        if (columns <= 0 || rows <= 0) {
            throw new IllegalArgumentException("Fire dimensions must be positive.");
        }
        this.columns = columns;
        this.rows = rows;
        temperatures = new int[rows][columns];
    }

    /** Creates the next frame, generating heat only in the bottom row. */
    public void evolveTemperatures() {
        int bottomRow = rows - 1;
        for (int column = 0; column < columns; column++) {
            temperatures[bottomRow][column] = 0;
        }
        setCoolPoints(coolPointPercentage);
        setSparks(sparkPercentage);
        recalcTemperatures();
    }

    /** Recalculates each row from the row immediately below it. */
    public void recalcTemperatures() {
        int[][] nextFrame = copyMatrix();
        for (int row = rows - 2; row >= 0; row--) {
            for (int column = 0; column < columns; column++) {
                nextFrame[row][column] = calcTempCell(row, column);
            }
        }
        temperatures = nextFrame;
    }

    /**
     * A cell receives the average of the three cells below it. Cells outside
     * the matrix contribute zero, so the divisor is always three.
     */
    public int calcTempCell(int row, int column) {
        if (row < 0 || row >= rows || column < 0 || column >= columns) {
            throw new IndexOutOfBoundsException("Cell is outside the fire matrix.");
        }
        if (row == rows - 1) {
            return temperatures[row][column];
        }

        int heat = 0;
        for (int sourceColumn = column - 1; sourceColumn <= column + 1; sourceColumn++) {
            if (sourceColumn >= 0 && sourceColumn < columns) {
                heat += temperatures[row + 1][sourceColumn];
            }
        }
        return heat / 3;
    }

    public void setCoolPoints(double percentage) {
        validatePercentage(percentage);
        int bottomRow = rows - 1;
        for (int column = 0; column < columns; column++) {
            if (Math.random() < percentage) {
                temperatures[bottomRow][column] = 0;
            }
        }
    }

    public void setSparks(double percentage) {
        validatePercentage(percentage);
        int bottomRow = rows - 1;
        for (int column = 0; column < columns; column++) {
            if (Math.random() < percentage) {
                temperatures[bottomRow][column] = MAX_TEMPERATURE;
            }
        }
    }

    public void setCoolPointPercentage(double percentage) {
        validatePercentage(percentage);
        coolPointPercentage = percentage;
    }

    public void setSparkPercentage(double percentage) {
        validatePercentage(percentage);
        sparkPercentage = percentage;
    }

    public int[][] getTemperatureMatrix() {
        return copyMatrix();
    }

    public int getNumCol() {
        return columns;
    }

    public int getNumRow() {
        return rows;
    }

    private int[][] copyMatrix() {
        int[][] copy = new int[rows][columns];
        for (int row = 0; row < rows; row++) {
            System.arraycopy(temperatures[row], 0, copy[row], 0, columns);
        }
        return copy;
    }

    private static void validatePercentage(double percentage) {
        if (!Double.isFinite(percentage) || percentage < 0 || percentage > 1) {
            throw new IllegalArgumentException("Percentage must be between 0 and 1.");
        }
    }
}
