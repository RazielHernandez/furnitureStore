package com.fekea.furniturestore.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fekea.furniturestore.R
import com.fekea.furniturestore.firebase.model.DBUser
import com.fekea.furniturestore.firebase.service.UserModel


class UserFragment : Fragment() {

    private lateinit var userModel: UserModel
    lateinit var v: View

    lateinit var loginButton: Button
    lateinit var signupButton: Button

    companion object {
        const val TAG = "com.fekea.furniturestore:UserFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e(TAG, "onCreateView")
        v = inflater.inflate(R.layout.fragment_user, container, false)

        loginButton = v.findViewById<Button>(R.id.button_login)
        loginButton.setOnClickListener {
            showLoginDialog(v)
        }

        signupButton = v.findViewById(R.id.button_signup)
        signupButton.setOnClickListener {
            showSignUpDialog(v)
        }

        v.findViewById<LinearLayout>(R.id.settings_option_logout).setOnClickListener {
            showLogoutDialog(v)
        }

        v.findViewById<LinearLayout>(R.id.settings_option_account).setOnClickListener {
            showAccountDialog(v)
        }

        v.findViewById<LinearLayout>(R.id.settings_option_password).setOnClickListener {
            showPasswordDialog(v)
        }

        v.findViewById<LinearLayout>(R.id.settings_option_about).setOnClickListener {
            showAboutDialog(v)
        }

        v.findViewById<LinearLayout>(R.id.settings_option_notification).setOnClickListener {
            val intent = Intent(activity, NotificationActivity::class.java)
            startActivity(intent)
        }

        v.findViewById<LinearLayout>(R.id.settings_option_addresses).setOnClickListener {
            val intent = Intent(activity, AddressActivity::class.java)
            startActivity(intent)
        }

        v.findViewById<LinearLayout>(R.id.settings_option_cards).setOnClickListener {
            val intent = Intent(activity, CreditCardActivity::class.java)
            startActivity(intent)
        }


        userModel = ViewModelProvider(this)[UserModel::class.java]
        setupLiveDataListener()

        MainActivity.user?.let { showView(it.enable) }
        return v

    }

    private fun showView(isLogged: Boolean) {
        if (isLogged) {
            v.findViewById<Button>(R.id.button_login).visibility = View.GONE
            v.findViewById<Button>(R.id.button_signup).visibility = View.GONE
            v.findViewById<LinearLayout>(R.id.user_settings_view).visibility = View.VISIBLE
        } else {
            v.findViewById<Button>(R.id.button_login).visibility = View.VISIBLE
            v.findViewById<Button>(R.id.button_signup).visibility = View.VISIBLE
            v.findViewById<LinearLayout>(R.id.user_settings_view).visibility = View.GONE
        }
    }

