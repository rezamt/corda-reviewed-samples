#!/usr/bin/env zsh

gradle deployNodes

cp defaults/node_notary.conf build/nodes/Notary/node.conf
cp defaults/node_partya.conf build/nodes/PartyA/node.conf
