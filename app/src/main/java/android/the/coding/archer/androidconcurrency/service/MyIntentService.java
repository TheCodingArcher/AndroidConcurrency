package android.the.coding.archer.androidconcurrency.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "android.the.coding.archer.androidconcurrency.service.action.FOO";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "android.the.coding.archer.androidconcurrency.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "android.the.coding.archer.androidconcurrency.service.extra.PARAM2";
    public static final String TAG = "CodeRunner";
    public static final String SERVICE_MESSAGE = "Service Message";
    public static final String MESSAGE_KEY = "message";

    public MyIntentService() {
        super("MyIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        sendMessage("handleActionFoo: Service Started");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sendMessage("handleActionFoo: Service Finished");
    }

    @Override
    public void onCreate() {
        sendMessage("onCreate");
    }

    @Override
    public void onDestroy() {
        sendMessage("onDestroy");
    }

    private void sendMessage(String message) {
        Intent intent = new Intent(SERVICE_MESSAGE);
        intent.putExtra(MESSAGE_KEY, message);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }
}
