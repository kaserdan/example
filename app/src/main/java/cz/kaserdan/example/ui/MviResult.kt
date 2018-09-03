package cz.kaserdan.example.ui


abstract class MviResult<T> {

    abstract fun reduce(previousState: T): T

}