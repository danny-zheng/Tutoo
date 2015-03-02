package com.parse.tutoo.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.parse.tutoo.R;
import com.parse.tutoo.util.Dispatcher;

/**
 * Created by hilary on 25/02/2015.
 */
public class ViewPostActivity extends ActionBarActivity {

    private Dispatcher dispatcher = new Dispatcher();


    public void addListenerSelectTutor(View button) {
        final RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        Button thisTutorButton = (Button) findViewById(R.id.button1);



        thisTutorButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                RadioButton radioSelected = (RadioButton) findViewById(selectedId);

                radioSelected.setText("selected");
                /*
                new AlertDialog.Builder(this)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
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
                        .show();*/
            }

        });

    }

    public void addListenerReply() {
        Button thisTutorButton = (Button) findViewById(R.id.button1);

        thisTutorButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText replyET = (EditText)findViewById(R.id.editText);
                String replyMessage = replyET.getText().toString();
                // TODO: Save this message
            }

        });

    }


    public void addListenerEdit() {
        Button thisTutorButton = (Button) findViewById(R.id.button3);

        thisTutorButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO: Go to new post view
            }

        });

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        TextView textView = (TextView) findViewById(R.id.viewpost1);

        // TODO: Post Id stuff eh
        Intent intent = getIntent();
        //textView.setText(intent.getStringExtra("post_id"));

        TextView titleTV = (TextView)findViewById(R.id.textView1);

        TextView textTV = (TextView)findViewById(R.id.textView2);

        titleTV.setText("Looking for Help");
        titleTV.setTextSize(20);

        textTV.setText("Hi I am looking for Help with my interviews!!! Hi I am looking for Help with my interviews!!! Hi I am looking for Help with my interviews!!! Hi I am looking for Help with my interviews!!! Hi I am looking for Help with my interviews!!! Hi I am looking for Help with my interviews!!!");
        textTV.setTextSize(20);

        // Replace this with number of skills later
        int size = 3; // total number of TextViews to add



        //TODO: check if current user is the owner of the post, if yes display radio buttons
        boolean owner = false;


        // Hiding stuff according to user role
        if (owner) {
            LinearLayout replyLL = (LinearLayout) findViewById(R.id.linearLayout3);
            replyLL.setVisibility(View.GONE);
            //addListenerSelectTutor();
            addListenerEdit();
        } else {
            Button thisTutorB = (Button) findViewById(R.id.button1);
            thisTutorB.setVisibility(View.GONE);
            Button editB = (Button) findViewById(R.id.button3);
            editB.setVisibility(View.GONE);
            addListenerReply();
        }


        if (owner) {
            RadioButton[] tv = new RadioButton[size];
            RadioButton temp;

            for (int i = 0; i < size; i++)
            {
                temp = new RadioButton(this);
                // Replace this with actual skills later
                temp.setText("Tutor " + i);
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
                radioGroup.addView(temp);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(50,10,20,10);
                temp.setTextSize(20);
                temp.setLayoutParams(params);
                //temp.setBackgroundColor(Color.parseColor("CCFFCC"));
                tv[i] = temp;
            }
        } else {
            TextView[] tv = new TextView[size];
            TextView tempTV;

            LinearLayout[] ll = new LinearLayout[size];
            LinearLayout tempLL;
            boolean userOwnsThisReply = false;
            for (int i = 0; i < size; i++) {
                tempLL = new LinearLayout(this);
                tempLL.setOrientation(LinearLayout.HORIZONTAL);

                tempTV = new TextView(this);
                // TODO: Replace this with actual skills later
                tempTV.setText("Tutor " + i);
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout2);
                tempLL.addView(tempTV);
                linearLayout.addView(tempLL);
                if (i == 0) {
                    userOwnsThisReply = true;
                } else {
                    userOwnsThisReply = false;
                }
                if (userOwnsThisReply) {
                    Button editReplyB = new Button(this);
                    editReplyB.setText("Edit Reply");
                    tempLL.addView(editReplyB);

                }


                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(80, 10, 20, 10);
                tempTV.setTextSize(20);
                tempTV.setLayoutParams(params);
                //TODO: Color? temp.setBackgroundColor(Color.parseColor("CCFFCC"));
                tv[i] = tempTV;
            }

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_posts, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_newpost:
                dispatcher.openNewPost(getApplicationContext(), this);
                return true;
            case R.id.action_search:
                dispatcher.openSearch(getApplicationContext(),this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
