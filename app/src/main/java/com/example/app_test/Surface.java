package com.example.app_test;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 
 */
public class Surface {
	private static final String TAG = null;
	public float density;
	public int width; //
	public int height; 
	public float monthHeight; 
	//public float monthChangeWidth; // 
	public float weekHeight; // 
	public float cellWidth; //
	public float cellHeight; // 
	public float borderWidth;
	public int bgColor = Color.parseColor("#FFFFFF");
	public int textColor = Color.BLACK;
	//private int textColorUnimportant = Color.parseColor("#666666");
	public int btnColor = Color.parseColor("#666666");
	public int borderColor = Color.parseColor("#CCCCCC");
	public int todayNumberColor = Color.RED;
	public int cellDownColor = Color.parseColor("#CCFFFF");
	public int cellSelectedColor = Color.parseColor("#99CCFF");
	public Paint borderPaint;
	public Paint monthPaint;
	public Paint weekPaint;
	public Paint datePaint;
	public Paint monthChangeBtnPaint;
	public Paint cellBgPaint;
	public Path boxPath; // 
	//public Path preMonthBtnPath; // 
	//public Path nextMonthBtnPath; // 
	public String[] weekText = { "Sun","Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
	public String[] monthText = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
	   
	public void init() {
		float temp = height / 7f;
		monthHeight = 0;//(float) ((temp + temp * 0.3f) * 0.6);
		//monthChangeWidth = monthHeight * 1.5f;
		weekHeight = (float) ((temp + temp * 0.3f) * 0.7);
		cellHeight = (height - monthHeight - weekHeight) / 6f;
		cellWidth = width / 7f;
		borderPaint = new Paint();
		borderPaint.setColor(borderColor);
		borderPaint.setStyle(Paint.Style.STROKE);
		borderWidth = (float) (0.5 * density);
		// Log.d(TAG, "borderwidth:" + borderWidth);
		borderWidth = borderWidth < 1 ? 1 : borderWidth;
		borderPaint.setStrokeWidth(borderWidth);
		monthPaint = new Paint();
		monthPaint.setColor(textColor);
		monthPaint.setAntiAlias(true);
		float textSize = cellHeight * 0.4f;
		Log.d(TAG, "text size:" + textSize);
		monthPaint.setTextSize(textSize);
		monthPaint.setTypeface(Typeface.DEFAULT_BOLD);
		weekPaint = new Paint();
		weekPaint.setColor(textColor);
		weekPaint.setAntiAlias(true);
		float weekTextSize = weekHeight * 0.6f;
		weekPaint.setTextSize(weekTextSize);
		weekPaint.setTypeface(Typeface.DEFAULT_BOLD);
		datePaint = new Paint();
		datePaint.setColor(textColor);
		datePaint.setAntiAlias(true);
		float cellTextSize = cellHeight * 0.5f;
		datePaint.setTextSize(cellTextSize);
		datePaint.setTypeface(Typeface.DEFAULT_BOLD);
		boxPath = new Path();
		//boxPath.addRect(0, 0, width, height, Direction.CW);
		//boxPath.moveTo(0, monthHeight);
		boxPath.rLineTo(width, 0);
		boxPath.moveTo(0, monthHeight + weekHeight);
		boxPath.rLineTo(width, 0);
		for (int i = 1; i < 6; i++) {
			boxPath.moveTo(0, monthHeight + weekHeight + i * cellHeight);
			boxPath.rLineTo(width, 0);
			boxPath.moveTo(i * cellWidth, monthHeight);
			boxPath.rLineTo(0, height - monthHeight);
		}
		boxPath.moveTo(6 * cellWidth, monthHeight);
		boxPath.rLineTo(0, height - monthHeight);
		//preMonthBtnPath = new Path();
		//int btnHeight = (int) (monthHeight * 0.6f);
		//preMonthBtnPath.moveTo(monthChangeWidth / 2f, monthHeight / 2f);
		//preMonthBtnPath.rLineTo(btnHeight / 2f, -btnHeight / 2f);
		//preMonthBtnPath.rLineTo(0, btnHeight);
		//preMonthBtnPath.close();
		//nextMonthBtnPath = new Path();
		//nextMonthBtnPath.moveTo(width - monthChangeWidth / 2f,
		//		monthHeight / 2f);
		//nextMonthBtnPath.rLineTo(-btnHeight / 2f, -btnHeight / 2f);
		//nextMonthBtnPath.rLineTo(0, btnHeight);
		//nextMonthBtnPath.close();
		monthChangeBtnPaint = new Paint();
		monthChangeBtnPaint.setAntiAlias(true);
		monthChangeBtnPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		monthChangeBtnPaint.setColor(btnColor);
		cellBgPaint = new Paint();
		cellBgPaint.setAntiAlias(true);
		cellBgPaint.setStyle(Paint.Style.FILL);
		cellBgPaint.setColor(cellSelectedColor);
	}
}

