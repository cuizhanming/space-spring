package com.example.springffmpeg.controller;

import com.example.springffmpeg.service.StreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/stream")
@CrossOrigin(origins = "*")
public class StreamController {

    @Autowired
    private StreamService streamService;

    @PostMapping("/start")
    public ResponseEntity<?> startStream(@RequestBody StreamRequest request) {
        boolean success = streamService.startStream(request.getVideoFilename(), request.getStreamKey());
        if (success) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Stream started");
            response.put("flvUrl", streamService.getPlayUrl(request.getStreamKey(), "flv"));
            response.put("hlsUrl", streamService.getPlayUrl(request.getStreamKey(), "hls"));
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body("Failed to start stream");
        }
    }

    @PostMapping("/stop")
    public ResponseEntity<?> stopStream(@RequestBody StreamRequest request) {
        boolean success = streamService.stopStream(request.getStreamKey());
        if (success) {
            return ResponseEntity.ok("Stream stopped");
        } else {
            return ResponseEntity.badRequest().body("Stream not found or failed to stop");
        }
    }

    public static class StreamRequest {
        private String videoFilename;
        private String streamKey;

        public String getVideoFilename() {
            return videoFilename;
        }

        public void setVideoFilename(String videoFilename) {
            this.videoFilename = videoFilename;
        }

        public String getStreamKey() {
            return streamKey;
        }

        public void setStreamKey(String streamKey) {
            this.streamKey = streamKey;
        }
    }
}
