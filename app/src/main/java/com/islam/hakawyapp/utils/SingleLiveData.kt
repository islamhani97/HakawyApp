package com.islam.hakawyapp.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveData<T> {
    private val liveData = MutableLiveData<T?>()
    private val pending = AtomicBoolean(false)

    fun observe(owner: LifecycleOwner?, observer: Observer<T?>) {
        liveData.observe(owner!!, { t ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    fun setValue(t: T?) {
        pending.set(true)
        liveData.value = t
    }
}