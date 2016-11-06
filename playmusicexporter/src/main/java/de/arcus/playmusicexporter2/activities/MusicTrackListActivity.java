/*
 * Copyright (c) 2015 David Schulte
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package de.arcus.playmusicexporter2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import de.arcus.framework.crashhandler.CrashHandler;
import de.arcus.playmusicexporter2.R;
import de.arcus.playmusicexporter2.fragments.MusicTrackListFragment;
import de.arcus.playmusicexporter2.items.SelectedTrackList;
import de.arcus.playmusiclib.PlayMusicManager;
import de.arcus.playmusiclib.items.MusicTrackList;


/**
 * An activity representing a single Track detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link MusicContainerListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link MusicTrackListFragment}.
 */
public class MusicTrackListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_detail);

        // Adds the crash handler to this class
        CrashHandler.addCrashHandler(this);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();

            // Loads the track list
            long id = getIntent().getLongExtra(MusicTrackListFragment.ARG_MUSIC_TRACK_LIST_ID, 0);
            String type = getIntent().getStringExtra(MusicTrackListFragment.ARG_MUSIC_TRACK_LIST_TYPE);


            PlayMusicManager playMusicManager = PlayMusicManager.getInstance();
            if (playMusicManager != null) {
                MusicTrackList musicTrackList = MusicTrackList.deserialize(playMusicManager, id, type);

                if (musicTrackList != null) {
                    // Sets the title
                    setTitle(musicTrackList.getTitle());
                }
            }

            // Puts the track list information to the fragment
            arguments.putLong(MusicTrackListFragment.ARG_MUSIC_TRACK_LIST_ID, id);
            arguments.putString(MusicTrackListFragment.ARG_MUSIC_TRACK_LIST_TYPE, type);


            // Loads the fragment
            MusicTrackListFragment fragment = new MusicTrackListFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.track_detail_container, fragment)
                    .commit();
        }

        // Setup the selection list for this activity
        SelectedTrackList.getInstance().setupActionMode(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, MusicContainerListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Update all view lists
     */
    public void updateLists() {
        // Gets the music list fragment
        MusicTrackListFragment musicTrackDetailFragment = (MusicTrackListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.track_detail_container);

        if (musicTrackDetailFragment != null)
            musicTrackDetailFragment.updateListView();
    }

    /**
     * Select all items
     */
    public void selectAll() {
        // Gets the music list fragment
        MusicTrackListFragment musicTrackDetailFragment = (MusicTrackListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.track_detail_container);

        if (musicTrackDetailFragment != null)
            musicTrackDetailFragment.selectAll();
    }

    /**
     * Deselect all items
     */
    public void deselectAll() {
        // Gets the music list fragment
        MusicTrackListFragment musicTrackDetailFragment = (MusicTrackListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.track_detail_container);

        if (musicTrackDetailFragment != null)
            musicTrackDetailFragment.deselectAll();
    }
}
