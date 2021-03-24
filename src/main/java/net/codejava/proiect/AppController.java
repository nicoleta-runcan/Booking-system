package net.codejava.proiect;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private LocatieRepository locRepo;

    @Autowired
    private CalatoriiRepository calRepo;

    private User currrentUser;

    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "signup_form";
    }


    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);
        //CustomUserDetailsService.currentUser
        return "users";
    }

    @GetMapping("/recommendations")
    public String showRecomandari(Model model) {
        List<Locatie> listLocatie = locRepo.findAll();

        List<Calatorii> listLegaturi = calRepo.findAll();

        Recommendation recommendation = new Recommendation();

        List <Locatie> recomandari = recommendation.getRecommandations(CustomUserDetailsService.currentUser, listLocatie, listLegaturi);

        model.addAttribute("listLocatii", recomandari);
        return "recommendations";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepo.save(user);

        return "register_success";
    }

    @PostMapping("/ceva_test")
    public String printSelect(SearchCriteria searchCriteria, Model model){

        List<Locatie> listLocatie = locRepo.findAll();
        SearchedItems searched = new SearchedItems();
        List <Locatie> recomandari = new ArrayList<>();
        recomandari = searched.choose(listLocatie,searchCriteria);

        model.addAttribute("listLocatii", recomandari);
        return "search_result";
    }



    @GetMapping("/search")
    public String showSearch(Model model) {

        model.addAttribute("searchCriteria", new SearchCriteria());
        return "search";
    }


    @PostMapping("/home")
    public String homePage(Model model) {
        List<User> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);

        return "users";
    }


    @PostMapping("/final")
    public String finalPage(Model model){
        return "final";
    }
}
