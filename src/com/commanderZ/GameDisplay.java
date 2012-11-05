package com.commanderZ;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
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
        private Rect _backgroundSize;
        private Canvas _puck;
        private Bitmap _puckImage;
        private Bitmap _tiles;
        private Bitmap _levelBitmap;
        private Canvas _level;
        private Rect _camera;
        private Rect _screen;
        private Character _character;
        private int _mapHeight = 0;
        private int _mapWidth = 0;
        private int tileSheetWidth = 13;
        private Bitmap _background;
        
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
			_backgroundSize =  new Rect();
			
			_tiles = BitmapFactory.decodeResource(getResources(), com.commanderZ.R.drawable.tilestitle);
			_character = new Character(BitmapFactory.decodeResource(getResources(), com.commanderZ.R.drawable.commanderz));
			_background =  BitmapFactory.decodeResource(getResources(), com.commanderZ.R.drawable.background1);
			getMaxTileWidth();
			int w =  _mapWidth, h =  GameDataManager.getInstance().getOriginalTileHeight() * GameDataManager.getInstance().getCurrentMap().length;
			
			
			
		
			_camera.set(0,0,this.getWidth(),this.getHeight());
			_screen.set(0,0,this.getWidth(),this.getHeight());
			_backgroundSize.set(0,0, 920 , 1641 );
			
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
				
				_puck.drawColor( 0,PorterDuff.Mode.CLEAR );
				_puck.drawBitmap(_levelBitmap, 0,0 , null);
		        
		       
		    	Bitmap charImage = _character.draw();
		        
		        _tileScreenLocation.set( _character.getX() ,_character.getY(), _character.getX() +  GameDataManager.getInstance().getCharWidth(), _character.getY() +  GameDataManager.getInstance().getCharHeight());
		        _tileLocation.set(0, 0 ,  GameDataManager.getInstance().getCharWidth() , GameDataManager.getInstance().getCharHeight());
		        _puck.drawBitmap(charImage, _tileLocation, _tileScreenLocation, null); 

		        canvas.drawBitmap(_background, _backgroundSize, _screen,null);
		        canvas.drawBitmap(_puckImage, _camera, _screen, null);
		}
		
		public void updatePhysics(int fps){
			int paddingBottom = (_camera.height()/3);
	
			int newX = _character.getX() - (_camera.width()/2);
			 
			int newY = _character.getY() - _camera.height() + paddingBottom;
			 if(newX < 0){
				 newX = 0;
			 }else if(newX >  _mapWidth - _camera.width()){
				 newX =  _mapWidth - _camera.width();
			 }
			if(newY < 0){
				newY = 0;
			}else if(newY > _mapHeight - _camera.height()){
				newY = _mapHeight - _camera.height();
			 }
			
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
		     _mapHeight = GameDataManager.getInstance().getCurrentMap().length * adjustedTileHeight;
		     
		        for( int i =0 ;i < GameDataManager.getInstance().getCurrentMap().length ; i++){
		        	

	        		
		        	for( int j =0; j < GameDataManager.getInstance().getCurrentMap()[i].length ; j++){
		        		
		        		
				        //this sets the location of the tile on the game screen
		        		//note: this is diff from as3 or javascript because the rect represents top left corner and bottom right corner
		        		// instead of top left corner and width and height
				        _tileScreenLocation.set( j * adjustedTileWidth , i * adjustedTileHeight, (j * adjustedTileWidth) + adjustedTileWidth, (i * adjustedTileHeight) + adjustedTileHeight);
				        
				        //this sets the location of the tile on the tile map
				        //note: this is diff from as3 or javascript because the rect represents top left corner and bottom right corner
		        		// instead of top left corner and width and height
				        int x = (GameDataManager.getInstance().getCurrentMap()[i][j]  %  tileSheetWidth ) * tileWidth;
				        int y = (GameDataManager.getInstance().getCurrentMap()[i][j]  /  tileSheetWidth ) * tileHeight;
				        _tileLocation.set(x, y , x + tileWidth ,  y + tileHeight);
				     
				        //Log.d("test", "dpi= " + dpi);
				        //this draws the tile to the screen
				        canvas.drawBitmap(_tiles, _tileLocation, _tileScreenLocation, null);
		        	}
		        }
			 return canvas;
		}
		
		private void getMaxTileWidth(){
			int adjustedTileWidth =  GameDataManager.getInstance().getTileWidth();
			 for( int i =0 ;i < GameDataManager.getInstance().getCurrentMap().length ; i++){
		        	
		        	int length = GameDataManager.getInstance().getCurrentMap()[i].length * adjustedTileWidth;
	        		
		        	if(_mapWidth < length  ){
	        			_mapWidth = length;
	        			
	        		}
			 }
			
		}
		/////////////////////////////////////////////////////////////////////////////////////
		//END :D
		/////////////////////////////////////////////////////////////////////////////////////
}
