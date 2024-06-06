package com.caresaas.app.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.caresaas.app.R
import com.caresaas.app.core.ResultState
import com.caresaas.app.data.model.DtoModel
import com.caresaas.app.databinding.FragmentMedicationBinding
import com.caresaas.app.features.MainActivity
import com.caresaas.app.features.login.LoginViewModel
import com.caresaas.app.util.extensions.gone
import com.caresaas.app.util.extensions.show
import com.caresaas.app.util.extensions.showDialog
import com.caresaas.app.util.helpers.CareSaasSharePreference
import com.caresaas.app.util.helpers.GenericListAdapter
import com.caresaas.app.util.helpers.KEY
import com.caresaas.app.util.helpers.KEY.CARE_HOME_ID
import com.caresaas.app.util.helpers.KEY.SHORT_CODE
import com.caresaas.app.util.ui.ProgressLoader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MedicationFragment : Fragment() {
    private lateinit var binding: FragmentMedicationBinding
    private val homeViewModel: HomeViewModel by viewModels()
    @Inject
    lateinit var progressLoader: ProgressLoader

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMedicationBinding.inflate(layoutInflater)
        setObservers()
        return binding.root
    }

    private fun setListItems(it: MutableList<DtoModel.TasksData>) {
        if (it.isNotEmpty()) {
            binding.recyclerView.adapter = GenericListAdapter(
                it,
                R.layout.tab_item
            )
            { view, item, _ ->
                val title = view.findViewById<TextView>(R.id.title_layout)
                val assignee = view.findViewById<TextView>(R.id.person_text)
                val roomNumber = view.findViewById<TextView>(R.id.room_text)
                val bedNumber = view.findViewById<TextView>(R.id.bed_text)
                val timeOfDay = view.findViewById<TextView>(R.id.time_text)

                title.text = item.action
                assignee.text = item.taskAssignments[0].assignee.firstName
                roomNumber.text = StringBuilder("Rm ${item.taskDetailRef}")
                bedNumber.text = StringBuilder("Bed ${item.taskDetailRef}")
                timeOfDay.text = item.hourOfDay.uppercase()
            }
            binding.noTasks.gone()
        } else {
            binding.noTasks.show()
        }
    }

    override fun onResume() {
        super.onResume()
        val shortCode = CareSaasSharePreference(requireContext()).getString(SHORT_CODE)
        val careHomeId = CareSaasSharePreference(requireContext()).getString(CARE_HOME_ID)
        val userId = CareSaasSharePreference(requireContext()).getString(KEY.USER_ID)
        homeViewModel.getTasks(shortCode = shortCode, careHomeId = careHomeId, assignee = userId)
    }


    private fun setObservers(){
        homeViewModel.taskDetails.observe(viewLifecycleOwner) {
            val state = it.getContentIfNotHandled() ?: return@observe
            when (state) {
                is ResultState.Loading -> {progressLoader.show("Fetching medications...")}
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
                    setListItems(tasksList.filter { data -> data.taskType == "MEDICATION_ADMIN"}.toMutableList())
                }
            }
        }
    }
}