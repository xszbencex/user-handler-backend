package interview.userhandlerbackend;

import interview.userhandlerbackend.model.UserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = UserHandlerBackendApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
		locations = "classpath:application-integration-test.properties")
public class UserHandlerBackendApplicationIntegrationTests {

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

	@Autowired
	private MockMvc mvc;

	@Test
	public void whenSignUp_thenStatus200() throws Exception {
		UserDTO user = new UserDTO();
		user.setEmail("test@email.com");
		user.setUsername("test");
		user.setPassword("test");
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(user);

		mvc.perform(MockMvcRequestBuilders.post("/user/signUp").contentType(APPLICATION_JSON_UTF8).content(requestJson))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
	}

	@Test
	public void givenTestUser_whenSignIn_thenStatus200() throws Exception {
		final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("username", "admin");
		params.add("password", "admin");
		mvc.perform(MockMvcRequestBuilders.post("/user/authenticate").params(params).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
	}

	@Test
	@WithMockUser(username = "admin", authorities = { "ADMIN", "PUBLIC" })
	public void givenAdminUser_whenGetUsers_thenStatus200() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/user").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@Test
	@WithMockUser(username = "admin", authorities = { "ADMIN", "PUBLIC" })
	public void givenAdminUser_whenDelete_thenStatus200() throws Exception {
		mvc.perform(MockMvcRequestBuilders.delete("/user/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "admin", authorities = { "ADMIN", "PUBLIC" })
	public void givenAdminUser_whenUpdate_thenStatus200() throws Exception {
		UserDTO user = new UserDTO();
		user.setPhone("test");
		user.setName("test");
		user.setAddress("test");
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(user);

		mvc.perform(MockMvcRequestBuilders.put("/user/1").contentType(APPLICATION_JSON_UTF8).content(requestJson))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
}
