package com.caroline.android.udacitycapstoneproject.concurrency;

import android.os.Handler;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

public class UiExecutor implements Executor {

    private final Handler mainLooperHandler;

    public UiExecutor(Handler mainLooperHandler) {
        this.mainLooperHandler = mainLooperHandler;
    }

    @Override
    public void execute(@NonNull Runnable command) {
        mainLooperHandler.post(command);
    }
}
