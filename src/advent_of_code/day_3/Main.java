package advent_of_code.day_3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

//Answer Part 1 = 514969
//Answer Part 1 = 78915902

public class Main {
    private static final List<String> input = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static char[][] schematic;
    private static int sum = 0;
    private static int gearRatioSum = 0;

    public static void main(String[] args) {
        readInput();
        initSchematic();
        extractSchematicContent();
        extractPartNums();
        findGearRatio();
        System.out.printf("Sum from part 1 is: %d%n", sum);
        System.out.printf("Sum from part 2 is: %d", gearRatioSum);
    }

    private static void readInput() {
        String line = scanner.nextLine().trim();

        while (!line.equals("exit")) {
            input.add(line);
            line = scanner.nextLine().trim();
        }
    }

    private static void initSchematic() {
        int rows = input.size();
        int cols = input.size();
        schematic = new char[rows][cols];
    }

    private static void extractSchematicContent() {
        IntStream.range(0, input.size()).forEach(i -> {
            char[] row = input.get(i).toCharArray();
            schematic[i] = row;
        });
    }

    private static void extractPartNums() {
        StringBuilder crrNum = new StringBuilder();

        for (int r = 0; r < schematic.length; r++) {
            for (int c = 0; c < schematic[r].length; c++) {
                char crrChar = schematic[r][c];

                if (crrChar == '.') {
                    continue;
                }

                if (Character.isDigit(crrChar)) {
                    crrNum.append(crrChar);
                }

                if (!Character.isDigit(crrChar) && !crrNum.toString().isEmpty()) {
                    calc(r, c, crrNum);
                    crrNum.delete(0, crrNum.length());
                    continue;
                }

                if (schematic[r].length > c + 1 && schematic[r][c + 1] == '.' && !crrNum.toString().isEmpty()) {
                    calc(r, c, crrNum);
                    crrNum.delete(0, crrNum.length());
                    continue;
                }

                if (c + 1 == schematic[c].length && Character.isDigit(crrChar)) {
                    calc(r, c, crrNum);
                    crrNum.delete(0, crrNum.length());
                }
            }

            crrNum.delete(0, crrNum.length());
        }
    }

    private static void calc(int r, int c, StringBuilder crrNum) {
        int startRow = r > 0 ? r - 1 : r;
        int endRow = r < schematic.length - 1 ? r + 1 : r;
        int startCol = c - crrNum.length() > 0 ? c - crrNum.length() : c - crrNum.length() + 1;
        int endCol = c < schematic[r].length - 1 ? c + 1 : c;

        boolean isPart = isPart(startRow, endRow, startCol, endCol);

        if (isPart) {
            sum += Integer.parseInt(crrNum.toString());
        }
    }

    private static boolean isPart(int startRow, int endRow, int startCol, int endCol) {
        for (int r = startRow; r <= endRow; r++) {
            for (int c = startCol; c <= endCol; c++) {
                char crrChar = schematic[r][c];

                if (crrChar != '.' && !Character.isDigit(crrChar)) {
                    return true;
                }
            }
        }

        return false;
    }


