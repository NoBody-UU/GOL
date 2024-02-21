import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game extends JFrame {
    /**
     * Declaración de variables
     * initialPopulation: población inicial de la cuadrícula
     * gridButtons: matriz de botones que representan las celdas de la cuadrícula
     * grid: matriz de booleanos que representa el estado de las celdas de la cuadrícula
     * timer: temporizador para actualizar la cuadrícula
     * gameStarted: indica si el juego ha comenzado o no
     */
    private final String[] initialPopulation;
    private final JButton[][] gridButtons;
    private boolean[][] grid;
    private Timer timer;
    private boolean gameStarted = false;


    public Game(int height, int width, int maxGenerations, int speed, String population) {
        /*
         * Configuración de la ventana principal
         * 1. Establecer el título
         * 2. Establecer el ícono
         * 3. Establecer la operación de cierre
         * 4. Establecer el tamaño de la ventana
         * 5. Deshabilitar el cambio de tamaño
         * 6. Centrar la ventana
         */
        setTitle("Game of Life - Huber");
        setIconImage(new ImageIcon("src/Assets/logo.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(820, 820);
        setResizable(false);
        setLocationRelativeTo(null);

        /*
         * Configuración de la cuadrícula y los botones
         * gridButtons: matriz de botones que representan las celdas de la cuadrícula, es la parte visual
         * grid: matriz de booleanos que representa el estado de las celdas de la cuadrícula, es la parte lógica y escogí usar booleanos para que sea más fácil de manejar si esta viva o muerta
         */
        gridButtons = new JButton[height][width];
        grid = new boolean[height][width];
        initialPopulation = population.equals("rnd") ? null : population.split("#");


        initializeGrid();
        setupUI();
        setupTimer(maxGenerations, speed);
        //esperar 2 segundos antes de iniciar el juego
        new Timer(2000, e -> {
            startGame();
            ((Timer) e.getSource()).stop();
        }).start();
    }

    private void initializeGrid() {
        /*
         * Inicializar la cuadrícula con la población inicial si se proporciona o de lo contrario, con aleatoriedad
         * String [] initialPopulation
         * Boolean [][] grid
         */
        if (initialPopulation != null) {
            for (int i = 0; i < initialPopulation.length; i++) {
                for (int j = 0; j < initialPopulation[i].length(); j++) {
                    grid[i][j] = initialPopulation[i].charAt(j) == '1';
                }
            }
        } else {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    grid[i][j] = Math.random() < 0.5;
                }
            }
        }
    }


    private void setupUI() {
        setLayout(new GridLayout(grid.length + 1, grid[0].length));

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                JButton button = new JButton();
                button.setEnabled(false);
                button.setBackground(grid[i][j] ? Color.BLACK : Color.WHITE);
                gridButtons[i][j] = button;
                add(button);
            }
        }

    }

    private void setupTimer(int maxGenerations, int speed) {
        // Configurar el temporizador con el intervalo de actualización
        timer = new Timer(speed, new ActionListener() {
            private int generationCount = 0;

            public void actionPerformed(ActionEvent e) {
                updateGrid();
                generationCount++;
                setTitle("Generación: " + generationCount + " Lives: " + countLiveCells());

                if (maxGenerations > 0 && generationCount >= maxGenerations) {
                    stopGame();
                }
            }
        });
    }

    private void startGame() {
        // Iniciar el temporizador solo si el juego no ha comenzado ya
        if (!gameStarted) {
            timer.start();
            gameStarted = true;
        }
    }

    private void stopGame() {
        // Detener el temporizador
        timer.stop();
        gameStarted = false;
    }

    private void updateGrid() {
        boolean[][] newGrid = new boolean[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                int neighbors = countLiveNeighbors(i, j);

                if (grid[i][j]) {
                    // Célula viva
                    newGrid[i][j] = neighbors == 2 || neighbors == 3;
                } else {
                    // Célula muerta
                    newGrid[i][j] = neighbors == 3;
                }
            }
        }

        // Actualizar la cuadrícula y los botones
        grid = newGrid;
        updateButtons();
    }

    private int countLiveNeighbors(int row, int col) {
        int count = 0;

        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < grid.length && j >= 0 && j < grid[0].length && !(i == row && j == col)) {
                    if (grid[i][j]) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    private int countLiveCells() {
        int count = 0;
        for (boolean[] row : grid) {
            for (boolean cell : row) {
                if (cell) {
                    count++;
                }
            }
        }

        return count;
    }

    private void updateButtons() {
        // Actualizar la apariencia de los botones según la cuadrícula
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                gridButtons[i][j].setBackground(grid[i][j] ? Color.BLACK : Color.WHITE);
            }
        }
    }

}
