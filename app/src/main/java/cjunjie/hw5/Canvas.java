package cjunjie.hw5;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashMap;
import java.util.Stack;

/**
 * Created by chengjunjie on 11/26/16.
 */

public class Canvas extends View {

    HashMap<Integer, Path> activePath;
    HashMap<Integer, Stack<ColorPath>> allPaths;
    Stack<IconInfo> iconStack;
    Stack<Integer> stack;

    Paint pathPaint;
    Paint redPaint;
    Paint bluePaint;
    Paint greenPaint;

    Bitmap icon1;
    Bitmap icon2;

    public Canvas(Context context, AttributeSet attrs) {
        super(context, attrs);

        activePath = new HashMap<>();
        allPaths = new HashMap<>();
        iconStack = new Stack<>();
        stack = new Stack<>();

        redPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        redPaint.setColor(Color.RED);
        redPaint.setStyle(Paint.Style.STROKE);
        redPaint.setStrokeWidth(50);

        bluePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bluePaint.setColor(Color.BLUE);
        bluePaint.setStyle(Paint.Style.STROKE);
        bluePaint.setStrokeWidth(50);

        greenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        greenPaint.setColor(Color.GREEN);
        greenPaint.setStyle(Paint.Style.STROKE);
        greenPaint.setStrokeWidth(50);

        pathPaint = redPaint;

        icon1 = BitmapFactory.decodeResource(getResources(), R.drawable.icon1);
        icon1 = Bitmap.createScaledBitmap(icon1, 150, 150, false);
        icon2 = BitmapFactory.decodeResource(getResources(), R.drawable.icon2);
        icon2 = Bitmap.createScaledBitmap(icon2, 150, 150, false);
    }

    @Override
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);

        for (Stack<ColorPath> stack : allPaths.values()) {
            for (ColorPath path : stack) {
                canvas.drawPath(path.path, path.paint);
            }
        }

        for (IconInfo iconInfo : iconStack) {
            if (iconInfo.id == 100) {
                canvas.drawBitmap(icon1, iconInfo.x, iconInfo.y, null);
            }
            else {
                canvas.drawBitmap(icon2, iconInfo.x, iconInfo.y, null);
            }
        }
    }

    public void addPath(int id, float x, float y) {
        Path path = new Path();
        path.moveTo(x, y);
        activePath.put(id, path);

        if (allPaths.containsKey(id)) {
            allPaths.get(id).push(new ColorPath(path, pathPaint));
        } else {
            allPaths.put(id, new Stack<ColorPath>());
            allPaths.get(id).push(new ColorPath(path, pathPaint));
        }

        stack.push(id);
        invalidate();
    }

    public void updatePath(int id, float x, float y) {
        Path path = activePath.get(id);

        if (path != null) {
            path.lineTo(x, y);
        }

        invalidate();
    }

    public void removePath(int id) {
        if (activePath.containsKey(id)) {
            activePath.remove(id);
        }

        invalidate();
    }

    public void setPaintColor(int color) {
        switch (color) {
            case Color.RED:
                pathPaint = redPaint;
                break;
            case Color.BLUE:
                pathPaint = bluePaint;
                break;
            case Color.GREEN:
                pathPaint = greenPaint;
                break;
        }
    }

    public void clear() {
        allPaths.clear();
        iconStack.clear();
        invalidate();
    }

    public void undo() {
        if (!stack.isEmpty()) {
            allPaths.get(stack.pop()).pop();
            invalidate();
        }
    }

    public void onDoubleTap(float x, float y) {
        iconStack.push(new IconInfo(100, x - 75, y - 75));
    }

    public void onLongPress(float x, float y) {
        iconStack.push(new IconInfo(101, x - 75, y - 75));
    }

}
