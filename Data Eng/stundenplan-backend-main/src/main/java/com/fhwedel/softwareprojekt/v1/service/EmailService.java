package com.fhwedel.softwareprojekt.v1.service;

import com.microsoft.graph.auth.confidentialClient.AuthorizationCodeProvider;
import com.microsoft.graph.auth.enums.NationalCloud;
import com.microsoft.graph.models.EmailAddress;
import com.microsoft.graph.models.ItemBody;
import com.microsoft.graph.models.Message;
import com.microsoft.graph.models.Recipient;
import com.microsoft.graph.models.UserSendMailParameterSet;
import com.microsoft.graph.requests.GraphServiceClient;
import lombok.RequiredArgsConstructor;
import okhttp3.Request;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {

    public void sendEmail(String subject, String address, String content) {
        AuthorizationCodeProvider authProvider = new AuthorizationCodeProvider("<client-Id>",
                Arrays.asList("https://graph.microsoft.com/user.read", "https://graph.microsoft.com/Mail.ReadWrite"),
                "<authorizationCode>",
                "<redirectURi>",
                NationalCloud.Global,
                "<tenant>",
                "<clientSecret>");

        GraphServiceClient<Request> graphClient = GraphServiceClient
                .builder()
                .authenticationProvider(authProvider)
                .buildClient();

        EmailAddress toAddress = new EmailAddress();
        toAddress.address = address;

        Message message = new Message();
        message.subject = subject;
        Recipient rec = new Recipient();
        rec.emailAddress = toAddress;
        message.toRecipients = List.of(rec);

        ItemBody body = new ItemBody();
        body.content = content;
        message.body = body;

        graphClient
                .me()
                .sendMail(UserSendMailParameterSet.newBuilder()
                        .withMessage(message)
                        .build())
                .buildRequest()
                .post();
    }
}
