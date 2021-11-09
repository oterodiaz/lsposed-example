/*
 * NOTE: Parts of this code are licensed differently from the rest of the project.
 * See the LICENSE file for more information.
 */
package com.example.statusbarmod;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import android.graphics.Color;
import android.widget.TextView;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class StatusbarMod implements IXposedHookLoadPackage {
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.android.systemui")) // Only run the module on SystemUI
            return;

        XposedBridge.log("(StatusbarMod) Loaded app: " + lpparam.packageName);
        findAndHookMethod("com.android.systemui.statusbar.policy.Clock", lpparam.classLoader, "updateClock", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                TextView tv = (TextView) param.thisObject;
                String text = tv.getText().toString();
                tv.setText(String.format("The time is %s", text));
                tv.setTextColor(Color.GREEN);
            }
        });

        XposedBridge.log("(StatusbarMod) Clock modified");
    }
}