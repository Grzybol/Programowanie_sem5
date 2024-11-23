package org.betterbox;

import java.util.*;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            displayMenu();

            String input = scanner.nextLine();
            String[] parts = input.split(" ");

            if (parts.length > 0 && parts[0].matches("\\d+")) {
                int choice = Integer.parseInt(parts[0]);
                switch (choice) {
                    case 1:
                        helloUser(parts.length > 1 ? parts[1] : "Świecie");
                        System.out.println();
                        enterBreak(scanner);
                        break;
                    case 2:
                        convertTemperature(scanner);
                        System.out.println();
                        break;
                    case 3:
                        dateInfo(scanner);
                        System.out.println();
                        break;
                    case 4:
                        if(!userLogin()){
                            userLogin(scanner);
                        };
                        System.out.println();
                        break;
                    case 5:
                        checkMagicSquare(scanner);
                        System.out.println();
                        break;
                    case 0:
                        scanner.close();
                        return;
                    default:
                        System.out.println("Nieprawidłowy wybór, spróbuj ponownie.");
                }

            } else {
                System.out.println("Proszę wprowadzić prawidłowy numeryczny input.");
            }

        }
    }
    private static void displayMenu() {
        System.out.println("Wybierz opcję (1-5) lub 0 aby zakończyć:");
        System.out.println("1: Witaj świecie / Witaj użytkowniku");
        System.out.println("2: Konwersja temperatur");
        System.out.println("3: Informacje o dacie");
        System.out.println("4: Logowanie użytkownika");
        System.out.println("5: Sprawdzenie macierzy magicznej");
    }

    public static void helloUser(String name) {
        System.out.println("Witaj " + name + "!");

    }

    public static void convertTemperature(Scanner scanner) {
        System.out.println("Wybierz opcję konwersji: 1) C->F, 2) F->C, 3) C->K, 4) K->C, 5) F->K, 6) K->F");
        int option = scanner.nextInt();
        System.out.println("Podaj temperaturę:");
        double temp = scanner.nextDouble();
        switch (option) {
            case 1:
                System.out.printf("%.2f C to %.2f F\n", temp, (temp * 9 / 5) + 32);
                break;
            case 2:
                System.out.printf("%.2f F to %.2f C\n", temp, (temp - 32) * 5 / 9);
                break;
            case 3:
                System.out.printf("%.2f C to %.2f K\n", temp, temp + 273.15);
                break;
            case 4:
                System.out.printf("%.2f K to %.2f C\n", temp, temp - 273.15);
                break;
            case 5:
                System.out.printf("%.2f F to %.2f K\n", temp, (temp - 32) * 5 / 9 + 273.15);
                break;
            case 6:
                System.out.printf("%.2f K to %.2f F\n", temp, (temp - 273.15) * 9 / 5 + 32);
                break;
        }
        enterBreak(scanner);
    }
    public static void enterBreak(Scanner scanner){
        System.out.println("Naciśnij Enter, aby kontynuować...");
        scanner.nextLine();


    }

    public static void dateInfo(Scanner scanner) {
        System.out.println("Podaj datę w formacie YYYY-MM-DD:");
        String input = scanner.next();
        LocalDate date = LocalDate.parse(input);
        YearMonth yearMonth = YearMonth.of(date.getYear(), date.getMonth());
        int daysInMonth = yearMonth.lengthOfMonth();
        System.out.println("Miesiąc: " + date.getMonth() + ", liczba dni: " + daysInMonth);
        enterBreak(scanner);
    }

    public static boolean userLogin() {
        Console console = System.console();
        if (console == null) {
            System.out.println("Brak dostępu do konsoli, nie można kontynuować. Proszę odpalić zbudowany .jar z CMD.");
            System.out.println("Fail-over do userLogin(scanner)");
            return false;
        }
        String login = console.readLine("Podaj login: ");
        char[] password = console.readPassword("Podaj hasło: ");
        Map<String, String> users = new HashMap<>();
        users.put("admin", "admin123");
        users.put("user", "password");
        if (users.containsKey(login) && users.get(login).equals(new String(password))) {
            System.out.println("Logowanie udane.");
            return true;
        } else {
            System.out.println("Logowanie nieudane.");

        }
        return false;
    }
    public static void userLogin(Scanner scanner) {
        System.out.print("Podaj login: ");
        String login = scanner.nextLine();
        System.out.print("Podaj hasło: ");
        String password = scanner.nextLine();
        Map<String, String> users = new HashMap<>();
        users.put("admin", "admin123");
        users.put("user", "password");
        if (users.containsKey(login) && users.get(login).equals(password)) {
            System.out.println("Logowanie udane.");

        } else {
            System.out.println("Logowanie nieudane.");
        }
        enterBreak(scanner);
    }

    public static void checkMagicSquare(Scanner scanner) {
        System.out.println("Podaj ścieżkę do pliku z macierzą N x N:");
        String path = scanner.next();
        try {
            File file = new File(path);
            Scanner fileScanner = new Scanner(file);
            List<int[]> matrix = new ArrayList<>();
            while (fileScanner.hasNextLine()) {
                String[] numbers = fileScanner.nextLine().trim().split("\\s+");
                int[] row = Arrays.stream(numbers).mapToInt(Integer::parseInt).toArray();
                matrix.add(row);
            }
            if (isMagicSquare(matrix)) {
                System.out.println("Macierz jest magicznym kwadratem.");
            } else {
                System.out.println("Macierz nie jest magicznym kwadratem.");
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Nie znaleziono pliku: " + path);
            enterBreak(scanner);
        }
        enterBreak(scanner);
    }

    private static boolean isMagicSquare(List<int[]> matrix) {
        if (matrix.size() == 0) return false;
        int size = matrix.size();
        int[] rowSums = new int[size];
        int[] colSums = new int[size];
        int diag1Sum = 0, diag2Sum = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int value = matrix.get(i)[j];
                rowSums[i] += value;
                colSums[j] += value;
                if (i == j) diag1Sum += value;
                if (i + j == size - 1) diag2Sum += value;
            }
        }
        int firstSum = rowSums[0];
        if (diag1Sum != firstSum || diag2Sum != firstSum) return false;
        for (int sum : rowSums) if (sum != firstSum) return false;
        for (int sum : colSums) if (sum != firstSum) return false;
        return true;
    }

}
