package com.example.kotlinmvpdemo.ndk

class nativelib {


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

}