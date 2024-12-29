package com.example.test_wi;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.plugin.common.MethodChannel;
import androidx.annotation.NonNull;

public class MainActivity extends FlutterActivity {
    private static final String CHANNEL = "com.example.test_wi/widget";

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new MethodChannel(getFlutterEngine().getDartExecutor().getBinaryMessenger(), CHANNEL)
                .setMethodCallHandler((call, result) -> {
                    if (call.method.equals("updateWidget")) {
                        String text = call.argument("text");
                        updateWidgetText(text);
                        result.success(null);
                    } else {
                        result.notImplemented();
                    }
                });
    }

    private void updateWidgetText(String text) {
        // Update widget logic here
    }
}
