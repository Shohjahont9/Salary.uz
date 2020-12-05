package shohjahon.salaryuz.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import shohjahon.salaryuz.R
import shohjahon.salaryuz.adapter.HomeAdapter
import shohjahon.salaryuz.anim.ViewAnimation
import shohjahon.salaryuz.api.ApiService
import shohjahon.salaryuz.api.RetrofitClient
import shohjahon.salaryuz.api.reponses.DeleteUserResponse
import shohjahon.salaryuz.interfaces.OnClickItemListener
import shohjahon.salaryuz.models.Ishchi


class HomeFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: HomeAdapter
    val retrofit = RetrofitClient.getInstance()

    private var myList = ArrayList<Ishchi>()

    var api = retrofit.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    var isRotate = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        //        val api = retrofit.create(ApiService::class.java)
        recyclerView = view.findViewById(R.id.recycler_home)
//        init1()

        var son = 0
        ViewAnimation().init(view.fabIshchi);
        ViewAnimation().init(view.fabLavozim);
        ViewAnimation().init(view.fabBonus);

        view.fabAdd.setColorFilter(Color.WHITE);
        view.fabAdd.setOnClickListener(View.OnClickListener { v ->
            isRotate = ViewAnimation().rotateFab(v, !isRotate)
            if (isRotate) {
                ViewAnimation().showIn(view.fabIshchi)
                ViewAnimation().showIn1(view.fabBonus)
                ViewAnimation().showIn2(view.fabLavozim)
            } else {
                ViewAnimation().showOut(view.fabIshchi)
                ViewAnimation().showOut1(view.fabBonus)
                ViewAnimation().showOut2(view.fabLavozim)
            }
        })

        view.fabLavozim.setOnClickListener(View.OnClickListener {
            son = 1
            val bundle = Bundle()
            bundle.putInt("LavozimActive", son)
            val lavozimlarFragment = LavozimlarFragment()

            val manager = requireActivity().supportFragmentManager
            val transaction = manager.beginTransaction()
            lavozimlarFragment.arguments = bundle

            transaction.replace(R.id.frameLayout, lavozimlarFragment).commit()
//        }
            Toast.makeText(
                requireContext(), "lavozim",
                Toast.LENGTH_SHORT
            ).show()
        })

        view.fabIshchi.setOnClickListener(View.OnClickListener {
            val manager = requireActivity().supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.frameLayout, AddWorkerFragment()).addToBackStack("xixi")
                .commit()

            Toast.makeText(
                requireContext(),
                "ishchi",
                Toast.LENGTH_SHORT
            ).show()
        })
        view.fabBonus.setOnClickListener(View.OnClickListener {
            son = 1
            val bundle = Bundle()
            bundle.putInt("UstamaActive", son)
            val ustamalarFragment = UstamalarFragment()

            val manager = requireActivity().supportFragmentManager
            val transaction = manager.beginTransaction()
            ustamalarFragment.arguments = bundle

            transaction.replace(R.id.frameLayout, ustamalarFragment).commit()
            Toast.makeText(
                requireContext(),
                "bonus",
                Toast.LENGTH_SHORT
            ).show()
        })



        return view
    }

    override fun onStart() {
        super.onStart()
        myList.clear()
        dkjsadkjsa()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun dkjsadkjsa() {
        api.getUser().enqueue(object : Callback<ArrayList<Ishchi>> {
            override fun onFailure(call: Call<ArrayList<Ishchi>>, t: Throwable) {
                Toast.makeText(requireContext(), "xmmmm", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ArrayList<Ishchi>>, response: Response<ArrayList<Ishchi>>) {
                if (response.isSuccessful) {



                    val ishchilist = response.body()

                    ishchilist?.forEach {
                        myList.add(it)
                    }
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        setHasFixedSize(true)
                        recyclerAdapter = HomeAdapter(myList)
                        adapter = recyclerAdapter
                    }
                    recyclerAdapter.onClickItems(object : OnClickItemListener {
                        override fun onClickItems(position: Int) {
                        }

                        override fun onDeleteItems(position: Int) {

                            println(ishchilist)


                            ishchilist!![position].apply {
                                api.deleteWorker(name, surname).enqueue(object :
                                    Callback<DeleteUserResponse> {
                                    override fun onResponse(
                                        call: Call<DeleteUserResponse>,
                                        response: Response<DeleteUserResponse>
                                    ) {
                                        if (response.isSuccessful) {
                                            response.body()?.apply {
                                                if (status == 200 && message == "SUCCESS") {
                                                    println("in success")
                                                    myList.removeAt(position)
                                                    recyclerAdapter.removeAt(position)
                                                    recyclerAdapter.notifyDataSetChanged()
                                                    println(myList)
                                                }
                                            }
                                        } else
                                            println("not succes")
                                    }

                                    override fun onFailure(
                                        call: Call<DeleteUserResponse>,
                                        t: Throwable
                                    ) {
                                        println(t.localizedMessage)
                                    }

                                })
                            }
                        }
                    })
                } else {
                    Toast.makeText(requireContext(), "pffff", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }


}