package advent_of_code.day_4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    private static int points = 0;
    private static final String filePath = String.format("%s/src/advent_of_code/day_4/input_1.txt", System.getProperty("user.dir"));
    private static final List<String> Rinput = new ArrayList<>();
    private static final HashMap<String, Integer> memo = new HashMap<>();
    private static int recursionCalls = 0;
    public static void main(String[] args) {
        readCards();
        calc(Rinput, 0);
        System.out.println(points);
        System.out.println(recursionCalls);
    }

    private static void readCards() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                Rinput.add(line);
            }
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    private static void calc(List<String> input, int index) {
        recursionCalls++;
        for (int i = 0; i < input.size(); i++) {
            String[] arr = input.get(i).split(":");
            String card  = arr[0].trim();
            String[] nums = arr[1].split("\\|");
            int matches;

            if(!memo.containsKey(card)){
                int[] winningNums = extractNums(nums[0].trim().split(" "));
                int[] myNums = extractNums(nums[1].trim().split(" "));
                matches = calcPoints(winningNums, myNums);
                memo.put(card, matches);
            }else {
                matches = memo.get(card);
            }

            if (matches == 0) {
                System.out.println("returning");
                return;
            }

            List<String> subList = Rinput.subList(index + 1, index + matches + 1);

            calc(subList, index + 1);
        }
    }

    private static int[] extractNums(String[] arr) {
        return Arrays.stream(arr).
                filter(s -> !s.isEmpty())
                .mapToInt(Integer::parseInt).toArray();
    }

    private static int calcPoints(int[] winningNums, int[] myNums) {
        int p = 0;
        int matches = 0;
        for (int winningNum : winningNums) {
            for (int myNum : myNums) {
                if (winningNum == myNum) {
                    if (p == 0) {
                        p = 1;
                    } else {
                        p += p;
                    }
                    matches++;
                    break;
                }
            }
        }

        points += p;
        return matches;
    }
}