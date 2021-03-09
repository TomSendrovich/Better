package com.better.model.dataHolders

/**
 * Represents a fixture score.
 * @property halfTime score situation after half time
 * @property fullTime score situation after full time
 * @property extraTime score situation after extra time
 * @property penalty score situation after penalty
 */
data class Score(
    val halfTime: Goals,
    val fullTime: Goals,
    val extraTime: Goals,
    val penalty: Goals
)
