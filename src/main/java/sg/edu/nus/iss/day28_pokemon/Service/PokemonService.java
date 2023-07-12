package sg.edu.nus.iss.day28_pokemon.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import sg.edu.nus.iss.day28_pokemon.Model.Pokemon;
import sg.edu.nus.iss.day28_pokemon.Repository.PokemonRepo;

@Service
public class PokemonService 
{
    @Autowired
    PokemonRepo pokeRepo;

    public List<String> listOfType()
    {
        List<Document> types = pokeRepo.showAllTypes();
        List<String> list = new ArrayList<>();
        for(Document d:types)
        {
            String type = d.getString("_id");
            list.add(type);
        }
        return list;
    }
    public List<Pokemon> getPokemons(String type)
    {
        List<Document> doc = pokeRepo.findByType(type);
        List<Pokemon> pokemonList = new ArrayList<>();
        for(Document d : doc)
        {
            Pokemon one = new Pokemon();
            one.setId(d.getString("id"));
            one.setImg(d.getString("img"));
            one.setName(d.getString("name"));
            List<String> types = (List<String>) d.get("type");
            one.setType(types);
           // HashMap<String, String> stats = d.get("stats");
            //one.setStats(stats);
            pokemonList.add(one);
        }
        return pokemonList;
    }
}
