import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Form_Unigrams extends Main{


    public  ArrayList<String> Tokens_into_a_list() throws IOException
    {
        ArrayList<String> stopwordList = new ArrayList<>();

        stopwordList.add("a");
        stopwordList.add("isn");
        stopwordList.add("b");
        stopwordList.add("c");
        stopwordList.add("d");
        stopwordList.add("e");
        stopwordList.add("f");
        stopwordList.add("g");
        stopwordList.add("h");
        stopwordList.add("i");
        stopwordList.add("j");
        stopwordList.add("k");
        stopwordList.add("l");
        stopwordList.add("m");
        stopwordList.add("n");
        stopwordList.add("o");
        stopwordList.add("p");
        stopwordList.add("q");
        stopwordList.add("r");
        stopwordList.add("s");
        stopwordList.add("t");
        stopwordList.add("u");
        stopwordList.add("v");
        stopwordList.add("w");
        stopwordList.add("x");
        stopwordList.add("y");
        stopwordList.add("z");
        stopwordList.add("ll");
        stopwordList.add("than");
        stopwordList.add("that");
        stopwordList.add("that's");
        stopwordList.add("the");
        stopwordList.add("their");
        stopwordList.add("theirs");
        stopwordList.add("them");
        stopwordList.add("themselves");
        stopwordList.add("then");
        stopwordList.add("there");
        stopwordList.add("there's");
        stopwordList.add("when");
        stopwordList.add("when's");
        stopwordList.add("where");
        stopwordList.add("where's");
        stopwordList.add("which");
        stopwordList.add("while");
        stopwordList.add("who");
        stopwordList.add("who's");
        stopwordList.add("whom");
        stopwordList.add("why");
        stopwordList.add("why's");
        stopwordList.add("with");
        stopwordList.add("won't");
        stopwordList.add("would");
        stopwordList.add("wouldn't");
        stopwordList.add("you");
        stopwordList.add("you'd");
        stopwordList.add("you'll");
        stopwordList.add("you're");
        stopwordList.add("you've");
        stopwordList.add("your");
        stopwordList.add("didn");
        stopwordList.add("hasn");
        stopwordList.add("don");
        stopwordList.add("about");
        stopwordList.add("above");
        stopwordList.add("after");
        stopwordList.add("again");
        stopwordList.add("against");
        stopwordList.add("all");
        stopwordList.add("am");
        stopwordList.add("an");
        stopwordList.add("do");
        stopwordList.add("does");
        stopwordList.add("doesn't");
        stopwordList.add("doing");
        stopwordList.add("don't");
        stopwordList.add("down");
        stopwordList.add("during");
        stopwordList.add("each");
        stopwordList.add("few");
        stopwordList.add("these");
        stopwordList.add("they");
        stopwordList.add("they'd");
        stopwordList.add("they'll");
        stopwordList.add("they're");
        stopwordList.add("they've");
        stopwordList.add("this");
        stopwordList.add("those");
        stopwordList.add("through");
        stopwordList.add("to");
        stopwordList.add("too");
        stopwordList.add("under");
        stopwordList.add("until");
        stopwordList.add("up");
        stopwordList.add("very");
        stopwordList.add("was");
        stopwordList.add("wasn't");
        stopwordList.add("we");
        stopwordList.add("ve");
        stopwordList.add("we'd");
        stopwordList.add("we'll");
        stopwordList.add("we're");
        stopwordList.add("we've");
        stopwordList.add("were");
        stopwordList.add("weren't");
        stopwordList.add("what");
        stopwordList.add("what's");
        stopwordList.add("for");
        stopwordList.add("from");
        stopwordList.add("further");
        stopwordList.add("had");
        stopwordList.add("hadn't");
        stopwordList.add("has");
        stopwordList.add("hasn't");
        stopwordList.add("have");
        stopwordList.add("haven't");
        stopwordList.add("having");
        stopwordList.add("he");
        stopwordList.add("he'd");
        stopwordList.add("he'll");
        stopwordList.add("he's");
        stopwordList.add("her");
        stopwordList.add("here");
        stopwordList.add("here's");
        stopwordList.add("hers");
        stopwordList.add("herself");
        stopwordList.add("him");
        stopwordList.add("himself");
        stopwordList.add("his");
        stopwordList.add("how");
        stopwordList.add("how's");
        stopwordList.add("i");
        stopwordList.add("i'd");
        stopwordList.add("i'll");
        stopwordList.add("i'm");
        stopwordList.add("i've");
        stopwordList.add("if");
        stopwordList.add("in");
        stopwordList.add("into");
        stopwordList.add("is");
        stopwordList.add("isn't");
        stopwordList.add("it");
        stopwordList.add("it's");
        stopwordList.add("its");
        stopwordList.add("itself");
        stopwordList.add("let's");
        stopwordList.add("me");
        stopwordList.add("more");
        stopwordList.add("most");
        stopwordList.add("mustn't");
        stopwordList.add("my");
        stopwordList.add("myself");
        stopwordList.add("no");
        stopwordList.add("nor");
        stopwordList.add("not");
        stopwordList.add("of");
        stopwordList.add("off");
        stopwordList.add("on");
        stopwordList.add("once");
        stopwordList.add("only");
        stopwordList.add("or");
        stopwordList.add("other");
        stopwordList.add("ought");
        stopwordList.add("our");
        stopwordList.add("ours");
        stopwordList.add("ourselves");
        stopwordList.add("out");
        stopwordList.add("over");
        stopwordList.add("own");
        stopwordList.add("same");
        stopwordList.add("shan't");
        stopwordList.add("she");
        stopwordList.add("she'd");
        stopwordList.add("and");
        stopwordList.add("any");
        stopwordList.add("are");
        stopwordList.add("aren't");
        stopwordList.add("as");
        stopwordList.add("at");
        stopwordList.add("be");
        stopwordList.add("because");
        stopwordList.add("been");
        stopwordList.add("before");
        stopwordList.add("being");
        stopwordList.add("below");
        stopwordList.add("between");
        stopwordList.add("both");
        stopwordList.add("but");
        stopwordList.add("by");
        stopwordList.add("can't");
        stopwordList.add("cannot");
        stopwordList.add("could");
        stopwordList.add("couldn't");
        stopwordList.add("did");
        stopwordList.add("didn't");
        stopwordList.add("she'll");
        stopwordList.add("");
        stopwordList.add(" ");
        stopwordList.add("she's");
        stopwordList.add("should");
        stopwordList.add("shouldn't");
        stopwordList.add("so");
        stopwordList.add("some");
        stopwordList.add("such");
        stopwordList.add("yours");
        stopwordList.add("yourself");
        stopwordList.add("yourselves");


        //initializing a Array List to store words
        ArrayList<String> words = new ArrayList<String>();

        //reference to input stream reader
        BufferedReader reader= null;

        //Error handling reading input through try,catch

        try {
            reader = new BufferedReader(new FileReader("input/amazon.txt"));
            String line = null;
            while ((line = reader.readLine()) != null) {

                // Regex for removing all punctuations
                line = line.replaceAll("\\p{Punct}", " ").toLowerCase();

                // Regex for removing all numbers/digits
                line = line.replaceAll("[^a-zA-Z ]", " ");

                // Storing all the tokens of a single line of input temporarily(line by line iterations)

                String[] midwords = line.split("\\s+");
                for (int i = 0; i < midwords.length; i++) {
                    if (/*midwords[i].equals("")||midwords[i].equals("null")||midwords[i].equals(" ")*/ stopwordList.contains(midwords[i]))
                    {
                        continue;
                    }
                    words.add(midwords[i]);

                }

            }
        }

        catch(Exception e)
        {
            // Printing Excess information about error
            e.printStackTrace();
            System.out.println("\namazon.txt file not found in the input folder\n");
            System.out.println("\nInput File should be named as amazon.txt and moved to input folder before running");
        }

        finally

        {
            // Always close the reader after use
            reader.close();
        }
        return words;

    }
}
