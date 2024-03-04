package app.carstore.web;

import app.carstore.exception.ObjectNotFoundException;
import app.carstore.model.dto.offer.CreateOrUpdateOfferDTO;
import app.carstore.model.dto.offer.SearchOfferDTO;
import app.carstore.service.BrandService;
import app.carstore.service.OfferService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;


@Controller
public class OfferController {

    private final OfferService offerService;
    private final BrandService brandService;

    public OfferController(OfferService offerService,
                           BrandService brandService) {

        this.offerService = offerService;
        this.brandService = brandService;
    }


    @GetMapping("/offers/all")
    public String allOffers(Model model,
                            @PageableDefault(
                                    sort = "price",
                                    direction = Sort.Direction.ASC,
                                    size = 4)
                                    Pageable pageable) {

        model.addAttribute("offers", offerService.getAllOffers(pageable));

        return "offers";
    }

    @GetMapping("/offers/search")
    public String searchOffers(@Valid SearchOfferDTO searchOfferDTO,
                               BindingResult bindingResult,
                               Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("searchOfferModel", searchOfferDTO);
            model.addAttribute(
                    "org.springframework.validation.BindingResult.searchOfferModel",
                    bindingResult);

            return "redirect:/offer-search";
        }

        if (!model.containsAttribute("searchOfferModel")) {
            model.addAttribute("searchOfferModel", searchOfferDTO);
        }

        if (!searchOfferDTO.isEmpty()) {
            model.addAttribute("offers", offerService.searchOffer(searchOfferDTO));
        }


        return "offer-search";
    }

    @GetMapping("/offers/add")
    public String addOffer(Model model) {
        if (!model.containsAttribute("addOfferModel")) {
            model.addAttribute("addOfferModel", new CreateOrUpdateOfferDTO());
        }

        model.addAttribute("brands", brandService.getAllBrand());

        return "offer-add";
    }

    @PostMapping("/offers/add")
    public String addOffer(@Valid CreateOrUpdateOfferDTO addOfferModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           @AuthenticationPrincipal UserDetails userDetails) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("addOfferModel", addOfferModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addOfferModel", bindingResult);

            return "redirect:/offers/add";
        }

        offerService.addOffer(addOfferModel, userDetails);


        return "redirect:/offers/all";
    }

    @GetMapping("/offers/{id}")
    public String getOfferDetails(@PathVariable("id") Long id,
                                  Model model) {

        var offerDetailDTO
                = offerService.findOfferById(id).orElseThrow(() -> new ObjectNotFoundException("Offer with id " + id + " not found"));

        model.addAttribute("offer", offerDetailDTO);


        return "details";
    }


    @PreAuthorize("@offerService.isOwner(#principal.name, #id)")
    @DeleteMapping("/offers/{id}")
    public String deleteOffer(Principal principal,
                              @PathVariable("id") Long id) {
        offerService.deleteOfferById(id);

        return "redirect:/offers/all";
    }


    @GetMapping("/offers/{id}/update")
    public String editOffer(@PathVariable("id") Long id, Model model) {

        var offer = offerService.getEditOffer(id).
                orElseThrow(() -> new ObjectNotFoundException("Offer with ID " + id + "not found"));

        model.addAttribute("updateOfferModel", offer);


        return "update";
    }

    @PostMapping("/offers/{id}/update")
    public String updateOffer(@PathVariable("id") Long id,
                              CreateOrUpdateOfferDTO updateOfferDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes
                              ) {

        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("updateOfferModel", updateOfferDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addOfferModel", bindingResult);

            return "redirect:/offers/{id}/update";
        }

        offerService.updateOffer(updateOfferDTO, id);

        return "redirect:/offers/all";
    }


}
