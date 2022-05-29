//
//  ValueMachine.kt
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
 * A machine that accepts a value as input and immediately emits it as output.
 * When subscribed - emits `null`.
 */
class ValueMachine<T> : ChildMachine<T, T?> {

    /**
     * `ChildMachine` interface method
     */
    override fun process(input: T?, callback: Handler<T?>) {
        callback(input)
    }

    /**
     * `ChildMachine` interface property
     */
    override val dispatcher: CoroutineDispatcher
        get() = Dispatchers.Main
}