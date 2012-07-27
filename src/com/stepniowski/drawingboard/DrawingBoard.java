package com.stepniowski.drawingboard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ShareActionProvider;


public class DrawingBoard extends Activity {
	ShareActionProvider shareActionProvider;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing_board);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_drawing_board, menu);
//		shareActionProvider = (ShareActionProvider) menu.findItem(R.id.menu_share).getActionProvider();
//		try {a
//			shareActionProvider.setShareIntent(createShareIntent());
//		} catch (IOException e) {
//			Log.wtf("DrawingBoard", e);
//		}
        return true;
    }
    
    public void onClick(View view){
        BoardView board = (BoardView) findViewById(R.id.boardView1);
        switch (view.getId()){
        	case R.id.yellow_color_button: board.setPaintColor(0xFFF6F792); break;
        	case R.id.gray_color_button: board.setPaintColor(0xFF333745); break;
        	case R.id.blue_color_button: board.setPaintColor(0xFF77C4D3); break;
        	case R.id.white_color_button: board.setPaintColor(0xFFFFFFFF); break;
        	case R.id.magenta_color_button: board.setPaintColor(0xFFEA2E49); break;
        	case R.id.black_color_button: board.setPaintColor(0xFF000000); break;
        }
    }
        
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        BoardView board = (BoardView) findViewById(R.id.boardView1);

        if (item.getItemId() == R.id.menu_share) {
	    	try {
	    		File file = new File(getExternalCacheDir(), "shared_image.png");
	    		FileOutputStream fos = new FileOutputStream(file);
	
	            Bitmap bitmap = Bitmap.createBitmap(board.getMeasuredWidth(), board.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
	            Canvas canvas = new Canvas(bitmap);
	            board.draw(canvas);
	            bitmap.compress(CompressFormat.PNG, 100, fos);
	    		fos.close();
	    		
	            Intent shareIntent = new Intent(Intent.ACTION_SEND);
	            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
	            shareIntent.setType("image/png");
	            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
	            startActivity(Intent.createChooser(shareIntent, "Share Image"));
	    	} catch (IOException e) {
	    		Log.wtf("DrawingBoard", e);
	    	}
        } else if (item.getItemId() == R.id.menu_clear) {
        	board.clear();
        }
    	return true;
    }
}
