package com.szakdologzat.repiceapp.web.rest;

import com.szakdologzat.repiceapp.domain.Recipe;
import com.szakdologzat.repiceapp.domain.User;
import com.szakdologzat.repiceapp.repository.RecipeBookRepository;
import com.szakdologzat.repiceapp.repository.RecipeRepository;
import com.szakdologzat.repiceapp.repository.UserRepository;
import com.szakdologzat.repiceapp.service.UserService;
import com.szakdologzat.repiceapp.service.dto.*;
import com.szakdologzat.repiceapp.service.mapper.RecipeBookMapper;
import com.szakdologzat.repiceapp.service.mapper.RecipeBookRelationMapper;
import com.szakdologzat.repiceapp.service.mapper.RecipeMapper;
import com.szakdologzat.repiceapp.service.mapper.UserMapper;
import java.security.Principal;
import java.util.*;
import java.util.Collections;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

@RestController
@RequestMapping("/api")
public class PublicUserResource {

    private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(
        Arrays.asList("id", "login", "firstName", "lastName", "email", "activated", "langKey")
    );

    private static final Logger LOG = LoggerFactory.getLogger(PublicUserResource.class);

    private final UserService userService;
    private final UserMapper usermapper;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final RecipeBookRepository recipeBookRepository;
    private final RecipeBookMapper recipeBookMapper;
    private final RecipeBookRelationMapper recipeBookRelationMapper;
    private final RecipeMapper recipeMapper;

    public PublicUserResource(
        UserService userService,
        RecipeRepository recipeRepository,
        UserRepository userRepository,
        UserMapper usermapper,
        RecipeBookRepository recipeBookRepository,
        RecipeBookMapper recipeBookMapper,
        RecipeBookRelationMapper recipeBookRelationMapper,
        RecipeMapper recipeMapper
    ) {
        this.userService = userService;
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.usermapper = usermapper;
        this.recipeBookRepository = recipeBookRepository;
        this.recipeBookMapper = recipeBookMapper;
        this.recipeBookRelationMapper = recipeBookRelationMapper;
        this.recipeMapper = recipeMapper;
    }

    /**
     * {@code GET /users} : get all users with only public information - calling this method is allowed for anyone.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all users.
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllPublicUsers(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get all public User names");
        if (!onlyContainsAllowedProperties(pageable)) {
            return ResponseEntity.badRequest().build();
        }

        final Page<UserDTO> page = userService.getAllPublicUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/user/{login}")
    @Transactional
    public ResponseEntity<ProfileDTO> getUser(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @PathVariable String login,
        Principal principal
    ) {
        LOG.debug("REST request to get public User");
        ProfileDTO profileDTO = new ProfileDTO();

        Page<Recipe> recipePage = recipeRepository.findFirstUserRecipes(login, pageable);

        List<RecipeDTO> recipeDTOs = recipePage.getContent().stream().map(recipeMapper::toDto).toList();

        profileDTO.setRecipes(recipeDTOs);
        AdminUserDTO admin = userRepository
            .findOneByLogin(login)
            .map(usermapper::userToAdminUserDTO)
            .orElseThrow(() -> new RuntimeException("User not found"));
        //        admin.ifPresent(profileDTO::setAdminUser);
        if (principal != null && principal.getName().equals(admin.getLogin())) {
            profileDTO.setRecipeBooks(
                recipeBookRepository
                    .findAllByUserWithPrivate(admin.getLogin())
                    .stream()
                    .map(recipeBookMapper::toDtoWithRecipeImages)
                    .collect(Collectors.toList())
            );
        } else {
            profileDTO.setRecipeBooks(
                recipeBookRepository.findAllByUser(admin.getLogin()).stream().map(recipeBookMapper::toDto).collect(Collectors.toList())
            );
        }

        profileDTO.setAdminUser(admin);
        profileDTO.setSumRecipeBooks(recipeBookRepository.countByUser(admin.getLogin()));
        profileDTO.setSumRecipes(recipeRepository.countRecipesByUser(admin.getLogin()));
        return ResponseEntity.status(HttpStatus.OK).body(profileDTO);
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }
}
