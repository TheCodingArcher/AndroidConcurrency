package android.the.coding.archer.androidconcurrency;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class MyJobService extends JobService {

    public static final String TAG = "CodeRunner";

    public MyJobService() {
    }

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        Log.i(TAG, "onStartJob: " + jobParameters.getJobId());

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "run: job completed");

                LocalBroadcastManager.getInstance(getApplicationContext())
                        .sendBroadcast(new Intent("ServiceMessage"));

                jobFinished(jobParameters, false);
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.i(TAG, "onStopJob: " + jobParameters.getJobId());
        return false;
    }

}
