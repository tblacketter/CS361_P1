# Project #: P1 - Deterministic Finite Automaton

* Authors: Teryn Blacketter, Lance Loper
* Class: CS361 Section 001
* Semester: Spring 2026

## Overview

This project sets up the framework for creating a DFA. It allows for the addition of characters in an alphabet (sigma), the creation of 
states (Q) with their transitions (delta), and a start and final state. There is also a method that swaps transitions between 2 states that creates 
a deep copy of the DFA with the new transitions.

## Files

Interfaces -
    FAInterface.java
    DFAInterface.java

Classes - 
    State.java
    DFAState.java
    DFA.java (Inherits FAInterface through DFAInterface)

## Compiling and Using

There is no main driver for this program, however there is a test suite that can be ran against our created classes. 
On a linux system you can compile the tests using:

javac -cp .:/usr/share/java/junit.jar ./test/dfa/DFATest.java

Then run the tests using:

java -cp .:/usr/share/java/junit.jar:/usr/share/java/hamcrest/hamcrest.jar org.junit.runner.JUnitCore test.dfa.DFATest




