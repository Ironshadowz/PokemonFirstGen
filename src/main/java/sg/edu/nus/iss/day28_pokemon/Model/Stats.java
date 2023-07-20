package sg.edu.nus.iss.day28_pokemon.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stats 
{
    private String hp;
    private String attack;
    private String defense;
    private String spattack;
    private String spdefense;
    private String speed;
}
