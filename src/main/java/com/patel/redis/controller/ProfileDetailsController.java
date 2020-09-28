package com.patel.redis.controller;

import java.util.List;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.patel.redis.entity.ProfileDetails;
import com.patel.redis.exception.ProfileDetailsNotFoundException;
import com.patel.redis.model.request.ProfileDetailsCreateRquest;
import com.patel.redis.model.request.ProfileDetailsUpdateRequest;
import com.patel.redis.model.response.ProfileDetailsCreateResponse;
import com.patel.redis.service.ProfileDetailsService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController("v1ProfileDetails")
@RequestMapping(path = "/v1/profileDetails")
public class ProfileDetailsController {

    @Autowired
    private ProfileDetailsService profileDetailsService;

    @PostMapping(path = "/{tenantName}/create", consumes = "application/json",
                    produces = "application/json")
    @ApiImplicitParams({@ApiImplicitParam(name = "tenantName", paramType = "path")})
    public ResponseEntity<?> createProfileDetails(@RequestBody ProfileDetailsCreateRquest request,
                    @PathVariable(name = "tenantName", required = true) String tenantName)
                    throws Exception {
        try {
            ProfileDetailsCreateResponse response =
                            profileDetailsService.createProfileDetails(request, tenantName);
            return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ModelMap().addAttribute("id", response.getId()).addAttribute(
                                            "msg",
                                            response.getMsg()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        }
    }

    @GetMapping(path = "/{userName}", produces = "application/json")
    @ApiImplicitParams({@ApiImplicitParam(name = "userName", paramType = "path")})
    public ResponseEntity<?> getProfileDetails(
                    @PathVariable(name = "userName", required = true) String userName)
                    throws Exception {
        try {
            ProfileDetails profileDetails =
                            profileDetailsService.getProfileDetailsByUserName(userName);
            return ResponseEntity.status(HttpStatus.OK)
                            .body(new ModelMap().addAttribute("response", profileDetails));
        } catch (final ProfileDetailsNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.OK)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        }
    }

    @GetMapping(path = "/{id}/get")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", paramType = "path")})
    public ResponseEntity<?> getAllProfileDetails(
                    @PathVariable(name = "id", required = true) Integer id) {
        try {
            List<ProfileDetails> listOfProfileDetails =
                            profileDetailsService.getAllProfileDetails(id);
            return ResponseEntity.status(HttpStatus.OK)
                            .body(new ModelMap().addAttribute("response", listOfProfileDetails));
        } catch (final Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        }
    }

    @DeleteMapping(path = "/{email}/{id}")
    public ResponseEntity<?> deleteProfileDetails(
                    @PathVariable(name = "email", required = true) String email,
                    @PathVariable(name = "id", required = true) Integer id) throws Exception {
        try {
            profileDetailsService.deleteProfileDetails(email, id);
            return ResponseEntity.status(HttpStatus.OK)
                            .body(new ModelMap().addAttribute("msg", "Successfully deleted."));
        } catch (final ProfileDetailsNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        }
    }

    @PutMapping(path = "/update", consumes = "application/json")
    public ResponseEntity<?> updateProfileDetails(@RequestBody ProfileDetailsUpdateRequest payload,
                    @RequestParam(value = "id", required = true) Integer id) throws Exception {
        try {
            profileDetailsService.updateProfileDetails(payload, id);
            return ResponseEntity.status(HttpStatus.OK)
                            .body(new ModelMap().addAttribute("msg", "Successfully Updated"));
        } catch (final ProfileDetailsNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ModelMap().addAttribute("msg", ex.getMessage()));
        }
    }

    @GetMapping(path = "/check")
    public ResponseEntity<?> check() throws JsonProcessingException, ParseException {

        profileDetailsService.check();
        return ResponseEntity.ok(new ModelMap().addAttribute("msg", "Success"));
    }
}
