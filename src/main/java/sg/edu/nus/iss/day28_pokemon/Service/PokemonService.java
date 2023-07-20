package sg.edu.nus.iss.day28_pokemon.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import sg.edu.nus.iss.day28_pokemon.Model.Pokemon;
import sg.edu.nus.iss.day28_pokemon.Model.Stats;
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
            Document docu = pokeRepo.pokemon(d.getString("name"));
            Stats stat = new Stats();
            stat.setAttack(docu.get("attack").toString());
            stat.setDefense(docu.get("defense").toString());
            stat.setHp(docu.get("hp").toString());
            stat.setSpattack(docu.get("spattack").toString());
            stat.setSpdefense(docu.get("spdefense").toString());
            stat.setSpeed(docu.get("speed").toString());
            one.setStats(stat);
            pokemonList.add(one);
        }
        return pokemonList;
    }
    public Pokemon getById(String id)
    {
        Pokemon newPokemon = new Pokemon();
        Optional<Document> pokemon = pokeRepo.findPokemonById(id);
        if(!pokemon.isPresent())
        {
            return newPokemon;
        } else
        {
            Document poke = pokemon.get();
            newPokemon.setId(poke.getString("id"));
            newPokemon.setImg(poke.getString("img"));
            newPokemon.setName(poke.getString("name"));
            List<String> types = (List<String>) poke.get("type");
            newPokemon.setType(types);
           // HashMap<String, String> stats = d.get("stats");
            //one.setStats(stats);
            Document docu = pokeRepo.pokemon(poke.getString("name"));
            Stats stat = new Stats();
            stat.setAttack(docu.get("attack").toString());
            stat.setDefense(docu.get("defense").toString());
            stat.setHp(docu.get("hp").toString());
            stat.setSpattack(docu.get("spattack").toString());
            stat.setSpdefense(docu.get("spdefense").toString());
            stat.setSpeed(docu.get("speed").toString());
            newPokemon.setStats(stat);
            return newPokemon;
        }
    }
}
