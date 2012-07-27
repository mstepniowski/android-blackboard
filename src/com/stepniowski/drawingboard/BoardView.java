package com.stepniowski.drawingboard;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class BoardView extends View {
	private ArrayList<DrawingPath> paths;
	private DrawingPath currentDrawingPath;
	private Paint currentPaint;
	
	public BoardView(Context context, AttributeSet attrs, int defStyle) {
		
		super(context, attrs, defStyle);
		paths = new ArrayList<DrawingPath>();
		setCurrentPaint();
	}

	public BoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		paths = new ArrayList<DrawingPath>();
		setCurrentPaint();
	}
	
	public BoardView(Context context) {
		super(context);
		
		paths = new ArrayList<DrawingPath>();
		setCurrentPaint();
	}
	
	private void setCurrentPaint() {
		setPaintColor(0xFF000000);
	}

	public void setPaintColor(int color) {
		currentPaint = new Paint();
		currentPaint.setDither(true);
		currentPaint.setColor(color);
		currentPaint.setStyle(Paint.Style.STROKE);
		currentPaint.setStrokeJoin(Paint.Join.ROUND);
		currentPaint.setStrokeCap(Paint.Cap.ROUND);
		currentPaint.setStrokeWidth(20);
	}

    @Override
    protected void onDraw (Canvas canvas) {
    	super.onDraw(canvas);
    	canvas.drawColor(0xFFFFFFFF);
    	if (paths != null) {
    		for (DrawingPath drawingPath : paths) {
    			canvas.drawPath(drawingPath.path, drawingPath.paint);
    		}
    		if (currentDrawingPath != null) {
    			canvas.drawPath(currentDrawingPath.path, currentDrawingPath.paint);
    		}
    	}
    }
    
    public void clear () {
		paths = new ArrayList<DrawingPath>();
		invalidate();
    }
    
    @Override
    public boolean onTouchEvent (MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: 
      	      	currentDrawingPath = new DrawingPath();
      	      	currentDrawingPath.paint = currentPaint;
      	      	currentDrawingPath.path = new Path();
      	      	currentDrawingPath.path.moveTo(motionEvent.getX(), motionEvent.getY());
      	      	currentDrawingPath.path.lineTo(motionEvent.getX(), motionEvent.getY());
      	      	break;
            case MotionEvent.ACTION_MOVE:
                currentDrawingPath.path.lineTo(motionEvent.getX(), motionEvent.getY());
                break;
            case MotionEvent.ACTION_UP:
            	currentDrawingPath.path.lineTo(motionEvent.getX(), motionEvent.getY());
            	paths.add(currentDrawingPath);
            	currentDrawingPath = null;
                break;
//                final int historySize = ev.getHistorySize();
//                final int pointerCount = ev.getPointerCount();
//                for (int h = 0; h < historySize; h++) {
//                	for (int p = 0; p < pointerCount; p++) {
//                		points.add(new PointF(ev.getHistoricalX(p, h), ev.getHistoricalY(p, h)));
//            		}
//                }
//                for (int p = 0; p < pointerCount; p++) {
//                	points.add(new PointF(ev.getX(p), ev.getY(p)));
//                }
        }
    	invalidate();
        return true;        
    }
}
