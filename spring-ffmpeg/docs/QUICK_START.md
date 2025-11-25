# StreamFlow - Quick Start Guide

## 🚀 Quick Start (5 Minutes)

### 1. Start Infrastructure
```bash
# Start ZLMediaKit
docker run -d -p 1935:1935 -p 8099:80 --name zlmediakit zlmediakit/zlmediakit:master

# Pull FFmpeg image
docker pull jrottenberg/ffmpeg:4.4-ubuntu
```

### 2. Prepare Video
```bash
mkdir -p ~/Videos
# Place your video file in ~/Videos/ or download test video:
curl -o ~/Videos/test.mp4 http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4
```

### 3. Run Application
```bash
cd spring-ffmpeg
mvn spring-boot:run
```

### 4. Stream Video
1. Open http://localhost:8080
2. Enter filename: `test.mp4`
3. Enter stream key: `mystream`
4. Click **Start Stream**
5. Wait 5 seconds
6. Click video to play

## 📁 Project Files

```
spring-ffmpeg/
├── ARTICLE.md              # Comprehensive technical article
├── README.md               # Setup and usage guide
├── PRD.md                  # Product requirements document
├── QUICK_START.md          # This file
├── pom.xml                 # Maven configuration
├── src/
│   ├── main/java/com/example/springffmpeg/
│   │   ├── SpringFfmpegApplication.java
│   │   ├── config/StreamConfig.java
│   │   ├── controller/StreamController.java
│   │   └── service/StreamService.java
│   └── main/resources/
│       ├── application.yml
│       └── static/index.html
```

## 🎯 API Reference

### Start Stream
```bash
POST http://localhost:8080/api/stream/start
Content-Type: application/json

{
  "videoFilename": "test.mp4",
  "streamKey": "mystream"
}

Response:
{
  "message": "Stream started",
  "flvUrl": "http://localhost:8099/live/mystream.live.flv",
  "hlsUrl": "http://localhost:8099/live/mystream/hls.m3u8"
}
```

### Stop Stream
```bash
POST http://localhost:8080/api/stream/stop
Content-Type: application/json

{
  "streamKey": "mystream"
}
```

## 🔧 Configuration

Edit `src/main/resources/application.yml`:

```yaml
stream:
  zlm-host: localhost
  rtmp-port: 1935
  http-port: 8099
  ffmpeg-image: jrottenberg/ffmpeg:4.4-ubuntu
  video-path: /Users/cui/Videos/    # Change to your path
  docker-video-mount: /videos
```

## 🐛 Common Issues

### Video Not Playing
- **Wait longer**: Stream needs 5-10 seconds to initialize
- **Click video**: Browser autoplay policy requires user interaction
- **Check console**: Open browser DevTools for errors

### Stream 404 Error
- **Verify ZLMediaKit**: `docker ps | grep zlmediakit`
- **Check logs**: `docker logs zlmediakit --tail 50`
- **Wait longer**: Stream registration takes a few seconds

### FFmpeg Fails
- **Check Docker**: `docker ps`
- **Verify path**: Ensure video file exists
- **Check permissions**: Video file must be readable

## 📊 Monitoring

### Check Running Streams
```bash
# View FFmpeg containers
docker ps | grep ffmpeg

# View ZLMediaKit logs
docker logs zlmediakit --tail 50

# Check Spring Boot logs
# Look for "Started streaming" messages
```

### Resource Usage
```bash
# Monitor Docker containers
docker stats

# Each FFmpeg container uses ~100-200MB RAM
```

## 🎬 Demo Video

A complete walkthrough video has been recorded showing:
1. Application interface tour
2. Starting a stream
3. Video playback
4. Stopping a stream

Video location: `streamflow_walkthrough.webp`

## 📚 Documentation

- **[ARTICLE.md](ARTICLE.md)** - Full technical article with implementation details
- **[README.md](../README.md)** - Comprehensive setup and usage guide
- **[PRD.md](PRD.md)** - Product requirements and architecture
- **[INDEX.md](INDEX.md)** - Documentation navigation guide

## 🌟 Features

✅ Docker-based FFmpeg (no local installation)  
✅ RESTful API for stream control  
✅ Modern, beautiful web interface  
✅ Low-latency streaming  
✅ Multiple concurrent streams  
✅ Automatic cleanup  
✅ Comprehensive error handling  

## 🔗 Useful Commands

```bash
# Stop all streams
docker stop $(docker ps -q --filter ancestor=jrottenberg/ffmpeg:4.4-ubuntu)

# Restart ZLMediaKit
docker restart zlmediakit

# Clean up
docker stop zlmediakit
docker rm zlmediakit

# View application logs
# Check terminal where mvn spring-boot:run is running
```

## 💡 Tips

1. **Use unique stream keys** for each stream
2. **Wait 5 seconds** after clicking Start Stream
3. **Click the video** to start playback (browser policy)
4. **Check browser console** if issues occur
5. **Monitor Docker** with `docker stats` for resource usage

## 🚀 Next Steps

- Add video upload functionality
- Implement user authentication
- Support multiple video formats
- Add stream recording
- Implement adaptive bitrate
- Add WebRTC support

---

**Need Help?** Check the full documentation in ARTICLE.md and README.md
