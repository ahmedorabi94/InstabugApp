package com.ahmedorabi.instabugapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.ahmedorabi.instabugapp.data.domain.Word
import com.ahmedorabi.instabugapp.features.words_list.repo.DbManager
import junit.framework.TestCase.assertTrue
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class DbManagerTest {

    private lateinit var dbManager: DbManager


    @Before
    fun setUp() {
        dbManager = DbManager(InstrumentationRegistry.getInstrumentation().targetContext)
    }

    @After
    fun finish() {
        dbManager.closeDb()
    }

    @Test
    fun testPreConditions() {
        assertNotNull(dbManager)
    }

    @Test
    @Throws(Exception::class)
    fun testShouldAddExpenseType() {
        val word = Word(name = "ios", total = 12)
        val list = ArrayList<Word>()
        list.add(word)
        dbManager.deleteAllWords()
        dbManager.insertAllWords(list)
        val item = dbManager.getWordsList()
        assertThat(item.size, `is`(1))
        assertTrue(item[0].name == "ios")
        assertTrue(item[0].total == 12)


    }

    @Test
    fun testDeleteAll() {
        dbManager.deleteAllWords()
        val item: List<Word> = dbManager.getWordsList()
        assertThat(item.size, `is`(0))
    }

    @Test
    fun testDeleteOnlyOne() {
        val word = Word(name = "ios", total = 12)
        val list = ArrayList<Word>()
        list.add(word)
        dbManager.deleteAllWords()
        dbManager.insertAllWords(list)
        val item: List<Word?> = dbManager.getWordsList()
        assertThat(item.size, `is`(1))
    }

    @Test
    fun testAddAndDelete() {
        dbManager.deleteAllWords()
        val word = Word(name = "ios1", total = 12)
        val word1 = Word(name = "ios2", total = 13)
        val word2 = Word(name = "ios3", total = 14)

        val list = ArrayList<Word>()
        list.add(word)
        list.add(word1)
        list.add(word2)
        dbManager.deleteAllWords()
        dbManager.insertAllWords(list)
        val item: List<Word?> = dbManager.getWordsList()
        assertThat(item.size, `is`(3))
        assertThat(item.size, `is`(3))

    }


}