package android.the.coding.archer.androidconcurrency;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CodeRunner";

    // View object references
    private ScrollView mScroll;
    private TextView mLog;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the logging components
        mScroll = findViewById(R.id.scrollLog);
        mLog = findViewById(R.id.tvLog);
        mProgressBar = findViewById(R.id.progress_bar);

        mLog.setText(R.string.lorem_ipsum);
    }

    //  Run some code, called from the onClick event in the layout file
    public void runCode(View v) {
        MyTask myTask = new MyTask();
        myTask.execute("Red", "Green", "Blue");

        // NB: we can't run the same task more than once
        // it will result in crash.
        // However, we can create multiple object of same AsyncTask
        // and run one after another
        MyTask myTask2 = new MyTask();
        myTask2.execute("Bruce", "Nightwing", "Robin", "Jason");
    }

    //  Clear the output, called from the onClick event in the layout file
    public void clearOutput(View v) {
        mLog.setText("");
        scrollTextToEnd();
    }

    //  Log output to logcat and the screen
    private void log(String message) {
        Log.i(TAG, message);
        mLog.append(message + "\n");
        scrollTextToEnd();
    }

    private void scrollTextToEnd() {
        mScroll.post(new Runnable() {
            @Override
            public void run() {
                mScroll.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    @SuppressWarnings("unused")
    private void displayProgressBar(boolean display) {
        if (display) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            for (String value :
                    strings) {
                Log.i(TAG, "doInBackground: " + value);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
