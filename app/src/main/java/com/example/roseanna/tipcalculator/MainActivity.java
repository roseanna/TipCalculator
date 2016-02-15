package com.example.roseanna.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.*;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.*;
import android.util.*;
public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {
    int tip             = 0;
    float bill          = 0;
    float billWithTip   = 0;
    int numPeople       = 0;

    SeekBar     seekBar;
    EditText    tipValue;
    EditText    billValue;
    TextView    resultText;
    Button      reset;
    Button      roundUP;
    Spinner     spinner;
    String[]    peopleArray = {"1","2","3","4","5"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar     = (SeekBar) findViewById(R.id.seekBar1);
        tipValue    = (EditText) findViewById(R.id.tipText);
        resultText  = (TextView) findViewById(R.id.billValue);
        billValue   = (EditText) findViewById(R.id.billWithTip);
        reset       = (Button) findViewById(R.id.reset);
        roundUP     = (Button) findViewById(R.id.roundUp);
        spinner     = (Spinner) findViewById(R.id.spinner1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                peopleArray);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        tipValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                tipValue.setSelection(tipValue.length());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tipValue.setSelection(tipValue.length());
            }

            @Override
            public void afterTextChanged(Editable s) {
                seekBar.setProgress(Integer.valueOf(String.valueOf(tipValue.getText())));
                tipValue.setSelection(tipValue.length());
            }
        });
        billValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                billValue.setSelection(billValue.length());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                billValue.setSelection(billValue.length());
            }

            @Override
            public void afterTextChanged(Editable s) {
                calculate();
                uncheckRB();
                billValue.setSelection(billValue.length());
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tipValue.setText(String.valueOf(progress));
                tip = progress;
                calculate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipValue.setText("0");
                billValue.setText("0");
                resultText.setText("0");
                uncheckRB();

            }
        });

        roundUP.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                billWithTip = Float.valueOf(String.valueOf(Math.ceil(Double.parseDouble(String.valueOf(billWithTip)))));
                setText();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String people = peopleArray[position];
        numPeople = Integer.valueOf(String.valueOf(people));
        calculate();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onRadioButtonClicked(View view){
        Log.i("Radio", String.valueOf(view.getId()));
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.r1:
                if (checked)
                    seekBar.setProgress(5);
                    tipValue.setText("5");
                    break;
            case R.id.r2:
                if (checked)
                    seekBar.setProgress(10);
                    tipValue.setText("10");
                    break;
            case R.id.r3:
                if(checked)
                    seekBar.setProgress(15);
                    tipValue.setText("15");
                    break;
        }
        calculate();
    }

    public void calculate(){
        if(billValue.getText().length() == 0)
            bill = 0;
        else
            bill = Integer.valueOf(String.valueOf(billValue.getText()));
        billWithTip = bill + bill*tip/100;
        billWithTip = billWithTip/numPeople;
        setText();
    }
    public void setText(){
        String result = "$" + String.valueOf(billWithTip);
        resultText.setText(result);
    }
    public void uncheckRB(){
        RadioButton r1 = (RadioButton) findViewById(R.id.r1);
        RadioButton r2 = (RadioButton) findViewById(R.id.r2);
        RadioButton r3 = (RadioButton) findViewById(R.id.r3);
        r1.setChecked(false);
        r2.setChecked(false);
        r3.setChecked(false);
    }
}
