package com.backend.domain.chat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.backend.domain.chat.entity.Chat;
import com.backend.domain.chat.entity.MessageType;
import com.backend.domain.chat.repository.ChatRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ChatControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ChatRepository chatRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	private final String postId = "1";
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@BeforeAll
	void beforeAll() {
		mongoTemplate.dropCollection(Chat.class);
		Objects.requireNonNull(redisTemplate.keys("chatList:*")).forEach(redisTemplate::delete);
	}

	@BeforeEach
	void setup() {
		redisTemplate.delete("chat:list:" + postId);

		chatRepository.saveAll(List.of(
			new Chat(postId, "1", "User1", "Hello!", MessageType.CHAT, LocalDateTime.now().format(formatter)),
			new Chat(postId, "2", "User2", "Hi!", MessageType.CHAT, LocalDateTime.now().format(formatter)),
			new Chat(postId, "3", "User3", "How are you?", MessageType.CHAT, LocalDateTime.now().format(formatter)),
			new Chat(postId, "4", "User4", "I'm good!", MessageType.CHAT, LocalDateTime.now().format(formatter))
		));
	}


	@AfterAll
	void afterAll() {
		mongoTemplate.dropCollection(Chat.class);
		Objects.requireNonNull(redisTemplate.keys("chatList:*")).forEach(redisTemplate::delete);
	}

	@Test
	@DisplayName("채팅 목록 전체 조회 성공")
	void testGetChattingList() throws Exception {
		mockMvc.perform(get("/api/v1/chat/list/{postId}", postId)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.code").value(200))
			.andExpect(jsonPath("$.data.chats").isArray())
			.andExpect(jsonPath("$.data.chats.length()").value(4))
			.andExpect(jsonPath("$.data.chats[0].userId").value("1"))
			.andExpect(jsonPath("$.data.chats[0].username").value("User1"))
			.andExpect(jsonPath("$.data.chats[0].content").value("Hello!"))
			.andExpect(jsonPath("$.data.chats[0].type").value("CHAT"))
			.andDo(print());
	}

}
