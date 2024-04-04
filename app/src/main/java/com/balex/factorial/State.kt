package com.balex.factorial

sealed class State

data object Error : State()
data object Progress : State()
class Result(val factorial: String) : State()