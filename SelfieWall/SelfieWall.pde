import processing.video.*;


import gab.opencv.*;

import ddf.minim.*;
import ddf.minim.analysis.*;
import ddf.minim.effects.*;
import ddf.minim.signals.*;
import ddf.minim.spi.*;


AudioPlayer song;
AudioSample camera;
 BeatDetect beat;
 BeatListener bl;


 Minim minim;
 
 float kickSize, snareSize; 
 
 
Capture video;
OpenCV opencv;
 
 
 float xpos;
float ypos;
 
 void setup() {
     minim = new Minim(this);
     song = minim.loadFile("song.mp3");
     camera = minim.loadSample( "camera.mp3");
   
  // fullScreen();
   
   size(720, 480);
     video = new Capture(this, 720, 480);
     opencv = new OpenCV(this, 720, 480);  
     scale(2);
      opencv.loadImage(video);
     video.start();
  
  
  
  
  
  
  beat = new BeatDetect(song.bufferSize(), song.sampleRate());
  beat.setSensitivity(300);
  kickSize = snareSize = 16;
 bl = new BeatListener(beat, song);
 
 
 song.play();
 }
 
 
 void draw() {
// image(video, 0, 0 );
  
   // Effects on beat detection
  int lowBand = 3;
  int highBand = 13;
  // If the above ranges are detected then inRange will be true
  int numberOfOnsetsThreshold = 2; // Change the sensitivity
  
  if ( beat.isRange(lowBand, highBand, numberOfOnsetsThreshold) )

  {
    // saveFrame("selfie-#################.png");
    camera.trigger();
    opencv.getInput();
    opencv.drawOpticalFlow();
    opencv.getOutput();
    xpos = 0;
    ypos = 0;
    xpos++;
    ypos++;
    
    if (xpos > width) {
      xpos = 0;
    } else if (xpos <width) {
      xpos = 0;
    }
    if (ypos > width) {
      ypos = 0;
    } else if (ypos < width) {
      ypos = 0;
    }

    xpos += (random(-10, 10));
    ypos += (random(-10, 10));    
  tint((random(0, 255)), (random(0,255)), (random(0,255)));
    image(video, xpos, ypos );
  }
 }

void captureEvent(Capture c) {
  c.read();
}