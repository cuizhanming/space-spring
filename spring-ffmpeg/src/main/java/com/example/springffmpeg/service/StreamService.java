package com.example.springffmpeg.service;

import com.example.springffmpeg.config.StreamConfig;
import org.apache.commons.exec.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class StreamService {

    private static final Logger log = LoggerFactory.getLogger(StreamService.class);

    @Autowired
    private StreamConfig streamConfig;

    // Store streaming processes
    private final Map<String, DefaultExecutor> streamProcesses = new ConcurrentHashMap<>();

    // Manual stop flags
    private final Map<String, Boolean> manualStopFlags = new ConcurrentHashMap<>();

    /**
     * Start streaming
     */
    public boolean startStream(String videoFilename, String streamKey) {
        try {
            // Check if video file exists on host
            String hostVideoPath = streamConfig.getVideoPath() + videoFilename;
            File videoFile = new File(hostVideoPath);
            if (!videoFile.exists()) {
                log.error("Video file does not exist: {}", hostVideoPath);
                return false;
            }

            // Construct RTMP URL
            // Use host.docker.internal for Mac/Windows Docker to access host from container
            String dockerHost = "host.docker.internal";
            String rtmpUrl = String.format("rtmp://%s:%d/live/%s",
                    dockerHost, streamConfig.getRtmpPort(), streamKey);

            // Construct Docker Command
            CommandLine cmdLine = getDockerCommandLine(videoFilename, rtmpUrl);

            // Create Executor
            DefaultExecutor executor = new DefaultExecutor();
            executor.setExitValue(0);

            // Watchdog for process management
            ExecuteWatchdog watchdog = new ExecuteWatchdog(ExecuteWatchdog.INFINITE_TIMEOUT);
            executor.setWatchdog(watchdog);

            // Output stream handler
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
            executor.setStreamHandler(streamHandler);

            // Execute asynchronously
            executor.execute(cmdLine, new ExecuteResultHandler() {
                @Override
                public void onProcessComplete(int exitValue) {
                    log.info("Streaming complete, streamKey: {}, exitValue: {}", streamKey, exitValue);
                    streamProcesses.remove(streamKey);
                }

                @Override
                public void onProcessFailed(ExecuteException e) {
                    Boolean wasManualStop = manualStopFlags.remove(streamKey);
                    boolean isManualStop = wasManualStop != null && wasManualStop;
                    if (isManualStop) {
                        log.info("Streaming stopped manually, streamKey: {}", streamKey);
                    } else {
                        log.error("Streaming failed, streamKey: {}, error: {}", streamKey, e.getMessage());
                        log.error("FFmpeg Output: {}", outputStream.toString());
                    }
                    streamProcesses.remove(streamKey);
                }
            });

            // Save process reference
            streamProcesses.put(streamKey, executor);

            log.info("Started streaming, streamKey: {}, rtmpUrl: {}", streamKey, rtmpUrl);
            log.info("Command: {}", cmdLine.toString());
            return true;

        } catch (Exception e) {
            log.error("Failed to start streaming", e);
            return false;
        }
    }

    private CommandLine getDockerCommandLine(String videoFilename, String rtmpUrl) {
        // docker run --rm -v /host/path:/container/path --network host image
        // ffmpeg_args...
        CommandLine cmdLine = new CommandLine("docker");
        cmdLine.addArgument("run");
        cmdLine.addArgument("--rm");

        // Volume mounting
        // -v hostPath:containerPath
        cmdLine.addArgument("-v");
        cmdLine.addArgument(streamConfig.getVideoPath() + ":" + streamConfig.getDockerVideoMount());

        // Network
        // Using host network to access ZLMediaKit easily (might need adjustment for
        // Mac/Windows)
        // Or we can rely on zlmHost being an accessible IP
        // cmdLine.addArgument("--network");
        // cmdLine.addArgument("host");

        cmdLine.addArgument(streamConfig.getFfmpegImage());

        // FFmpeg arguments
        cmdLine.addArgument("-re"); // Read at native framerate
        cmdLine.addArgument("-i");
        cmdLine.addArgument(streamConfig.getDockerVideoMount() + "/" + videoFilename);

        cmdLine.addArgument("-c:v");
        cmdLine.addArgument("libx264");
        cmdLine.addArgument("-preset");
        cmdLine.addArgument("ultrafast");
        cmdLine.addArgument("-tune");
        cmdLine.addArgument("zerolatency");

        cmdLine.addArgument("-c:a");
        cmdLine.addArgument("aac");
        cmdLine.addArgument("-ar");
        cmdLine.addArgument("44100");
        cmdLine.addArgument("-b:a");
        cmdLine.addArgument("128k");

        cmdLine.addArgument("-f");
        cmdLine.addArgument("flv");

        cmdLine.addArgument(rtmpUrl);

        return cmdLine;
    }

    /**
     * Stop streaming
     */
    public boolean stopStream(String streamKey) {
        try {
            DefaultExecutor executor = streamProcesses.get(streamKey);
            if (executor != null) {
                manualStopFlags.put(streamKey, true);
                ExecuteWatchdog watchdog = executor.getWatchdog();
                if (watchdog != null) {
                    watchdog.destroyProcess();
                }
                streamProcesses.remove(streamKey);
                log.info("Stopped streaming successfully, streamKey: {}", streamKey);
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error("Failed to stop streaming", e);
            return false;
        }
    }

    /**
     * Get play URL
     */
    public String getPlayUrl(String streamKey, String protocol) {
        // Since the browser needs to access ZLMediaKit, we use localhost (or the server
        // IP)
        // The zlmHost in config might be internal (for FFmpeg), so we might need a
        // separate public host config
        // For now, assuming they are the same or localhost
        String host = "localhost"; // Default for browser access

        return switch (protocol.toLowerCase()) {
            case "flv" -> String.format("http://%s:%d/live/%s.live.flv",
                    host, streamConfig.getHttpPort(), streamKey);
            case "hls" -> String.format("http://%s:%d/live/%s/hls.m3u8",
                    host, streamConfig.getHttpPort(), streamKey);
            default -> null;
        };
    }

    public boolean isStreaming(String streamKey) {
        return streamProcesses.containsKey(streamKey);
    }
}
