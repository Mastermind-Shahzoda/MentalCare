package mening.dasturim.technovationchalange.ui.main.user.consultation.ii

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.alan.alansdk.AlanCallback
import com.alan.alansdk.AlanConfig
import com.alan.alansdk.button.AlanButton
import com.alan.alansdk.events.EventCommand
import com.google.android.material.snackbar.Snackbar
import mening.dasturim.technovationchalange.R
import org.json.JSONException
import org.json.JSONObject




class IrtificialFragment : Fragment() {

    /// Add AlanButton variable
    private var alanButton: AlanButton? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_irtificial, container, false)




/// Define the project key
        val config = AlanConfig.builder().setProjectId("341a03c30cabb7f708f8baf9b34f104b2e956eca572e1d8b807a3e2338fdd0dc/stage").build()
        alanButton = view.findViewById(R.id.alan_button)
        alanButton?.initWithConfig(config)

        val alanCallback: AlanCallback = object : AlanCallback() {
            /// Handle commands from Alan Studio
            override fun onCommand(eventCommand: EventCommand) {
                try {
                    val command = eventCommand.data

                    val commandName = command.getJSONObject("data").getString("command")
//                    when(commandName){
//                        "showMessage"->{
//                           // Snackbar.make("Hello",Snackbar.LENGTH_LONG).show()
//                            Toast.makeText(context,"Hello",Toast.LENGTH_LONG).show()
//                        }
//                    }
                    Log.d("AlanButton", "onCommand: commandName: $commandName")
                } catch (e: JSONException) {
                    e.message?.let { Log.e("AlanButton", it) }
                }
            }
        }

/// Register callbacks
        alanButton?.registerCallback(alanCallback)
        setVisualState()
        return view
    }

    /// Set up a visual state
    fun setVisualState() {
        /// Provide any params
        val params = JSONObject()
        try {
            params.put("data", "your data")
        } catch (e: JSONException) {
            Log.e("AlanButton", e.message!!)
        }
        alanButton!!.setVisualState(params.toString())
    }
}