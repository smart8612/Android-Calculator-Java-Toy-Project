package com.example.smart8612.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.smart8612.calculator.MathEval.postfixChanger;
import static com.example.smart8612.calculator.MathEval.postfixEval;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textView;

    Button[] keyNum = new Button[10];
    Button[] operator = new Button[7];

    Integer[] btnNumID = {
            R.id.btn0,
            R.id.btn1, R.id.btn2, R.id.btn3,
            R.id.btn4, R.id.btn5, R.id.btn6,
            R.id.btn7, R.id.btn8, R.id.btn9
    };
    Integer[] btnOptID = {
            R.id.btnP, R.id.btnM, R.id.btnMul,
            R.id.btnDe, R.id.btnDot, R.id.btnC,
            R.id.btnEval
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load User Interface
        textView = (TextView) findViewById(R.id.formularTextView);

        for (int i = 0; i < btnNumID.length; i++) {
            keyNum[i] = (Button) findViewById(btnNumID[i]);
            keyNum[i].setOnClickListener(this);
        }

        for (int i = 0; i < btnOptID.length; i++) {
            operator[i] = (Button) findViewById(btnOptID[i]);
            operator[i].setOnClickListener(this);
        }

    }


    // Call Back Function
    @Override
    public void onClick(View v) {
        String tmpTxt = textView.getText().toString();

        for (int i = 0; i < btnOptID.length; i++) {
            if (v.getId() == btnOptID[i]) {
                String keyTxt = operator[i].getText().toString();

                switch (keyTxt) {
                    case "C":
                        tmpTxt = "";
                        break;
                    case "=":
                        tmpTxt = postfixChanger(tmpTxt);
                        tmpTxt = String.valueOf(postfixEval(tmpTxt));
                        break;

                    default:
                        if (tmpTxt.length() > 0) {
                            Character tmplast = tmpTxt.charAt(tmpTxt.length() - 1);

                            if (tmplast == '+' || tmplast == '-' || tmplast == '*' || tmplast == '/') {
                                tmpTxt = tmpTxt.substring(0, tmpTxt.length() - 1);
                                tmpTxt += keyTxt;
                                break;

                            } else if (tmplast == '.') {
                                tmpTxt = tmpTxt.substring(0, tmpTxt.length() - 1);
                                tmpTxt += keyTxt;
                                break;

                            } else {
                                tmpTxt += keyTxt;
                                break;
                            }

                        } else {
                            break;
                    }
                }
            }
        }

        for (int i = 0; i < btnNumID.length; i++) {
            if (v.getId() == btnNumID[i]) {
                tmpTxt += keyNum[i].getText().toString();
                break;
            }

        }

        textView.setText(tmpTxt);
        
    }



}
