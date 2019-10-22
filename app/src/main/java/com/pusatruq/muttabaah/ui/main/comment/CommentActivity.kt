package com.pusatruq.muttabaah.ui.main.comment

import android.os.Bundle
import com.pusatruq.muttabaah.R
import com.pusatruq.muttabaah.ui.core.base.BaseActivity
import com.pusatruq.muttabaah.util.ext.addFragment

/**
 * Created by cuongpm on 12/31/18.
 */

class CommentActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        addFragment(R.id.content_frame, intent.extras, ::CommentFragment)
    }

}