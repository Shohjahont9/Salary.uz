package shohjahon.salaryuz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.lavozim_item.view.*
import shohjahon.salaryuz.R
import shohjahon.salaryuz.interfaces.OnClickItemListener
import shohjahon.salaryuz.models.Lavozim

class LavozimAdapter1(val listOfItems: List<Lavozim>) : RecyclerView.Adapter<LavozimAdapter1.ViewHolder>() {
    lateinit var listener: OnClickItemListener

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lavozim = itemView.findViewById<TextView>(R.id.lavozim_tv1)
        val koeff = itemView.findViewById<TextView>(R.id.koef_tv1)



        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onClickItems(position)
                }
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lavozim1_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = listOfItems[position]

        holder.lavozim.text = items.position
        holder.koeff.text = items.score.toString()
    }

    override fun getItemCount(): Int = listOfItems.size

    fun onClickItems(listener: OnClickItemListener) {
        this.listener = listener
    }
}
