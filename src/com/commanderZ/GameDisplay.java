package com.commanderZ;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class GameDisplay extends SurfaceView  implements SurfaceHolder.Callback {
		/////////////////////////////////////////////////////////////////////////////////////
		//PRIVATE VARIABLES
		/////////////////////////////////////////////////////////////////////////////////////
		private SurfaceHolder _holder;
        private GameDisplayThread _gameDisplayThread;
        private Rect _tileScreenLocation;
        private Rect _tileLocation;
        private Canvas _puck;
        private Bitmap _puckImage;
        private Bitmap _tiles;
        private Bitmap _levelBitmap;
        private Canvas _level;
        private Rect _camera;
        private Rect _screen;
        private Character _character;
        
		/////////////////////////////////////////////////////////////////////////////////////
		//SETUP STUFF
		/////////////////////////////////////////////////////////////////////////////////////
		public GameDisplay(Context context)
		{
		    super(context);
		    init();
		}
		public GameDisplay(Context context, AttributeSet attrs) {
			super( context, attrs );
			init();
		}
		public GameDisplay(Context context, AttributeSet attrs, int defStyle) {
			super( context, attrs, defStyle );
			init();
		}
		private void init(){
			_holder = getHolder();
		    _holder.addCallback(this);
		}
		
		@SuppressLint({ "UseValueOf", "NewApi" })
		private void createBitmaps(){
			
			GameDataManager.getInstance().setDpi(240);
			_tileScreenLocation = new Rect();
			_tileLocation = new Rect();
			_camera = new Rect();
			_screen = new Rect();
			
			_tiles = BitmapFactory.decodeResource(getResources(), com.commanderZ.R.drawable.tiles);
			_character = new Character(BitmapFactory.decodeResource(getResources(), com.commanderZ.R.drawable.commanderz));
			
			
			int w =  GameDataManager.getInstance().getTileWidth() * GameDataManager.getInstance().getCurrentMap()[0].length, h =  GameDataManager.getInstance().getOriginalTileHeight() * GameDataManager.getInstance().getCurrentMap().length;
			
			
			
		
			_camera.set(0,0,this.getWidth(),this.getHeight());
			_screen.set(0,0,this.getWidth(),this.getHeight());
			Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
			_puckImage = Bitmap.createBitmap(w, h, conf); // this creates a MUTABLE bitmap
			_puck = new Canvas(_puckImage);
			
			_levelBitmap = Bitmap.createBitmap(w, h, conf); // this creates a MUTABLE bitmap
			_level = new Canvas(_levelBitmap);
			
			_level = drawTiles(_level);
		}
		
		/////////////////////////////////////////////////////////////////////////////////////
		//HANDEL EVENTS
		/////////////////////////////////////////////////////////////////////////////////////
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			
		}

		public void surfaceCreated(SurfaceHolder holder) {
			createBitmaps();
			_gameDisplayThread = new GameDisplayThread (holder, this);
			_gameDisplayThread.setRunning(true);
			_gameDisplayThread.start();
		}
		public void surfaceDestroyed(SurfaceHolder holder) {
			 boolean retry = true;
			 _gameDisplayThread.setRunning(false);
			    while (retry) {
			        try {
			        	_gameDisplayThread.join();
			            retry = false;
			        } catch (InterruptedException e) {
			        }
			    }
		}

		@Override
		public void onDraw(Canvas canvas) {
		
				_puck.drawBitmap(_levelBitmap, 0,0 , null);
		        
		       
		    	Bitmap charImage = _character.draw();
		        
		        _tileScreenLocation.set( _character.getX() ,_character.getY(), _character.getX() +  GameDataManager.getInstance().getCharWidth(), _character.getY() +  GameDataManager.getInstance().getCharHeight());
		        _tileLocation.set(0, 0 ,  GameDataManager.getInstance().getCharWidth() , GameDataManager.getInstance().getCharHeight());
		        _puck.drawBitmap(charImage, _tileLocation, _tileScreenLocation, null); 
		        canvas.drawColor(Color.BLACK);
		        canvas.drawBitmap(_puckImage, _camera, _screen, null);
		}
		
		public void updatePhysics(int fps){
			int paddingBottom = (_camera.height()/3);
	
			int newX = _character.getX() - (_camera.width()/2);
			 
			int newY = _character.getY() - _camera.height() + paddingBottom;
			 
			 
			_camera.set(  newX, newY ,newX + _camera.width(),newY + _camera.height());
				
				
		
			_character.updatePhysics(fps);
			
		}
		 
		/////////////////////////////////////////////////////////////////////////////////////
		//OTHER FUNCTIONS
		///////////////////////////////////////////////////////////////////////////////////// 
		private Canvas drawTiles(Canvas canvas){
			
			 int tileWidth = GameDataManager.getInstance().getOriginalTileWidth();
		     int tileHeight = GameDataManager.getInstance().getOriginalTileHeight();
		     int adjustedTileWidth =  GameDataManager.getInstance().getTileWidth();
		     int adjustedTileHeight = GameDataManager.getInstance().getTileHeight();
				 
		        for( int i =0 ;i < GameDataManager.getInstance().getCurrentMap().length ; i++){
		        	for( int j =0; j < GameDataManager.getInstance().getCurrentMap()[i].length ; j++){
		        		
				        //this sets the location of the tile on the game screen
		        		//note: this is diff from as3 or javascript because the rect represents top left corner and bottom right corner
		        		// instead of top left corner and width and height
				        _tileScreenLocation.set( j * adjustedTileWidth , i * adjustedTileHeight, (j * adjustedTileWidth) + adjustedTileWidth, (i * adjustedTileHeight) + adjustedTileHeight);
				        
				        //this sets the location of the tile on the tile map
				        //note: this is diff from as3 or javascript because the rect represents top left corner and bottom right corner
		        		// instead of top left corner and width and height
				        _tileLocation.set(GameDataManager.getInstance().getCurrentMap()[i][j] * tileWidth, 0 , (GameDataManager.getInstance().getCurrentMap()[i][j] * tileWidth) + tileWidth , tileHeight);
				     
				        //Log.d("test", "dpi= " + dpi);
				        //this draws the tile to the screen
				        canvas.drawBitmap(_tiles, _tileLocation, _tileScreenLocation, null);
		        	}
		        }
			 return canvas;
		}
		/////////////////////////////////////////////////////////////////////////////////////
		//END :D
		/////////////////////////////////////////////////////////////////////////////////////
}
