package com.pusatruq.muttabaah.ui.main.scorring.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.pusatruq.muttabaah.R
import com.pusatruq.muttabaah.databinding.ItemScoreBinding
import com.pusatruq.muttabaah.ui.main.scorring.model.ScoreTop
import com.pusatruq.muttabaah.util.Utils
import kotlinx.android.synthetic.main.item_score.view.*


/**
 * Created by cuongpm on 12/10/18.
 */

class ScoreAdapter(
    private var score: List<ScoreTop>, private var mContext: Context
) : RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {
    private lateinit var itemClickListener: ScoreAdapter.OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int, score: ScoreTop)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val binding = DataBindingUtil.inflate<ItemScoreBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_score, parent, false
        )
        return ScoreViewHolder(binding)
    }

    override fun getItemCount() = score.size

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        holder.bindScore(score[position])
//        Toast.makeText(
//            mContext,
//            "Width Px:" + Utils.getScreenWidth(mContext) + " to Dp :" + Utils.pxToDp(
//                mContext,
//                Utils.getScreenWidth(mContext)
//            )+"Info Screen :"+Utils.determineScreenDensityCode(mContext),
//            Toast.LENGTH_LONG
//        ).show()
        val layout = holder.itemView.ln_width
// Gets the layout params that will allow you to resize the layout
        val params = layout.getLayoutParams()
// Changes the height and width to the specified *pixels*
        params.width = Utils.dpToPx(mContext, Utils.getScreenWidth(mContext)) / 27
        layout.setLayoutParams(params)
        holder.itemView.TvInputScore.setOnClickListener {
            itemClickListener.onItemClick(it, position, score[position])
        }
    }

    fun setOnItemClickListener(mItemClickListener: ScoreAdapter.OnItemClickListener) {
        this.itemClickListener = mItemClickListener
    }

    class ScoreViewHolder(private val binding: ItemScoreBinding) : RecyclerView.ViewHolder(binding.root) {
        private val tvNumberHole = binding.TvNumberHole
        private val tvMaxScore = binding.TvMaxScore
        private val tvScore = binding.TvInputScore

        fun bindScore(score: ScoreTop) {
            tvNumberHole.text = score.holeNumber.toString()
            tvMaxScore.text = score.holeMaxScore.toString()
            tvScore.text = score.holeValue.toString()
        }
    }

    fun setData(score: List<ScoreTop>) {
        this.score = score
        notifyDataSetChanged()
    }

    fun setContext(mcontext: Context) {
        this.mContext = mcontext
    }
}