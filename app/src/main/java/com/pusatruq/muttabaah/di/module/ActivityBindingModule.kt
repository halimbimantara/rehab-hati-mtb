package com.pusatruq.muttabaah.di.module

import com.pusatruq.muttabaah.di.ActivityScoped
import com.pusatruq.muttabaah.ui.main.comment.CommentActivity
import com.pusatruq.muttabaah.ui.main.comment.CommentModule
import com.pusatruq.muttabaah.ui.main.home.HomeModule
import com.pusatruq.muttabaah.ui.main.MainActivity
import com.pusatruq.muttabaah.ui.main.accounts.AccountActivity
import com.pusatruq.muttabaah.ui.main.accounts.ProfileActivity
import com.pusatruq.muttabaah.ui.main.accounts.SignUpActivity
import com.pusatruq.muttabaah.ui.main.sholat.activity.SholatActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * modified by mhbx on 11/29/18.
 */

@Module
internal abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [HomeModule::class])
    internal abstract fun bindMainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector()
    internal abstract fun bindAccountActivity(): AccountActivity

    @ActivityScoped
    @ContributesAndroidInjector()
    internal abstract fun bindRegisterActivity(): SignUpActivity

    @ActivityScoped
    @ContributesAndroidInjector()
    internal abstract fun bindProfileActivity(): ProfileActivity

    @ActivityScoped
    @ContributesAndroidInjector()
    internal abstract fun bindSholatActivity(): SholatActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [CommentModule::class])
    internal abstract fun bindCommentActivity(): CommentActivity
}