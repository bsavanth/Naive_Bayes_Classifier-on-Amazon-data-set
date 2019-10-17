import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class ContextBuilder {
    String token1, token2;

    ArrayList words = new ArrayList<>(); // Array list which holds all the tokens in the corpus
    ArrayList<String> word1_contexts=new ArrayList<>();        // Array list to store contexts of first word
    ArrayList<String> word2_contexts= new ArrayList<>();       // Array list to store contexts of second word
    ArrayList <String> word1_training_set = new ArrayList<>(); // Array list to store contexts of training set of first word
    ArrayList <String> word1_testing_set = new ArrayList<>();  // Array list to store contexts of testing set of first word
    ArrayList <String> word2_training_set = new ArrayList<>(); // Array list to store contexts of training set of second word
    ArrayList <String> word2_testing_set = new ArrayList<>();  // Array list to store contexts of testing set of second word
    Hashtable<String, Integer> word1_vocab= new Hashtable<String, Integer>();     // Hash table to store words and their frequencies of word 1 in its training set
    Hashtable<String, Integer> word2_vocab= new Hashtable<String, Integer>();     // Hash table to store words and their frequencies of word 2 in its training set
    Hashtable<String, Integer> total_vocab = new Hashtable<String, Integer>();    // Hash table to store all the words in both the training contexts.
    Hashtable<String, Double> word1_vocab_prob= new Hashtable<String, Double>();  // Hash table to store Naive-bayes probabilities of words in word 1 training set
    Hashtable<String, Double> word2_vocab_prob = new Hashtable<String, Double>(); // Hash table to store Naive-bayes probabilities of words in word 2 training set

    int token1_count_incontext, token2_count_incontext; // These two are to count total tokens in each of the training sets

    double word_1_train, word_1_test, word_2_train, word_2_test; // percentage of training and testing data in the available contexts


   int correct1=0, correct2=0, wrong1=0,wrong2=0;   // Variables to count Correct and incorrect classifications of word 1 and word 2 testing set.


    int window_size;
    String output;
    String type;


    // Default constructor which calls all the methods necessary for our code


    public ContextBuilder (String a, String b, ArrayList<String> unigrams, int window_size, String outputpath, String type) throws IOException {



         this.token1=a;
         this.token2=b;
         this.words=unigrams;
         this.window_size = window_size;
         this.type=type;
         this.output = outputpath;




         // Method to extract contexts of word1 and word2 from global list of words
         Contextextractor(token1, words, token1, token2, window_size);
         Contextextractor(token2,words, token1, token2, window_size);


         // Number of training and testing contexts

         if(type=="unbalanced") {
             int word1_contexts_size = word1_contexts.size();
             int word2_contexts_size = word2_contexts.size();

             this.word_1_train = (int)(0.80 * (word1_contexts_size));
             this.word_1_test = (int) (0.20 * (word1_contexts_size));
             this.word_2_train = (int)(0.80 * (word2_contexts_size));
             this.word_2_test =(int)(0.20 * (word2_contexts_size));


         }
         else if(type=="balanced")
         {
             int word1_contexts_size = word1_contexts.size();
             int word2_contexts_size = word2_contexts.size();
             double lesser = word1_contexts_size<word2_contexts_size?word1_contexts_size:word2_contexts_size;


             this.word_1_train =(int) (0.8*lesser);
             this.word_1_test = (int)(0.2*lesser);
             this.word_2_train = (int) (0.8*lesser);
             this.word_2_test = (int)(0.2*lesser);



         }


        // Dividing all available contexts of word1, word2 into training and testing data

         Word_traintest_Builder(token1,word1_contexts, word_1_train, word_1_test, token1, token2);
         Word_traintest_Builder(token2, word2_contexts, word_2_train, word_2_test, token1, token2);

         // Building vocabulary for words in the training contexts of word1 and word2

         Word_vocab_builder(token1,word1_training_set, token1, token2);
         Word_vocab_builder(token2,word2_training_set, token1, token2);

         
         // Calculating probabilties for all the words in both word1 and word 2 senses

         double prior_of_word1 = (double)word_1_train / (double)(word_1_train + word_2_train);
         double prior_of_word2 = (double)word_2_train / (double)(word_1_train + word_2_train);
         Word_vocab_prob_builder(word1_vocab, word2_vocab, total_vocab);





         // classifying the testing data for their senses

         classify(word1_testing_set, word2_testing_set, total_vocab, word1_vocab_prob, word2_vocab_prob,prior_of_word1, prior_of_word2);


         // Printing the data
        try {
           PrintData(token1, token2, correct1, correct2, wrong1, wrong2);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


    }

    public  void Contextextractor(String token, ArrayList<String> words, String token1, String token2, int window_size) throws IOException {


        for (int kl = window_size; kl < words.size() - window_size; kl++) {
            if (words.get(kl).equals(token) && token == token1) {
                if (window_size == 10) {
                    word1_contexts.add(words.get(kl - 10) + " " + words.get(kl - 9) + " " + words.get(kl - 8) + " " + words.get(kl - 7) + " " + words.get(kl - 6) + " " + words.get(kl - 5) + " " + words.get(kl - 4) + " " + words.get(kl - 3) + " " + words.get(kl - 2) + " " + words.get(kl - 1) + " " + words.get(kl + 1) + " " + words.get(kl + 2) + " " + words.get(kl + 3) + " " + words.get(kl + 4) + " " + words.get(kl + 5) + " " + words.get(kl + 6) + " " + words.get(kl + 7) + " " + words.get(kl + 8) + " " + words.get(kl + 9) + " " + words.get(kl + 10));
                } else if (window_size == 5) {
                    word1_contexts.add(words.get(kl - 5) + " " + words.get(kl - 4) + " " + words.get(kl - 3) + " " + words.get(kl - 2) + " " + words.get(kl - 1) + " "+ words.get(kl + 1) + " " + words.get(kl + 2) + " " + words.get(kl + 3) + " " + words.get(kl + 4) + " " + words.get(kl + 5));
                }
                else if (window_size ==20)
                {
                    word1_contexts.add(words.get(kl - 20) + " " + words.get(kl - 19) + " " + words.get(kl - 18) + " " + words.get(kl - 17) +words.get(kl - 16) + " " + words.get(kl - 15) + " " + words.get(kl - 14) + " " + words.get(kl - 13) + " " + words.get(kl - 12) + " " + words.get(kl - 11) + " " +words.get(kl - 10) + " " + words.get(kl - 9) + " " + words.get(kl - 8) + " " + words.get(kl - 7) + " " + words.get(kl - 6) + " " + words.get(kl - 5) + " " + words.get(kl - 4) + " " + words.get(kl - 3) + " " + words.get(kl - 2) + " " + words.get(kl - 1) + " "  + words.get(kl + 1) + " " + words.get(kl + 2) + " " + words.get(kl + 3) + " " + words.get(kl + 4) + " " + words.get(kl + 5) + " " + words.get(kl + 6) + " " + words.get(kl + 7) + " " + words.get(kl + 8) + " " + words.get(kl + 9) + " " + words.get(kl + 10)+ " "+ words.get(kl + 11) + " " + words.get(kl + 12) + " " + words.get(kl + 13) + " " + words.get(kl + 14) + " " + words.get(kl + 15) + " " + words.get(kl + 16) + " " + words.get(kl + 17) + " " + words.get(kl + 18) + " " + words.get(kl + 19) + " " + words.get(kl + 20));

                }


            } else if (words.get(kl).equals(token) && token == token2) {
                if (window_size == 10) {

                    word2_contexts.add(words.get(kl - 10) + " " + words.get(kl - 9) + " " + words.get(kl - 8) + " " + words.get(kl - 7) + " " + words.get(kl - 6) + " " + words.get(kl - 5) + " " + words.get(kl - 4) + " " + words.get(kl - 3) + " " + words.get(kl - 2) + " " + words.get(kl - 1) + " "  + words.get(kl + 1) + " " + words.get(kl + 2) + " " + words.get(kl + 3) + " " + words.get(kl + 4) + " " + words.get(kl + 5) + " " + words.get(kl + 6) + " " + words.get(kl + 7) + " " + words.get(kl + 8) + " " + words.get(kl + 9) + " " + words.get(kl + 10));
                } else if (window_size == 5) {
                    word2_contexts.add(words.get(kl - 5) + " " + words.get(kl - 4) + " " + words.get(kl - 3) + " " + words.get(kl - 2) + " " + words.get(kl - 1) + " " + words.get(kl + 1) + " " + words.get(kl + 2) + " " + words.get(kl + 3) + " " + words.get(kl + 4) + " " + words.get(kl + 5));
                }
                else if (window_size == 20)
                {
                    word2_contexts.add(words.get(kl - 20) + " " + words.get(kl - 19) + " " + words.get(kl - 18) + " " + words.get(kl - 17) +words.get(kl - 16) + " " + words.get(kl - 15) + " " + words.get(kl - 14) + " " + words.get(kl - 13) + " " + words.get(kl - 12) + " " + words.get(kl - 11) + " " +words.get(kl - 10) + " " + words.get(kl - 9) + " " + words.get(kl - 8) + " " + words.get(kl - 7) + " " + words.get(kl - 6) + " " + words.get(kl - 5) + " " + words.get(kl - 4) + " " + words.get(kl - 3) + " " + words.get(kl - 2) + " " + words.get(kl - 1) + " " + words.get(kl + 1) + " " + words.get(kl + 2) + " " + words.get(kl + 3) + " " + words.get(kl + 4) + " " + words.get(kl + 5) + " " + words.get(kl + 6) + " " + words.get(kl + 7) + " " + words.get(kl + 8) + " " + words.get(kl + 9) + " " + words.get(kl + 10)+ " "+ words.get(kl + 11) + " " + words.get(kl + 12) + " " + words.get(kl + 13) + " " + words.get(kl + 14) + " " + words.get(kl + 15) + " " + words.get(kl + 16) + " " + words.get(kl + 17) + " " + words.get(kl + 18) + " " + words.get(kl + 19) + " " + words.get(kl + 20));

                }

            }


        }


    }






    public  void Word_traintest_Builder(String word, ArrayList<String> context, double train_instances, double test_instaces, String token1, String token2)
    {

            int i=0;
            while(i< (train_instances+test_instaces) )
            {
                if(i<train_instances) {
                    if (word == token1) {
                        word1_training_set.add(word1_contexts.get(i));

                        i++;
                    } else {
                        word2_training_set.add(word2_contexts.get(i));

                        i++;
                    }
                }
                else
                {
                    if(word==token1) {
                        word1_testing_set.add(word1_contexts.get(i));
                        i++;
                    }
                    else
                    {
                        word2_testing_set.add(word2_contexts.get(i));
                        i++;
                    }
                }
            }



    }





    public void Word_vocab_builder(String word, ArrayList<String> WordContext,String token1,String token2)

    {
        for(int j=0;j< WordContext.size();j++)
        {
            String s = WordContext.get(j);
            String[] token_in_context=s.split(" ");
            for(int k=0;k<token_in_context.length;k++)
            {
                if(word==token1) {
                    token1_count_incontext++;

                    if (word1_vocab.containsKey(token_in_context[k])) {
                        word1_vocab.put(token_in_context[k], word1_vocab.get(token_in_context[k]) + 1);

                    } else {

                        word1_vocab.put(token_in_context[k], 1);
                        if(!total_vocab.containsKey(token_in_context[k]))
                        {
                            total_vocab.put(token_in_context[k],1);
                        }
                    }


                }
                else
                {
                    token2_count_incontext++;
                    if (word2_vocab.containsKey(token_in_context[k]))
                    {
                        word2_vocab.put(token_in_context[k], word2_vocab.get(token_in_context[k]) + 1);
                    }
                    else

                        {
                            word2_vocab.put(token_in_context[k], 1);

                            if(!total_vocab.containsKey(token_in_context[k]))
                            {
                                total_vocab.put(token_in_context[k],1);

                            }

                        }



                }

            }
        }

    }

    public void Word_vocab_prob_builder(Hashtable<String, Integer> word1_vocab,Hashtable<String, Integer> word2_vocab, Hashtable<String, Integer> total_vocab ) {
        Set<String> keys = total_vocab.keySet();

        for (String s : keys) {
            double w_c1 = 0;
            if (word1_vocab.containsKey(s)) {
                w_c1 = word1_vocab.get(s);
            }
            double prob_key = (1 + w_c1) / (token1_count_incontext + total_vocab.size());
            word1_vocab_prob.put(s, prob_key);

            double w_c2 = 0;
            if (word2_vocab.containsKey(s)) {
                w_c2 = word2_vocab.get(s);
            }
            prob_key = (1 + w_c2) / (token2_count_incontext + total_vocab.size());
            word2_vocab_prob.put(s, prob_key);

        }


    }


    public void classify(ArrayList<String> word1_testing_set, ArrayList<String> word2_testing_set, Hashtable<String, Integer> total_vocab, Hashtable<String, Double> word1_vocab_prob, Hashtable<String, Double> word2_vocab_prob, Double wprob1, Double wprob2 )
    {

        double prob1;
        double prob2;
        for(String s:word1_testing_set)
        {
            prob1=wprob1;
            prob2=wprob2;
            String[] words = s.split(" ");
            for(int i=0;i<words.length;i++)
            {
                if(word1_vocab_prob.containsKey(words[i])) {
                    prob1 = (double)prob1 * (double)(word1_vocab_prob.get(words[i]));
                }
                if(word2_vocab_prob.containsKey(words[i])) {

                    prob2 = (double)prob2 * (double)(word2_vocab_prob.get(words[i]));
                }
            }
           // System.out.println(prob1+"    "+prob2);


            if(prob1>prob2)
            {
                correct1++;
            }
            else
            {
                wrong1++;
            }


        }

        for(String s:word2_testing_set)
        {
            prob1=wprob1;
            prob2=wprob2;
            String[] words = s.split(" ");
            for(int i=0;i<words.length;i++)
            {
                if(word1_vocab_prob.containsKey(words[i])) {
                    prob1 = (double)prob1 * (double)(word1_vocab_prob.get(words[i]));
                }

                if(word2_vocab_prob.containsKey(words[i])) {
                    prob2 = (double)prob2 * (double)(word2_vocab_prob.get(words[i]));
                }

            }

           // System.out.println(prob2+"    "+prob1);
            if(prob2>prob1)
            {
                correct2++;
            }
            else
            {
                wrong2++;
            }
        }





    }



       public void PrintData(String token1,String token2, int correct1, int correct2, int wrong1, int wrong2) throws IOException

       {

           // Writing training contexts into word1_trainingset_windowsize.txt"

           System.out.println("\n"+"Total comtexts for "+token1+" are: "+word1_contexts.size()+"\n");
           System.out.println(""+"Total comtexts for "+token2+" are: "+word2_contexts.size()+"\n");


           BufferedWriter buff1 = new BufferedWriter(new FileWriter(output + "/" + token1 + "_train" + "_" + window_size + ".txt"));

           buff1.write("\n\n" + "Number of contexts of " + token1 + " in train set are " + word1_training_set.size() + "\n\n\n\n\n\n\n\n\n");
           System.out.println("\n\n" + "Number of contexts of " + token1 + " in train set are " + word1_training_set.size() + "");
           for (String s : word1_training_set) {
               buff1.write(s);
               buff1.newLine();
           }
           buff1.close();


           BufferedWriter buff2 = new BufferedWriter(new FileWriter(output + "/" + token1 + "_test" + "_" + window_size + ".txt"));
           buff2.write("\n\n" + "Number of contexts of " + token1 + " in test set are " + word1_testing_set.size());
           System.out.println("\n" + "Number of contexts of " + token1 + " in test set are " + word1_testing_set.size() + "\n\n");



           for (String s : word1_testing_set) {
               buff2.write(s);
               buff2.newLine();
           }
           buff2.close();


           BufferedWriter buff3 = new BufferedWriter(new FileWriter(output + "/" + token2 + "_train" + "_" + window_size + ".txt"));
           buff3.write("\n\n" + "Number of contexts of " + token2 + " in train set are " + word2_training_set.size() + "\n\n\n\n\n\n\n");
           System.out.println("\n" + "Number of contexts of " + token2 + " in train set are " + word2_training_set.size() + "");


           for (String s : word2_testing_set) {
               buff3.write(s);
               buff3.newLine();
           }
           buff3.close();

           BufferedWriter buff4 = new BufferedWriter(new FileWriter(output + "/" + token2 + "_test" + "_" + window_size + ".txt"));
           buff4.write("\n\n" + "Number of contexts of " + token2 + " in test set are " + word2_testing_set.size() + "\n\n\n\n\n\n");
           System.out.println("\n" + "Number of contexts of " + token2 + " in test set are " + word2_testing_set.size() + "\n\n");


           for (String s : word2_testing_set) {
               buff4.write(s);
               buff4.newLine();
           }
           buff4.close();

           BufferedWriter buff5 = new BufferedWriter(new FileWriter(output + "/" + (token1+"_"+ token2) + "_" + window_size + "_results" + ".txt"));

           buff5.write("\n\n\n\n");
           buff5.write("Total contexts for " + token1 + " in the corpus are: " + word1_contexts.size());
           buff5.write("\n\n\n");
           buff5.write("Total contexts for " + token2 + " in the corpus are: " + word2_contexts.size());
           buff5.write("\n\n\n");
           buff5.write("Number of Contexts for " + token1 + " in the train set are: " + word1_training_set.size());
           buff5.write("\n\n\n");
           buff5.write("Number of Contexts for " + token1 + " in the test set are: " + word1_testing_set.size());
           buff5.write("\n\n\n\n");
           buff5.write("Number of Contexts for " + token2 + " in the train set are: " + word2_training_set.size());
           buff5.write("\n\n\n");
           buff5.write("Number of Contexts for " + token2 + " in the test set are: " + word2_testing_set.size());

           buff5.write("\n\n\n\n");


           buff5.write("Accuracy of Naive_bayes classification on testing contexts of " + token1 + " is: " + ((double)(correct1) / (double)(correct1 + wrong1)) * 100 + "%");
           System.out.println("\n\nAccuracy of Naive_bayes classification on testing contexts of " + token1 + " is: " + ((double)(correct1) / (double)(correct1 + wrong1)) * 100 + "%\n");
           buff5.write("\nCorrect classifications: " + correct1 + "\t" + "Incorrect classifications: " + wrong1);
           System.out.println("Correct classifications: " + correct1 + "\t" + "Incorrect classifications: " + wrong1);

           buff5.write("\n\n\n\n");

           buff5.write("Accuracy of Naive_bayes classification on testing contexts of " + token2 + " is: " + ((double)(correct2) /(double) (correct2 + wrong2)) * 100 + "%");
           System.out.println("\n\nAccuracy of Naive_bayes classification on testing contexts of " + token2 + " is: " + ((double)(correct2) / (double)(correct2 + wrong2)) * 100 + "%\n");
           buff5.write("\nCorrect classifications: " + correct2 + "\t" + "Incorrect classifications: " + wrong2);
           System.out.println("Correct classifications: " + correct2 + "\t" + "Incorrect classifications: " + wrong2+"\n");
           buff5.write("\n\n\n\n\n");


           buff5.write("ACCURACY OF CLASSIFIER OVER THE WHOLE TESTING SET IS:" + ( ((double)(correct1 + correct2) / (double)(wrong1 + correct1+correct2+wrong2)) * 100) + "%");

           System.out.println((token1+token2)+", window_size used:"+window_size+", "+"ACCURACY= " + ( ((double)(correct1 + correct2) / (double)(wrong1 + correct1+correct2+wrong2)) * 100) + "%");

           buff5.write("\n\n\n");
            buff5.close();


            }



}


















