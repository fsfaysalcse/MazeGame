package com.example.assignmentfinal;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.Stack;

public class MazeController {
    @FXML
    private Canvas mazeCanvas;
    @FXML
    private Label timerLabel;

    private final int rows = 20;
    private final int cols = 20;
    private final int cellSize = 30;
    private Cell[][] maze = new Cell[rows][cols];
    private Cell player;
    private AnimationTimer timer;
    private long startTime;
    private Stage stage;

    public void initialize() {
        generateMaze();
        placePlayer();
        drawMaze();
        startTimer();
    }

    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                movePlayer(-1, 0);
                break;
            case DOWN:
                movePlayer(1, 0);
                break;
            case LEFT:
                movePlayer(0, -1);
                break;
            case RIGHT:
                movePlayer(0, 1);
                break;
        }
    }

    private void generateMaze() {
        Stack<Cell> stack = new Stack<>();
        Random rand = new Random();
        int currentRow = 0;
        int currentCol = 0;

        maze[currentRow][currentCol] = new Cell(currentRow, currentCol, true);
        stack.push(maze[currentRow][currentCol]);

        while (!stack.isEmpty()) {
            Cell current = stack.peek();
            Cell next = getNeighbor(current);

            if (next != null) {
                removeWalls(current, next);
                stack.push(next);
            } else {
                stack.pop();
            }
        }
    }

    private Cell getNeighbor(Cell cell) {
        ArrayList<Cell> neighbors = new ArrayList<>();
        int[] dRow = new int[]{0, 1, 0, -1};
        int[] dCol = new int[]{1, 0, -1, 0};

        for (int i = 0; i < 4; i++) {
            int nx = cell.row + dRow[i];
            int ny = cell.col + dCol[i];

            if (nx >= 0 && nx < rows && ny >= 0 && ny < cols && (maze[nx][ny] == null || !maze[nx][ny].visited)) {
                neighbors.add(new Cell(nx, ny, false));
            }
        }

        if (neighbors.size() > 0) {
            int index = new Random().nextInt(neighbors.size());
            Cell selected = neighbors.get(index);
            maze[selected.row][selected.col] = selected;
            return selected;
        }
        return null;
    }

    private void removeWalls(Cell c1, Cell c2) {
        int dx = c2.row - c1.row;
        int dy = c2.col - c1.col;

        if (dx == 1) {
            c1.southWall = false;
            c2.northWall = false;
        } else if (dx == -1) {
            c1.northWall = false;
            c2.southWall = false;
        }
        if (dy == 1) {
            c1.eastWall = false;
            c2.westWall = false;
        } else if (dy == -1) {
            c1.westWall = false;
            c2.eastWall = false;
        }
        c2.visited = true;
    }

    private void drawMaze() {
        GraphicsContext gc = mazeCanvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, mazeCanvas.getWidth(), mazeCanvas.getHeight());

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double x = j * cellSize;
                double y = i * cellSize;
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(2);

                // Draw walls as lines
                if (maze[i][j].northWall) gc.strokeLine(x, y, x + cellSize, y);
                if (maze[i][j].southWall) gc.strokeLine(x, y + cellSize, x + cellSize, y + cellSize);
                if (maze[i][j].westWall) gc.strokeLine(x, y, x, y + cellSize);
                if (maze[i][j].eastWall) gc.strokeLine(x + cellSize, y, x + cellSize, y + cellSize);

                // Draw the player
                if (maze[i][j].hasPlayer) {
                    gc.setFill(Color.RED);
                    gc.fillOval(x + 5, y + 5, cellSize - 10, cellSize - 10);
                }

                // Mark the finish point with a green point
                if (i == rows - 1 && j == cols - 1) {
                    gc.setFill(Color.GREEN);
                    gc.fillOval(x + 5, y + 5, cellSize - 10, cellSize - 10);
                }
            }
        }
    }


    private void placePlayer() {
        player = maze[0][0];
        player.hasPlayer = true;
    }

    private void movePlayer(int dx, int dy) {
        int newRow = player.row + dx;
        int newCol = player.col + dy;

        if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && canMove(player, maze[newRow][newCol])) {
            player.hasPlayer = false;
            maze[newRow][newCol].hasPlayer = true;
            player = maze[newRow][newCol];
            drawMaze();
            if (newRow == rows - 1 && newCol == cols - 1) {
                gameover();
            }
        }
    }

    private boolean canMove(Cell from, Cell to) {
        if (from.row == to.row) {
            return from.col < to.col ? !from.eastWall : !from.westWall;
        } else if (from.col == to.col) {
            return from.row < to.row ? !from.southWall : !from.northWall;
        }
        return false;
    }

    private void gameover() {
        timer.stop();
        double elapsedTime = (System.nanoTime() - startTime) / 1e9;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("Congratulations!");
        alert.setContentText("You've completed the maze in " + String.format("%.2f seconds", elapsedTime));
        ButtonType backButton = new ButtonType("Go Back");
        alert.getButtonTypes().setAll(backButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == backButton) {
            // Code to handle "Go Back" action
            // For instance, return to the main menu or close the stage
            ((Stage) mazeCanvas.getScene().getWindow()).close();
        }
    }

    private void startTimer() {
        startTime = System.nanoTime();
        timer = new AnimationTimer() {
            public void handle(long now) {
                double elapsedTime = (now - startTime) / 1e9;
                timerLabel.setText(String.format("Time: %.2f seconds", elapsedTime));
            }
        };
        timer.start();
    }

    class Cell {
        int row, col;
        boolean visited = false;
        boolean northWall = true, southWall = true, westWall = true, eastWall = true;
        boolean hasPlayer = false;

        public Cell(int row, int col, boolean visited) {
            this.row = row;
            this.col = col;
            this.visited = visited;
        }
    }
}
