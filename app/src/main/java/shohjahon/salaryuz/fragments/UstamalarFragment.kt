package shohjahon.salaryuz.fragments

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.add_lavozim.view.*
import kotlinx.android.synthetic.main.fragment_lavozimlar.view.*
import kotlinx.android.synthetic.main.fragment_ustamalar.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import shohjahon.salaryuz.R
import shohjahon.salaryuz.adapter.BonusAdapter
import shohjahon.salaryuz.adapter.LavozimAdapter
import shohjahon.salaryuz.api.ApiService
import shohjahon.salaryuz.api.RetrofitClient
import shohjahon.salaryuz.interfaces.OnClickItemListener
import shohjahon.salaryuz.models.Bonus
import shohjahon.salaryuz.models.Lavozim


class UstamalarFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: BonusAdapter
    var retrofit = RetrofitClient.getInstance()
    var api = retrofit.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ustamalar, container, false)

        recyclerView = view.findViewById(R.id.recycler_ustamalar)
        getLavozim()


        view.fbustama.setColorFilter(Color.WHITE)
        view?.fbustama?.setOnClickListener {
            addLavozim()
        }


        return view
    }



    override fun onStart() {
        super.onStart()
        val addlav = arguments?.getInt("UstamaActive")
        if (addlav==1){
            addLavozim()
        }
    }

    private fun addLavozim() {

        val builder = AlertDialog.Builder(requireContext())
        val view = View.inflate(requireContext(), R.layout.add_lavozim, null)
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()

        view.tvu.text = "Bonus nomi"
        view.tvm.text = "Bonus`"
        view.lavozimet.hint = "Ustama"
        view.koefet.hint= "Mablag`"

        view.btn_cancel.setOnClickListener {
            dialog.cancel()
        }
        view.btn_save.setOnClickListener {
            val lav = view.lavozimet.text
            val koe = view.koefet.text

            if (lav.isEmpty()) {
                view.lavozimet.error = "Bonus nomini kiriting!"
            }
            if (koe.isEmpty()) {
                view.koefet.error = "Mablag` kiriting!"
            } else {


                api.addBonus(Bonus(lav.toString(), koe.toString().toFloat())).enqueue(object :
                    Callback<Bonus> {
                    override fun onFailure(call: Call<Bonus>, t: Throwable) {
                        Toast.makeText(requireContext(), "pff", Toast.LENGTH_SHORT).show()
                    }


                    override fun onResponse(call: Call<Bonus>, response: Response<Bonus>) {
                        if (response.isSuccessful) {
                            Toast.makeText(requireContext(), "Hello new user", Toast.LENGTH_SHORT)
                                .show()

                            val manager = requireActivity().supportFragmentManager
                            val trans = manager.beginTransaction()
                            trans.replace(R.id.frameLayout,UstamalarFragment()).commit()
                        }
                        dialog.dismiss()
                    }
                })
                dialog.dismiss()
            }
        }
    }
    private fun getLavozim() {

        api.getBonus().enqueue(object : Callback<List<Bonus>> {
            override fun onFailure(call: Call<List<Bonus>>, t: Throwable) {
                Toast.makeText(requireContext(), "pffffffff", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Bonus>>, response: Response<List<Bonus>>) {

                if (response.isSuccessful) {
                    val avv:List<Bonus>? = response.body()

                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        setHasFixedSize(true)
                        recyclerAdapter = BonusAdapter(avv!!)
                        adapter = recyclerAdapter
                    }
                    recyclerAdapter.onClickItems(object : OnClickItemListener {
                        override fun onClickItems(position: Int) {
                        }

                        override fun onDeleteItems(position: Int) {

                        }
                    })
                }
            }
        })
    }

}