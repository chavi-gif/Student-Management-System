package com.example.SMS.controller;

import com.example.SMS.model.MarksData;
import com.example.SMS.model.MarksReportDTO;
import com.example.SMS.service.MarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/marks")
public class MarksController {

    @Autowired
    private MarksService marksService;

    // ── Menu ──────────────────────────────────────────────────────────────────

    @GetMapping("")
    public String menu() {
        return "marks/menu";
    }

    // ── Add Marks ─────────────────────────────────────────────────────────────

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("marks", new MarksData());
        return "marks/add";
    }

    @PostMapping("/add")
    public String addSubmit(@ModelAttribute MarksData marks, RedirectAttributes ra) {
        String result = marksService.addMarks(marks);
        if (result.contains("does not exist") || result.contains("already exist")) {
            ra.addFlashAttribute("error", result);
        } else {
            ra.addFlashAttribute("success", result);
        }
        return "redirect:/marks/add";
    }

    // ── Display All Marks ──────────────────────────────────────────────────────

    @GetMapping("/all")
    public String allMarks(Model model) {
        List<MarksReportDTO> reports = marksService.getAllMarksReport();
        model.addAttribute("reports", reports);
        return "marks/all";
    }

    // ── Display Individual Marks ───────────────────────────────────────────────

    @GetMapping("/individual")
    public String individualForm() {
        return "marks/individual-search";
    }

    @PostMapping("/individual")
    public String individualResult(@RequestParam String regNo, Model model) {
        MarksReportDTO report = marksService.getIndividualReport(regNo.trim());
        if (report != null) {
            model.addAttribute("report", report);
        } else {
            model.addAttribute("error", "No marks found for RegNo: " + regNo);
        }
        model.addAttribute("regNo", regNo);
        return "marks/individual-result";
    }
}
