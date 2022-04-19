//
//  ScanMachine.kt
//  simproktools
//
//  Created by Andrey Prokhorenko on 12.03.2022.
//  Copyright (c) 2022 simprok. All rights reserved.

package com.simprok.simproktools.scan

import com.simprok.simprokmachine.api.*
import com.simprok.simprokmachine.machines.Machine
import com.simprok.simproktools.classic.ClassicMachine
import com.simprok.simproktools.classic.ClassicResult


/**
 * Takes `this` and applies specific behavior.
 * When parent machine sends new input, it is either reduced into
 * new child state and sent to the `this` or mapped into parent output and emitted back.
 * When child machine sends new output, it is either reduced into
 * new child state and sent back to the `self` or mapped into parent output and emitted.
 * @param initial - initial state that is sent to the `this` machine when subscribed.
 * @param reducer - `BiMapper` that accepts current state as `Input`,
 * new input as `ScanInput`, and returns `ScanOutput`.
 */
fun <ParentInput, ParentOutput, Input, Output> Machine<Input, Output>.scan(
    initial: Input,
    reducer: BiMapper<Input, ScanInput<ParentInput, Output>, ScanOutput<ParentOutput, Input>>
): Machine<ParentInput, ParentOutput> {
    val machine: Machine<ScanEmit<ParentInput, ParentOutput, Input, Output>, ScanEmit<ParentInput, ParentOutput, Input, Output>> =
        outward {
            Ward.set<ScanEmit<ParentInput, ParentOutput, Input, Output>>(
                ScanEmit.ToReducer(ScanInput.Inner(it))
            )
        }.inward {
            when (it) {
                is ScanEmit.ToMachine<ParentInput, ParentOutput, Input, Output> -> {
                    Ward.set(it.value)
                }
                is ScanEmit.ToReducer<ParentInput, ParentOutput, Input, Output>,
                is ScanEmit.Out<ParentInput, ParentOutput, Input, Output> -> {
                    Ward.set()
                }
            }
        }

    val reducerMachine: Machine<ScanEmit<ParentInput, ParentOutput, Input, Output>, ScanEmit<ParentInput, ParentOutput, Input, Output>> =
        ClassicMachine<Input, ScanInput<ParentInput, Output>, ScanEmit<ParentInput, ParentOutput, Input, Output>>(
            ClassicResult.set(initial, ScanEmit.ToMachine(initial)),
            reducer = { state, event ->
                when (val result = reducer(state, event)) {
                    is ScanOutput.State<ParentOutput, Input> -> {
                        ClassicResult.set(result.value, ScanEmit.ToMachine(result.value))
                    }
                    is ScanOutput.Event<ParentOutput, Input> -> {
                        ClassicResult.set(state, ScanEmit.Out(result.value))
                    }
                }
            }
        ).inward {
            when (it) {
                is ScanEmit.ToMachine<ParentInput, ParentOutput, Input, Output>,
                is ScanEmit.Out<ParentInput, ParentOutput, Input, Output> -> Ward.set()
                is ScanEmit.ToReducer<ParentInput, ParentOutput, Input, Output> -> Ward.set(it.value)
            }
        }

    return merge(machine, reducerMachine).redirect {
        when (it) {
            is ScanEmit.ToMachine<ParentInput, ParentOutput, Input, Output>,
            is ScanEmit.ToReducer<ParentInput, ParentOutput, Input, Output> -> {
                Direction.Back(it)
            }
            is ScanEmit.Out<ParentInput, ParentOutput, Input, Output> -> {
                Direction.Prop()
            }
        }
    }.outward {
        when (it) {
            is ScanEmit.ToMachine<ParentInput, ParentOutput, Input, Output>,
            is ScanEmit.ToReducer<ParentInput, ParentOutput, Input, Output> -> {
                Ward.set()
            }
            is ScanEmit.Out<ParentInput, ParentOutput, Input, Output> -> {
                Ward.set(it.value)
            }
        }
    }.inward {
        Ward.set(ScanEmit.ToReducer(ScanInput.Outer(it)))
    }
}