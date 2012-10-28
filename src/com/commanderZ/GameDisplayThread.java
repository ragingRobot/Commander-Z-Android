package com.commanderZ;
import android.graphics.Canvas;
import android.view.SurfaceHolder;


public class GameDisplayThread extends Thread {
	 	private SurfaceHolder holder;
	    private boolean running = true;
	    private GameDisplay gameDisplay;
	    int i = 0;
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

	    public void setRunning(boolean b) {
	        running = b;
	    }
}
