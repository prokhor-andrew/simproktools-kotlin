# simproktools

```simproktools``` is a small library consisting of useful machines for [simprokmachine](https://github.com/simprok-dev/simprokmachine-kotlin) framework. 

## Installation

Add this in your project's gradle file:

```groovy
implementation 'com.github.simprok-dev:simproktools-kotlin:1.1.4'
```

and this in your settings.gradle file:

```groovy
repositories {
    ...
    maven { url 'https://jitpack.io' }
}
```

## BasicMachine

A machine with an injectable processing behavior.

```Kotlin

val machine = BasicMachine<Input, Output>(dispatcher = Main /* or IO */) { input, callback ->
    // any processing logic
}
```

## ProcessMachine

A machine with an injectable processing behavior over the injected object.

```Kotlin

val obj: ObjectType = ...
val machine = ProcessMachine.create<ObjectType, Input, Output>(obj) { obj, input, callback ->
    // any processing logic
}
```

Works on the main thread.

## JustMachine

A machine that emits the injected value when subscribed and every time input is received.

```Kotlin
val machine = JustMachine<Input, Int>(0) // Int can be replaced with any type
```

Works on the main thread.


## SingleMachine

A machine that emits the injected value *when subscribed*.

```Kotlin
val machine = SingleMachine<Input, Int>(0) // Int can be replaced with any type
```

Works on the main thread.

## ValueMachine

A machine that accepts a value as input and immediately emits it as output.
When subscribed - emits `null`.

```Kotlin
val machine = ValueMachine<Int>() // Int can be replaced with any type
```

Works on the main thread.

## NeverMachine

A machine that when subscribed or receives input - ignores it, never emitting output.

```Kotlin

val machine = NeverMachine<Input, Output>() 
```

Works on the main thread.

## ReducerMachine

A machine that receives input, reduces it into state and emits it.

```Kotlin

// Boolean and Int can be replaced with any types

val _ = ReducerMachine<Boolean, Int>(0) { state, value -> // Int and Boolean
    // return ReducerResult.Set(0) // 0 will be a new State and will be passed as output 
    // return ReducerResult.Skip() // state won't be changed and passed as output
}
```


## ClassicMachine

A machine that receives input, reduces it into state and array of outputs that are emitted.

```Kotlin

// Boolean, Unit, and Int can be replaced with any types

val _ = ClassicMachine<Boolean, Unit, Int>(
    ClassicResult<Boolean, Int>.set(false, outputs = 0, 1, 2) // initial state and initial outputs that are emitted when machine is subscribed to
) { state, event -> 
    ClassicResult<Boolean, Int>.set(true, outputs = 3, 4, 5) // new state `true` and outputs `3, 4, 5` 
}

```

## Scan operator

Takes `this` and applies specific behavior.
When parent machine sends new input, it is either reduced into new child state and sent to `this` or mapped into parent output and emitted back.
When `this` sends new output, it is either reduced into new child state and sent back to `this` or mapped into parent output and emitted.

```Kotlin

// All the types can be replaced with anything else.

val machine: Machine<Boolean, Int> = ...

val result: Machine<String, Unit> = machine.scan(true) { state, event -> 
    // event has either come from parent as input or from child as output.
    // output should either go to the parent as output or to the child as new input and state.
    
    // Return
    // ScanOutput<Unit, Boolean>.State(Bool) // when input has to be sent to the child machine AND state has to be changed.
    // ScanOutput<Unit, Boolean>.Event(Unit) // when output has to be sent to the parent machine.
    ...
}
```

## ConnectableMachine

A machine for dynamic creation and connection of other machines.


```Kotlin
val _ = ConnectableMachine<Input, Output, BasicConnection<Input, Output> /* or any class that conforms to Connection*/>(
    BasicConnection.create(/* machines for connection go here */)
) { connection, input -> 
    // Return
    // ConnectionType<Input, Output, BasicConnection<Input, Output>>.Reduce(BasicConnection<Input, Output>.create(/* machines for connection go here */)) // when we want to connect new array of machines
    // ConnectionType<Input, Output, BasicConnection<Input, Output>>.Inward() // when we want to pass input to the connected machines
}
```
