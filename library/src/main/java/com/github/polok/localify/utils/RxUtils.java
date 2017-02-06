/**
 * Copyright 2015 Marcin Polak
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.polok.localify.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

import com.github.polok.localify.LocalifyCallback;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 *
 */
public final class RxUtils {

    /**
     * @param executor
     * @param observable
     * @param callback
     * @param <R>
     * @return
     */
    public static <R> LocalifyCallback<R> subscribe(final Executor executor, Observable<R> observable, final LocalifyCallback<R> callback) {
        observable
                .subscribe(new Consumer<R>() {
                    @Override
                    public void accept(final R r) throws Exception {
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                callback.onSuccess(r);
                            }
                        });
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(final Throwable throwable) throws Exception {
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                callback.onError(throwable);
                            }
                        });
                    }
                });

        return callback;
    }

    /**
     * @param <T>
     */
    public abstract static class DefFunc<T> implements Callable<Observable<T>> {
        @Override
        public final Observable<T> call() {
            return Observable.just(method());
        }

        /**
         * @return
         */
        public abstract T method();
    }

    /**
     * @param func
     * @param <R>
     * @return
     */
    public static <R> Observable<R> defer(DefFunc<R> func) {
        return Observable.defer(func).subscribeOn(Schedulers.io());
    }

}
