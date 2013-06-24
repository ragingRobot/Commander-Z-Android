package com.commanderZ;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Character {
	// ///////////////////////////////////////////////////////////////////////////////////
	// PRIVATE VARIABLES
	// ///////////////////////////////////////////////////////////////////////////////////
	private Bitmap _character;
	private int _x = 200;
	private int _y = 600;
	private int _gravity = 22;
	private float _speedX = 0;
	private float _speedY = 0;
	private float _jumpSpeed = 0;
	private Canvas _puck;
	private Bitmap _puckImage;
	private Rect _tileScreenLocation;
	private Rect _tileLocation;
	private int _frame = 0;
	private int _frameY = 0;
	private int _frameTick = 0;
	private int _maxJump = 80;
	private int _maxSpeed = 20;
	private int _maxRun = 30;
	private int _tileUp=0;
	private int _tileRight=0;
	private int _tileDown=0;
	private int _tileLeft=0;
	private int cleartiles = 26;
	private int tileBellow;
	
	
	private int tileHeight;
	int tileWidth;
	int[][] map;

	int tileCurrentX;
	int tileCurrentY ;


	int tileCurrentLeftX;
	int tileCurrentRightX;
	

	// ///////////////////////////////////////////////////////////////////////////////////
	// SETUP STUFF
	// ///////////////////////////////////////////////////////////////////////////////////
	public Character(Bitmap charImage, int startx, int starty) {
		this._x = startx;
		this._y = starty;
		this._character = charImage;
		int w = GameDataManager.getInstance().getTileWidth();
		int h = GameDataManager.getInstance().getCharHeight();

		Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
		_puckImage = Bitmap.createBitmap(w, h, conf); // this creates a MUTABLE
														// bitmap
		_puck = new Canvas(_puckImage);
		_tileScreenLocation = new Rect();
		_tileLocation = new Rect();
	}

	// ///////////////////////////////////////////////////////////////////////////////////
	// EVENT HANDELING
	// ///////////////////////////////////////////////////////////////////////////////////
	public void updatePhysics(int fps) {
		tileBellow = Math.round((_y + (GameDataManager.getInstance().getTileHeight() * 2) + _gravity) / GameDataManager.getInstance().getTileHeight());
	
		tileHeight = GameDataManager.getInstance().getTileHeight();
		tileWidth = GameDataManager.getInstance().getTileWidth();
		map = GameDataManager.getInstance().getCurrentMap();
	
		tileCurrentX = Math.round((_x + ( GameDataManager.getInstance().getCharWidth() / 3))/tileWidth);
		tileCurrentY = Math.round((_y + (GameDataManager.getInstance().getCharHeight() - GameDataManager.getInstance().getCharHeight() / 3))/ tileHeight);


		tileCurrentLeftX = Math.round((_x + 5 )/tileWidth);
		tileCurrentRightX = Math.round((_x + 20 + ( GameDataManager.getInstance().getCharWidth() / 3))/tileWidth);
		/***************************************************************************
		 * This gets the values of the surrounding tiles
		 ***************************************************************************/
		_tileUp = map[tileCurrentY - 2][tileCurrentX];
		_tileRight = map[tileCurrentY][tileCurrentRightX ];
		_tileDown = map[tileCurrentY + 1][tileCurrentX];
		_tileLeft = map[tileCurrentY][tileCurrentLeftX];

		TriggerTile trigger = TriggerTile.getTriggerTile(tileCurrentX,tileCurrentY);	

		/***************************************************************************
		 * Animation
		 ***************************************************************************/
		
		if (_frameTick < fps / 7) {
			_frameTick++;
		} else {
			_frameTick = 0;
			if (GameDataManager.getInstance().getMovingRight()
					|| GameDataManager.getInstance().getMovingLeft()) {
				if (_frame < 3) {
					_frame++;
				} else {
					_frame = 1;
				}

				if (GameDataManager.getInstance().getMovingRight()) {
					_frameY = 0;
				} else {
					_frameY = 1;
				}
			} else {
				_frame = 0;
			}
		}

		
		
		
		
		
		/***************************************************************************
		 * Gravity
		 ***************************************************************************/
		if (_tileDown < cleartiles) {
			this._y += _gravity;

		} else {
			this._y = (tileCurrentY - 1)* tileHeight;
		}
		
		
		
		/***************************************************************************
		 * Jumping
		 ***************************************************************************/

		if (Math.floor(_jumpSpeed) > 0) {

			GameDataManager.getInstance().setJumping(false);
			_jumpSpeed -= _jumpSpeed * .15;
			
			
			if (Math.floor(_jumpSpeed) > 10) {
				_frame = 5;
			} else {
				_frame = 7;

			}
		}
	
		
		if (GameDataManager.getInstance().getJumping() && ( _tileDown > cleartiles)) {

			
				
				_jumpSpeed = _maxJump;
				GameDataManager.getInstance().setJumping(false);
			

		}

		if (Math.floor(_jumpSpeed) < 10) {
			GameDataManager.getInstance().setJumping(false);
			_jumpSpeed = 0;
		}

		
	

		if ( _tileUp < cleartiles) {

			this._y -= _jumpSpeed;
		} else {
			GameDataManager.getInstance().setJumping(false);
			_jumpSpeed = 0;
		}
		

		
		/***************************************************************************
		 * Set moving speed
		 ***************************************************************************/
		
		int maxMoveSpeed = _maxSpeed;
		
		if(GameDataManager.getInstance().getRunning()){
			maxMoveSpeed = _maxRun;
			
		}
		
		
		if (GameDataManager.getInstance().getMovingRight()) {
			if (_speedX < maxMoveSpeed) {
				_speedX+= 4;
			}

		} else if (GameDataManager.getInstance().getMovingLeft()) {
			if (_speedX > -maxMoveSpeed) {
				_speedX-= 4;
			}

		} else {
			if (_speedX > 0) {
				_speedX -= Math.abs(_speedX) * .2;
			}

			if (_speedX < 0) {
				_speedX += Math.abs(_speedX) * .2;
			}
		}

		if (_speedX > 0) {


			if (_tileRight > cleartiles) {

			
				_speedX = 0;

			}

		} else if (_speedX < 0) {

			if (_tileLeft > cleartiles) {

				
				_speedX = 0;
			}
			

		}

		this._x += _speedX;
		this._y += _speedY;
		
		
		
	
		

		

	}

	public Bitmap draw() {
		_puck.drawColor(0, PorterDuff.Mode.CLEAR);
		_tileScreenLocation.set(0, 0, GameDataManager.getInstance().getCharWidth(), GameDataManager.getInstance().getCharHeight());

		_tileLocation.set(_frame * GameDataManager.getInstance().getCharWidth(), _frameY * GameDataManager.getInstance().getCharHeight(), _frame * GameDataManager.getInstance().getCharWidth() + GameDataManager.getInstance().getCharWidth(), _frameY * GameDataManager.getInstance().getCharHeight() + GameDataManager.getInstance().getCharHeight());

	
		// this draws the tile to the screen
		_puck.drawBitmap(_character, _tileLocation, _tileScreenLocation, null);
		return _puckImage;
	}

	// ///////////////////////////////////////////////////////////////////////////////////
	// GETTERS
	// ///////////////////////////////////////////////////////////////////////////////////
	public int getX() {
		return _x;
	}

	public int getY() {
		return _y;
	}
	// ///////////////////////////////////////////////////////////////////////////////////
	// END :D
	// ///////////////////////////////////////////////////////////////////////////////////
}
