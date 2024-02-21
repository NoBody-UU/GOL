import javax.swing.*;
import java.util.Arrays;

public class Main  {
    public static void main(String[] args) {
        ValidateArgs validateArgs = new ValidateArgs(args);

        if (!validateArgs.isValid) {
            String errorMessage = """
                    Argumentos inválidos
                    Uso: java Main w=<width> h=<height> s=<speed> g=<maxGenerations> p=<population>
                    Donde:
                    •  <width> y <height> son 10, 20, 40 o 80
                    •  <speed> es un valor entre 200 y 1000
                    •  <maxGenerations> es un valor igual o mayor a 0
                    •  <population> es un string de 1 y 0, con una longitud de <width> * <height> y cada <width> caracteres separados por #

                    Ejemplo: java Main w=10 h=20 g=100 s=300 p="101#010#111\"""";

            JOptionPane.showMessageDialog(null, errorMessage, "Error: Argumentos inválidos: " + Arrays.toString(args), JOptionPane.ERROR_MESSAGE);
            return;
        }

        new Game(validateArgs.height, validateArgs.width, validateArgs.maxGenerations, validateArgs.speed, validateArgs.population).setVisible(true);

    }
}
