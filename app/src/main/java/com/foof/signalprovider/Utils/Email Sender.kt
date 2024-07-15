package com.foof.signalprovider.Utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


suspend fun otpEmail(otp:String, recevierEmail: String)
{
    val content =
        "<html><head><style>" +
                "body { font-family: Arial, sans-serif; color: #333333; background-color: #f0f0f0; }" +
                "h3 { color: #008080; }" +
                "p { margin-bottom: 10px; color:#000000 }" +
                "</style></head><body>" +
                "<p>Hello</p>" +
                "<p>\n you have requested an OTP to verify your identity on Singlee App..\n</p>" +
                "<h3>Your OTP is: $otp\n</h3>" +
                "<p>\n If you did not request this OTP, please ignore this message..\n</p>" +
                "<p>Regards,<br>Singlee App Team</p>" +
                "</body></html>"

    withContext(Dispatchers.IO)
    {
        try {
            sendEmail(recevierEmail,content,"Forget Password OTP")
        }
        catch(e : Exception ){}
    }
}
suspend fun welcomeEmail(recevierEmail: String)
{
    val content =
        "<html><head><style>" +
                "body { font-family: Arial, sans-serif; color: #333333; background-color: #f0f0f0; }" +
                "h2 { color: #008080; }" +
                "p { margin-bottom: 10px; color:#000000 }" +
                "</style></head><body>" +
                "<h2>Welcome to Singlee App! </h2>" +
                "<p>Thank you for registering with us. We hope you find our services helpful.</p>" +
                "<p>Let's earn and learn together.</p>" +
                "<p>If you have any questions, please contact us.</p>" +
                "<p>Regards,<br>Singlee App Team</p>" +
                "</body></html>"

    withContext(Dispatchers.IO)
    {
        try {
            sendEmail(recevierEmail,content,"Singlee Team")
        }
        catch(e : Exception ){}
    }
}

private suspend fun sendEmail(recevierEmail : String  , content : String , sub : String) {

    val stringSenderEmail = "programerx55@gmail.com"
    val stringPasswordSenderEmail = "ybtbpttopxoyzryw"
    val stringHost = "smtp.gmail.com"

    val properties = Properties().apply {
        put("mail.smtp.host", stringHost)
        put("mail.smtp.port", "465")
        put("mail.smtp.ssl.enable", "true")
        put("mail.smtp.auth", "true")
    }

    val session = Session.getInstance(properties, object : Authenticator() {
        override fun getPasswordAuthentication(): PasswordAuthentication {
            return PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail)
        }
    })

    val mimeMessage = MimeMessage(session).apply {
        setFrom(InternetAddress(stringSenderEmail))
        addRecipient(Message.RecipientType.TO, InternetAddress(recevierEmail))
        subject = sub

        setContent(content,"</body></html>")
    }

    // Send email asynchronously using coroutines
    withContext(Dispatchers.IO) {
        try {
            Transport.send(mimeMessage)
            println("Email sent successfully!")
        } catch (e: MessagingException) {
            e.printStackTrace()
            println("Failed to send email: ${e.message}")
        }
    }
}
