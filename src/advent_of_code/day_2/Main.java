package advent_of_code.day_2;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

//answer Part 1 - 2176
//answer Part 2 - 63700
public class Main {
    private static int sumForPart1 = 0;
    private static int sumForPart2 = 0;
    private static final int maxRedCubes = 12;
    private static final int maxGreenCubes = 13;
    private static final int maxBlueCubes = 14;
    private static int redCubes = 0;
    private static int greenCubes = 0;
    private static int blueCubes = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String sequence = scanner.nextLine();

        while (!sequence.equals("exit")) {
            String[] sp = sequence.split(":");
            int gameId = Integer.parseInt(sp[0].split("\\s")[1]);

            boolean isGamePossible = isGamePossible(sp[1]);

            if (isGamePossible) {
                sumForPart1 += gameId;
            }

            sequence = scanner.nextLine();
        }

        System.out.printf("Part 1 answer is: %d%n", sumForPart1);
        System.out.printf("Part 2 answer is: %d%n", sumForPart2);
    }

    private static boolean isGamePossible(String seq) {
        final AtomicBoolean isGamePossible = new AtomicBoolean(true);
        String[] gameSequences = seq.trim().split(";");
        final AtomicInteger minRed = new AtomicInteger(Integer.MIN_VALUE);
        final AtomicInteger minGreen = new AtomicInteger(Integer.MIN_VALUE);
        final AtomicInteger minBlue = new AtomicInteger(Integer.MIN_VALUE);


        for (String gameSequence : gameSequences) {
            Map<String, Integer> games = extractGames(gameSequence.trim());

            games.forEach((k, v) -> {
                switch (k) {
                    case "red" -> {
                        if (v > minRed.get()) {
                            minRed.set(v);
                        }

                        if (maxRedCubes >= redCubes + v) {
                            break;
                        }

                        isGamePossible.set(false);
                    }
                    case "green" -> {
                        if (v > minGreen.get()) {
                            minGreen.set(v);
                        }

                        if (maxGreenCubes >= greenCubes + v) {
                            break;
                        }

                        isGamePossible.set(false);
                    }
                    case "blue" -> {
                        if (v > minBlue.get()) {
                            minBlue.set(v);
                        }

                        if (maxBlueCubes >= blueCubes + v) {
                            break;
                        }

                        isGamePossible.set(false);
                    }
                }

                redCubes = 0;
                blueCubes = 0;
                greenCubes = 0;
            });
        }

        sumForPart2 += minRed.get() * minGreen.get() * minBlue.get();


        return isGamePossible.get();
    }

    private static Map<String, Integer> extractGames(String seq) {
        return Arrays.stream(seq.trim().split(","))
                .map(s -> s.trim().split("\\s"))
                .collect(Collectors.toMap(s -> s[1].trim(), s -> Integer.parseInt(s[0].trim())));
    }
}
