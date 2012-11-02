package com.commanderZ;
import android.graphics.Canvas;
import android.view.SurfaceHolder;


public class GameDisplayThread extends Thread {
	 	private SurfaceHolder holder;
	    private boolean running = true;
	    private GameDisplay gameDisplay;
	    int i = 0;
	    private long mLastTime;     

	            /** Variables for the counter */
	            private int frameSamplesCollected = 0;
	            private int frameSampleTime = 0;
	            private int fps = 0;

	    public GameDisplayThread(SurfaceHolder holder, GameDisplay gameDisplay) {
	        this.holder = holder;
	        this.gameDisplay = gameDisplay;
	        
	    }

	    @Override
	    public void run() {
	        // this is where the animation will occur
	    	
	    	while(running) {
	    		 Canvas canvas = null;
	    		 try {
	                 canvas = holder.lockCanvas();
	                  synchronized (holder) {
	                     // draw
	              
	                	 updatePhysics();
	                     gameDisplay.onDraw(canvas);
	                     
	                     
	                     
	                 }
	             }
	             finally {
	                      if (canvas != null) {
	                              holder.unlockCanvasAndPost(canvas);
	                          }
	                  }
	             }
	            
	        
	    }
	    
	    
	   
	            private void updatePhysics() {

	                long now = System.currentTimeMillis();
	 
	     
	                if (mLastTime != 0) {
	                    //Time difference between now and last time we were here
	                    int time = (int) (now - mLastTime);
	                    frameSampleTime += time;
	                    frameSamplesCollected++;
	                    //After 10 frames
	                    if (frameSamplesCollected == 10) {
	                        //Update the fps variable
	                        fps = (int) (10000 / frameSampleTime);
	    
	                        //Reset the sampletime + frames collected
	                        frameSampleTime = 0;
	                        frameSamplesCollected = 0;
	                    }
	                }
	                mLastTime = now;
	                
	                gameDisplay.updatePhysics(fps);
	            }
	     


	    public void setRunning(boolean b) {
	        running = b;
	    }
}
