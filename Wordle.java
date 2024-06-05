import java.util.Scanner;
import java.util.ArrayList;
import doodlepad.*;
import java.io.File;
import java.io.FileNotFoundException;

public class Wordle
{
    public static Scanner inptr;
    public static ArrayList<RoundRect> rects = new ArrayList<RoundRect>();
    public static ArrayList<Text> letters = new ArrayList<Text>();
    public static ArrayList<String> words = new ArrayList<String>();
    public static int guessCt;
     //50 left right margins, 10 between columns
     //50 up and down margins, 10 between rows
     //100 x 100 squares
    public static Pad board;
    static int[] xPoses = {50, 160, 270, 380, 490};
    static int[] yPoses = {50, 160, 270, 380, 490, 600};
    static int[][] colors = {{100, 175, 100}, {255, 200, 50}, {150, 150, 150}}; //green, yellow, grey

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
        board= new Pad(640, 750);
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
        guessCt = 1;
        printAcc(guess, ans);

        while (!guess.equals(ans) && guessCt < 6)
        {
            //System.out.print("while loop entered");
            guess = inptGuess(false);
            guessCt++;
            //rearranging some stuff to help
            printAcc(guess, ans);

            //System.out.println(guessCt);
        }

        if (guess.equals(ans))
        {
            System.out.println("Congratulations! You guessed the word in " + guessCt + " guesses!");
        }
        else
        {
            System.out.println("Sorry, try again! The word this time was " + ans);
        }
        System.out.println("Hit enter to close the program.");
        guess  = inptr.nextLine();
        inptr.close();
        board.close();
        return;
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
        for (int c = 0; c < 5; c++) // first check for complete matches
        {
            if (ans.substring(c,c+1).equals(charas.get(c))) //strings equal eachother
            {
                charas.set(c, "/");
                ans = ans.substring(0, c) + ":" + ans.substring(c+1);
                acc[c] = 'O';
            }
        }

        for (int d = 0; d < 5; d++)
        {
            if (charas.contains(ans.substring(d,d+1)))
            {
                //System.out.println("contains " + ans.substring(d,d+1) + " at " + charas.indexOf(ans.substring(d,d+1)));
                acc[charas.indexOf(ans.substring(d,d+1))] = '/';
                charas.set(charas.indexOf(ans.substring(d,d+1)), "/");
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
        dispAcc(guess, acc);
        System.out.println(build);
    }

    public static void dispAcc(String guess, Character[] acc)
    {
        int xInd = 0;
        int colInd;
        for (Character g : acc)
        {
            rects.add(new RoundRect(xPoses[xInd], yPoses[guessCt - 1], 100, 100, 25, 25));

            if (g == 'O')
                colInd = 0;
            else if (g == '/')
                colInd = 1;
            else
                colInd = 2;
            rects.get(rects.size() - 1).setFillColor(colors[colInd][0], colors[colInd][1], colors[colInd][2]);
            rects.get(rects.size() - 1).setStrokeWidth(0);
            letters.add(new Text(guess.substring(xInd, xInd+1).toUpperCase(), xPoses[xInd] + 10, yPoses[guessCt-1] + 10, 100));
            letters.get(letters.size() - 1).setFillColor(255);
            xInd++;
        }
    }
}