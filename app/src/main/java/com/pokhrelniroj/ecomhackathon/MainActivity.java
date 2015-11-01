package com.pokhrelniroj.ecomhackathon;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class MainActivity extends ActivityWithNavigation {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private static RecycleViewAdapterForMain mAdapter;
    private static ArrayList<String> mListOfItems;
    private static Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);



        //Recycle view used !!!!
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mListOfItems = new ArrayList<String>();
        RecycleViewAdapterForMain recycleAdapterForMain = new RecycleViewAdapterForMain(mListOfItems);
        mAdapter = recycleAdapterForMain;
        mRecyclerView.setAdapter(mAdapter);


        mActivity = this;
        //Floating action bar

        //One thing can be
        FloatingActionButton floatingBtn = (FloatingActionButton) findViewById(R.id.fab);
        floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new CategoryPickerDialog();
                newFragment.show(getFragmentManager(), "selectCategory");

            }
        });
        //
        beaconManager = new BeaconManager(this);
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                Log.d("niroj", "onBeaconsDiscovered and size = " + list.size() );
                if (!list.isEmpty()) {
                    Beacon nearestBeacon = list.get(0);
                    //List<String> places = placesNearBeacon(nearestBeacon);
                    // TODO: update the UI here
                    //Log.d("niroj", "Nearest places: " + places);
                }
            }
        });

        region = new Region("ranged region", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null);
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


    public static class CategoryPickerDialog extends DialogFragment implements DialogInterface.OnCancelListener{
        private static Spinner spinner;
        private static ArrayAdapter<CharSequence> adapter;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            LayoutInflater li = LayoutInflater.from(mActivity);
            View convertView = li.inflate(
                    R.layout.layout_pick_category, null);
            spinner = (Spinner) convertView.findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
            adapter = ArrayAdapter.createFromResource(mActivity,
                    R.array.category_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
            spinner.setAdapter(adapter);
            builder.setView(convertView);
            builder.setTitle("Pick Category");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int position = spinner.getSelectedItemPosition();
                    if( position >= 1 ) {
                        CharSequence charSeq = adapter.getItem(position);
                        mListOfItems.add(charSeq.toString());
                        //!!! Check for the repetition of same areas here.
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });

            return builder.create();
        }


    }


    // TODO: replace "<major>:<minor>" strings to match your own beacons.
  /*  private static final Map<String, List<String>> PLACES_BY_BEACONS;

    static {
        Map<String, List<String>> placesByBeacons = new HashMap<>();
        placesByBeacons.put("22504:48827", new ArrayList<String>() {{
            add("Shoes on Sales");
            // read as: "Heavenly Sandwiches" is closest
            // to the beacon with major 22504 and minor 48827
            add("Bags on Sales");
            // "Green & Green Salads" is the next closest
            add("T-shirts on Sales");
            // "Mini Panini" is the furthest away
        }});
        placesByBeacons.put("648:12", new ArrayList<String>() {{
            add("Wines");
            add("Beer");
            add("Water");
        }});
        PLACES_BY_BEACONS = Collections.unmodifiableMap(placesByBeacons);
    }

    private List<String> placesNearBeacon(Beacon beacon) {
        String beaconKey = String.format("%d:%d", beacon.getMajor(), beacon.getMinor());
        Log.d("niroj", " Major = "+beacon.getMajor()+ " Minor = " + beacon.getMinor());
        Log.d("niroj", "Beacon = " +beacon.getProximityUUID());
*//*        if (PLACES_BY_BEACONS.containsKey(beaconKey)) {
            return PLACES_BY_BEACONS.get(beaconKey);
        }*//*
        return Collections.emptyList();
    }*/

    private BeaconManager beaconManager;
    private Region region;

    @Override
    protected void onResume() {
        super.onResume();
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                Log.d("niroj", " onServiceReady");
                beaconManager.startRanging(region);
            }
        });
    }

    @Override
    protected void onPause() {
        beaconManager.stopRanging(region);
        super.onPause();
    }
}
