package com.amitverma.newsapp

import android.content.Context
import com.amitverma.newsapp.di.component.DaggerTestComponent
import com.amitverma.newsapp.di.component.TestComponent
import com.amitverma.newsapp.di.module.ApplicationTestModule
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class TestComponentRule(private val context: Context) : TestRule {

    var testComponent: TestComponent? = null

    fun getContext() = context

    private fun setupDaggerTestComponentInApplication(){
        val application = context.applicationContext as NewsApplication
        testComponent = DaggerTestComponent.builder()
            .applicationTestModule(ApplicationTestModule(application))
            .build()
        application.setTestComponent(testComponent!!)
    }

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement(){
            @Throws(Throwable::class)

            override fun evaluate() {
                try {
                    setupDaggerTestComponentInApplication()
                    base.evaluate()
                } finally {
                    testComponent = null
                }
            }

        }
    }
}