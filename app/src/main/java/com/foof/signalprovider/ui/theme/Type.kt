package com.foof.signalprovider.ui.theme

import androidx.compose.animation.core.snap
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.foof.signalprovider.R


val interFontFamily = FontFamily(
    Font(R.font.inter_bold , weight = FontWeight.Bold),
    Font(R.font.inter_semibold , weight = FontWeight.SemiBold),
    Font(R.font.inter_regular , weight = FontWeight.Normal)
)

val sans = FontFamily(
    Font(R.font.opensans_semibold , weight = FontWeight.SemiBold)
)
val tsukimi = FontFamily(
    Font(R.font.tsukimi_bold , weight = FontWeight.Bold)
)

val authLogo = TextStyle(
    fontSize = 24.sp,
    fontFamily = tsukimi,
    fontWeight = FontWeight.Bold,
    color = logoColor
)

val smalhint = TextStyle(
    fontSize = 12.sp,
    fontFamily = interFontFamily,
    fontWeight = FontWeight.Normal,
    color = hintColor)


val mediumHint = TextStyle(
    fontSize = 14.sp,
    fontFamily = interFontFamily,
    fontWeight = FontWeight.Normal,
    color = hintColor)


val errorStyle = TextStyle(
    fontSize = 12.sp,
    fontFamily = interFontFamily,
    fontWeight = FontWeight.Normal,
    color = red)





// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontSize = 24.sp,
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Bold,
        color = titleColor
    ),
    titleMedium = TextStyle(
        fontSize = 14.sp,
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Normal,
        color = mediumTitle
    ),
    titleSmall = TextStyle(
        fontSize = 12.sp,
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Normal,
        color = mediumTitle
    ),
    bodySmall = TextStyle(
        fontSize = 16.sp,
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Normal,
        color = titleColor
    ),
    labelSmall = TextStyle(
        fontSize = 16.sp,
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Normal,
        color = hintColor
    ),
   displaySmall  = TextStyle(
       fontSize = 14.sp,
       fontFamily = sans,
       fontWeight = FontWeight.SemiBold,
       color = mediumTitle
    ) ,
   displayMedium  = TextStyle(
       fontSize = 14.sp,
       fontFamily = sans,
       fontWeight = FontWeight.SemiBold,
       color = titleColor
    ) ,
   displayLarge = TextStyle(
        fontSize = 14.sp,
        fontFamily = sans,
        fontWeight = FontWeight.SemiBold,
        color = blue
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)



