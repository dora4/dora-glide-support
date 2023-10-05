package dora.lifecycle.activity

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.bumptech.glide.Glide

class GlideActivityLifecycle : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
        Glide.with(activity).resumeRequests()
    }

    override fun onActivityPaused(activity: Activity) {
        Glide.with(activity).pauseRequests()
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }
}