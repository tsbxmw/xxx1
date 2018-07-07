

package com.google.zxing.client.android;


import com.example.app_test.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.camera.CameraManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.Map;
import com.example.app_test.*;

public final class CaptureActivity extends Activity implements SurfaceHolder.Callback {
     /*
      * Copyright (C) 2008 ZXing authors
      *
      * Licensed under the Apache License, Version 2.0 (the "License");
      * you may not use this file except in compliance with the License.
      * You may obtain a copy of the License at
      *
      *      http://www.apache.org/licenses/LICENSE-2.0
      *
      * Unless required by applicable law or agreed to in writing, software
      * distributed under the License is distributed on an "AS IS" BASIS,
      * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
      * See the License for the specific language governing permissions and
      * limitations under the License.
      */

    

     /**
      * This activity opens the camera and does the actual scanning on a background thread. It draws a
      * viewfinder to help the user place the barcode correctly, shows feedback as the image processing
      * is happening, and then overlays the results when a scan is successful.
      *
      * @author dswitkin@google.com (Daniel Switkin)
      * @author Sean Owen
      */

       private static final String TAG = CaptureActivity.class.getSimpleName();

       private static final long DEFAULT_INTENT_RESULT_DURATION_MS = 1500L;
       private static final long BULK_MODE_SCAN_DELAY_MS = 1000L;

       private static final String[] ZXING_URLS = { "http://zxing.appspot.com/scan", "zxing://scan/" };

       public static final int HISTORY_REQUEST_CODE = 0x0000bacc;

       private static final Collection<ResultMetadataType> DISPLAYABLE_METADATA_TYPES =
           EnumSet.of(ResultMetadataType.ISSUE_NUMBER,
                      ResultMetadataType.SUGGESTED_PRICE,
                      ResultMetadataType.ERROR_CORRECTION_LEVEL,
                      ResultMetadataType.POSSIBLE_COUNTRY);

       private CameraManager cameraManager;
       private CaptureActivityHandler handler;
       private Result savedResultToShow;
       private ViewfinderView viewfinderView;
       private TextView statusView;
       private View resultView;
       private Result lastResult;
       private boolean hasSurface;
       private boolean copyToClipboard;
       private IntentSource source;
       private String sourceUrl;
       private Collection<BarcodeFormat> decodeFormats;
       private Map<DecodeHintType,?> decodeHints;
       private String characterSet;
       private InactivityTimer inactivityTimer;
       private BeepManager beepManager;
       private Bundle bundle;
       
     //  private Button imageButton_back;
       
       ViewfinderView getViewfinderView() {
         return viewfinderView;
       }

       public Handler getHandler() {
         return handler;
       }

       CameraManager getCameraManager() {
         return cameraManager;
       }

       @Override
       public void onCreate(Bundle icicle) {
         super.onCreate(icicle);

         Window window = getWindow();
         window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
         setContentView(R.layout.capture);
         Intent intent = this.getIntent();
         bundle = intent.getExtras();
         System.out.println("test of capture !");
         hasSurface = false;
         inactivityTimer = new InactivityTimer(this);
         beepManager = new BeepManager(this);

         PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
         System.out.println("it is true");
        
         
         
         
        // imageButton_back = (Button) findViewById(R.id.capture_imageview_back);
      //   imageButton_back.setOnClickListener(new View.OnClickListener() {
             //    @Override
       //      public void onClick(View v) {
              //   finish();
        //     }
        // });
         
       }

       @Override
       protected void onResume() {
         super.onResume();
         
         // historyManager must be initialized here to update the history preference

         // CameraManager must be initialized here, not in onCreate(). This is necessary because we don't
         // want to open the camera driver and measure the screen size if we're going to show the help on
         // first launch. That led to bugs where the scanning rectangle was the wrong size and partially
         // off screen.
         cameraManager = new CameraManager(getApplication());

         viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
         viewfinderView.setCameraManager(cameraManager);

         handler = null;
         SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
         SurfaceHolder surfaceHolder = surfaceView.getHolder();
         if (hasSurface) {
        	 initCamera(surfaceHolder);
         } else {
        	 surfaceHolder.addCallback(this);
         }
         beepManager.updatePrefs();
         inactivityTimer.onResume();
         source = IntentSource.NONE;
         decodeFormats = null;
         characterSet = null;

      
       }

