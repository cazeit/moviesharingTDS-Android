package de.tdsoftware.moviesharing.ui.video

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.tdsoftware.moviesharing.R
import de.tdsoftware.moviesharing.data.models.VideoApp
import de.tdsoftware.moviesharing.ui.BaseFragment
import de.tdsoftware.moviesharing.util.VideoUpdateEvent
import org.greenrobot.eventbus.EventBus

class VideoDetailsFragment: BaseFragment() {

    private lateinit var mainView: VideoDetailsFragmentView
    private lateinit var video: VideoApp
    private lateinit var sharedPreferences: SharedPreferences

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPreferences = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainView = inflater.inflate(R.layout.fragment_video_details, container, false) as VideoDetailsFragmentView
        return mainView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        video = activity?.intent?.getSerializableExtra("video") as VideoApp

        setUpMainView()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setUpMainView(){
        mainView.viewListener = object: VideoDetailsFragmentView.Listener{
            override fun onRatingChanged(rating: Float) {
                video.rating = rating
                sharedPreferences.edit().putFloat(video.id + "_rating", video.rating).apply()
                val event = VideoUpdateEvent()
                event.video = video
                EventBus.getDefault().post(event)
            }
        }
        mainView.videoTitle = video.title
        mainView.videoSecondaryText = video.secondaryText
        //TODO: missing imageView
        mainView.videoDescription = video.description
        mainView.videoRatingBarValue = video.rating
    }

    companion object{
        @JvmStatic
        fun newInstance(): VideoDetailsFragment{
            return VideoDetailsFragment()
        }
    }
}