package sg.edu.nus.iss.day28_pokemon.Repository;

import java.io.StringReader;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Repository
public class PokemonRepo 
{
    @Autowired
    private MongoTemplate template;

    private static final String C_pokemon = "pokemon";
    /*
     * db.pokemon.aggregate
     * ([
     *      {$match:{type:""}}
     * ])
     */
    public List<Document> findByType(String type)
    {
        MatchOperation matchedType = Aggregation.match(Criteria.where("type").is(type));
        Aggregation pipeline = Aggregation.newAggregation(matchedType);
        AggregationResults<Document> results = template.aggregate(pipeline, C_pokemon, Document.class);
        return results.getMappedResults();
    }
    /*db.pokemon.aggregate
    ([
    {
        $unwind:"$type"    
    },
    {
        $group : {_id:"$type"}
    },
    {
        $sort:{_id:1}
    }
    ]) 
    */
    public List<Document> showAllTypes()
    {
        AggregationOperation unwindType = Aggregation.unwind("type");
        GroupOperation groupByType = Aggregation.group("type");
        //ProjectionOperation projectAllTypes = Aggregation.project("_id");
        SortOperation sortById = Aggregation.sort(Sort.by(Direction.DESC,"_id"));
        Aggregation pipeline = Aggregation.newAggregation(unwindType, groupByType, sortById);
        AggregationResults<Document> results = template.aggregate(pipeline, C_pokemon, Document.class);
        return results.getMappedResults();
    }

    public Document pokemon(String name)
    {
        Criteria criteria = Criteria.where("name").regex(name,"i");
        Query query = Query.query(criteria);
        List<Document> result = template.find(query, Document.class, C_pokemon);

        Document root = result.get(0);
        Document stats = root.get("stats", Document.class);
        for (String i : List.of("hp", "attack", "defense", "spattack", "spdefense", "speed")) 
        {
            System.out.printf("%s: %s\n", i, stats.get(i));
        }

        Document moves = root.get("moves", Document.class);
        List<Document> level = moves.getList("level", Document.class);
        for(Document d : level)
        {
            System.out.printf("name %s\n", d.getString("name"));
        }

        // Reading from Json 
        JsonReader jsonReader = Json.createReader(new StringReader(root.toJson()));
        JsonObject pokemonJson = jsonReader.readObject();
        JsonObject statsJson = pokemonJson.getJsonObject("stats");
        for(String i : List.of("hp", "attack", "defense", "spattack", "spdefense", "speed"))
        {
            System.out.printf("%s:%s\n", i, statsJson.get(i));
        }
            JsonArray levelJson = pokemonJson.getJsonObject("moves").getJsonArray("level");
            levelJson.stream()
                .map(o->o.asJsonObject())
                .forEach(o->{
                                 System.out.printf("name: %s\n", o.getString("name"));
                            }
                        );
         return stats;
    }
    public Optional<Document> findPokemonById(String id) 
    {
        ObjectId docId = new ObjectId(id);
        return Optional.ofNullable(template.findById(docId, Document.class, C_pokemon));
    }
}
