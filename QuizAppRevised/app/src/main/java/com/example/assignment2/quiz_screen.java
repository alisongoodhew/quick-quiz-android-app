package com.example.assignment2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@RequiresApi(api = Build.VERSION_CODES.O)
public class quiz_screen extends AppCompatActivity {

    //instantiate new User
    User currentUser = new User();
    Test newTest = new Test();

    private TextView qNumTextView;
    private TextView scoreTextView;
    private TextView rightWrongTextView;
    private TextView answerTextView;
    private Button btnAnswerOne;
    private Button btnAnswerTwo;
    private Button btnAnswerThree;
    private Button btnAnswerFour;
    private Button btnReturn;
    private Button btnNext;

    //retrieve from textfile
    //WIP - This will read from intent with user's choice of quiz
    public void getInputStream() {
        String stringRead;
        BufferedReader br;
        ArrayList<String[]> initialArray = new ArrayList<String[]>();
        String error;
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.testfile);
            br = new BufferedReader(new InputStreamReader(inputStream));
            while ((stringRead = br.readLine()) != null) {
                initialArray.add(stringRead.split("#"));
            }
            inputStream.close();
            newTest.setInitialArray(initialArray);
        } catch (
                IOException e) {

            error = e.getMessage();
            logErrors(error);
        } catch (Exception e) {
            error = e.getMessage();
            logErrors(error);
        }
    }

    //function for logging errors to .txt
    public void logErrors(String error)
    {
        Timestamp errorTime = new Timestamp(System.currentTimeMillis());
        String errorMsg = errorTime + ": " + error;
        try {
            FileOutputStream fileOutput = openFileOutput("errorLog.txt", MODE_APPEND);
            OutputStreamWriter output = new OutputStreamWriter(fileOutput);
            output.write(errorMsg);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //assign values to buttons and answer text
    public void assignButtons() {
        rightWrongTextView.setText(newTest.getCorrectDefinition());
        btnAnswerOne.setText(newTest.getOptionOne());
        btnAnswerTwo.setText(newTest.getOptionTwo());
        btnAnswerThree.setText(newTest.getOptionThree());
        btnAnswerFour.setText(newTest.getOptionFour());
    }

    //is there a single command that lets your toggle on/off all in one line?
    //disable all quiz buttons
    public void disableButtons() {
        btnAnswerOne.setEnabled(false);
        btnAnswerTwo.setEnabled(false);
        btnAnswerThree.setEnabled(false);
        btnAnswerFour.setEnabled(false);
    }

    //re-enable buttons
    public void enableButtons(){
        btnAnswerOne.setEnabled(true);
        btnAnswerTwo.setEnabled(true);
        btnAnswerThree.setEnabled(true);
        btnAnswerFour.setEnabled(true);
    }

    //add to score and update score for user
    public void adjustScore(int number){
        newTest.setQuestionsCorrect((newTest.getQuestionsCorrect())+ number);
        String scoreText = newTest.getQuestionsCorrect() + "/" + newTest.getLengthOfTest();
        scoreTextView.setText(scoreText);
    }


    //highlight selected button answer to indicate correct or incorrect choice
    //
    public void highlightAnswer(Button chosenButton){
        //if the user's choice matches the hashmap answer
        if(chosenButton.getText() == newTest.getCorrectTerm()){
            //increase score, set button to green-tone
            chosenButton.setBackgroundColor(Color.parseColor("#315C2B"));
            chosenButton.setTextColor(Color.parseColor("#EEEEEE"));
            adjustScore(1);
            //inform user in upper textbox
            answerTextView.setTextColor(Color.parseColor("#315C2B"));
            answerTextView.setText("Correct!");

        }
        //if user's choice is incorrect
        else{
            //set button to red-ton
            chosenButton.setBackgroundColor(Color.parseColor("#FF5747"));
            //inform user in upper textbox
            answerTextView.setTextColor(Color.parseColor("#FF5747"));
            String correctAnswer = "Correct Answer: " + newTest.getCorrectTerm();
            answerTextView.setText(correctAnswer);

        }
        //prevent user from pressing another button
        disableButtons();
    }


    //decrement the number of questions remaining in quiz
    public void countdownQuestionsRemaining(int number){
        //set text to inform user
        newTest.setQuestionsRemaining(newTest.getQuestionsRemaining() - number);
        String numQuestionsText = newTest.getQuestionsRemaining() + " left!";
        qNumTextView.setText(numQuestionsText);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_screen);

        //connect all components to xml counterparts
        qNumTextView = (TextView) findViewById(R.id.qNumTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        rightWrongTextView = (TextView) findViewById(R.id.rightWrongTextView);
        answerTextView = (TextView) findViewById(R.id.answerTextView);
        btnAnswerOne = (Button) findViewById(R.id.btnAnswerOne);
        btnAnswerTwo = (Button) findViewById(R.id.btnAnswerTwo);
        btnAnswerThree = (Button) findViewById(R.id.btnAnswerThree);
        btnAnswerFour = (Button) findViewById(R.id.btnAnswerFour);
        btnReturn = (Button) findViewById(R.id.btnReturn);
        btnNext = (Button) findViewById(R.id.btnNext);

        getInputStream();

        //check if initial arrayLists are already made
        if (newTest.getQuestionsRemaining() == -1000) {
            newTest.generateTermDefArrays();
            newTest.generateHashMap();
            newTest.initializeCounters();
        }

        //generate questions from hashmap
        newTest.generateQuestions();
        assignButtons();
        adjustScore(0);
        countdownQuestionsRemaining(0);


        //OnClickListeners for answer buttons

        btnAnswerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if answer is correct
                highlightAnswer(btnAnswerOne);
            }
        });

        btnAnswerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if answer is correct
                highlightAnswer(btnAnswerTwo);

            }
        });

        btnAnswerThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if answer is correct
                highlightAnswer(btnAnswerThree);
            }
        });

        btnAnswerFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if answer is correct
                highlightAnswer(btnAnswerFour);
            }
        });


        //OnClickListener for return to main menu button
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reset quiz
                newTest.resetQuiz();
                //send back to previous page
                Intent i = new Intent(quiz_screen.this, first_screen.class);
                startActivityForResult(i, 1);

            }
        });

        //OnClickListener for next question button
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countdownQuestionsRemaining(1);
                //if quiz is complete
                if (newTest.getQuestionsRemaining() == 0) {
                    //display final score message with username
                    String finalString = currentUser.getUserName() + ", " +
                            newTest.createFinalMessage();
                    rightWrongTextView.setText(finalString);
                    //reset parameters for quiz
                    newTest.resetQuiz();
                    currentUser.setUserName("");
                    //disable next button
                    btnNext.setEnabled(false);
                    //highlight return button
                    btnReturn.setBackgroundColor(Color.parseColor("#FF5747"));

                //if more questions remaining
                } else {
                    //call a new question and options
                    newTest.generateQuestions();
                    //assign options to buttons
                    assignButtons();
                    //set color to option buttons
                    String colorString = "#DCE2E5";
                    btnAnswerOne.setBackgroundColor(Color.parseColor(colorString));
                    btnAnswerTwo.setBackgroundColor(Color.parseColor(colorString));
                    btnAnswerThree.setBackgroundColor(Color.parseColor(colorString));
                    btnAnswerFour.setBackgroundColor(Color.parseColor(colorString));
                    //reset answer textview
                    answerTextView.setText("");
                    //enable option buttons
                    enableButtons();
                    //reset all button text colors
                    btnAnswerOne.setTextColor(Color.BLACK);
                    btnAnswerTwo.setTextColor(Color.BLACK);
                    btnAnswerThree.setTextColor(Color.BLACK);
                    btnAnswerFour.setTextColor(Color.BLACK);

                }
            }
        });
        //check for the incoming intent from the first screen
        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                String receivedName = extras.getString("KEY");
                //take bundled string from first screen, set it here
                currentUser.setUserName(receivedName);
            }
        //catch error if intent does not correctly load
        }catch(RuntimeException e){
            String error = e.getMessage();
            logErrors(error);
        }catch(Exception e){
            String error = e.getMessage();
            logErrors(error);
        }
    }

}