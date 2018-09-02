package cz.kaserdan.example.model.entity

import android.os.Parcel
import android.os.Parcelable

data class TransactionItem(val id: Int, val amountInAccountCurrency: Int, val direction: Direction) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        Direction.valueOf(parcel.readString())
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(amountInAccountCurrency)
        parcel.writeString(direction.name)
    }

    override fun describeContents(): Int = 0

    enum class Direction {
        INCOMING, OUTGOING
    }

    data class TransactionFilter(val direction: Direction? = null)

    companion object {

        const val TAG = "TransactionItem"

        @JvmField
        val CREATOR = object : Parcelable.Creator<TransactionItem> {
            override fun createFromParcel(parcel: Parcel): TransactionItem {
                return TransactionItem(parcel)
            }

            override fun newArray(size: Int): Array<TransactionItem?> {
                return arrayOfNulls(size)
            }
        }

    }

}