package com.example.SMS.controller;

import com.example.SMS.model.StudentData;
import com.example.SMS.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // ── Menu ──────────────────────────────────────────────────────────────────

    @GetMapping("")
    public String menu() {
        return "student/menu";
    }

    // ── Add Student ───────────────────────────────────────────────────────────

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("student", new StudentData());
        return "student/add";
    }

    @PostMapping("/add")
    public String addSubmit(@ModelAttribute StudentData student,
                            RedirectAttributes ra) {
        String result = studentService.addStudent(student);
        if (result.contains("already exists")) {
            ra.addFlashAttribute("error", result);
        } else {
            ra.addFlashAttribute("success", result);
        }
        return "redirect:/student/add";
    }

    // ── View Student ──────────────────────────────────────────────────────────

    @GetMapping("/view")
    public String viewForm() {
        return "student/view-search";
    }

    @PostMapping("/view")
    public String viewResult(@RequestParam String regNo, Model model) {
        Optional<StudentData> student = studentService.findByRegNo(regNo.trim());
        if (student.isPresent()) {
            model.addAttribute("student", student.get());
        } else {
            model.addAttribute("error", "No student found with RegNo: " + regNo);
        }
        model.addAttribute("regNo", regNo);
        return "student/view-result";
    }

    // ── Update Student ─────────────────────────────────────────────────────────

    @GetMapping("/update")
    public String updateSearch() {
        return "student/update-search";
    }

    @PostMapping("/update")
    public String updateMenu(@RequestParam String regNo, Model model) {
        Optional<StudentData> student = studentService.findByRegNo(regNo.trim());
        if (student.isEmpty()) {
            model.addAttribute("error", "No student found with RegNo: " + regNo);
            return "student/update-search";
        }
        model.addAttribute("student", student.get());
        model.addAttribute("regNo", regNo.trim());
        return "student/update-menu";
    }

    // Update Name
    @GetMapping("/update/name")
    public String updateNameForm(@RequestParam String regNo, Model model) {
        model.addAttribute("regNo", regNo);
        Optional<StudentData> student = studentService.findByRegNo(regNo);
        student.ifPresent(s -> model.addAttribute("currentName", s.getName()));
        return "student/update-name";
    }

    @PostMapping("/update/name")
    public String updateNameSubmit(@RequestParam String regNo,
                                   @RequestParam String name,
                                   RedirectAttributes ra) {
        String result = studentService.updateName(regNo, name);
        ra.addFlashAttribute("success", result);
        ra.addFlashAttribute("regNo", regNo);
        return "redirect:/student/update/name?regNo=" + regNo;
    }

    // Update DoB
    @GetMapping("/update/dob")
    public String updateDobForm(@RequestParam String regNo, Model model) {
        model.addAttribute("regNo", regNo);
        Optional<StudentData> student = studentService.findByRegNo(regNo);
        student.ifPresent(s -> model.addAttribute("currentDob", s.getDob()));
        return "student/update-dob";
    }

    @PostMapping("/update/dob")
    public String updateDobSubmit(@RequestParam String regNo,
                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob,
                                  RedirectAttributes ra) {
        String result = studentService.updateDob(regNo, dob);
        ra.addFlashAttribute("success", result);
        return "redirect:/student/update/dob?regNo=" + regNo;
    }

    // Update Contact
    @GetMapping("/update/contact")
    public String updateContactForm(@RequestParam String regNo, Model model) {
        model.addAttribute("regNo", regNo);
        Optional<StudentData> student = studentService.findByRegNo(regNo);
        student.ifPresent(s -> model.addAttribute("currentContact", s.getContact()));
        return "student/update-contact";
    }

    @PostMapping("/update/contact")
    public String updateContactSubmit(@RequestParam String regNo,
                                      @RequestParam String contact,
                                      RedirectAttributes ra) {
        String result = studentService.updateContact(regNo, contact);
        ra.addFlashAttribute("success", result);
        return "redirect:/student/update/contact?regNo=" + regNo;
    }

    // ── Delete Student ─────────────────────────────────────────────────────────

    @GetMapping("/delete")
    public String deleteForm() {
        return "student/delete";
    }

    @PostMapping("/delete")
    public String deleteSubmit(@RequestParam String regNo, RedirectAttributes ra) {
        String result = studentService.deleteStudent(regNo.trim());
        if (result.contains("not found")) {
            ra.addFlashAttribute("error", result);
        } else {
            ra.addFlashAttribute("success", result);
        }
        return "redirect:/student/delete";
    }
}
