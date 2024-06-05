import java.util.Scanner;
import java.util.ArrayList;
import doodlepad.*;
import java.io.File;
import java.io.FileNotFoundException;

public class Wordle
{
    public static Scanner inptr;
    public static ArrayList<String> words = new ArrayList<String>();
    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.out.println("Error: Incorrect Argument Line");
            System.out.println("java Wordle wordList.csv");
            return;
        }
        try
        {
            File inptWords = new File(args[0]);
            inptr = new Scanner(inptWords);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error: Word list not found");
            return;
        }
        
        String[] wordsArr = inptr.nextLine().split(",");

        for (String a : wordsArr)
        {
            words.add(a);
        }

        //choose a random word
        String ans = words.get((int) (Math.random() * words.size()));
        inptr.close();

        inptr =  new Scanner(System.in);

        String guess = inptGuess(true);
        int guessCt = 1;

        while (!guess.equals(ans) && guessCt >= 6)
        {
            printAcc(guess, ans);
            guess = inptr.nextLine();
            guess = inptGuess(false);
            guessCt++;
        }

        if (guess.equals(ans))
        {
            System.out.println("Congratulations! You guessed the word in " + guessCt + " guesses!");
        }
        else
        {
            System.out.println("Sorry, try again! The word this time was " + ans);
        }
        inptr.close();
    }

    public static String inptGuess(boolean first)
    {
        String word;
        if (first)
            System.out.print("Enter a 5-letter guess: ");
        else
            System.out.print("Enter another 5-letter guess: ");

        word = inptr.nextLine().toLowerCase();

        while (!words.contains(word))
        {
            if (word.length() > 5)
                System.out.println("Your guess is too long.");
            else if (word.length() < 5)
                System.out.println("Your guess is too short.");
            else
                System.out.println("Your guess is not a valid 5-letter word.");
            System.out.print("Enter another 5-letter guess:");
            word = inptr.nextLine().toLowerCase();
        }
        return word;
    }

    public static void printAcc(String guess, String ans)
    {
        int len = guess.length();
        ArrayList<String> charas = new ArrayList<String>();
        for (int b = 0; b < len; b++)
        {
            charas.add(guess.substring(b, b+1));
        }
        Character[] acc = new Character[5];
        for (int c = len - 1; c >= 0; c--) // first check for complete matches
        {
            if (charas.get(c).equals(ans.substring(c,c+1)))
            {
                charas.remove(c);
                acc[c] = 'O';
            }
        }

        len = charas.size();
        for (int d = len - 1; d >= 0; d--)
        {
            if (charas.contains(ans.substring(d,d+1)))
            {
                acc[d] = '/';
                charas.remove(d);
            }
        }

        for (int e = 0; e < 5; e++)
        {
            if (acc[e] == null)
            {
                acc[e] = 'X';
            }
        }
        String build = "";

        for (int f = 0; f < 5; f++)
        {
            build += acc[f].toString();
        }

        System.out.println(build);
    }
}