package edu.washington.trosane.arewethereyet;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

public class MainActivity extends ActionBarActivity {
    Button startButton;
    TextView messageText;
    TextView phoneText;
    TextView timeText;
    static int time;
    static String toastMessage;
    EditText editTextMessage, editTextPhone, editTextTime;
    Intent start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.startButton);
        messageText = (TextView) findViewById(R.id.messageBox);
        phoneText = (TextView) findViewById(R.id.phoneNumberBox);
        timeText = (TextView) findViewById(R.id.timeBox);

        editTextMessage = (EditText) findViewById(R.id.message);
        editTextPhone = (EditText) findViewById(R.id.phone);
        editTextTime = (EditText) findViewById(R.id.time);

        start = new Intent(MainActivity.this, TimeService.class);

        startButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if (editTextTime.getText().toString().equals("")) {
                    time = 0;
                } else {
                    time = Integer.parseInt(editTextTime.getText().toString());
                }
                toastMessage = editTextPhone.getText().toString();
                if (startButton.getText().toString().equals("Start")) {
                    if (validateMessage(editTextMessage.getText().toString()) && validatePhoneNumber
                            (editTextPhone.getText().toString()) && validateTime(time)) {
                        startButton.setText("Stop");
                        startService(start);
                    }
                } else {
                    startButton.setText("Start");
                    stopService(start);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    // returns true if the EditText is not empty, false if it is
    public boolean validateMessage(String message) {
        if (editTextMessage.getText().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    // returns true if the phone number is valid, false if not
    public static boolean validatePhoneNumber(String phoneNumber) {
        if(phoneNumber.matches("\\d{10}"))
            return true;
        else if(phoneNumber.matches("\\d{3}[-\\s]\\d{3}[-\\s]\\d{4}"))
            return true;
        else
            return false;
    }

    // returns true if the time is greater than zero, false if not
    public static boolean validateTime(int time) {
        if (time > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static int getTime() {
        return time;
    }

    public static String getToast() {
        return toastMessage + ": Are we there yet?";
    }
}
