package io.reflectoring.pastebinlite.controller;

import io.reflectoring.pastebinlite.bean.Paste;
import io.reflectoring.pastebinlite.request.CreatePasteRequest;
import io.reflectoring.pastebinlite.service.PasteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/pastes")
/*@CrossOrigin(
        origins = "https://pastebin-frontend-1s1ek4s8d-rohinis-projects-c95122e8.vercel.app"
)*/
public class PasteController {

    @Autowired
    private PasteService service;

    @PostMapping
    public ResponseEntity<?> createPaste(@RequestBody CreatePasteRequest request, HttpServletRequest httpServletRequest){

        if (request.getContent() == null || request.getContent().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "content required"));
        }
        if (request.getTtl_seconds() != null && request.getTtl_seconds() < 1) {
            return ResponseEntity.badRequest().body(Map.of("error", "ttl_seconds must be ≥ 1"));
        }
        if (request.getMax_views() != null && request.getMax_views() < 1) {
            return ResponseEntity.badRequest().body(Map.of("error", "max_views must be ≥ 1"));
        }

        Paste create = service.create(request.getContent(), request.getTtl_seconds(), request.getMax_views(), httpServletRequest);

        return  ResponseEntity.ok(Map.of("id", create.getId().toString(),
                "url", "https://pastebin-backend-production-57eb.up.railway.app/p/"+create.getId())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> fetchPaste(@PathVariable("id") UUID id, HttpServletRequest httpServletRequest){
        try {
            Paste fetch = service.fetch(id, httpServletRequest);

            Integer remainingViews = fetch.getMaxViews()==null? null : fetch.getMaxViews() - fetch.getViews();

            return ResponseEntity.ok(Map.of(
                    "content", fetch.getContent(),
                    "remaining_views", remainingViews,
                    "expires_at", fetch.getExpiresAt()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(Map.of("error", "Not Found"));
        }

    }
}
