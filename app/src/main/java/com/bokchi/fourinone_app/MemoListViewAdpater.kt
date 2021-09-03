package com.bokchi.fourinone_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class MemoListViewAdpater(val List:MutableList<MemoDataModel>):BaseAdapter() {
    override fun getCount(): Int {
        return List.size
    }

    override fun getItem(p0: Int): Any {
        return List[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var convertViewV = p1
        if(convertViewV == null){
            convertViewV = LayoutInflater.from(p2?.context).inflate(R.layout.memolistview_item,p2,false)
        }

        val memo_date = convertViewV?.findViewById<TextView>(R.id.memolistViewDateAreaId)
        val memo_memo = convertViewV?.findViewById<TextView>(R.id.memolistViewMemoAreaId)

        memo_date!!.text = List[p0].memo_date
        memo_memo!!.text = List[p0].memo_memo

        return convertViewV!!
    }
}