/*
 * MIT License
 *
 * Copyright (c) 2017 Yuriy Budiyev [yuriy.budiyev@yandex.ru]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.budiyev.android.imageloader;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

final class Callbacks<T> {
    private final LoadCallback<T> mLoadCallback;
    private final ErrorCallback<T> mErrorCallback;

    public Callbacks(@Nullable LoadCallback<T> loadCallback,
            @Nullable ErrorCallback<T> errorCallback) {
        mLoadCallback = loadCallback;
        mErrorCallback = errorCallback;
    }

    @Nullable
    public LoadCallback<T> getLoadCallback() {
        return mLoadCallback;
    }

    @Nullable
    public ErrorCallback<T> getErrorCallback() {
        return mErrorCallback;
    }

    @WorkerThread
    public static <T> void notifyLoaded(@Nullable Callbacks<T> callbacks, @NonNull T data,
            @NonNull Bitmap image) {
        if (callbacks == null) {
            return;
        }
        LoadCallback<T> callback = callbacks.mLoadCallback;
        if (callback == null) {
            return;
        }
        callback.onLoaded(data, image);
    }

    @WorkerThread
    public static <T> void notifyError(@Nullable Callbacks<T> callbacks, @NonNull T data,
            @NonNull Throwable error) {
        if (callbacks == null) {
            return;
        }
        ErrorCallback<T> callback = callbacks.mErrorCallback;
        if (callback == null) {
            return;
        }
        callback.onError(data, error);
    }
}
