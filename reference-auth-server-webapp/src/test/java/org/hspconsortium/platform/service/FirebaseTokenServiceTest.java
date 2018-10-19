package org.hspconsortium.platform.service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.hspconsortium.platform.authorization.repository.impl.FirebaseUserProfileDto;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

import static org.junit.Assert.*;

public class FirebaseTokenServiceTest {

    private FirebaseApp firebaseApp;

    private String jwtCookie = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjdhMWViNTE2YWU0MTY4NTdiM2YwNzRlZDQxODkyZTY0M2MwMGYyZTUifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vaHNwYy10c3QiLCJuYW1lIjoiSmFjb2IgQ3J1bXAiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDQuZ29vZ2xldXNlcmNvbnRlbnQuY29tLy13Y3VpclY5cXIwUS9BQUFBQUFBQUFBSS9BQUFBQUFBQUFHby9KeUVLLWpfYTRGYy9waG90by5qcGciLCJhdWQiOiJoc3BjLXRzdCIsImF1dGhfdGltZSI6MTUzOTg2OTc5NywidXNlcl9pZCI6IldtWTZtZFdlZlJkMGFqeFVLR3ZuVGNNOXhScTEiLCJzdWIiOiJXbVk2bWRXZWZSZDBhanhVS0d2blRjTTl4UnExIiwiaWF0IjoxNTM5OTgyMTY5LCJleHAiOjE1Mzk5ODU3NjksImVtYWlsIjoiamtjcnVtcDEyQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7Imdvb2dsZS5jb20iOlsiMTAxNDE5NTk0NzYyNjQwMDU5OTk0Il0sImVtYWlsIjpbImprY3J1bXAxMkBnbWFpbC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJnb29nbGUuY29tIn19.pvYi4odUMsGNWzejjFQr9HOA1pJwD8keUw5rLpN1R8FO_bJ_ec7b6EEIWwZ7PP3_qzQr6vLlLUvWMwIdC5RIZRVguwJitXn5S-GeK97nBx0p5Ntm0SctQ0qSc-yh3YTxRyytfE1VeHE6u9pabhFh3UCPFag6Kg1-LgQOdaNysyVCPNo_XxVpmxNB0LJwHilchm6QzrjPaPTgjwT4v5R-NNK1ZjHB2EwAb7uVo-fVDLeXwgTwHjeEnR4uAbeDKfVpuEnZgOHVSRZYeajIH0WOIsSZk-Y7ZMep-ST-_2ZMrgbOCj_KukU1aztsldWtg8yjAB54NlT5eUFRuphKTw9CMQ";

    private String credentials = "{\n" +
            "  \"type\": \"service_account\",\n" +
            "  \"project_id\": \"hspc-tst\",\n" +
            "  \"private_key_id\": \"09f2b3f05ec952b3e3be0915485d8b4517927149\",\n" +
            "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCXwsDYNJzuzIWG\\nZcX80+tgt2UBPuMfIX0AX0uw/0kyT+FTofrMU5W1w6W9CwY+eV2j2BiiK6FfD7YD\\nKuBa8odLnae+QXvvRf6PUnupkXiti5/VIXFdKc+E+eO02rR8Qy0i8Aucfa7YVwq5\\nHBdrNW2yeabiKG/nVWuwX/ZJL901QTS+tbgScy/SfHn0mey127c0evJCKR5QtE3z\\ntyf56YNtcq1S7VELjiqSt7Yqu/IC/17BWsMr9IOLp5YtaugPqy+m/u15Xc/KrFNv\\nXVmUchS7o+IS/CKJgsTtBuJyL9M6YUAvWIVVOiFMEwgqIMncWqBHgN7e6IisyGEZ\\nVfL6tgqFAgMBAAECggEAHi6jf9xm4a9XVvVivPfAtkmDEw5YyVY1sB9DxH3hJtpw\\nK9vpLz3oZm/0xHYu1bn5f554t7gJrghH4LVkTXIyqj0+oT4Q0fOPvx1ebsj5wTol\\nnZO7IhYwqXexa8SbZKNPlMU86fCtF2WoQkxt8+p6WKaNJCl6p/17H2bWapPUYMS6\\nuaW5S6B+/YizYS8H0+764hpmNGck6n0V009mSnZK87uFDEmzL1HV/r50/DrTPJNI\\nCF68KtZdvHoRA3uoUQEQvKoXG8CH5rFMSs6JyTXNKNJxGONigqIyY3Ip0T3lmIeK\\n9LLD4Ldo4uTg4iACMo6+yE2DEXVvT5ByYAox1kkNeQKBgQDIA+9fE44HI27NBXGu\\nieQN9PU00vQVOG+fiblOMCs3adPxdKUB6psr2LQpGeFM1pv4Z0jQh+CZaGWUfBZW\\nya28nnIEufFeqA6ErvISxf8s7q/U9OVLn8G5gLVTVGNBpSOiBkbTZebGUOijt0I+\\ne/B97q/6xp2Seis7RYX07u1FjwKBgQDCPSR83284/DWHV/4S0yys9WlfVqs1c4Ui\\n/rBQ7sZSaqWr4mRx/bF8fuzP6PyYYRgpvyqlQ5dNHItlKctwOW6WFAEmEeIjIvxW\\n+qhjNlF4JjWnT+OgtCjixfz/6AcfG3HLVHEZc+p7B3cnTm//t+qFMuc3KDEtfKo6\\n1WluB3wsqwKBgDqOxF7uh69ZVVAa3ux9nFHlvIBJnoIz8qIfnSvh+FUR26YeQ0zr\\nVaChoiVCWW64ihNQkcXTchMXuF2fcp7YDtBhBvqRQbQ3uYBpA6AxbYLHIp1TUWox\\njHL11qBd3W1TnroJ4mRzufunpmR/+5n1iTQlVEo0Kd+/erawpQZPiRHLAoGALEnC\\nqxJLCuQ+1EhU7zflRN6EEy/ww7Q+VBdcXmfDIoGcms949aDRukA6gMFv7tKcrtAe\\nyiXaNsPeaMeZ0lHWDDQtet+y/y33+WEXyd9Vvh6AN1D45uiNNf/MyxC9pvMcBC+R\\nxiru+PRfz7+Lu/PUbmCH5cStSeDV86o7atIcFxcCgYEAuGgz+P9cG2F+YgWZ1Vd6\\nUr6HnnSKrq6IibbrH2JXUDnydRkCSz8QyizeqIMmq18NZTeEdLTJetSjpQHal/3s\\npvGPxA6Gv58oGmU+UrkMscPnzFfG9LxH2QOJLhVHvg+PX/gHGslPuOWanbMjH1v0\\n3VO5oaskzIl8A7IJ2jNLLF4=\\n-----END PRIVATE KEY-----\\n\",\n" +
            "  \"client_email\": \"firebase-adminsdk-r753f@hspc-tst.iam.gserviceaccount.com\",\n" +
            "  \"client_id\": \"105575754257564935908\",\n" +
            "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
            "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
            "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
            "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-r753f%40hspc-tst.iam.gserviceaccount.com\"\n" +
            "}";

