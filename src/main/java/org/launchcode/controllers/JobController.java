package org.launchcode.controllers;

import org.launchcode.models.Employer;
import org.launchcode.models.Job;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {
        Job theJob = jobData.findById(id);
        model.addAttribute("title","Job: " + theJob.getName());
        model.addAttribute("job", theJob);
        // TODO #1 - Done

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid@NotNull JobForm jobForm, Errors errors) {
        if (errors.hasErrors()){
;           model.addAttribute(jobForm);
            return "job-detail";
        }

        Job newJob = new Job(
                jobForm.getName(),
                jobData.getEmployers().findById(jobForm.getEmployerId()),
                jobData.getLocations().findById(jobForm.getLocationsId()),
                jobData.getPositionTypes().findById(jobForm.getPositionTypesId()),
                jobData.getCoreCompetencies().findById(jobForm.getCoreCompetenciesId())
        );
        jobData.add(newJob);

        return "redirect:?id=" + newJob.getId();
        // TODO #6 - Complete


    }
}
