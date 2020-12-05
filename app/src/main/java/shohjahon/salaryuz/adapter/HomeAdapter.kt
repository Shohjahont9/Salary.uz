package shohjahon.salaryuz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import shohjahon.salaryuz.R
import shohjahon.salaryuz.interfaces.OnClickItemListener
import shohjahon.salaryuz.models.Ishchi

class HomeAdapter(val listOfItems: List<Ishchi>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    lateinit var listener: OnClickItemListener

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val maosh = itemView.findViewById<TextView>(R.id.tv_maosh)
        val ism = itemView.findViewById<TextView>(R.id.tv_name)
        val familya = itemView.findViewById<TextView>(R.id.tv_surname)
        val lavozim = itemView.findViewById<TextView>(R.id.tv_lavozim)
        val next = itemView.findViewById<ImageView>(R.id.next_page)
        val delete = itemView.findViewById<ImageView>(R.id.delete)
        val etsurname = itemView.findViewById<EditText>(R.id.et_surname)
        val etname = itemView.findViewById<EditText>(R.id.et_name)
        val etlavozim = itemView.findViewById<EditText>(R.id.et_lavozim)
        val etmaosh = itemView.findViewById<EditText>(R.id.et_maosh)
        val check = itemView.findViewById<ImageView>(R.id.check)
        val rel = itemView.findViewById<RelativeLayout>(R.id.rel)
        val rel1 = itemView.findViewById<RelativeLayout>(R.id.rel1)


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
                    rel.visibility = View.GONE
                    rel1.visibility = View.VISIBLE

                    etsurname.setText(familya.text.toString())
                    etname.setText(ism.text.toString())
                    etlavozim.setText(lavozim.text.toString())
                    etmaosh.setText(maosh.text.toString())

                }
            }
            check.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    next.visibility = View.VISIBLE
                    check.visibility = View.GONE
                    rel.visibility = View.VISIBLE
                    rel1.visibility = View.GONE

                    familya.text = etsurname.text.toString()
                    ism.text = etname.text.toString()
                    lavozim.text = etlavozim.text.toString()
                    maosh.text = etmaosh.text.toString()

                }
            }
            delete.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDeleteItems(position)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = listOfItems[position]

        holder.maosh.text = items.salary
        holder.ism.text = items.name
        holder.familya.text = items.surname
        holder.lavozim.text = items.position
    }

    override fun getItemCount(): Int = listOfItems.size

    fun onClickItems(listener: OnClickItemListener) {
        this.listener = listener
    }

    fun removeAt(position: Int) {
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, listOfItems.size)
    }


}