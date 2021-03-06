package xyz.wolkarkadiusz.githubservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import xyz.wolkarkadiusz.githubservice.exception.ExtractFieldsException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Used in parsing GithubAPI responses.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class GitRepo implements Serializable {
    String name;
    String html_url;
    String description;

    Integer stargazers_count;
    Integer forks_count;
    Boolean fork;

    public Map<String, Object> toMap(List<String> fields) throws ExtractFieldsException {
        var map = new HashMap<String, Object>();
        //default fields
        map.put("name", name);
        map.put("stars", stargazers_count);

        //remove duplicate fields
        fields = fields.stream()
                    .filter(field -> !field.equals("name") && !field.equals("stargazers_count"))
                    .collect(Collectors.toList());

        //retrieve values
        try{
            for(String field : fields){
                var classField = GitRepo.class.getDeclaredField(field);
                map.put(field, classField.get(this));
            }
        }catch (NoSuchFieldException | IllegalAccessException e){
            throw new ExtractFieldsException("Object does not contain \"" + e.getMessage() + "\" field.");
        }
        return map;
    }
}
