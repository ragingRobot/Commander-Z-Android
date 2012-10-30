package com.commanderZ;

import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;

public class CommanderZ extends Activity {
	private GameDisplay view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	DisplayMetrics dm = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(dm);
	       
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //view = new GameDisplay(this, dm.densityDpi);
        //Button button = new Button(this);
        setContentView(R.layout.gamescreen); 
        
        view = (GameDisplay) findViewById(R.id.surfaceView1);
        
        Button leftButton = (Button) findViewById(R.id.button1);
        leftButton.setOnTouchListener(leftListener);
        
        
        Button rightButton = (Button) findViewById(R.id.button2);
        rightButton.setOnTouchListener(rightListener);
        
    }
    
    
    public OnTouchListener rightListener = new OnTouchListener() {
   
    	        public boolean onTouch(View v, MotionEvent event) {
    	
    	            if (event.getAction() == MotionEvent.ACTION_DOWN) {
    	
    	                Log.d("base", "rightDown");
    	                view.right(true);
    	
    	            }
    	            
    	            if (event.getAction() == MotionEvent.ACTION_UP) {
    	            	
    	                Log.d("base", "rightUp");
    	                view.right(false);
    	            }
    
    	            return false;
    	
    	        }
    	
    };
    
    public OnTouchListener leftListener = new OnTouchListener() {
    	   
        public boolean onTouch(View v, MotionEvent event) {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                Log.d("base", "leftDown");
                view.left(true);
            }
            
            if (event.getAction() == MotionEvent.ACTION_UP) {
            	
                Log.d("base", "leftUp");
                view.left(false);
            }

            return false;

        }

    };

    
  
}
