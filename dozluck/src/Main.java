import java.util.Scanner;
import java.util.Random;

import static java.lang.Math.abs;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Welcome to dozluck! ---");
        Scanner in = new Scanner(System.in);
        System.out.println("All numbers in this game are represented in dozenal.\n" +
                "I am going to roll a dice with 100 sides. Each round I will roll it twice, once for me and once for you.\n" +
                "Scores will be accumulative, your roll will be added to your other rolls.\n" +
                "We will play 10 rounds, the highest score at the end of the 10 rounds will win.\n" +
                "If you roll a prime number, you will be awarded an extra roll.\n" +
                "If your final score is divisible by 10, your total score will double.\n" +
                "Press enter to indicate your readiness:");
        in.nextLine();
        runDozLuck();
        System.out.println("--- Goodbye! ---");

    }

    public static String dozenalise(int decimal) {
        String digit;
        int numdigit;
        String dozenal;
        int currentpow;
        boolean fsf;
        fsf = true;
        dozenal = "";
        for (int x = 3; x > -1; x--) {
            currentpow = (int) Math.pow(12, x);
            numdigit = decimal / currentpow;
            digit = String.valueOf(numdigit);
            if (numdigit != 0 || (fsf == false)) {
                if (numdigit == 10) {
                    digit = "X";
                }
                if (numdigit == 11) {
                    digit = "E";
                }
                dozenal += digit;
                decimal -= (numdigit * currentpow);
                fsf = false;
            }
        }
        return dozenal;
    }

    public static void runDozLuck() {
        int playertotal = 0;
        int comptotal = 0;
        int playerround;
        int compround;
        int playerbonus;
        int compbonus;
        Scanner s = new Scanner(System.in);
        Random r = new Random();
        for(int i = 12; i > 0; i--){
            playerround = r.nextInt(144) + 1;
            compround = r.nextInt(144) + 1;
            playerbonus = 0;
            compbonus = 0;
            if(i == 1){
                System.out.println("This is the final round. Get ready.");
                int dif = abs(playertotal - compbonus);
                if(dif < 283){
                    System.out.println("With a difference between us of only " + dozenalise(dif) + ", anything could happen");
                } else {
                    if (comptotal > playertotal) {
                        System.out.println("You're going to need to end on a multiple of 10 to win.");
                    } else {
                        System.out.println("Unless I end on a multiple of 10, you will win.");
                    }
                }
            }
            System.out.println("I have rolled " + dozenalise(compround) + " and you have rolled " + dozenalise(playerround));
            boolean comprime = isPrime(compround);
            boolean playprime = isPrime(playerround);
            if(comprime && playprime){
                System.out.println("As both our numbers are prime, we both get a second roll.");
                compbonus = r.nextInt(144) +1;
                playerbonus = r.nextInt(144) + 1;
                System.out.println("You have rolled " + dozenalise(playerbonus) + " and I have rolled " + dozenalise(compbonus) + ".");
                playerround += playerbonus;
                compround += compbonus;
            } else if (comprime) {
                System.out.println("As my number is prime, I get a second roll.");
                compbonus = r.nextInt(144) + 1;
                System.out.println("I have rolled " + dozenalise(compbonus)+".");
                compround += compbonus;
            } else if (playprime) {
                System.out.println("As your number is prime, you get a second roll.");
                playerbonus = r.nextInt(144) + 1;
                System.out.println("You have rolled " + dozenalise(playerbonus) +".");
                playerround += playerbonus;
            }
            if(playerround > compround){
                System.out.println("Which, of course, means you have won this round.");
            } else if (compround > playerround) {
                System.out.println("Which, of course means I have won this round.");
            }else{
                System.out.println("Which ends this round in a draw.");
            }
            playertotal += playerround;
            comptotal += compround;
            System.out.println("In total, I have " + dozenalise(comptotal) + " and you have " + dozenalise(playertotal)+".");
            if(playertotal > comptotal){
                System.out.println("Which puts you in the lead.");
                System.out.println("... for now.");
            } else if (comptotal > playertotal) {
                System.out.println("Which means I am currently winning.");
            } else {
                System.out.println("Which means we are drawing.");
            }
            System.out.println("Press enter to continue.");
            s.nextLine();

        }
        System.out.println("So the 10 rounds are over.");
        System.out.println("In total, my score is now " + dozenalise(comptotal) +" and yours is " + dozenalise(playertotal) +".");
        boolean playerdozen = false;
        boolean compdozen = false;
        if (playertotal % 12 == 0){
            playerdozen = true;
            playertotal *= 2;
        }
        if (comptotal % 12 == 0){
            compdozen = true;
            comptotal *=2;
        }
        if (compdozen && playerdozen){
            System.out.println("Both our scores are multiples of 10, so they have both doubled.");
        } else if (compdozen) {
            System.out.println("My score divides my 10 so it has doubled.");
        } else if (playerdozen) {
            System.out.println("Your score divides my 10 so it has doubled.");
        }else {
            System.out.println("Neither divides by 10, so those are our final scores.");
        }
        if(compdozen || playerdozen){
            System.out.println("Which means your final score is " + dozenalise(playertotal) + " and mine is "+ dozenalise(comptotal) +".");
        }
        if(comptotal > playertotal){
            System.out.println("Which means I have won.");
        } else if (playertotal > comptotal) {
            System.out.println("Which makes you the winner.");
        } else{
            System.out.println("Which means we have drawn.");
        }
        System.out.println("Good game, player.");
        System.out.println("Play again? (y/n)");
        if(s.nextLine().toUpperCase().equals("Y")){
            System.out.println("Good choice. Let us begin.");
            System.out.println("");
            runDozLuck();
        }else {
            System.out.println("Very well.");
        }
    }

    public static boolean isPrime(int num){
        if(num > 1) {
            if (num % 2 == 0) {
                return false;
            }
            for (int x = 3; x > Math.sqrt(num); x += 2) {
                if (num % x == 0) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
