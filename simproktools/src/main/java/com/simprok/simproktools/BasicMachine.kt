//
//  BasicMachine.kt
//  simproktools
//
//  Created by Andrey Prokhorenko on 12.03.2022.
//  Copyright (c) 2022 simprok. All rights reserved.

package com.simprok.simproktools

import com.simprok.simprokmachine.api.BiHandler
import com.simprok.simprokmachine.api.Handler
import com.simprok.simprokmachine.machines.ChildMachine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

/**
 * A class that describes a machine with an injectable processing behavior.
 * @param dispatcher - `ChildMachine` interface property
 * @param processor - triggered when `process()` method is triggered.
 */
class BasicMachine<Input, Output>(
    override val dispatcher: CoroutineDispatcher = IO,
    private val processor: BiHandler<Input?, Handler<Output>>
) : ChildMachine<Input, Output> {

    /**
     * `ChildMachine` interface method
     */
    override fun process(input: Input?, callback: Handler<Output>) {
        processor(input, callback)
    }
}