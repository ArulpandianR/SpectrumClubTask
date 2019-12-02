package com.spectrum.task.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.spectrum.task.R
import com.spectrum.task.model.ClubCompany
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.company_item.view.*

class CompanyAdapter : RecyclerView.Adapter<CompanyAdapter.ViewHolder>() {

    private val callBack = object : DiffUtil.ItemCallback<ClubCompany>() {

        override fun areItemsTheSame(oldItem: ClubCompany, newItem: ClubCompany): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ClubCompany, newItem: ClubCompany): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, callBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.company_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var viewHolder = holder
        var company = differ.currentList[position]
        viewHolder.setCompanyData(company)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<ClubCompany>) {
        differ.submitList(list)
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setCompanyData(clubCompany: ClubCompany) {

            Picasso.get().load(clubCompany.logo)
                .placeholder(R.drawable.ic_person_black_24dp).into(itemView.companyLogo)
            itemView.companyName.text = clubCompany.company
            itemView.about.text = clubCompany.about
            itemView.companyWebsite.text = clubCompany.website
            setFav(clubCompany, itemView.favIcon)
            setFollow(clubCompany, itemView.follow)

            itemView.follow.setOnClickListener {
                clubCompany.isfollowed = !clubCompany.isfollowed
                setFollow(clubCompany, itemView.follow)
            }
            itemView.favIcon.setOnClickListener {
                clubCompany.isFav = !clubCompany.isFav
                setFav(clubCompany, itemView.favIcon)
            }
        }

        /** Update the ui only not updated to server  **/
        private fun setFollow(clubCompany: ClubCompany, followView: ImageView) {
            if (clubCompany.isfollowed) {
              /*  Toast.makeText(
                    followView.context,
                    " ${clubCompany.company} is Following",
                    Toast.LENGTH_SHORT
                ).show()*/
                followView.setImageResource(R.drawable.ic_following_red_900_24dp)
            } else {
               /* Toast.makeText(
                    followView.context,
                    "${clubCompany.company} is UnFollowed",
                    Toast.LENGTH_SHORT
                ).show()*/
                followView.setImageResource(R.drawable.ic_follow_black_24dp)
            }
        }

        /** Update the ui only not updated to server  **/
        private fun setFav(clubCompany: ClubCompany, favView: ImageView) {
            if (clubCompany.isFav) {
                /*Toast.makeText(
                    favView.context,
                    "${clubCompany.company} Favorite",
                    Toast.LENGTH_SHORT
                ).show()*/
                favView.setImageResource(R.drawable.ic_favorite_24dp)
            } else {
                /*Toast.makeText(
                    favView.context,
                    "${clubCompany.company} UnFavorite",
                    Toast.LENGTH_SHORT
                ).show()*/
                favView.setImageResource(R.drawable.ic_favorite_border_black_24dp)
            }
        }
    }

}