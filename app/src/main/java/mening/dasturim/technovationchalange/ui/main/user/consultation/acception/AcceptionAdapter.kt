package mening.dasturim.technovationchalange.ui.main.user.consultation.acception

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import mening.dasturim.technovationchalange.R
import mening.dasturim.technovationchalange.data.module.CalendarItems
import mening.dasturim.technovationchalange.databinding.ItemCalendarBinding

class AcceptionAdapter (val context: Context, private val itemClickListener: (Int) -> Unit) :
    RecyclerView.Adapter<AcceptionAdapter.VH>() {
    private var arrayList= listOf<CalendarItems>()


    fun setData(itemList : List<CalendarItems>){
        this.arrayList=itemList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater= LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ItemCalendarBinding>(inflater, R.layout.item_calendar,parent,false)
        return VH(binding,parent.context)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {


        holder.itemView.setOnClickListener {
            itemClickListener.invoke(position)
            holder.binding.cvDoc2.setCardBackgroundColor(ContextCompat.getColorStateList(context,R.color.blue))
        }

        holder.onBind(arrayList[position])
    }

    override fun getItemCount()=arrayList.size

    class VH( val binding: ItemCalendarBinding, private val context: Context)
        : RecyclerView.ViewHolder(binding.root){

        fun onBind(rate : CalendarItems){
            binding.apply {
                tvName.setText(rate.week!!)
                tvChat.setText(rate.number!!)

            }
        }
    }
}