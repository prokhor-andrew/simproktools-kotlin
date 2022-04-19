//
//  ScanEmit.kt
//  simproktools
//
//  Created by Andrey Prokhorenko on 12.03.2022.
//  Copyright (c) 2022 simprok. All rights reserved.

package com.simprok.simproktools.scan

internal sealed interface ScanEmit<ParentInput, ParentOutput, ChildInput, ChildOutput> {

    class ToMachine<ParentInput, ParentOutput, ChildInput, ChildOutput>(
        val value: ChildInput
    ) : ScanEmit<ParentInput, ParentOutput, ChildInput, ChildOutput>

    class ToReducer<ParentInput, ParentOutput, ChildInput, ChildOutput>(
        val value: ScanInput<ParentInput, ChildOutput>
    ) : ScanEmit<ParentInput, ParentOutput, ChildInput, ChildOutput>

    class Out<ParentInput, ParentOutput, ChildInput, ChildOutput>(
        val value: ParentOutput
    ) : ScanEmit<ParentInput, ParentOutput, ChildInput, ChildOutput>
}