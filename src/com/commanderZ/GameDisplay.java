package com.commanderZ;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameDisplay extends SurfaceView  implements SurfaceHolder.Callback {
	
	
		private SurfaceHolder holder;
        private GameDisplayThread gameDisplayThread;
        int[][] map;
        Rect tileScreenLocation;
        Rect tileLocation;
        int tileWidth = 67;
        int tileHeight = 67;
        int adjustedTileHeight;
        int adjustedTileWidth;
        int dpi;
        private Bitmap tiles;
        
		public GameDisplay(Context context, int dpi)
		{
			
		    super(context);
		    this.dpi = dpi;
		    holder = getHolder();
	        holder.addCallback(this);
	       
	        //tiles = BitmapFactory.decodeResource(getResources(), R.drawable.btn_radio);
		       
           
		}
		
		private void createBitmaps(){
			 
		      
			tileScreenLocation = new Rect();
			tileLocation = new Rect();
			tiles = BitmapFactory.decodeResource(getResources(), com.commanderZ.R.drawable.tiles);
			 map = new int[][] {{1,0,0,0,0,0,0,0,0,0,0,0,0,1},{1,2,2,0,0,0,0,0,0,0,0,0,0,1},{1,0,0,0,0,0,0,0,0,0,0,0,0,1},{1,0,0,1,1,0,0,0,0,0,0,0,0,1},{1,1,1,1,1,1,1,1,1,1,1,0,0,1},{1,0,0,0,0,0,0,0,0,0,0,0,0,1},{1,0,0,2,0,0,2,0,0,2,0,0,2,1},{1,0,0,0,0,0,0,0,0,0,0,0,0,1},{1,0,2,2,2,0,0,0,2,2,2,0,0,1},{1,0,0,0,0,0,0,0,0,0,0,0,0,1},{1,0,0,0,1,1,0,0,0,0,0,0,0,1},{1,1,1,1,1,0,0,0,1,1,1,1,1,1},{1,0,0,0,0,0,0,0,0,0,0,0,0,1},{1,0,0,0,0,0,0,0,0,0,0,0,0,1},{1,2,2,0,0,2,2,2,0,0,0,2,2,1},{1,0,0,0,0,0,0,0,0,0,0,0,0,1},{1,0,0,1,1,0,0,0,1,1,1,0,0,1},{1,0,0,0,0,0,0,0,0,0,0,0,0,1},{1,0,0,0,2,2,0,0,0,0,0,0,0,1},{1,0,0,0,0,0,0,0,0,0,0,0,0,1},{1,0,0,0,0,0,2,2,0,0,0,0,0,1},{1,0,0,0,0,0,0,0,0,0,0,0,0,1},{1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
			 //120 is the dpi of the sprite sheet
			 if(dpi != 120){
				 adjustedTileWidth = tileWidth / (dpi/120);
				 adjustedTileHeight = tileHeight  / (dpi/120);
			 }else{
				 adjustedTileWidth =  tileWidth;
				 adjustedTileHeight = tileHeight;
				 
			 }
		}

		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			// TODO Auto-generated method stub
			
		}

		public void surfaceCreated(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			createBitmaps();
			gameDisplayThread = new GameDisplayThread (holder, this);
			gameDisplayThread.setRunning(true);
			gameDisplayThread.start();
		    
			
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			 boolean retry = true;
			 gameDisplayThread.setRunning(false);
			    while (retry) {
			        try {
			        	gameDisplayThread.join();
			            retry = false;
			        } catch (InterruptedException e) {
			        }
			    }
			
		}
		
		 @Override
		    public void onDraw(Canvas canvas) {
			
		        
		        canvas.drawColor(Color.BLACK);
		        
		        for( int i =0 ;i < map.length ; i++){
		        	for( int j =0; j < map[i].length ; j++){
		        		
				        //this sets the location of the tile on the game screen
		        		//note: this is diff from as3 or javascript because the rect represents top left corner and bottom right corner
		        		// instead of top left corner and width and height
				        tileScreenLocation.set( j * adjustedTileWidth , i * adjustedTileHeight, (j * adjustedTileWidth) + adjustedTileWidth, (i * adjustedTileHeight) + adjustedTileHeight);
				        
				        //this sets the location of the tile on the tile map
				        //note: this is diff from as3 or javascript because the rect represents top left corner and bottom right corner
		        		// instead of top left corner and width and height
				        tileLocation.set(map[i][j] * tileWidth, 0 , (map[i][j] * tileWidth) + tileWidth , tileHeight);
				     
				        //Log.d("test", "dpi= " + dpi);
				        //this draws the tile to the screen
				        canvas.drawBitmap(tiles, tileLocation, tileScreenLocation, null);
		        	}
		        }
		        
                
                
		    }

		
		    
}
