package shohjahon.salaryuz.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_add_worker.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import shohjahon.salaryuz.R
import shohjahon.salaryuz.adapter.BonusAdapter1
import shohjahon.salaryuz.adapter.LavozimAdapter1
import shohjahon.salaryuz.api.ApiService
import shohjahon.salaryuz.api.RetrofitClient
import shohjahon.salaryuz.interfaces.OnClickItemListener
import shohjahon.salaryuz.models.Bonus
import shohjahon.salaryuz.models.Ishchi
import shohjahon.salaryuz.models.Lavozim

class AddWorkerFragment : Fragment() {
    val retrofit = RetrofitClient.getInstance()
    var api = retrofit.create(ApiService::class.java)
    lateinit var recyclerAdapter1: LavozimAdapter1
    lateinit var recyclerAdapter2: BonusAdapter1
    lateinit var recyclerView1: RecyclerView
    lateinit var recyclerView2: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_worker, container, false)
        recyclerView1 = view?.findViewById(R.id.recy_la)!!
        recyclerView2 = view?.findViewById(R.id.recy_bo)!!

        val name = view.etname.text
        val familya = view.etfamilya.text
        val lavo = view.etlavozim
        val bonus = view.etmaosh

        var count = 0
        var count1 = 0

        lavo.setOnClickListener {
            count++
            getLavozim()
            view?.let { activity?.hideKeyboard(it) }
            Log.d("lllll", count.toString())
            if (count == 1) {
                view.recy_la.visibility = View.VISIBLE

            }
            if (count == 2) {
                view.recy_la.visibility = View.GONE
                count = 0
            }


        }

        bonus.setOnClickListener {
            count1++
            getBonus()
            view?.let { activity?.hideKeyboard(it) }

            if (count1 == 1) {
                view.recy_bo.visibility = View.VISIBLE
            }
            if (count1 == 2) {
                view.recy_bo.visibility = View.GONE
                count1 = 0
            }

        }
        view.btn_cancel1.setOnClickListener {
            var manager = requireActivity().supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.frameLayout, HomeFragment()).commit()

        }
        view.btn_save1.setOnClickListener {


            if (name.isEmpty() && familya.isEmpty() && lavo.text == "lavozim" && bonus.text == "Bonus") {
                Toast.makeText(
                    requireContext(),
                    "Hamma ma'lumotlarni to`ldiring",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                api.addUser(
                    Ishchi(
                        familya.toString(),
                        name.toString(),
                        lavo.text.toString(),
                        bonus.text.toString(), ""
                    )
                )
                    .enqueue(object : Callback<Ishchi> {
                        override fun onFailure(call: Call<Ishchi>, t: Throwable) {
                            Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call<Ishchi>, response: Response<Ishchi>) {
                            if (response.isSuccessful) {
                                Toast.makeText(
                                    requireContext(),
                                    "Hello new user",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()

                                var manager = requireActivity().supportFragmentManager
                                val transaction = manager.beginTransaction()
                                transaction.replace(R.id.frameLayout, HomeFragment()).commit()
                            } else {

                                Toast.makeText(
                                    requireContext(),
                                    "smt went wrong",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                    })

            }

        }

        return view
    }

    private fun getLavozim() {
        api.getLavozim().enqueue(object : Callback<List<Lavozim>> {
            override fun onFailure(call: Call<List<Lavozim>>, t: Throwable) {
            }

            override fun onResponse(call: Call<List<Lavozim>>, response: Response<List<Lavozim>>) {
                val av = response.body()

                if (response.isSuccessful) {
                    recyclerView1.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        setHasFixedSize(true)
                        recyclerAdapter1 = LavozimAdapter1(av!!)
                        adapter = recyclerAdapter1
                    }
                    recyclerAdapter1.onClickItems(object : OnClickItemListener {
                        override fun onClickItems(position: Int) {
                            Log.d("fffff", position.toString())
                            view?.etlavozim?.text = av!![position].position

                            view!!.recy_la.visibility = View.GONE
                        }

                        override fun onDeleteItems(position: Int) {

                        }
                    })

                }
            }
        })


    }

    private fun getBonus() {
        api.getBonus().enqueue(object : Callback<List<Bonus>> {
            override fun onFailure(call: Call<List<Bonus>>, t: Throwable) {
                Toast.makeText(requireContext(), "pffffffff", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Bonus>>, response: Response<List<Bonus>>) {

                if (response.isSuccessful) {
                    val avv: List<Bonus>? = response.body()

                    recyclerView2.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        setHasFixedSize(true)
                        recyclerAdapter2 = BonusAdapter1(avv!!)
                        adapter = recyclerAdapter2
                    }
                    recyclerAdapter2.onClickItems(object : OnClickItemListener {
                        override fun onClickItems(position: Int) {
                            Log.d("fffff", position.toString())
                            view?.etmaosh?.text = avv!![position].bonus
                            view!!.recy_bo.visibility = View.GONE
                        }

                        override fun onDeleteItems(position: Int) {

                        }
                    })
                }
            }
        })
    }


    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


}