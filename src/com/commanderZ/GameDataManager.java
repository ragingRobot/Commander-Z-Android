package com.commanderZ;

public class GameDataManager {
		/////////////////////////////////////////////////////////////////////////////////////
		//STATIC VARIABLES
		/////////////////////////////////////////////////////////////////////////////////////
		private static GameDataManager _instance;
		
		/////////////////////////////////////////////////////////////////////////////////////
		//PRIVATE VARIABLES
		/////////////////////////////////////////////////////////////////////////////////////
		private boolean _movingLeft = false;
		private boolean _movingRight = false;
		private boolean _jumping = false;
		private int _tileHeight;
        private int _tileWidth;
        private int _dpi;
        private int _originalTileWidth = 67;
        private int _originalTileHeight = 67;
        private int[][] _map;
		/////////////////////////////////////////////////////////////////////////////////////
		//STATIC FUNCTIONS
		/////////////////////////////////////////////////////////////////////////////////////
		public static GameDataManager getInstance(){
			
			if(_instance == null){
				
				_instance = new GameDataManager();
			}
			return _instance;
		}
		/////////////////////////////////////////////////////////////////////////////////////
		//PUBLIC GET FUNCTIONS
		/////////////////////////////////////////////////////////////////////////////////////
		public boolean getMovingLeft(){
			return _movingLeft;
		}
		public boolean getMovingRight(){
			return _movingRight;
		}
		public boolean getJumping(){
			return _jumping;
		}
		public int getTileHeight(){
			return _tileHeight;
		}
		public int getTileWidth(){
			return _tileWidth;
		}
		public int getOriginalTileHeight(){
			return _originalTileHeight;
		}
		public int getOriginalTileWidth(){
			return _originalTileWidth;
		}
		public int getDpi(){
			return _dpi;
		}
		public int[][] getCurrentMap(){
			return _map;
		}
		/////////////////////////////////////////////////////////////////////////////////////
		//PUBLIC SET FUNCTIONS
		/////////////////////////////////////////////////////////////////////////////////////
		public void setMovingLeft(boolean val){
			_movingLeft = val;
		}
		public void setMovingRight(boolean val){
			_movingRight = val;
		}
		public void setJumping(boolean val){
			_jumping = val;
		}
		public void setDpi(int val){
			_dpi = val;
			 if(_dpi != 120){
				 _tileWidth = _originalTileWidth / (_dpi/120);
				 _tileHeight = _originalTileHeight  / (_dpi/120);
			 }else{
				 _tileWidth =  _originalTileWidth;
				 _tileHeight = _originalTileHeight;
				 
			 }
		}
		public void setCurrentMap(int[][] val){
			_map = val;
		}
		/////////////////////////////////////////////////////////////////////////////////////
		//END :D
		/////////////////////////////////////////////////////////////////////////////////////
}
