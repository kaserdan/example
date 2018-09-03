package cz.kaserdan.example.ui.list

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.support.v7.recyclerview.extensions.AsyncListDiffer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import cz.kaserdan.example.R
import cz.kaserdan.example.model.entity.TransactionItem
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.transaction_body.view.*

/**
 * Created by egold
 */
class TransactionsAdapter : RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder>() {

    private val _itemClicks = PublishSubject.create<TransactionItem>()

    val itemClicks: Observable<TransactionItem>
        get() = _itemClicks.hide()

    private val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<TransactionItem>() {
        override fun areItemsTheSame(first: TransactionItem, second: TransactionItem): Boolean =
                first.id == second.id

        override fun areContentsTheSame(first: TransactionItem, second: TransactionItem): Boolean = first == second

    })

    fun changeItems(items: List<TransactionItem>) {
        differ.submitList(items)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.item = differ.currentList[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder =
            TransactionViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_transaction, parent, false))
                    .apply {
                        RxView.clicks(itemView)
                                .flatMap {
                                    item?.let { item -> Observable.just(item) }
                                            ?: Observable.empty()
                                }
                                .subscribe { _itemClicks.onNext(it) }
                    }


    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val iconIV = itemView.iconIV
        private val priceTV = itemView.priceTV
        private val directionTV = itemView.directionTV

        var item: TransactionItem? = null
            set(value) {
                iconIV.setImageResource(if (value?.direction == TransactionItem.Direction.INCOMING) R.drawable.ic_incoming else R.drawable.ic_outgoing)
                priceTV.text = String.format("%d Kč", value?.amountInAccountCurrency)
                directionTV.text = value?.direction?.name?.toLowerCase()
                field = value
            }

    }

}