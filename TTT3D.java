/*
  This program lets you play three-dimensional 4x4x4 tic-tac-toe against
       an intelligent AI.

  Student: Benjamin Smith (betasmit@ucsc.edu)
  This code was decompiled from the example class given by the instructor,
       with major stylistic edits, including comments, variable names, and 
       line length.

 */
import java.io.*;
import java.util.*;

class TTT3D {
    static int[][][] board = new int[4][4][4];
    static int[] sums = new int[76];
    
    //a GIANT array storing all possible scoring lines
    static final int[][][] lines = new int[][][]{
        {{0, 0, 0}, {0, 0, 1}, {0, 0, 2}, {0, 0, 3}}, //lev 0; row 0  horizontal
        {{0, 1, 0}, {0, 1, 1}, {0, 1, 2}, {0, 1, 3}}, //       row 1
        {{0, 2, 0}, {0, 2, 1}, {0, 2, 2}, {0, 2, 3}}, //       row 2
        {{0, 3, 0}, {0, 3, 1}, {0, 3, 2}, {0, 3, 3}}, //       row 3
        {{1, 0, 0}, {1, 0, 1}, {1, 0, 2}, {1, 0, 3}}, //lev 1; row 0
        {{1, 1, 0}, {1, 1, 1}, {1, 1, 2}, {1, 1, 3}}, //       row 1
        {{1, 2, 0}, {1, 2, 1}, {1, 2, 2}, {1, 2, 3}}, //       row 2
        {{1, 3, 0}, {1, 3, 1}, {1, 3, 2}, {1, 3, 3}}, //       row 3
        {{2, 0, 0}, {2, 0, 1}, {2, 0, 2}, {2, 0, 3}}, //lev 2; row 0
        {{2, 1, 0}, {2, 1, 1}, {2, 1, 2}, {2, 1, 3}}, //       row 1
        {{2, 2, 0}, {2, 2, 1}, {2, 2, 2}, {2, 2, 3}}, //       row 2
        {{2, 3, 0}, {2, 3, 1}, {2, 3, 2}, {2, 3, 3}}, //       row 3
        {{3, 0, 0}, {3, 0, 1}, {3, 0, 2}, {3, 0, 3}}, //lev 3; row 0
        {{3, 1, 0}, {3, 1, 1}, {3, 1, 2}, {3, 1, 3}}, //       row 1
        {{3, 2, 0}, {3, 2, 1}, {3, 2, 2}, {3, 2, 3}}, //       row 2
        {{3, 3, 0}, {3, 3, 1}, {3, 3, 2}, {3, 3, 3}}, //       row 3
        {{0, 0, 0}, {0, 1, 0}, {0, 2, 0}, {0, 3, 0}}, //depth rows
        {{0, 0, 1}, {0, 1, 1}, {0, 2, 1}, {0, 3, 1}}, 
        {{0, 0, 2}, {0, 1, 2}, {0, 2, 2}, {0, 3, 2}}, 
        {{0, 0, 3}, {0, 1, 3}, {0, 2, 3}, {0, 3, 3}}, 
        {{1, 0, 0}, {1, 1, 0}, {1, 2, 0}, {1, 3, 0}}, 
        {{1, 0, 1}, {1, 1, 1}, {1, 2, 1}, {1, 3, 1}}, 
        {{1, 0, 2}, {1, 1, 2}, {1, 2, 2}, {1, 3, 2}}, 
        {{1, 0, 3}, {1, 1, 3}, {1, 2, 3}, {1, 3, 3}}, 
        {{2, 0, 0}, {2, 1, 0}, {2, 2, 0}, {2, 3, 0}}, 
        {{2, 0, 1}, {2, 1, 1}, {2, 2, 1}, {2, 3, 1}}, 
        {{2, 0, 2}, {2, 1, 2}, {2, 2, 2}, {2, 3, 2}}, 
        {{2, 0, 3}, {2, 1, 3}, {2, 2, 3}, {2, 3, 3}}, 
        {{3, 0, 0}, {3, 1, 0}, {3, 2, 0}, {3, 3, 0}}, 
        {{3, 0, 1}, {3, 1, 1}, {3, 2, 1}, {3, 3, 1}}, 
        {{3, 0, 2}, {3, 1, 2}, {3, 2, 2}, {3, 3, 2}}, 
        {{3, 0, 3}, {3, 1, 3}, {3, 2, 3}, {3, 3, 3}}, 
        {{0, 0, 0}, {1, 0, 0}, {2, 0, 0}, {3, 0, 0}}, //vertical columns
        {{0, 0, 1}, {1, 0, 1}, {2, 0, 1}, {3, 0, 1}}, 
        {{0, 0, 2}, {1, 0, 2}, {2, 0, 2}, {3, 0, 2}}, 
        {{0, 0, 3}, {1, 0, 3}, {2, 0, 3}, {3, 0, 3}}, 
        {{0, 1, 0}, {1, 1, 0}, {2, 1, 0}, {3, 1, 0}}, 
        {{0, 1, 1}, {1, 1, 1}, {2, 1, 1}, {3, 1, 1}}, 
        {{0, 1, 2}, {1, 1, 2}, {2, 1, 2}, {3, 1, 2}}, 
        {{0, 1, 3}, {1, 1, 3}, {2, 1, 3}, {3, 1, 3}}, 
        {{0, 2, 0}, {1, 2, 0}, {2, 2, 0}, {3, 2, 0}}, 
        {{0, 2, 1}, {1, 2, 1}, {2, 2, 1}, {3, 2, 1}}, 
        {{0, 2, 2}, {1, 2, 2}, {2, 2, 2}, {3, 2, 2}}, 
        {{0, 2, 3}, {1, 2, 3}, {2, 2, 3}, {3, 2, 3}}, 
        {{0, 3, 0}, {1, 3, 0}, {2, 3, 0}, {3, 3, 0}}, 
        {{0, 3, 1}, {1, 3, 1}, {2, 3, 1}, {3, 3, 1}}, 
        {{0, 3, 2}, {1, 3, 2}, {2, 3, 2}, {3, 3, 2}}, 
        {{0, 3, 3}, {1, 3, 3}, {2, 3, 3}, {3, 3, 3}}, 
        {{0, 0, 0}, {0, 1, 1}, {0, 2, 2}, {0, 3, 3}}, //within-level diagonals
        {{0, 3, 0}, {0, 2, 1}, {0, 1, 2}, {0, 0, 3}}, 
        {{1, 0, 0}, {1, 1, 1}, {1, 2, 2}, {1, 3, 3}}, 
        {{1, 3, 0}, {1, 2, 1}, {1, 1, 2}, {1, 0, 3}}, 
        {{2, 0, 0}, {2, 1, 1}, {2, 2, 2}, {2, 3, 3}}, 
        {{2, 3, 0}, {2, 2, 1}, {2, 1, 2}, {2, 0, 3}}, 
        {{3, 0, 0}, {3, 1, 1}, {3, 2, 2}, {3, 3, 3}}, 
        {{3, 3, 0}, {3, 2, 1}, {3, 1, 2}, {3, 0, 3}}, 
        {{0, 0, 0}, {1, 0, 1}, {2, 0, 2}, {3, 0, 3}}, //ignore depth diagonals
        {{3, 0, 0}, {2, 0, 1}, {1, 0, 2}, {0, 0, 3}}, 
        {{0, 1, 0}, {1, 1, 1}, {2, 1, 2}, {3, 1, 3}}, 
        {{3, 1, 0}, {2, 1, 1}, {1, 1, 2}, {0, 1, 3}}, 
        {{0, 2, 0}, {1, 2, 1}, {2, 2, 2}, {3, 2, 3}}, 
        {{3, 2, 0}, {2, 2, 1}, {1, 2, 2}, {0, 2, 3}}, 
        {{0, 3, 0}, {1, 3, 1}, {2, 3, 2}, {3, 3, 3}}, 
        {{3, 3, 0}, {2, 3, 1}, {1, 3, 2}, {0, 3, 3}}, 
        {{0, 0, 0}, {1, 1, 0}, {2, 2, 0}, {3, 3, 0}}, //ignore row diagonals
        {{3, 0, 0}, {2, 1, 0}, {1, 2, 0}, {0, 3, 0}}, 
        {{0, 0, 1}, {1, 1, 1}, {2, 2, 1}, {3, 3, 1}}, 
        {{3, 0, 1}, {2, 1, 1}, {1, 2, 1}, {0, 3, 1}}, 
        {{0, 0, 2}, {1, 1, 2}, {2, 2, 2}, {3, 3, 2}}, 
        {{3, 0, 2}, {2, 1, 2}, {1, 2, 2}, {0, 3, 2}}, 
        {{0, 0, 3}, {1, 1, 3}, {2, 2, 3}, {3, 3, 3}}, 
        {{3, 0, 3}, {2, 1, 3}, {1, 2, 3}, {0, 3, 3}}, 
        {{0, 0, 0}, {1, 1, 1}, {2, 2, 2}, {3, 3, 3}}, //cube diagonals
        {{3, 0, 0}, {2, 1, 1}, {1, 2, 2}, {0, 3, 3}}, 
        {{0, 3, 0}, {1, 2, 1}, {2, 1, 2}, {3, 0, 3}}, 
        {{3, 3, 0}, {2, 2, 1}, {1, 1, 2}, {0, 0, 3}}
    };

