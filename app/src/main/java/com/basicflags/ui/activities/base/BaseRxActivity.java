package com.basicflags.ui.activities.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.basicflags.module.DependencyInjection;
import com.basicflags.service.RxApiService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lombok.Getter;
import lombok.experimental.Accessors;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Nick Unuchek (skype: kolyall) on 06.10.2016.
 */
@Accessors(prefix = "m")
public abstract class BaseRxActivity extends AppCompatActivity {
    private CompositeSubscription mSubscriptions;
    @Getter @Inject RxApiService mRxApiService;

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscriptions = new CompositeSubscription();
        DependencyInjection.inject(this);
    }

    @CallSuper
    @Override
    protected void onStart() {
        super.onStart();
        subscribe();
    }

    private void subscribe() {
        for (Subscription subscription : createSubscriptions()) {
            mSubscriptions.add(subscription);
        }
    }

    @CallSuper
    @Override
    protected void onStop() {
        super.onStop();
        unsubscribeAll();
    }

    protected void unsubscribeAll() {
        if (mSubscriptions == null) { return; }
        mSubscriptions.clear();
        mSubscriptions = new CompositeSubscription();
    }

    protected void unsubscribe(Subscription subscription) {
        if (subscription == null || mSubscriptions == null) { return; }
        mSubscriptions.remove(subscription);
    }

    public Subscription addSubscription(Subscription subscription) {
        mSubscriptions.add(subscription);
        return subscription;
    }

    /**
     * Subscribe to the {@link Observable}s that should be started on {@link android.support.v4.app .Fragment#onActivityCreated} and then
     * return the subscriptions that you want attached to this fragment's lifecycle. Each {@link Subscription} will have {@link
     * Subscription#unsubscribe() unsubscribe()} called when {@link android.support.v4.app.Fragment#onDetach() onDetach()} is fired.
     * <p>The default implementation returns an empty array.</p>
     */
    protected List<Subscription> createSubscriptions() {
        return new ArrayList<>(0);
    }

    // quick subscriptions

    protected <T> Subscription createAndAddSubscription(Observable<T> observable, Observer<T> observer) {
        return addSubscription(bindObservable(observable, observer));
    }
    protected <T> Subscription createAndAddSubscription(Observable<T> observable) {
        return addSubscription(bindObservable(observable));
    }

    private  <T> Subscription bindObservable(Observable<T> observable, Observer<T> observer) {
        return observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        doOnError(throwable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        doOnSubscribe();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        doOnCompleted();
                    }
                })
                .subscribe(observer);
    }

    public  <T> Observable<Boolean> bindOnNextAction(Observable<T> observable,Action1<T> onNextAction) {
        return observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).doOnNext(onNextAction)
                .map(new Func1<T, Boolean>() {
                    @Override
                    public Boolean call(T t) {
                        return true;
                    }
                });
    }

    public  <T> Subscription bindObservable(Observable<T> observable) {
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onNext(T t) {

            }
        };
        return bindObservable(observable,observer);
    }

    public abstract void doOnSubscribe();
    public abstract void doOnError(Throwable throwable);
    public abstract void doOnCompleted();
}