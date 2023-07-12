package sg.edu.nus.iss.day28_pokemon.Repository;

import java.util.List;

import org.bson.Document;
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
import org.springframework.stereotype.Repository;

@Repository
public class PokemonRepo 
{
    @Autowired
    private MongoTemplate template;

    private static final String C_pokemon = "pokemon";

    public List<Document> findByType(String type)
    {
        MatchOperation matchedType = Aggregation.match(Criteria.where("type").is(type));
        Aggregation pipeline = Aggregation.newAggregation(matchedType);
        AggregationResults<Document> results = template.aggregate(pipeline, C_pokemon, Document.class);
        return results.getMappedResults();
    }
    
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
}
