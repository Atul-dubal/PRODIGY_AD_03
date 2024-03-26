package apcoders.in.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView timerTextView;
    private Button startButton;
    private Button pauseButton;
    private Button resetButton;

    private boolean isTimerRunning = false;
    private int milliseconds = 0;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.timerTextView);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        resetButton = findViewById(R.id.resetButton);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                milliseconds += 10;
                int seconds = (milliseconds / 1000) % 60;
                int minutes = (milliseconds / 1000) / 60;

                String timerText = String.format("%02d:%02d:%03d", minutes, seconds, milliseconds % 1000);
                timerTextView.setText(timerText);

                handler.postDelayed(this, 10);
            }
        };

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isTimerRunning) {
                    isTimerRunning = true;
                    handler.postDelayed(runnable, 10);
                }
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTimerRunning) {
                    isTimerRunning = false;
                    handler.removeCallbacks(runnable);
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTimerRunning = false;
                milliseconds = 0;
                timerTextView.setText("00:00:000");
                handler.removeCallbacks(runnable);
            }
        });
    }
}
