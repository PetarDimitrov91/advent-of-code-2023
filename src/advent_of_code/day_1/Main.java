package advent_of_code.day_1;

import java.util.Scanner;

//answer part 1 - 54927
//answer part 2 - 54581
public class Main {
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

            if (firstNumber == -1) {
                //check for word match
                firstNumber = tryExtractNumFromSeq(seq.substring(index), -1, false);
            }

            index++;
        }

        index = seq.length() - 1;

        while (secondNumber == -1) {
            secondNumber = extractNum(seq.charAt(index));

            if (secondNumber == -1) {
                //check for word match
                secondNumber = tryExtractNumFromSeq(seq.substring(0, index + 1), index + 1, true);
            }

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

    private static int tryExtractNumFromSeq(String seq, int endIndex, boolean isBackwards) {
        int n = -1;

        if (seq.length() >= 3) {
            String subSeq = !isBackwards ? seq.substring(0, 3) : seq.substring(seq.length() - 3, endIndex);
            n = matchNumber(subSeq);
        }

        if (n == -1 && seq.length() >= 4) {
            String subSeq = !isBackwards ? seq.substring(0, 4) : seq.substring(seq.length() - 4, endIndex);
            n = matchNumber(subSeq);
        }

        if (n == -1 & seq.length() >= 5) {
            String subSeq = !isBackwards ? seq.substring(0, 5) : seq.substring(seq.length() - 5, endIndex);
            n = matchNumber(subSeq);
        }

        return n;
    }

    private static int matchNumber(String subSeq) {
        return switch (subSeq) {
            case Number.ONE -> 1;
            case Number.TWO -> 2;
            case Number.THREE -> 3;
            case Number.FOUR -> 4;
            case Number.FIVE -> 5;
            case Number.SIX -> 6;
            case Number.SEVEN -> 7;
            case Number.EIGHT -> 8;
            case Number.NINE -> 9;
            default -> -1;
        };
    }
}
