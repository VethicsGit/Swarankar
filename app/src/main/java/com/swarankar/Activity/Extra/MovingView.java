package com.swarankar.Activity.Extra;

import android.app.ActionBar;
import android.view.Window;
import android.view.WindowManager;

import com.daimajia.androidanimations.library.Techniques;
import com.swarankar.R;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

/*

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.swarankar.R;
*/
public class MovingView extends AwesomeSplash {

    @Override
    public void initSplash(ConfigSplash configSplash) {

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);



        configSplash.setBackgroundColor(R.drawable.splash_back);
        configSplash.setRevealFlagX(Flags.REVEAL_LEFT);
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);


        configSplash.setLogoSplash(R.drawable.logo);
        configSplash.setAnimLogoSplashDuration(5000);
        configSplash.setAnimLogoSplashTechnique(Techniques.Bounce);





    }

    @Override
    public void animationsFinished() {

    }
}


/**
 15  * Created by barisemreefe on 05/01/15.
 16  */

/*

public class MovingView extends SurfaceView {

    private static final int X_AXIS = 0;

    private static final int Y_AXIS = 1;


    private Bitmap background;

    private Drawable backgroundDrawable;

    private int speed = 1;



    private int direction;


    private int firstImageY;

    private int followingImageY;


    private int firstImageX;

    private int followingImageX;



    public MovingView(Context context, int speed, Drawable backgroundDrawable) {
         super(context);
         this.speed = speed;
         this.backgroundDrawable = backgroundDrawable;
         init(context, null);

    }




    public MovingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }




    public MovingView(Context context, AttributeSet attrs, int defStyle) {
         super(context, attrs, defStyle);
         init(context, attrs);

    }





    @Override


    protected void onDraw(Canvas canvas) {
       super.onDraw(canvas);
       move(canvas);
       invalidate();

    }








    private void move(Canvas canvas) {
                 */
/**
        3          * Calculate new X,Y values by the help of speed ,delta,
        4          *//*

         switch (direction) {
             case X_AXIS:
                 firstImageX -= speed;
                 followingImageX = background.getWidth() - (-firstImageX);
                 if (followingImageX <= 0) {
                     firstImageX = 0;
                  canvas.drawBitmap(background, firstImageX, firstImageY, null);



                } else {
                    canvas.drawBitmap(background, firstImageX, firstImageY, null);
                    canvas.drawBitmap(background, followingImageX, followingImageY, null);

                }
                 break;
             case Y_AXIS:
                 firstImageY -= speed;
                 followingImageY = background.getHeight() - (-firstImageY);
                 if (followingImageY <= 0) {
                   firstImageY = 0;
                   canvas.drawBitmap(background, firstImageX, firstImageY, null);



                } else {
                     canvas.drawBitmap(background, firstImageX, firstImageY, null);
                     canvas.drawBitmap(background, followingImageX, followingImageY, null);

                }
                 break;



        }







    }


    private void init(Context context, AttributeSet attrs) {
                 */
/**
         98          * Get attributes from xml
         99          *//*

         if (attrs != null) {

            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MovingView, 0, 0);
             if (a.hasValue(R.styleable.MovingView_speed)) {
                 speed = a.getInt(R.styleable.MovingView_speed, 1);

            }
             if (a.hasValue(R.styleable.MovingView_src)) {
               backgroundDrawable = a.getDrawable(R.styleable.MovingView_src);

            }
             if (a.hasValue(R.styleable.MovingView_direction)) {
                 direction = a.getInt(R.styleable.MovingView_direction, 1);

            }
            try {
                 a.recycle();

            } catch (Exception e) {
            }

        }
      */
/**
        * Create a full-screen bitmap
        *//*




        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        final int width = display.getWidth();
        final int heigth = display.getHeight();

        background = Bitmap.createScaledBitmap(drawableToBitmap(backgroundDrawable), width, heigth, true);


        */
/**
         * @see https://groups.google.com/forum/?fromgroups#!topic/android-developers/oLccWfszuUo
         *     enable onDraw() for the view.
         *//*

        setWillNotDraw(false);


    }


    */
/**
          *
          *  https://stackoverflow.com/questions/3035692/how-to-convert-a-drawable-to-a-bitmap
          *

     *//*

    private Bitmap drawableToBitmap(Drawable drawable) {


        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }


        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);


        return bitmap;
    }


}
*/
