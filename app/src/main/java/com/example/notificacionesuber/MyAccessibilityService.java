package com.example.notificacionesuber;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.util.Log;

public class MyAccessibilityService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        AccessibilityNodeInfo rootNode = getRootInActiveWindow();

        if (rootNode == null) return;

        String texto = rootNode.getText() != null ? rootNode.getText().toString() : "";

        if(texto.contains("$")) {
            Log.d("UBER", "Posible tarifa detectada: " + texto);
        }

    }

    @Override
    public void onInterrupt() {
    }
}