    private static void findGearRatio() {
        for (int r = 0; r < schematic.length; r++) {
            int rowSize = schematic[r].length;

            for (int c = 0; c < rowSize; c++) {
                if (schematic[r][c] == '*') {
                    List<String> nums = new ArrayList<>();

                    //look right for digits
                    if (c + 1 < rowSize && Character.isDigit(schematic[r][c + 1])) {
                        //extract sub list from c + 1 to end of row
                        char[] subArr = Arrays.copyOfRange(schematic[r], c + 1, rowSize);

                        StringBuilder num = new StringBuilder();
                        for (char crrCh : subArr) {
                            if (Character.isDigit(crrCh)) {
                                num.append(crrCh);
                            } else {
                                break;
                            }
                        }

                        nums.add(num.toString());
                    }

                    //look left for digits
                    if (c - 1 >= 0 && Character.isDigit(schematic[r][c - 1])) {
                        //extract sub list from c - 1 to start of row
                        char[] subArr = Arrays.copyOfRange(schematic[r], 0, c);

                        StringBuilder num = new StringBuilder();

                        for (int i = subArr.length - 1; i >= 0; i--) {
                            if (Character.isDigit(subArr[i])) {
                                num.append(subArr[i]);
                            } else {
                                break;
                            }
                        }

                        nums.add(num.reverse().toString());
                    }

                    //look up for digits
                    if (r - 1 >= 0) {
                        char[] subArr = schematic[r - 1];
                        StringBuilder num = new StringBuilder();

                        boolean isNumDirectAbove = Character.isDigit(subArr[c]);

                        if (isNumDirectAbove) {
                            //if both are num start from left and add to right
                            if (c - 1 >= 0 && c + 1 < subArr.length && Character.isDigit(subArr[c - 1]) && Character.isDigit(subArr[c + 1])) {
                                for (int i = c - 1; i < subArr.length; i++) {
                                    if (Character.isDigit(subArr[i])) {
                                        num.append(subArr[i]);
                                    } else {
                                        break;
                                    }
                                }

                                nums.add(num.toString());

                            } else if (c - 1 >= 0 && !Character.isDigit(subArr[c - 1])) {//if to left is not num look and add to right
                                for (int i = c; i < subArr.length; i++) {
                                    if (Character.isDigit(subArr[i])) {
                                        num.append(subArr[i]);
                                    } else {
                                        break;
                                    }
                                }

                                nums.add(num.toString());
                            } else if (c + 1 < subArr.length && !Character.isDigit(subArr[c + 1])) { //if to right is not num look and add to left
                                for (int i = c; i >= 0; i--) {
                                    if (Character.isDigit(subArr[i])) {
                                        num.append(subArr[i]);
                                    } else {
                                        break;
                                    }
                                }

                                nums.add(num.reverse().toString());
                            }
                        }

                        num.delete(0, num.length());

                        //look right for r - 1
                        if (c + 1 < subArr.length && Character.isDigit(subArr[c + 1]) && !isNumDirectAbove) {
                            for (int i = c + 1; i < subArr.length; i++) {
                                if (Character.isDigit(subArr[i])) {
                                    num.append(subArr[i]);
                                } else {
                                    break;
                                }
                            }

                            nums.add(num.toString());
                        }

                        num.delete(0, num.length());
                        //look left for r - 1
                        if (c - 1 >= 0 && Character.isDigit(subArr[c - 1]) && !isNumDirectAbove) {
                            for (int i = c - 1; i >= 0; i--) {
                                if (Character.isDigit(subArr[i])) {
                                    num.append(subArr[i]);
                                } else {
                                    break;
                                }
                            }

                            nums.add(num.reverse().toString());
                        }

                    }

                    //look down for digits
                    if (r + 1 < schematic.length) {
                        //look right for r + 1
                        char[] subArr = schematic[r + 1];

                        StringBuilder num = new StringBuilder();
                        boolean isNumDirectBottom = Character.isDigit(subArr[c]);

                        //case when char direct on the bottom is num
                        if (isNumDirectBottom) {
                            //if both are num start from left and add to right
                            if (c - 1 >= 0 && c + 1 < subArr.length && Character.isDigit(subArr[c - 1]) && Character.isDigit(subArr[c + 1])) {
                                for (int i = c - 1; i < subArr.length; i++) {
                                    if (Character.isDigit(subArr[i])) {
                                        num.append(subArr[i]);
                                    } else {
                                        break;
                                    }
                                }

                                nums.add(num.toString());

                            } else if (c - 1 >= 0 && !Character.isDigit(subArr[c - 1])) {//if to left is not num look and add to right
                                for (int i = c; i < subArr.length; i++) {
                                    if (Character.isDigit(subArr[i])) {
                                        num.append(subArr[i]);
                                    } else {
                                        break;
                                    }
                                }

                                nums.add(num.toString());

                            } else if (c + 1 < subArr.length && !Character.isDigit(subArr[c + 1])) { //if to right is not num look and add to left
                                for (int i = c; i >= 0; i--) {
                                    if (Character.isDigit(subArr[i])) {
                                        num.append(subArr[i]);
                                    } else {
                                        break;
                                    }
                                }

                                nums.add(num.reverse().toString());
                            }
                        }

                        //case when the char which direct on the bottom is not num
                        if (c + 1 < subArr.length && Character.isDigit(subArr[c + 1]) && !isNumDirectBottom) {
                            for (int i = c + 1; i < subArr.length; i++) {
                                if (Character.isDigit(subArr[i])) {
                                    num.append(subArr[i]);
                                } else {
                                    break;
                                }
                            }

                            nums.add(num.toString());
                        }

                        num.delete(0, num.length());

                        //look left for r + 1
                        if (c - 1 >= 0 && Character.isDigit(subArr[c - 1]) && !isNumDirectBottom) {
                            for (int i = c - 1; i >= 0; i--) {
                                if (Character.isDigit(subArr[i])) {
                                    num.append(subArr[i]);
                                } else {
                                    break;
                                }
                            }

                            nums.add(num.reverse().toString());
                        }
                    }

                    if (nums.size() == 2) {
                        int num1 = Integer.parseInt(nums.get(0));
                        int num2 = Integer.parseInt(nums.get(1));
                        System.out.printf("%d * %d = %d%n", num1, num2, num1 * num2);
                        gearRatioSum += num1 * num2;
                    }
                }
            }
        }
    }
}