package com.vladislav.workout.timer

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.vladislav.workout.R
import com.vladislav.workout.databinding.ActivityMainBinding
import com.vladislav.workout.databinding.FragmentTimerBinding


class TimerFragment : Fragment() {

    private val viewModel: TimerViewModel by viewModels()

    private lateinit var binding: FragmentTimerBinding

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentTimerBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.backgroundColor.observe(viewLifecycleOwner) { color ->
            activity?.let { activity ->
                activity.window?.let { window ->
                    window.statusBarColor = color
                }
            }
        }

        viewModel.isTimerInProcess.observe(viewLifecycleOwner) {
            viewModel.backgroundColor.value = if (it) resources.getColor(R.color.surface, null) else resources.getColor(R.color.yellow, null)
            viewModel.foregroundColor.value = if (it) resources.getColor(R.color.white, null) else resources.getColor(R.color.black, null)
        }

        viewModel.isTimerInProcess.observe(viewLifecycleOwner) {
            viewModel.stateString.value = if (it) getString(R.string.rest) else getString(R.string.work)
        }

//        viewModel.isTimerInProcess.observe(viewLifecycleOwner) {
//            activity?.let { activity ->
//                activity.window?.let { window ->
//                    if (it) {
//                        window.insetsController?.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
//                                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
//                    } else {
//                        window.insetsController?.setSystemBarsAppearance(0,
//                                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
//                    }
//                }
//            }
//        }

        return view
    }
}