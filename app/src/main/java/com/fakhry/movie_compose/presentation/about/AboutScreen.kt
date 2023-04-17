package com.fakhry.movie_compose.presentation.about

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fakhry.movie_compose.R
import com.fakhry.movie_compose.common.theme.MovieComposeTheme
import com.fakhry.movie_compose.common.theme.SansSerif
import com.fakhry.movie_compose.common.values.spacingLarger
import com.fakhry.movie_compose.common.values.spacingRegular
import com.fakhry.movie_compose.common.values.spacingSmaller
import com.fakhry.movie_compose.common.values.spacingTiny
import com.fakhry.movie_compose.presentation.component.AppBarDefault

@Composable
fun AboutScreen(
    navigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            AppBarDefault(
                title = stringResource(R.string.about_page),
                navigateBack = { navigateBack() })
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            ProfileCompose()
        }
    }
}

@Composable
fun ProfileCompose(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val emailAddress = "fakhrymubarak@gmail.com"
    val emailSubject = "Your Dicoding Submission Has Been Checked!"
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        item {
            Spacer(modifier = Modifier.height(spacingLarger))
            Card(
                shape = CircleShape,
                elevation = 2.dp,
                modifier = Modifier.size(100.dp)
            ) {
                Image(
                    painterResource(R.drawable.img_avatar),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.height(spacingSmaller))
            Text(
                text = "A. Muh. Fakhry Mubarak",
                style = SansSerif.Sp16.Bold,
                modifier = Modifier.padding(horizontal = spacingRegular, vertical = spacingTiny)
            )
            Text(
                text = emailAddress,
                style = SansSerif.Sp14.Regular,
                modifier = Modifier
                    .padding(horizontal = spacingRegular, vertical = spacingTiny)
                    .clickable {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:")
                            putExtra(Intent.EXTRA_EMAIL, emailAddress)
                            putExtra(Intent.EXTRA_SUBJECT, emailSubject)
                        }
                        context.startActivity(intent)
                    }
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun ProfileComposePreview() {
    MovieComposeTheme {
        ProfileCompose()
    }
}


