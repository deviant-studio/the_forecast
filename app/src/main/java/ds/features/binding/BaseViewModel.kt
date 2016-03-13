package ds.features.binding

import android.databinding.ViewDataBinding
import com.trello.rxlifecycle.RxLifecycle
import cz.kinst.jakub.viewmodelbinding.ViewModel
import ds.features.binding.ModelViewLifecycleEvent.*
import ds.features.di.DaggerSingletones
import rx.Observable
import rx.subjects.BehaviorSubject

abstract class BaseViewModel<T : ViewDataBinding> : ViewModel<T>() {

	private val lifecycleSubject = BehaviorSubject.create<ModelViewLifecycleEvent>()

	private var dagger = DaggerSingletones()

	val timer = dagger.timer
	val service = dagger.service
	val db = dagger.db

	protected abstract fun toggleProgress(enable: Boolean)

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// RxLifecycle implementation

	fun <T> bindUntilEvent(event: ModelViewLifecycleEvent): Observable.Transformer<T, T> = RxLifecycle.bindUntilEvent<T, ModelViewLifecycleEvent>(lifecycleSubject, event)

	override fun onViewModelCreated() {
		super.onViewModelCreated()
		lifecycleSubject.onNext(CREATED)
	}

	override fun onViewModelDestroyed() {
		super.onViewModelDestroyed()
		lifecycleSubject.onNext(DESTROYED)
	}

	override fun onViewAttached(firstAttachment: Boolean) {
		super.onViewAttached(firstAttachment)
		lifecycleSubject.onNext(ATTACHED)
	}

	override fun onViewDetached(finalDetachment: Boolean) {
		super.onViewDetached(finalDetachment)
		lifecycleSubject.onNext(DETACHED)
	}

	override fun onResume() {
		super.onResume()
		lifecycleSubject.onNext(RESUME)
	}

	override fun onPause() {
		super.onPause()
		lifecycleSubject.onNext(PAUSE)
	}
}

enum class ModelViewLifecycleEvent {
	CREATED, DESTROYED, ATTACHED, DETACHED, RESUME, PAUSE
}