    TTT3D() {
    }

    //prints the initial board
    static void printBoardRaw() {
        for (int i = 3; i >= 0; --i) {
            for (int j = 3; j >= 0; --j) {
                for (int k = 0; k < 4; ++k) {
                    System.out.printf("%1d ", board[i][j][k]);
                }
                System.out.printf("\n", new Object[0]);
            }
            System.out.printf("\n", new Object[0]);
        }
    }

    //determines each board space character
    static char marker(int n) {
        switch (n) {
            case 0: {
                return '_';
            }
            case 1: {
                return 'O';
            }
            case 5: {
                return 'X';
            }
        }
        return '?';
    }

    //prints the board
    static void printBoardMarkers() {
        for (int lev = 3; lev >= 0; --lev) {
            System.out.printf("\n", new Object[0]);
            for (int row = 3; row >= 0; --row) {
                int col;
                for (col = row; col >= 0; --col) {
                    System.out.printf(" ", new Object[0]);
                }
                System.out.printf("%d%1d  ", lev, row);
                for (col = 0; col < 4; ++col) {
                    System.out.printf("%1c ", Character.valueOf(TTT3D.marker(board[lev][row][col])));
                }
                System.out.printf("\n", new Object[0]);
            }
        }
        System.out.printf("\n   0 1 2 3\n", new Object[0]);
    }

