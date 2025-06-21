package de.alixcja.home.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.alixcja.home.persistence.entity.Console;
import de.alixcja.home.persistence.entity.ConsoleAccessory;
import de.alixcja.home.persistence.entity.Game;
import de.alixcja.home.persistence.repository.BookingEntityRepository;
import de.alixcja.home.persistence.repository.ConsoleAccessoryRepository;
import de.alixcja.home.persistence.repository.ConsoleRepository;
import de.alixcja.home.persistence.repository.GameRepository;
import de.alixcja.home.resource.requestbody.ArchiveUpdateRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BookingEntityResourceTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  GameRepository gameRepository;

  @Autowired
  ConsoleRepository consoleRepository;

  @Autowired
  ConsoleAccessoryRepository consoleAccessoryRepository;

  @Autowired
  BookingEntityRepository bookingEntityRepository;

  @Autowired
  ObjectMapper objectMapper;

  private Game game;
  private Console console;
  private ConsoleAccessory accessory;

  @BeforeEach
  void setUp() {
    game = new Game("Mario Kart Deluxe 8", "This is a nice game which breaks friendships!", "Nintendo Switch");
    gameRepository.save(game);

    console = new Console("Nintendo Switch", "The most expensive console in the world");
    consoleRepository.save(console);

    accessory = new ConsoleAccessory("Controller", "Idk, I'm not that creative", "Nintendo Switch");
    accessory.setArchived(true);
    consoleAccessoryRepository.save(accessory);
  }

  @Test
  void shouldReturnListWithAllEntities() throws Exception {

    mvc.perform(get("/entities")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status()
                    .isOk())
            .andExpect(content()
                    .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[*].name", containsInAnyOrder("Nintendo Switch", "Mario Kart Deluxe 8", "Controller")))
            .andExpect(jsonPath("$[*].type", containsInAnyOrder("CONSOLE", "GAME", "CONSOLE_ACCESSORY")))
            .andExpect(jsonPath("$[*].archived", containsInAnyOrder(false, true, false)));
  }

  @Test
  void shouldReturnListWithNotArchivedEntities() throws Exception {
    mvc.perform(get("/entities")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("archived", "false"))
            .andExpect(status()
                    .isOk())
            .andExpect(content()
                    .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()",
                    is(2)))
            .andExpect(jsonPath("$[*].name", containsInAnyOrder("Nintendo Switch", "Mario Kart Deluxe 8")))
            .andExpect(jsonPath("$[*].archived", containsInAnyOrder(false, false)));
  }

  @Test
  void shouldReturnListWithArchivedEntities() throws Exception {
    mvc.perform(get("/entities")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("archived", "true"))
            .andExpect(status()
                    .isOk())
            .andExpect(content()
                    .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()",
                    is(1)))
            .andExpect(jsonPath("$[*].name",
                    containsInAnyOrder("Controller")))
            .andExpect(jsonPath("$[*].archived",
                    containsInAnyOrder(true)));
  }

  @Test
  void shouldReturnEntityById() throws Exception {

    mvc.perform(get("/entities/" + game.getId())
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status()
                    .isOk())
            .andExpect(content()
                    .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("name",
                    is("Mario Kart Deluxe 8")))
            .andExpect(jsonPath("type",
                    is("GAME")));
  }

  @Test
  void shouldNotReturnEntityByIdDueNonExistingId() throws Exception {
    mvc.perform(get("/entities/9999")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status()
                    .isNotFound());
  }

  @Test
  void shouldArchiveEntityById() throws Exception {
    mvc.perform(patch("/entities/" + game.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"archived\": true}"))
            .andExpect(status()
                    .isNoContent());
  }

  @Test
  void shouldNotArchiveEntityByIdDueNonExistingId() throws Exception {
    ArchiveUpdateRequest request = new ArchiveUpdateRequest(true);

    mvc.perform(patch("/entities/9999")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(request)))
            .andExpect(status()
                    .isNotFound());
  }

  @AfterEach
  void tearDown() {
    bookingEntityRepository.deleteAll();
  }
}