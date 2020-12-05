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
import shohjahon.salaryuz.models.Bonus
import shohjahon.salaryuz.models.Lavozim

class BonusAdapter(val listOfItems: List<Bonus>) : RecyclerView.Adapter<BonusAdapter.ViewHolder>() {
    lateinit var listener: OnClickItemListener

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lavozim = itemView.findViewById<TextView>(R.id.lavozim_tv)
        val koeff = itemView.findViewById<TextView>(R.id.koef_tv)
        val next = itemView.findViewById<ImageView>(R.id.next_page1)
        val check = itemView.findViewById<ImageView>(R.id.check1)



        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
//                    listener.onClickItems(position)
                }
            }
            next.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    next.visibility = View.GONE
                    check.visibility = View.VISIBLE
                    itemView.rell.visibility = View.GONE
                    itemView.rel1l.visibility = View.VISIBLE

                    itemView.lavozim_et.setText(lavozim.text.toString())
                    itemView.koef_et.setText(koeff.text.toString())
                }
            }
            check.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    next.visibility = View.VISIBLE
                    check.visibility = View.GONE
                    itemView.rell.visibility = View.VISIBLE
                    itemView.rel1l.visibility = View.GONE

                    lavozim.text = itemView.lavozim_et.text.toString()
                    koeff.text = itemView.koef_et.text.toString()

                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lavozim_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = listOfItems[position]

        holder.lavozim.text = items.bonus
        holder.koeff.text = items.value.toString()
    }

    override fun getItemCount(): Int = listOfItems.size

    fun onClickItems(listener: OnClickItemListener) {
        this.listener = listener
    }
}
