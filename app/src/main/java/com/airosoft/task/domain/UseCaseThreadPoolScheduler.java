package com.airosoft.task.domain;

import android.os.Handler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class UseCaseThreadPoolScheduler implements UseCaseScheduler {

    private Handler handler = new Handler();

    private static final int CORE_SIZE = Runtime.getRuntime().availableProcessors();
    private static final int MAX_SIZE = CORE_SIZE * 2;
    private static final int ALIVE = 20;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    private static final ArrayBlockingQueue<Runnable> WORK_ARRAY = new ArrayBlockingQueue<>(CORE_SIZE);

    private ThreadPoolExecutor threadPoolExecutor;

    public UseCaseThreadPoolScheduler() {
        threadPoolExecutor = new ThreadPoolExecutor(CORE_SIZE, MAX_SIZE, ALIVE, TIME_UNIT, WORK_ARRAY);
    }

    @Override
    public void execute(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }

    @Override
    public <R extends UseCase.ResponseData> void notifyResponse(final R response, final UseCase.Callback<R> useCaseCallback) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                useCaseCallback.onSuccess(response);
            }
        });
    }

    @Override
    public <R extends UseCase.ResponseData> void notifyError(final UseCase.Callback<R> useCaseCallback) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                useCaseCallback.onError();
            }
        });
    }
}
