package com.caster.notes.dsl.common.test

import com.nhaarman.mockito_kotlin.given
import io.reactivex.*
import org.mockito.BDDMockito

infix fun <T> T?.willReturn(value: T): BDDMockito.BDDMyOngoingStubbing<T?> =
    given(this).willReturn(value)

infix fun <T> Flowable<T>?.willReturn(
    value: T
): BDDMockito.BDDMyOngoingStubbing<Flowable<T>?> = given(this).willReturn(Flowable.just(value))!!

infix fun <T> Maybe<T>?.willReturn(
    value: T
): BDDMockito.BDDMyOngoingStubbing<Maybe<T>?> = given(this).willReturn(Maybe.just(value))

infix fun <T> Observable<T>?.willReturn(
    value: T
): BDDMockito.BDDMyOngoingStubbing<Observable<T>?> = given(this).willReturn(Observable.just(value))

infix fun <T> Single<T>?.willReturn(
    value: T
): BDDMockito.BDDMyOngoingStubbing<Single<T>?> = given(this).willReturn(Single.just(value))

infix fun <T> Single<T>?.willReturnError(
    value: Throwable
): BDDMockito.BDDMyOngoingStubbing<Single<T>?> = given(this).willReturn(Single.error(value))

infix fun <T> Observable<T>?.willReturnError(
    value: Throwable
): BDDMockito.BDDMyOngoingStubbing<Observable<T>?> = given(this).willReturn(Observable.error(value))

infix fun <T> Flowable<T>?.willReturnError(
    value: Throwable
): BDDMockito.BDDMyOngoingStubbing<Flowable<T>?> = given(this).willReturn(Flowable.error(value))

infix fun Completable?.willReturnError(
    value: Throwable
): BDDMockito.BDDMyOngoingStubbing<Completable?> = given(this).willReturn(Completable.error(value))
