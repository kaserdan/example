package cz.kaserdan.example.ui


/**
 * Created by egold
 */
abstract class MviResult<T> {

    abstract fun reduce(previousState: T): T

}