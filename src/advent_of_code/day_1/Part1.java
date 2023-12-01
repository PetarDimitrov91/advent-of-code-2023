package advent_of_code.day_1;

import java.util.Scanner;

//answer part 1 - 54927
public class Part1 {
    private static int sum = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String sequence = scanner.nextLine();

        while (!sequence.equals("exit")) {
            calc(sequence.trim());
            sequence = scanner.nextLine();
        }

        System.out.println(sum);
    }

    private static void calc(String seq) {
        int firstNumber = -1;
        int secondNumber = -1;
        int index = 0;

        while (firstNumber == -1) {
            firstNumber = extractNum(seq.charAt(index));
            index++;
        }

        index = seq.length() - 1;

        while (secondNumber == -1) {
            secondNumber = extractNum(seq.charAt(index));
            index--;
        }

        int twoDigitNum = Integer.parseInt(String.format("%d%d", firstNumber, secondNumber));
        sum += twoDigitNum;
    }

    private static int extractNum(char c) {
        if (Character.isDigit(c)) {
            return Integer.parseInt(String.valueOf(c));
        }

        return -1;
    }
}
