public class ValidateArgs {
    public int height;
    public int width;
    public int maxGenerations;
    public int speed;
    public String population;
    public boolean isValid = true;

    public  ValidateArgs(String[] args) {
        if (args.length != 5) {
            this.isValid = false;
            return;
        }

        // los args serria algo como: w=10 h=20 g=100 s=300 p=”101#010#111”
        for (String arg : args) {
            try {
                String[] parts = arg.split("=");

                if (parts.length != 2) {
                    this.isValid = false;
                    break;
                }
                switch (parts[0].toLowerCase()) {
                    case "w":
                        this.width = Integer.parseInt(parts[1]);
                        break;
                    case "h":
                        this.height = Integer.parseInt(parts[1]);
                        break;
                    case "s":
                        if (Integer.parseInt(parts[1]) < 200 || Integer.parseInt(parts[1]) > 1000) {
                            this.isValid = false;
                            break;
                        }
                        this.speed = Integer.parseInt(parts[1]);
                        break;
                    case "g":
                        if (Integer.parseInt(parts[1]) < 0) {
                            this.isValid = false;
                            break;
                        }
                        this.maxGenerations = Integer.parseInt(parts[1]);
                        break;
                    case "p":
                        if (parts[1].equalsIgnoreCase("rnd")) {
                            this.population = "rnd";
                            break;
                        }
                        //  como vi en el ejmplo que usaba "" para el string de la poblacion, lo que hice fue quitarle las comillas en caso de que las tenga
                        String population = parts[1].replace("\"", "");
                        String[] rows = population.split("#");


                        if (rows.length > this.height) {
                            this.isValid = false;
                            break;
                        }

                        for (String row : rows) {
                            // aqui valido que la longitud de cada fila sea igual al ancho y que solo contenga 0 y 1
                            // como vi que solo se usan 1 y 0 hice que solo se puedan usar esos dos valores
                            if ((row.length() > this.width) || (row.matches(".*[^01].*"))) {
                                this.isValid = false;
                                break;
                            }
                        }

                        this.population = parts[1];
                        break;
                    default:
                        this.isValid = false;
                        break;
                }
            } catch (NumberFormatException e) {
                this.isValid = false;
                break;
            }
        }

        if (height != 10 && height != 20 && height != 40) {
            isValid = false;
        }

        else if (width != 10 && width != 20 && width != 40 && width != 80) {
            isValid = false;
        }


    }
}