//
//  ReducerMachine.kt
//  simproktools
//
//  Created by Andrey Prokhorenko on 12.03.2022.
//  Copyright (c) 2022 simprok. All rights reserved.

package com.simprok.simproktools.reducer

import com.simprok.simprokmachine.api.BiMapper
import com.simprok.simprokmachine.machines.Machine
import com.simprok.simprokmachine.machines.ParentMachine
import com.simprok.simproktools.classic.ClassicMachine
import com.simprok.simproktools.classic.ClassicResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


/**
 * A machine that receives input, reduces it into state and emits it.
 *
 * @param initial - initial state and array of outputs that are emitted when machine is subscribed to.
 * @param dispatcher - `ChildMachine` interface property.
 * @param reducer - a `BiMapper` object that accepts current state, received input and returns
 * an object of `ReducerResult` type depending on which the state is either changed or not.
 */
class ReducerMachine<Event, State>(
    private var initial: State,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val reducer: BiMapper<State, Event, ReducerResult<State>>
) : ParentMachine<Event, State> {

    /**
     * `ParentMachine` interface property
     */
    override val child: Machine<Event, State>
        get() = ClassicMachine(
            ClassicResult.set(initial, initial),
            dispatcher,
        ) { state, event ->
            when (val result = reducer(state, event)) {
                is ReducerResult.Set<State> -> ClassicResult.set(result.value, result.value)
                is ReducerResult.Skip<State> -> ClassicResult.set(state)
            }
        }
}