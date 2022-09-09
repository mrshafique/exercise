package net.trexis.shafikexcersie

import com.google.common.truth.Truth.assertThat
import net.trexis.shafikexcersie.utils.Validations
import org.junit.Before
import org.junit.Test

class ValidationsTest {
    private lateinit var validation: Validations

    @Before
    fun init() {
        validation = Validations
    }

    @Test
    fun checkEmptyField()
    {
        val result = validation.isFiledEmpty("")
        assertThat(result).isTrue()
    }

}