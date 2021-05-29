package net.bova.fps;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Paint;

import android.util.Log;

import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import androidx.core.content.ContextCompat;


public class View extends SurfaceView implements SurfaceHolder.Callback {
    private Loop loop;
    private Context context;

    private int x;


    public View(Context context) {
        super(context);

        this.context= context;
        x= 40;

        // Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        loop= new Loop(this, surfaceHolder);

        setFocusable(true);
    }

    @Override public void surfaceCreated(@NonNull SurfaceHolder Holder) {
        Log.d("Game.java", "surfaceCreated()");

        if (loop.getState().equals(Thread.State.TERMINATED)) {
            SurfaceHolder surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);
            loop= new Loop(this, surfaceHolder);
        }

        loop.startLoop();

    }

    @Override public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override public void draw(Canvas canvas) {
        super.draw(canvas);

        String ups = Double.toString(loop.getAverageUPS());
        String fps = Double.toString(loop.getAverageFPS());
        Paint p = new Paint();
        int c = ContextCompat.getColor(context,R.color.white);

        p.setColor(c);
        p.setTextSize(40);
        canvas.drawText("UPS : " + ups,40,40,p);
        canvas.drawText("FPS : " + fps,40,100,p);

        canvas.drawText("TEST",x,300,p);
    }

    public void update() {
        x++;
        if (x > 1000) x= 0;
    }

    public void pause() {
        loop.stopLoop();
    }


}
