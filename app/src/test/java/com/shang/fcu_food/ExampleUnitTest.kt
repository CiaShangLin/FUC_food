package com.shang.fcu_food

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun randomPicture() {
        for(i in 1..100){
            var p=(Math.random()*2).toInt()+1
            assertTrue(p==1 || p==2)
        }
    }
}
