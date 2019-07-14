# Heartbeat CorDapp

This CorDapp is a simple showcase of scheduled activities (i.e. activities started by a node at a specific time without 
direct input from the node owner).

A node starts its heartbeat by calling the `StartHeartbeatFlow`. This creates a `HeartState` on the ledger. This 
`HeartState` has a scheduled activity to start the `HeatbeatFlow` one second later.

When the `HeartbeatFlow` runs one second later, it consumes the existing `HeartState` and creates a new `HeartState`. 
The new `HeartState` also has a scheduled activity to start the `HeatbeatFlow` in 10 second.

In this way, calling the `StartHeartbeatFlow` creates an endless chain of `HeartbeatFlow`s one second apart.

# Pre-requisites:
  
See https://docs.corda.net/getting-set-up.html.

# Usage

## Running the nodes:

```bash
# Inside the project folder

chmod 0755 ./build.sh

./build.sh

cd build/nodes/Notary/
java -jar corda.jar


cd build/nodes/PartyA/
java -jar corda.jar

```

## Interacting with the nodes:

Go to the CRaSH shell for PartyA, and run the `StartHeatbeatFlow`:

    start StartHeartbeatFlow ownerId: Alexa

If you now start monitoring the node's flow activity...

    flow watch

...you will see the `Heartbeat` flow running every second until you close the Flow Watch window using `ctrl/cmd + c`:

    xxxxxxxx-xxxx-xxxx-xx Heartbeat xxxxxxxxxxxxxxxxxxxx Alexa's Heart: Lub-dub

# Useless Debugging - if you stuck
```bash

java -Dcapsule.jvm.args="-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005" -jar corda.jar

```