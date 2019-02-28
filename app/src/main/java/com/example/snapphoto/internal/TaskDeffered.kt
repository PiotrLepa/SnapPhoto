package com.example.snapphoto.internal

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

fun <T> Task<T>.asDeferred(): Deferred<T> {
    val deferred = CompletableDeferred<T>()

    this.addOnSuccessListener { result ->
        deferred.complete(result)
    }

    this.addOnFailureListener { exception ->
        deferred.completeExceptionally(exception)
    }

    return deferred
}
//
//suspend fun <T> Task<T>.await(): T {
//    return suspendCoroutine { continuation ->
//        addOnCompleteListener { task ->
//            Timber.d("await: <T>: $<T>")
//            if (task.isSuccessful) continuation.resume(task.result)
//            else continuation.resumeWithException(task.exception)
//        }
//    }
//}
suspend fun <T> Task<T>.await(): T =
        suspendCancellableCoroutine { continuation ->
            addOnSuccessListener { result ->
                continuation.resume(result)
            }

            addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }

            addOnCanceledListener { continuation.cancel() }
        }