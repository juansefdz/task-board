package com.TaskBoard.TaskBoard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.TaskBoard.TaskBoard.entity.Task;
import com.TaskBoard.TaskBoard.services.TasksService;

@Controller
@RequestMapping("/")
public class TaskController {

    @Autowired
    private TasksService objTasksService;

    @GetMapping("/")
    public String showViewGetAll(Model objModel, @RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "2") int size, @RequestParam(required = false) String name,
                                 @RequestParam(required = false) String state) {
        Page<Task> list;
        if (state != null) {
            list = this.objTasksService.findPaginatedByState(page - 1, size, state);
        } else {
            list = this.objTasksService.findPaginated(page - 1, size, name);
        }
        objModel.addAttribute("taskList", list);
        objModel.addAttribute("currentPage", page);
        objModel.addAttribute("totalPages", list.getTotalPages());
        return "viewTasks";
    }

    @GetMapping("/form")
    public String showViewFormTask(Model objModel) {
        objModel.addAttribute("task", new Task());
        objModel.addAttribute("action", "/create");
        return "viewForm";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute Task objTask) {
        this.objTasksService.insert(objTask);
        return "redirect:/";
    }

    @GetMapping("/update/{id}")
    public String showFormUpdate(@PathVariable Long id, Model objModel) {
        Task objTaskFind = this.objTasksService.findByid(id);
        objModel.addAttribute("task", objTaskFind);
        objModel.addAttribute("action", "/edit/" + id);
        return "viewForm";
    }

    @PostMapping("/edit/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute Task objTask) {
        this.objTasksService.update(id, objTask);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        this.objTasksService.delete(id);
        return "redirect:/";
    }

    @GetMapping("/search/")
    public String searchByTitle(@RequestParam("name") String name, Model model) {
        Page<Task> taskList = objTasksService.findPaginated(1, 2, name);
        model.addAttribute("taskList", taskList);
        return "viewTasks";
    }

    @PostMapping("/updateTask/{id}")
    public String updateTaskState(@PathVariable Long id, @RequestParam("state") String state) {
        this.objTasksService.updateTaskState(id, state);
        return "redirect:/";
    }
}
