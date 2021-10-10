import java.util.Scanner; // Import the Scanner class
import java.util.Arrays;


public class Game {

    static final int NLINES = 8;
    static final int NCOLUMN = 8;
    static int[] position = new int[NLINES];

    static void affichage(int[][] grid) {
        for (int j = 0; j < grid[0].length; j++) {
            if (j == 0) {
                System.out.print("| ");
            }
            System.out.print(j + " | ");
        }
        System.out.print("\n");


        for (int i = grid.length - 1; i >= 0; i--) {
            for (int j = 0; j < grid[0].length; j++) {
                if (j == 0) {
                    System.out.print("| ");
                }

                if (grid[i][j] == 1) {
                    System.out.print("X");
                } else if (grid[i][j] == 2) {
                    System.out.print("O");
                } else {
                    System.out.print(" ");
                }
                System.out.print(" | ");
            }
            System.out.print("\n");
        }
        for (int j = 0; j < grid[0].length; j++) {
            System.out.print("‾‾‾‾");
        }

    }

    static void checkWinner(int[][] grid, int player) {
        int count = 0;
        int countRight = 0;
        int countUp = 0;
        // System.out.println("Player : " + player);
        for (int i = 0; i < grid[0].length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == player) {

                    for (int r = 0; r < 4; r++) {
                        if (j <= NCOLUMN - 4) {
                            if (grid[i][j+r] == player) {
                                countRight++;
                            }
                        }
                        if (i >= 3) {
                            if (grid[i-r][j] == player) {
                                countUp++;
                            }
                        }
                    }
                    if ((countRight == 4) || (countUp == 4)) {
                        System.out.println("WINNER");
                        System.exit(0);
                    }
                    else {
                        countRight = 0;
                        countUp = 0;
                    }

                    // checkdiagonalup();
                    if ((i <= NLINES - 4) && (j <= NCOLUMN - 4)) {
                        // System.out.println("Count au départ :" + count);
                        for (int r = 0; r < 4; r++) {
                            if (grid[i+r][j+r] == player) {
                                count++;
                                // System.out.println("Count : " + count);
                                if (count == 4) {
                                    System.out.println("WINNER");
                                    System.exit(0);
                                }
                            } else {
                                count = 0;
                                break;
                            }
                        }
                    }
                    // checkdiagonaldown();
                    if ((i >= 3) && (j <= NCOLUMN - 4)) {
                        for (int r = 0; r < 4; r++) {
                            if (grid[i - r][j + r] == player) {
                                count++;
                                if (count == 4) {
                                    System.out.println("WINNER");
                                    System.exit(0);
                                }
                            } else {
                                count = 0;
                                break;
                            }
                        }
                    }
                }
            }
        }

        if (count == 4) {
            System.out.println("WINNER");
        }
    }

    static void menu() {
        System.out.println("\n\n\n\n==========================");
        System.out.println("Welcome to Connect 4 !");
        System.out.println("==========================");
        System.out.println("\n");

        System.out.println("Menu :");
        System.out.println("1. Mode 2 Players");
        System.out.println("2. Mode against bot");
        System.out.println("\n");

        System.out.println("Enter the mode : ");

        Scanner myObject = new Scanner(System.in);
        int mode = myObject.nextInt(); // Read user input

        if (mode == 1) {
            return;
        }
        else {
            System.out.println("\nMode not implemented yet...\n");
            System.exit(0);
        }
    }

    public static int[] getColumn(int[][] array, int index) {
        int[] column = new int[array[0].length]; // Here I assume a rectangular 2D array!
        for (int i = 0; i < column.length; i++) {
            column[i] = array[i][index];
        }
        return column;
    }

    public static int[] getDiagUp(int[][] array, int lin, int col) {
        int[] column = new int[array[0].length]; // Here I assume a rectangular 2D array!
        for (int i = 0; i < Math.min(NCOLUMN, NLINES) - Math.max(lin, col); i++) {
            column[i] = array[lin + i][col + i];
        }
        return column;
    }

    public static int[] getDiagDown(int[][] array, int lin, int col) {
        int[] column = new int[lin+1]; // Here I assume a rectangular 2D array!
        for (int i = 0; i <= Math.abs(lin - col); i++) {
            column[i] = array[lin - i][col + i];
        }
        return column;
    }

    public static int[] getRow(int[][] array, int index) {
        int[] column = array[index];
        return column;
    }

    public static int[] getPiece(int[] array, int start) {
        int[] smallcolumn = new int[4]; // Here I assume a rectangular 2D array!
        for (int i = start; i > start - 4; i--) {
            smallcolumn[start - i] = array[i];
        }
        return smallcolumn;
    }

    public static int count (int[] array, int objective) {
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == objective) {
                count++;
            }
        }
        return count;
    }

    public static int scoring(int[] array, int player) {
        int score = 0;
        if (count(array, player) == 4) {
            score += 1000;
        }
        else if ((count(array, player) == 3) && (count(array, 0) == 1)) {
            score += 20;
        }
        else if ((count(array, player) == 2) && (count(array, 0) == 2)) {
            score += 5;
        }
        else if ((count(array, (player % 2 ) + 1) == 3) && (count(array, 0) == 1)) {
            score -= 50;
        }
        else if (count(array, (player % 2) + 1) == 4) {
            score -= 1000;
        }
        return score;
    }

    static int giveScore(int[][] grid, int player) {
        int score = 0;
        // Colonne    
        for (int r = 0; r < NCOLUMN; r++) {
            int[] colonne = getColumn(grid, r);
            for (int s = 3; s < NLINES; s++) {
                int[] piece = getPiece(colonne, s);
                score += scoring(piece, player);
            }
        }

        // Ligne
        for (int l = 0; l < NLINES; l++) {
            int[] line = getRow(grid, l);
            for (int s = 3; s < NCOLUMN; s++) {  // Doute ici, sur le s=3
                int[] piece = getPiece(line, s);
                score += scoring(piece, player);
            }
        }

        // Diagonale Supérieure
        for (int l = 0; l < NLINES - 3; l++) {
            int[] diag = getDiagUp(grid, l, 0);
            for (int s = 3; s < diag.length; s++) { // Doute ici, sur le s=3
                int[] piece = getPiece(diag, s);
                score += scoring(piece, player);
            }
        }
        for (int r = 1; r < NCOLUMN - 3; r++) {
            int[] diag = getDiagUp(grid, 0, r);
            for (int s = 3; s < diag.length; s++) { // Doute ici, sur le s=3
                int[] piece = getPiece(diag, s);
                score += scoring(piece, player);
            }
        }

        // Diagonale Inférieure
        for (int l = 3; l < NLINES; l++) {
            int[] diag = getDiagDown(grid, l, 0);
            for (int s = 3; s < diag.length; s++) { // Doute ici, sur le s=3
                int[] piece = getPiece(diag, s);
                score += scoring(piece, player);
            }
        }
        for (int r = 1; r < NCOLUMN - 3; r++) {
            int[] diag = getDiagDown(grid, NLINES-1, r);
            for (int s = 3; s < diag.length; s++) { // Doute ici, sur le s=3
                int[] piece = getPiece(diag, s);
                score += scoring(piece, player);
            }
        }

        // System.out.println("Score de " + player + " : " + score);
        return score;
    }

    static boolean is_terminal_node(int[][] grid, int turn) {
        if (turn >= NLINES*NCOLUMN) {
            return true;
        }
        if (Math.abs(giveScore(grid, 1)) + Math.abs(giveScore(grid, 2)) > 1000) {
            return true;
        }
        return false;
    }

    static int[] minimax(int[][] grid, int depth, int player) {
        if ((depth == 0) || (is_terminal_node(grid, 2))) {
            // System.out.println("Minimax 0 : score = " + giveScore(grid, player));
            // affichage(grid);
            // System.out.println("Player = " + player);
            int[] result = {0, giveScore(grid, 2)};
            return result;
        }

        int column = 0;
        int value = 0;

        // Maximize
        if (player == 2) {
            value = -10000;
            for (int col = 0; col < NCOLUMN; col++) {
                int[][] possible_grid = addCoin(grid, col, player);
                // System.out.println("\n(" + depth + ") J2 : Place dans colonne " + col);
                int[] new_score = minimax(possible_grid, depth-1, (player%2) + 1);
                // System.out.println("(" + depth + ") J2 : Score pour colonne " + col + " : " + new_score[1]);
                if (new_score[1] > value) {
                    value = new_score[1];
                    column = col;
                }
            }

            // System.out.println("Choice = " + column + " (score = " + value + ")\n");
        }

        // Minimize
        if (player == 1) {
            value = 10000;
            for (int col = 0; col < NCOLUMN; col++) {
                int[][] possible_grid = addCoin(grid, col, player);
                int[] new_score = minimax(possible_grid, depth - 1, (player%2)+1);
                // System.out.println("(" + depth + ") J1 : Score pour colonne " + col + " : " + new_score[1]);
                if (new_score[1] < value) {
                    value = new_score[1];
                    column = col;
                }
            }

            // System.out.println("Choice = " + column + " (score = " + value + ")");
        }

        int[] result = {column, value};
        return result;
    }

    static int[][] addCoin(int[][] grid, int choice, int tour) {
        // System.out.println("Position : " + Arrays.toString(position));
        int[][] newgrid = new int[grid.length][];
        for (int i = 0; i < NLINES; i++) {
            newgrid[i] = Arrays.copyOf(grid[i], grid[i].length);
        }
        // System.out.println("Choix : " + choice + " ; Position : " + position[choice]);
        for (int j = 0; j < NLINES; j++) {
            if (newgrid[j][choice] == 0) {
                newgrid[j][choice] = ((tour + 1) % 2) + 1;;
                break;
            }
        }
        // newgrid[position[choice]][choice] = ((tour+1) % 2) + 1;
        return newgrid;
    }




    public static void main(String[] args)  {

        // menu();

        // Grille
        int[][] grid = new int[NLINES][NCOLUMN];
        
        int tour = 1;
        int player = 1;

        for (int t = 0; t < position.length; t++) {
            position[t] = 0;
        }

        for (int n = 0; n < 55; n++) {
            
            player = ((tour+1) % 2) + 1;
            System.out.println("Joueur " + player);
            int choice = 0;
            choice = minimax(grid, 3, player)[0];
            System.out.println("Choix : " + choice);
            if (player == 2) {
                
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    System.out.println("got interrupted!");
                }
                
            }
            else {
                Scanner myObj = new Scanner(System.in);
                choice = myObj.nextInt(); // Read user input
            }
            if (choice > 10) {
                return;
            }

            grid = addCoin(grid, choice, tour);
            position[choice]++;
    
            
            tour++;

            affichage(grid);
    
            
            System.out.print("\n");
            System.out.print("\n");

            
            checkWinner(grid, player);

            giveScore(grid, player);

            is_terminal_node(grid, tour);
        }

    }
}
