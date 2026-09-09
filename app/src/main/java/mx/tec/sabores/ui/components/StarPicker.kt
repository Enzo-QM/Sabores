package mx.tec.sabores.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StarPicker(value: Int, onValueChange: (Int) -> Unit, modifier: Modifier = Modifier) {
    Row(modifier) {
        (1..5).forEach { n ->
            IconButton(onClick = { onValueChange(n) }) {
                // No existe una estrella hueca en material-icons-core:
                // la diferencia entre marcada y sin marcar la hace el TINTE.
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "$n estrella${if (n == 1) "" else "s"}",
                    tint = if (n <= value) Color(0xFFF5A623)
                           else MaterialTheme.colorScheme.outlineVariant,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}