    fun loadFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        if (transaction != null) {
            transaction.replace(R.id.container,fragment)
            transaction.commit()
        }
    }


    private fun setupLiveDataListener() {
        userModel.userLiveData.observe(viewLifecycleOwner) {
            Log.e(TAG, "User type ${it.type} enabled: ${it.enable}")
            MainActivity.user = it
            showView(it.enable)
        }

        userModel.idLiveData.observe(viewLifecycleOwner) {
            Log.e(TAG, "User id $it")
            //showView(true)
        }

        userModel.userLogged.observe(viewLifecycleOwner) {
            Log.e(TAG, "User logged successfully: $it")
            //showView(it)
        }
    }

    private fun showAccountDialog(view: View) {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity?.layoutInflater
        builder.setTitle("Account info")
        val dialogLayout = inflater?.inflate(R.layout.dialog_account, null)
        val textName  = dialogLayout?.findViewById<EditText>(R.id.account_name)
        val textLastName = dialogLayout?.findViewById<EditText>(R.id.account_lastname)
        val textTelephone = dialogLayout?.findViewById<EditText>(R.id.account_telephone)

        textName?.setText(MainActivity.user?.name)
        textLastName?.setText(MainActivity.user?.lastName)
        textTelephone?.setText(MainActivity.user?.telephone)

        builder.setView(dialogLayout)
        builder.setPositiveButton("Save") { dialogInterface, i ->
            MainActivity.user?.name = textName?.text.toString()
            MainActivity.user?.lastName = textLastName?.text.toString()
            MainActivity.user?.telephone = textTelephone?.text.toString()
            MainActivity.user?.let { userModel.updateUser(it) }
        }
        builder.setNegativeButton("Cancel") { dialogInterface, i ->
            Log.e(TAG, "Cancel")
        }
        builder.show()
    }

    private fun showPasswordDialog(view: View) {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity?.layoutInflater
        builder.setTitle("Account info")
        val dialogLayout = inflater?.inflate(R.layout.dialog_password, null)
        val textActual  = dialogLayout?.findViewById<EditText>(R.id.password_actual)
        val textNew = dialogLayout?.findViewById<EditText>(R.id.password_new)
        val textConfirm = dialogLayout?.findViewById<EditText>(R.id.password_confirm)

        builder.setView(dialogLayout)
        builder.setPositiveButton("Save") { dialogInterface, i ->

            if (textActual?.text.toString() == MainActivity.user?.password) {
                if (textNew?.text.toString() == textConfirm?.text.toString()) {
                    MainActivity.user?.let {
                        it.password = textNew?.text.toString()
                        userModel.updateUser(it)
                    }
                } else {
                    Log.e(TAG, "The new password doesn't match, try again")
                }
            } else {
                Log.e(TAG, "Your actual password doesn't match")
            }
        }
        builder.setNegativeButton("Cancel") { dialogInterface, i ->
            Log.e(TAG, "Cancel")
        }
        builder.show()
    }

    private fun showLogoutDialog(view: View) {
        val builder = AlertDialog.Builder(activity)

        with(builder)
        {
            setTitle("Logout")
            setMessage("Are you sure to logout the App?")
            setPositiveButton("Yes") { dialogInterface, i ->
                MainActivity.user = DBUser()
                showView(false)
            }
            setNegativeButton("Keep me logged") { dialogInterface, i -> }
            show()
        }
    }

    private fun showLoginDialog(view: View) {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity?.layoutInflater
        builder.setTitle("Login")
        val dialogLayout = inflater?.inflate(R.layout.dialog_login, null)
        val textLogin  = dialogLayout?.findViewById<EditText>(R.id.user_login)
        val textPassword = dialogLayout?.findViewById<EditText>(R.id.user_password)
        builder.setView(dialogLayout)
        builder.setPositiveButton("OK") { dialogInterface, i ->
            Log.e(TAG, "Login ok pressed")
            if (textLogin?.text?.isNotBlank() == true){
                userModel.loginUser(textLogin?.text.toString(),textPassword?.text.toString())
            }
        }
        builder.setNegativeButton("Cancel") { dialogInterface, i ->
            Log.e(TAG, "Cancel")
        }
        builder.show()
    }

    private fun showAboutDialog(view: View) {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity?.layoutInflater
        builder.setTitle("About this App")
        val dialogLayout = inflater?.inflate(R.layout.dialog_about, null)
        builder.setView(dialogLayout)
        builder.setPositiveButton("OK") { dialogInterface, i ->
            Log.e(TAG, "Login ok pressed")
        }
        builder.show()
    }

    private fun showSignUpDialog(view: View) {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity?.layoutInflater
        builder.setTitle("Sign up")
        val dialogLayout = inflater?.inflate(R.layout.dialog_signup, null)
        val textLogin  = dialogLayout?.findViewById<EditText>(R.id.signup_mail)
        val textPassword = dialogLayout?.findViewById<EditText>(R.id.signup_password)
        val textConfirm = dialogLayout?.findViewById<EditText>(R.id.signup_confirm)
        builder.setView(dialogLayout)
        builder.setPositiveButton("OK") { dialogInterface, i ->
            Log.e(TAG, "Signup ok pressed")
            if (textLogin?.text?.isNotEmpty() == true
                && textPassword?.text?.isNotEmpty() == true
                && textConfirm?.text?.isNotEmpty() == true) {
                if (textPassword.text.toString() == textConfirm.text.toString()) {
                    val newUser = DBUser(
                        name = "",
                        lastName = "",
                        login = textLogin.text.toString(),
                        password = textPassword.text.toString(),
                        email = textLogin.text.toString(),
                        enable = true,
                        telephone = "",
                        type = "APP",
                    )
                    userModel.insertUser(newUser)
                }else{
                    Log.e(TAG,"Password don't match")
                }
            }else {
                Log.e(TAG, "A field is missing")
            }
        }
        builder.setNegativeButton("Cancel") { dialogInterface, i ->
            Log.e(TAG, "Cancel")
        }
        builder.show()
    }

}