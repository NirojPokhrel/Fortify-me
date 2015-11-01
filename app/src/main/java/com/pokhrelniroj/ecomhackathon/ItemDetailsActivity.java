package com.pokhrelniroj.ecomhackathon;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Iterator;

/**
 * Created by Niroj Pokhrel on 10/31/2015.
 */
public class ItemDetailsActivity extends Activity {
    public static TextView mTvUpdate;
    private SphereService sphereService;

    @Override
    public void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);

        mTvUpdate = (TextView) findViewById(R.id.textDesc);
        Button btn = (Button) findViewById(R.id.itemDetailsBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Run the async task to get the data
                Log.d("niroj", "onClick event for getting information about the products");
                new GetProducts().execute();
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        final Intent intent = new Intent(this, SphereService.class);
        Log.d("niroj", " before bind service");
        bindService(intent, sphereServiceConnection, Context.BIND_AUTO_CREATE);
        Log.d("niroj", " after bind service");
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(sphereServiceConnection);
    }

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private ServiceConnection sphereServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            final SphereServiceBinder binder = (SphereServiceBinder) service;
            sphereService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
        }
    };

    private class GetProducts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }



        @Override
        protected Void doInBackground(Void... arg0) {
            Log.d("niroj", "doInBackground");
            final SphereRequest productRequest = SphereRequest.get("/product-projections/").where("slug(en=\"dell-inspiron-3148-1446367680115\")");
            sphereService.executeJacksonRequest(productRequest,
                    new Response.Listener<JsonNode>() {
                        @Override
                        public void onResponse(JsonNode response) {
                            processJson(response);
                            Log.d("niroj", "Just before processJson");
                            //setAdapter();
                        }
                    });
            return null;
        }

        private void processJson(JsonNode queryResult) {
            if (queryResult != null) {
                final Iterator<JsonNode> results = queryResult.get("results").elements();
                while (results.hasNext()) { //TODO: No need of the while loop if we are just querying single elements
                    JsonNode next = results.next();

                    String name = next.get("name").get("en").textValue();
                    String str = null;

                    final Iterator<JsonNode> lineItems = next.get("masterVariant").get("prices").elements();
                    while (lineItems.hasNext()) {
                        JsonNode curr = lineItems.next();
                        String currencyCode = curr.get("value").get("currencyCode").textValue();
                        int centAmt = curr.get("value").get("centAmount").asInt(0);
                        //Log.d("niroj", "name = " + name + " currencyCode = " + curr.get("value").get("currencyCode") + " centAmount = " + curr.get("value").get("centAmount").asInt(0));
                        Log.d("niroj", "name = " + name + " currencyCode = " + currencyCode + " centAmount = " + centAmt );
                        str = "Name: " + name +"\n" +"currecnyCode = " + currencyCode + "\n"+"centAmount = " + centAmt;
                        break; // TODO: we are doing for a single element now. Is it necessary to loop ? Check the apis !!!
                    }


                    final Iterator<JsonNode> attributes = next.get("masterVariant").get("attributes").elements();
                    while(attributes.hasNext()) {
                        JsonNode attr = attributes.next();
                        String strName = attr.get("name").textValue();
                        String strReview = attr.get("value").textValue();
                        Log.d("niroj", strName + " = " + strReview );
                        str += "\n\n" + "Review:" + "\n" + strReview;
                        break;
                    }
                    mTvUpdate.setText(str);
                }
            } else {
                Log.d(this.getClass().getSimpleName(), "Couldn't get any data from the url");
            }
        }
    }

}
