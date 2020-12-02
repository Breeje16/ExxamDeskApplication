package com.breejemodi.exxxamdesk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity {

    private TextView question,noindicator;
    private LinearLayout optionscontainer;
    private Button nextbtn;
    private TextView sharebtn;
    private int count=0;
    private List<QuestionModel> list;
    private int position =0;
    private int score = 0;

    private static final String FILE_NAME = "response.txt";
    private String ans;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        question = findViewById(R.id.question);
        noindicator = findViewById(R.id.no_indicator);
        sharebtn = findViewById(R.id.share_btn);
        nextbtn = findViewById(R.id.next_btn);
        optionscontainer = findViewById(R.id.options_container);
        list= new ArrayList<>();
        list.add(new QuestionModel("question1","a","b","c","d","a"));
        list.add(new QuestionModel("question2","a","b","c","d","a"));
        list.add(new QuestionModel("question3","a","b","c","d","b"));
        list.add(new QuestionModel("question4","a","b","c","d","a"));
        list.add(new QuestionModel("question5","a","b","c","d","c"));
        list.add(new QuestionModel("question6","a","b","c","d","a"));
        list.add(new QuestionModel("question7","a","b","c","d","d"));
        list.add(new QuestionModel("question8","a","b","c","d","b"));
        for(int i=0;i<4;i++){
            optionscontainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer((Button) v);
                }
            });
        }

        playAnim(question,0,list.get(position).getQuestion());
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextbtn.setEnabled(false);
                nextbtn.setAlpha(0.7f);
                enableOption(true);

                position++;
                if(position == list.size()){
                    ////score activity
                    Intent scoreIntent = new Intent(QuestionsActivity.this,ScoreActivity.class);
                    scoreIntent.putExtra("score",score);
                    scoreIntent.putExtra("total",list.size());
                    startActivity(scoreIntent);
                    finish();
                    return;
                }
                count=0;
                playAnim(question,0,list.get(position).getQuestion());
            }
        });

        new Thread(new Runnable() {
            public void run() {

                try {
                    new CountDownTimer(300000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            sharebtn.setText("Time Left: " + millisUntilFinished / 1000);
                        }

                        public void onFinish() {
                            sharebtn.setText("Done!");
                            Intent scoreIntent = new Intent(QuestionsActivity.this,ScoreActivity.class);
                            scoreIntent.putExtra("score",score);
                            scoreIntent.putExtra("total",list.size());
                            startActivity(scoreIntent);
                            finish();

                        }
                    }.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }).start();


    }
    private void playAnim(final View view, final int value, final String data){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                if(value==0 && count<4) {
                    String option="";
                    if(count==0){
                        option = list.get(position).getOptionA();
                    }
                    else if (count==1){
                        option = list.get(position).getOptionB();
                    }
                    else if (count==2){
                        option = list.get(position).getOptionC();
                    }
                    else if (count==3){
                        option = list.get(position).getOptionD();
                    }
                    playAnim(optionscontainer.getChildAt(count),0,option);
                    count++;
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {

                if(value==0 ){
                    try{
                        ((TextView)view).setText(data);
                        noindicator.setText(position+1+"/"+list.size());
                    }
                    catch (ClassCastException ex){
                        ((Button)view).setText(data);
                    }
                    view.setTag(data);
                    playAnim(view,1,data);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }
    private void checkAnswer(Button selectedOption){
        enableOption(false);
        nextbtn.setEnabled(true);
        nextbtn.setAlpha(1);
        ans = selectedOption.getText().toString();

        //Storing Response to File:

        FileOutputStream fos = null;

        try {

            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(ans.getBytes());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (selectedOption.getText().toString().equals(list.get(position).getCorrectANS())){
            //correct
            score++;
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
        }
        else{
            //incorrect
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
            Button correctoption =(Button) optionscontainer.findViewWithTag(list.get(position).getCorrectANS());
            correctoption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
        }
    }


    private void enableOption(boolean enable){
        for(int i=0;i<4;i++){
            optionscontainer.getChildAt(i).setEnabled(enable);
            if(enable){
                optionscontainer.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#989898")));
            }
        }
    }
}