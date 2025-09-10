package com.ontime.office.controller;

import com.ontime.office.service.FaceRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/face")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://attendencefrontend.vercel.app"
})
public class FaceRecognitionController {

    @Autowired
    private FaceRegistrationService faceRegistrationService;

    // ✅ Endpoint: Register face for a specific employee
    @PostMapping("/register/{employeeId}")
    public ResponseEntity<?> registerFace(
            @PathVariable Long employeeId,
            @RequestParam("image") MultipartFile imageFile
    ) {
        try {
            boolean success = faceRegistrationService.registerFace(employeeId, imageFile);
            if (success) {
                return ResponseEntity.ok("Face registered successfully");
            } else {
                return ResponseEntity.badRequest().body("Face registration failed");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    // ✅ Endpoint: Perform face-based check-in
    @PostMapping("/checkin")
    public ResponseEntity<?> checkInFace(@RequestParam("image") MultipartFile imageFile) {
        try {
            String result = faceRegistrationService.checkInFace(imageFile);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Check-in failed: " + e.getMessage());
        }
    }
}
