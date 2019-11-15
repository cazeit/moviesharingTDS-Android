package de.tdsoftware.moviesharing.ui.moviedetails

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.data.models.Movie
import de.tdsoftware.moviesharing.ui.BaseFragment

/**
 * Fragment inside MovieDetailsActivity that displays all the important information regarding to a movie
 * as well as handles ratingChange and coverOnClick
 */
class MovieDetailsFragment: BaseFragment() {

    // region public types

    companion object{
        @JvmStatic
        fun newInstance(): MovieDetailsFragment{
            return MovieDetailsFragment()
        }
    }

    // endregion

    // region properties

    private lateinit var mainView: MovieDetailsFragmentView
    private lateinit var movie: Movie
    private lateinit var sharedPreferences: SharedPreferences

    //endregion

    // region lifecycle callbacks

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPreferences = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainView = inflater.inflate(R.layout.fragment_movie_details, container, false) as MovieDetailsFragmentView
        return mainView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        movie = activity?.intent?.getSerializableExtra("movie") as Movie

        setUpMainView()
        super.onViewCreated(view, savedInstanceState)
    }

    // endregion

    // region private API

    /**
     * fill the view with data from video and handle interactions with user
     */
    private fun setUpMainView(){
        mainView.viewListener = object: MovieDetailsFragmentView.Listener{
            override fun onCoverImageClick() {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + movie.id))
                startActivity(intent)
            }

            override fun onRatingChanged(rating: Float) {
                sharedPreferences.edit().putFloat(movie.id + "_rating", rating).apply()
            }
        }
        mainView.title = movie.title
        mainView.secondaryText = movie.secondaryText
        //TODO: missing imageView
        mainView.description = movie.description
        mainView.ratingBarValue = sharedPreferences.getFloat(movie.id + "_rating",0f)
    }

    // endregion

}