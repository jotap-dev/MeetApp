package com.example.meetapp

import com.example.meetapp.data.repositories.PresentersRepository
import com.google.gson.JsonArray
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private lateinit var presentersRepository: PresentersRepository

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}