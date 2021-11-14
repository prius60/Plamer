package com.mer.plamer.usecases;

import android.os.Environment;

import com.mer.plamer.entities.Track;
import com.mer.plamer.entities.TrackLibrary;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TrackLibraryAction implements LibraryAction<Track>{

    public static TrackLibrary trackLibrary = new TrackLibrary();

    /**
     * Delete the track in the track library.
     * @param track_id the id of the playlist.
     * @return Whether the track is successfully removed or not.
     */
    public boolean delete(String track_id) {
        return trackLibrary.remove(track_id);
    }

    /**
     * Search the required track.
     * @param track_id the id of the required track.
     * @return the required track.
     */
    public Track search(String track_id) {
        return trackLibrary.contain(track_id);
    }


    /**
     * add a track to the track library.
     * @param track the track we want to add.
     */
    public void add(Track track) {
        trackLibrary.add(track);
    }

    /**
     * scan the local file to add every track exists when the app restarts.
     */
    public static void scanLocal() {
        File musicFolder = new File(Environment.getExternalStorageDirectory(), "Music");
        File[] files = musicFolder.listFiles();

        for(int i = 0; i < Objects.requireNonNull(files).length; i++){
            if(files[i].getName().contains(".mp3")){
                trackLibrary.add(new Track(files[i].getAbsolutePath()));
            }
        }

    }
}
