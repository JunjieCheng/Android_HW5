package cjunjie.hw5;

import android.support.v4.view.GestureDetectorCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * Created by chengjunjie on 11/26/16.
 */

public class TouchHandler implements OnTouchListener {

    private CanvasActivity canvasActivity;

    private GestureDetectorCompat gestureDetectorCompat;

    public TouchHandler(CanvasActivity canvasActivity) {
        this.canvasActivity = canvasActivity;
        this.gestureDetectorCompat = new GestureDetectorCompat(this.canvasActivity, new GestureListener(canvasActivity));
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int maskedAction = motionEvent.getActionMasked();
        gestureDetectorCompat.onTouchEvent(motionEvent);

        switch (maskedAction) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                for (int i = 0, size = motionEvent.getPointerCount(); i < size; i++) {
                    int id = motionEvent.getPointerId(i);
                    canvasActivity.addNewPath(id, motionEvent.getX(i), motionEvent.getY(i));
                }

                break;

            case MotionEvent.ACTION_MOVE:
                for (int i = 0, size = motionEvent.getPointerCount(); i < size; i++) {
                    int id = motionEvent.getPointerId(i);
                    canvasActivity.updatePath(id, motionEvent.getX(i), motionEvent.getY(i));
                }

                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                for (int i = 0, size = motionEvent.getPointerCount(); i < size; i++) {
                    int id = motionEvent.getPointerId(i);
                    canvasActivity.removePath(id);
                }

                break;
        }

        return true;
    }
}
