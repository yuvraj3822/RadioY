package com.example.radioy;

import android.media.MediaPlayer;

/**
 * This is the Singleton class for retrofit
 */
public class CustomPlay extends MediaPlayer {
    /**
     * I have taken it protected because
     * even when stopping and releasing the media player
     * it still have the instacne so I took it protected to make
     * it null to make the instance completely release
     */
    protected static CustomPlay instance = null;

    public static CustomPlay getInstance()
    {
        if (instance == null)
            instance = new CustomPlay();

        return instance;
    }
}
