import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.video.*; 
import gab.opencv.*; 
import ddf.minim.*; 
import ddf.minim.analysis.*; 
import ddf.minim.effects.*; 
import ddf.minim.signals.*; 
import ddf.minim.spi.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class audiodetectortwo extends PApplet {













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
 
 public void setup() {
     minim = new Minim(this);
     song = minim.loadFile("song.mp3");
     camera = minim.loadSample( "camera.mp3");
   
   
   
   
     video = new Capture(this, width, height);
     opencv = new OpenCV(this, width, height);  
     scale(2);
      opencv.loadImage(video);
     video.start();
  
  
  
  
  
  
  beat = new BeatDetect(song.bufferSize(), song.sampleRate());
  beat.setSensitivity(300);
  kickSize = snareSize = 16;
 bl = new BeatListener(beat, song);
 
 
 song.play();
 }
 
 
 public void draw() {
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

public void captureEvent(Capture c) {
  c.read();
}
class BeatListener implements AudioListener
{
  private BeatDetect beat;
  private AudioPlayer source;

  BeatListener (BeatDetect beat, AudioPlayer source)
  {
    this.source = source;
    this.source.addListener(this);
    this.beat = beat;
  }
  public void samples(float[] samps)
  {
    beat.detect(source.mix);
  }

  public void samples(float[] sampsL, float[] sampsR)
  {
    beat.detect(source.mix);
  }
}
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "audiodetectortwo" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
