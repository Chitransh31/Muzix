package com.stackroute.trackservice;

import com.stackroute.domain.Track;
import com.stackroute.exceptions.TrackAlreadyExistsException;
import com.stackroute.exceptions.TrackNotFoundException;

import java.util.List;

public interface TrackService {

    public Track saveTrack(Track track) throws TrackAlreadyExistsException;

    public Track getTrackById(int trackId) throws TrackNotFoundException;

    public List<Track> getAllTracks();

    public Track updateTrack(int trackId, String comment) throws TrackNotFoundException;

    public boolean deleteTrack(int trackId) throws TrackNotFoundException;

}
