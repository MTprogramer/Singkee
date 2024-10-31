package com.Singlee.forex.screens.Home

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.Singlee.forex.R
import com.Singlee.forex.Repo.Response
import com.Singlee.forex.graph.HomeRoutes
import com.Singlee.forex.graph.NavRouts
import com.Singlee.forex.screens.Home.ViewModels.UserViewModel
import com.Singlee.forex.ui.theme.duble_extra_light
import com.Singlee.forex.ui.theme.extra_light
import com.Singlee.forex.ui.theme.red
import com.Singlee.forex.ui.theme.signalType
import com.Singlee.forex.ui.theme.titleColor
import de.cketti.mailto.EmailIntentBuilder


@Composable
fun ProfileScreen(navController: NavHostController, userViewModel: UserViewModel)
{
    val context = LocalContext.current

    val name = remember { mutableStateOf("") }
    val image_url = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }

    val update by userViewModel.userDataUpdate.collectAsState(initial = Response.Empty)

    Log.d("UserProfileScreen", "Current update state: $update")

    LaunchedEffect(update) {
        when (update) {
            is Response.Loading -> {
                Log.d("status", "Loading")
            }
            is Response.Success -> {
                Log.d("status", "Success: ${update}")
            }
            is Response.Error -> {
                Log.d("error", (update as Response.Error).message)
            }
           is Response.Empty -> { Log.d("status", "Empty response") }
        }
    }

    LaunchedEffect(Unit) {
        name.value = userViewModel.userData().name
        email.value = userViewModel.userData().email
        image_url.value = userViewModel.userData().profileImage
    }




    Column(
        Modifier
            .padding(horizontal = 20.dp, vertical = 25.dp)
            .verticalScroll(state = rememberScrollState()))
            {
                logput(navController, userViewModel)
                AvtarDesign(name, image_url, userViewModel)
                Spacer(modifier = Modifier.height(5.dp))
                itemBox(
                    title = "My Profile",
                    dis = "Your profile and personal information",
                    icon = R.drawable.profile_icon
                ) { navController.navigate(HomeRoutes.ProfileSettingScreen.route) }
                itemBox(
                    title = "Preferences",
                    dis = "Settings and configurations",
                    icon = R.drawable.prefrences_icon
                ) { navController.navigate(HomeRoutes.PreferencesScreen.route) }
                itemBox(
                    title = "Privacy policy",
                    dis = "Read Our Terms Privacy Policy",
                    icon = R.drawable.privacy_policy_icon
                ) { navController.navigate(HomeRoutes.PrivacyPolicy.route) }
                itemBox(
                    title = "About",
                    dis = "About “Singlee App”",
                    icon = R.drawable.about_icon
                ) { navController.navigate(HomeRoutes.AboutSinglee.route) }
                itemBox(
                    title = "Contact us",
                    dis = "If you have any query contact “Singlee”",
                    icon = R.drawable.contectus_icon
                ) {

                    EmailIntentBuilder
                        .from(context)
                        .to("singleeteam55@gmail.com")
                        .subject("Singlee Help Mail")
                        .start();
                }

            }

}




@Composable
fun logput(navController: NavHostController, userViewModel: UserViewModel)
{
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopEnd)
    {
        Button(onClick = {

            userViewModel.signout()

            navController.navigate(NavRouts.AuthRoute.route) {
                // Clear the back stack to remove all screens
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        })
        {
            Text(
                text = "Logout",
                style = MaterialTheme.typography.displayMedium,
                color = red,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
    }
}

@Composable
fun AvtarDesign(
    name: MutableState<String>,
    image_url: MutableState<String>,
    userViewModel: UserViewModel
)
{
    // Image picker launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            image_url.value = it.toString() // Update the image URL
            userViewModel.updateUserAvtar(image_url.value)
        }
    }



    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = image_url.value,
                    placeholder = painterResource(R.drawable.fake_avtar),  // Placeholder when loading
                    error = painterResource(R.drawable.fake_avtar)
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(108.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(15.dp))

            Box(
                modifier = Modifier
                    .clickable { }
                    .size(52.dp)
                    .background(extra_light, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            )
            {
                Image(
                    painter = painterResource(id = R.drawable.camera_icon),
                    contentDescription = null ,
                    Modifier
                        .size(26.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .clickable {
                            launcher.launch("image/*")
                        }
                )
            }

        }

        Text(
            text = "Hello, ${name.value}",
            style = signalType,
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier.padding(top = 20.dp)
        )

        Text(
            text = "Manage your account & Preferences",
            style = MaterialTheme.typography.displayMedium,
            color = titleColor,
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
}


@Composable
fun itemBox(title: String, dis: String, icon: Int, function: () -> Unit)
{
    Spacer(modifier = Modifier.height(10.dp))
    Box(modifier = Modifier
        .clickable { function.invoke() }
        .fillMaxWidth()
        .height(height = 72.dp)
        .background(
            duble_extra_light,
            RoundedCornerShape(15.dp)
        ),
        contentAlignment = Alignment.CenterStart
    )
    {
        Row(verticalAlignment = Alignment.CenterVertically  , modifier = Modifier.padding(horizontal = 10.dp)) {
            Box(modifier = Modifier
                .size(44.dp)
                .background(extra_light, RoundedCornerShape(15.dp)),
                contentAlignment = Alignment.Center
            )
            {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null ,
                    Modifier
                        .size(21.dp),
                    contentScale = ContentScale.FillBounds)
            }

            Column(Modifier.padding(start = 10.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White,
                )
                Text(
                    text = dis,
                    style = MaterialTheme.typography.displayMedium,
                    color = titleColor,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
        }
    }
}