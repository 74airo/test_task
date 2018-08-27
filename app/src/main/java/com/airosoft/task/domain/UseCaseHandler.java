package com.airosoft.task.domain;

public class UseCaseHandler {
    private static UseCaseHandler instance;

    private final UseCaseScheduler useCaseScheduler;

    private UseCaseHandler(UseCaseScheduler useCaseScheduler) {
        this.useCaseScheduler = useCaseScheduler;
    }

    public static UseCaseHandler getInstance() {
        if (instance == null) {
            instance = new UseCaseHandler(new UseCaseThreadPoolScheduler());
        }
        return instance;
    }

    public <Q extends UseCase.RequestData, P extends UseCase.ResponseData> void execute(
            final UseCase<Q,P> useCase, Q requestData, UseCase.Callback<P> useCaseCallback) {
        useCase.setRequestData(requestData);
        useCase.setUseCaseCallback(new UiCallback<>(this, useCaseCallback));

        useCaseScheduler.execute(new Runnable() {
            @Override
            public void run() {
                useCase.run();
            }
        });
    }

    public <R extends UseCase.ResponseData> void notifyResponse(
            final R responseData, final UseCase.Callback<R> useCaseCallback) {
        useCaseScheduler.notifyResponse(responseData, useCaseCallback);
    }

    public <R extends UseCase.ResponseData> void notifyError(
            final UseCase.Callback<R> useCaseCallback) {
        useCaseScheduler.notifyError(useCaseCallback);
    }

    private static final class UiCallback<T extends UseCase.ResponseData> implements UseCase.Callback<T> {

        private UseCaseHandler useCaseHandler;
        private UseCase.Callback<T> useCaseCallback;

        public UiCallback(UseCaseHandler useCaseHandler,
                          UseCase.Callback<T> useCaseCallback) {
            this.useCaseHandler = useCaseHandler;
            this.useCaseCallback = useCaseCallback;
        }

        @Override
        public void onSuccess(T response) {
            useCaseHandler.notifyResponse(response, useCaseCallback);
        }

        @Override
        public void onError() {
            useCaseHandler.notifyError(useCaseCallback);
        }
    }
}
