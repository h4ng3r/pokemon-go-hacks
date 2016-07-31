package com.nianticlabs.pokemongo.hacks;

import android.util.Log;

import java.security.cert.X509Certificate;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;


public class PokemonGoHacks implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        if(lpparam.packageName.equals("com.nianticlabs.pokemongo")) {
            XposedBridge.log("Loaded app: " + lpparam.packageName);

            findAndHookMethod("com.nianticlabs.nia.network.NianticTrustManager", lpparam.classLoader,
                    "checkClientTrusted", X509Certificate[].class, String.class,
                    new XC_MethodReplacement() {
                        @Override
                        protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                            Log.d("POKEMON-GO-HACKS", "SSL Pinning Bypassed (called checkClientTrusted)");
                            return null;
                        }
                    }
            );

            findAndHookMethod("com.nianticlabs.nia.network.NianticTrustManager", lpparam.classLoader,
                    "checkServerTrusted", X509Certificate[].class, String.class,
                    new XC_MethodReplacement() {
                        @Override
                        protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                            Log.d("POKEMON-GO-HACKS", "SSL Pinning Bypassed (called checkServerTrusted)");
                            return null;
                        }
                    }
            );
        }
    }
}
