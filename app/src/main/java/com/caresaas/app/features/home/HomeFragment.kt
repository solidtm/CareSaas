package com.caresaas.app.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.caresaas.app.R
import com.caresaas.app.core.ResultState
import com.caresaas.app.databinding.FragmentHomeBinding
import com.caresaas.app.features.MainActivity
import com.caresaas.app.features.home.adapter.ViewPagerAdapter
import com.caresaas.app.util.extensions.gone
import com.caresaas.app.util.extensions.show
import com.caresaas.app.util.extensions.showDialog
import com.caresaas.app.util.helpers.CareSaasSharePreference
import com.caresaas.app.util.helpers.KEY
import com.caresaas.app.util.ui.ProgressLoader
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private val homeViewModel: HomeViewModel by viewModels()
    @Inject
    lateinit var progressLoader: ProgressLoader
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentHomeBinding.inflate(layoutInflater)
        viewPagerAdapter = ViewPagerAdapter(requireActivity())
        mainActivity = requireActivity() as MainActivity
        setObservers()
        binding.apply {
            viewPager.adapter = viewPagerAdapter
            tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    viewPager.currentItem = tab.position
                    if (tab.position == 1) {  // Assuming "Activities" tab is at position 1
                        mainActivity.changeMiddleNavItem()
                    }else{
                        mainActivity.revertMiddleNavItem()
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}

                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
            viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    tabLayout.getTabAt(position)?.select()
                    if (position == 1) {  // Assuming "Activities" tab is at position 1
                        mainActivity.changeMiddleNavItem()
                    }else{
                        mainActivity.revertMiddleNavItem()
                    }
                }
            })

            buttonClockIn.setOnClickListener{
                takeBreakLayout.show()
                buttonClockIn.gone()
                labelClockedIn.show()
                clockInTv.text = resources.getString(R.string.begin_task)
            }

            takeBreakBtn.setOnClickListener{

            }

            clockOutBtn.setOnClickListener{
                takeBreakLayout.gone()
                buttonClockIn.show()
                labelClockedIn.gone()
                clockInTv.text = resources.getString(R.string.clock_in_to_begin)
            }
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val shortCode = CareSaasSharePreference(requireContext()).getString(KEY.SHORT_CODE)
        val careHomeId = CareSaasSharePreference(requireContext()).getString(KEY.CARE_HOME_ID)
        val userId = CareSaasSharePreference(requireContext()).getString(KEY.USER_ID)
        val givenName = CareSaasSharePreference(requireContext()).getString(KEY.FIRST_NAME)
        binding.greeting.text = StringBuilder("Hi, $givenName!").toString()
        homeViewModel.getTasks(shortCode = shortCode, careHomeId = careHomeId, assignee = userId)
    }

    private fun setObservers(){
        homeViewModel.taskDetails.observe(viewLifecycleOwner) {
            val state = it.getContentIfNotHandled() ?: return@observe
            when (state) {
                is ResultState.Loading -> {progressLoader.show("Fetching tasks...")}
                is ResultState.Error -> {
                    progressLoader.hide()
                    requireContext().showDialog(
                        title = "Error",
                        message = state.error
                    ){}
                }
                is ResultState.Success -> {
                    progressLoader.hide()
                    val tasksList = state.data.data


                }
            }
        }
    }
}