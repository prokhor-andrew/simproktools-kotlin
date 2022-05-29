//
//  ClassicMachine.kt
//  simproktools
//
//  Created by Andrey Prokhorenko on 12.03.2022.
//  Copyright (c) 2022 simprok. All rights reserved.

package com.simprok.simproktools.classic

import com.simprok.simprokmachine.api.BiMapper
import com.simprok.simprokmachine.api.Handler
import com.simprok.simprokmachine.machines.ChildMachine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO

/**
 * A machine that receives input, reduces it into state and array of outputs that are emitted.
 *
 * @param initial - initial state and array of outputs that are emitted when machine is subscribed to.
 * @param dispatcher - `ChildMachine` interface property.
 * @param reducer - a `BiMapper` object that accepts current state, received input and
 * returns an object of `ClassicResult` type that contains new state and emitted array of outputs.
 */
class ClassicMachine<State, Input, Output>(
    initial: ClassicResult<State, Output>,
    override val dispatcher: CoroutineDispatcher = IO,
    private val reducer: BiMapper<State, Input, ClassicResult<State, Output>>
) : ChildMachine<Input, Output> {

    private var state: ClassicResult<State, Output> = initial

    /**
     * `ChildMachine` interface method
     */
    override fun process(input: Input?, callback: Handler<Output>) {
        if (input != null) {
            state = reducer(state.state, input)
        }
        state.outputs.forEach { callback(it) }
    }
}