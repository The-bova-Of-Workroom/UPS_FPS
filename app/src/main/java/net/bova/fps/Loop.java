package net.bova.fps;

import android.graphics.Canvas;

import android.util.Log;

import android.view.SurfaceHolder;


public class Loop extends Thread {
    public static final double MAX_UPS = 60.0; //30.0;
    private static final double UPS_PERIOD = 1E+3 / MAX_UPS;

    private SurfaceHolder surfaceHolder;
    private View view;

    private boolean isRunning = false;
    private double averageUPS;
    private double averageFPS;

    public Loop(View view, SurfaceHolder surfaceHolder) {
        this.view= view;
        this.surfaceHolder= surfaceHolder;
    }

    @Override public void run() {
        Log.d("Loop.java", "run()");
        super.run();

        // Declare time and cycle count variables
        int updateCount = 0, frameCount = 0;

        long startTime, elapsedTime, sleepTime;

        // Game loop
        Canvas canvas = null;
        startTime= System.currentTimeMillis();
        while(isRunning) { // Try to update and render game
            try {
                    canvas= surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        view.update();
                        updateCount++;
                        view.draw(canvas);
                    }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();

            } finally {
                if(canvas != null) {
                    try {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                            frameCount++;
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            // Pause game loop to not exceed target UPS
            elapsedTime= System.currentTimeMillis() - startTime;
            sleepTime= (long)(updateCount * UPS_PERIOD - elapsedTime);
            if(sleepTime > 0) {
                try {
                        sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Skip frames to keep up with target UPS
            while(sleepTime < 0 && updateCount < MAX_UPS - 1) {
                view.update();
                updateCount++;
                elapsedTime= System.currentTimeMillis() - startTime;
                sleepTime= (long)(updateCount * UPS_PERIOD - elapsedTime);
            }

            // Calculate average UPS and FPS
            elapsedTime= System.currentTimeMillis() - startTime;
            if(elapsedTime >= 1000) {
                averageUPS= updateCount / (1E-3 * elapsedTime);
                averageFPS= frameCount / (1E-3 * elapsedTime);
                updateCount= frameCount= 0;
                startTime= System.currentTimeMillis();
            }
        }
    }

    public void startLoop() {
        Log.d("Loop.java", "startLoop()");
        isRunning= true;
        start();
    }

    public void stopLoop() {
        Log.d("Loop.java", "stopLoop()");
        isRunning= false;

        try {
                join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public double getAverageUPS() {
        return averageUPS;
    }

    public double getAverageFPS() {
        return averageFPS;
    }

}