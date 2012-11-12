package com.commanderZ;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.widget.Button;

@SuppressLint("FloatMath")
public class Character {
	// ///////////////////////////////////////////////////////////////////////////////////
	// PRIVATE VARIABLES
	// ///////////////////////////////////////////////////////////////////////////////////
	private Bitmap _character;
	private int _x = 200;
	private int _y = 400;
	private int _gravity = 22;
	private float _speed = 0;
	private float _jumpSpeed = 0;
	private Canvas _puck;
	private Bitmap _puckImage;
	private Rect _tileScreenLocation;
	private Rect _tileLocation;
	private int _frame = 0;
	private int _frameY = 0;
	private int _frameTick = 0;
	private int _maxJump = 60;
	private int _maxSpeed = 8;

	// ///////////////////////////////////////////////////////////////////////////////////
	// SETUP STUFF
	// ///////////////////////////////////////////////////////////////////////////////////
	public Character(Bitmap charImage, int startx, int starty) {
		this._x = startx;
		this._y = starty;
		this._character = charImage;
		int w = GameDataManager.getInstance().getTileWidth(), h = GameDataManager
				.getInstance().getOriginalTileHeight() * 2;

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
	@SuppressLint("FloatMath")
	public void updatePhysics(int fps) {
		int cleartiles = 26;
		int tileBellow = Math.round((_y + (GameDataManager.getInstance().getTileHeight() * 2) + _gravity) / GameDataManager.getInstance().getTileHeight());
		int tileAbove = Math.round((_y + (GameDataManager.getInstance().getTileHeight() * 2))/ GameDataManager.getInstance().getTileHeight()) - 1;
		int tileRight = Math.round((_x + (GameDataManager.getInstance().getTileWidth()) + _speed)/ GameDataManager.getInstance().getTileWidth());
		int tileLeft = Math.round((_x + _speed - 15)/ GameDataManager.getInstance().getTileWidth());
		int tileCurrentX = Math.round((_x + (GameDataManager.getInstance().getTileWidth() / 2))/ GameDataManager.getInstance().getTileWidth());
		int tileHeight = GameDataManager.getInstance().getTileHeight();
		int tileWidth = GameDataManager.getInstance().getTileWidth();
		int[][] map = GameDataManager.getInstance().getCurrentMap();

		TriggerTile trigger = TriggerTile.getTriggerTile(_x / tileWidth,(_y / tileHeight) + 1);

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

		if (Math.floor(_jumpSpeed) > 0) {
			GameDataManager.getInstance().setJumping(false);
			_jumpSpeed -= _jumpSpeed * .15;

			if (Math.floor(_jumpSpeed) > 10) {
				_frame = 5;
			} else {
				_frame = 7;

			}
		}

		if (GameDataManager.getInstance().getJumping()
				&& (map[tileBellow][tileCurrentX] > cleartiles)) {

			if (trigger == null || trigger.getName() != "death") {
				_jumpSpeed = _maxJump;
				GameDataManager.getInstance().setJumping(false);
			} else {
				_jumpSpeed = 0;
				GameDataManager.getInstance().setJumping(false);
			}

		}

		if (Math.floor(_jumpSpeed) < 10) {

			_jumpSpeed = 0;
		}

		if (tileBellow < GameDataManager.getInstance().getCurrentMap().length - 1) {
			if (map[tileBellow][tileCurrentX] < cleartiles) {
				this._y += _gravity;

			} else {
				this._y = (tileBellow * tileHeight) - (tileHeight * 2);
			}
		}

		if (map[Math.round((_y - 10) / tileHeight)][tileCurrentX] < cleartiles) {

			this._y -= _jumpSpeed;
		} else {

			_jumpSpeed = 0;
		}

		if (GameDataManager.getInstance().getMovingRight()) {
			if (_speed < _maxSpeed) {
				_speed++;
			}

		} else if (GameDataManager.getInstance().getMovingLeft()) {
			if (_speed > -_maxSpeed) {
				_speed--;
			}

		} else {
			if (_speed > 0) {
				_speed -= Math.abs(_speed) * .3;
			}

			if (_speed < 0) {
				_speed += Math.abs(_speed) * .6;
			}
		}

		if (_speed > 0) {

			// This checks diagonally right
			if (_jumpSpeed == 0 && map[tileBellow][tileCurrentX] < cleartiles) {
				if (map[tileBellow][tileRight] > cleartiles) {
					_jumpSpeed = 0;
					this._x = (tileRight - 1) * tileWidth;
				}
			} else {
				if (map[tileAbove][tileRight] > cleartiles) {
					_speed = 0;
					this._x = (tileRight - 1) * tileWidth;
				}
			}

			if (map[Math.round((_y + (tileHeight)) / tileHeight)][tileRight] > cleartiles) {

				this._x = (tileRight - 1) * tileWidth;
				_speed = 0;

			}

		} else if (_speed < 0) {

			// This checks diagonally left
			if (_jumpSpeed == 0 && map[tileBellow][tileCurrentX] < cleartiles) {
				if (map[tileBellow][tileLeft] > cleartiles) {
					_jumpSpeed = 0;
					this._x = (tileLeft + 1) * tileWidth;
				}
			} else {
				if (map[tileAbove][tileLeft] > cleartiles) {
					_speed = 0;
					this._x = (tileLeft + 1) * tileWidth;
				}
			}

			if (map[Math.round((_y + (tileHeight)) / tileHeight)][tileLeft] > cleartiles) {

				this._x = (tileLeft + 1) * tileWidth;
				_speed = 0;
			}

		}

		this._x += _speed;

		_puck.drawColor(0, PorterDuff.Mode.CLEAR);
		_tileScreenLocation.set(0, 0, GameDataManager.getInstance().getCharWidth(), GameDataManager.getInstance().getCharHeight());

		_tileLocation.set(_frame * GameDataManager.getInstance().getOriginalCharWidth(), _frameY * GameDataManager.getInstance().getOriginalCharHeight(), _frame * GameDataManager.getInstance().getOriginalCharWidth() + GameDataManager.getInstance().getOriginalCharWidth(), _frameY * GameDataManager.getInstance().getOriginalCharHeight() + GameDataManager.getInstance().getOriginalCharHeight());

		// Log.d("test", "dpi= " + dpi);
		// this draws the tile to the screen
		_puck.drawBitmap(_character, _tileLocation, _tileScreenLocation, null);

	}

	public Bitmap draw() {

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
