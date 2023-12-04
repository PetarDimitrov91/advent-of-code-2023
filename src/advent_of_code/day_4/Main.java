package advent_of_code.day_4;


import advent_of_code.util.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static final String filePath = String.format("%s/src/advent_of_code/day_4/input_2.txt", System.getProperty("user.dir"));

    public static void main(String[] args) {
        List<String> lines = Utilities.readLines(filePath);

        List<Card> cards = lines.stream()
                .map(Card::new)
                .toList();

        // Part 1
        cards.stream()
                .map(c -> c.cardPoints)
                .reduce(Integer::sum)
                .ifPresent(System.out::println);

        // Part 2
        ArrayList<Card> copies = new ArrayList<>();
        cards.forEach(c -> processCard(c, cards, copies));
        System.out.println(copies.size());
    }

    static void processCard(Card card, List<Card> originalCards, List<Card> copies) {
        copies.add(card);
        for (int i = card.cardNumber; i < card.myWinningNumbers.size() + card.cardNumber; i++) {
            processCard(originalCards.get(i), originalCards, copies);
        }
    }

    static class Card {

        int cardNumber;
        List<Integer> winningNumbers;
        List<Integer> myNumbers;
        List<Integer> myWinningNumbers;
        int cardPoints = 0;

        public Card(String input) {
            String[] parts = input.split(":");
            this.cardNumber = Integer.parseInt(parts[0].trim().split("\\s+")[1]);

            String[] numbersGroups = parts[1].trim().split("\\|");
            winningNumbers = Arrays.stream(numbersGroups[0].trim().split("\\s+"))
                    .map(Integer::parseInt)
                    .toList();

            myNumbers = Arrays.stream(numbersGroups[1].trim().split("\\s+"))
                    .map(Integer::parseInt)
                    .toList();

            myWinningNumbers = myNumbers.stream()
                    .filter(winningNumbers::contains)
                    .toList();

            if (!myWinningNumbers.isEmpty()) {
                cardPoints = (int) Math.pow(2, myWinningNumbers.size() - 1);
            }
        }
    }
}
