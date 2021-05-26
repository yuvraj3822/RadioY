package com.example.radioy

import androidx.annotation.IdRes
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class Utils(var context:FragmentActivity) {

    companion object {
        val togglePlay = "togglePlay"
       val baseUrl =  "https://api.itmwpb.com/nowplaying/v3/935/"
    }

     fun addFragment(
        @IdRes containerViewId: Int,
        fragment: Fragment,
        fragmentTag: String
    ) {
         context.supportFragmentManager
            .beginTransaction()
            .add(containerViewId, fragment, fragmentTag)
            .disallowAddToBackStack()
            .commit()
    }

}