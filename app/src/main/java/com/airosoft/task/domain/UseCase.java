package com.airosoft.task.domain;

public abstract class UseCase<Q extends UseCase.RequestData, R extends UseCase.ResponseData> {

    private Q requestData;

    private UseCase.Callback<R> useCaseCallback;

    public void run() {
        executeUseCase(requestData);
    }

    public abstract void executeUseCase(Q requestData);

    public Q getRequestData() {
        return requestData;
    }

    public void setRequestData(Q requestData) {
        this.requestData = requestData;
    }

    public Callback<R> getUseCaseCallback() {
        return useCaseCallback;
    }

    public void setUseCaseCallback(Callback<R> useCaseCallback) {
        this.useCaseCallback = useCaseCallback;
    }

    public interface Callback<P> {
        void onSuccess(P response);
        void onError();
    }

    public interface RequestData {

    }

    public interface ResponseData {

    }
}
