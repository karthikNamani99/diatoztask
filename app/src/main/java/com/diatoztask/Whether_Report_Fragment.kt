package com.diatoztask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Whether_Report_Fragment : Fragment() {
    private var columnCount = 1

    private var recyclerView:RecyclerView? = null
    private var myItemRecyclerViewAdapter : Weather_Adapter? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.whether_reporter_login, container, false)
        recyclerView = view.findViewById(R.id.recycler_view);
        val toolbar = view.findViewById(R.id.toolbar_main) as Toolbar
        val fragmentTitle = view.findViewById<TextView>(R.id.title_nav)
        fragmentTitle.text = "Whether Report"
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //you can set the title for your toolbar here for different fragments different titles
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView!!.layoutManager = LinearLayoutManager(activity);

        val model = ViewModelProviders.of(this).get(WhwtherList::class.java)
        model.getCitiesList().observe(viewLifecycleOwner, androidx.lifecycle.Observer { cityContentList ->
            myItemRecyclerViewAdapter = Weather_Adapter(cityContentList,activity)
            recyclerView!!.adapter = myItemRecyclerViewAdapter

        })}
    override fun onDetach() {
        super.onDetach()
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
                Whether_Report_Fragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }

}