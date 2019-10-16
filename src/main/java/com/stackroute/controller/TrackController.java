package com.stackroute.controller;

import com.stackroute.domain.Track;
import com.stackroute.exceptions.TrackNotFoundException;
import com.stackroute.trackservice.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class TrackController {
    TrackService trackService;

    @Autowired
    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @PostMapping("/tracks")
    public ResponseEntity<?> createTrack(@Valid @RequestBody Track track) {
        ResponseEntity responseEntity;
        try
        {
            trackService.saveTrack(track);
            responseEntity=new ResponseEntity<String>("successfully created", HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            responseEntity= new ResponseEntity<String>(e.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping("/tracks")
    public ResponseEntity<?> getTracks() {
        return new ResponseEntity<List<Track>>(trackService.getAllTracks(), HttpStatus.OK);
    }

    @GetMapping("/tracks/{trackId}")
    public ResponseEntity<?> getTrackById(@PathVariable(value = "trackId") int trackId) {
        try {
            return new ResponseEntity<Track>(trackService.getTrackById(trackId), HttpStatus.OK);
        }
        catch (TrackNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/tracks/{trackId}/{comment}")
    public ResponseEntity<?> updateTrack(@PathVariable(value = "trackId") int trackId,
                             @PathVariable(value = "comment") String comment) {
        try {
            return new ResponseEntity<Track>(trackService.updateTrack(trackId, comment), HttpStatus.OK);
        }
        catch(TrackNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/tracks/{trackId}")
    public ResponseEntity<?> deleteTrack(@PathVariable(value = "trackId") int trackId)
                                                    throws TrackNotFoundException {
        ResponseEntity responseEntity;
        try {
            boolean result = trackService.deleteTrack(trackId);
            responseEntity=new ResponseEntity<String>("successfully deleted", HttpStatus.OK);
        }
        catch(TrackNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        }

        return responseEntity;
    }

}