    private String jwtCookie2 = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjdhMWViNTE2YWU0MTY4NTdiM2YwNzRlZDQxODkyZTY0M2MwMGYyZTUifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vaHNwYy10c3QiLCJuYW1lIjoiTWlrZSBCeWx1bmQiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDUuZ29vZ2xldXNlcmNvbnRlbnQuY29tLy1yYXdhdmpfOVlGWS9BQUFBQUFBQUFBSS9BQUFBQUFBQUFBay9aYmNpYllINWluWS9waG90by5qcGciLCJhdWQiOiJoc3BjLXRzdCIsImF1dGhfdGltZSI6MTUzOTgwOTk1NSwidXNlcl9pZCI6IldVS0tJSzBoM0NaY0pVMTdjRndVVVhvNE1QNzMiLCJzdWIiOiJXVUtLSUswaDNDWmNKVTE3Y0Z3VVVYbzRNUDczIiwiaWF0IjoxNTM5OTgxODgxLCJleHAiOjE1Mzk5ODU0ODEsImVtYWlsIjoibWlrZUBpbnRlcm9waW9uLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7Imdvb2dsZS5jb20iOlsiMTE0MzgwMDQyODMzMzQ2NTA3MzQ4Il0sImVtYWlsIjpbIm1pa2VAaW50ZXJvcGlvbi5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJnb29nbGUuY29tIn19.Iutmq_tOyZAMH2TJrBwDY8PQ5hvjqB-OPI7bFnsHh-yijO7_eBsC1teABvqDdTzq0k4nb4tGOsfOxDjST6agD6vm9F6Ai94bxQ-NmePqfPXcEN5Bw-gezMbp1XKK08y3EkjDYoGb7MV4E0W77QLkEuaNp6hmfbrv20-dxmsjDkmhdEwDxR0eJxO5A2hlbXjpbgB-hObrLNg-9kr9YOEPZQc0mEHnr1_8MmSMIFUFzwv2muGOj2aQijKNLaT-_0wizveYE7Qc2OY_rZOri2l3cvbytlzt79W7_PrFh0u3hNAg824THQg_1KgPWMY5cafNTuxlpTFq2ii6Bbp9C0ZKbA";
    FirebaseTokenService firebaseTokenService = new FirebaseTokenService();

    @BeforeClass
    public void setup() {
        firebaseTokenService.initTempFirebase(credentials);
    }

    private int count = 0;

    @Test(threadPoolSize = 3, invocationCount = 30,  timeOut = 10000)
    public void getUserProfileInfo() {
        // TODO: Run simultaneously
        String name = Thread.currentThread().getName();
        System.out.println("Start " + name);
        FirebaseToken firebaseToken;
        count = new Random().nextInt();

        if (count++%2 == 0) {
            FirebaseUserProfileDto firebaseUserProfileDto = firebaseTokenService.getUserProfileInfo("jkcrump12@gmail.com");
            if(!firebaseUserProfileDto.getEmail().equals("jkcrump12@gmail.com")) {
                System.out.println(firebaseUserProfileDto.getEmail());
            }
        } else {
            FirebaseUserProfileDto firebaseUserProfileDto = firebaseTokenService.getUserProfileInfo("mike@interopion.com");
            if(!firebaseUserProfileDto.getEmail().equals("mike@interopion.com")) {
                System.out.println(firebaseUserProfileDto.getEmail());
            }
        }
        System.out.println("End " + name);
    }


}