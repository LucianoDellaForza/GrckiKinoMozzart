package com.luka.grcki_kino_mozzart.utils

fun generateRandomNumbers(startLimit: Int, endLimit: Int, count: Int): List<Int> {
    return (startLimit..endLimit).asSequence().shuffled().take(count).toList()
}