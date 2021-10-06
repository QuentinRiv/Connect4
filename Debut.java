import java.util.Scanner; // Import the Scanner class


public class Debut {

    static final int NLINES = 8;
    static final int NCOLUMN = 8;

    static void affichage(int[][] grid, int tour) {
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
        player = (player % 2) + 1;
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
                                    System.out.println("WINNER (diagonal up, from " + i + j + ")");
                                    // return;
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
                                    System.out.println("WINNER (diagonal down)");
                                    // return;
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
        System.out.println("==========================");
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

    public static void main(String[] args)  {

        menu();

        // Grille
        int[][] grid = new int[NLINES][NCOLUMN];
        int[] position = new int[NLINES];
        int tour = 0;

        for (int t = 0; t < position.length; t++) {
            position[t] = 0;
        }

        for (int n = 0; n < 55; n++) {
            System.out.println("Joueur " + (tour % 2 + 1));

            Scanner myObj = new Scanner(System.in);
            int column = myObj.nextInt(); // Read user input

            if (column > 10) {
                return;
            }
    
            grid[position[column]++][column] = (tour % 2) + 1;
            tour++;

            affichage(grid, tour);
    
            
            System.out.print("\n");
            System.out.print("\n");

            checkWinner(grid, (tour % 2) + 1);
        }

    }
}
