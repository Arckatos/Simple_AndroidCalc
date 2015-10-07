package filipebotelho.lab3androidcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
/**
 * <h3>Class: CalculatorActivity</h3>
 * <h4>Description:</h4>
 *Create a calculator with a Gui for an android device.
 * @author    Filipe Botelho
 * @version   1.0.0 15/10/05
 */
public class CalculatorActivity extends AppCompatActivity {

    private static double total;
    private boolean clearDigitFlag;

    private String firstButtonPressed;


    private double oldNumber;
    private double newNumber;
    private NumberFormat nmbFormat;
    private String opButtonPressed;

    private boolean digitFlag;
    private boolean oppFlag;
    private boolean equalFlag;
    private boolean totalFlag;

    public CalculatorActivity()
    {
        digitFlag=false;//true if digit was pressed
        oppFlag=false; //true if operation was pressed
        equalFlag=false; //true if equals was pressed
        totalFlag=false; //true if there is a total value
        opButtonPressed="";
        oldNumber=0;
        newNumber=0;
        firstButtonPressed="";
        clearDigitFlag =false;//used to clear the current digits
        total=0;
        nmbFormat = new DecimalFormat("0.##########E+0");

    }

    public void enterDigit(View view)
    {

        TextView textV = (TextView)findViewById(R.id.digitDisplay);
        String buttonPressed = ((Button)view).getText().toString();
        String digit = (String) textV.getText();

        if(clearDigitFlag)
        {
            digit="0";
            clearDigitFlag =false;

        }

        //this condition ensures that the number doesn't start with a zero example: 0123
        if(digit.contentEquals("0"))
            //if the first button was the dot
            if(buttonPressed.contains("."))
                digit="0.";
            else
                //add the first digit on the display variable
                digit = buttonPressed;
        //this else-if ensures that there is only one dot in the number
        else if(digit.contains("."))
            {
                if (!buttonPressed.contains("."))
                    digit += buttonPressed;//will add all numbers as decimals at this point
            }
            else
                digit += buttonPressed;
        //add the current number to the display
        newNumber=Double.parseDouble(digit);
        textV.setText(digit);



        digitFlag=true;
        if(oppFlag) {//Sequence: digit,operation,digit
            total = oldNumber;
            totalFlag=true;
        }
        else if(equalFlag)//Sequence: equals,digit
            totalFlag=false;

        oppFlag=false;
        equalFlag =false;



    }//end method enterDigit(View view)

    public void enterOperation(View view) {

        opButtonPressed = ((Button) view).getText().toString();



        clearDigitFlag = true;
        if(digitFlag&&!totalFlag&&!oppFlag)//Sequence:digit,operation
        {
            oldNumber=newNumber;
            firstButtonPressed = opButtonPressed;
        }

        else if(totalFlag&&!equalFlag&&digitFlag)//Sequence: digit,operation,digit,operation
        {

            enterEquals(view);
            firstButtonPressed = opButtonPressed;
        }
        else if(equalFlag)//Sequence:equals,operation
        {
            firstButtonPressed = opButtonPressed;
        }
        else if(oppFlag)//Sequence:operation,operation
        {
            firstButtonPressed = opButtonPressed;
        }


        digitFlag=false;
        oppFlag=true;
        equalFlag =false;


    }

    public void enterEquals(View view) {

        TextView textV = (TextView) findViewById(R.id.digitDisplay);

        opButtonPressed = ((Button) view).getText().toString();


        if(oppFlag&&!digitFlag)//Sequence: equals,operation,equals
        {
            newNumber = Double.parseDouble((String) textV.getText());
            switch (firstButtonPressed) {

                case "+":
                    total = oldNumber + newNumber;
                    break;
                case "-":
                    total = oldNumber - newNumber;
                    break;
                case "*":
                    total = oldNumber * newNumber;
                    break;
                default:
                    total = oldNumber / newNumber;


            }
            oldNumber = total;

            String temp = "" + total;


            if (temp.length() >= 10)
                temp = nmbFormat.format(Double.parseDouble(temp));
            textV.setText(temp);

            clearDigitFlag = true;
            equalFlag =true;


            digitFlag=false;
            oppFlag=false;

            totalFlag=true;
        }
        else if(!opButtonPressed.equals("=")) {//Sequence: digit,operation,digit,operation
            switch (firstButtonPressed) {

                case "+":
                    total = total + newNumber;
                    break;
                case "-":
                    total = total - newNumber;
                    break;
                case "*":
                    total = total * newNumber;
                    break;
                default:
                    total = total / newNumber;


            }
            oldNumber=total;

            String temp = "" + total;


            if (temp.length() >= 10)
                temp = nmbFormat.format(Double.parseDouble(temp));
            textV.setText(temp);

            clearDigitFlag = true;
            equalFlag =false;
            totalFlag=true;

        }
        else if(totalFlag)//Sequence: digit,operation,digit,equals
        {
            switch (firstButtonPressed) {

                case "+":
                    total = oldNumber + newNumber;
                    break;
                case "-":
                    total = oldNumber - newNumber;
                    break;
                case "*":
                    total = oldNumber * newNumber;
                    break;
                default:
                    total = oldNumber / newNumber;


            }
            oldNumber = total;

            String temp = "" + total;


            if (temp.length() >= 10)
                temp = nmbFormat.format(Double.parseDouble(temp));
            textV.setText(temp);

            clearDigitFlag = true;
            equalFlag =true;
            totalFlag=true;
        }


        digitFlag=false;
        oppFlag=false;


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calculator);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calculator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
