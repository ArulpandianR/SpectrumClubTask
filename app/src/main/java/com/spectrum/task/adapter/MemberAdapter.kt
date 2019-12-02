package com.spectrum.task.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.spectrum.task.R
import com.spectrum.task.model.Member
import kotlinx.android.synthetic.main.member_item.view.*

class MemberAdapter : RecyclerView.Adapter<MemberAdapter.ViewHolder>() {

    private val callBack = object : DiffUtil.ItemCallback<Member>() {

        override fun areItemsTheSame(oldItem: Member, newItem: Member): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Member, newItem: Member): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, callBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.member_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var viewHolder = holder
        var member = differ.currentList[position]
        viewHolder.setCompanyData(member)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Member>) {
        differ.submitList(list)
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setCompanyData(member: Member) {

            itemView.memberName.text = member.name?.first + member.name?.last
            itemView.memberAge.text = member.age.toString()
            itemView.memberPhone.text = member.phone
            itemView.memberEmail.text = member.email
            setFav(member, itemView.favIcon)

            itemView.favIcon.setOnClickListener {
                member.isFav = !member.isFav
                setFav(member, itemView.favIcon)
            }
        }

        /** Update the ui only not updated to server  **/
        private fun setFav(member: Member, favView: ImageView) {
            if (member.isFav) {
                favView.setImageResource(R.drawable.ic_favorite_24dp)
            } else {
                favView.setImageResource(R.drawable.ic_favorite_border_black_24dp)
            }
        }
    }

}