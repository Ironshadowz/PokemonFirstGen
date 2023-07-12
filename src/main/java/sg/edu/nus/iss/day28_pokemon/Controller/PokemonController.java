package sg.edu.nus.iss.day28_pokemon.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.nus.iss.day28_pokemon.Model.Pokemon;
import sg.edu.nus.iss.day28_pokemon.Service.PokemonService;

@Controller
@RequestMapping("/home")
public class PokemonController 
{
    @Autowired
    PokemonService pokeSer;

    @GetMapping
    public ModelAndView showAllTypes()
    {
        ModelAndView mav = new ModelAndView();
        List<String> typeList = pokeSer.listOfType();
        mav.setViewName("Type");
        mav.addObject("typeList", typeList);
        return mav;
    }
    @GetMapping("/{type}")
    public ModelAndView pokemons(@PathVariable("type") String type)
    {
        List<Pokemon> pokemons = pokeSer.getPokemons(type);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("ListByType");
        mav.addObject("type", type);
        mav.addObject("types", pokemons);
        return mav;
    }
}
