package app.carstore.web;


import app.carstore.service.BrandService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/brands/all")
    public String getAllBrand (Model model)
    {

       model.addAttribute("brands",brandService.getAllVehicles());

        return "brands";
    }

}
