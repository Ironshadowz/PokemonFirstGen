package sg.edu.nus.iss.day28_pokemon.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @GetMapping("/id")
    public ModelAndView getPokemon(@RequestParam("id") String id)
    {
        Pokemon pokemon = pokeSer.getById(id);
        ModelAndView mav = new ModelAndView();
        if(pokemon.getId()==null)
        {
            mav.setViewName("NotFound");
            mav.addObject("NoPokemon", HttpStatus.valueOf(404));
            return mav;
        }
        mav.setViewName("Single");
        mav.addObject("pokemon", pokemon);
        return mav;
    }
}
