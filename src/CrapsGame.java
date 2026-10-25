import java.util.Scanner;
import java.util.Random;

public class CrapsGame {
    /**
     * Handles user input for simple yes or no.
     * @param userPrompt the userPrompt the user receives.
     * @return the user's validated response
     */
    public static String userYesOrNo(String userPrompt) {
        Scanner in = new Scanner(System.in);
        String response;
        boolean isValid = false;

        // User instructions.

        // Use scanner to receive input and then remove whitespace. Validates input for 'yes' and 'no' cases.
        do {
            System.out.print(userPrompt);
            response = in.nextLine().strip();
            if (response.equalsIgnoreCase("y") || response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("n") || response.equalsIgnoreCase("no")) {
                response = response.toLowerCase().substring(0,1);
                isValid = true;
            } else {
                System.out.printf("You entered '%s'. '%s' is not a valid response! Please enter either yes or no!%n", response, response);
            }
        } while(!isValid);
        return response;
    }

    /**
     * Handles the phase of the game where the user is trying for point.
     * @param point the value the user is rolling for.
     * @return the final sum the user rolls.
     */
    public static int tryingForPoint(int point) {
        // Declarations
        Random roller = new Random();
        int diceOne = 0;
        int diceTwo = 0;
        int sum = 0;
        String response;
        boolean isValid = false;

        // Repeat what the point is;
        System.out.printf("%d is the point.%n", point);

        while(true) {
            // Validates only a 'yes' from the user.
            do {
                response = userYesOrNo("Ready to throw the dice? (Y)>> ");
                if (response.equals("y")) {
                    diceOne = roller.nextInt(1, 7);
                    diceTwo = roller.nextInt(1, 7);
                    sum = diceOne + diceTwo;
                    isValid = true;
                } else {
                    System.out.print("You must answer with a yes when you are ready!");
                }
            } while (!isValid);
            isValid = false;

            // Outputs the results and then checks to see if point was met or a 7 was rolled.
            System.out.printf("The first dice rolled: %d.%nThe second dice rolled: %d.%nYou rolled: %d.%n", diceOne, diceTwo, sum);
            if (sum == point || sum == 7) {
                return sum;
            }
        }
    }

    /**
     * Main program flow.
     * @param args command line arguments.
     */
    public static void main(String[] args){
        // Declarations
        Random roller = new Random();
        int diceOne;
        int diceTwo;
        int sum;
        int point;
        int wins = 0;
        int losses = 0;
        String response;
        boolean playGame = false;

        // Player starts the game. Though not explicitly defined by the conditional, the game does not start if the player enters 'no' or 'n' as playGame is never set to true.
        response = userYesOrNo("Would you like to play Craps? (Y/N)>> ");
        if(response.equals("y")) {
            playGame = true;
        }

        // The game repeats as long as the user states they want to keep playing.
        while(playGame) {
            diceOne = roller.nextInt(1, 7);
            diceTwo = roller.nextInt(1, 7);
            sum = diceOne + diceTwo;
            System.out.printf("The first dice rolled: %d.%nThe second dice rolled: %d.%nYou rolled: %d.%n", diceOne, diceTwo, sum);

            // Crapping out.
            if (sum == 2 || sum == 3 || sum == 12) {
                System.out.printf("You rolled a '%d.' You crapped out! You lose!%n", sum);
                losses += 1;
            // Rolling a natural.
            } else if (sum == 7 || sum == 11) {
                System.out.printf("You rolled a '%d.' It's a natural! You win!%n", sum);
                wins += 1;
            // Trying for point.
            } else {
                point = sum;
                sum = tryingForPoint(point);
                if(sum == point) {
                    System.out.printf("You rolled a %d. Your roll hit the point of %d. Point is made! You win!%n", sum, point);
                    wins += 1;
                } else {
                    System.out.printf("You rolled a %d. Seven out! You lose!%n", sum);
                    losses += 1;
                }
            }

            // Output score.
            System.out.printf("You've won %d times. You've lost %d times. (%d-%d)%n", wins, losses, wins, losses);
            // Prompt user to play again or not.
            response = userYesOrNo("Would you like to play again? (Y/N)>> ");
            if(response.equals("n")) {
                playGame = false;
            }
        }
    }
}
