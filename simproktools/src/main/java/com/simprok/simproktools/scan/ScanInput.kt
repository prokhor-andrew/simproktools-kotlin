//
//  ScanInput.kt
//  simproktools
//
//  Created by Andrey Prokhorenko on 12.03.2022.
//  Copyright (c) 2022 simprok. All rights reserved.

package com.simprok.simproktools.scan

/**
 * A type that represents a behavior of `Machine.scan()` operator.
 * Received by `Machine.scan()`'s `reducer` as event.
 */
sealed interface ScanInput<ParentInput, MachineOutput> {

    /**
     * Received from `Machine.this` object.
     */
    class Inner<ParentInput, MachineOutput>(
        val value: MachineOutput
    ) : ScanInput<ParentInput, MachineOutput>

    /**
     * Received from parent machine.
     */
    class Outer<ParentInput, MachineOutput>(
        val value: ParentInput
    ) : ScanInput<ParentInput, MachineOutput>
}