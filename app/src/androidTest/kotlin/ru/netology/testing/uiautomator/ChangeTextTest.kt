package ru.netology.testing.uiautomator

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


const val SETTINGS_PACKAGE = "com.android.settings"
const val MODEL_PACKAGE = "ru.netology.testing.uiautomator"

const val TIMEOUT = 5000L

@RunWith(AndroidJUnit4::class)
class ChangeTextTest {

    private lateinit var device: UiDevice
    private val textToSet = "Netology"
    val packageName = MODEL_PACKAGE
    private val timeout = 5000L

    private fun waitForPackage(packageName: String) {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(packageName)), TIMEOUT)
    }

    @Before
    fun beforeEachTest() {
        // Press home
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        // Wait for launcher
        val launcherPackage = device.launcherPackageName
        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT)

        waitForPackage(packageName)
    }


    @Test
    fun testChangeText() {

        device.wait(Until.findObject(By.res(packageName, "userInput")), timeout)?.text = textToSet
        device.wait(Until.findObject(By.res(packageName, "buttonChange")), timeout)?.click()

        val result = device.wait(Until.findObject(By.res(packageName, "textToBeChanged")), timeout)?.text
        assertEquals(result, textToSet)
    }

    @Test
    fun testSetEmptyText() {
        val textEmpty = "Hello UiAutomator!"

        device.wait(Until.findObject(By.res(packageName, "userInput")), timeout)?.text = "   "
        device.wait(Until.findObject(By.res(packageName, "buttonChange")), timeout)?.click()

        val finalResultText = device.wait(Until.findObject(By.res(packageName, "textToBeChanged")), timeout)?.text

        assertEquals(finalResultText, textEmpty)
    }


    @Test
    fun testAnotherActivity() {
        val textToVerify = "Hello, activity"

        device.wait(Until.findObject(By.res(packageName, "userInput")), timeout)?.text = textToVerify
        device.wait(Until.findObject(By.res(packageName, "buttonActivity")), timeout)?.click()
        
        val result = device.wait(Until.findObject(By.res(packageName, "text")), TIMEOUT)?.text

        assertEquals(result, textToVerify)
    }
}





