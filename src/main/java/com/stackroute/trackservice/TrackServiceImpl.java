package com.stackroute.trackservice;

import com.stackroute.domain.Track;
import com.stackroute.exceptions.TrackAlreadyExistsException;
import com.stackroute.exceptions.TrackNotFoundException;
import com.stackroute.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackServiceImpl implements TrackService {

    TrackRepository trackRepository;

    public TrackServiceImpl() {

    }

    @Autowired
    public TrackServiceImpl(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    public Track saveTrack(Track track) throws TrackAlreadyExistsException {
        if(trackRepository.existsById(track.getTrackId())) {
            throw new TrackAlreadyExistsException("Track Already Exists!");
        }
        Track savedTrack = trackRepository.save(track);
        if(savedTrack == null) {
            throw new TrackAlreadyExistsException("Track Already Exists!");
        }

        return savedTrack;
    }

    @Override
    public Track getTrackById(int trackId) throws TrackNotFoundException {
        Track track = null;
        if(!trackRepository.existsById(trackId)) {
            throw new TrackNotFoundException("Track Not Found!");
        }
        track = trackRepository.findById(trackId).orElse(null);
        return track;
    }

    @Override
    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    @Override
    public Track updateTrack(int trackId, String comment) throws TrackNotFoundException {
        Track updateTrack = null;
        if(!trackRepository.existsById(trackId))  {
            throw new TrackNotFoundException("Track Not Found!");
        }
        updateTrack = getTrackById(trackId);
        updateTrack.setComment(comment);

        return updateTrack;
    }

    @Override
    public boolean deleteTrack(int trackId) throws TrackNotFoundException {
        boolean retVal = false;
        if(!trackRepository.existsById(trackId)) {
            throw new TrackNotFoundException("Track Not Found!");
        }
        trackRepository.delete(getTrackById(trackId));
        retVal = true;

        return retVal;
    }

}
