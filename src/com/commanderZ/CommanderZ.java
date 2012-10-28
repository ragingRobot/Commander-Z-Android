package com.commanderZ;

import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Window;

public class CommanderZ extends Activity {
	private GameDisplay view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	DisplayMetrics dm = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(dm);
	       
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = new GameDisplay(this, dm.densityDpi);
        setContentView(view);
    }
    
  
}
