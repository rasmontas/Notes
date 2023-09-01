package lt.rimas.notes;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private String message;
    private String tag;

    public BaseActivity(String message, String tag) {
        this.message = message;
        this.tag = tag;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        print("onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        print("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        print("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        print("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        print("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        print("onDestroy");
    }

    public void print(String lifecycle){
        Log.i(tag,
                "************" + "\n*" +
                message + "\n*" +
                        lifecycle + "\n" +
                        "************"
        );
    }
}
