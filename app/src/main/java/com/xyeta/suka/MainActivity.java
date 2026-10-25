package com.xyeta.suka;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    private static final String KEY_URL = "https://t.me/leontapkey_off";

    private FrameLayout root;
    private LinearLayout menu;
    private LinearLayout content;

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
        panel.setBackgroundColor(0xEE222222);
        panel.setPadding(dp(12), dp(12), dp(12), dp(12));

        TextView title = new TextView(this);
        title.setText("@leontapkey_off");
        title.setGravity(Gravity.CENTER);
        title.setTextColor(0xFFFFFFFF);
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        title.setPadding(dp(8), dp(12), dp(8), dp(12));
        title.setBackgroundColor(0xFF444444);
        title.setOnTouchListener(new DragListener(panel));
        panel.addView(title);

        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(0, dp(12), 0, 0);
        panel.addView(content);
        showLogin();

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                dp(300),
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.TOP | Gravity.START);
        params.setMargins(dp(16), dp(80), 0, 0);
        panel.setLayoutParams(params);
        return panel;
    }

    private void showLogin() {
        content.removeAllViews();

        TextView label = text("Введите ключ", 16);
        content.addView(label);

        final EditText keyInput = new EditText(this);
        keyInput.setHint("Ключ");
        keyInput.setHintTextColor(0xFF888888);
        keyInput.setTextColor(0xFFFFFFFF);
        content.addView(keyInput);

        Button getKey = button("Получить ключ");
        getKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(KEY_URL)));
            }
        });
        content.addView(getKey);

        Button login = button("Войти");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMain();
            }
        });
        content.addView(login);
    }

    private void showMain() {
        content.removeAllViews();

        content.addView(text("Добро пожаловать!", 16));

        CheckBox test = new CheckBox(this);
        test.setText("Test");
        test.setTextColor(0xFFFFFFFF);
        content.addView(test);
    }

    private TextView text(String value, int sizeSp) {
        TextView view = new TextView(this);
        view.setText(value);
        view.setTextColor(0xFFFFFFFF);
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeSp);
        view.setPadding(0, dp(8), 0, dp(8));
        return view;
    }

    private Button button(String value) {
        Button view = new Button(this);
        view.setText(value);
        view.setAllCaps(false);
        return view;
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
