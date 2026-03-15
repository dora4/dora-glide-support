package dora.lifecycle.activity

import android.app.Activity
import android.app.Application
import android.os.Bundle

class GlideActivityLifecycle : Application.ActivityLifecycleCallbacks {

    override fun onActivityStarted(activity: Activity) {
        // 跟Glide内部一样，仅演示，不要加
//        Glide.with(activity).resumeRequests()
    }

    override fun onActivityStopped(activity: Activity) {
        // 跟Glide内部一样，仅演示，不要加
//        Glide.with(activity).pauseRequests()
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}
}