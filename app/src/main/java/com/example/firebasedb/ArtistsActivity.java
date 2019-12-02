package com.example.firebasedb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ArtistsActivity extends AppCompatActivity {

    TextView tvArtistName, tvRatingDisplay;
    EditText etTrackName;
    RatingBar ratingBar;
    Button  btnAddTracks;
    ListView listView;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists);



        tvArtistName = findViewById(R.id.textViewArtist);
        tvRatingDisplay = findViewById(R.id.textViewRating);
        etTrackName = findViewById(R.id.editTextName);
        ratingBar = findViewById(R.id.ratingBar);
        btnAddTracks = findViewById(R.id.buttonAddTrack);

        Bundle extras = getIntent().getExtras();
        String artistID = extras.getString("artistId");
        String artistName = extras.getString("artistName");
        String artistGenre = extras.getString("artistGenre");

        //
        db = FirebaseDatabase.getInstance().getReference("tracks").child(extras.getString("artistId"));


        if (extras != null) {
            tvArtistName.setText(extras.getString("artistName"));
        }

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                tvRatingDisplay.setText(String.valueOf(rating));
            }
        });

        btnAddTracks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code will run when user clisks the button
                saveTrack();
            }
        });

    }

    private void saveTrack(){
        //this method will save the track oto the database for that specific artist
         int rating = ratingBar.getProgress();

         if(!TextUtils.isEmpty(etTrackName.getText().toString().trim())){
             String id = db.push().getKey();

             Track track = new Track(id, etTrackName.getText().toString(), rating);

             db.child(id).setValue(track);

         }else{
             //if the et box is empty

         }
    }
}
