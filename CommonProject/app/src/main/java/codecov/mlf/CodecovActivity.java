package codecov.mlf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.loyangliu.android.commonproject.R;

public class CodecovActivity extends Activity {

    public static String TAG = "CodecovActivity";
    private static String DEFAULT_COVERAGE_FILE_PATH = "/mnt/sdcard/coverage.ec";

    private static String launchActivityName = "com.loyangliu.android.commonproject.MainActivity";
    private static Class<?> launchActivityClass;

    private String mCoverageFilePath;

    static {
        try {
            launchActivityClass = Class.forName(launchActivityName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codecov);

        Button startTest = (Button)findViewById(R.id.startTest);
        Button endTest = (Button)findViewById(R.id.endTest);

        startTest.setOnClickListener(onStartTestListener);
        endTest.setOnClickListener(onEndTestListener);

        File file = new File(DEFAULT_COVERAGE_FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Log.d(TAG, "create file execption:" + e);
                e.printStackTrace();
            }
        }

        mCoverageFilePath = DEFAULT_COVERAGE_FILE_PATH;
    }

    private String getCoverageFilePath() {
        if (mCoverageFilePath == null) {
            return DEFAULT_COVERAGE_FILE_PATH;
        } else {
            return mCoverageFilePath;
        }
    }

    private void generateCoverageReport() {
        Log.d(TAG, "generateCoverageReport():" + getCoverageFilePath());
        OutputStream out = null;
        try {
            out = new FileOutputStream(getCoverageFilePath(), true);
            Object agent = Class.forName("org.jacoco.agent.rt.RT")
                    .getMethod("getAgent")
                    .invoke(null);

            out.write((byte[]) agent.getClass().getMethod("getExecutionData", boolean.class)
                    .invoke(agent, false));
        } catch (Exception e) {
            Log.d(TAG, e.toString(), e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**********************************/
    private View.OnClickListener onStartTestListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(CodecovActivity.this, launchActivityClass);
            startActivity(intent);
        }
    };

    private View.OnClickListener onEndTestListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            generateCoverageReport();
        }
    };
}
