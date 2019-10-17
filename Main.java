import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Main {

    public static void main(String args[]) throws IOException {

        ArrayList<String> unigrams = new ArrayList<>();
        Form_Unigrams f = new Form_Unigrams();
        unigrams= f.Tokens_into_a_list();

        String outputpath1;
        String outputpath;
        File outputDir;
        String word1, word2;
        int window_size;
        String training_type=null;
        word1=args[0].toLowerCase();
        word2 = args[1].toLowerCase();
        window_size = Integer.parseInt(args[2]);
        if(!(window_size==5 || window_size==10 || window_size==20 ))
        {
            System.out.println("\nEnter a proper window size, try again...\n ");
            System.exit(-1);
        }
        int typeinput =Integer.parseInt(args[3]);

        if(!(typeinput==1||typeinput==2))
        {
            System.out.println("\nEnter either 1 or 2 for training type, try again...\n");
            System.exit(-1);
        }
        if(typeinput==1)
         {
             training_type="unbalanced";
         }
         else if(typeinput==2)
         {
             training_type="balanced";
         }

         outputpath1 = (word1+"_"+word2+"_"+"output");
         outputDir = new File(outputpath1);
         if (!outputDir.exists()) {
            if (outputDir.mkdir()) {
                System.out.println("Output directory with the name "+(word1+word2)+" is created!");
            } else {

                System.out.println("\nFailed to create output directory!! Please try again....\n");
            }
         }
        outputpath = outputDir.getAbsolutePath();


        ContextBuilder cb = new ContextBuilder(word1, word2, unigrams, window_size, outputpath, training_type);

    }

}

