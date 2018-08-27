package com.airosoft.task.domain;

public interface UseCaseScheduler {
    void execute(Runnable runnable);

    <R extends UseCase.ResponseData> void notifyResponse(R response, UseCase.Callback<R> useCaseCallback);

    <R extends UseCase.ResponseData> void notifyError(UseCase.Callback<R> useCaseCallback);
}
