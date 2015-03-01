package com.parse.tutoo.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.parse.tutoo.R;


public class NewPostActivity extends Activity {

    private String title;
    private String description;
    private String feedbackType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_post);
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


    public void createPost(View button) {
        // Do click handling here
        finish();
        /*
        final EditText nameField = (EditText) findViewById(R.id.inputSearchEditText);
        title = nameField.getText().toString();

        final EditText emailField = (EditText) findViewById(R.id.inputTitle);
        description = emailField.getText().toString();

        //final Spinner feedbackSpinner = (Spinner) findViewById(R.id.SpinnerFeedbackType);
        //feedbackType = feedbackSpinner.getSelectedItem().toString();

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(description)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
         */
    }
}
