package sg.edu.nus.iss.day28_pokemon.Model;

import java.util.HashMap;
import java.util.List;

import org.springframework.util.MultiValueMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pokemon
{
    private String id;
    private String name;
    private String img;
    private List<String> type;
    private Stats stats;
}
