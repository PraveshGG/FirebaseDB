package com.example.firebasedb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Spinner genreSpinner;
    EditText etNameOfTheArtist;
    Button   btnAddArtist;
    DatabaseReference db;

    TextView tvArtistName;
    TextView tvArtistGenre;
    ListView lvArtists;

    List<Artist> listsArtists;
    ArtistListAdapter newArtistListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creating a database named artists
        db = FirebaseDatabase.getInstance().getReference("artists");

        etNameOfTheArtist = findViewById(R.id.name_of_artist);
        genreSpinner = findViewById(R.id.spinner_artists_dropdown);
        btnAddArtist = findViewById(R.id.btn_add_user);
        lvArtists = findViewById(R.id.lv_artists);
        listsArtists = new ArrayList<Artist>();

        ArrayAdapter <String> myArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,
                   getResources().getStringArray(R.array.genres) );
            //myArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_2);
            genreSpinner.setAdapter(myArrayAdapter);

            btnAddArtist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addArtist(v);
                }
            });



    }

    @Override
    protected void onStart() {
        super.onStart();

        //event listeners for the db

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //clear our list
                listsArtists.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren() ){
                    Artist artist =  postSnapshot.getValue(Artist.class);
                    listsArtists.add(artist);
                }


                //create adapter and attach it ou listview


                newArtistListAdapter = new ArtistListAdapter(MainActivity.this, listsArtists);
                lvArtists.setAdapter(newArtistListAdapter);

                lvArtists.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return false;
                    }
                });

                lvArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();

                        Artist a = listsArtists.get(position);
                        Intent n = new Intent(MainActivity.this, ArtistsActivity.class);
                        n.putExtra("artistId", a.getArtistId());
                        n.putExtra("artistName", a.getArtistName());
                        Toast.makeText(MainActivity.this, "name:" + a.getArtistName(), Toast.LENGTH_SHORT).show();
                        n.putExtra("artistGenre", a.getArtistGenre());
                        startActivity(n);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addArtist (View view){
        String name = etNameOfTheArtist.getText().toString().trim();
        String genre =  genreSpinner.getSelectedItem().toString();

        if(!TextUtils.isEmpty(name)){
            //runs only if name not empty


            String id = db.push().getKey();

            Artist  artist = new Artist(id, name, genre);

            db.child(id).setValue(artist);

            etNameOfTheArtist.setText("");
            Toast.makeText(this, "Artists added successfully. :)", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Please add a name first. :(", Toast.LENGTH_SHORT).show();

        }

    }




}
