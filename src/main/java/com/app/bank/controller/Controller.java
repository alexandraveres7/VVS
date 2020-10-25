package com.app.bank.controller;

import com.app.bank.model.*;
import com.app.bank.service.BankClientService;
import com.app.bank.service.BankService;
import com.app.bank.service.InternalRevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller

public class Controller {
        @Value("${spring.application.name}")
        String appName;

        @Autowired
        BankClientService bankClientService;

        @Autowired
        BankService bankService;

        @Autowired
        private InternalRevenueService irs;

        @GetMapping("/")
        public String homePage(Model model) {
            model.addAttribute("appName", appName);
            return "home";
        }

      @RequestMapping(value = "/listBanks", method = RequestMethod.GET)
      public String listBanks(Model model) {

        List<Bank> a = bankService.getBanks();
        model.addAttribute("banksLIST", a);
        return "listbanks";
    }

    @GetMapping("/addBank")
    public String addBank(Model model){
        model.addAttribute("bank", new InternationalBank("ING", "Int"));

        return "addbank";
    }

    @PostMapping("/addBank")
    public String addBANK(@ModelAttribute("bank") InternationalBank bank, BindingResult bindingResult){
        bankService.addBank(bank);

        if (bindingResult.hasErrors()) {
            return "addbank";
        }

        return "redirect:/listBanks";
    }

    @GetMapping("/removeBank")
    public String removeBank(Model model){
        model.addAttribute("name", "");

        return "removeBank";
    }

    @PostMapping("/removeBank")
    public String removeBANK(@ModelAttribute("name") String name){
        try{
            bankService.removeBank(name);
            return "redirect:/listBanks";
        } catch (IllegalArgumentException i){
            return "deletebankerror";
        }
    }


    @GetMapping("/addClient")
    public String addClient(Model model){
        model.addAttribute("client", new RegularClient("12345678", "Ale"));

        return "addClient";
    }

    @PostMapping("/addClient")
    public String addCLIENT(@ModelAttribute("client") RegularClient client, BindingResult bindingResult){
        bankClientService.registerClient(client);

        if (bindingResult.hasErrors()) {
            return "addClient";
        }

        return "redirect:/listClients";
    }

    @GetMapping("/removeClient")
    public String removeClient(Model model){
            model.addAttribute("CNP", "");

            return "removeClient";
    }

    @PostMapping("/removeClient")
    public String removeCLIENT(@ModelAttribute("CNP") String CNP){
        try{
            bankClientService.removeClientAccount(CNP);
            return "redirect:/listClients";
        } catch (Exception e){
            return "deleteclienterror";
        }
    }

    @RequestMapping(value = "/listClients", method = RequestMethod.GET)
    public String list(Model model) {

        List<Client> a = bankClientService.getClients().findAll();
        model.addAttribute("clientsLIST", a);
        return "listclients";
    }


    @RequestMapping(value = "/deposit", method = RequestMethod.GET)
    public String deposit(Model model) {
        model.addAttribute("CNP", "");
        model.addAttribute("sum", "");
        model.addAttribute("currency","");

        return "deposit";
    }

    @RequestMapping(value = "/deposit", method = RequestMethod.POST)
    public String depositPOST(@ModelAttribute("CNP") String CNP,
                              @ModelAttribute("sum") Double sum, @ModelAttribute("currency") String currency) {
        bankClientService.startObserving(CNP);
        bankClientService.deposit(CNP, sum, currency);
        bankClientService.stopObserving(CNP);

        return "redirect:/listClients";
    }

   @RequestMapping(value = "/withdraw", method = RequestMethod.GET)
    public String withdraw(Model model) {
       model.addAttribute("CNP", "");
       model.addAttribute("sum", "");
       model.addAttribute("currency","");

        return "withdraw";
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public String withdrawPOST(@ModelAttribute("CNP") String CNP,
                               @ModelAttribute("sum") String sum, @ModelAttribute("currency") String currency){
        bankClientService.startObserving(CNP);
        bankClientService.withdraw(CNP, Double.parseDouble(sum), currency);
        bankClientService.stopObserving(CNP);

        return "redirect:/listClients";
    }

    @GetMapping("/checkbalance")
    public String checkBalance(Model model){
        model.addAttribute("CNP", "");

        return "clientbalance";
    }

    @PostMapping("/checkbalance")
    public String checkBALANCE(@ModelAttribute("CNP") String CNP, Model m){
        Client c = bankClientService.searchByCNP(CNP);

        m.addAttribute("client", c);
        return "balance";
    }

    @GetMapping("/addobservedclient")
    public String addClientToIRS(Model model){
            model.addAttribute("client", new RegularClient("3", "A"));

            return "addirsclient";
    }

    @PostMapping("/addobservedclient")
    public String addIRSClient(@ModelAttribute("client") RegularClient client, BindingResult bindingResult){
            irs.registerClient(client);

            if(bindingResult.hasErrors()){
                return "addirsclient";
            }
            return "redirect:/listIRSClients";
    }

    @GetMapping("/listIRSClients")
    public String showIRSObservedClients(Model model){
            List<Client> list = irs.getObservedClients();
            model.addAttribute("observedclients", list);

            return "observedclients";
    }
}
