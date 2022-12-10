package com.fakhry.movie_compose.presentation.component

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.fakhry.movie_compose.R
import com.fakhry.movie_compose.common.theme.MovieComposeTheme


@Composable
fun AppBarDefault(
    title: String,
    navigateBack: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = {
                navigateBack()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
        },
        title = {
            Text(title)
        },
    )
}

@Preview
@Composable
fun AppBarDefaultPreview() {
    MovieComposeTheme {
        AppBarDefault(title = "Testing App Bar", navigateBack = {})
    }
}
