import java.util.*;
import java.util.Scanner;
import java.io.*;
import java.io.PrintWriter;
import java.util.regex.Pattern;

public class WordMatchTesterMasterCopy
{
   private static ArrayList<Word> words  = new ArrayList<Word>();
//   private static ArrayList<String> matches = new ArrayList<String>();

   public static void main(String[] args) throws Exception
   {
      loadData("sample1-pp.txt");
      loadData("sample2-zoo.txt");
  //    findNeighbour();
      sortData(words);
      findMatches();
   //   writeData("sample4-results.txt");
   }

   public static void loadData(String fileName) throws Exception
   {
      Scanner in = new Scanner(new File(fileName));
      String line = in.nextLine();

      while(in.hasNextLine())
      {
         line =  line.concat(in.nextLine()+" ");
      }
      StringTokenizer tokenizer = new StringTokenizer(line);
      in.close();

      while(tokenizer.hasMoreTokens())
      {
         String spelling = tokenizer.nextToken().replaceAll("[^a-zA-Z]+","").toLowerCase();
         if(spelling.length() > 0)
         {
            int index = search(spelling);
            if(index == -1)
            {
               words.add(new Word(spelling));
            }
            else
            {
               words.get(index).incrementFrequency();
            }
         }
      }
   }

   public static int search(String spelling)
   {
      int index = -1;
      for(int i = 0; i < words.size(); i++)
      {
         if(words.get(i).getSpelling().equals(spelling))
         {
            index = i;
            break;
         }
      }
      return index;
   }
   
   public static void sortData(ArrayList<Word> list)
   {
      int first = 0;
      int last = list.size()-1;

      int pos = first + 1;
      while (true)
      {
         if (pos > last)
         {
            return;               // done
         }
         else if (pos == first)
         {
            pos = first + 1;      // move right
         }
         else if (list.get(pos).getSpelling().compareTo(list.get(pos-1).getSpelling()) >= 0)
         {
            pos++;                // move right

         }
         else // list.get(pos).compareTo(list.get(pos-1)) < 0
         {
            swap(list, pos, pos-1);
            pos--;             // swap and move left
         }
      }
   }

   public static void swap(ArrayList<Word> words, int i, int j)
   {
      Word temp =  words.get(i);
      words.set(i, words.get(j));
      words.set(j, temp);
   }

   public static void findMatches() throws Exception
   {
      displayMatches("ear");//alphabets only in the middle
      displayMatches("ma?");
      displayMatches("?o?");
      displayMatches("Mr*");
      displayMatches("i*");
      displayMatches("?ear*");
      displayMatches("?ay");
      displayMatches("?e*ing");
      displayMatches("?r*");
      displayMatches("*at");
      displayMatches("*l*");
      displayMatches("?ell");
      displayMatches("and");
      displayMatches("zdkfjk");
      displayMatches("he"); // Including a test case to find words
      // that has given pattern anywhere inside the word
   }

   public static void displayMatches(String pattern) throws Exception
   {
      //matches.clear();
      boolean found = false;
      System.out.println("Displaying result of words matching pattern: " + pattern);
      for(Word w: words)
      {
         if(match(w.getSpelling(), pattern))
         {
            System.out.println(w.getSpelling() + " " + w.getFrequency());
            writeData("sample4-results.txt", w.getSpelling(), w.getFrequency());
            found = true;
         }
      }
      if(!found)
      {
         System.out.println("There are no words matching pattern: " + pattern);
      }
   }

   public static boolean match(String spelling, String pattern)
   {
      // process the input
      // check in it matches the specific word
      // In addition, checks for pattern included only with alphabets not * and
      // not ?
      String newP = "";
      boolean alphabetsOnly = true;
      for(int i = 0; i<= pattern.length()-1; i++)
      {
         if(pattern.charAt(i) == '?')
         {
            newP += "[a-z]";
            alphabetsOnly = false;            
         }
         else if (pattern.charAt(i) == '*')
         {
            newP += "[a-z]*";
            alphabetsOnly = false;
         }
         else
         {
            newP += pattern.charAt(i);
         }
      }
      if(alphabetsOnly)  // Assignment doesn't specifically tells if only alphabets are also to   
      {                  // be included in pattern testing. Patterns with * does that but this
         // case search for patterns even with out *    
         newP = "[a-z]*" + newP + "[a-z]*"; // Can also be specified for start, middle and end
      }
      return Pattern.matches(newP,spelling);
   }

   public static void writeData(String fileName, String match, int frequency) throws Exception
   {
      PrintWriter outfile = new PrintWriter(new FileWriter(fileName, true));
      //outfile.println("Pattern Matching words in alphabetical order for:  " + w);
      outfile.println(match + " " + frequency);
      outfile.close();
   }


   /*catch IOException(Exception e)
     {
     System.out.println("File does not exist");
     */
}
