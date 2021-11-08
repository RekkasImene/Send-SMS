package fr.mastersid.rekkas.sendsms.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.telephony.SmsManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import fr.mastersid.rekkas.sendsms.viewModels.QuestionsViewModel
import fr.mastersid.rekkas.sendsms.adapters.QuestionsListAdapter
import fr.mastersid.rekkas.sendsms.databinding.FragmentQuestionsBinding
import fr.mastersid.rekkas.sendsms.models.RequestState


@AndroidEntryPoint
class QuestionsFragment : Fragment() {

    private lateinit var _binding: FragmentQuestionsBinding
    private val questionModel: QuestionsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuestionsBinding.inflate(inflater)
        return _binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val requestPermissionLauncher =
            registerForActivityResult (
                ActivityResultContracts.RequestPermission ()
            ) { isGranted : Boolean ->
                if ( isGranted ) {
                    val smsManager = SmsManager.getDefault()
                    smsManager.sendTextMessage(" 5554 ", null, "message", null, null)

                } else {
                    Snackbar . make (
                        _binding.root ,
                        " Permission needed ",
                        Snackbar . LENGTH_LONG
                    ). setAction ("Go to settings ") {
                        startActivity (
                            Intent(
                                Settings. ACTION_APPLICATION_DETAILS_SETTINGS ,
                                Uri.parse (" package :" + requireActivity (). packageName )
                            )
                        )
                    }. show ()
                }
            }

        val questionsListAdapter = QuestionsListAdapter(){
            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.SEND_SMS
                ) == PackageManager.PERMISSION_GRANTED
                -> {
                    Log.d("Send SMS", "Permission granted")
                    val smsManager = SmsManager.getDefault()
                    smsManager.sendTextMessage(" 5554 ", null, it.title, null, null)
                }
                shouldShowRequestPermissionRationale (
                    Manifest.permission.SEND_SMS
                )
                ->{
                    Snackbar.make (
                        _binding .root ,
                        " Permission is needed to send SMS ",
                        Snackbar . LENGTH_LONG
                    ). setAction (" Allow ") {
                        requestPermissionLauncher.launch ( Manifest.permission.SEND_SMS)
                    }. show ()
                }
                else -> {
                    Log.d("Send SMS", "permission denied")
                    requestPermissionLauncher
                        .launch ( Manifest.permission.SEND_SMS)
                }
            }
        }

        _binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = questionsListAdapter
        }

        _binding.refresh.setOnRefreshListener {
            questionModel.updateQuestionsList()
        }


        questionModel.questionList.observe(viewLifecycleOwner) { value ->
            _binding.refresh.isRefreshing = false
            questionsListAdapter.submitList(value)
        }

        questionModel.requestState.observe(viewLifecycleOwner) { value ->
            when(value) {
                RequestState.PENDING ->
                    _binding.refresh.isRefreshing = true
                RequestState.NONE_OR_DONE ->
                    _binding.refresh.isRefreshing = false
            }
        }






    }

}