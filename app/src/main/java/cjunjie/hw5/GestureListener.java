package cjunjie.hw5;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by chengjunjie on 11/26/16.
 */

public class GestureListener extends GestureDetector.SimpleOnGestureListener {

    private CanvasActivity canvasActivity;

    public GestureListener(CanvasActivity canvasActivity) {
        this.canvasActivity = canvasActivity;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        canvasActivity.onDoubleTap(e.getX(), e.getY());
        return super.onDoubleTap(e);
    }

    @Override
    public void onLongPress(MotionEvent e) {
        canvasActivity.onLongPress(e.getX(), e.getY());
        super.onLongPress(e);
    }
}
