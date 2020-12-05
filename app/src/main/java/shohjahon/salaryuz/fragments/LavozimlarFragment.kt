package shohjahon.salaryuz.fragments

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.add_lavozim.view.*
import kotlinx.android.synthetic.main.fragment_lavozimlar.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import shohjahon.salaryuz.R
import shohjahon.salaryuz.adapter.HomeAdapter
import shohjahon.salaryuz.adapter.LavozimAdapter
import shohjahon.salaryuz.api.ApiService
import shohjahon.salaryuz.api.RetrofitClient
import shohjahon.salaryuz.interfaces.OnClickItemListener
import shohjahon.salaryuz.models.Ishchi
import shohjahon.salaryuz.models.Lavozim


class LavozimlarFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: LavozimAdapter
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
        val view = inflater.inflate(R.layout.fragment_lavozimlar, container, false)

        recyclerView = view.findViewById(R.id.recycler_lavozim)

        getLavozim()

        view.fbLavozim.setColorFilter(Color.WHITE)
//        setRecyclerView()
        view?.fbLavozim?.setOnClickListener {
            addLavozim()
        }

        return view
    }

    private fun getLavozim() {

        api.getLavozim().enqueue(object : Callback<List<Lavozim>> {
            override fun onFailure(call: Call<List<Lavozim>>, t: Throwable) {
                Toast.makeText(requireContext(), "pffffffff", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Lavozim>>, response: Response<List<Lavozim>>) {

                if (response.isSuccessful) {
                    val av = response.body()

                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        setHasFixedSize(true)
                        recyclerAdapter = LavozimAdapter(av!!)
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

    private fun addLavozim() {

        val builder = AlertDialog.Builder(requireContext())
        val view = View.inflate(requireContext(), R.layout.add_lavozim, null)
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()

        view.btn_cancel.setOnClickListener {
            dialog.cancel()
        }
        view.btn_save.setOnClickListener {
            val lav = view.lavozimet.text
            val koe = view.koefet.text

            if (lav.isEmpty()) {
                view.lavozimet.error = "Lavozimni kiriting!"
            }
            if (koe.isEmpty()) {
                view.koefet.error = "Koeffisientni kiriting!"
            } else {


                api.addLavozim(Lavozim(lav.toString(), koe.toString().toFloat())).enqueue(object :
                    Callback<Lavozim> {
                    override fun onFailure(call: Call<Lavozim>, t: Throwable) {
                        Toast.makeText(requireContext(), "pff", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<Lavozim>, response: Response<Lavozim>) {
                        if (response.isSuccessful) {
                            Toast.makeText(requireContext(), "Hello new user", Toast.LENGTH_SHORT)
                                .show()
                            val manager = requireActivity().supportFragmentManager
                            val trans = manager.beginTransaction()
                            trans.replace(R.id.frameLayout,LavozimlarFragment()).commit()

                        }
                        dialog.dismiss()
                    }
                })
                dialog.dismiss()
            }
        }
    }

//    private fun setRecyclerView() {
//        recyclerView.apply {
//            layoutManager = LinearLayoutManager(requireContext())
//            setHasFixedSize(true)
//            recyclerAdapter = LavozimAdapter(listOfLavozim)
//            adapter = recyclerAdapter
//        }
//        recyclerAdapter.onClickItems(object : OnClickItemListener {
//            override fun onClickItems(position: Int) {
//            }
//        })
//    }


    override fun onStart() {
        super.onStart()
        val addlav = arguments?.getInt("LavozimActive")
        if (addlav == 1) {
            addLavozim()
        }
    }

}