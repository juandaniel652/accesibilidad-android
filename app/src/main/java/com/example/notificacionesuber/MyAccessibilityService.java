package com.example.notificacionesuber;
import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.util.Log;

public class MyAccessibilityService extends AccessibilityService {
    private static final String TAG = "MyAccessibilityService";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event == null) return;
        CharSequence packageName = event.getPackageName();
        if (packageName == null) return;
        if (!packageName.toString().startsWith("com.ubercab")) return;

        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode != null) {
            parseUberInfo(rootNode);
            rootNode.recycle();
        }
    }

    private void parseUberInfo(AccessibilityNodeInfo node) {
        if (node == null) return;

        if (node.getText() != null) {
            String text = node.getText().toString().trim();
            if (!text.isEmpty()) {
                java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(
                    "^(\\w+)\\s+\\1.*?\\$\\s*([\\d.]+).*?A (\\d+) minutos[•·](\\d+:\\d+\\s*[ap]\\.m\\.)",
                    java.util.regex.Pattern.CASE_INSENSITIVE
                );
                java.util.regex.Matcher matcher = pattern.matcher(text);
                if (matcher.find()) {
                    String tipo    = matcher.group(1);
                    String precio  = matcher.group(2);
                    String minutos = matcher.group(3);
                    String hora    = matcher.group(4);
                    Log.d(TAG, "VIAJE | Tipo: " + tipo + " | Precio: $" + precio + " | Llega en: " + minutos + " min | Hora: " + hora);
                }
            }
        }

        for (int i = 0; i < node.getChildCount(); i++) {
            AccessibilityNodeInfo child = node.getChild(i);
            parseUberInfo(child);
            if (child != null) child.recycle();
        }
    }

    @Override
    public void onInterrupt() {}
}