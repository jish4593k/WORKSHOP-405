import javax.swing.*;
import java.awt.*;

public class ShipPlacement {
    private GameWindow gameWindow;
    private int fieldSize;
    private int[] ships;
    private JButton[][] field;
    private String[] shipColors = {"black", "#064020", "#AA0020", "#22DDCC", "#888888", "#70AA00",
                                    "#1111CC", "#FFFF66", "#FFB266", "#B266FF", "#663300"};

    public ShipPlacement(GameWindow gui, int fieldSize, int[] ships) {
        this.gameWindow = gui;
        this.fieldSize = fieldSize;
        this.ships = ships;
        this.field = new JButton[fieldSize][fieldSize];
        placeShips();
    }

    private void placeShips() {
        JFrame gameFrame = new JFrame();
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(fieldSize * 50 + 200, fieldSize * 50 + 50);
        gameFrame.setResizable(false);
        gameFrame.setVisible(true);

        String[] letters = {"А", "Б", "В", "Г", "Д", "Е", "Ж", "З", "И", "К", "Л"};
        String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};

        JPanel fieldPanel = new JPanel(new GridLayout(fieldSize + 1, fieldSize + 1));
        JLabel fieldLabel = new JLabel("Расставьте ваши корабли");
        fieldLabel.setFont(new Font("Roboto", Font.PLAIN, 16));
        fieldPanel.add(fieldLabel);

        for (int k = 0; k < fieldSize; k++) {
            JLabel numberLabel = new JLabel(numbers[k]);
            fieldPanel.add(numberLabel);
            JLabel letterLabel = new JLabel(letters[k]);
            fieldPanel.add(letterLabel);
        }

        int nextShipX = 0;
        int nextShipY = 0;
        int currentShipIndex = 0;
        int currentLen = 0;
        Ship ship = new Ship(ships[0], "h", shipColors[0], new Point(0, 0));

        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (nextShipX == j && nextShipY == i && currentShipIndex < ships.length && currentLen < ships[currentShipIndex]) {
                    field[i][j] = new FieldButton(gameFrame, ship, i, j);
                    currentLen++;

                    if (currentLen + 1 > ships[currentShipIndex]) {
                        nextShipX = (nextShipX + 2 < fieldSize) ? nextShipX + 2 : 0;
                        nextShipY = (nextShipX == 0) ? nextShipY + 2 : nextShipY;
                    } else {
                        nextShipX = nextShipX + 1;
                    }
                } else {
                    field[i][j] = new FieldButton(gameFrame, null, i, j);
                    if (currentLen != 0) {
                        currentShipIndex++;
                        if (currentShipIndex < ships.length) {
                            ship = new Ship(ships[currentShipIndex], "h", shipColors[currentShipIndex], new Point(nextShipX, nextShipY));
                        }
                    }
                    currentLen = 0;
                }
            }
        }
    }

    public static void main(String[] args) {
        // Assuming you have a GameWindow class
        GameWindow gui = new GameWindow();
        ShipPlacement shipPlacement = new ShipPlacement(gui, 10, new int[]{4, 3, 2, 1});
    }
}
