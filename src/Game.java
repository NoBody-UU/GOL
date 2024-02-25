import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Game extends JFrame {
    /**
     * Declaración de Propiedades
     * initialPopulation: población inicial de la cuadrícula
     * gridButtons: matriz de botones que representan las celdas de la cuadrícula
     * grid: matriz de booleanos que representa el estado de las celdas de la cuadrícula
     * timer: temporizador para actualizar la cuadrícula
     * gameStarted: indica si el juego ha comenzado o no
     */
    private final String[] initialPopulation;
    private boolean[][] grid;
    private Timer timer;
    protected boolean gameStarted = false;
    private final Listener listener;
    private  final Grid gridManager;
    private final int maxGenerations;
    private final int speed;


    public Game(int height, int width, int maxGenerations, int speed, String population) {
        /*
         * Configuración de la cuadrícula y los botones
         * grid: matriz de booleanos que representa el estado de las celdas de la cuadrícula, es la parte lógica y escogí usar booleanos para que sea más fácil de manejar si esta viva o muerta
         */
        this.grid = new boolean[height][width];
        this.initialPopulation = population.equals("rnd") ? null : population.split("#");
        this.listener = new Listener(this);
        this.gridManager = new Grid(this);
        this.maxGenerations = maxGenerations;
        this.speed = speed;


        setTitle("Game of Life - Huber");
        setIconImage(new ImageIcon("src/Assets/logo.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // establecer el tamaño de la ventana en función del tamaño de la cuadrícula
        setSize(width * 20, height * 20);
        setResizable(false);
        setLocationRelativeTo(null);

        this.initGame();
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
        this.gridManager.init();

    }

    private void setupTimer() {
        // Configurar el temporizador con el intervalo de actualización
        timer = new Timer(this.speed, new ActionListener() {
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

    public void startGame() {
        // Iniciar el temporizador solo si el juego no ha comenzado ya
        if (!gameStarted) {
            this.timer.start();
            this.gameStarted = true;
            this.addKeyListener(listener);
        }


    }

    public void stopGame() {
        this.gameStarted = false;
        this.timer.stop();
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
        this.gridManager.updateButtons();
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


    public boolean[][] getGrid() {
        return grid;
    }

    // method to start all the game
    public void initGame() {
        this.initializeGrid();
        this.setupUI();
        this.setupTimer();
        this.startGame();
    }

}
