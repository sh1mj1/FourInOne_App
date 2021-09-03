package com.bokchi.fourinone_app

import android.animation.Animator
import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class MainActivity : AppCompatActivity() {
    /*// layout 확장
    private var currentAnimator: Animator? = null
    private var shortAnimationDuration: Int = 0*/

    val dataModelList = mutableListOf<MemoDataModel>()

    private var time = 0
    private var isRunning = false
    private var timerTask: Timer? = null
    private var index: Int = 1
    private lateinit var secText: TextView
    private lateinit var milliText: TextView
    private lateinit var minText: TextView
    private lateinit var startBtn: Button
    private lateinit var resetBtn: Button
    private lateinit var recordBtn: Button
    private lateinit var lap_Layout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*val thumb1View: View = findViewById(R.id.gotoCountBtn)
        thumb1View.setOnClickListener({
            zoomImageFromThumb(thumb1View, R.id.countId)
        })

        shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)*/


        val database = Firebase.database
        val myRef = database.getReference("myMemo")

        val memoListView = findViewById<ListView>(R.id.memoLVId)
        val adapter_list = MemoListViewAdpater(dataModelList)
        memoListView.adapter = adapter_list

        myRef.child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    dataModelList.clear()

                    for (dataModel in snapshot.children) {
                        Log.d("DATA", dataModel.toString())
                        dataModelList.add(dataModel.getValue(MemoDataModel::class.java)!!)
                    }

                    adapter_list.notifyDataSetChanged()
                    Log.d("MEMODATAMODEL", dataModelList.toString())
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        val writeBtnV = findViewById<ImageView>(R.id.memoWriteBtn)
        writeBtnV.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.memo_dialogue, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Exercise Memo Dialogue")

            val mAlertDialog = mBuilder.show()

            val DateSelectBtnV = mAlertDialog.findViewById<Button>(R.id.dateSelectBtnB)
            var memoDateText = ""
            DateSelectBtnV.setOnClickListener {

                val today = GregorianCalendar()
                val year: Int = today.get(Calendar.YEAR)
                val month: Int = today.get(Calendar.MONTH)
                val date: Int = today.get(Calendar.DATE)

                val dlg = DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                        DateSelectBtnV.setText("${p1}, ${p2 + 1} ,${p3}")

                        memoDateText = "${p1}, ${p2 + 1} ,${p3}"
                    }
                }, year, month, date)
                dlg.show()
            }

            val saveBtnV = mAlertDialog.findViewById<Button>(R.id.saveBtnB)
            saveBtnV.setOnClickListener {

                val memo_memo =
                    mAlertDialog.findViewById<EditText>(R.id.exerciseMemoId).text.toString()
                // Write a message to the database
                val database = Firebase.database
                val myRef = database.getReference("myMemo").child(Firebase.auth.currentUser!!.uid)
                val model = MemoDataModel(memoDateText, memo_memo)

                myRef.push().setValue(model)
                mAlertDialog.dismiss()
            }

        }
        //View inflate
        secText = findViewById(R.id.secText)
        milliText = findViewById(R.id.milliText)
        minText = findViewById(R.id.minText)
        startBtn = findViewById(R.id.startBtn)
        resetBtn = findViewById(R.id.resetBtn)
        recordBtn = findViewById(R.id.recordBtn)
        lap_Layout = findViewById(R.id.lap_Layout)

        startBtn.setOnClickListener {
            isRunning = !isRunning
            if (isRunning) start() else pause()
        }
        resetBtn.setOnClickListener {
            reset()
        }
        recordBtn.setOnClickListener {
            if (time != 0) lapTime()
        }


    }
//-----------------------------














    // ------------------------------

    private fun start() {
        startBtn.text = "중지"
        timerTask =
            kotlin.concurrent.timer(period = 10) { //반복주기는 peroid 프로퍼티로 설정, 단위는 1000분의 1초 (period = 1000, 1초)
                time++ // 0.01초마다 time 1씩 증가하게 됩니다
                val min = time / 6000
                val sec_ss = time/100
                val sec = sec_ss%60
                val milli = time % 100 //millisec


                // UI조작을 위한 메서드
                runOnUiThread {
                    minText.text = "$min"
                    secText.text = "$sec"
                    milliText.text = "$milli"
                }
            }
    }

    private fun pause() {
        startBtn.text = "재실행"
        timerTask?.cancel();
    }

    private fun reset() {
        timerTask?.cancel() // timerTask가 null이 아니라면 cancel() 호출

        time = 0 // 시간저장 변수 초기화
        isRunning = false //
        minText.text = "0"  // min = 0
        secText.text = "0" // sec = 0
        milliText.text = "00" // millisec = 0

        startBtn.text = "시작"
        lap_Layout.removeAllViews() // Layout에 추가한 기록View 모두 삭제
        index = 1
    }

    private fun lapTime() {
        val lapTime = time // 함수 호출 시 시간(time) 저장

        // apply() 스코프 함수로, TextView를 생성과 동시에 초기화
        val textView = TextView(this).apply {
            setTextSize(20f) // fontSize 20 설정
        }
        textView.text = "${lapTime/6000}. ${lapTime / 100}. ${lapTime % 100}" // 출력할 시간 설정

        lap_Layout.addView(textView, 0) // layout에 추가, (View, index) 추가할 위치(0 최상단 의미)
        index++ // 추가된 View의 개수를 저장하는 index 변수
    }
}


