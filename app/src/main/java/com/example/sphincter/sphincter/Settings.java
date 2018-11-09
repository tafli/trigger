package com.example.sphincter.sphincter;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Settings extends Activity { //AppCompatActivity
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
*/
    // Form Animals Example
    private Context mContext;

    RelativeLayout mRelativeLayout;
    private RecyclerView mRecyclerView;
    private Button mButtonAdd;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Random mRandom = new Random();

    // Form Animals Example
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Request window feature action bar
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings); //activity_main

        // Get the application context
        mContext = getApplicationContext();

        // Change the action bar color
        //getSupportActionBar().setBackgroundDrawable(
        //        new ColorDrawable(Color.parseColor("#FFFF00BF"))
        //);

        // Get the widgets reference from XML layout
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rl);
        mButtonAdd = (Button) findViewById(R.id.btn_add);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        if (findViewById(R.id.recycler_view) == null) {
            System.out.println("recycler_view is null!");
        }

        // Initialize a new String array
        final String[] animals = {
                "Aardvark",
                "Albatross",
                "Alligator",
                "Alpaca",
                "Ant",
                "Anteater",
                "Antelope",
                "Ape",
                "Armadillo",
                "Donkey",
                "Baboon",
                "Badger",
                "Barracuda",
                "Bear",
                "Beaver",
                "Bee"
        };

        // Intilize an array list from array
        final List<String> animalsList = new ArrayList(Arrays.asList(animals));

        // Define a layout for RecyclerView
        mLayoutManager = new GridLayoutManager(mContext,3);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Initialize a new instance of RecyclerView Adapter instance
        mAdapter = new AnimalsAdapter(mContext,animalsList);

        // Set the adapter for RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        // Set a click listener for add item button
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Specify the position
                int position = 0;
                String itemLabel = "" + mRandom.nextInt(100);

                // Add an item to animals list
                animalsList.add(position,"" + itemLabel);

                /*
                    public final void notifyItemInserted (int position)
                        Notify any registered observers that the item reflected at position has been
                        newly inserted. The item previously at position is now at position position + 1.

                        This is a structural change event. Representations of other existing items
                        in the data set are still considered up to date and will not be rebound,
                        though their positions may be altered.

                    Parameters
                    position : Position of the newly inserted item in the data set

                */

                // Notify the adapter that an item inserted
                mAdapter.notifyItemInserted(position);

                /*
                    public void scrollToPosition (int position)
                        Convenience method to scroll to a certain position. RecyclerView does not
                        implement scrolling logic, rather forwards the call to scrollToPosition(int)

                    Parameters
                    position : Scroll to this adapter position

                */
                // Scroll to newly added item position
                mRecyclerView.scrollToPosition(position);

                // Show the added item label
                Toast.makeText(mContext,"Added : " + itemLabel,Toast.LENGTH_SHORT).show();
            }
        });
    }

}
