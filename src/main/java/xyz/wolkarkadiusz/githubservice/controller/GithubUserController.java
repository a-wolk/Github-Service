package xyz.wolkarkadiusz.githubservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.wolkarkadiusz.githubservice.dto.ErrorResponse;
import xyz.wolkarkadiusz.githubservice.exception.ExtractFieldsException;
import xyz.wolkarkadiusz.githubservice.model.GitRepo;
import xyz.wolkarkadiusz.githubservice.service.GithubUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class GithubUserController {
    private final GithubUserService githubUserService;

    @Autowired
    public GithubUserController(GithubUserService githubUserService){
        this.githubUserService = githubUserService;
    }

    @GetMapping("/{username}/repos")
    public ResponseEntity<?> getRepos(@PathVariable("username") String username,
                                      @RequestParam(value = "fields", required = false, defaultValue = "") List<String> fields,
                                      @RequestParam(value = "exclude_forks", required = false, defaultValue = "false") Boolean exclude_forks,
                                      @RequestParam(value = "per_page", required = false, defaultValue = "50") Integer per_page,
                                      @RequestParam(value = "page", required = false, defaultValue = "1") Integer page){
        try{
            var repos = new ArrayList<>();
            for(GitRepo repo : githubUserService.getRepos(username, per_page, page)){
                if(exclude_forks && repo.getFork())
                    continue;
                repos.add(repo.toMap(fields));
            }
            return ResponseEntity.ok(repos);
        }catch (ExtractFieldsException e){
            return ResponseEntity.badRequest().body(new ErrorResponse("No such fields present in object."));
        }
    }

    @GetMapping("/{username}/stars")
    public Integer getStars(@PathVariable("username") String username){
        return githubUserService.getStarsCount(username);
    }

    @GetMapping("/{username}/languages")
    public Map<String, Integer> getLanguages(@PathVariable("username") String username){
        return githubUserService.getLanguages(username);
    }
}
