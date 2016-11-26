package cjunjie.hw5;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

/**
 * Created by chengjunjie on 11/26/16.
 */

public class CanvasActivity extends AppCompatActivity implements View.OnClickListener {

    static final String IMAGE = "Image";

    private Canvas canvas;
    private TouchHandler touchHandler;

    private Button startButton;
    private FrameLayout canvasLayout;

    private Button redButton;
    private Button blueButton;
    private Button greenButton;
    private Button undoButton;
    private Button clearButton;
    private Button doneButton;

    private Bitmap image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        canvas = (Canvas) findViewById(R.id.canvas);
        touchHandler = new TouchHandler(this);
        canvas.setOnTouchListener(touchHandler);

        startButton = (Button) findViewById(R.id.start_button);
        canvasLayout = (FrameLayout) findViewById(R.id.canvas_layout);

        startButton.setVisibility(View.GONE);
        canvasLayout.setVisibility(View.VISIBLE);

        redButton = (Button) findViewById(R.id.red_button);
        blueButton = (Button) findViewById(R.id.blue_button);
        greenButton = (Button) findViewById(R.id.green_button);
        undoButton = (Button) findViewById(R.id.undo_button);
        clearButton = (Button) findViewById(R.id.clear_button);
        doneButton = (Button) findViewById(R.id.done_button);

        redButton.setOnClickListener(this);
        blueButton.setOnClickListener(this);
        greenButton.setOnClickListener(this);

        undoButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        doneButton.setOnClickListener(this);

        Intent intent = getIntent();
        image = BitmapFactory.decodeFile(intent.getStringExtra(IMAGE));
        canvas.setBackground(new BitmapDrawable(getResources(), image));
    }

    public void addNewPath(int id, float x, float y) {
        canvas.addPath(id, x, y);
    }

    public void updatePath(int id, float x, float y) {
        canvas.updatePath(id, x, y);
    }

    public void removePath(int id) {
        canvas.removePath(id);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.red_button:
                canvas.setPaintColor(Color.RED);
                break;
            case R.id.blue_button:
                canvas.setPaintColor(Color.BLUE);
                break;
            case R.id.green_button:
                canvas.setPaintColor(Color.GREEN);
                break;
            case R.id.undo_button:
                canvas.undo();
                break;
            case R.id.clear_button:
                canvas.clear();
                break;
            case R.id.done_button:
                finish();
                break;
        }
    }

    public void onDoubleTap(float x, float y) {
        canvas.onDoubleTap(x, y);
    }

    public void onLongPress(float x, float y) {
        canvas.onLongPress(x, y);
    }
}
