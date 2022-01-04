package com.example.kotlinmvpdemo.ndk

class Nativelib {


    var testAccessFieldName = "testAccessFieldName"

    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("nativehaha")
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    external fun sum(x: Int, y: Int): Int

    external fun testbasictype()

    external fun testVariableAddress()

    external fun testPointerType()

    external fun testMultistagePointer()

    external fun testPointerOperation()

    external fun testassignapointertoanarray()

    external fun testFunctionPointer(a: Int, b: Int)

    external fun testFindSmallestValue()

    external fun testDynamicArray()

    external fun testReallocateMemory()

    external fun testString()

    external fun testStructure()

    external fun testConsortium()

    external fun testEnumerate()

    external fun testOperatingFile(path: String)

    external fun testPrecompiled()

    external fun testAccessField()

}