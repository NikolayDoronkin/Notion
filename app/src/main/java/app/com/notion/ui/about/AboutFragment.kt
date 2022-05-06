package app.com.notion.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import app.com.notion.R
import app.com.notion.core.BaseFragment
import app.com.notion.core.extension.getViewModelExt
import app.com.notion.databinding.FragmentAboutBinding

class AboutFragment : BaseFragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    private lateinit var aboutViewModel: AboutViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        aboutViewModel = getViewModelExt { AboutViewModel() }

        binding.infoView.text = HtmlCompat.fromHtml(getString(R.string.info_view), HtmlCompat.FROM_HTML_MODE_LEGACY)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}