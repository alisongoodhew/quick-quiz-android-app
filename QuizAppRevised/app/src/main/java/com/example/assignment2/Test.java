package com.example.assignment2;

import androidx.annotation.RequiresApi;
import android.os.Build;
import java.util.ArrayList;
import java.util.*;

@RequiresApi(api = Build.VERSION_CODES.O)

public class Test {
    private String correctTerm;
    private String optionOne;
    private String optionTwo;
    private String optionThree;
    private String optionFour;
    private String correctDefinition;
    private ArrayList<String[]> initialArray = new ArrayList<String[]>();;
    private ArrayList<String> termArray = new ArrayList<String>();
    private ArrayList<String> definitionArray = new ArrayList<String>();
    private Map<String,String> answerMap = new HashMap<String, String>();
    private int questionsRemaining = -1000;
    private int questionsCorrect;
    private int lengthOfTest;


    //constructor
    public Test()
    {};


    //start counters
    public void initializeCounters(){
        lengthOfTest = answerMap.size();
        questionsRemaining = answerMap.size();
        questionsCorrect = 0;
    }

    //split answer and options into two arrays
    public void generateTermDefArrays() {
        //place terms and defs into separate arrays
        for (String[] a : initialArray) {
            termArray.add(a[0]);
            definitionArray.add(a[1]);
        }
    }

    //take array of definitions and add to hashmap
    public void generateHashMap()
    {
        //add to hashmap
        for (int i = 0; i < definitionArray.size(); i++) {
            answerMap.put(definitionArray.get(i),termArray.get(i));
        }
    }

    //create question/options
    public void generateQuestions(){
        //shuffle the terms and definitions arrays
        Collections.shuffle(termArray);
        Collections.shuffle(definitionArray);
        //pick first term off of the shuffled array
        setCorrectDefinition(definitionArray.get(0));
        setCorrectTerm(answerMap.get(correctDefinition));
        //create an arrayList and add the correct term to it
        //this will be the list that is set to the buttons
        ArrayList<String> termPoolArray = new ArrayList<>();
        termPoolArray.add(correctTerm);
        //to fill the terms
        for(int i = 0; i < 3; i++){
                //shuffle termArray until a term not in the termPoolArray
                while(termPoolArray.contains(termArray.get(i))){
                    Collections.shuffle(termArray);
                }
                //once it is found, add new term
                termPoolArray.add(termArray.get(i));
            }
        //shuffle the new array to prevent correct term from always being on top
        Collections.shuffle(termPoolArray);


        //assign term values to buttons
        optionOne = termPoolArray.get(0);
        optionTwo = termPoolArray.get(1);
        optionThree = termPoolArray.get(2);
        optionFour = termPoolArray.get(3);

        //remove the current term from the map
        answerMap.remove(correctDefinition);
        definitionArray.remove(correctDefinition);
    }

    //build a message for the player at end of quiz with final score
    public String createFinalMessage()
    {
            String finalScore = "your final score is " + questionsCorrect + " out of " + lengthOfTest;
            return finalScore;
    }

    //reset the score and term/definition arrays
    public void resetQuiz()
    {
        initialArray.clear();
        termArray.clear();
        definitionArray.clear();
        questionsCorrect = 0;

    }


    //Getters and setters

    public int getQuestionsRemaining() {
        return questionsRemaining;
    }

    public void setQuestionsRemaining(int questionsRemaining) {
        this.questionsRemaining = questionsRemaining;
    }

    public int getQuestionsCorrect() {
        return questionsCorrect;
    }

    public void setQuestionsCorrect(int questionsCorrect) {
        this.questionsCorrect = questionsCorrect;
    }

    public String getCorrectTerm() {
        return correctTerm;
    }

    public void setCorrectTerm(String correctTerm) {
        this.correctTerm = correctTerm;
    }

    public String getOptionOne() {
        return optionOne;
    }

    public String getOptionTwo() {
        return optionTwo;
    }

    public String getOptionThree() {
        return optionThree;
    }

    public String getOptionFour() {
        return optionFour;
    }

    public String getCorrectDefinition() {
        return correctDefinition;
    }

    public void setCorrectDefinition(String correctDefinition) {
        this.correctDefinition = correctDefinition;
    }

    public void setInitialArray(ArrayList<String[]> initialArray) {
        this.initialArray = initialArray;
    }

    public int getLengthOfTest() {
        return lengthOfTest;
    }

}
