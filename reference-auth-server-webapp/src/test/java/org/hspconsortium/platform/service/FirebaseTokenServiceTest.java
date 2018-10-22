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

    private String credentials = "secret";

    private String jwtCookie2 = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjdhMWViNTE2YWU0MTY4NTdiM2YwNzRlZDQxODkyZTY0M2MwMGYyZTUifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vaHNwYy10c3QiLCJuYW1lIjoiTWlrZSBCeWx1bmQiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDUuZ29vZ2xldXNlcmNvbnRlbnQuY29tLy1yYXdhdmpfOVlGWS9BQUFBQUFBQUFBSS9BQUFBQUFBQUFBay9aYmNpYllINWluWS9waG90by5qcGciLCJhdWQiOiJoc3BjLXRzdCIsImF1dGhfdGltZSI6MTUzOTgwOTk1NSwidXNlcl9pZCI6IldVS0tJSzBoM0NaY0pVMTdjRndVVVhvNE1QNzMiLCJzdWIiOiJXVUtLSUswaDNDWmNKVTE3Y0Z3VVVYbzRNUDczIiwiaWF0IjoxNTM5OTgxODgxLCJleHAiOjE1Mzk5ODU0ODEsImVtYWlsIjoibWlrZUBpbnRlcm9waW9uLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7Imdvb2dsZS5jb20iOlsiMTE0MzgwMDQyODMzMzQ2NTA3MzQ4Il0sImVtYWlsIjpbIm1pa2VAaW50ZXJvcGlvbi5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJnb29nbGUuY29tIn19.Iutmq_tOyZAMH2TJrBwDY8PQ5hvjqB-OPI7bFnsHh-yijO7_eBsC1teABvqDdTzq0k4nb4tGOsfOxDjST6agD6vm9F6Ai94bxQ-NmePqfPXcEN5Bw-gezMbp1XKK08y3EkjDYoGb7MV4E0W77QLkEuaNp6hmfbrv20-dxmsjDkmhdEwDxR0eJxO5A2hlbXjpbgB-hObrLNg-9kr9YOEPZQc0mEHnr1_8MmSMIFUFzwv2muGOj2aQijKNLaT-_0wizveYE7Qc2OY_rZOri2l3cvbytlzt79W7_PrFh0u3hNAg824THQg_1KgPWMY5cafNTuxlpTFq2ii6Bbp9C0ZKbA";
    FirebaseTokenService firebaseTokenService = new FirebaseTokenService();

    @BeforeClass
    public void setup() {
        firebaseTokenService.initTempFirebase(credentials);
    }

    private int count = 0;

    @Test(threadPoolSize = 3, invocationCount = 10, timeOut = 10000)
    public void getUserProfileInfo() {
        String name = Thread.currentThread().getName();
        System.out.println("Start " + name);

        if (count++ % 2 == 0) {
            FirebaseUserProfileDto firebaseUserProfileDto = firebaseTokenService.getUserProfileInfo("jkcrump12@gmail.com");
            if (firebaseUserProfileDto == null) {
                System.out.println("null FirebaseUserProfileDto for jacob");
            } else if (firebaseUserProfileDto.getEmail() == null) {
                System.out.println("Email is null for jacob.");
            } else if (!firebaseUserProfileDto.getEmail().equals("jkcrump12@gmail.com")) {
                System.out.println(firebaseUserProfileDto.getEmail());
            }
        } else {
            FirebaseUserProfileDto firebaseUserProfileDto = firebaseTokenService.getUserProfileInfo("mike@interopion.com");
            if (firebaseUserProfileDto == null) {
                System.out.println("null FirebaseUserProfileDto for mike");
            } else if (firebaseUserProfileDto.getEmail() == null) {
                System.out.println("Email is null for mike.");
            } else if (!firebaseUserProfileDto.getEmail().equals("mike@interopion.com")) {
                System.out.println(firebaseUserProfileDto.getEmail());
            }
        }
        System.out.println("End " + name);
    }


}