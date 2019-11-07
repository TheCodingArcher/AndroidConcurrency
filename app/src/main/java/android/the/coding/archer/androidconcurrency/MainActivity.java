package android.the.coding.archer.androidconcurrency;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract.Contacts;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "CodeRunner";
    private static final int REQUEST_CODE_PERMISSION = 14;

    ListView mListView;
    private boolean mPermissionGranted;

    private static final String[] PROJECTION = {
        Contacts._ID,
        Contacts.LOOKUP_KEY,
        Contacts.DISPLAY_NAME_PRIMARY
    };

    private static final String[] FROM_COLUMN = {
        Contacts.DISPLAY_NAME_PRIMARY
    };

    private static final int[] TO_VIEWS = {
        android.R.id.text1
    };

    private CursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = findViewById(R.id.list_view);

        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS);

        if (permission == PackageManager.PERMISSION_GRANTED) {
            mPermissionGranted = true;
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
            loadContactsData();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                        REQUEST_CODE_PERMISSION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            mPermissionGranted = true;
            loadContactsData();
        }
    }

    private void loadContactsData() {
        if (mPermissionGranted) {
            mCursorAdapter = new SimpleCursorAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    null,
                    FROM_COLUMN,
                    TO_VIEWS,
                    0
            );
            mListView.setAdapter(mCursorAdapter);
            getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new CursorLoader(
                this,
                Contacts.CONTENT_URI,
                PROJECTION,
                null,
                null,
                Contacts.DISPLAY_NAME_PRIMARY
        );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
