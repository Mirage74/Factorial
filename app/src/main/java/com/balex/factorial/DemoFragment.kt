package com.balex.factorial

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.balex.factorial.databinding.CoroutineBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DemoFragment : Fragment() {

    private var _binding: CoroutineBinding? = null
    private val binding: CoroutineBinding
        get() = _binding ?: throw RuntimeException("CoroutineBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CoroutineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("viewLifecycleOwner:",  "$viewLifecycleOwner")
        printGlobalScopeWithIO()
        printGlobalScope()
        printCoroutineScope()
        printCoroutineScopeWithMain()
        printLifecycleScope()
        printLifecycleScopeWithIO()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val coroutineScope = CoroutineScope(Dispatchers.IO)





    override fun onPause() {
        super.onPause()
        //coroutineScope.cancel()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun printGlobalScopeWithIO() = GlobalScope.launch(Dispatchers.IO) {
        while (isActive) {
            delay(10000)
            Log.d("CoroutineDemo", "[GlobalScope-IO] I'm alive on thread ${Thread.currentThread().name}!")
        }
    }

    private fun printGlobalScope() = GlobalScope.launch {
        while (isActive) {
            delay(10000)
            Log.d("CoroutineDemo", "[GlobalScope] I'm alive on ${Thread.currentThread().name}!")
        }
    }

    private fun printCoroutineScope() = coroutineScope.launch {
        while (isActive) {
            delay(10000)
            Log.d("CoroutineDemo", "[CoroutineScope] I'm alive on ${Thread.currentThread().name}!")
        }
        Log.d("CoroutineDemo", "[CoroutineScope] I'm exiting!")
    }

    private fun printCoroutineScopeWithMain() = coroutineScope.launch(Dispatchers.Main) {
        while (isActive) {
            delay(10000)
            Log.d("CoroutineDemo", "[CoroutineScope-Main] I'm alive on ${Thread.currentThread().name}!")
        }
        Log.d("CoroutineDemo", "[CoroutineScope-Main] I'm exiting!")
    }

    private fun printLifecycleScopeWithIO() = viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
        while (isActive) {
            delay(10000)
            Log.d("CoroutineDemo", "[LifecycleScope-IO] I'm alive on ${Thread.currentThread().name}!")
        }
        Log.d("CoroutineDemo", "[LifecycleScope-IO]  I'm exiting!")
    }

    private fun printLifecycleScope() = lifecycleScope.launch {
        while (isActive) {
            delay(10000)
            Log.d("CoroutineDemo", "[LifecycleScope] I'm alive on ${Thread.currentThread().name}!")
        }
        Log.d("CoroutineDemo", "[LifecycleScope] I'm exiting!")
    }



}