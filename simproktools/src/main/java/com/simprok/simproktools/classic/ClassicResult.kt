//
//  ClassicResult.kt
//  simproktools
//
//  Created by Andrey Prokhorenko on 12.03.2022.
//  Copyright (c) 2022 simprok. All rights reserved.

package com.simprok.simproktools.classic

/**
 * A type that represents a behavior of `ClassicMachine`.
 *
 * */
class ClassicResult<State, Output> private constructor(
    val state: State,
    val outputs: List<Output>
) {
    companion object {

        /**
         * Creates a `ClassicResult` object that is when returned from
         * `ClassicMachine` reducer changes state and emits outputs.
         * @param state - new state.
         * @param outputs - emitted outputs.
         * */
        fun <State, Output> set(
            state: State,
            vararg outputs: Output
        ): ClassicResult<State, Output> = ClassicResult(state, outputs.toList())


        /**
         * Creates a `ClassicResult` object that is when returned from
         * `ClassicMachine` reducer changes state and emits outputs.
         * @param state - new state.
         * @param outputs - emitted outputs.
         * */
        fun <State, Output> set(
            state: State,
            outputs: List<Output>
        ): ClassicResult<State, Output> = ClassicResult(state, outputs)
    }
}