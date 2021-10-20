import java.util.Scanner; // Import the Scanner class
import java.util.Arrays;


public class Game {

    // Global constant variable
    static final int NLINES = 8;
    static final int NCOLUMN = 8;

    // Show the grid, with the pieces inside
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

        System.out.print("\n");

    }

    // Starting menu
    static int menu() {
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

        return mode;
    }

    // Select a column from the grid
    public static int[] getColumn(int[][] array, int index) {
        int[] column = new int[array[0].length]; // Here I assume a rectangular 2D array!
        for (int i = 0; i < column.length; i++) {
            column[i] = array[i][index];
        }
        return column;
    }

    // Select a diagonal from the grid
    public static int[] getDiagUp(int[][] array, int lin, int col) {
        int[] column = new int[array[0].length]; // Here I assume a rectangular 2D array!
        for (int i = 0; i < Math.min(NCOLUMN, NLINES) - Math.max(lin, col); i++) {
            column[i] = array[lin + i][col + i];
        }
        return column;
    }

    // Select a diagonal from the grid
    public static int[] getDiagDown(int[][] array, int lin, int col) {
        int[] column = new int[lin+1]; // Here I assume a rectangular 2D array!
        for (int i = 0; i <= Math.abs(lin - col); i++) {
            column[i] = array[lin - i][col + i];
        }
        return column;
    }

    // Select a row from the grid
    public static int[] getRow(int[][] array, int index) {
        int[] column = array[index];
        return column;
    }

    // Get four elements for a column / row / diagonal
    public static int[] getPiece(int[] array, int start) {
        int[] smallcolumn = new int[4]; // Here I assume a rectangular 2D array!
        for (int i = start; i > start - 4; i--) {
            smallcolumn[start - i] = array[i];
        }
        return smallcolumn;
    }

    // Count the number of occurence of a given element in an array
    public static int count (int[] array, int objective) {
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == objective) {
                count++;
            }
        }
        return count;
    }

    // Compute a score for a player, given a set of 4 pieces
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

    // Give a score for a player, given a grid
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
            for (int s = 3; s < NCOLUMN; s++) {
                int[] piece = getPiece(line, s);
                score += scoring(piece, player);
            }
        }

        // Diagonale Supérieure
        for (int l = 0; l < NLINES - 3; l++) {
            int[] diag = getDiagUp(grid, l, 0);
            for (int s = 3; s < diag.length; s++) {
                int[] piece = getPiece(diag, s);
                score += scoring(piece, player);
            }
        }
        for (int r = 1; r < NCOLUMN - 3; r++) {
            int[] diag = getDiagUp(grid, 0, r);
            for (int s = 3; s < diag.length; s++) {
                int[] piece = getPiece(diag, s);
                score += scoring(piece, player);
            }
        }

        // Diagonale Inférieure
        for (int l = 3; l < NLINES; l++) {
            int[] diag = getDiagDown(grid, l, 0);
            for (int s = 3; s < diag.length; s++) {
                int[] piece = getPiece(diag, s);
                score += scoring(piece, player);
            }
        }
        for (int r = 1; r < NCOLUMN - 3; r++) {
            int[] diag = getDiagDown(grid, NLINES-1, r);
            for (int s = 3; s < diag.length; s++) {
                int[] piece = getPiece(diag, s);
                score += scoring(piece, player);
            }
        }

        return score;
    }

    // Test to see if the game is over (either a player wins or ex-aequo)
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
                int[] new_score = minimax(possible_grid, depth-1, (player%2) + 1);
                if (new_score[1] > value) {
                    value = new_score[1];
                    column = col;
                }
            }
        }

        // Minimize
        if (player == 1) {
            value = 10000;
            for (int col = 0; col < NCOLUMN; col++) {
                int[][] possible_grid = addCoin(grid, col, player);
                int[] new_score = minimax(possible_grid, depth - 1, (player%2)+1);
                if (new_score[1] < value) {
                    value = new_score[1];
                    column = col;
                }
            }
        }

        int[] result = {column, value};
        return result;
    }

    // Add a piece in the grid
    static int[][] addCoin(int[][] grid, int choice, int tour) {
        int[][] newgrid = new int[grid.length][];
        for (int i = 0; i < NLINES; i++) {
            newgrid[i] = Arrays.copyOf(grid[i], grid[i].length);
        }

        for (int j = 0; j < NLINES; j++) {
            if (newgrid[j][choice] == 0) {
                newgrid[j][choice] = ((tour + 1) % 2) + 1;;
                break;
            }
        }
        return newgrid;
    }



    // Main function
    public static void main(String[] args)  {

        int mode = menu();

        // Grille
        int[][] grid = new int[NLINES][NCOLUMN];
        
        int tour = 1;
        int player;


        for (int n = 0; n < 50; n++) {
            
            player = ((tour+1) % 2) + 1;
            System.out.println("Joueur " + player);

            int choice = 0;
            
            if ((player == 2) && (mode == 2)){
                choice = minimax(grid, 3, player)[0];
                System.out.println("-> Choix : " + choice);
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    System.out.println("got interrupted!");
                }
            }
            else {
                Scanner myObj = new Scanner(System.in);
                try {
                    choice = myObj.nextInt(); // Read user input
                } catch (java.util.InputMismatchException e) {
                    System.out.println("\nGame interrupted !");
                    System.exit(0);
                }
            }


            // Add a Piece, and obtain a new grid
            grid = addCoin(grid, choice, tour++);
    
            // Show the grid
            affichage(grid);
    
            // Check is the game is finished
            if (is_terminal_node(grid, tour)) {
                System.out.print("\nWINNER !");
                System.exit(0);
            }
        }

    }
}
