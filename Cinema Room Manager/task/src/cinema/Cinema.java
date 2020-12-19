package cinema;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of rows:");
        int row = scanner.nextInt();

        System.out.println("Enter the number of seats in each row:");
        int col = scanner.nextInt();
        System.out.println();

        char[][] seats = new char[row][col];
        for (char[] rows : seats) {
            Arrays.fill(rows, 'S');
        }

        char choice = 'a';
        int rowSeat = 0;
        int colSeat;
        int currentIncome = 0;

        while (choice != '0') {
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");

            choice = scanner.next().charAt(0);
            System.out.println();

            switch (choice) {
                case '1': {
                    displaySeats(seats);
                    System.out.println();
                    break;
                }
                case '2': {
                    boolean isBookedFlag = false;

                    while (!isBookedFlag) {
                        System.out.println("Enter a row number:");
                        rowSeat = scanner.nextInt();
                        System.out.println("Enter a seat number in that row:");
                        colSeat = scanner.nextInt();

                        if (checkArrayBounds(row, col, rowSeat, colSeat)) {
                            System.out.println("\nWrong input!\n");

                        } else if (isBooked(seats, rowSeat, colSeat)) {
                            System.out.println("\nThat ticket has already been purchased!\n");

                        } else {
                            bookSeat(seats, row, col, rowSeat, colSeat);
                            isBookedFlag = true;
                        }
                    }

                    System.out.println();

                    currentIncome += calculateTicketCost(row, col, rowSeat);
                    break;
                }
                case '3': {
                    System.out.println("Number of purchased tickets: "
                            + countPurchasedTickets(seats, row, col));

                    displayPercentage(row, col, countPurchasedTickets(seats, row, col));

                    System.out.printf("\nCurrent income: $%d%n", currentIncome);

                    displayTotalIncome(row, col);
                    System.out.println();
                    break;
                }
                case '0': {
                    break;
                }
                default: {
                    System.out.println("Unsuitable action! Try again.");
                }
            }
        }
    }

    public static int calculateTicketCost(int row, int col, int rowSeat) {
        int ticketCost;

        if (row * col < 60) {
            ticketCost = 10;
        } else {
            if (rowSeat <= row / 2) {
                ticketCost = 10;
            } else {
                ticketCost = 8;
            }
        }

        return ticketCost;
    }

    public static void displaySeats(char[][] seats) {
        int i;
        System.out.print("Cinema:\n ");

        for (i = 1; i <= seats[0].length; i++) {
            System.out.print(" " + i);
        }
        System.out.println();

        i = 0;
        for (char[] rows : seats) {
            System.out.print(i + 1);
            for (char val : rows) {
                System.out.print(" " + val);
            }
            System.out.println();
            i++;
        }
    }

    public static int countPurchasedTickets(char[][] seats, int row, int col) {
        int counter = 0;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (seats[i][j] == 'B') {
                    counter++;
                }
            }
        }

        return counter;
    }

    public static void displayPercentage(int row, int col, int purchasedTickets) {
        double percent = (double) purchasedTickets / (row * col) * 100;

        System.out.printf("Percentage: %.2f", percent);
        System.out.print("%");
    }

    public static void displayTotalIncome(int row, int col) {
        int totalCost;

        if (row * col < 60) {
            totalCost = row * col * 10;

        } else {
            totalCost = row / 2 * col * 10 + (row - row / 2) * col * 8;
        }

        System.out.println("Total income: $" + totalCost);
    }

    public static boolean checkArrayBounds(int row, int col, int rowSeat, int colSeat) {
        return rowSeat - 1 < 0 || rowSeat - 1 > row - 1 ||
                colSeat - 1 < 0 || colSeat - 1 > col - 1;
    }

    public static boolean isBooked(char[][] seats, int rowSeat, int colSeat) {
        return seats[rowSeat - 1][colSeat - 1] == 'B';
    }

    public static void bookSeat(char[][] seats, int row, int col, int rowSeat, int colSeat) {
        seats[rowSeat - 1][colSeat - 1] = 'B';
        System.out.printf("\nTicket price: $%d",
                calculateTicketCost(row, col, rowSeat));
        System.out.println();
    }
}