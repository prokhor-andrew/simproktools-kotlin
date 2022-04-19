//
//  ScanOutput.kt
//  simproktools
//
//  Created by Andrey Prokhorenko on 12.03.2022.
//  Copyright (c) 2022 simprok. All rights reserved.

package com.simprok.simproktools.scan

/**
 * A type that represents a behavior of `Machine.scan()` operator.
 * Returned from `Machine.scan()`'s `reducer` method.
 */
sealed interface ScanOutput<ParentOutput, MachineInput> {

    /**
     * Changes current state of `Machine.scan()` into `MachineInput`
     */
    class State<ParentOutput, MachineInput>(
        val value: MachineInput
    ) : ScanOutput<ParentOutput, MachineInput>

    /**
     * Emits an output object to the parent machine.
     */
    class Event<ParentOutput, MachineInput>(
        val value: ParentOutput
    ) : ScanOutput<ParentOutput, MachineInput>
}
