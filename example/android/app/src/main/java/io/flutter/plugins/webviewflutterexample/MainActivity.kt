package io.flutter.plugins.webviewflutterexample

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.android.TransparencyMode
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

class MainActivity : FragmentActivity() {
    // Declare a local variable to reference the FlutterFragment so that you
    // can forward calls to it later.
    private var flutterFragment1: FlutterFragment? = null
    private var flutterFragment2: FlutterFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // WebViewFlutterPlugin.registerWith(PluginRegistry.registrarFor("io.flutter.plugins.webviewflutter.WebViewFlutterPlugin"));

        // Inflate a layout that has a container for your FlutterFragment.
        // For this example, assume that a FrameLayout exists with an ID of
        // R.id.fragment_container.
        setContentView(R.layout.my_activity_layout)

        // Get a reference to the Activity's FragmentManager to add a new
        // FlutterFragment, or find an existing one.
        val fragmentManager = supportFragmentManager

        // Attempt to find an existing FlutterFragment,
        // in case this is not the first time that onCreate() was run.
        flutterFragment1 = fragmentManager
                .findFragmentByTag(TAG_FLUTTER_FRAGMENT) as FlutterFragment?
        flutterFragment2 = fragmentManager
                .findFragmentByTag(TAG_FLUTTER_FRAGMENT) as FlutterFragment?
        val flutterEngine1 = FlutterEngine(this)
        val flutterEngine2 = FlutterEngine(this)
        flutterEngine1.dartExecutor.executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
        )
        flutterEngine2.dartExecutor.executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
        )
        FlutterEngineCache
                .getInstance()
                .put("engine1", flutterEngine1)
        FlutterEngineCache
                .getInstance()
                .put("engine2", flutterEngine2)

        // Create and attach a FlutterFragment if one does not exist.
        if (flutterFragment1 == null) {
            // flutterFragment = FlutterFragment.withNewEngine().shouldAttachEngineToActivity(false).build();
            flutterFragment1 = FlutterFragment
                    .withCachedEngine("engine1")
                    .transparencyMode(TransparencyMode.opaque)
                    .build()
            //            new MethodChannel(
//                    flutterEngine.getDartExecutor().getBinaryMessenger(),
//                    "INSERT_YOUR_DESIRED_METHOD_CHANNEL_NAME_HERE"
//            ).invokeMethod("setRoute", "INSERT_ROUTE_YOU_WANT_HERE");
            fragmentManager
                    .beginTransaction()
                    .add(
                            R.id.flutter_container1,
                            flutterFragment1!!,
                            TAG_FLUTTER_FRAGMENT
                    )
                    .commit()
        }
        if (flutterFragment2 == null) {
            // flutterFragment = FlutterFragment.withNewEngine().shouldAttachEngineToActivity(false).build();
            flutterFragment2 = FlutterFragment
                    .withCachedEngine("engine2")
                    .transparencyMode(TransparencyMode.opaque)
                    .build()
            //            new MethodChannel(
//                    flutterEngine.getDartExecutor().getBinaryMessenger(),
//                    "INSERT_YOUR_DESIRED_METHOD_CHANNEL_NAME_HERE"
//            ).invokeMethod("setRoute", "INSERT_ROUTE_YOU_WANT_HERE");
            fragmentManager
                    .beginTransaction()
                    .add(
                            R.id.flutter_container2,
                            flutterFragment2!!,
                            TAG_FLUTTER_FRAGMENT
                    )
                    .commit()
        }
    }

    companion object {
        // Define a tag String to represent the FlutterFragment within this
        // Activity's FragmentManager. This value can be whatever you'd like.
        private const val TAG_FLUTTER_FRAGMENT = "flutter_fragment"
    }
}