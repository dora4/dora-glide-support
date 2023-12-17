package dora.lifecycle.config

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager
import dora.lifecycle.activity.GlideActivityLifecycle
import dora.lifecycle.application.ApplicationLifecycleCallbacks

class GlideGlobalConfig : GlobalConfig {

    override fun injectApplicationLifecycle(
        context: Context,
        lifecycles: MutableList<ApplicationLifecycleCallbacks>
    ) {
    }

    override fun injectActivityLifecycle(
        context: Context,
        lifecycles: MutableList<Application.ActivityLifecycleCallbacks>
    ) {
        lifecycles.add(GlideActivityLifecycle())
    }

    override fun injectFragmentLifecycle(
        context: Context,
        lifecycles: MutableList<FragmentManager.FragmentLifecycleCallbacks>
    ) {
    }
}