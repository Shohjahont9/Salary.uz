package shohjahon.salaryuz.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.activity_main.*
import shohjahon.salaryuz.R
import shohjahon.salaryuz.fragments.*

class MainActivity : AppCompatActivity() {
    //
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.frameLayout, HomeFragment()).commitAllowingStateLoss()

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        createDrawerLayout()

    }



    private fun createDrawerLayout() {
        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout2,
            toolbar,
            R.string.open,
            R.string.close
        )
        drawerLayout2.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        nav_view2.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    val manager = supportFragmentManager
                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.frameLayout, HomeFragment()).commitAllowingStateLoss()

                    drawerLayout2.closeDrawer(GravityCompat.START)
                }

                R.id.lavozim -> {
                    val manager = supportFragmentManager
                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.frameLayout, LavozimlarFragment())
                        .commitAllowingStateLoss()

                    drawerLayout2.closeDrawer(GravityCompat.START)
                }

                R.id.ustamalar -> {
                    val manager = supportFragmentManager
                    val transaction = manager.beginTransaction()
                    transaction.replace(R.id.frameLayout, UstamalarFragment())
                        .commitAllowingStateLoss()

                    drawerLayout2.closeDrawer(GravityCompat.START)
                }

            }
            true
        }

    }

    override fun onBackPressed() {
        if (drawerLayout2.isDrawerOpen(GravityCompat.START)) {
            drawerLayout2.closeDrawer(GravityCompat.START)
        } else
            super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}