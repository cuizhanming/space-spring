# StreamFlow - Video Streaming Service

> A modern, production-ready video streaming service built with Spring Boot, FFmpeg (Docker), and ZLMediaKit.

## 🚀 Quick Links

- **[Get Started in 5 Minutes](docs/QUICK_START.md)** ⚡
- **[Full Technical Article](docs/ARTICLE.md)** 📖
- **[Documentation Index](docs/INDEX.md)** 📚
- **[Product Requirements](docs/PRD.md)** 🎯

## 📁 Project Structure

```
spring-ffmpeg/
├── README.md                    # This file
├── docs/                        # All documentation
│   ├── QUICK_START.md          # 5-minute setup guide
│   ├── ARTICLE.md              # Complete technical article
│   ├── PRD.md                  # Product requirements
│   └── INDEX.md                # Documentation navigation
├── pom.xml                      # Maven configuration
└── src/
    ├── main/java/              # Spring Boot application
    └── main/resources/         # Configuration & web UI
```

## 🎯 What is StreamFlow?

StreamFlow enables you to stream local video files through your browser using:
- **Spring Boot 4.0** backend with RESTful API
- **FFmpeg** running in Docker containers (no local installation!)
- **ZLMediaKit** media server for RTMP → HTTP-FLV conversion
- **flv.js** player for smooth browser playback

## ⚡ Quick Start

```bash
# 1. Start media server
docker run -d -p 1935:1935 -p 8099:80 --name zlmediakit zlmediakit/zlmediakit:master

# 2. Pull FFmpeg image
docker pull jrottenberg/ffmpeg:4.4-ubuntu

# 3. Run application
mvn spring-boot:run

# 4. Open browser
open http://localhost:8080
```

**That's it!** Enter a video filename, click "Start Stream", and watch your video play.

## 📚 Documentation

### For Different Audiences

**Just want to try it?**  
→ [Quick Start Guide](docs/QUICK_START.md) - 5 minutes to first stream

**Want to understand how it works?**  
→ [Technical Article](docs/ARTICLE.md) - Complete implementation walkthrough

**Need to customize or deploy?**  
→ [Product Requirements](docs/PRD.md) - Architecture and design decisions

**Looking for something specific?**  
→ [Documentation Index](docs/INDEX.md) - Navigate all docs

## 🌟 Key Features

✅ **No Local FFmpeg** - Everything runs in Docker  
✅ **Modern Stack** - Java 21, Spring Boot 4.0  
✅ **Beautiful UI** - Premium web interface  
✅ **Low Latency** - Optimized for real-time streaming  
✅ **Production Ready** - Error handling, logging, monitoring  
✅ **Well Documented** - Multiple guides for different needs  

## 🎬 Demo

A complete walkthrough video demonstrates:
1. Application interface
2. Starting a stream
3. Video playback
4. Stopping a stream

See the [Quick Start Guide](docs/QUICK_START.md) for details.

## 🔧 Technology Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| Language | Java | 21 |
| Framework | Spring Boot | 4.0.0 |
| Media Server | ZLMediaKit | Docker (latest) |
| Transcoder | FFmpeg | Docker 4.4 |
| Player | flv.js | 1.6.2 |

## 📖 API Reference

### Start Stream
```bash
POST /api/stream/start
{
  "videoFilename": "test.mp4",
  "streamKey": "mystream"
}
```

### Stop Stream
```bash
POST /api/stream/stop
{
  "streamKey": "mystream"
}
```

See [Quick Start Guide](docs/QUICK_START.md) for complete API documentation.

## 🐛 Troubleshooting

**Video not playing?**
- Wait 5-10 seconds for stream initialization
- Click the video to start playback (browser autoplay policy)

**Stream 404 error?**
- Verify ZLMediaKit is running: `docker ps | grep zlmediakit`
- Check logs: `docker logs zlmediakit --tail 50`

**FFmpeg fails?**
- Ensure Docker is running
- Verify video file exists and is readable

For more help, see the [Troubleshooting Guide](docs/ARTICLE.md#troubleshooting).

## 🚀 Next Steps

After getting it running:
1. Try different video files
2. Customize the UI
3. Add authentication
4. Implement video upload
5. Deploy to production

See [Technical Article](docs/ARTICLE.md) for implementation ideas.

## 📝 License

Educational purposes.

## 🙏 Acknowledgments

Built with:
- [Spring Boot](https://spring.io/projects/spring-boot)
- [ZLMediaKit](https://github.com/ZLMediaKit/ZLMediaKit)
- [FFmpeg](https://ffmpeg.org/)
- [flv.js](https://github.com/bilibili/flv.js)

---

**Ready to stream?** Start with the [Quick Start Guide](docs/QUICK_START.md) →
