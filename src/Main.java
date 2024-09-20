import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Arrays;

/**
 * @author Manuel Aguilera / @aguileradev
 **/

public class Main {
    private final static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int length = 0;

        length = getValue("Paso 1. Introduce la longitud de la lista de numeros");
        int list[] = new int[length];


        String[] data = fillData("2", "Llena el listado numerico de menor a mayor separados por un espacio");
        equalsLengths(length, data.length);
        verifyOrder(data);

        int[] dataset = Arrays.stream(data).mapToInt(Integer::parseInt).toArray();

        final int attempts = getValue("Paso 3. Ingresa el numero de consultas a realizar por numero");

        String[] fillWithNumbers = fillData("4", "Introduce los numeros a buscar separados por un espacio");
        int[] numbersToSearch = Arrays.stream(fillWithNumbers).mapToInt(Integer::parseInt).toArray();
        int query = 0;
        int marker = 0;
        int min = 0;
        int max = dataset.length - 1;

        int attemptsForNumber = attempts;

        for (int j = 0; j < numbersToSearch.length; j++) {
            query = numbersToSearch[j];
            attemptsForNumber = attempts;
            while (attemptsForNumber > 0 && min <= max) {
                marker = (min + max) / 2;

                if (dataset[marker] < query) {
                    min = marker + 1;
                } else if (dataset[marker] > query) {
                    max = marker - 1;
                } else {
                    if (marker == 0) {
                        System.out.printf("X %d%n", dataset[marker + 1]);
                    } else if (marker == dataset.length - 1) {
                        System.out.printf("%d X%n", dataset[marker]);

                    } else {
                        System.out.printf("%d %d%n", dataset[marker - 1], dataset[marker + 1]);
                    }
                    min = 0;
                    max = dataset.length - 1;
                    break;
                }

                attemptsForNumber--;
            }

            if (min > max) {
                System.out.println("X");
                min = 0;
                max = dataset.length - 1;
            }
        }


    }

    public static void verifyOrder(String[] list) {
        int previous = 0;
        for (int i = 0; i < list.length; i++) {
            try {
                isNumber(list[i]);

                boolean isGreaterThanBefore;
                int actual = Integer.parseInt(list[i]);
                if (actual < 0 && previous == 0) {
                    previous = Integer.MIN_VALUE;
                }
                isGreaterThanBefore = verifySecuence(previous, actual);

                if (!isGreaterThanBefore) {
                    throw new RuntimeException();
                }

                previous = actual;

            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Se permiten solo numeros");
            } catch (RuntimeException e) {
                System.out.printf("Numero %s en la posicion %s no es mayor que %d", list[i], (i + 1), previous);
                System.exit(1);
            }
        }

        System.out.println("*****OK*****");
    }

    public static Boolean verifySecuence(int previous, int actual) {
        return actual > previous;
    }

    public static boolean isNumber(String possibleNumber) {
        try {
            Integer.parseInt(possibleNumber);
            return true;
        } catch (NumberFormatException e) {
            String message = """
                    '%s' no es un numero. Intenta nuevamente desde el inicio :)
                    """.formatted(possibleNumber);
            System.out.println(message);
            System.exit(1);
        }

        return false;
    }

    public static void equalsLengths(int defineLength, int dataLength) {
        if (!(defineLength == dataLength)) {
            System.out.println("**La cantidad de datos suministrados no coincide con el tamaño anteriormente definido**");
            System.out.println("*****intenta nuevamente*****");
            System.exit(1);
        }
    }

    public static Integer getValue(String message) {
        int storage = 0;
        try {
            System.out.println(message);
            storage = input.nextInt();
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("Debes ingresar solo numeros");
            System.exit(1);
        }
        input.nextLine();

        if (storage < 0) {
            System.out.println("Debes introducir un numero positivo");
            System.exit(1);
        }

        return storage;
    }

    public static String[] fillData(String step, String message) {
        System.out.printf("Paso %s. %s%n", step, message);
        String dataRaw = input.nextLine();

        return dataRaw.trim().split(" ");

    }


}