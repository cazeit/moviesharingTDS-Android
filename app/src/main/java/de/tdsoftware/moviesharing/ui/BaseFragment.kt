package de.tdsoftware.moviesharing.ui

import android.content.Intent
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import de.tdsoftware.moviesharing.ui.main.movies.adapter.VideoBaseAdapter
import de.tdsoftware.moviesharing.ui.video.VideoDetailsActivity

/**
 * do it like that? because at the moment all fragments need it
 */
abstract class BaseFragment : Fragment(), VideoBaseAdapter.ItemClickListener {

    override fun onRecyclerItemClick(position: Int, imageView: ImageView) {
        Toast.makeText(context, "Item Index: $position was clicked!", Toast.LENGTH_LONG).show()
        val intent = Intent(context, VideoDetailsActivity::class.java)
        startActivity(intent)
    }

}
