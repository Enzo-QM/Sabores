package mx.tec.sabores

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import mx.tec.sabores.ui.navigation.SaboresApp
import mx.tec.sabores.ui.theme.SaboresTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SaboresTheme { SaboresApp() } }
    }
}