    static void printSums() {
        for (int i = 0; i < sums.length; ++i) {
            System.out.println("line " + i + "is " + sums[i]);
        }
    }

    static boolean isEmpty(int[] arrn) {
        if (board[arrn[0]][arrn[1]][arrn[2]] == 0) {
            return true;
        }
        return false;
    }

    static void move(int[] arrn, int n) {
        TTT3D.move(arrn[0], arrn[1], arrn[2], n);
    }

    static void move(int n, int n2, int n3, int n4) {
        TTT3D.board[n][n2][n3] = n4;
    }

    static void setAll(int n) {
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                for (int k = 0; k < 4; ++k) {
                    TTT3D.move(i, j, k, n);
                }
            }
        }
    }

    static boolean isEqual(int[] arrn, int[] arrn2) {
        for (int i = 0; i < arrn.length; ++i) {
            if (arrn[i] == arrn2[i]) continue;
            return false;
        }
        return true;
    }

    static int[] findEmptyCel(int[][] arrn) {
        for (int i = 0; i < 4; ++i) {
            if (!TTT3D.isEmpty(arrn[i])) continue;
            return arrn[i];
        }
        return null;
    }

    static int[] findComMtCel(int[][] arrn, int[][] arrn2) {
        for (int i = 0; i < arrn.length; ++i) {
            for (int j = 0; j < arrn.length; ++j) {
                if (!TTT3D.isEqual(arrn[i], arrn2[j]) || !TTT3D.isEmpty(arrn[i]) || !TTT3D.isEmpty(arrn2[j])) continue;
                return arrn[i];
            }
        }
        return null;
    }

    static void compLineSums() {
        for (int i = 0; i < sums.length; ++i) {
            TTT3D.sums[i] = 0;
            for (int j = 0; j < 4; ++j) {
                int[] arrn = sums;
                int n = i;
                arrn[n] = arrn[n] + board[lines[i][j][0]][lines[i][j][1]][lines[i][j][2]];
            }
        }
    }

    static int findLineSum(int n) {
        for (int i = 0; i < sums.length; ++i) {
            if (sums[i] != n) continue;
            return i;
        }
        return -1;
    }

    static int[] findFork(int n, int[][][] arrn, int[] arrn2) {
        int[] arrn3 = new int[3];
        for (int i = 0; i < arrn2.length - 1; ++i) {
            if (arrn2[i] != 2 * n) continue;
            for (int j = i + 1; j < arrn2.length; ++j) {
                if (arrn2[j] != 2 * n || (arrn3 = TTT3D.findComMtCel(arrn[i], arrn[j])) == null) continue;
                return arrn3;
            }
        }
        return null;
    }

    public static void main(String[] arrstring) throws FileNotFoundException {
        int n, n2, n3, n4;
        Scanner scanner = new Scanner(System.in);
        Random random = new Random(1234);
        int[] arrn = new int[3];
        int[] arrn2 = new int[3];
        int[] arrn3 = new int[3];
        int n5 = 0;
        if (arrstring.length != 0) {
            System.out.println("getting initial setup from file");
            Scanner scanner2 = new Scanner(new FileInputStream(arrstring[0]));
            n4 = scanner2.nextInt();
            for (n2 = 0; n2 < n4; ++n2) {
                n = scanner2.nextInt() % 4;
                n3 = scanner2.nextInt() % 4;
                int n6 = scanner2.nextInt() % 4;
                int n7 = scanner2.nextInt();
                TTT3D.move(n, n3, n6, n7);
            }
        } else {
            TTT3D.setAll(0);
        }
        boolean bl = false;
        while (!bl) {
            TTT3D.printBoardMarkers();
            n4 = 0;
            while (n4 == 0) {
                System.out.println("Type your move as one three digit number(lrc)");
                n2 = scanner.nextInt();
                arrn2[2] = n2 % 10 % 4;
                arrn2[1] = n2 / 10 % 10 % 4;
                arrn2[0] = n2 / 100 % 4;
                if (TTT3D.isEmpty(arrn2)) {
                    n4 = 1;
                    TTT3D.move(arrn2, 5);
                    continue;
                }
                System.out.println("Sorry that cell is occupied.");
            }
            TTT3D.compLineSums();
            if (TTT3D.findLineSum(20) != -1) {
                TTT3D.printBoardMarkers();
                System.out.println("Congradulations you won!");
                bl = true;
                continue;
            }
            n5 = TTT3D.findLineSum(3);
            if (n5 != -1) {
                TTT3D.move(TTT3D.findEmptyCel(lines[n5]), 1);
                TTT3D.printBoardMarkers();
                System.out.println("Computer wins again!");
                bl = true;
                continue;
            }
            n5 = TTT3D.findLineSum(15);
            if (n5 != -1) {
                TTT3D.move(TTT3D.findEmptyCel(lines[n5]), 1);
                continue;
            }
            arrn = TTT3D.findFork(1, lines, sums);
            if (arrn != null) {
                TTT3D.move(arrn, 1);
                continue;
            }
            arrn = TTT3D.findFork(5, lines, sums);
            if (arrn != null) {
                TTT3D.move(arrn, 1);
                continue;
            }
            n2 = random.nextInt(sums.length);
            n = 0;
            for (n3 = 0; n3 < sums.length && n == 0; ++n3) {
                switch (sums[n2]) {
                    case 0: 
                    case 1: 
                    case 2: 
                    case 5: 
                    case 10: {
                        n = 1;
                        TTT3D.move(TTT3D.findEmptyCel(lines[n2]), 1);
                    }
                }
                n2 = (n2 + 1) % sums.length;
            }
            if (n != 0) continue;
            System.out.println("game is a draw");
            bl = true;
        }
    }
}
