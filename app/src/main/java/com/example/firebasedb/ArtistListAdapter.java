package com.example.firebasedb;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ArtistListAdapter extends ArrayAdapter<Artist> {


    //variables
    private Activity context;
    List<Artist> artists;

    public ArtistListAdapter(Activity context, List<Artist> artists) {
        super(context, R.layout.layout_artist_custom_list_item,artists);
        this.context = context;
        this.artists = artists;
    }

    //constructor inherits from the super
    public View getView(int position, View convertView, ViewGroup parent){


        //instantiates the layout file from the custom list item into view objects

//        Artist user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view


//        LayoutInflater inflater = context.getLayoutInflater();
//
//        View listView = inflater.inflate(R.layout.layout_artist_custom_list_item,
//                null,
//                true);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_artist_custom_list_item, parent, false);
        }

        //TextView tvName = listView.findViewById(R.id.textView);
        TextView tvName = convertView.findViewById(R.id.textView);
        //TextView tvGenre = listView.findViewById(R.id.textView2);
        TextView tvGenre = convertView.findViewById(R.id.textView2);

        Artist artist = artists.get(position);

        tvName.setText(artist.getArtistName());
        tvGenre.setText(artist.getArtistGenre());

        return convertView;
    }




}
