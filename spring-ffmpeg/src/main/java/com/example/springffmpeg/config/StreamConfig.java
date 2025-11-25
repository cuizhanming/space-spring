package com.example.springffmpeg.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "stream")
public class StreamConfig {

    /**
     * ZLMediaKit host address
     */
    private String zlmHost;

    /**
     * RTMP push port
     */
    private Integer rtmpPort;

    /**
     * HTTP-FLV pull port
     */
    private Integer httpPort;

    /**
     * FFmpeg Docker image name
     */
    private String ffmpegImage;

    /**
     * Local video storage path
     */
    private String videoPath;

    /**
     * Path where videos are mounted inside the Docker container
     */
    private String dockerVideoMount;

    public String getZlmHost() {
        return zlmHost;
    }

    public void setZlmHost(String zlmHost) {
        this.zlmHost = zlmHost;
    }

    public Integer getRtmpPort() {
        return rtmpPort;
    }

    public void setRtmpPort(Integer rtmpPort) {
        this.rtmpPort = rtmpPort;
    }

    public Integer getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(Integer httpPort) {
        this.httpPort = httpPort;
    }

    public String getFfmpegImage() {
        return ffmpegImage;
    }

    public void setFfmpegImage(String ffmpegImage) {
        this.ffmpegImage = ffmpegImage;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getDockerVideoMount() {
        return dockerVideoMount;
    }

    public void setDockerVideoMount(String dockerVideoMount) {
        this.dockerVideoMount = dockerVideoMount;
    }
}
