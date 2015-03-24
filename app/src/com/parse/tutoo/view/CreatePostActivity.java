package com.parse.tutoo.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.tutoo.R;
import com.parse.tutoo.model.Category;
import com.parse.tutoo.model.Market;
import com.parse.tutoo.model.MarketPost;
import com.parse.tutoo.model.Post;
import com.parse.tutoo.model.State;
import com.parse.tutoo.util.Dispatcher;
import com.parse.tutoo.view.fragment.DatePickerFragment;
import com.parse.tutoo.view.fragment.TimePickerFragment;

import java.util.Calendar;

import java.util.ArrayList;
import java.util.List;


public class CreatePostActivity extends ActionBarActivity {

    private String title;
    private String description;
    private String feedbackType;
    private String skillsets;
    private Context context;
    private Location location;
    private LocationManager manager;
    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = getApplicationContext();
        manager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE );
        setContentView(R.layout.activity_new_post);
        setCurrentDateOnView();
		setupUI();
    }


    private void setDateOnButton(Button b, int year, int month, int day) {
        b.setText(new StringBuilder()
                .append(year).append("-")
                // Month is 0 based, just add 1
                .append(month + 1).append("-")
                .append(day).append(" "));
        b.setTextAppearance(this, android.R.attr.textAppearanceSmall );
    }

    private void setTimeOnButton(Button b, int hour, int minute) {
        b.setText( new StringBuilder().append(hour)
                .append(":").append(minute));
        b.setTextAppearance(this, android.R.attr.textAppearanceSmall );
    }

    private void setCurrentDateOnView() {
        Button datepicker1Display = (Button) findViewById(R.id.datepicker1);
        Button timepicker1Display = (Button) findViewById(R.id.timepicker1);

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // set current date time into textview
        setDateOnButton(datepicker1Display, year, month, day);
        setTimeOnButton(timepicker1Display, hour, minute);
    }

    private void setSubSpinner(Class<?> cls, Spinner spinner) {
        List<String> list = new ArrayList<>();
        if (cls == Category.class) {
            for (Category element : Category.values()) {
                if (element == Category.All) {
                    continue;
                }
                list.add(element.toString());
            }
        }
        else if (cls == Market.class) {
            for (Market element: Market.values()) {
                if (element == Market.All) {
                    continue;
                }
                list.add(element.toString());
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }
    private void setupUI() {
        final Spinner feedbackSpinner = (Spinner) findViewById(R.id.SpinnerFeedbackType);
        final Spinner feedbackSubSpinner = (Spinner) findViewById(R.id.SpinnerFeedbackSubType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.newPostSpinnerList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        feedbackSpinner.setAdapter(adapter);
        System.out.println("onCreate setupUI");
        feedbackSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("position=" + parent.getItemAtPosition(position).toString());
                if (parent.getItemAtPosition(position).toString().equals(getString(R.string.spinnerItem1Services))) {
                    // refresh list, service list
                    // setcategory list
                    EditText editText = (EditText)findViewById(R.id.skillsets);
                    editText.setHint("  Skillsets");
                    System.out.println("++++");
                    setSubSpinner(Category.class, feedbackSubSpinner);

                } else if (parent.getItemAtPosition(position).toString().equals(getString(R.string.spinnerItem2Markets))) {
                    // refresh list
                    EditText editText = (EditText)findViewById(R.id.skillsets);
                    editText.setHint("  Tags");
                    System.out.println("====");
                    setSubSpinner(Market.class, feedbackSubSpinner);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        switch (id) {
            case R.id.action_newpost:
                Dispatcher.openNewPost(getApplicationContext(), this);
                return true;
            case R.id.action_search:
                Dispatcher.openSearch(getApplicationContext(),this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void createPost(View button) {
        // Do click handling here
        finish();

        final EditText nameField = (EditText) findViewById(R.id.inputSearchEditText);
        title = nameField.getText().toString();

        final EditText emailField = (EditText) findViewById(R.id.inputTitle);
        description = emailField.getText().toString();

        // skillsets or tags
        final EditText skillSets = (EditText) findViewById(R.id.skillsets);
        skillsets = skillSets.getText().toString();

        if (manager.isProviderEnabled( LocationManager.GPS_PROVIDER )) {
            location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        final Spinner feedbackSpinner = (Spinner) findViewById(R.id.SpinnerFeedbackType);
        final Spinner feedbackSubSpinner = (Spinner) findViewById(R.id.SpinnerFeedbackSubType);
        if (feedbackSpinner.getSelectedItem().toString().equals(getString(R.string.spinnerItem1Services))) {
            String category = feedbackSubSpinner.getSelectedItem().toString();
            Category enumCategory = Category.valueOf(category);

            final Post userPost = new Post();
            userPost.setUser(ParseUser.getCurrentUser().getObjectId());
            userPost.setTitle(title);
            userPost.setDescription(description);
            userPost.setCategory(enumCategory);
            userPost.setSkills(skillsets);
            userPost.setGeoPoints(location);

            userPost.saveInBackground(new SaveCallback() {
                public void done(ParseException e) {
                    if (e != null) {
                        System.out.println(e.getMessage());
                    }
                }
            });
        } else if (feedbackSpinner.getSelectedItem().toString().equals(getString(R.string.spinnerItem2Markets))) {
            String market = feedbackSubSpinner.getSelectedItem().toString();
            Market enumMarket = Market.valueOf(market);

            final MarketPost userPost = new MarketPost();

            userPost.setUser(ParseUser.getCurrentUser().getObjectId());
            userPost.setTitle(title);
            userPost.setDescription(description);
            userPost.setMarket(enumMarket);
            //userPost.setTags(tags);
            userPost.setGeoPoints(location);

            userPost.saveInBackground(new SaveCallback() {
                public void done(ParseException e) {
                    if (e != null) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        });
    }

    public void showDatePickerDialog(final View v) {
        DatePickerDialog.OnDateSetListener dpListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                setDateOnButton((Button) v,year,monthOfYear,dayOfMonth);
            }
        };
        DatePickerFragment dpFragment = DatePickerFragment.newInstance(dpListener);
        dpFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(final View v) {
        TimePickerDialog.OnTimeSetListener tpListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                setTimeOnButton((Button) v, hourOfDay, minute);
            }
        };
        TimePickerFragment tpFragment = TimePickerFragment.newInstance(tpListener);
        tpFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // get user location
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);;
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // show alert
            showSettingsAlert();
        }
        location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (checked) {
        // if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {}

                public void onStatusChanged(String provider, int status, Bundle extras) {}

                public void onProviderEnabled(String provider) {
                    location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    System.out.println("Location is " + location.getAltitude());
                }

                public void onProviderDisabled(String provider) {

                }

            };
        } else {
            // store location
            System.out.println("Location=" + location);
            //System.out.println("Location=" + location.getAltitude());
            //location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
    }

    public void showSettingsAlert(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void attachPicture(View button) {
        // Do click handling here
        finish();

        final EditText nameField = (EditText) findViewById(R.id.inputSearchEditText);
        title = nameField.getText().toString();

        final EditText emailField = (EditText) findViewById(R.id.inputTitle);
        description = emailField.getText().toString();

        final EditText skillSets = (EditText) findViewById(R.id.skillsets);
        skillsets = skillSets.getText().toString();

        if (manager.isProviderEnabled( LocationManager.GPS_PROVIDER )) {
            location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        final Spinner feedbackSpinner = (Spinner) findViewById(R.id.SpinnerFeedbackType);
        String category = feedbackSpinner.getSelectedItem().toString();
        Category enumCategory = Category.valueOf(category);

        final Post userPost = new Post();
        userPost.setUser(ParseUser.getCurrentUser().getObjectId());
        userPost.setTitle(title);
        userPost.setDescription(description);
        userPost.setCategory(enumCategory);
        userPost.setSkills(skillsets);
        userPost.setGeoPoints(location);

        userPost.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e != null) {
                    System.out.println(e.getMessage());
                }
            }
        });

    }
}
