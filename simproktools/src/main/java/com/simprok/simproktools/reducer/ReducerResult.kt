
//
//  ReducerResult.kt
//  simproktools
//
//  Created by Andrey Prokhorenko on 12.03.2022.
//  Copyright (c) 2022 simprok. All rights reserved.

package com.simprok.simproktools.reducer

/**
 * A type that represents a behavior of `ReducerMachine`.
 */
sealed interface ReducerResult<T> {

    /**
     * Returning this value from `ReducerMachine`'s `reducer`
     * method ensures that the state *will* be changed and emitted.
     */
    class Set<T>(val value: T) : ReducerResult<T>

    /**
     * Returning this value from `ReducerMachine`'s `reducer`
     * method ensures that the state *won't* be changed and emitted .
     */
    class Skip<T> : ReducerResult<T>
}
