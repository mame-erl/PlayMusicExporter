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

package de.arcus.playmusiclib.items;

import de.arcus.playmusiclib.PlayMusicManager;
import de.arcus.playmusiclib.datasources.MusicTrackDataSource;

/**
 * An artist
 */
public class Artist extends MusicTrackList {
    // Variables
    private long mArtistId;
    private String mArtist;

    /**
     * Creates a data item
     * @param playMusicManager The manager
     */
    public Artist(PlayMusicManager playMusicManager) {
        super(playMusicManager);
    }

    /**
     * @return Gets the id of the artist
     */
    public long getArtistId() {
        return mArtistId;
    }

    /**
     * @param artistId Sets the id of the artist
     */
    public void setArtistId(long artistId) {
        mArtistId = artistId;
    }

    /**
     * @return Gets the name of the artist
     */
    public String getArtist() {
        return mArtist;
    }

    /**
     * @param artist Sets the name of the artist
     */
    public void setArtist(String artist) {
        mArtist = artist;
    }

    @Override
    /**
     * Gets the artist name
     */
    public String getTitle() {
        return getArtist();
    }

    @Override
    /**
     * Gets the artist name
     */
    public String getDescription() {
        return ""; // TODO
    }

    @Override
    /**
     * Gets the id of the track list
     */
    public long getMusicTrackListID() {
        return getArtistId();
    }

    /**
     * @return Artists tracks should show the album artwork
     */
    public boolean getShowArtworkInTrack() {
        return true;
    }

    @Override
    /**
     * Loads all tracks from this artist
     */
    protected void fetchTrackList() {
        // Music track data source
        MusicTrackDataSource musicTrackDataSource = new MusicTrackDataSource(mPlayMusicManager);

        // Load the track list
        mMusicTrackList = musicTrackDataSource.getByArtist(this);
    }
}
