package com.common.module.data.controller;

import com.common.auth.dto.MessageResponse;
import com.common.auth.service.AuthUser;
import com.common.constants.CommonConstants;
import com.common.module.data.dao.CompanyDAO;
import com.common.module.data.dto.Company;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/company")
public class CompanyController {
    private static Logger LOGGER = Logger.getLogger(CompanyController.class);
    @Autowired
    private CompanyDAO dao;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Company company) {
        AuthUser loggedInUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        company.setCreateBy(loggedInUser.getId());
        company.setUpdateBy(loggedInUser.getId());
        try {
            int id = dao.save(company);
            return ResponseEntity.ok(new MessageResponse(String.format("add " + dao.getTableName() + " successfully!"), CommonConstants.MessageType.SUCCESS));
        } catch (Exception e) {
            LOGGER.error("Error while company Item {}");
            e.printStackTrace();
            return ResponseEntity.ok(new MessageResponse("Some error occurred!", CommonConstants.MessageType.ERROR));
        }

    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody Company company) {
        AuthUser loggedInUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        company.setUpdateBy(loggedInUser.getId());
        try {
            dao.update(company);
            return ResponseEntity.ok(new MessageResponse(String.format("update " + dao.getTableName() + " successfully!"), CommonConstants.MessageType.SUCCESS));
        } catch (Exception e) {
            LOGGER.error("Error while update company {}");
            e.printStackTrace();
            return ResponseEntity.ok(new MessageResponse("Some error occurred!", CommonConstants.MessageType.ERROR));
        }
    }

    @GetMapping("/list")
    public Collection<Company> list() {
        return dao.getAll();
    }

    @GetMapping("/getById")
    public Company get(@RequestBody Company company) {
        return dao.getById(company.getId());
    }
}
