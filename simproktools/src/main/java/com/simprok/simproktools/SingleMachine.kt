//
//  SingleMachine.kt
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
 * A machine that emits the injected value *when subscribed*.
 */
class SingleMachine<Input, Output>(private val value: Output) : ChildMachine<Input, Output> {

    /**
     * `ChildMachine` interface method
     */
    override fun process(input: Input?, callback: Handler<Output>) {
        if (input == null) {
            callback(value)
        }
    }

    /**
     * `ChildMachine` interface property
     */
    override val dispatcher: CoroutineDispatcher
        get() = Dispatchers.Main
}