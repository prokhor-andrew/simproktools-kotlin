//
//  NeverMachine.kt
//  simproktools
//
//  Created by Andrey Prokhorenko on 12.03.2022.
//  Copyright (c) 2022 simprok. All rights reserved.

package com.simprok.simproktools

import com.simprok.simprokmachine.api.Handler
import com.simprok.simprokmachine.machines.ChildMachine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * A machine that when subscribed or receives input - ignores it, never emitting output.
 */
class NeverMachine<Input, Output> : ChildMachine<Input, Output> {

    /**
     * `ChildMachine` interface method
     */
    override suspend fun process(input: Input?, callback: Handler<Output>) {
        // do nothing, as we never should call the callback
    }

    /**
     * `ChildMachine` interface property
     */
    override val dispatcher: CoroutineDispatcher
        get() = Dispatchers.Main
}