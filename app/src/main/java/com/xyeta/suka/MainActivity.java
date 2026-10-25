package com.xyeta.suka;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    private FrameLayout root;
    private LinearLayout menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        root = findViewById(R.id.root);
        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMenu();
            }
        });
    }

    private void toggleMenu() {
        if (menu == null) {
            menu = createMenu();
            root.addView(menu);
            return;
        }
        menu.setVisibility(menu.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

    private LinearLayout createMenu() {
        LinearLayout panel = new LinearLayout(this);
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setBackgroundColor(0xCC222222);
        panel.setPadding(dp(8), dp(8), dp(8), dp(8));

        TextView title = new TextView(this);
        title.setText("Меню (тяни за заголовок)");
        title.setTextColor(0xFFFFFFFF);
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        title.setPadding(dp(8), dp(8), dp(8), dp(8));
        title.setBackgroundColor(0xFF444444);
        panel.addView(title);
        title.setOnTouchListener(new DragListener(panel));

        CheckBox test = new CheckBox(this);
        test.setText("Test");
        test.setTextColor(0xFFFFFFFF);
        panel.addView(test);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.TOP | Gravity.START);
        params.setMargins(dp(16), dp(80), 0, 0);
        panel.setLayoutParams(params);
        return panel;
    }

    private int dp(int value) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics()));
    }

    /** Moves the panel while the user drags its title bar. */
    private static class DragListener implements View.OnTouchListener {
        private final View target;
        private float offsetX;
        private float offsetY;

        DragListener(View target) {
            this.target = target;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    offsetX = target.getX() - event.getRawX();
                    offsetY = target.getY() - event.getRawY();
                    return true;
                case MotionEvent.ACTION_MOVE:
                    target.setX(event.getRawX() + offsetX);
                    target.setY(event.getRawY() + offsetY);
                    return true;
                default:
                    return false;
            }
        }
    }
}
