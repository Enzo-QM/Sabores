package mx.tec.sabores.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

/** Las estrellas de UNA resena. Distinto de RatingLabel, que resume MUCHAS. */
@Composable
fun StarsRow(stars: Int, modifier: Modifier = Modifier) {
    Row(modifier.semantics { contentDescription = "$stars de 5 estrellas" }) {
        repeat(stars) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color(0xFFF5A623),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
