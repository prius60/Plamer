package com.mer.plamer;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.mer.plamer.controller.PlayControl;
import com.mer.plamer.controller.PlaylistAdapter;
import com.mer.plamer.controller.PlaylistControl;
import com.mer.plamer.controller.UniversalPlaylistAdapter;
import com.mer.plamer.controller.UserControl;
import com.mer.plamer.usecases.PlayAction;
import com.mer.plamer.usecases.PlaylistLibraryAction;
import com.mer.plamer.usecases.UserLibraryAction;

import java.util.ArrayList;

/**
 * Activity to provide the view of a user's playlists
 */
public class UserPlaylistActivity extends AppCompatActivity {

    private ListView lv;
    private PlaylistAdapter plAdapter;
    private ArrayList<String> playListID;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist_layout);

        String name = getIntent().getStringExtra("selected_user_name");
        UserControl userControl = new UserControl();
        playListID = UserLibraryAction.getUserPlaylist(name);
        UniversalPlaylistAdapter universalplAdapter = new
                UniversalPlaylistAdapter(UserPlaylistActivity.this, playListID);
        lv = findViewById(R.id.playlist_list);

        // show tittle
        TextView tittle = findViewById(R.id.playlist_tittle);
        String t = name + "'s Playlists";
        tittle.setText(t);

        // show the list of all playlists
        lv.setAdapter(universalplAdapter);

        // click playlist to open it
        AdapterView.OnItemClickListener openList = (parent, view, position, l) -> {
            String id = playListID.get(position);
            Intent intent = new Intent(UserPlaylistActivity.this, OwnPlaylistActivity.class);
            intent.putExtra("play_list_id", id);
            startActivity(intent);
        };

        lv.setOnItemClickListener(openList);


        // back to the last page
        ImageButton back = findViewById(R.id.playlist_back_last_page);
        back.setOnClickListener(v -> finish());

        // create new playlist by pressing "+"
        View pop = getLayoutInflater().inflate(R.layout.new_playlist_popup_layout, null);

        // set the pop up window
        PopupWindow popupwindow = new PopupWindow(pop, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        ImageButton add = findViewById(R.id.playlist_add);
        ImageButton new_go = pop.findViewById(R.id.new_playlist_go);

        // show pop up window when click "+"
        add.setOnClickListener(v -> {
            popupwindow.showAsDropDown(add);
            // get the input of name
            EditText playlist_name = pop.findViewById(R.id.new_playlist_name);
            // confirm the name
            new_go.setOnClickListener(v1 -> {
                // add new playlist into library
                String pll_name = playlist_name.getText().toString();
                PlaylistControl playlistControl = new PlaylistControl();
                playlistControl.add(pll_name);
                ArrayList<String> lst = PlaylistLibraryAction.getListOfPlaylistId();
                int i = PlaylistLibraryAction.getListOfPlaylistId().size() - 1;
                playListID.add(lst.get(i));
                plAdapter.notifyDataSetChanged();
                Toast.makeText(UserPlaylistActivity.this,
                        "You have created a playlist.", Toast.LENGTH_LONG).show();
                playlist_name.setText("");
                popupwindow.dismiss();
            });

        });

        // play music
        ImageButton playing = findViewById(R.id.playlist_playing);

        playing.setOnClickListener(v -> {
            Intent intent = new Intent(UserPlaylistActivity.this,
                    PlayerActivity.class);
            startActivity(intent);
        });

        // play/pause music
        ImageButton playButton = findViewById(R.id.playlist_play);
        playButton.setOnClickListener(v -> {
            PlayControl.playPause();
            if (PlayAction.isPlaying()) {
                ((ImageButton)v).setImageResource(R.drawable.pause);
            } else{
                ((ImageButton) v).setImageResource(R.drawable.play);
            }
        });

        // change the loop style
        ImageButton repeatButton = findViewById(R.id.playlist_repeat_list);
        repeatButton.setOnClickListener(v -> PlayAction.loop());

        // previous music
        ImageButton prevButton = findViewById(R.id.playlist_prev);
        prevButton.setOnClickListener(v -> PlayControl.prev());

        // next music
        ImageButton nextButton = findViewById(R.id.playlist_next);
        nextButton.setOnClickListener(v -> PlayControl.next());

    }


}