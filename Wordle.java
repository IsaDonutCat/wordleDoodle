import java.util.Scanner;
import java.util.ArrayList;
import doodlepad.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.function.IntPredicate;

public class Wordle
{
    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.out.println("Error: Incorrect Argument Line");
            System.out.println("java Wordle wordList.csv");
            return;
        }
        Scanner inptr;
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
        
        ArrayList<String> words = new ArrayList<String>();
        String[] wordsArr = inptr.nextLine().split(",");

        for (String a : wordsArr)
        {
            words.add(a);
        }
        //choose a random word
        String ans = words.get((int) (Math.random() * words.size()));
        inptr.close();

    }
}