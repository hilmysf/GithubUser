package UI

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.githubuser.R
import data.User
import kotlinx.android.synthetic.main.activity_detail.view.*

class UserAdapter(private val listUser: List<User>) :
    RecyclerView.Adapter<UserAdapter.CardViewViewHolder>() {
    internal var users = arrayListOf<User?>()

    fun setData(items: List<User?>) {
        users.clear()
        users.addAll(items)
        notifyDataSetChanged()
    }
    inner class CardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .apply(RequestOptions().override(600, 800))
                    .into(img_user)
                tv_username_detail.text = user.login

                itemView.setOnClickListener {
                    Toast.makeText(
                        itemView.context,
                        "Kamu memilih " + user.login,
                        Toast.LENGTH_SHORT
                    ).show()
                    val moveDetail = Intent(itemView.context, DetailActivity::class.java)
                    moveDetail.putExtra(DetailActivity.EXTRA_USER, user)
                    context.startActivity(moveDetail)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewViewHolder {
        var view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cardview_user, parent, false)
        return CardViewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

//    fun setFilter(filterList: ArrayList<User>) {
//        users = arrayListOf()
//        users.addAll(filterList)
//        notifyDataSetChanged()
//    }
}