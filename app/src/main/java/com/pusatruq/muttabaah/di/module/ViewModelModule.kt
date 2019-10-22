package com.pusatruq.muttabaah.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pusatruq.muttabaah.di.ViewModelKey
import com.pusatruq.muttabaah.ui.main.accounts.viewmodels.SignUpViewModel
import com.pusatruq.muttabaah.ui.main.comment.CommentViewModel
import com.pusatruq.muttabaah.ui.main.home.HomeViewModel
import com.pusatruq.muttabaah.ui.main.maparea.MapsViewModel
import com.pusatruq.muttabaah.ui.main.scorring.ScoreViewModel
import com.pusatruq.muttabaah.ui.main.sholat.viewmodels.SholatViewModel
import com.pusatruq.muttabaah.ui.main.shop.ShopViewModel
import com.pusatruq.muttabaah.util.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

/**
 * Created by cuongpm on 11/29/18.
 */

@Module(includes = [AppModule::class, RepositoryModule::class])
abstract class ViewModelModule {

    @Singleton
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MapsViewModel::class)
    abstract fun bindMapViewModel(viewModel: MapsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ScoreViewModel::class)
    abstract fun bindScoreViewModel(viewModel: ScoreViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CommentViewModel::class)
    abstract fun bindCommentViewModel(viewModel: CommentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShopViewModel::class)
    abstract fun bindShopViewModel(viewModel: ShopViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SholatViewModel::class)
    abstract fun bindSolatViewModel(viewModel: SholatViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    abstract fun bindSignUpViewModel(viewModel: SignUpViewModel): ViewModel
}