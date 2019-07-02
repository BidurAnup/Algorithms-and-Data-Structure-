import java.util.*;
import java.util.Scanner;
import java.io.*;
import java.io.PrintWriter;

public class LexiconTesterMasterCopy
{
   private static ArrayList<Word> words  = new ArrayList<Word>();
   public static void main(String[] args) throws Exception
   {
      // ArrayList<Word> words  = new ArrayList<Word>();
      loadData("sample1-pp.txt");
      loadData("sample2-zoo.txt");
      findNeighbour();
      sortData(words);
      writeData("sample3-wordlist.txt");
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
         System.out.println(spelling);
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
         //  return index;
      }
      return index;
   }

   public static void findNeighbour()
   {
      for(Word w1: words)
      {
         for(Word w2: words)
         {
            if(IsNeighbour(w1.getSpelling(),w2.getSpelling()))
            {
               w1.addNeighbours(w2.getSpelling());
            }
         }
      }
   }

   public static boolean IsNeighbour(String s, String s2)
   {
      int count = 0;
      if(s.length()!= s2.length())
      {
         return false;
      }
      else if(s.equals(s2))
      {
         return false;
      } 
      else
      {
         for (int i = 0; i< s.length(); ++i)
         {
            if(s.charAt(i) != s2.charAt(i))
            {
               count++;
            }
         }
         if(count == 1)
         {
            return true;
         }
         else
         {
            return false;
         }
      }
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

   public static void writeData(String fileName) throws Exception
   {
      PrintWriter outfile = new PrintWriter(new File(fileName));
      for(Word w: words)
      {
         outfile.println(w);
      }
      outfile.close();
   }


   /*catch IOException(Exception e)
     {
     System.out.println("File does not exist");
     */
}
