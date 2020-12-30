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
import kotlinx.android.synthetic.main.activity_detail.view.img_user
import kotlinx.android.synthetic.main.item_cardview_user.view.*

class UserAdapter :
    RecyclerView.Adapter<UserAdapter.CardViewViewHolder>() {
    private var users = arrayListOf<User?>()

    fun setData(items: List<User?>) {
        if (items.isNullOrEmpty()) return
        users.clear()
        users.addAll(items)
        notifyDataSetChanged()
    }

    inner class CardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User?) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(user?.avatarUrl)
                    .apply(RequestOptions().override(300, 300))
                    .into(img_user)
                tv_username.text = user?.login
                itemView.setOnClickListener {
                    Toast.makeText(
                        itemView.context,
                        "Kamu memilih " + user?.login,
                        Toast.LENGTH_SHORT
                    ).show()
                    val moveDetail = Intent(itemView.context, DetailActivity::class.java)
                    moveDetail.putExtra(DetailActivity.EXTRA_USER, user?.login)
                    moveDetail.putExtra(FollFragment.EXTRA_USER, user?.login)
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
        return users.size
    }

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        holder.bind(users[position])
    }

}