       private int getCurrentOrientation() {
         int rotation = getWindowManager().getDefaultDisplay().getRotation();
         if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
           switch (rotation) {
             case Surface.ROTATION_0:
             case Surface.ROTATION_90:
               return ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
             default:
               return ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
           }
         } else {
           switch (rotation) {
             case Surface.ROTATION_0:
             case Surface.ROTATION_270:
               return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
             default:
               return ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
           }
         }
       }
       
       private static boolean isZXingURL(String dataString) {
         if (dataString == null) {
           return false;
         }
         for (String url : ZXING_URLS) {
           if (dataString.startsWith(url)) {
             return true;
           }
         }
         return false;
       }

       @Override
       protected void onPause() {
    	   if (handler != null) {
    	         handler.quitSynchronously();
    	         handler = null;
    	     }
    	     inactivityTimer.onPause();
    	     beepManager.close();
    	     cameraManager.closeDriver();
    	     if (!hasSurface) {
    	         SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
    	         SurfaceHolder surfaceHolder = surfaceView.getHolder();
    	         surfaceHolder.removeCallback(this);
    	     }
    	     super.onPause();
       }

       @Override
       protected void onDestroy() {
    	   inactivityTimer.shutdown();
    	     super.onDestroy();
       }

       @Override
       public boolean onKeyDown(int keyCode, KeyEvent event) {
         switch (keyCode) {
           case KeyEvent.KEYCODE_BACK:
             if (source == IntentSource.NATIVE_APP_INTENT) {
               setResult(RESULT_CANCELED);
               finish();
               return true;
             }
             if ((source == IntentSource.NONE || source == IntentSource.ZXING_LINK) && lastResult != null) {
               restartPreviewAfterDelay(0L);
               return true;
             }
             break;
           case KeyEvent.KEYCODE_FOCUS:
           case KeyEvent.KEYCODE_CAMERA:
             // Handle these events so they don't launch the Camera app
             return true;
           // Use volume up/down to turn on light
           case KeyEvent.KEYCODE_VOLUME_DOWN:
             cameraManager.setTorch(false);
             return true;
           case KeyEvent.KEYCODE_VOLUME_UP:
             cameraManager.setTorch(true);
             return true;
         }
         return super.onKeyDown(keyCode, event);
       }

       @Override
       public boolean onCreateOptionsMenu(Menu menu) {
         MenuInflater menuInflater = getMenuInflater();
         menuInflater.inflate(R.menu.capture, menu);
         return super.onCreateOptionsMenu(menu);
       }

       @Override
       public boolean onOptionsItemSelected(MenuItem item) {
         Intent intent = new Intent(Intent.ACTION_VIEW);
         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
         switch (item.getItemId()) {
           case R.id.menu_share:
            
            
             break;
           case R.id.menu_history:
             
             break;
           case R.id.menu_settings:
             intent.setClassName(this, PreferencesActivity.class.getName());
             startActivity(intent);
             break;
           case R.id.menu_help:
             
             break;
           default:
             return super.onOptionsItemSelected(item);
         }
         return true;
       }

       @Override
       public void onActivityResult(int requestCode, int resultCode, Intent intent) {
       
       }

       private void decodeOrStoreSavedBitmap(Bitmap bitmap, Result result) {
         // Bitmap isn't used yet -- will be used soon
         if (handler == null) {
           savedResultToShow = result;
         } else {
           if (result != null) {
             savedResultToShow = result;
           }
           if (savedResultToShow != null) {
             Message message = Message.obtain(handler, R.id.decode_succeeded, savedResultToShow);
             handler.sendMessage(message);
           }
           savedResultToShow = null;
         }
       }

       @Override
       public void surfaceCreated(SurfaceHolder holder) {
         if (holder == null) {
           Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
         }
         if (!hasSurface) {
           hasSurface = true;
           initCamera(holder);
         }
       }

       @Override
       public void surfaceDestroyed(SurfaceHolder holder) {
         hasSurface = false;
       }

       @Override
       public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

       }

       /**
        * A valid barcode has been found, so give an indication of success and show the results.
        *
        * @param rawResult The contents of the barcode.
        * @param scaleFactor amount by which thumbnail was scaled
        * @param barcode   A greyscale bitmap of the camera data which was decoded.
        */
       public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
    	   inactivityTimer.onActivity();
    	   boolean fromLiveScan = barcode != null;
    	  if (fromLiveScan) {
    	       beepManager.playBeepSoundAndVibrate();
    	       Toast.makeText(this, "scansuccessful", Toast.LENGTH_SHORT).show();
    	       Intent intent = getIntent();
    	       intent.setClass(CaptureActivity.this, ScanResultShow.class);
    	       Bundle temp= new Bundle();
    	       temp.putString("scanresult", rawResult.getText());
    	       temp.putString("user", bundle.getString("user"));
    	     //  temp.putString("pass", bundle.getString("pass"));
    	       intent.putExtras(temp);
    	       System.out.println("TAG : debug : codedContent = "+ rawResult.getText());
    	       
    	       setResult(RESULT_OK, intent);
    	       startActivity(intent);
    	       finish();
    	   }
       }

       /**
        * Superimpose a line for 1D or dots for 2D to highlight the key features of the barcode.
        *
        * @param barcode   A bitmap of the captured image.
        * @param scaleFactor amount by which thumbnail was scaled
        * @param rawResult The decoded results which contains the points to draw.
        */
       private void drawResultPoints(Bitmap barcode, float scaleFactor, Result rawResult) {
         ResultPoint[] points = rawResult.getResultPoints();
         if (points != null && points.length > 0) {
           Canvas canvas = new Canvas(barcode);
           Paint paint = new Paint();
           paint.setColor(getResources().getColor(R.color.result_points));
           if (points.length == 2) {
             paint.setStrokeWidth(4.0f);
             drawLine(canvas, paint, points[0], points[1], scaleFactor);
           } else if (points.length == 4 &&
                      (rawResult.getBarcodeFormat() == BarcodeFormat.UPC_A ||
                       rawResult.getBarcodeFormat() == BarcodeFormat.EAN_13)) {
             // Hacky special case -- draw two lines, for the barcode and metadata
             drawLine(canvas, paint, points[0], points[1], scaleFactor);
             drawLine(canvas, paint, points[2], points[3], scaleFactor);
           } else {
             paint.setStrokeWidth(10.0f);
             for (ResultPoint point : points) {
               if (point != null) {
                 canvas.drawPoint(scaleFactor * point.getX(), scaleFactor * point.getY(), paint);
               }
             }
           }
         }
       }

       private static void drawLine(Canvas canvas, Paint paint, ResultPoint a, ResultPoint b, float scaleFactor) {
         if (a != null && b != null) {
           canvas.drawLine(scaleFactor * a.getX(), 
                           scaleFactor * a.getY(), 
                           scaleFactor * b.getX(), 
                           scaleFactor * b.getY(), 
                           paint);
         }
       }

       // Put up our own UI for how to handle the decoded contents.
      

       // Briefly show the contents of the barcode, then handle the result outside Barcode Scanner.
       
       
       private void sendReplyMessage(int id, Object arg, long delayMS) {
         if (handler != null) {
           Message message = Message.obtain(handler, id, arg);
           if (delayMS > 0L) {
             handler.sendMessageDelayed(message, delayMS);
           } else {
             handler.sendMessage(message);
           }
         }
       }

       private void initCamera(SurfaceHolder surfaceHolder) {
         if (surfaceHolder == null) {
           throw new IllegalStateException("No SurfaceHolder provided");
         }
         if (cameraManager.isOpen()) {
           Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
           return;
         }
         try {
           cameraManager.openDriver(surfaceHolder);
           // Creating the handler starts the preview, which can also throw a RuntimeException.
           if (handler == null) {
             handler = new CaptureActivityHandler(this, decodeFormats, decodeHints, characterSet, cameraManager);
           }
           decodeOrStoreSavedBitmap(null, null);
         } catch (IOException ioe) {
           Log.w(TAG, ioe);
           displayFrameworkBugMessageAndExit();
         } catch (RuntimeException e) {
           // Barcode Scanner has seen crashes in the wild of this variety:
           // java.?lang.?RuntimeException: Fail to connect to camera service
           Log.w(TAG, "Unexpected error initializing camera", e);
           displayFrameworkBugMessageAndExit();
         }
       }

       private void displayFrameworkBugMessageAndExit() {
         AlertDialog.Builder builder = new AlertDialog.Builder(this);
         builder.setTitle(getString(R.string.app_name));
         builder.setMessage(getString(R.string.msg_camera_framework_bug));
         builder.setPositiveButton(R.string.button_ok, new FinishListener(this));
         builder.setOnCancelListener(new FinishListener(this));
         builder.show();
       }

       public void restartPreviewAfterDelay(long delayMS) {
         if (handler != null) {
           handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
         }
         resetStatusView();
       }

       private void resetStatusView() {
         resultView.setVisibility(View.GONE);
         statusView.setText(R.string.msg_default_status);
         statusView.setVisibility(View.VISIBLE);
         viewfinderView.setVisibility(View.VISIBLE);
         lastResult = null;
       }

       public void drawViewfinder() {
         viewfinderView.drawViewfinder();
       }
     }
